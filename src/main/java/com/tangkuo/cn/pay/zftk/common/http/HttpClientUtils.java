package com.tangkuo.cn.pay.zftk.common.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.tangkuo.cn.pay.zftk.common.xml.XMLBeanTransform;

public class HttpClientUtils {
	private static Log log = LogFactory.getLog(HttpClientUtils.class);
	
	private static int CONNECT_TIMEOUT = 10000;
	private static int READ_TIMEOUT = 30000;
	private static int maxTotal = 2000;
	private static int maxPerRout = 50;
	
	//特殊https请求的转发，如：jdk1.6不支持的https证书地址
	private static boolean httpsProxy = false;
	//重试请求，转发http请求到代理服务器
	private static boolean retryReq = true;
	
	private HttpClient httpClient = null;
	private volatile static HttpClientUtils instance = null;	
	
	private static synchronized void initHttpClient(boolean dnsCache, boolean filterHttpsProxy) {
		
		if(instance == null) {
			log.info("begin init httpClient,dns cache is " + dnsCache);
			
			X509TrustManager tm = new X509TrustManager() {
		        
		        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		                return null;
		        }
		        public void checkClientTrusted(X509Certificate[] chain, String authType)  throws CertificateException {
		        }
		        public void checkServerTrusted(X509Certificate[] chain, String authType)  throws CertificateException {
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
					
			PoolingClientConnectionManager cm = null;
			if(dnsCache) {
				CacheDnsResolver dnsResolver = new CacheDnsResolver();
				cm = new PoolingClientConnectionManager(schemeRegistry, dnsResolver);
				Executors.newScheduledThreadPool(1).scheduleAtFixedRate(dnsResolver, 1, 2, TimeUnit.HOURS);
			} else {
				cm = new PoolingClientConnectionManager(schemeRegistry);
			}
			
			
			cm.setMaxTotal(maxTotal);
			cm.setDefaultMaxPerRoute(maxPerRout);

			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, CONNECT_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, READ_TIMEOUT);
			HttpConnectionParams.setTcpNoDelay(params, true);
			params.setLongParameter("http.conn-manager.timeout", (long)CONNECT_TIMEOUT );
			params.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, true);  //检测连接是否可用			

			instance = new HttpClientUtils();
			DefaultHttpClient client = new DefaultHttpClient(cm, params);
			//重试次数为0
			client.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
			instance.httpClient = client ;
			
			httpsProxy = filterHttpsProxy;			
			ProxyServerConfig.init();
			if(ProxyServerConfig.domainNameLists.size() == 0) {
				httpsProxy = false;
			}
			if(ProxyServerConfig.proxyServerAddressList.size() == 0) {
				retryReq = false;
			}
			
			log.info("httpClient init success.");
		}
		
	}
	
	public static HttpClientUtils getInstance(boolean dnsCache) {
		if(instance == null) {
			initHttpClient(dnsCache, false);
		}
		
		return instance;
	}
	
	public static HttpClientUtils getInstance(boolean dnsCache, boolean filterHttpsProxy) {
		if(instance == null) {
			initHttpClient(dnsCache, filterHttpsProxy);
		}
		
		return instance;
	}
	
	public static HttpClientUtils getInstance(boolean dnsCache, int connectionTimeout, int readTimeout) {
		if(instance == null) {
			CONNECT_TIMEOUT = connectionTimeout;
			READ_TIMEOUT = readTimeout;
			initHttpClient(dnsCache, false);
		}
		
		return instance;
	}
	
	public static HttpClientUtils getInstance() {
		return getInstance(true);
	}
	
	public void setConnectionTimeout(int timeOut) {
		HttpConnectionParams.setConnectionTimeout(instance.httpClient.getParams(), timeOut);
	}
	
	public void setReadTimeout(int timeOut) {
		HttpConnectionParams.setSoTimeout(instance.httpClient.getParams(), timeOut);
	}
	
	
	public int sendSimplePostRequest(String strUrl,	Map<String, Object> params, Map<String,String> reqHeads, String reqEncoding) {
		int responseCode = 0;
		if(reqHeads == null) {
			reqHeads = new HashMap<String,String>();
		}
		
		if(httpsProxy) {			
			strUrl = filterHttpsProxyUrl(strUrl, reqHeads);
		}		
		
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
			//转发http请求，重试一次
			if(retryReq && !reqHeads.containsKey("notifyURL")) {
				String proxyUrl = ProxyServerConfig.getProxyServer();			
				reqHeads.put("notifyURL", strUrl);
				return sendSimplePostRequest(proxyUrl, params, reqHeads, reqEncoding);
			}
		}
		
		return responseCode;
	}

	public int sendSimplePostRequest(String strUrl,	Map<String, Object> params) {
		return sendSimplePostRequest(strUrl, params, null, null);
	}
	
	/*
	 * 返回http响应状态码及响应内容
	 */
	public String[] sendPostRequestEntity(String strUrl, Map<String, Object> params, Map<String,String> reqHeads, String reqEncoding, boolean returnResponse) {
		int responseCode = 0;
		if(reqHeads == null) {
			reqHeads = new HashMap<String,String>();
		}
		
		if(httpsProxy) {			
			strUrl = filterHttpsProxyUrl(strUrl, reqHeads);
		}
		
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
			//转发http请求，重试一次
			if(retryReq && !reqHeads.containsKey("notifyURL")) {
				String proxyUrl = ProxyServerConfig.getProxyServer();			
				reqHeads.put("notifyURL", strUrl);
				return sendPostRequestEntity(proxyUrl, params, reqHeads, reqEncoding, returnResponse);
			}
			
			return new String[] {String.valueOf(responseCode), ex.getMessage()};
		}
	}
	
	public String[] sendPostRequestEntity(String strUrl,	Map<String, Object> params) {
		return sendPostRequestEntity(strUrl, params, null, null, true);
	}
	
	public String[] sendPostRequestEntity(String strUrl,	Map<String, Object> params, boolean returnResponse) {
		return sendPostRequestEntity(strUrl, params, null, null, returnResponse);
	}
	
	public String sendPostRequest(String strUrl, Map<String, Object> params, Map<String,String> reqHeads, String reqEncoding) {
		String responseStr = null;
		if(reqHeads == null) {
			reqHeads = new HashMap<String,String>();
		}
		
		if(httpsProxy) {			
			strUrl = filterHttpsProxyUrl(strUrl, reqHeads);
		}
		
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
			//转发http请求，重试一次
			if(retryReq && !reqHeads.containsKey("notifyURL")) {
				String proxyUrl = ProxyServerConfig.getProxyServer();			
				reqHeads.put("notifyURL", strUrl);
				return sendPostRequest(proxyUrl, params, reqHeads, reqEncoding);
			}
		}

		return responseStr;
	}
	
	
	public String sendPostRequest(String strUrl, Map<String, Object> params, String reqEncoding) {
		return sendPostRequest(strUrl, params, null, reqEncoding);
	}
	
	/**
	 * 增加带有返回编码设置的httpPost方法
	 * @param strUrl
	 * @param reqEncoding
	 * @param respEncoding
	 * @param params
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String sendPostRequest(String strUrl,String reqEncoding,String respEncoding, Map<String, Object> params) {
		List<NameValuePair> nameValueList = new ArrayList<NameValuePair>();
		Iterator<String> keyIter = params.keySet().iterator();
		while(keyIter.hasNext()) {
			String keyStr = keyIter.next();
			nameValueList.add(new BasicNameValuePair(keyStr, params.get(keyStr).toString()));
		}
		StringEntity entity = null;
		if(StringUtils.isEmpty(reqEncoding)) {
			reqEncoding = HTTP.UTF_8;
		}
		if(StringUtils.isEmpty(respEncoding)) {
			respEncoding = HTTP.UTF_8;
		}
		try {
			entity = new UrlEncodedFormEntity(nameValueList, reqEncoding);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(),e);
			return null;
		}
		return sendPostRequest(strUrl,entity,null,respEncoding);
	}
	
	public String sendPostRequest(String strUrl, HttpEntity entity, Map<String,String> reqHeads,String respEncoding) {
		String responseStr = null;
		if(reqHeads == null) {
			reqHeads = new HashMap<String,String>();
		}
		
		if(httpsProxy) {			
			strUrl = filterHttpsProxyUrl(strUrl, reqHeads);
		}
		
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
			if(null != respEncoding){
				HttpResponse response = httpClient.execute(post);
				responseStr = EntityUtils.toString(response.getEntity(),respEncoding);
			}else{
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				responseStr = httpClient.execute(post, responseHandler);
			}
		} catch(Exception ex) {
			log.error("send post request error,URL:"+strUrl, ex);
			post.abort();
			//转发http请求，重试一次
			if(retryReq && !reqHeads.containsKey("notifyURL")) {
				String proxyUrl = ProxyServerConfig.getProxyServer();			
				reqHeads.put("notifyURL", strUrl);
				return sendPostRequest(proxyUrl, entity, reqHeads, respEncoding);
			}
		} 

		return responseStr;
	}
	
	public String sendPostRequest(String strUrl, Map<String, Object> params, Map<String,String> reqHeads,String reqEncoding,String respEncoding) {
		String responseStr = null;
		if(reqHeads == null) {
			reqHeads = new HashMap<String,String>();
		}
		
		if(httpsProxy) {			
			strUrl = filterHttpsProxyUrl(strUrl, reqHeads);
		}
		
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
			if(null != respEncoding){
				HttpResponse response = httpClient.execute(post);
				responseStr = EntityUtils.toString(response.getEntity(),respEncoding);
			}else{
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				responseStr = httpClient.execute(post, responseHandler);
			}
		} catch(Exception ex) {
			log.error("send post request error,URL:"+strUrl, ex);
			post.abort();
			//转发http请求，重试一次
			if(retryReq && !reqHeads.containsKey("notifyURL")) {
				String proxyUrl = ProxyServerConfig.getProxyServer();			
				reqHeads.put("notifyURL", strUrl);
				return sendPostRequest(proxyUrl, params, reqHeads, reqEncoding, respEncoding);
			}
		} 
		
		return responseStr;
	}
	
	public String sendPostRequest(String strUrl,	HttpEntity entity, Map<String,String> reqHeads){
		return sendPostRequest(strUrl,entity,reqHeads,null);
	}
	
	public String sendGetRequest(String strUrl, Map<String,String> reqHeads) {		
		String responseStr = null;
		if(reqHeads == null) {
			reqHeads = new HashMap<String,String>();
		}
		
		if(httpsProxy) {			
			strUrl = filterHttpsProxyUrl(strUrl, reqHeads);
		}
		
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
			//转发http请求，重试一次
			if(retryReq && !reqHeads.containsKey("notifyURL")) {
				String proxyUrl = ProxyServerConfig.getProxyServer();			
				reqHeads.put("notifyURL", strUrl);
				return sendGetRequest(proxyUrl, reqHeads);
			}
		}

		return responseStr;
	}
	
	/**
	 * 如果是jdk1.6不支持的https证书地址，通过代理服务进行转发请求
	 * @param url
	 * @param reqHeads
	 * @return
	 */
	private static String filterHttpsProxyUrl(String url, Map<String,String> reqHeads) {
		String strUrl = null;
		if(url.startsWith("https") && ProxyServerConfig.isProxyDomainName(url)) {
			strUrl = ProxyServerConfig.getProxyServer();			
			reqHeads.put("notifyURL", url);
		} else {
			strUrl = url;
		}
		
		return strUrl;
	}
	
	public HttpClient getHttpClient(boolean dnsCache) {
		return getInstance(dnsCache).httpClient;
	}
	
	public void shutdown() {
		httpClient.getConnectionManager().shutdown();
	}
	
	
	public static Object sendXMLRequest(String strURL, Object xmlObject, Class<?> returnClass, int readTimeout) {
		OutputStream ous = null;
		InputStream ins = null;
		Object returnObject = null;
		if(log.isDebugEnabled()) {
			log.debug("send xml http request,xml object:"+xmlObject.toString());
		}
		HttpURLConnection httpConn = null;
		try {
			URL url = new URL(strURL);
			httpConn = (HttpURLConnection)url.openConnection();
			httpConn.setRequestMethod("POST");
			httpConn.setRequestProperty("Content-Type","text/xml");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			httpConn.setConnectTimeout(CONNECT_TIMEOUT);
			httpConn.setReadTimeout(readTimeout);
			httpConn.connect();
			
			ous = httpConn.getOutputStream();
			XMLBeanTransform.transformObject2XML(xmlObject, ous);
			ous.flush();
			ins = httpConn.getInputStream();
			returnObject = XMLBeanTransform.transformXML2Object(ins, returnClass);			
			
		} catch(Exception ex) {
			log.error("send xml request error,xml object:"+xmlObject, ex);
		} finally {
			if(ous != null) {
				try {
					ous.close();
				} catch(Exception e) {}				
			}
			if(ins != null) {
				try {
					ins.close();
				} catch(Exception e) {}
			}
			if(httpConn != null) {
				httpConn.disconnect();
			}
		}
		
		return returnObject;
	}
	
	public static Object sendXMLRequest(String strURL, Object xmlObject, Class returnClass) {
		return sendXMLRequest(strURL, xmlObject, returnClass, READ_TIMEOUT);
	}
	
	public static int sendXMLRequest(String strURL, Object xmlObject) {
		OutputStream ous = null;
		int responseCode = 0;
		HttpURLConnection httpConn = null;
		try {
			URL url = new URL(strURL);
			httpConn = (HttpURLConnection)url.openConnection();
			httpConn.setRequestMethod("POST");
			httpConn.setRequestProperty("Content-Type","text/xml");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			httpConn.setConnectTimeout(CONNECT_TIMEOUT);
			httpConn.setReadTimeout(READ_TIMEOUT);
			httpConn.connect();
			
			ous = httpConn.getOutputStream();
			XMLBeanTransform.transformObject2XML(xmlObject, ous);
			ous.flush();
			
			responseCode = httpConn.getResponseCode();
			
		} catch(Exception ex) {
			log.error("send xml request error,xml object:"+xmlObject, ex);
		} finally {
			if(ous != null) {
				try {
					ous.close();
				} catch(Exception e) {}
			}
			if(httpConn != null) {
				httpConn.disconnect();
			}
		}
		
		return responseCode;
	}

}