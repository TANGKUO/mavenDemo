package com.tangkuo.cn.pay.zftk.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
* @ClassName: parseXMLUtil
* @Description: (扫描支付（银行，支付宝，微信）工具类)
* @author tangkuo
*
 */
public class parseXMLUtil {
	
	private static Logger logger = LoggerFactory.getLogger(parseXMLUtil.class);

	/**
	 * 
	* @Title: getValue
	* @Description: (解析返回结果)
	* @param  result
	* 				 String
	* @param  element 
	* 				String
	* @return String    返回类型
	* @throws
	 */
	public static String getValue(String result, String element) {
		if (StringUtils.isNotEmpty(result) && StringUtils.isNotEmpty(element) && result.contains(element)) {
			try {
				return result.substring(result.indexOf("<" + element + ">") + element.length() + 2, 
						result.indexOf("</" + element + ">"));
			} catch (Exception e) {
				logger.warn("解析数据时未找到相关元素：" + element);
			}
		}
		return StringUtils.EMPTY;
	}
	
}
