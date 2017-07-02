package com.tangkuo.cn.pay.kmtk.netbank.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateElment {

    public static final String DOUBLE_PATTERN = "^([+-]?)\\d*\\.?\\d+$";
    
    public static final String NAME_PATTERN = "^(?:[0-9]|[\u4e00-\u9fa5]|[a-zA-Z])*$";
    
    /**
     * 手机号码表达式
     */
    public static final String MOBILE_PATTERN = "^1[3|4|5|7|8][0-9]\\d{4,8}$";
    
    /**
     * 正整数表达式
     */
    public static final String POSITIVE_NUMBER_PATTERN = "^[1-9]+\\d*$";//必须是1-9开头的
    
    /**
     * 邮件表达式
     */
    public static final String EMAIL_PATTERN = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    
    /**
     * 判断日期的正则表达式
     */
    public static final String DATE_PATTERN = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$";

    /**
     * 字母、数字或符号(空格除外)不能是纯数字/纯字母
     */
    public static final String PASSWORD_PATTERN = "^(?=.*\\d.*)(?=.*[a-zA-Z].*)([^ ]{6,20})$";

    /**
     * 是否是邮件
     * 
     * @param value
     */
    public static boolean isEmail(String value) {
        return compare(value, EMAIL_PATTERN);
    }
    
    /**
     * 判断是否是正整数
     * @param value
     * @return
     */
    public static boolean isPositiveNumber(String value) {
    	return compare(value, POSITIVE_NUMBER_PATTERN);
    }
    
    /**
     * 是否是手机号
     * @param value
     * @return
     */
    public static boolean isMobile(String value) {
    	return compare(value, MOBILE_PATTERN);
    }
    
//    public static void main(String[] args) {
//        String value = "500.";
//        System.out.println(compare(value, POSITIVE_NUMBER_PATTERN));
//        
//    }

    public static boolean compare(String value, String patternFormat) {
    	if (StringUtils.isEmpty(value)) {
    		return false;
    	}
        Pattern pattern = Pattern.compile(patternFormat);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

}
