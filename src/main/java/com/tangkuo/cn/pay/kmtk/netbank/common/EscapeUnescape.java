
package com.tangkuo.cn.pay.kmtk.netbank.common;

import org.apache.commons.lang3.StringUtils;

/**
 * ClassName: EscapeUnescape <br/>
 * Function: 像JS的escape方法和unescape方法一样. <br/>
 * Reason: 对中文等特别字符加密处理. <br/>
 * date: 2015年11月30日 下午3:03:19 <br/>
 *
 */
public class EscapeUnescape {

	/**
	 * escape : 用url混淆的方式加密字符串
	 * @param src 
	 * @return String
	 * */
	public static String escape(String src) {
		if(StringUtils.isEmpty(src)) return StringUtils.EMPTY;
		int i;
		char j;
		StringBuilder tmp = new StringBuilder();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	/**
	 * unescape : 用url混淆的方式解密字符串
	 * @param src 
	 * @return String
	 * */
	public static String unescape(String src) {
		if(StringUtils.isEmpty(src)) return StringUtils.EMPTY;
		StringBuilder tmp = new StringBuilder();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(
							src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(
							src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	
}

