package com.tangkuo.cn.pay.kmtk.enums;

public enum IdType {
	TEMP("临时身份证"),
	IDCARD("身份证"),
	RPCARD("居住证"),
	VSCARD("签证"),
	PPCARD("护照"),
	RBCARD("户口本"),
	MTCARD("军官证");
	
	private String text;
	
	IdType(String text){
		this.text = text;
	}
	
	public String getText(){
		return this.text;
	}
}
