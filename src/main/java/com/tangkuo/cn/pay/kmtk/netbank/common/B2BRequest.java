
package com.tangkuo.cn.pay.kmtk.netbank.common;

import javax.validation.constraints.NotNull;

import com.tangkuo.cn.pay.kmtk.netbank.common.util.validatetype.CheckAdd;
import com.tangkuo.cn.pay.kmtk.netbank.common.util.validatetype.CheckDel;


public class B2BRequest extends TtyObject {

	/**
	 * serialVersionUID:(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 订单号
	 */
	@NotNull(message="订单号不能为空",groups = {CheckDel.class,CheckAdd.class})
	private String orderNo;
	/**
	 * 签名数据
	 */
	@NotNull(message="签名数据不能为空",groups = CheckDel.class)
	private String sign;
	/**
	 * 交易金额
	 */
	@NotNull(message="交易金额不能为空",groups = {CheckDel.class,CheckAdd.class})
	private String amount;
	/**
	 * 银行编码
	 */
	@NotNull(message="银行编码不能为空",groups = {CheckDel.class,CheckAdd.class})
	private String bankCode;
	/** 
	* 银行名称
	*/
	@NotNull(message="银行名称不能为空",groups = {CheckDel.class,CheckAdd.class})
	private String bankName;
	/**
	 * 支付渠道
	 */
	@NotNull(message="支付渠道号不能为空",groups = {CheckDel.class,CheckAdd.class})
	private String payChannelType;
	/**
	 * 卡类型
	 */
	@NotNull(message="支付渠道号不能为空",groups = {CheckDel.class,CheckAdd.class})
	private String cardType;
	/** 
	* 交易币钟
	*/
	private String currency;
	/** 
	* 商户返回地址
	*/
	@NotNull(message="商户返回地址不能为空",groups = {CheckDel.class,CheckAdd.class})
	private String merUrl;
	/** 
	* 备注
	*/
	//@NotNull(message="备注不能为空",groups = {CheckDel.class,CheckAdd.class})
	private String remark;
	
	private String netBankType = "B2B";
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
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
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getPayChannelType() {
		return payChannelType;
	}
	public void setPayChannelType(String payChannelType) {
		this.payChannelType = payChannelType;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getMerUrl() {
		return merUrl;
	}
	public void setMerUrl(String merUrl) {
		this.merUrl = merUrl;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getNetBankType() {
		return netBankType;
	}
	public void setNetBankType(String netBankType) {
		this.netBankType = netBankType;
	}
	
}
