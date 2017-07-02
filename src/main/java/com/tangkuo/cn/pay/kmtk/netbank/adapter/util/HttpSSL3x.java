package com.tangkuo.cn.pay.kmtk.netbank.adapter.util;

import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;

public class HttpSSL3x {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// process("",null,null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String process(String url, String reqXml, String contentType,
			String chantName) throws Exception {
		String postUrl = "";
		postUrl = url;

		String result = "";

		HttpClient httpClient = new HttpClient();
		if (postUrl.startsWith("https:")) {
			//enableSSL(postUrl, httpClient);// 测试环境ssl证书过期需要增加此行绕过，生产时取消此行即可
		}
		PostMethod method = new PostMethod(postUrl);

		/*
		 * method.addRequestHeader("Content-Type",
		 * "application/xml; charset=utf-8");
		 */

		try {
			method.setRequestEntity(new StringRequestEntity(reqXml,
					contentType, chantName));

			httpClient.executeMethod(method);

			String resXml = method.getResponseBodyAsString();
			result = resXml;
			System.out.println(resXml);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return result;

	}

	public static String process(String url, Map<?, ?> params,
			String contentType, String chantName) throws Exception {
		String postUrl = "";
		postUrl = url;

		String result = "";

		HttpClient httpClient = new HttpClient();
		if (postUrl.startsWith("https:")) {
			enableSSL(postUrl, httpClient);// 测试环境ssl证书过期需要增加此行绕过，生产时取消此行即可
		}
		PostMethod method = new PostMethod(postUrl);

		try {
			Iterator<?> it = params.keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				Object o = params.get(key);
				if (o != null && o instanceof String) {
					method.addParameter(new NameValuePair(key.toString(), o
							.toString()));
				}
				if (o != null && o instanceof String[]) {
					String[] s = (String[]) o;
					if (s != null) {
						for (int i = 0; i < s.length; i++) {
							method.addParameter(new NameValuePair(key
									.toString(), s[i]));
						}
					}
				}
			}

			httpClient.executeMethod(method);

			String resXml = method.getResponseBodyAsString();
			result = resXml;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return result;
	}

	private static void enableSSL(String url, HttpClient client) {

		try {
			setSSLProtocol(url, client);
		} catch (Exception e) {
			System.out.println("setProtocol error " + e);
		}
	}

	private static void setSSLProtocol(String strUrl, HttpClient client)
			throws Exception {
		URL url = new URL(strUrl);
		String host = url.getHost();
		int port = url.getPort();
		if (port <= 0) {
			port = 443;
		}

		Protocol authhttps = new Protocol("https",
				new HttpProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", authhttps);

		// set https protocol
		System.setProperty("apache.commons.httpclient.cookiespec",
				"compatibility");
		client.getHostConfiguration().setHost(host, port, authhttps);
	}
}
