package com.tangkuo.cn.utils.string;

public class StringUtil {
	public static final char[] NUMBER = new char[] { '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '0' };
	public static final char[] ALPHEBIC = new char[] { 'A', 'B', 'C', 'D', 'E',
			'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	public static final char[] ALPHEBIC_AND_NUMBER_UPPER = new char[] { 'A',
			'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
			'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1',
			'2', '3', '4', '5', '6', '7', '8', '9', '0' };
	public static final char[] ALPHEBIC_AND_NUMBER_LOWER = new char[] { 'a',
			'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1',
			'2', '3', '4', '5', '6', '7', '8', '9', '0' };

	public static final char[] ALPHEBIC_LOWER = new char[] { 'a', 'b', 'c',
			'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	public static String getRandomUpperStrNum(int strLength) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strLength; i++) {
			int j = (int) (Math.random() * ALPHEBIC_AND_NUMBER_UPPER.length);
			sb.append(j);
		}
		return sb.toString();
	}

	public static String getAlphaOrder(int order) {
		StringBuffer sb = new StringBuffer();
		int size = ALPHEBIC.length;
		int i = order - 1;
		if (i < 0)
			throw new IllegalArgumentException();
		while (i >= size) {
			int j = i % size;
			i = i / size - 1;
			sb.insert(0, ALPHEBIC[j]);
		}
		sb.insert(0, ALPHEBIC[i]);
		return sb.toString();
	}

	public static String getRandomLowerStr(int strLength) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strLength; i++) {
			int j = (int) (Math.random() * ALPHEBIC_AND_NUMBER_LOWER.length);
			sb.append(ALPHEBIC_AND_NUMBER_LOWER[j]);
		}
		return sb.toString();
	}

	public static String getRandomLower(int strLength) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strLength; i++) {
			int j = (int) (Math.random() * ALPHEBIC_LOWER.length);
			sb.append(ALPHEBIC_LOWER[j]);
		}
		return sb.toString();
	}

	public static String getRandomUpperStr(int strLength) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strLength; i++) {
			int j = (int) (Math.random() * ALPHEBIC_AND_NUMBER_UPPER.length);
			sb.append(ALPHEBIC_AND_NUMBER_UPPER[j]);
		}
		return sb.toString();
	}

	public static String getRandomStrNum(int strLength) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strLength; i++) {
			int j = (int) (Math.random() * NUMBER.length);
			sb.append(NUMBER[j]);
		}
		return sb.toString();
	}

	public static String getRandomString(int strLength) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strLength; i++) {
			int j = (int) (Math.random() * ALPHEBIC.length);
			sb.append(ALPHEBIC[j]);
		}
		return sb.toString();
	}
	
	/**
	 * 
	* @Title: getISO2Utf8Str
	* @Description: (转化ISO-8859-1字符串为utf-8)
	* @param  str String
	* @return String    返回类型
	* @throws
	 */
	public static String getISO2Utf8Str(String str) {
		if (str == null) {
			return "";
		}
		String temp = null;
		try {
			byte[] buf = str.getBytes(StringEncrypt.ISO8859_1);
			temp = new String(buf, StringEncrypt.UTF_8);
		} catch (Exception e) {
			temp = str;
		}
		return temp;
	}
}
