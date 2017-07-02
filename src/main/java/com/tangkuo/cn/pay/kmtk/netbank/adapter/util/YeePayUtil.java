/**
 * Project Name:kame-bank
 * File Name:YeePayUtil.java
 * Package Name:com.kame.micropay.netbank.service.adapter.util
 * Date:2016年5月3日下午3:44:01
 * Copyright (c) 2016, Kame-Pay All Rights Reserved.
 *
 */

package com.kame.micropay.netbank.service.adapter.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.kame.micropay.commons.Constants;
import com.kame.micropay.commons.signtype.AES;
import com.kame.micropay.commons.signtype.Digest;
import com.kame.micropay.commons.signtype.RSA;

/**
 * ClassName: YeePayUtil <br/>
 * Function: 易宝支付 帮助类. <br/>
 * Reason: ADD REASON(可选). <br/>
 * date: 2016年5月3日 下午3:44:01 <br/>
 *
 * @author Bill Huang
 * @version 1.1
 * @since JDK 1.7
 */
public class YeePayUtil {

	private static Logger LOG = LoggerFactory.getLogger(YeePayUtil.class);
	
	public static Random random = new Random();
	
	public static final String CUSTOMERROR_KEY = "customError";
	
	
	/**
	 * 生成RSA签名
	 */
	public static String handleRSA(TreeMap<String, Object> map,
			String privateKey) {
		StringBuilder sbuffer = new StringBuilder();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			sbuffer.append(entry.getValue());
		}
		String signTemp = sbuffer.toString();

		String sign = "";
		if (StringUtils.isNotEmpty(privateKey)) {
			sign = RSA.sign(signTemp, privateKey,Constants.CHARSET_UTF8);
		}
		return sign;
	}

	/**
	 * 对易宝支付返回的结果进行验签
	 * 
	 * @param data   易宝支付返回的业务数据密文
	 * @param encrypt_key   易宝支付返回的对ybAesKey加密后的密文
	 * @param yibaoPublickKey   易宝支付提供的公钥
	 * @param merchantPrivateKey   商户自己的私钥
	 * @return 验签是否通过
	 * @throws Exception
	 */
	public static boolean checkDecryptAndSign(String data, String encrypt_key,
			String yibaoPublickKey, String merchantPrivateKey) throws Exception {

		/** 1.使用YBprivatekey解开aesEncrypt。 */
		String AESKey = "";
		try {
			AESKey = RSA.decrypt(encrypt_key, merchantPrivateKey,Constants.CHARSET_UTF8);
		} catch (Exception e) {
			/** AES密钥解密失败 */
			LOG.error(e.getMessage(), e);
			return false;
		}

		/** 2.用aeskey解开data。取得data明文 */
		String realData = AES.decryptFromBase64(data, AESKey);
		TreeMap<String, String> map = JSON.parseObject(realData,new TypeReference<TreeMap<String, String>>() {});

		/** 3.取得data明文sign。 */
		String sign = StringUtils.trimToEmpty(map.get("sign"));

		/** 4.对map中的值进行验证 */
		StringBuilder signData = new StringBuilder();
		Iterator<Entry<String, String>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();

			/** 把sign参数隔过去 */
			if (StringUtils.equals((String) entry.getKey(), "sign")) {
				continue;
			}
			signData.append(entry.getValue() == null ? "" : entry.getValue());
		}
		
		/** 5. result为true时表明验签通过 */
		boolean result = RSA.verify(signData.toString(), sign, yibaoPublickKey, Constants.CHARSET_UTF8);
		return result;
	}

	/**
	 * 生成hmac
	 */
	public static String handleHmac(TreeMap<String, String> map, String hmacKey) {
		StringBuilder sbuffer = new StringBuilder();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sbuffer.append(entry.getValue());
		}
		String hmacTemp = sbuffer.toString();

		String hmac = "";
		if (StringUtils.isNotEmpty(hmacKey)) {
			hmac = Digest.hmacSHASign(hmacTemp, hmacKey, Constants.CHARSET_UTF8);
		}
		return hmac;
	}
	
	/**
	 * 格式化字符串
	 */
	public static String getRandom(int length) {
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < length; i++) {
			boolean isChar = (random.nextInt(2) % 2 == 0);// 输出字母还是数字
			if (isChar) { // 字符串
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				ret.append((char) (choice + random.nextInt(26)));
			} else { // 数字
				ret.append(Integer.toString(random.nextInt(10)));
			}
		}
		return ret.toString();
	}
	
	/**
	 * 解析易宝返回数据处理
	 */
	public static Map<String, String> parseYeepayResult(String result,String merchantPriKey,String yeepayPubkey) {
		if (StringUtils.isEmpty(result) || StringUtils.isEmpty(merchantPriKey) || StringUtils.isEmpty(yeepayPubkey)) {
			return null;
		}
		Map<String, String> _result	= new HashMap<String, String>();

		Map<String, String> jsonMap	= JSON.parseObject(result, new TypeReference<TreeMap<String, String>>() {});

		if(jsonMap.containsKey("error_code")) {
			_result	= jsonMap;
			return (_result);
		}

		String dataFromYeepay		= StringUtils.trimToEmpty(jsonMap.get("data"));
		String encryptkeyFromYeepay	= StringUtils.trimToEmpty(jsonMap.get("encryptkey"));

		boolean signMatch = false;
		try {
			signMatch = checkDecryptAndSign(dataFromYeepay, encryptkeyFromYeepay,yeepayPubkey, merchantPriKey);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			_result.put(CUSTOMERROR_KEY,	"check Decrypt Sign Error.");
			return (_result);
		}
		if(!signMatch) {
			_result.put(CUSTOMERROR_KEY,	"Sign not match error");
			return (_result);
		}

		String yeepayAESKey = "";
		try {
			yeepayAESKey = RSA.decrypt(encryptkeyFromYeepay, merchantPriKey, Constants.CHARSET_UTF8);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			_result.put(CUSTOMERROR_KEY,	"decrypt Merchant Yeepay AES Key Error.");
			return (_result);
		}
		String decryptData		= AES.decryptFromBase64(dataFromYeepay, yeepayAESKey);
		LOG.debug("DecryptData : " + decryptData);
		_result	= JSON.parseObject(decryptData, new TypeReference<TreeMap<String, String>>() {});

		return(_result);
	}

}

