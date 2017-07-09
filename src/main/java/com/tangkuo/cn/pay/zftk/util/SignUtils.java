package com.tangkuo.cn.pay.zftk.util;

import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

public class SignUtils {
	
	/**
	 * 
	* @Title: getSignString
	* @Description: (对参数进行自然排序，并构造成key1=val1&key2=val2...的字符串返回)
	* @param  params 
	*    Map<String, Object>
	* @return String    返回类型
	* @throws
	 */
	public static String getSignString(Map<String, Object> params) {
		StringBuilder signSrc = new StringBuilder();

		if (null == params || params.isEmpty()) {
			return null;
		}
		params.remove("sign");
		params.remove("sign_type");
		
		TreeSet<String> keys = new TreeSet<String>();
		keys.addAll(params.keySet());

		for (String key : keys) {
			String value = (String)params.get(key);
			if (StringUtils.isNotEmpty(value)) {
				signSrc.append(key).append("=").append(value).append("&");
			}
		}
		String signStr = signSrc.toString();
		signStr = signStr.substring(0,signStr.length()-1);
		return signStr;
	}
	
	
}
