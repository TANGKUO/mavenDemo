package com.tangkuo.cn.pay.kmtk.netbank.adapter.util;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.tangkuo.cn.pay.kmtk.netbank.adapter.domain.BankTrade;


/** 
* @ClassName: B2cUtil 
* @Description: 个人网银工具类
*  
*/ 
public class B2cUtil {
	/** 
	* @Title: pay 
	* @Description: 支付
	* @param @param req    设定文件 
	* @return void    返回类型 
	* @throws 
	*/ 
	public static BankTrade pay(PayReq req) {
		Date curDate = new Date();
		BankTrade bankTrade = new BankTrade();
		bankTrade.setOrderNo(req.getOrderNo()); // 订单号
		bankTrade.setOrderAmount(Long.valueOf(req.getAmount())); // 订单金额
		bankTrade.setBankCode(req.getBankCode()); // 银行编码
		bankTrade.setPayCardType(req.getCardType().name()); // 卡类型
		bankTrade.setPaychannelType(req.getPaychannelType()); // 渠道路由
		bankTrade.setBussInterfaceType(BussInterfaceType.PAY); // 业务类型
		bankTrade.setPaymentTypeId(req.getOrderNo()); // 订单号
		bankTrade.setCurrencyType(CurrencyType.CNY.name()); // 币种
		bankTrade.setTradeType(GatewayType.B2C.name()); // 网关类型
		bankTrade.setRequestTime(curDate); // 请求时间
		bankTrade.setRequestMsg(""); // 请求报文
		bankTrade.setMerUrl(req.getMerUrl()); // 商户URL
		bankTrade.setStatus(BankTradeStatus.REQUEST.name()); // 处理状态
		bankTrade.setCreateTime(curDate); // 创建时间
		bankTrade.setChannelName(req.getPaychannelType().getText());
		bankTrade.setBankName(req.getBankName());
		bankTrade.setRemark(req.getRemark());
		return bankTrade;
	}
	
	/**
	 * pay 支付
	 * @param req 设定文件 
	 * @param orderMapNo MapNo编号
	 * @param reqXml 请求报文
	 * @return
	 */
	public static BankTrade pay(PayReq req,String orderMapNo,String reqXml) {
		Date curDate = new Date();
		BankTrade bankTrade = new BankTrade();
		bankTrade.setOrderNo(req.getOrderNo()); // 订单号
		bankTrade.setOrderAmount(Long.valueOf(req.getAmount())); // 订单金额
		bankTrade.setBankCode(req.getBankCode()); // 银行编码
		bankTrade.setPayCardType(req.getCardType().name()); // 卡类型
		bankTrade.setPaychannelType(req.getPaychannelType()); // 渠道路由
		bankTrade.setBussInterfaceType(BussInterfaceType.PAY); // 业务类型
		bankTrade.setPaymentTypeId(req.getOrderNo()); // 订单号
		bankTrade.setCurrencyType(CurrencyType.CNY.name()); // 币种
		bankTrade.setTradeType(GatewayType.B2C.name()); // 网关类型
		bankTrade.setRequestTime(curDate); // 请求时间
		bankTrade.setRequestMsg(""); // 请求报文
		bankTrade.setMerUrl(req.getMerUrl()); // 商户URL
		bankTrade.setStatus(BankTradeStatus.REQUEST.name()); // 处理状态
		bankTrade.setCreateTime(curDate); // 创建时间
		bankTrade.setChannelName(req.getPaychannelType().getText());
		bankTrade.setBankName(req.getBankName());
		bankTrade.setRemark(req.getRemark());
		bankTrade.setRequestMsg(reqXml);
		return bankTrade;
	}
	
	/**
	 * pay 支付
	 * @param req 设定文件 
	 * @param orderMapNo MapNo编号
	 * @param reqXml 请求报文
	 * @param mechNo 商户号
	 * @return
	 */
	public static BankTrade pay(PayReq req,String orderMapNo,String reqXml,String mechNo) {
		Date curDate = new Date();
		BankTrade bankTrade = new BankTrade();
		bankTrade.setOrderNo(req.getOrderNo()); // 订单号
		bankTrade.setOrderAmount(Long.valueOf(req.getAmount())); // 订单金额
		bankTrade.setBankCode(req.getBankCode()); // 银行编码
		bankTrade.setPayCardType(req.getCardType().name()); // 卡类型
		bankTrade.setPaychannelType(req.getPaychannelType()); // 渠道路由
		bankTrade.setBussInterfaceType(BussInterfaceType.PAY); // 业务类型
		bankTrade.setPaymentTypeId(req.getOrderNo()); // 订单号
		bankTrade.setCurrencyType(CurrencyType.CNY.name()); // 币种
		bankTrade.setTradeType(GatewayType.B2C.name()); // 网关类型
		bankTrade.setRequestTime(curDate); // 请求时间
		bankTrade.setRequestMsg(""); // 请求报文
		bankTrade.setMerUrl(req.getMerUrl()); // 商户URL
		bankTrade.setStatus(BankTradeStatus.REQUEST.name()); // 处理状态
		bankTrade.setCreateTime(curDate); // 创建时间
		bankTrade.setChannelName(req.getPaychannelType().getText());
		bankTrade.setBankName(req.getBankName());
		bankTrade.setRemark(req.getRemark());
		bankTrade.setRequestMsg(reqXml);
		bankTrade.setReqReserved1(mechNo);
		return bankTrade;
	}
	
