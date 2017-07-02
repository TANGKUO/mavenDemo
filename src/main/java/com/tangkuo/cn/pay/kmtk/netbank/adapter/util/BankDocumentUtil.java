package com.kame.micropay.netbank.service.adapter.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: BankDocumentUtil 
* @Description: 解析银行报文数据
* @author fubin
* @date 2015年9月18日 下午4:59:48 
*  
*/ 
public class BankDocumentUtil {
	private static Logger log = LoggerFactory.getLogger(BankDocumentUtil.class);
	
	/** 
	* @Title: parseXmlTODocument 
	* @Description: 解析银行返回报文
	* @param @param xml
	* @param @return    设定文件 
	* @return Document    返回类型 
	* @throws 
	*/ 
	public static Document parseXml(String xml) {
		try {
			Document  doc= DocumentHelper.parseText(xml);
			return doc;
		} catch (DocumentException e) {
			e.printStackTrace();
			log.error("解析银行xml错误." + e);
		}
		return null;
	}
}
