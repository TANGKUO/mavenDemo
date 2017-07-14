package com.tangkuo.cn.pay.zftk.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @ClassName: RequestUtil
 * @Description: (从请求对象中获取参数值)
 *
 */
public class RequestUtil {
	/**
	 * 从request对象中获取所有对象，并获取跳转前地址和客户IP
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public static Map<String, Object> getRequestParams(HttpServletRequest request) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		// 循环获取所有参数
		Enumeration attNames = request.getParameterNames();
		while (attNames.hasMoreElements()) {
			String attName = (String) attNames.nextElement();
			paramsMap.put(attName, request.getParameter(attName));
		}
		return paramsMap;
	}
}
