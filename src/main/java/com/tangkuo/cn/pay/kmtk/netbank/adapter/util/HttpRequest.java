package com.tangkuo.cn.pay.kmtk.netbank.adapter.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangkuo.cn.pay.kmtk.netbank.common.TtyException;



public class HttpRequest {
	
	private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			//System.out.println("发送GET请求出现异常！" + e);
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/** 
	* @Title: sendPost 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param url 请求地址
	* @param @param param 请求参数
	* @param @param contentType
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/ 
	public static String sendPost(String url, String param, String contentType) {
		return sendPost("", "", url, param, contentType, false, "");
	}
	
	/** 
	* @Title: sendPost 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param url 请求地址
	* @param @param param 请求参数
	* @param @param contentType
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/ 
	public static String sendPost(String url, String param, String contentType,String charset) {
		return sendPost("", "", url, param, contentType, false, charset);
	}
	
	/** 
	* @Title: sendPost 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param url 请求地址
	* @param @param param 请求参数
	* @param @param contentType
	* @param @param useProxy 是否使用代理
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/ 
	public static String sendPost(String url, String param, String contentType, boolean useProxy) {
		return sendPost("", "", url, param, contentType, useProxy, "");
	}
	
	/** 
	* @Title: sendPost 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param url 请求地址
	* @param @param param 请求参数
	* @param @param contentType
	* @param @param useProxy 是否使用代理
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/ 
	public static String sendPost(String proxyIp, String proxyPort, String url, String param, String contentType, boolean useProxy, String charset) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = null;
			if(useProxy) {
				/*构造Proxy对象，以适用于代理上网的方式*/  
				InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(proxyIp), Integer.parseInt(proxyPort));
				Proxy proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
				conn = realUrl.openConnection(proxy);
			} else {
				conn = realUrl.openConnection();
			}
			
			// 设置通用的请求属性
			conn.setRequestProperty("Content-Type", contentType);
			//conn.setRequestProperty("Content-Length", contentLength);
			//conn.setRequestProperty("accept", "*/*");
			//conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Jakarta Commons-HttpClient/3.1");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			// 获取URLConnection对象对应的输出流
			if (StringUtils.isEmpty(charset)) {
				out = new PrintWriter(conn.getOutputStream());
			} else {
				out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), charset));
			}
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			
			
			InputStream output = conn.getInputStream();
			/*ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int c;
			while ((c = output.read()) >= 0) {
				buffer.write(c);
			}
			buffer.close();
			byte[] bytes = buffer.toByteArray();
			result = new String(bytes);
			System.out.println("工商返回报文：" + result);*/
			
			// 定义BufferedReader输入流来读取URL的响应
			//in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
			if (StringUtils.isEmpty(charset)) {
				in = new BufferedReader(new InputStreamReader(output));
			} else {
				in = new BufferedReader(new InputStreamReader(output, charset));
			}
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TtyException("发送POST请求出现异常。", e);
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				throw new TtyException("关闭流失败。");
			}
		}
		return result;
	}

	// 设置代理
	static class MyAuthenticator extends Authenticator {
		private String user = "";
		private String password = "";

		public MyAuthenticator(String user, String password) {
			this.user = user;
			this.password = password;
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user, password.toCharArray());
		}
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
	
	/*
	 * 
	 * 	@SuppressWarnings("deprecation")
		public static String sendPost(String url, String param,String localFilePath, String contentType, String charset) {
			@SuppressWarnings("resource")
			HttpClient client = new DefaultHttpClient(); 
			try {  
	            HttpPost httpPost = new HttpPost(url);
	            client.getParams().setParameter(HttpMethodParams.USER_AGENT,"Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");
	            StringEntity postEntity = new StringEntity(param, charset);
			    httpPost.addHeader("Content-Type", contentType);
			    httpPost.setEntity(postEntity);
			    //httpPost.getParams().setParameter("ticketId", "C8B2037C957AFCCF83A105EEA315D186CDE5E303EDEDEAE4F1E1C4C5");
			    //httpPost.getParams().setParameter("uri", "boc%253A%252F%252FIPPS%252FMerchantDownloadFile%253FmerchantNo%253D33201%2526fileType%253DMCC%2526fileDate%253D20160321");
	            HttpResponse response = client.execute(httpPost);  
	  
	            HttpEntity entity = response.getEntity();  
	            InputStream is = entity.getContent();
	            localFilePath = localFilePath+getFileName(response);
	            File file = new File(localFilePath);  
	            if (!file.getParentFile().exists()) {
	            	file.getParentFile().mkdirs();  
	            }
	            FileOutputStream fileout = new FileOutputStream(file);  
	            *//** 
	             * 根据实际运行效果 设置缓冲区大小 
	             *//*  
	            byte[] buffer=new byte[10 * 1024];  
	            int ch = 0;  
	            while ((ch = is.read(buffer)) != -1) {  
	                fileout.write(buffer,0,ch);  
	            }  
	            is.close();  
	            fileout.flush();  
	            fileout.close();  
	        } catch (Exception e) {  
	            log.error(e.getMessage(), e);
	        } finally {
	        	client.getConnectionManager().shutdown();
			}
	        return localFilePath;
		}
		public static String getFileName(HttpResponse response) {  
	        Header contentHeader = response.getFirstHeader("Content-Disposition");  
	        String filename = "";  
	        if (contentHeader != null) {  
	            HeaderElement[] values = contentHeader.getElements();  
	            if (values.length == 1) {  
	                NameValuePair param = values[0].getParameterByName("filename");  
	                if (param != null) {  
	                    try {  
	                        filename = param.getValue();  
	                    } catch (Exception e) {  
	                        log.error(e.getMessage(), e);
	                    }  
	                }  
	            }  
	        }  
	        return filename;  
	    }
	 * 
	 * 
	 * public static void main(String[] args) throws Exception { String
	 * packageID = DateUtil.getCDateString(DateUtil.YYYYMMDDHHMMSS) + "000025";
	 * String tradeDate = DateUtil.getCDateString(DateUtil.YYYYMMDD); String
	 * tradeTime = DateUtil.getCDateString(DateUtil.DATA_FORMAT_PATTERN_3);
	 * String transCode = "QBILL";
	 * 
	 * // 签名请求处理 // String xml =
	 * "<?xml version=\"1.0\" encoding=\"GBK\"?><CMS><eb><pub><TransCode>"
	 * +transCode+
	 * "</TransCode><CIS>400090001604411</CIS><BankCode>102</BankCode><ID>ncrp.y.4000</ID>"
	 * // + "<TranDate>"+tradeDate+"</TranDate><TranTime>"+tradeTime+
	 * "</TranTime><fSeqno>"+packageID+"</fSeqno></pub>" // +
	 * "<in><AccNo>4000023029200124946</AccNo><BeginDate>20150918</BeginDate><EndDate>20150918</EndDate>"
	 * // +
	 * "<BeginTime></BeginTime><EndTime></EndTime><MinAmt>0</MinAmt><MaxAmt>99999999</MaxAmt><NextTag></NextTag><ReqReserved1></ReqReserved1><ReqReserved2></ReqReserved2></in></eb></CMS>"
	 * ;
	 * 
	 * // String xml =
	 * "<?xml version=\"1.0\" encoding=\"GBK\"?><CMS><eb><pub><TransCode>QBILL</TransCode><CIS>400090001604411</CIS><BankCode>102</BankCode><ID>ncrp.y.4000</ID>"
	 * // +
	 * "<TranDate>20151008</TranDate><TranTime>111435</TranTime><fSeqno>20151008000000007763</fSeqno></pub><in><AccNo>4000023029200124946</AccNo><BeginDate>20150707</BeginDate><EndDate>20150707</EndDate>"
	 * // +
	 * "<BeginTime></BeginTime><EndTime></EndTime><MinAmt>0</MinAmt><MaxAmt>99999999</MaxAmt><NextTag></NextTag><ReqReserved1></ReqReserved1><ReqReserved2></ReqReserved2></in></eb></CMS>"
	 * ; // // String xml =
	 * "<?xml version=\"1.0\" encoding=\"GBK\"?><CMS><eb><pub><TransCode>B2CPAYINF</TransCode><CIS>400090001604411</CIS><BankCode>102</BankCode><ID>ncrp.y.4000</ID><TranDate>20150925</TranDate><TranTime>"
	 * +sendTime+"</TranTime><fSeqno>"+packageID+"</fSeqno></pub>" // +
	 * "<in><ShopType>1</ShopType><ShopCode>4000EC20000436</ShopCode><ShopAcct>4000023029200124946</ShopAcct>"
	 * // + "<QryOrderNum>20150906213512 </QryOrderNum></in></eb></CMS>";
	 * 
	 * String xml =
	 * "<?xml version=\"1.0\" encoding=\"GBK\"?><CMS><eb><pub><TransCode>"
	 * +transCode+
	 * "</TransCode><CIS>400090001604411</CIS><BankCode>102</BankCode><ID>ncrp.y.4000</ID>"
	 * + "<TranDate>"+tradeDate+"</TranDate><TranTime>"+tradeTime+
	 * "</TranTime><fSeqno>"+packageID+"</fSeqno></pub>" +
	 * "<in><TotalNum>1</TotalNum><ReqReserved1><ReqReserved2><rd><iSeqno>1</iSeqno><AccNo>4000023029200124946</AccNo>"
	 * +
	 * "<CurrType>001</CurrType><ReqReserved3></ReqReserved3><ReqReserved4></ReqReserved4></rd></in></eb></CMS>"
	 * ;
	 * 
	 * String xml =
	 * "<?xml version=\"1.0\" encoding=\"GBK\"?><CMS><eb><pub><TransCode>"
	 * +transCode+
	 * "</TransCode><CIS>400090001604411</CIS><BankCode>102</BankCode><ID>ncrp.y.4000</ID>"
	 * + ""; System.out.println("请求报文：" + xml);
	 * 
	 * // 公钥 InputStream is = new
	 * FileInputStream("D:/kame-bank/cert/icbc/ncrp.pem"); byte[] by = new
	 * byte[is.available()]; is.read(by); is.close();
	 * 
	 * // 【证书公钥】 // 证书公钥,base64编码 byte[] encCert = ReturnValue.base64enc(by);
	 * String certBase64 = new String(encCert, "GBK");
	 * 
	 * // 业务请求处理 //reqData = reqData.substring(reqData.indexOf("<sign>") + 6,
	 * reqData.indexOf("</sign>")); //url =
	 * "http://172.16.133.215:448/servlet/ICBCCMPAPIReqServlet?userID=ncrp.y.4000&PackageID="
	 * +packageID+"&SendTime="+sendTime; String url =
	 * "http://172.16.133.215:448/servlet/ICBCCMPAPIReqServlet"; String param =
	 * "Version=0.0.0.1&TransCode="
	 * +transCode+"&BankCode=102&GroupCIS=400090001604411&ID=ncrp.y.4000&PackageID="
	 * +packageID+"&Cert=&reqData=" + URLEncoder.encode(xml); //String param =
	 * "Version=0.0.0.1&TransCode="+transCode+
	 * "&BankCode=102&GroupCIS=400090001604411&ID=ncrp.y.4000&PackageID=20151008000000007763&Cert=&reqData="
	 * +reqData; String result = sendPost(url, param,
	 * "application/x-www-form-urlencoded"); //String result =
	 * "PD94bWwgIHZlcnNpb249IjEuMCIgZW5jb2Rpbmc9IkdCSyIgPz4KPENNUz4KPGViPgo8cHViPgo8VHJhbnNDb2RlPlFCSUxMPC9UcmFuc0NvZGU+CjxDSVM+NDAwMDkwMDAzNzc5NjU0PC9DSVM+CjxCYW5rQ29kZT4xMDI8L0JhbmtDb2RlPgo8SUQ+enNodDAyLnkuNDAwMDwvSUQ+CjxUcmFuRGF0ZT4yMDE1MTAwODwvVHJhbkRhdGU+CjxUcmFuVGltZT4xMTE0MzU8L1RyYW5UaW1lPgo8ZlNlcW5vPjIwMTUxMDA4MDAwMDAwMDA3NzYzPC9mU2Vxbm8+CjxSZXRDb2RlPkIwMTE2PC9SZXRDb2RlPgo8UmV0TXNnPsO709C3+7rPzPW8/rXEvMfCvDwvUmV0TXNnPgo8L3B1Yj4KPG91dD4KPEFjY05vPjQwMDAxMDQyMDkxMDAwMTA1MzM8L0FjY05vPgo8QWNjTmFtZT6xsb6p1cbJz7vjzai/xry809DP3rmry77J7tvat9a5q8u+PC9BY2NOYW1lPgo8Q3VyclR5cGU+MDAxPC9DdXJyVHlwZT4KPFN3aWZ0QmFuaz48L1N3aWZ0QmFuaz4KPFN0YXJ0QmFsYW5jZT4wPC9TdGFydEJhbGFuY2U+CjxFbmRCYWxhbmNlPjA8L0VuZEJhbGFuY2U+CjxUb3RhbE51bT4wPC9Ub3RhbE51bT4KPE5leHRUYWc+PC9OZXh0VGFnPgo8UmVwUmVzZXJ2ZWQxPjwvUmVwUmVzZXJ2ZWQxPgo8UmVwUmVzZXJ2ZWQyPjwvUmVwUmVzZXJ2ZWQyPgo8cmQ+CjxCaWxsRGF0ZT48L0JpbGxEYXRlPgo8QWNjTm8+PC9BY2NObz4KPEFjY05hbWU+PC9BY2NOYW1lPgo8Q3VyclR5cGU+PC9DdXJyVHlwZT4KPERldGFpbE5vPjwvRGV0YWlsTm8+CjxTdWJDb2RlPjwvU3ViQ29kZT4KPEJ1c2lEYXRlPjwvQnVzaURhdGU+CjxCdXNpVGltZT48L0J1c2lUaW1lPgo8UmV2VHJhbmY+PC9SZXZUcmFuZj4KPFVwZFRyYW5mPjwvVXBkVHJhbmY+CjxEcmNyZj48L0RyY3JmPgo8Vm91aFR5cGU+PC9Wb3VoVHlwZT4KPFZvdWhObz48L1ZvdWhObz4KPEN2b3VoVHlwZT48L0N2b3VoVHlwZT4KPEN2b3VoTm8+PC9Ddm91aE5vPgo8QW1vdW50PjwvQW1vdW50Pgo8QmFsYW5jZT48L0JhbGFuY2U+CjxWYWx1ZURheT48L1ZhbHVlRGF5Pgo8UmVjaXBCa05vPjwvUmVjaXBCa05vPgo8UmVjaXBCa05hbWU+PC9SZWNpcEJrTmFtZT4KPFJlY2lwQWNjTm8+PC9SZWNpcEFjY05vPgo8UmVjaXBOYW1lPjwvUmVjaXBOYW1lPgo8T3JlZ0FjY05vPjwvT3JlZ0FjY05vPgo8T3JlZ05hbWU+PC9PcmVnTmFtZT4KPE9yZWdCa05vPjwvT3JlZ0JrTm8+CjxTdGF0Q29kZT48L1N0YXRDb2RlPgo8U2V0dGxlTW9kZT48L1NldHRsZU1vZGU+CjxTdW1tYXJ5PjwvU3VtbWFyeT4KPFVzZUNOPjwvVXNlQ04+CjxQb3N0U2NyaXB0PjwvUG9zdFNjcmlwdD4KPFJlZj48L1JlZj4KPE9yZWY+PC9PcmVmPgo8U3FuPjwvU3FuPgo8QnVzQ29kZT48L0J1c0NvZGU+CjxFblN1bW1hcnk+PC9FblN1bW1hcnk+CjxBZGRJbmZvPjwvQWRkSW5mbz4KPEVSUGNoa25vPjwvRVJQY2hrbm8+CjxFUlBlZnRSZWY+PC9FUlBlZnRSZWY+CjxTaWduRGF0ZT48L1NpZ25EYXRlPgo8UmVwUmVzZXJ2ZWQzPjwvUmVwUmVzZXJ2ZWQzPgo8UmVwUmVzZXJ2ZWQ0PjwvUmVwUmVzZXJ2ZWQ0Pgo8L3JkPgo8L291dD4KPC9lYj4KPC9DTVM+Cg=="
	 * ; System.out.println("工行返回数据如下:" + new
	 * String(Base64.decode(result),"GBK")); }
	 */

	// 代发
	/*
	 * public static void main(String[] args) throws Exception { String
	 * packageID = DateUtil.getCDateString(DateUtil.YYYYMMDDHHMMSS) + "000025";
	 * String singTime = DateUtil.getCDateString(DateUtil.YYYYMMDDHHMMSS);
	 * String tradeDate = DateUtil.getCDateString(DateUtil.YYYYMMDD); String
	 * tradeTime = DateUtil.getCDateString(DateUtil.DATA_FORMAT_PATTERN_3);
	 * String transCode = "PAYPER";
	 * 
	 * String data = new StringBuffer() .append("<in>")
	 * .append("<OnlBatF>").append("").append("</OnlBatF>") // 联机批量标志
	 * .append("<SettleMode>").append("0").append("</SettleMode>") // 入账方式
	 * .append("<TotalNum>").append("1").append("</TotalNum>") // 总笔数
	 * .append("<TotalAmt>").append("12").append("</TotalAmt>") // 总金额
	 * .append("<SignTime>").append("20151025202555323").append("</SignTime>")
	 * // 签名时间 .append("<ReqReserved1>").append("").append("</ReqReserved1>") //
	 * 请求备用字段1 .append("<ReqReserved2>").append("").append("</ReqReserved2>") //
	 * 请求备用字段1 .append("<rd>")
	 * .append("<iSeqno>").append(packageID).append("</iSeqno>") // 指令顺序号
	 * .append("<ReimburseNo>").append("").append("</ReimburseNo>") //
	 * .append("<ReimburseNum>").append("").append("</ReimburseNum>") //
	 * .append("<StartDate>").append("").append("</StartDate>") //
	 * .append("<StartTime>").append("").append("</StartTime>") //
	 * .append("<PayType>").append("1").append("</PayType>") // 记账处理方式
	 * .append("<PayAccNo>").append("4000023029200124946").append("</PayAccNo>")
	 * // 本方账号 .append("<PayAccNameCN>").append("").append("</PayAccNameCN>") //
	 * 本方账户名称 .append("<PayAccNameEN>").append("").append("</PayAccNameEN>") //
	 * 本方账户英文名称
	 * .append("<RecAccNo>").append("6258590027819529").append("</RecAccNo>") //
	 * 对方账号 .append("<RecAccNameCN>").append("").append("</RecAccNameCN>") //
	 * 对方账户名称 .append("<RecAccNameEN>").append("").append("</RecAccNameEN>") //
	 * 对方账户英文名称 .append("<SysIOFlg>").append("1").append("</SysIOFlg>") //
	 * .append("<IsSameCity>").append("").append("</IsSameCity>") //
	 * .append("<RecICBCCode>").append("").append("</RecICBCCode>") //
	 * .append("<RecCityName>").append("").append("</RecCityName>") //
	 * .append("<RecBankNo>").append("").append("</RecBankNo>") //
	 * .append("<RecBankName>").append("").append("</RecBankName>") // 交易对方银行名称
	 * .append("<CurrType>").append("001").append("</CurrType>") // 币种
	 * .append("<PayAmt>").append("12").append("</PayAmt>") // 金额
	 * .append("<UseCode>").append("").append("</UseCode>") //
	 * .append("<UseCN>").append("工资").append("</UseCN>") //
	 * .append("<EnSummary>").append("").append("</EnSummary>") //
	 * .append("<PostScript>").append("").append("</PostScript>") //
	 * .append("<Summary>").append("").append("</Summary>") //
	 * .append("<Ref>").append("").append("</Ref>") //
	 * .append("<Oref>").append("").append("</Oref>") //
	 * .append("<ERPSqn>").append("").append("</ERPSqn>") //
	 * .append("<BusCode>").append("").append("</BusCode>") //
	 * .append("<ERPcheckno>").append("").append("</ERPcheckno>") //
	 * .append("<CrvouhType>").append("").append("</CrvouhType>") //
	 * .append("<CrvouhName>").append("").append("</CrvouhName>") //
	 * .append("<CrvouhNo>").append("").append("</CrvouhNo>") //
	 * .append("<ReqReserved3>").append("").append("</ReqReserved3>") //
	 * .append("<ReqReserved4>").append("").append("</ReqReserved4>") //
	 * .append("</rd>") .append("</in>").toString();
	 * 
	 * data =
	 * "<in><OnlBatF>1</OnlBatF><SettleMode>2</SettleMode><RecAccNo>4000023029200124946</RecAccNo><RecAccNameCN>孔篷建扛庇柳囤罩低审它霞脑圳茸酮柳（岛暇噪煎吉）</RecAccNameCN><RecAccNameEN></RecAccNameEN><TotalNum>1</TotalNum><TotalAmt>14</TotalAmt><SignTime>20151031110731323</SignTime><ReqReserved1></ReqReserved1><ReqReserved2></ReqReserved2><rd><iSeqno>1</iSeqno><PayAccNo>6222084000799000180</PayAccNo><PayAccNameCN>张一</PayAccNameCN><PayAccNameEN></PayAccNameEN><PayBranch>工行</PayBranch><Portno>20100813004</Portno><ContractNo>BDP300129309</ContractNo><CurrType>001</CurrType><PayAmt>14</PayAmt><UseCode></UseCode><UseCN>报刊费</UseCN><EnSummary></EnSummary><PostScript></PostScript><Summary></Summary><Ref></Ref><Oref></Oref><ERPSqn></ERPSqn><BusCode></BusCode><ERPcheckno></ERPcheckno><CrvouhType></CrvouhType><CrvouhName></CrvouhName><CrvouhNo></CrvouhNo><ReqReserved3></ReqReserved3><ReqReserved4></ReqReserved4></rd></in>"
	 * ; String xml =
	 * "<?xml version=\"1.0\" encoding=\"GBK\"?><CMS><eb><pub><TransCode>"
	 * +transCode+
	 * "</TransCode><CIS>400090001604411</CIS><BankCode>102</BankCode><ID>ncrp.y.4000</ID>"
	 * + "<TranDate>"+tradeDate+"</TranDate><TranTime>"+tradeTime+
	 * "</TranTime><fSeqno>"+packageID+"</fSeqno></pub>"+data+"</eb></CMS>";
	 * System.out.println("请求报文：" + xml); String url =
	 * "http://172.16.133.215:449"; String reqData = sendPost(url, xml,
	 * "INFOSEC_SIGN/1.0"); reqData =
	 * reqData.substring(reqData.indexOf("<sign>") + 6,
	 * reqData.indexOf("</sign>"));
	 * 
	 * url = "http://172.16.133.215:448/servlet/ICBCCMPAPIReqServlet"; String
	 * param = "Version=0.0.0.1&TransCode="+transCode+
	 * "&BankCode=102&GroupCIS=400090001604411&ID=ncrp.y.4000&PackageID="
	 * +packageID+"&Cert=&reqData="+URLEncoder.encode(reqData); String result =
	 * sendPost(url, param, "application/x-www-form-urlencoded");
	 * System.out.println("工行返回数据如下:" + new
	 * String(Base64.decode(result.split("reqData=")[1]),"GBK")); }
	 */

	// 快捷
	/*
	 * public static void main(String[] args) throws Exception { String
	 * transCode = "SZFH_SMSAPPLY"; String gis = "400090001604411"; String
	 * bankCode = "102"; String id = "ncrp.y.4000"; String tradeDate =
	 * DateUtil.getCDateString(DateUtil.YYYYMMDD); String tradeTime =
	 * DateUtil.getCDateString(DateUtil.DATA_FORMAT_PATTERN_3); String fSeqno =
	 * DateUtil.getCDateString(DateUtil.YYYYMMDDHHMMSS) + "000025";
	 * 
	 * String corpAccNo = "4000023029200124946";
	 * 
	 * 
	 * String data = new StringBuffer() .append("<in>")
	 * .append("<CorpAccNo>").append(corpAccNo).append("</CorpAccNo>") // 企业账号
	 * .append("<AccNo>").append("6258590027819529").append("</AccNo>") // 卡号/账号
	 * .append("<SupType>").append("1").append("</SupType>") // 支持卡种
	 * .append("<AccName>").append("李雪").append("</AccName>") // 户名
	 * .append("<IdType>").append("0").append("</IdType>") // 证件类型
	 * .append("<IdCode>").append("220323199106241623").append("</IdCode>") //
	 * 证件号码
	 * .append("<MobilePhone>").append("15044471699").append("</MobilePhone>")
	 * // 手机号码 .append("<CorpNo>").append("BDP800008").append("</CorpNo>") //
	 * 企业用户协议编号 .append("<PersonNo>").append("0").append("</PersonNo>") //
	 * 个人认证协议编号 .append("<DeadLine>").append("0").append("</DeadLine>") //
	 * 个人认证有效日期时间 .append("<ReqReserved1>").append("").append("</ReqReserved1>")
	 * // 请求备用字段1 .append("<ReqReserved2>").append("").append("</ReqReserved2>")
	 * // 请求备用字段2 .append("<ReqReserved3>").append("").append("</ReqReserved3>")
	 * // 请求备用字段3 .append("<ReqReserved4>").append("").append("</ReqReserved4>")
	 * // 请求备用字段4 .append("</in>").toString();
	 * 
	 * String xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>")
	 * .append("<CMS>") .append("<eb>") .append("<pub>")
	 * .append("<TransCode>").append(transCode).append("</TransCode>") // 交易代码
	 * .append("<CIS>").append(gis).append("</CIS>") // 集团CIS号
	 * .append("<BankCode>").append(bankCode).append("</BankCode>") // 归属银行编号
	 * .append("<ID>").append(id).append("</ID>") // 证书ID
	 * .append("<TranDate>").append(tradeDate).append("</TranDate>") // 交易日期
	 * .append("<TranTime>").append(tradeTime).append("</TranTime>") // 交易时间
	 * .append("<fSeqno>").append(fSeqno).append("</fSeqno>") // 指令包序列号
	 * .append("</pub>") .append(data) .append("</eb>")
	 * .append("</CMS>").toString();
	 * 
	 * System.out.println("请求报文：" + xml); String url =
	 * "http://172.16.133.215:449"; String reqData = sendPost(url, xml,
	 * "INFOSEC_SIGN/1.0"); reqData =
	 * reqData.substring(reqData.indexOf("<sign>") + 6,
	 * reqData.indexOf("</sign>"));
	 * 
	 * url = "http://172.16.133.215:448/servlet/ICBCCMPAPIReqServlet"; String
	 * param =
	 * "Version=0.0.0.1&TransCode="+transCode+"&BankCode="+bankCode+"&GroupCIS="
	 * +gis+"&ID="+id+"&PackageID="+fSeqno+"&Cert=&reqData="+URLEncoder.encode(
	 * reqData); String result = sendPost(url, param,
	 * "application/x-www-form-urlencoded"); System.out.println("工行返回数据如下:" +
	 * new String(Base64.decode(result), "GBK")); }
	 */

	// 建行快捷
	/*
	 * public static void main(String[] args) throws Exception { String body =
	 * new StringBuffer() .append("<Body>") .append("<request>")
	 * .append("<tx_flag>").append("0").append("</tx_flag>") //
	 * 交易标志，0-签约，1-注销，2-支付，3-信用卡分期支付（暂不支持3）
	 * .append("<shop_no>").append("105584073990235").append("</shop_no>") //
	 * 商户代码 .append("<cunt_no>").append("333093302").append("</cunt_no>") //
	 * 柜台编号
	 * .append("<cert_id>").append("411481199011288782").append("</cert_id>") //
	 * 证件号 .append("<cert_typ>").append("A").append("</cert_typ>") // 证件类型
	 * .append("<cust_nm>").append("王二零").append("</cust_nm>") // 客户姓名
	 * .append("<acct_no>").append("6227001217150005405").append("</acct_no>")
	 * // 客户账号 .append("<mobile>").append("11111288782").append("</mobile>") //
	 * 手机号 .append("{1}") // 短信验证码 .append("</request>")
	 * .append("</Body>").toString();
	 * 
	 * String data = new
	 * StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
	 * .append("<Tran>") .append("<Header>")
	 * .append("<txcode>").append("AL0001").append("</txcode>")// 交易码
	 * .append("<txseq>"
	 * ).append(System.currentTimeMillis()).append("</txseq>")// 请求系统序列号，唯一
	 * .append
	 * ("<txdate>").append(DateUtil.getCDateString(DateUtil.YYYYMMDD)).append
	 * ("</txdate>")// 交易日期
	 * .append("<txtime>").append(DateUtil.getCDateString(DateUtil
	 * .DATA_FORMAT_PATTERN_3)).append("</txtime>")// 交易时间 .append("{0}")// 签名
	 * //
	 * .append("<txsign>").append(this.signMap.get("trncod")).append("</txsign>"
	 * )// 签名
	 * .append("<tminf>").append("1055840739902350001").append("</tminf>")//
	 * 终端信息 .append("</Header>") .append(body) .append("</Tran>").toString();
	 * 
	 * String content = MessageFormat.format(data, "",
	 * "<acct_flag>0</acct_flag>"); System.out.println(content); String
	 * privateKey =
	 * "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAIaj11rqGW0A3UTllYmT5NptZdwQRZYho3Y8cOK17PVAAxyJrk06qWy3jcUCO5HfJkeyBMh57I4JicOJZYMjXBWxtHAaj14ULPtVHaVJQT6zwGd+7U1Ypf4At+mbYb/6nY9eol4/Hqp5j+MQ15bMA+o0lv5dAvg7MJakfChT25kxAgMBAAECgYEAgYSy8d4ov1cJg/FmvIXsrMJ4Lg3pItcRK/hQj4Z6gCIn3BgF+Hzp5o9cbZwMOfL0HBv/xeqjyK8cO2G8qT4mob/WlhoN1hGr1BBvb8b5wm8OKmgEq+gvIo/oGH1ydIKkUZ0JGad0sUEI/MVEKp5C6rUGd7madA4nqMXWT2kXWtECQQDSs/abThZoRT+4xMzLY0W6KrZkqmP5+HlvenKHd8uMEDn+qApvZq/6V1w0qz0aSfELnQEejCKGhTWoIw+8nv37AkEAo5XAr5fimHzRHWm4jIL97PY3PdYikqh4sqgYq11JJh9ze7WPumpBMa+Hd9a4P7q/vhyM7QwdL+MclPvTanX5wwJBAIDoByTG5mCOlUiWZnC8YGlgPkxiDl5dTPYN5MnKWaZsOv74kcHMAjfXgJrcdLqDqEwhlxC4TVsdXlFqefM9FkcCQCdA0gHQonEQ0mUKb1xYOnJYCp1t+tHSV1QXfgPw2JKrTVzMDnce9BHmGs2o2oGAYG5QD4j6NS6+gqdFXk/Xi10CQQCGzhdxpNyWSGHZka+UFmQzbWZ2b91JENJJEgDJKR+jhZOXeqbkadGoIcRkFBPEmSoTNZpU0/bATuCJnYauOq+c"
	 * ;
	 * 
	 * String signData = MD5WithRSA.sign(content, privateKey,
	 * Constants.CHARSET_UTF8); System.out.println(signData);
	 * 
	 * String xml = MessageFormat.format(data, "<txsign>"+signData+"</txsign>",
	 * "<acct_flag>0</acct_flag>"); System.out.println(xml);
	 * 
	 * String desData = DES.encrypt(xml, "IFJu4IovXWJSgIMWBwh2wSBSbuCKL11i");
	 * System.out.println(desData);
	 * 
	 * String resData = sendPost(
	 * "http://128.192.182.4:8101/FastPayment_adapter/FastPayChannelServlet/FastPay"
	 * , "xml=" + URLEncoder.encode(desData) + "&shop_no=105584073990235",
	 * "application/x-www-form-urlencoded"); System.out.println("回调信息：" +
	 * resData);
	 * 
	 * byte[] resDesData = DES.decrypt(resData,
	 * "IFJu4IovXWJSgIMWBwh2wSBSbuCKL11i"); String resXmlData = new
	 * String(resDesData, "UTF-8"); System.out.println(resXmlData); }
	 */
}