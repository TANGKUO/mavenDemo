package com.tangkuo.cn.pay.kmtk.netbank.common.util;

public class PropertyUtil {
	/**
	 * 根据属性文件内容返回填充占位符后的完整字符，不存的值返回空字符串
	 * 
	 * @param key
	 * @param arguments
	 * @return
	 */
	public static String completeMsg(String key, Object... arguments) {
		String value = Property.getProperty(key);
		return value == null ? "" : String.format(value, arguments);
	}
}