	/** 
	* @Title: toSingleRefund 
	* @Description: 退款
	* @param @param params	
	* @param @return    设定文件 
	* @return BankTrade    返回类型 
	* @throws 
	*/ 
	public static BankTrade singleRefund(SingleRefundReq req, String requestXml) {
		Date curDate = new Date();
		BankTrade bankTrade = new BankTrade();
		bankTrade.setOrderNo(req.getOrderNo()); // 订单号
		bankTrade.setOldOrderNo(req.getOldOrderNo());
		bankTrade.setOrderAmount(Long.valueOf(req.getAmount())); // 订单金额
		bankTrade.setBankCode(req.getBankCode()); // 银行编码
		bankTrade.setPayCardType(req.getCardType() == null ? StringUtils.EMPTY : req.getCardType().name()); // 卡类型
		bankTrade.setPaychannelType(req.getPaychannelType()); // 渠道路由
		bankTrade.setBussInterfaceType(BussInterfaceType.SINGLEREFUND); // 业务类型
		bankTrade.setPaymentTypeId(req.getOrderNo()); // 订单号
		bankTrade.setCurrencyType(CurrencyType.CNY.name()); // 币种
		bankTrade.setTradeType(GatewayType.B2C.name()); // 网关类型
		bankTrade.setRequestTime(curDate); // 请求时间
		bankTrade.setRequestMsg(requestXml); // 请求报文
		bankTrade.setStatus(BankTradeStatus.REQUEST.name()); // 处理状态
		bankTrade.setCreateTime(curDate); // 创建时间
		bankTrade.setBankName(req.getBankName()); // 银行名称
		bankTrade.setChannelName(req.getPaychannelType().getText()); // 渠道名称
		bankTrade.setRemark(req.getRemark()); // 备注
		return bankTrade;
	}
	
	/** 
	* @Title: toSingleRefund 
	* @Description: 退款
	* @param @param params	
	* @param @param orderMapNo MapNo编号
	* @param @return    设定文件 
	* @return BankTrade    返回类型 
	* @throws 
	*/ 
	public static BankTrade singleRefund(SingleRefundReq req, String orderMapNo,String requestXml) {
		Date curDate = new Date();
		BankTrade bankTrade = new BankTrade();
		bankTrade.setOrderNo(req.getOrderNo()); // 订单号
		bankTrade.setOldOrderNo(req.getOldOrderNo());
		bankTrade.setOrderAmount(Long.valueOf(req.getAmount())); // 订单金额
		bankTrade.setBankCode(req.getBankCode()); // 银行编码
		bankTrade.setPayCardType(req.getCardType() == null ? StringUtils.EMPTY : req.getCardType().name()); // 卡类型
		bankTrade.setPaychannelType(req.getPaychannelType()); // 渠道路由
		bankTrade.setBussInterfaceType(BussInterfaceType.SINGLEREFUND); // 业务类型
		bankTrade.setPaymentTypeId(req.getOrderNo()); // 订单号
		bankTrade.setCurrencyType(CurrencyType.CNY.name()); // 币种
		bankTrade.setTradeType(GatewayType.B2C.name()); // 网关类型
		bankTrade.setRequestTime(curDate); // 请求时间
		bankTrade.setRequestMsg(requestXml); // 请求报文
		bankTrade.setStatus(BankTradeStatus.REQUEST.name()); // 处理状态
		bankTrade.setCreateTime(curDate); // 创建时间
		bankTrade.setBankName(req.getBankName()); // 银行名称
		bankTrade.setChannelName(req.getPaychannelType().getText()); // 渠道名称
		bankTrade.setRemark(req.getRemark()); // 备注
		return bankTrade;
	}
	
}
