package com.tangkuo.cn.pay.kmtk.netbank.common;

public interface TradeType {

	String B2C_PAY = "abc";
	String B2C_SINGLEREFUND = null;

	public static String name(String value){
		return value;
		
	}
}
