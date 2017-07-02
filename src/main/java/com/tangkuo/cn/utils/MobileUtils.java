package com.tangkuo.cn.utils;

import org.apache.commons.lang.StringUtils;

public class MobileUtils {

	/**
	 * 返回手机号码运营商
	 * @param mobile
	 * @return cm:中国移动;cu:中国联通;ct:中国电信
	 */
	public static String getMobileOperator(String mobile){
		if(StringUtils.isEmpty(mobile)){
			return "";
		}
		if(mobile.startsWith("86")){
			mobile=mobile.substring(2);
		}else if(mobile.startsWith("+86")){
			mobile=mobile.substring(3);
		}
		
		String cm = "^((13[4-9])|(147)|(178)|(15[0-2,7-9])|(18[2-4,7-8]))\\d{8}|((1705))\\d{7}$";
		String cu = "^((13[0-2])|(145)|(176)|(15[5-6])|(18[5,6]))\\d{8}|((1707)|(1708)|(1709))\\d{7}$";
		String ct = "^((133)|(153)|(173)|(177)|(18[0,1,9]))\\d{8}|((1700)|(1701)|(1702))\\d{7}$";

		String operator = null;
		
		if (mobile.matches(cm)) {
			operator = "cm";
		} else if (mobile.matches(cu)) {
			operator = "cu";
		} else if (mobile.matches(ct)) {
			operator = "ct";
		} else{
			operator = "";
		}
		
		return operator;
	}
	
	/**
	 * 判断是否为国内手机号码
	 * @param mobile
	 * @return ture：是;false:否
	 */
	public static boolean isDomesticMobile(String mobile){
		if(!"".equals(getMobileOperator(mobile))){
			return true;
		}
		return false;
	}
	
	/**
	 * 去掉国内手机国家码前缀
	 * @param mobile
	 * @return mobile or null
	 */
	public static String rmMobilePrefix(String mobile){
		if(isDomesticMobile(mobile)){
			if(mobile.startsWith("86")){
				mobile=mobile.substring(2);
			}else if(mobile.startsWith("+86")){
				mobile=mobile.substring(3);
			}
			
			return mobile;
		}else{
		
			return "";
		}
		
	}
	
}
