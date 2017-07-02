package com.tangkuo.cn.pay.kmtk.netbank.adapter.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangkuo.cn.pay.kmtk.netbank.XmlFormatter;
import com.tangkuo.cn.pay.kmtk.netbank.common.Constants;

public class D9BillUtil {
	
	final static Logger log = LoggerFactory.getLogger(D9BillUtil.class);

	/**
	* 向指定URL发送POST方法的请求
	* @param url 发送请求的URL
	* @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
    * @return URL所代表远程资源的响应
	 * @throws Exception 
    */
	public static String sendPost(String url,String requestXml,String merchantId,String certPath,String certPassword)
	{
		if (StringUtils.isEmpty(url) || StringUtils.isEmpty(requestXml)
				|| StringUtils.isEmpty(certPath)
				|| StringUtils.isEmpty(certPassword)
				|| StringUtils.isEmpty(merchantId)) {
			return org.apache.commons.lang3.StringUtils.EMPTY;
		}
		System.setProperty("jsse.enableSNIExtension", "false");
		OutputStream out = null;
		
		String respXml = "";
		try {
			// 证书路径
			//String certPath = D9BillUtil.class.getResource("../81205154511001190.jks").toURI().getPath();
			// 获取证书路径
			File certFile = new File(certPath);
			// 访问Java密钥库，JKS是keytool创建的Java密钥库，保存密钥。
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(new FileInputStream(certFile), certPassword.toCharArray());//"123456"
			// 创建用于管理JKS密钥库的密钥管理器
			KeyManagerFactory kmf = KeyManagerFactory
					.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			// 初始化证书
			kmf.init(ks, certPassword.toCharArray());
			// 同位体验证信任决策源//同位体验证可信任的证书来源
			TrustManager[] tm = { new MyX509TrustManager() };
			// 初始化安全套接字
			SSLContext sslContext = SSLContext.getInstance("SSL");
			// 初始化SSL环境。第二个参数是告诉JSSE使用的可信任证书的来源，设置为null是从javax.net.ssl.trustStore中获得证书。
			// 第三个参数是JSSE生成的随机数，这个参数将影响系统的安全性，设置为null是个好选择，可以保证JSSE的安全性。
			sslContext.init(kmf.getKeyManagers(), tm, null);
			// 根据上面配置的SSL上下文来产生SSLSocketFactory,与通常的产生方法不同
			SSLSocketFactory factory = sslContext.getSocketFactory();
			//URL
		    URL realUrl = new URL(url);
		    //打开和URL之间的连接
		    HttpsURLConnection conn = (HttpsURLConnection)realUrl.openConnection();
		    //创建安全的连接套接字
			conn.setSSLSocketFactory(factory);
		    //发送POST请求必须设置如下两行,使用 URL 连接进行输出、入
		    conn.setDoOutput(true);
		    conn.setDoInput(true);
		    //设置URL连接的超时时限
		    conn.setReadTimeout(100000);
		    //设置通用的请求属性
			String authString = merchantId + ":" + certPassword;
			String auth = "Basic " + Base64Binrary.encodeBase64Binrary(authString.getBytes());
		    conn.setRequestProperty("Authorization", auth);
		    // 获取URLConnection对象对应的输出流
		    out = conn.getOutputStream();
		    //发送请求参数
		    out.write(requestXml.getBytes());
		    //flush 输出流的缓冲
		    out.flush();
		    //得到服务端返回
		    InputStream is = conn.getInputStream();
		    String reqData = "";
			if (is != null) {
		    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
				byte[] receiveBuffer = new byte[2048];//缓冲区长度
				int readBytesSize = is.read(receiveBuffer);//读取数据长度，InputStream要读取的数据长度一定要小于等于缓冲区中的字节数
				//log.info("readBytesSize："+readBytesSize);
				
				while(readBytesSize != -1){//判断流是否位于文件末尾而没有可用的字节
					bos.write(receiveBuffer, 0, readBytesSize);//从receiveBuffer内存处的0偏移开始写，写与readBytesSize长度相等的字节
					readBytesSize = is.read(receiveBuffer);
				}
				reqData = new String(bos.toByteArray(), Constants.CHARSET_UTF8);//编码后的tr2报文
		    }
		    respXml = XmlFormatter.format(reqData);
		    //log.info(respXml);
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    	log.error("sent POST request throw exception : " + e);
	    } finally {//使用finally块来关闭输出流、输入流
	    	IOUtils.closeQuietly(out);
	    }
		if((!StringUtils.isEmpty(respXml)) && respXml.indexOf("<MasMessage xmlns=\"http://www.99bill.com/mas_cnp_merchant_interface\">") != -1){
			respXml = respXml.replaceAll("<MasMessage xmlns=\"http://www.99bill.com/mas_cnp_merchant_interface\">", "<MasMessage>");
		}
	    return respXml;
	}
	
