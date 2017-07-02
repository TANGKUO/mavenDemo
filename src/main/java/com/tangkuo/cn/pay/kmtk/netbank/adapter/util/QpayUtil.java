package com.kame.micropay.netbank.service.adapter.util;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.kame.micropay.account.enums.CurrencyType;
import com.kame.micropay.base.enums.GatewayType;
import com.kame.micropay.netbank.domain.BankTrade;
import com.kame.micropay.netbank.domain.qpay.AgreementReq;
import com.kame.micropay.netbank.domain.qpay.PayReq;
import com.kame.micropay.netbank.domain.qpay.SingleRefundReq;
import com.kame.micropay.netbank.enums.BankTradeStatus;
import com.kame.micropay.netbank.enums.BussInterfaceType;

/** 
* @ClassName: QpayUtil 
* @Description: 快捷 工具类
* @author fubin
* @date 2015年12月16日 下午5:34:41 
*  
*/ 
public class QpayUtil {
	/** 
	* @Title: sendAgreement 
	* @Description: 预签约请求
	* @param @param req
	* @param @return    设定文件 
	* @return BankTrade    返回类型 
	* @throws 
	*/ 
	public static BankTrade sendAgreement(AgreementReq req, String snbinding, String requestMsg, String responseMsg) {
		Date date = new Date();
		BankTrade bankTrade = new BankTrade();
		bankTrade.setOrderNo(req.getSnBinding());
		bankTrade.setOldOrderNo(snbinding); // 绑定流水号
		bankTrade.setPaymentTypeId(req.getSnBinding());
		bankTrade.setPaychannelType(req.getPaychannelType());
		bankTrade.setBankCode(req.getBankCode());
		bankTrade.setPayCardType(req.getCardType().name());
		bankTrade.setRequestTime(date);
		bankTrade.setRequestMsg(requestMsg);
		bankTrade.setTradeType(GatewayType.QPAY.name());
		bankTrade.setBussInterfaceType(BussInterfaceType.SIGNAGREEMENT);
		bankTrade.setStatus(BankTradeStatus.REQUEST.name());
		bankTrade.setCreateTime(date);
		bankTrade.setResponseTime(date);
		bankTrade.setUpdateTime(date);
		bankTrade.setResponseMsg(responseMsg);
		bankTrade.setStatus(BankTradeStatus.SUCCESS.name());
		return bankTrade;
	}
	
	/** 
	* @Title: sendAgreement 
	* @Description: 支付
	* @param @param req
	* @param @return    设定文件 
	* @return BankTrade    返回类型 
	* @throws 
	*/ 
	public static BankTrade pay(PayReq req, String requestMsg) {
		Date date = new Date();
		BankTrade bankTrade = new BankTrade();
		bankTrade.setOrderNo(req.getOrderNo());
		bankTrade.setPaymentTypeId(req.getOrderNo());
		//bankTrade.setOldOrderNo(req.getSnBinding()); 
		bankTrade.setOrderAmount(req.getAmount());
		bankTrade.setCurrencyType(CurrencyType.CNY.name());
		bankTrade.setPayCardType(req.getCardType().name());
		bankTrade.setPaychannelType(req.getPaychannelType());
		bankTrade.setBankCode(req.getBankCode());
		bankTrade.setTradeType(GatewayType.QPAY.name());
		bankTrade.setRequestTime(date);
		bankTrade.setRequestMsg(requestMsg);
		bankTrade.setBussInterfaceType(BussInterfaceType.PAY);
		bankTrade.setStatus(BankTradeStatus.REQUEST.name());
		bankTrade.setPayName(req.getAccountName());
		bankTrade.setPayCardNo(req.getAccountNumber());
		//bankTrade.setResponseTime(date);
		//bankTrade.setUpdateTime(date);
		//bankTrade.setResponseMsg(responseMsg);
		bankTrade.setCreateTime(date);
		bankTrade.setChannelName(req.getPaychannelType().getText());
		bankTrade.setBankName(req.getBankName());
		return bankTrade;
	}
	
	/** 
	* @Title: singleRefund 
	* @Description: 单个退款
	* @param @param req
	* @param @param requestMsg
	* @param @param responseMsg
	* @param @return    设定文件 
	* @return BankTrade    返回类型 
	* @throws 
	*/ 
	public static BankTrade singleRefund(SingleRefundReq req, String requestMsg) {
		Date date = new Date();
		BankTrade bankTrade = new BankTrade();
		bankTrade.setOrderNo(req.getOrderNo());
		bankTrade.setPaymentTypeId(req.getOrderNo());
		bankTrade.setOldOrderNo(req.getOldOrderNo());
		bankTrade.setOrderAmount(req.getAmount());
		bankTrade.setPayCardType(req.getCardType() != null ? req.getCardType().name() : StringUtils.EMPTY);
		bankTrade.setPaychannelType(req.getPaychannelType());
		bankTrade.setCurrencyType(CurrencyType.CNY.name());
		bankTrade.setBankCode(req.getBankCode());
		bankTrade.setTradeType(GatewayType.QPAY.name());
		bankTrade.setRequestTime(date);
		bankTrade.setRequestMsg(requestMsg);
		bankTrade.setBussInterfaceType(BussInterfaceType.SINGLEREFUND);
		bankTrade.setStatus(BankTradeStatus.FAIL.name());
		bankTrade.setCreateTime(date);
		bankTrade.setBankName(req.getBankName());
		bankTrade.setChannelName(req.getPaychannelType().getText());
		return bankTrade;
	}
}
