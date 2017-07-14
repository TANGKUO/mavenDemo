package com.tangkuo.cn.pay.zftk.util;

import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
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
 * @ClassName: HttpClientUtils
 * @Description: (封装单列模式)
 * @author tangkuo
 * @date 2017年7月14日 下午8:35:22
 *
 */
public class HttpClientUtils {
	private static int CONNECT_TIMEOUT = 20000;
	private static int READ_TIMEOUT = 60000;
	private static int maxTotal = 2000;
	private static int maxPerRout = 50;

	private HttpClient httpClient = null;
	private static HttpClientUtils instance = null;

	private static synchronized void initHttpClient() {

		if (instance == null) {

			X509TrustManager tm = new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
				}
			};

			SSLContext ctx = null;
			try {
				ctx = SSLContext.getInstance("TLS");
				ctx.init(null, new TrustManager[] { tm }, null);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			Scheme httpsScheme = new Scheme("https", 443, socketFactory);
			Scheme httpScheme = new Scheme("http", 80, PlainSocketFactory.getSocketFactory());
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(httpsScheme);
			schemeRegistry.register(httpScheme);

			PoolingClientConnectionManager cm = null;
			cm = new PoolingClientConnectionManager(schemeRegistry);

			cm.setMaxTotal(maxTotal);
			cm.setDefaultMaxPerRoute(maxPerRout);

			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, CONNECT_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, READ_TIMEOUT);
			HttpConnectionParams.setTcpNoDelay(params, true);
			params.setLongParameter("http.conn-manager.timeout", (long) CONNECT_TIMEOUT * 3);

			instance = new HttpClientUtils();
			instance.httpClient = new DefaultHttpClient(cm, params);
		}

	}

	public static HttpClientUtils getInstance() {
		if (instance == null) {
			initHttpClient();
		}
		return instance;
	}

	/**
	 * 发送post请求到指定地址
	 * 
	 * @param strUrl
	 *            请求地址
	 * @param params
	 *            参数
	 * @param reqHeads
	 *            请求头
	 * @param reqEncoding
	 *            编码
	 * @return
	 */
	public String sendPostRequest(String strUrl, Map<String, Object> params, Map<String, String> reqHeads,
			String reqEncoding) {
		String responseStr = null;

		if (null == (reqEncoding) || "".equals(reqEncoding)) {
			reqEncoding = HTTP.UTF_8;
		}
		HttpPost post = new HttpPost(strUrl);

		List<NameValuePair> nameValueList = new ArrayList<NameValuePair>();
		Iterator<String> keyIter = params.keySet().iterator();
		while (keyIter.hasNext()) {
			String keyStr = keyIter.next();
			nameValueList.add(new BasicNameValuePair(keyStr, params.get(keyStr).toString()));
		}

		if (reqHeads != null) {
			Iterator<String> headIter = reqHeads.keySet().iterator();
			while (headIter.hasNext()) {
				String key = headIter.next();
				post.addHeader(key, reqHeads.get(key));
			}
		}

		try {
			StringEntity entity = new UrlEncodedFormEntity(nameValueList, reqEncoding);
			post.setEntity(entity);
			HttpResponse resp = httpClient.execute(post);
			responseStr = EntityUtils.toString(resp.getEntity());
		} catch (Exception ex) {
			ex.printStackTrace();
			post.abort();
		}

		return responseStr;
	}

	/**
	 * 发送post请求
	 * 
	 * @param strUrl
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param reqEncoding
	 *            编码方式
	 * @return
	 */
	public String sendPostRequest(String strUrl, Map<String, Object> params, String reqEncoding) {
		return sendPostRequest(strUrl, params, null, reqEncoding);
	}
}
