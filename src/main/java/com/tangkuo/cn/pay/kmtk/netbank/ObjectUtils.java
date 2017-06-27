package com.tangkuo.cn.pay.kmtk.netbank;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @ClassName: ObjectUtils
 * @Description: ()
 * @author tangkuo
 * @date 2017年6月27日 10:52:01
 *
 */
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {

	/** 判断对象为null或为空 */
	protected static boolean isNEmpty(Object obj) {
		return obj == null ? true : isEmpty(obj);
	}

	/** 判断对象不为null或为空 */
	public static boolean isNotEmpty(Object obj) {
		return !isNEmpty(obj);
	}

	/** 判断字符串为空，集合为空，数组为空(后续可以拓展hashSet,hashMap ...) */
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
	 * to String the Map ,and the format is :
	 * param=value&param1=value1&param2=value2.
	 * 
	 * @param m
	 *            Map<String,String>
	 * @throws @return
	 *             String
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

	/*
	 * 联合字符串 key不能为空 ,value可以为空 if key == null return ""; if value == null
	 * return key= else return key=value
	 */
	public static String jointKeyValue(String key, String value) {
		if (StringUtils.isEmpty(key)) {
			return StringUtils.EMPTY;
		}
		return StringUtils.isNotEmpty(value) ? key + value : key;
	}

	public static String jointKV(String key, String value) {
		if (StringUtils.isEmpty(key)) {
			return StringUtils.EMPTY;
		}
		return StringUtils.isNotEmpty(value) ? key + "=" + value : key;
	}

	public static String joinKeyVal(String key, String value) {
		return jointKeyValue(key, value);
	}

	/*
	 * 联合字符串 key不能为空 ,value可以为空 if key == null return key=value
	 */
	public static String joinKeyValNoEqVal(String key, String value) {
		if (StringUtils.isEmpty(key)) {
			return StringUtils.EMPTY;
		}
		return key + value;
	}

	public static String joinKVNotEqV(String key, String value) {
		if (StringUtils.isEmpty(key)) {
			return StringUtils.EMPTY;
		}
		return key + "=" + value;
	}

}
