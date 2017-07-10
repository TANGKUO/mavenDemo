package com.tangkuo.cn.pay.zftk.common.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
/**
 * 
* @ClassName: HttpClientSSL
* @Description: (HttpClientSSL)
 */
public class HttpClientSSL {
	private static Log log = LogFactory.getLog(HttpClientSSL.class);
	
	private static int connectTimeOut = 10000;
	private static int readTimeOut = 30000;	
	
	/**
	 * 
	* @Title: sendPostRequest
	* @Description: (sendPostRequest)
	* @param @param ctx
	* @param @param strUrl
	* @param @param params
	* @param @param reqHeads
	* @param @param reqEncoding
	* @param @param respEncoding
	* @return String    返回类型
	* @throws
	 */
	public static String sendPostRequest(SSLContext ctx, String strUrl,	Map<String, Object> params, Map<String,String> reqHeads, String reqEncoding,String respEncoding) {
		if(StringUtils.isEmpty(reqEncoding)) {
			reqEncoding = HTTP.UTF_8;
		}
		List<NameValuePair> nameValueList = new ArrayList<NameValuePair>();
		Iterator<String> keyIter = params.keySet().iterator();
		while(keyIter.hasNext()) {
			String keyStr = keyIter.next();
			nameValueList.add(new BasicNameValuePair(keyStr, params.get(keyStr).toString()));
		}
		try {
			StringEntity entity = new UrlEncodedFormEntity(nameValueList, reqEncoding);
			return sendPostRequest(ctx, strUrl, entity, reqHeads, respEncoding);
		} catch (UnsupportedEncodingException e) {
			log.error("send post request error,URL:"+strUrl, e);
		}
		return null;
	}
	
	public static String sendPostRequest(SSLContext ctx, String strUrl,	Map<String, Object> params, Map<String,String> reqHeads, String reqEncoding) {
		return sendPostRequest(ctx, strUrl, params, reqHeads, reqEncoding, null);
	}
	
	/**
	 * 
	* @Title: sendPostRequest
	* @Description: (sendPostRequest)
	* @param  ctx SSLContext
	* @param  strUrl String
	* @param  entity HttpEntity
	* @param  reqHeads Map<String,String>
	* @param  respEncoding String
	* @return String    返回类型
	* @throws
	 */
	public static String sendPostRequest(SSLContext ctx, String strUrl,	HttpEntity entity, Map<String,String> reqHeads, String respEncoding) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
		SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
		Scheme scheme = new Scheme("https", 443, socketFactory);
		httpClient.getConnectionManager().getSchemeRegistry().register(scheme);
		httpClient.getParams().setParameter("http.connection.timeout", connectTimeOut);
		httpClient.getParams().setParameter("http.socket.timeout", readTimeOut);
		httpClient.getParams().setLongParameter("http.conn-manager.timeout", (long)connectTimeOut);
		
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
		} finally {			
			httpClient.getConnectionManager().shutdown();
		}

		return responseStr;
	}
	
	public static String sendPostRequest(SSLContext ctx, String strUrl,	Map<String, Object> params, Map<String,String> reqHeads) {
		return sendPostRequest(ctx, strUrl, params, reqHeads, null);
	}
	
	public static String sendPostRequest(SSLContext ctx, String strUrl,	Map<String, Object> params) {
		return sendPostRequest(ctx, strUrl, params, null, null);
	}

}
