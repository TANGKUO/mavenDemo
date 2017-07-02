package com.tangkuo.cn.pay.kmtk.netbank.adapter.util;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BestPayUtil {

	private BestPayUtil(){}

	private static Logger log = LoggerFactory.getLogger(BestPayUtil.class);
	
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	
	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b 字节数组
	 * @return 16进制字串
	 */

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString().toUpperCase();
	}

	/**
	 * MD5 摘要计算(byte[]).
	 * 
	 * @param src
	 *            byte[]
	 * @throws Exception
	 * @return byte[] 16 bit digest
	 */
	public static byte[] md5Digest(byte[] src) throws Exception {
		MessageDigest alg = MessageDigest.getInstance("MD5");
		return alg.digest(src);
	}

	/**
	 * MD5 摘要计算(String).
	 * 
	 * @param src
	 *            String
	 * @throws Exception
	 * @return String
	 */
	public static String md5Digest(String src) throws Exception {
		return byteArrayToHexString(md5Digest(src.getBytes("UTF-8")));
	}
	
	/**
	 * MD5 摘要计算(String).
	 * 
	 * @param src String
	 * @return String
	 */
	public static String getMd5Value(String src) {
		if(StringUtils.isEmpty(src)){
			return StringUtils.EMPTY;
		}
		try {
			return md5Digest(src);
		} catch (Exception e) {
			log.error("MD5 the value ["+src+"] is fail: "+e.getMessage(),e);
			return StringUtils.EMPTY;
		}
	}

	/**
	 *  to String the Map ,and the format is : param=value&param1=value1&param2=value2.
	 * 
	 * @param m Map<String,String>
	 * @throws 
	 * @return String
	 */
	public static String mapToString(Map<String, String> m) {
		if (m == null || m.size() == 0) {
			return StringUtils.EMPTY;
		}
		StringBuilder strs = new StringBuilder();
		Iterator<Map.Entry<String, String>> it = m.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			strs.append(entry.getKey() + "=" + entry.getValue() + "&");
		}
		if (strs.length() > 0) {
			strs.delete(strs.length() - 1, strs.length());
		}
		return strs.toString();
	}
	
	/* 联合字符串
	 * key不能为空 ,value可以为空
	 * */
	public static String jointKeyValue(String key,String value) {
		if (StringUtils.isEmpty(key)){
			return StringUtils.EMPTY;
		}
		return StringUtils.isNotEmpty(value) ? key+value : key;
	}

}
