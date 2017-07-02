package com.tangkuo.cn.pay.kmtk.netbank.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * 
* @ClassName: RequestMapUtil
* @Description: (获取请求map对象转换成普通map对象)
* @author tangkuo
* @date 2017年7月2日 下午1:51:18
*
 */
public class RequestMapUtil {
	
	public static Map<String, String> getParameterMap(HttpServletRequest request) {
		// 参数Map
		Map<String, String[]> properties = request.getParameterMap();
		// 返回值Map
		Map<String, String> returnMap = new HashMap<String, String>();
		Set<String> set = properties.keySet();
		for (String name : set) {
			String[] values = properties.get(name);
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				if (StringUtils.isNotEmpty(value))
					returnMap.put(name, value.trim());
			}
		}

		/*
		 * while (entries.hasNext()) { entry = (Map.Entry) entries.next(); name
		 * = (String) entry.getKey(); String valueObj = entry.getValue();
		 * if(null == valueObj){ value = ""; }else if(valueObj instanceof
		 * String[]){ String[] values = (String[])valueObj; for(int
		 * i=0;i<values.length;i++){ value = values[i] + ","; } value =
		 * value.substring(0, value.length()-1); }else{ value =
		 * valueObj.toString(); } returnMap.put(name, value); }
		 */
		return returnMap;
	}
	
	public static Map<String,String> getParameterMapHasNullValue(HttpServletRequest request){
		// 参数Map
		Map<String, String[]> properties = request.getParameterMap();
		// 返回值Map
		Map<String, String> returnMap = new HashMap<String, String>();
		Set<String> set = properties.keySet();
		for (String name : set) {
			String[] values = properties.get(name);
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				if (StringUtils.isNotEmpty(name))
					returnMap.put(name, value.trim());
			}
		}
		return returnMap;
	}
	
}