	public static class Base64Binrary {
		
		private static final byte decodeMap[] = initDecodeMap();

		private static final char encodeMap[] = initEncodeMap();

		private Base64Binrary() {
		}

		private static char[] initEncodeMap()
		{
			char map[] = new char[64];
			for(int i = 0; i < 26; i++) map[i] = (char)(65 + i);

			for(int i = 26; i < 52; i++) map[i] = (char)(97 + (i - 26));

			for(int i = 52; i < 62; i++) map[i] = (char)(48 + (i - 52));

			map[62] = '+';
			map[63] = '/';
			return map;
		}

		private static byte[] initDecodeMap() {
			byte map[] = new byte[256];
			for(int i = 0; i < 256; i++) map[i] = -1;

			for(int i = 65; i <= 90; i++) map[i] = (byte)(i - 65);

			for(int i = 97; i <= 122; i++) map[i] = (byte)((i - 97) + 26);

			for(int i = 48; i <= 57; i++) map[i] = (byte)((i - 48) + 52);

			map[43] = 62;
			map[47] = 63;
			map[61] = 127;
			return map;
		}

		private static int calcLength(char buf[]) {
			int len = buf.length;
			int base64count = 0;
			int paddingCount = 0;
			int i;
			for( i = 0; i < len; i++ ) {
				if(buf[i] == '=') break;
				if(buf[i] >= '\u0100') return -1;
				if(decodeMap[buf[i]] != -1) base64count++;
			}

			for( ; i < len; i++ ) {
				if(buf[i] == '=') {
					paddingCount++;
				} else {
					if(buf[i] >= '\u0100') return -1;
					if(decodeMap[buf[i]] != -1) return -1;
				}
			}

			if(paddingCount > 2) return -1;
			if((base64count + paddingCount) % 4 != 0) return -1;
			else {
				return ((base64count + paddingCount) / 4) * 3 - paddingCount;
			}
		}

		public static byte[] decodeBase64Binrary(String lexicalValue) {
			char buf[] = lexicalValue.toCharArray();
			int outlen = calcLength(buf);
			if(outlen == -1) return null;
			byte out[] = new byte[outlen];
			int o = 0;
			int len = buf.length;
			byte quadruplet[] = new byte[4];
			int q = 0;
			for(int i = 0; i < len; i++) {
				byte v = decodeMap[buf[i]];
				if(v != -1) quadruplet[q++] = v;
				if(q == 4) {
					out[o++] = (byte)(quadruplet[0] << 2 | quadruplet[1] >> 4);

					if(quadruplet[2] != 127) {
						out[o++] = (byte)(quadruplet[1] << 4 | quadruplet[2] >> 2);
					}

					if(quadruplet[3] != 127) {
						out[o++] = (byte)(quadruplet[2] << 6 | quadruplet[3]);
					}

					q = 0;
				}
			}

			if(q != 0) throw new IllegalStateException();
			else return out;
		}

		protected static char encode(int i) {
			return encodeMap[i & 0x3f];
		}

		public static String encodeBase64Binrary(byte input[]) {
			StringBuffer r = new StringBuffer((input.length * 4) / 3);

			for( int i = 0; i < input.length; i += 3 ) {
				switch( (input.length - i) ) {
				case 1: // '\001'
					r.append(encode(input[i] >> 2));
					r.append(encode((input[i] & 0x3) << 4));
					r.append("==");
					break;

				case 2: // '\002'
					r.append(encode(input[i] >> 2));
					r.append(encode((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xf));
					r.append(encode((input[i + 1] & 0xf) << 2));
					r.append("=");
					break;

				default:
					r.append(encode(input[i] >> 2));
					r.append(encode((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xf));
					r.append(encode((input[i + 1] & 0xf) << 2 | input[i + 2] >> 6 & 0x3));
					r.append(encode(input[i + 2] & 0x3f));
				}
			}

			return r.toString();
		}
	}
	
	public static class MyX509TrustManager implements X509TrustManager {

		/**
		 * 简单描述该方法的实现功能（可选）.
		 * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
		 */
		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
			
			//Auto-generated method stub
			
		}

		/**
		 * 简单描述该方法的实现功能（可选）.
		 * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
		 */
		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
			
			//Auto-generated method stub
			
		}

		/**
		 * 简单描述该方法的实现功能（可选）.
		 * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
		 */
		@Override
		public X509Certificate[] getAcceptedIssuers() { 	
	        return new java.security.cert.X509Certificate[0];	
	    }
		
	}
	
}

