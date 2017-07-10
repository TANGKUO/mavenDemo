package com.tangkuo.cn.pay.zftk.common.http;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * 
* @ClassName: HttpClientMultiThreaded
* @Description: (HttpClientMultiThreaded)
 */
@Deprecated
public class HttpClientMultiThreaded {
	private static Log log = LogFactory.getLog(HttpClientMultiThreaded.class);
	
	public static HttpClient httpClient = null;

	private static int maxTotal = 2000;
	private static int maxPerRout = 50;
	private static int connectTimeOut = 10000;
	private static int readTimeOut = 30000;	

	static {
		X509TrustManager tm = new X509TrustManager() {
	        @SuppressWarnings("unused")
	        public void checkClientTrusted(X509Certificate[] xcs, String string)
	                        throws CertificateException {
	        }
	        @SuppressWarnings("unused")
	        public void checkServerTrusted(X509Certificate[] xcs, String string)
	                        throws CertificateException {
	        }
	        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return null;
	        }
	        @Override
	        public void checkClientTrusted(
	                java.security.cert.X509Certificate[] chain, String authType)
	                throws CertificateException {
	        }
	        @Override
	        public void checkServerTrusted(
	                java.security.cert.X509Certificate[] chain, String authType)
	                throws CertificateException {
	        }
	    };
	    
