package com.tangkuo.cn.utils;

/**
 * 加密
 */
public class EncryptUtils {
	/***
	 * 加密邮箱
	 * @param email
	 * @return
	 */
	public static String encryptEmail(String email) {
		if (null == email || 0 == email.trim().length()) {
			return null;
		}
		String resultEmail = null;
		if (email.contains("@")) {
			String[] arr = email.split("@");
			String headName = arr[0];
			String last = arr[1];
			String first = headName.substring(0, 1);
			for (int i = 1; i < headName.length(); i++) {
				if (i == headName.length() - 1) {
					first += headName.substring(headName.length() - 1, headName.length());
				} else {
					first += String.valueOf(i).replace(String.valueOf(i), "*");
				}
			}
			resultEmail = first + "@" + last;
		}
		return resultEmail;
	}
}
