/**
 * Copyright (c) 2011-2015 All Rights Reserved.
 */
package com.kame.micropay.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

import com.kame.micropay.commons.config.properties.Property;
import com.kame.micropay.commons.internal.parser.MapConverter;
import com.kame.micropay.commons.util.Md5Utils;
import com.kame.micropay.commons.util.StringUtils;
import com.kame.micropay.commons.util.ValidateUtil;
import com.kame.micropay.commons.util.validatetype.CheckAdd;
import com.kame.micropay.commons.util.validatetype.CheckDel;

/**
 *
 *
 * @author heming
 * @version $Id: BankPlatformUtil.java 2015年9月9日 上午11:50:01 $
 */
public class BankPlatformUtil {
	
	private static Logger log = LoggerFactory.getLogger(BankPlatformUtil.class);
	
	//支付网关的地址
	private static  String gateWayAdd = "/payment/submit.htm";
	//B2B支付网关地址
	private static String gateWayB2bSubmit = "/b2bPay/submit.htm";
	
	/**
	 * B2C支付请求加签  
	 * @param req
	 * @return
	 */
	public static String signB2C(B2CRequest req,ModelMap model){
		
		try {
			ValidateUtil.validate(req,CheckAdd.class);
		} catch (TtyException e) {
			log.error("验证参数错误",e);
			return null;
		}
		
		req.setSign(getSignData(req));
		
		String url = Property.getProperty("kame-gateway-url") + gateWayAdd;// + "?" + pkg;
		log.info("请求参数:[{}]",url);
		log.info("请求参数组装完成,准备向支付平台跳转");
		model.addAttribute("url", url);
		model.addAttribute("params", new MapConverter().toMap(req));
		
		return "notice";
	}
	
	
	/**
	 * B2C支付请求解签  
	 * @param req
	 * @return
	 */
	public static boolean  unSignB2C(B2CRequest req){
		if(null == req ){
			return false;
		}
		try {
			ValidateUtil.validate(req,CheckDel.class);
		} catch (TtyException e) {
			log.error("验证参数错误",e);
			return false;
		}
		
		return StringUtils.equals(getSignData(req), req.getSign());
	}
	
	
	private static  String getSignData(B2CRequest req){
		
		String signKey = Property.getProperty("web_gateway_signKey");
		if(StringUtils.isEmpty(signKey)){
			log.error("未配置b2c网关支付的加答密钥[web_gateway_signKey]");
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(req.getOrderNo())   							//支付方式 ID
		.append(req.getReturnUrl())   //返回url
		.append(req.getAmount())
		.append(req.getBankCode())
		.append(req.getBankName())
		.append(req.getCardType())
		.append(req.getPaychannelType())
		.append(req.getRemark())
		.append(req.getNetBankType())
		.append(signKey);										//加密key
		
		return Md5Utils.encryptMD5(sb.toString(), signKey);
	}
	
	/**
	 * B2B支付请求加签
	 * @param req
	 * @param model
	 * @return
	 */
	public static String signB2B(B2BRequest req,ModelMap model){
			
			try {
				ValidateUtil.validate(req,CheckAdd.class);
			} catch (TtyException e) {
				log.error("验证参数错误",e);
				return org.apache.commons.lang3.StringUtils.EMPTY;
			}
			
			req.setSign(getSignData(req));
			
			String url = Property.getProperty("kame-gateway-url") + gateWayB2bSubmit;// + "?" + pkg;
			log.info("请求参数:[{}]",url);
			log.info("请求参数组装完成,准备向支付平台跳转");
			model.addAttribute("url", url);
			model.addAttribute("params", new MapConverter().toMap(req));
			
			return "notice";
		}

	private static String getSignData(B2BRequest req){
			
			String signKey = Property.getProperty("web_gateway_signKey");
			if(StringUtils.isEmpty(signKey)){
				log.error("未配置b2c网关支付的加答密钥[web_gateway_signKey]");
				return null;
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append(req.getOrderNo())
			.append(req.getAmount())
			.append(req.getBankCode())
			.append(req.getBankName())
			.append(req.getPayChannelType())
			.append(req.getCardType())
			.append(req.getCurrency())
			.append(req.getMerUrl())
			.append(req.getRemark())
			.append(signKey);
			
			return Md5Utils.encryptMD5(sb.toString(), signKey);
		}


	/**
	 * B2B支付请求解签  
	 * @param req
	 * @return
	 */
	public static boolean unSignB2B(B2BRequest req) {
		if(null == req){
			return false;
		}
		try {
			ValidateUtil.validate(req, CheckDel.class);
		} catch (TtyException e) {
			log.error("验证参数错误",e);
			return false;
		}
		return StringUtils.equals(getSignData(req), req.getSign());
	}
	
	
}
