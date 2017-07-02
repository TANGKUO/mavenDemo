package com.tangkuo.cn.pay.kmtk.netbank.adapter.util;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: HttpTransport
 * @Description: (HTTP通信类,)
 * @author tangkuo
 * @date 2017年7月2日 下午3:38:15
 * @see com.bocnet.communitcate.HttpTransport
 */
public final class HttpTransport {
	private static Logger logger = LoggerFactory.getLogger(HttpTransport.class);

	/**
	 * 实现采用POST方法向目标地址进行HTTP连接
	 * 
	 * @param uri
	 *            目标地址
	 * @param nvps
	 *            KEY/VALUE对
	 * @return utf-8 string of response
	 * @throws Exception
	 */
	public static String post(String uri, List<NameValuePair> nvps) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			logger.debug("", httpPost.getRequestLine());
			logger.debug(EntityUtils.toString(httpPost.getEntity()));

			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				String content = EntityUtils.toString(response.getEntity(), "UTF-8");
				EntityUtils.consume(entity);
				return content;
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}

	/**
	 * 实现采用POST方法向目标地址进行HTTP连接
	 * 
	 * @param uri
	 *            目标地址
	 * @param nvps
	 *            KEY/VALUE对
	 * @return byte array of response
	 * @throws Exception
	 */
	public static byte[] post2(String uri, List<NameValuePair> nvps) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));

			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				byte[] content = EntityUtils.toByteArray(response.getEntity());
				EntityUtils.consume(entity);
				return content;
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}

}
