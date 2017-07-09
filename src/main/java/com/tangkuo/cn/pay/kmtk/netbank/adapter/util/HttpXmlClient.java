package com.tangkuo.cn.pay.kmtk.netbank.adapter.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangkuo.cn.pay.kmtk.netbank.ObjectUtils;

@SuppressWarnings("deprecation")
public class HttpXmlClient {
	
	private final static Logger log = LoggerFactory.getLogger(HttpXmlClient.class);

	public static String postGB18030(String url, Map<?,?> paramMap) {
		return post(url, paramMap, "GB18030");
	}
	
	public static String post(String url, Map<?,?> params, String toFilePath, String charsetName) {
		if (StringUtils.isEmpty(url) || ObjectUtils.isEmpty(params)
				|| StringUtils.isEmpty(toFilePath)) {
			log.info("post the to file Path null.");
			return "";
		}
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,"Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");
		
		PostMethod method = new PostMethod(url);
		Iterator<?> it = params.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			Object o = params.get(key);
			if (o != null && o instanceof String) {
				method.addParameter(new NameValuePair(key.toString(), o.toString()));
			}
			if (o != null && o instanceof String[]) {
				String[] s = (String[]) o;
				if (s != null){
					for (int i = 0; i < s.length; i++) {
						method.addParameter(new NameValuePair(key.toString(), s[i]));
					}
				}
			}
		}
		try {
			int statusCode = httpClient.executeMethod(method);
			log.info(method.getStatusLine().toString());
			log.info("httpClientUtils:statusCode="+statusCode);
			if (statusCode == HttpStatus.SC_OK) {
				InputStream instream = method.getResponseBodyAsStream();
				org.apache.commons.httpclient.Header contentHeader = method.getResponseHeader("Content-Disposition");
		        String filename = "";  
		        if (contentHeader != null) {  
		        	org.apache.commons.httpclient.HeaderElement[] values = contentHeader.getElements();  
		            if (values.length == 1) {
		            	org.apache.commons.httpclient.NameValuePair param = values[0].getParameterByName("filename");  
		                if (param != null) {  
		                    try {  
		                        filename = param.getValue();  
		                    } catch (Exception e) {  
		                        log.error(e.getMessage(), e);
		                    }  
		                }  
		            }  
		        }
		        toFilePath = toFilePath + filename;
                FileOutputStream fos = null;
                try {
                    // 创建文件对象  
                	File f = new File(toFilePath);  
                    // 创建文件路径  
                    if (!f.getParentFile().exists())  
                        f.getParentFile().mkdirs();  
                    // 写入文件  
                    BufferedInputStream in=new BufferedInputStream(instream);
                    fos = new FileOutputStream(new File(toFilePath));
    				byte[] inputByte = new byte[1024];
    				int length=0;
    				while((length=in.read(inputByte,0,inputByte.length))>0){
    					fos.write(inputByte,0,length);
    					fos.flush();
    				}
                } catch (Exception e) {  
                    log.error("保存文件错误,path=" + toFilePath + ",url=" + url, e);  
                } finally {  
                    try {  
                        if (fos != null) fos.close();  
                    } catch (Exception e) {  
                        log.error("finally BufferedOutputStream shutdown close",e);  
                    }  
                }
			}
			//content = new String(method.getResponseBody(), code);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if(method!=null)method.releaseConnection();
			method = null;
			httpClient = null;
		}
		return toFilePath;
	}
	
	public static String post(String url, Map<?,?> paramMap, String charsetName) {
		String content = StringUtils.EMPTY;
		if(StringUtils.isEmpty(url) || ObjectUtils.isEmpty(paramMap)){
			return content;
		}
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,"Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");
		
		PostMethod method = new PostMethod(url);
		Iterator<?> it = paramMap.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			Object o = paramMap.get(key);
			if (o != null && o instanceof String) {
				method.addParameter(new NameValuePair(key.toString(), o.toString()));
			}
			if (o != null && o instanceof String[]) {
				String[] s = (String[]) o;
				if (s != null){
					for (int i = 0; i < s.length; i++) {
						method.addParameter(new NameValuePair(key.toString(), s[i]));
					}
				}
			}
		}
		try {
			int statusCode = httpClient.executeMethod(method);
			log.info(method.getStatusLine().toString());
			log.info("httpClientUtils::statusCode="+statusCode);
			content = new String(method.getResponseBody(), charsetName);
		} catch (Exception e) {
			log.error("time out");
			log.error(e.getMessage(), e);
		} finally {
			if(method!=null)method.releaseConnection();
			method = null;
			httpClient = null;
		}
		return content;
	}
	/**
	 * POST方式发送
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url, Map<String, String> params) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;

		log.info("create httppost:" + url);
		HttpPost post = postForm(url, params);

		body = invoke(httpclient, post);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	public static String get(String url, String toFileDir, String charsetName) {
		HttpClient httpClient			= new HttpClient();
		GetMethod getMethod				= new GetMethod(url);
		try {
			int statusCode				= httpClient.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK) {
				InputStream instream = getMethod.getResponseBodyAsStream();
				org.apache.commons.httpclient.Header contentHeader = getMethod.getResponseHeader("Content-Disposition");
		        String filename = "";  
		        if (contentHeader != null) {
		        	org.apache.commons.httpclient.HeaderElement[] values = contentHeader.getElements();  
		            if (values.length == 1) {
		            	org.apache.commons.httpclient.NameValuePair param = values[0].getParameterByName("filename");  
		                if (param != null) {
		                	filename = param.getValue();  
		                }  
		            }  
		        }
		        if (StringUtils.isEmpty(filename)) {
		        	return new String(getMethod.getResponseBody(), charsetName);
		        }
		        toFileDir += filename;
                FileOutputStream fos = null;
                try {
                    // 创建文件对象  
                	File f = new File(toFileDir);  
                    // 创建文件路径  
                    if (!f.getParentFile().exists())  
                        f.getParentFile().mkdirs();  
                    // 写入文件  
                    BufferedInputStream in=new BufferedInputStream(instream);
                    fos = new FileOutputStream(f);
    				byte[] inputByte = new byte[1024];
    				int length=0;
    				while((length=in.read(inputByte,0,inputByte.length))>0){
    					fos.write(inputByte,0,length);
    					fos.flush();
    				}
                } catch (Exception e) {  
                    log.error("保存文件错误,path=" + toFileDir + ",url=" + url, e);  
                } finally {
                	IOUtils.closeQuietly(fos);
                }
			}
		} catch (IOException e) {
			log.error("保存文件错误:" + e.getMessage(), e);  
		} finally {
			if(getMethod != null) {
				getMethod.releaseConnection();
			}
			getMethod = null;
			httpClient = null;
		}
		return toFileDir;
	}
	
	/**
	 * get 方式发送
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;

		log.info("create httppost:" + url);
		HttpGet get = new HttpGet(url);
		body = invoke(httpclient, get);

		httpclient.getConnectionManager().shutdown();

		return body;
	}


	private static String invoke(DefaultHttpClient httpclient,
			HttpUriRequest httpost) {

		HttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response);

		return body;
	}

	private static String paseResponse(HttpResponse response) {
		log.info("get response from http server..");
		HttpEntity entity = response.getEntity();

		log.info("response status: " + response.getStatusLine());
		String charset = EntityUtils.getContentCharSet(entity);
		log.info(charset);

		String body = null;
		try {
			body = EntityUtils.toString(entity);
			log.debug(body);
		} catch (ParseException e) {
			log.error(e.getMessage(),e);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}

		return body;
	}

	private static HttpResponse sendRequest(DefaultHttpClient httpclient,
			HttpUriRequest httpost) {
		log.info("execute post...");
		//异常代码，需要谨慎全局控制
		//http://www.fwqtg.net/tomcat-%E5%BC%80%E5%90%AFhttps-%E5%90%8E%E7%88%86%E5%8F%91ssl%E7%9B%B8%E5%85%B3%E6%BC%8F%E6%B4%9E%E8%A7%A3%E5%86%B3%E6%96%B9%E6%B3%95.html
		//System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		HttpResponse response = null;
		long timeOutNum = 0;
		try {
			long startTime = System.currentTimeMillis();
			while(response == null && timeOutNum < 60000) {
				response = httpclient.execute(httpost);
				timeOutNum = System.currentTimeMillis() - startTime;
			}
		} catch (ClientProtocolException e) {
			log.error(e.getMessage(),e);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		//System.getProperties().remove("https.protocols");
		return response;
	}

	private static HttpPost postForm(String url, Map<String, String> params){

		HttpPost httpost = new HttpPost(url);
		List<org.apache.http.NameValuePair> nvps = new ArrayList <org.apache.http.NameValuePair>();

		Set<String> keySet = params.keySet();
		for(String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}

		try {
			log.info("set utf-8 form entity to httppost");
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}

		return httpost;
	}
}