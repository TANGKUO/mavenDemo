package com.tangkuo.cn.pay.kmtk.enums;

public enum SettleStatus {

	/**
     * 请求银行
     */
	SUCCESS("对账成功"), 
    
    /**
     * 银行回调
     */
    FAIL("对账失败");
    
    private String text;

    SettleStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
