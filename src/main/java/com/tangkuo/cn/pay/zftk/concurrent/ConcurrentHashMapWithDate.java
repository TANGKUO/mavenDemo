package com.tangkuo.cn.pay.zftk.concurrent;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWithDate<K,V> extends ConcurrentHashMap {
	private static final long serialVersionUID = 5539412425293410692L;
	
	public Object put(Object key, Object value) {
		if (value == null)
            throw new NullPointerException();
		DataObject<V> data = new DataObject(value);		
		return super.put(key, data);
	}
	
	public V get(Object key) {
		Object obj = super.get(key);		
		if(obj != null) {
			DataObject data = (DataObject)obj;
			return (V)data.getObject();
		} else {
			return null;
		}
	}
	
	public Date getDate(Object key) {
		Object obj = super.get(key);		
		if(obj != null) {
			DataObject data = (DataObject)obj;
			return data.getDate();
		} else {
			return null;
		}
	}
	
	
	public static void main(String[] args) {
		ConcurrentHashMapWithDate<String, String> map = new ConcurrentHashMapWithDate<String, String>();
		map.put("tk001", "value1");
		System.out.println(map.get("tk001"));
		System.out.println(map.getDate("tk001"));
	}

}