	    SSLContext ctx = null;
	    try {
	    	ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[] { tm }, null);
	    } catch(Exception ex) {
	    	log.error(ex);
	    }	    
		SSLSocketFactory socketFactory = new SSLSocketFactory(ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		
		Scheme httpsScheme = new Scheme("https", 443, socketFactory);
		Scheme httpScheme = new Scheme("http", 80, PlainSocketFactory.getSocketFactory());
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(httpsScheme);
		schemeRegistry.register(httpScheme);
				
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
		cm.setMaxTotal(maxTotal);
		cm.setDefaultMaxPerRoute(maxPerRout);

		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, connectTimeOut);
		HttpConnectionParams.setSoTimeout(params, readTimeOut);
		HttpConnectionParams.setTcpNoDelay(params, true);
		params.setLongParameter("http.conn-manager.timeout", (long)connectTimeOut * 3);

		httpClient = new DefaultHttpClient(cm, params);	
		
	}	
	
	public static HttpClient getHttpClient() {
		return httpClient;
	}
	
	public void setConnectionTimeout(int timeOut) {
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), timeOut);
	}
	
	public void setReadTimeout(int timeOut) {
		HttpConnectionParams.setSoTimeout(httpClient.getParams(), timeOut);
	}
	
	public static int sendSimplePostRequest(String strUrl,	Map<String, Object> params, Map<String,String> reqHeads, String reqEncoding) {
		int responseCode = 0;
		
		if(StringUtils.isEmpty(reqEncoding)) {
			reqEncoding = HTTP.UTF_8;
		}
		HttpPost post = new HttpPost(strUrl);
		List<NameValuePair> nameValueList = new ArrayList<NameValuePair>();
		Iterator<String> keyIter = params.keySet().iterator();
		while(keyIter.hasNext()) {
			String keyStr = keyIter.next();
			nameValueList.add(new BasicNameValuePair(keyStr, params.get(keyStr).toString()));
		}
		
		if(reqHeads != null) {
			Iterator<String> headIter = reqHeads.keySet().iterator();
			while(headIter.hasNext()) {
				String key = headIter.next();
				post.addHeader(key, reqHeads.get(key));
			}
		}
		
		try {
			StringEntity entity = new UrlEncodedFormEntity(nameValueList,reqEncoding);
			post.setEntity(entity);
			HttpResponse response = httpClient.execute(post);
			responseCode = response.getStatusLine().getStatusCode();
			
			HttpEntity responseEntity = response.getEntity();
			if(responseEntity != null) {
				InputStream ins = responseEntity.getContent();
				if(ins != null) {
					ins.close();
				}
			}
			
		} catch(Exception ex) {
			log.error("send post request error,URL:"+strUrl, ex);
			post.abort();
		}
		
		return responseCode;
	}

	public static int sendSimplePostRequest(String strUrl,	Map<String, Object> params) {
		return sendSimplePostRequest(strUrl, params, null, null);
	}
	
	/**
	 * 
	* @Title: sendPostRequestEntity
	* @Description: (返回http响应状态码及响应内容)
	* @param @param strUrl
	* @param @param params
	* @param @param reqHeads
	* @param @param reqEncoding
	* @param @param returnResponse
	* @return String[]    返回类型
	* @throws
	 */
	public static String[] sendPostRequestEntity(String strUrl,	Map<String, Object> params, Map<String,String> reqHeads, String reqEncoding, boolean returnResponse) {
		int responseCode = 0;
		if(StringUtils.isEmpty(reqEncoding)) {
			reqEncoding = HTTP.UTF_8;
		}
		
		HttpPost post = new HttpPost(strUrl);
		List<NameValuePair> nameValueList = new ArrayList<NameValuePair>();
		Iterator<String> keyIter = params.keySet().iterator();
		while(keyIter.hasNext()) {
			String keyStr = keyIter.next();
			nameValueList.add(new BasicNameValuePair(keyStr, params.get(keyStr).toString()));
		}
		
		if(reqHeads != null) {
			Iterator<String> headIter = reqHeads.keySet().iterator();
			while(headIter.hasNext()) {
				String key = headIter.next();
				post.addHeader(key, reqHeads.get(key));
			}
		}
		
		try {
			StringEntity reqEntity = new UrlEncodedFormEntity(nameValueList,reqEncoding);
			post.setEntity(reqEntity);
			
			HttpResponse response = httpClient.execute(post);
			responseCode = response.getStatusLine().getStatusCode();
			HttpEntity responseEntity = response.getEntity();
			
			if(responseCode >= 300) {
				EntityUtils.consume(responseEntity);
				return new String[] {String.valueOf(responseCode), null};
			} else {
				String responseStr = null;
				if(responseEntity != null && returnResponse) {
					responseStr = EntityUtils.toString(responseEntity);
				} else {
					EntityUtils.consume(responseEntity);
				}
				return new String[] {String.valueOf(responseCode), responseStr};
			}
			
		} catch(Exception ex) {
			log.error("send post request error,URL:"+strUrl, ex);
			post.abort();
			
			return new String[] {String.valueOf(responseCode), ex.getMessage()};
		}
	}
	
	public static String[] sendPostRequestEntity(String strUrl,	Map<String, Object> params) {
		return sendPostRequestEntity(strUrl, params, null, null, true);
	}
	
	public static String[] sendPostRequestEntity(String strUrl,	Map<String, Object> params, boolean returnResponse) {
		return sendPostRequestEntity(strUrl, params, null, null, returnResponse);
	}
	
	/**
	 * 
	* @Title: sendPostRequest
	* @Description: ()
	* @param @param strUrl
	* @param @param params
	* @param @param reqHeads
	* @param @param reqEncoding
	* @return String    返回类型
	* @throws
	 */
	public static String sendPostRequest(String strUrl,	Map<String, Object> params, Map<String,String> reqHeads, String reqEncoding) {
		String responseStr = null;
		
		if(StringUtils.isEmpty(reqEncoding)) {
			reqEncoding = HTTP.UTF_8;
		}
		HttpPost post = new HttpPost(strUrl);		
		
		List<NameValuePair> nameValueList = new ArrayList<NameValuePair>();
		Iterator<String> keyIter = params.keySet().iterator();
		while(keyIter.hasNext()) {
			String keyStr = keyIter.next();
			nameValueList.add(new BasicNameValuePair(keyStr, params.get(keyStr).toString()));
		}
		
		if(reqHeads != null) {
			Iterator<String> headIter = reqHeads.keySet().iterator();
			while(headIter.hasNext()) {
				String key = headIter.next();
				post.addHeader(key, reqHeads.get(key));
			}
		}
		
		try {			
			StringEntity entity = new UrlEncodedFormEntity(nameValueList, reqEncoding);
			post.setEntity(entity);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseStr = httpClient.execute(post, responseHandler);
			
		} catch(Exception ex) {
			log.error("send post request error,URL:"+strUrl, ex);
			post.abort();
		}

		return responseStr;
	}
	
	
	public static String sendPostRequest(String strUrl,	Map<String, Object> params, String reqEncoding) {
		return sendPostRequest(strUrl, params, null, reqEncoding);
	}
	
	public static String sendPostRequest(String strUrl,	HttpEntity entity, Map<String,String> reqHeads) {
		String responseStr = null;		
		
		HttpPost post = new HttpPost(strUrl);
		
		if(reqHeads != null) {
			Iterator<String> headIter = reqHeads.keySet().iterator();
			while(headIter.hasNext()) {
				String key = headIter.next();
				post.addHeader(key, reqHeads.get(key));
			}
		}
		
		try {
			post.setEntity(entity);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseStr = httpClient.execute(post, responseHandler);
			
		} catch(Exception ex) {
			log.error("send post request error,URL:"+strUrl, ex);
			post.abort();
		} 

		return responseStr;
	}
	
	/**
	 * 
	* @Title: sendGetRequest
	* @Description: ()
	* @param @param strUrl
	* @param @param reqHeads
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public static String sendGetRequest(String strUrl, Map<String,String> reqHeads) {		
		String responseStr = null;
		
		HttpGet get = new HttpGet(strUrl);
		if(reqHeads != null) {
			Iterator<String> headIter = reqHeads.keySet().iterator();
			while(headIter.hasNext()) {
				String key = headIter.next();
				get.addHeader(key, reqHeads.get(key));
			}
		}		
		
		try {			
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseStr = httpClient.execute(get, responseHandler);
			
		} catch(Exception ex) {
			log.error("send get request error,URL:"+strUrl, ex);
			get.abort();
		}

		return responseStr;
	}
	
	/**
	 * 
	* @Title: shutdown
	* @Description: (关闭连接)
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
	public static void shutdown() {
		httpClient.getConnectionManager().shutdown();
	}

}
