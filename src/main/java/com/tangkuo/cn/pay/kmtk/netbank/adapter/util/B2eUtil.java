package com.tangkuo.cn.pay.kmtk.netbank.adapter.util;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.tangkuo.cn.pay.kmtk.netbank.adapter.domain.BankTrade;


/** 
* @ClassName: B2eUtil 
* @Description: 个人网银工具类
*  
*/ 
public class B2eUtil {
	/** 
	* @Title: transfer 
	* @Description: 代发
	* @param @param params
	* @param @return    设定文件 
	* @return BankTrade    返回类型 
	* @throws 
	*/ 
	public static BankTrade transfer(TransferReq req) {
		Date curDate = new Date();
		BankTrade bankTrade = new BankTrade();
		bankTrade.setPaychannelType(req.getPaychannelType());
		bankTrade.setOrderNo(req.getOrderNo());
		bankTrade.setOrderAmount(req.getAmount());
		bankTrade.setBankCode(req.getBankCode());
		bankTrade.setTradeType(GatewayType.B2E.name());
		bankTrade.setBussInterfaceType(BussInterfaceType.TRANSFER);
		bankTrade.setPayCardType(StringUtils.EMPTY);
		bankTrade.setCurrencyType(CurrencyType.CNY.name());
		bankTrade.setRequestTime(curDate);
		bankTrade.setStatus(BankTradeStatus.REQUEST.name());
		bankTrade.setRequestMsg(StringUtils.EMPTY);
		bankTrade.setCreateTime(curDate);
		bankTrade.setPaymentTypeId(req.getOrderNo());
		bankTrade.setChannelName(req.getPaychannelType().getText());
		bankTrade.setBankName(req.getBankName());
		//bankTrade.setRemark(req.getr);
		//增加了收款方的信息
		bankTrade.setPayIbkn(req.getIsson());
		bankTrade.setPayCardNo(req.getAcctNo());
		bankTrade.setPayName(req.getAcctName());
		
		//bankTrade.setOldOrderNo(params.get("oldOrderNo"));
		/*bankTrade.setPayeeIbkn(params.get("payeeIbkn"));
		bankTrade.setPayeeCardNo(params.get("payeeCardNo"));
		bankTrade.setPayeeName(params.get("payeeName"));
		bankTrade.setPayIbkn(paymentType.getpay);
		bankTrade.setPayCardNo(params.get("payCardNo"));
		bankTrade.setPayName(params.get("payName"));*/
		return bankTrade;
	}
}
