package com.tangkuo.cn.pay.kmtk.netbank.common;

import javax.validation.constraints.NotNull;

import com.tangkuo.cn.pay.kmtk.netbank.common.util.validatetype.CheckAdd;
import com.tangkuo.cn.pay.kmtk.netbank.common.util.validatetype.CheckDel;

public class B2CRequest extends TtyObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull(message="支付方式ID号不能为空",groups = {CheckDel.class,CheckAdd.class})
	private String orderNo;
	
	@NotNull(message="页面通知地址不能为空",groups = {CheckDel.class,CheckAdd.class})
	private String returnUrl;
	
	@NotNull(message="签名数据不能为空",groups = CheckDel.class)
	private String sign;
	
	@NotNull(message="交易金额不能为空",groups = {CheckDel.class,CheckAdd.class})
	private String amount;
	@NotNull(message="银行编码不能为空",groups = {CheckDel.class,CheckAdd.class})
	private String bankCode;
	@NotNull(message="支付渠道号不能为空",groups = {CheckDel.class,CheckAdd.class})
	private String paychannelType;
	@NotNull(message="银行名称不能为空",groups = {CheckDel.class,CheckAdd.class})
	private String bankName;
	//@NotNull(message="备注不能为空",groups = {CheckDel.class,CheckAdd.class})
	private String remark;
	
	private String netBankType = "B2C";
	
	/**
	 * 卡类型
	 */
	private String cardType;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getPaychannelType() {
		return paychannelType;
	}

	public void setPaychannelType(String paychannelType) {
		this.paychannelType = paychannelType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getNetBankType() {
		return netBankType;
	}

	public void setNetBankType(String netBankType) {
		this.netBankType = netBankType;
	}

	
}
