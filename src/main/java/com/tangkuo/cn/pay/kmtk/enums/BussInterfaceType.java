package com.tangkuo.cn.pay.kmtk.enums;

/** 
* @ClassName: TradeType 
* @Description: 银行业务接口类型
*/ 
public enum BussInterfaceType {
	PAY("订单支付"), 
	QUERY("订单查询"), 
	SINGLEREFUND("退款"), 
	TRANSFER("银企代发"),
	BBUCKLE("银企代扣"),
	SIGNAGREEMENT("预签约"),
	PAYAGREEMENT("支付签约"),
	UNAGREEMENT("解约"),
	REEXCHANGE("退汇");
	
	private String text;

	BussInterfaceType(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
