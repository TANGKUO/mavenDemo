package com.tangkuo.cn.pay.kmtk.netbank;

import java.security.MessageDigest;
import java.util.UUID;
/**
 * 
* @ClassName: MD5Utils
* @Description: (MD5Utils工具类)
* @author tangkuo
* @date 2017年6月27日 下午10:47:50
*
 */
public class MD5Utils {

	private static final char[] hexCode = "0123456789abcdef".toCharArray();

	public static String toHexString(byte[] data) {
		if (data == null) {
			return null;
		}
		StringBuilder r = new StringBuilder(data.length * 2);
		for (byte b : data) {
			r.append(hexCode[(b >> 4) & 0xF]);
			r.append(hexCode[(b & 0xF)]);
		}
		return r.toString();
	}

	public static String generateValue() {
		return generateValue(UUID.randomUUID().toString());
	}

	public static String generateValue(String param) {
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(param.getBytes());
			byte[] messageDigest = algorithm.digest();
			return toHexString(messageDigest);
		} catch (Exception e) {
			throw new RuntimeException("Token cannot be generated.", e);
		}
	}

}
