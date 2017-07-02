package com.kame.micropay.commons.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kame.micropay.commons.TtyException;
import com.kame.micropay.commons.config.properties.Property;
import com.kame.micropay.commons.signtype.DES;

public class DESUtil {

	private static Logger log = LoggerFactory.getLogger(DESUtil.class);
	/**
	 * ECB模式的des解密
	 * 
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String desDecrypt(String msg){
		if(StringUtils.isEmpty(msg) ){
			return msg;
		}
		
		String key = Property.getProperty("data_encrypt_key", "");
		if(StringUtils.isEmpty(key)){
			return msg;
		}
		try {
			String str = DES.desDecrypt(msg, key);
			return str;
		} catch (TtyException e) {
			log.error("-------------------"+msg+"解密异常-------------------");
			return msg;
		}
	}
	
	/**
	 * ECB模式的des加密，以base64的编码输出
	 * 
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String desEncrypt(String msg){
		if(StringUtils.isEmpty(msg)){
			return msg;
		}
		String key = Property.getProperty("data_encrypt_key");
		if(StringUtils.isEmpty(key)){
			return msg;
		}
		try {
			return DES.desEncrypt(msg, key);
		} catch (TtyException e) {
			log.error("-------------------des加密异常-------------------");
			return msg;
		}
	}
	
	public static void main(String [] args){
		String key = "kiIoeWGSqYmzgSxIysJwSmq2As0KhKrS";
		System.out.println(DES.desEncrypt("redis",key));
		System.out.println(DES.desEncrypt("kmpayzf_2016",key));
		
		System.out.println(DES.desEncrypt("kmpaycwg",key));
		System.out.println(DES.desEncrypt("kmpaycwg_2016",key));
		
		System.out.println(DES.desEncrypt("kmpay",key));
		System.out.println(DES.desEncrypt("kmpay_qaz123",key));
		
		
		
		//String val = DES.desEncrypt("kmpay", key);
		//System.out.println(DES.desDecrypt(val, key));
	}
}
