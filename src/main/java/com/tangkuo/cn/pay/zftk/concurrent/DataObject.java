package com.tangkuo.cn.pay.zftk.concurrent;

import java.util.Date;

public class DataObject<V> implements java.io.Serializable {	
	private static final long serialVersionUID = 6944995201541149613L;
	
	private V object;
	private Date date;
	
	public DataObject() {
		this.date = new Date();
	}
	public DataObject(V value) {
		this.object = value;
		this.date = new Date();
	}
	
	public V getObject() {
		return object;
	}
	public void setObject(V object) {
		this.object = object;
	}
	public Date getDate() {
		return date;
	}
	
}
