package com.kame.micropay.netbank.service.adapter.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kame.micropay.commons.util.DateUtil;

/** 
* @ClassName: BocUtil 
* @Description: 中行工具类
* @author fubin
* @date 2015年9月22日 上午11:29:21 
*  
*/ 
public class IcbcUtil {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	/** 
	* @Title: requestXml 
	* @Description: 请求报文
	* @param @param orderNo
	* @param @param body
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/ 
	public String requestXml(Map<String, String> params, String body) {
		String data = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?>")
			.append("<CMS><eb>")
				.append("<pub>")
					.append("<TransCode>").append(params.get("transCode")).append("</TransCode>")	// 交易代码
					.append("<CIS>").append("400090001604411").append("</CIS>") // 集团CIS号
					.append("<BankCode>").append("102").append("</BankCode>") // 归属银行编号
					.append("<ID>").append("ncrp.y.4000").append("</ID>") // 证书ID
//					.append("<CIS>").append(Property.getProperty("ICBC.GIS")).append("</CIS>") // 集团CIS号
//					.append("<BankCode>").append(Property.getProperty("ICBC.BANKCODE")).append("</BankCode>") // 归属银行编号
//					.append("<ID>").append(Property.getProperty("ICBC.ID")).append("</ID>") // 证书ID
					.append("<TranDate>").append(DateUtil.getCDateString(DateUtil.YYYYMMDD)).append("</TranDate>") // 交易日期
					.append("<TranTime>").append(DateUtil.getCDateString(DateUtil.DATA_FORMAT_PATTERN_3)).append("</TranTime>") // 交易时间
					.append("<fSeqno>").append(params.get("fSeqno")).append("</fSeqno>") // 指令包序列号
				.append("</pub>")
				.append(body)
			.append("</eb></CMS>").toString();		
		log.debug("工行请求报文: {}", data);
		return data;
	}
	
	public String requestParam(Map<String, String> params) {
		return "Version=" + "0.0.0.1" + "&TransCode=" + params.get("transCode") 
				+ "&BankCode=" + "102" + "&GroupCIS=" + "400090001604411" 
				+ "&ID=" + "ncrp.y.4000" + "&PackageID=" + params.get("packageID") +"&Cert=" + params.get("cert") 
				+ "&reqData=" + params.get("reqData");
//		return "Version=" + Property.getProperty("ICBC.B2E.VERSION") + "&TransCode=" + params.get("transCode") 
//				+ "&BankCode=" + Property.getProperty("ICBC.B2E.BANKCODE") + "&GroupCIS=" + Property.getProperty("ICBC.B2E.GROUPCIS") 
//				+ "&ID=" + Property.getProperty("ICBC.B2E.ID") + "&PackageID=" + params.get("packageID") +"&Cert=" + params.get("cert") 
//				+ "&reqData=" + params.get("reqData");
	}
}
