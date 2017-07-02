package com.tangkuo.cn.pay.kmtk.netbank.adapter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象业务Handler适配器.
 */
public abstract class AbstractHandlerAdapter {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 
	* @Title: isNEmpty
	* @Description: (判断对象为null或为空)
	* @param  obj  Object
	* @return boolean    返回类型
	* @throws
	 */
	public static boolean isNEmpty(Object obj) {
		return obj == null ? true : isEmpty(obj);
	}

	/**
	 * 
	* @Title: isNotEmpty
	* @Description: (判断对象不为null或不为空)
	* @param  obj  Object
	* @return boolean    返回类型
	* @throws
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isNEmpty(obj);
	}

	/**
	 * 
	* @Title: isEmpty
	* @Description: (判断字符串为空，集合为空，数组为空(后续可以拓展hashSet,hashMap ...))
	* @param  obj  Object
	* @return boolean    返回类型
	* @throws
	 */
	public static boolean isEmpty(Object obj) {
		boolean isEmpty = true;
		if (obj != null) {
			if (obj instanceof String) {
				// 字符串
				String tmp = obj.toString();
				isEmpty = StringUtils.isEmpty(tmp);

			} else if (obj instanceof Collection<?>) {
				// 集合
				Collection<?> collections = (Collection<?>) obj;
				isEmpty = CollectionUtils.isEmpty(collections);

			} else if (obj instanceof Map<?, ?>) {
				// Map
				Map<?, ?> map = (Map<?, ?>) obj;
				isEmpty = map.size() == 0;

			} else if (obj instanceof Object[]) {
				// 数组
				Object[] objarray = (Object[]) obj;
				isEmpty = objarray.length == 0;

			} else if (obj instanceof Float) {
				Float f = (Float) obj;
				isEmpty = f == 0f;
			} else {
				isEmpty = false;
			}
		}
		return isEmpty;
	}

	/**
	 * 
	* @Title: mapToString
	* @Description: (to String the Map ,and the format is : param=value&param1=value1&param2=value2. )
	* @param  m  Map<String, String>
	* @return String    返回类型
	* @throws
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

	/**
	 * 
	* @Title: jointKeyValue
	* @Description: (联合字符串 key不能为空 ,value可以为空 if key == null return ""; 
	* if value == null return key= else return key+value)
	* @param @param key
	* @param @param value
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public String jointKeyValue(String key, String value) {
		if (StringUtils.isEmpty(key)) {
			return StringUtils.EMPTY;
		}
		return StringUtils.isNotEmpty(value) ? key + value : key;
	}

	/**
	 * 
	* @Title: jointKV
	* @Description: (联合字符串 key不能为空 ,value可以为空 if key == null return ""; 
	* if value == null return key else return key=value)
	* @param @param key
	* @param @param value
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public String jointKV(String key, String value) {
		if (StringUtils.isEmpty(key)) {
			return StringUtils.EMPTY;
		}
		return StringUtils.isNotEmpty(value) ? key + "=" + value : key;
	}

	public String joinKeyVal(String key, String value) {
		return jointKeyValue(key, value);
	}
	
	/**
	 * 
	* @Title: joinKeyValNoEqVal
	* @Description: (联合字符串 key不能为空 ,value可以为空 if key == null return key+value)
	* @param  key String
	* @param  value  String
	* @return String    返回类型
	* @throws
	 */
	public String joinKeyValNoEqVal(String key, String value) {
		if (StringUtils.isEmpty(key)) {
			return StringUtils.EMPTY;
		}
		return key + value;
	}

	/**
	 * 
	* @Title: joinKVNotEqV
	* @Description: (联合字符串 key不能为空 ,value可以为空 if key == null return key+value)
	* @param  key String
	* @param  value  String
	* @return String    返回类型
	* @throws
	 */
	public String joinKVNotEqV(String key, String value) {
		if (StringUtils.isEmpty(key)) {
			return StringUtils.EMPTY;
		}
		return key + "=" + value;
	}

	/**
	 * enJointMapToStr (将Map里面的key和value值用=连接,之后用|连接在一起,并返回)
	 * 
	 * @param Map<String,String>
	 *            map/请求参数
	 * @return String join在一起的字符串
	 */
	public String enJointMapToStr(Map<String, String> map) {
		if (isEmpty(map)) {
			return StringUtils.EMPTY;
		}
		StringBuilder target = new StringBuilder();
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			target.append(entry.getKey() + "=" + entry.getValue());
			target.append("|");
		}
		if (target.lastIndexOf("|") != -1) {
			target.deleteCharAt(target.length() - 1);
		}
		return target.toString();
	}

	/**
	 * 
	 * @Title: deSplitStrToMap
	 * @Description: (将字符串用|分隔开,之后用=分开为key,value填入到Map里面,并返回)
	 * @param str
	 *            String
	 * @return Map<String,Object> 返回类型
	 */
	public Map<String, Object> deSplitStrToMap(String str) {
		if (isEmpty(str)) {
			return null;
		}
		String[] target = null;
		if (str.indexOf("|") != -1) {
			target = str.split("\\|");
		} else {
			target = new String[] { str };
		}
		if (isEmpty(target)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		for (String s : target) {
			String[] v = s.split("=");
			map.put(v[0], v[1]);
		}
		return map;
	}

	/**
	 * 
	 * @Title: commonFormatValidDate
	 * @Description: (快捷格式化卡的有效期YYMM --> MMYY)
	 * @param validDate
	 *            String
	 * @return String 返回类型
	 */
	public String commonFormatValidDate(String validDate) {
		if (isEmpty(validDate)) {
			return "";
		}
		// String validDate = req.getValidDate();
		if (validDate.length() > 4 && validDate.indexOf("/") != -1) {
			String[] s = validDate.split("/");
			return s[0] + s[1];
		} else {
			return validDate.substring(2, 4) + validDate.substring(0, 2);
			// return validDate;
		}
	}

	/**
	 * 
	* @Title: getOrderMapNo
	* @Description: (获取映射订单号)
	* @param @param ChannelCode
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public String getOrderMapNo(String ChannelCode) {
		if (isEmpty(ChannelCode)) {
			return StringUtils.EMPTY;
		}
		// 获取映射订单号
		return this.getOrderMapNo(12, ChannelCode);
	}

	/**
	 * 
	 * @Title: getOrderMapNo
	 * @Description: (getOrderMapNo)
	 * @param len
	 *            int
	 * @param ChannelCode
	 *            String
	 * @return String 返回类型
	 */
	public String getOrderMapNo(int len, String ChannelCode) {
		if (isEmpty(ChannelCode)) {
			return StringUtils.EMPTY;
		}
		// 获取映射订单号
		return StringUtils.EMPTY;// 查询DB返回订单号.
	}
}
