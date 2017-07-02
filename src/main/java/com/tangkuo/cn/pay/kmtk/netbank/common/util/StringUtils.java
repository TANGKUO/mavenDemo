package com.tangkuo.cn.pay.kmtk.netbank.common.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class StringUtils {

    private StringUtils() {
    }

    /**
     * 检查指定的字符串是否为空。
     * <ul>
     * <li>SysUtils.isEmpty(null) = true</li>
     * <li>SysUtils.isEmpty("") = true</li>
     * <li>SysUtils.isEmpty("   ") = true</li>
     * <li>SysUtils.isEmpty("abc") = false</li>
     * </ul>
     * 
     * @param value 待检查的字符串
     * @return true/false
     */
    public static boolean isEmpty(String value) {
        int strLen = 0;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
    /**
     * 用于时间查询
     * 
     * @param str 2015-11-11
     * @return	2015-11-11 00:00:00
     */
    public static String getStartDate(String str){
    	if(isNotEmpty(str)){
    		return str + " 00:00:00";
    	}
    	return null;
    }
    /**
     * 用于时间查询
     * 
     * @param str 2015-11-11
     * @return	2015-11-11 23:59:59
     */
    public static String getEndDate(String str){
    	if(isNotEmpty(str)){
    		return str + " 23:59:59";
    	}
    	return null;
    }
    /**
     * 判断一个字符串不为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
    	if(!(str == null || str.trim().length() == 0)){
    		return true;
    	}
    	return false;
    }
    
    /**
     * 检查对象是否为数字型字符串,包含负数开头的。
     */
    public static boolean isNumeric(Object obj) {
        if (obj == null) {
            return false;
        }
        char[] chars = obj.toString().toCharArray();
        int length = chars.length;
        if(length < 1)
            return false;
        
        int i = 0;
        if(length > 1 && chars[0] == '-')
            i = 1;
        
        for (; i < length; i++) {
            if (!Character.isDigit(chars[i])) {
                return false;
            }
        }
        return true;
    }
    
    
	/** * 判断字符串是否是整数 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/** 搜索 * 判断字符串是否是浮点数 */
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			if (value.contains("."))
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/** * 判断字符串是否是数字 */
	public static boolean isNumber(String value) {
		return isInteger(value) || isDouble(value);
	}
    

    /**
     * 方法说明：在不确认参数是否为空时使用此方法<br>
     * 
     * @param cs1
     * @param cs2
     * @return
     */
    public static boolean equals(CharSequence cs1, CharSequence cs2) {
        return cs1 == null ? cs2 == null : cs1.equals(cs2);
    }

    /**
     * 检查指定的字符串列表是否不为空。
     */
    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }

    public static String encryptEmail(String email) {
        if (isEmpty(email))
            return "";
        int ind = email.indexOf("@");
        if (ind <= 0)
            return "";
        String[] ss = email.split("@");
        if (ind <= 2) {
            return ss[0] + "***" + "@" + ss[1];
        } else if (ind > 2) {
            return ss[0].substring(0, 2) + "***" + "@" + ss[1];
        }
        return "";
    }
    
    public static boolean checkEmail(String email) {
    	if(StringUtils.isEmpty(email)){
    		return false;
    	}
    	Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
    	Matcher matcher = pattern.matcher(email);
    	return matcher.matches();
    }
    public static boolean checkMobile(String mobile) {
    	if(StringUtils.isEmpty(mobile)){
    		return false;
    	}
    	Pattern pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
    	Matcher matcher = pattern.matcher(mobile);
    	return matcher.matches();
    }
    
    /**
     * 格式化银行卡号,前六后四
     * 
     * @param cardNo
     * @return
     */
    public static String formatBankCardNo(String cardNo){
    	if(StringUtils.isEmpty(cardNo) ){
    		return "";
    	}
    	if(cardNo.length() <= 10){
    		return cardNo;
    	}
    	StringBuffer sb = new StringBuffer();
    	sb.append(cardNo.substring(0, 6)).append("****").append(cardNo.substring(cardNo.length()-4, cardNo.length()));
    	return sb.toString();
    }
    /**
     * 获取身份证前3后4位
     * @param idCard
     * @return
     */
    public static String getIdCardFirst3Last4(String idCard){
    	if(StringUtils.isEmpty(idCard) ){
    		return "";
    	}
    	if(idCard.length() <= 10){
    		return idCard;
    	}
    	StringBuffer sb = new StringBuffer();
    	sb.append(idCard.substring(0, 3)).append("****").append(idCard.substring(idCard.length()-4, idCard.length()));
    	return sb.toString();
    }
    /**
     * 获取银行卡前6后4位
     * @param idCard
     * @return
     */
    public static String getBankCardFirst6Last4(String bankCard){
    	if(StringUtils.isEmpty(bankCard) ){
    		return "";
    	}
    	if(bankCard.length() <= 10){
    		return bankCard;
    	}
    	StringBuffer sb = new StringBuffer();
    	sb.append(bankCard.substring(0, 6)).append("****").append(bankCard.substring(bankCard.length()-4, bankCard.length()));
    	return sb.toString();
    }
    /**
     * 隐藏真实姓名    姓
     * @param name
     * @return
     */
    public static String getName(String name){
    	if(StringUtils.isNotEmpty(name)){
			if(name.length()>2){
				name ="*"+name.substring(name.length()-2,name.length());
			}else{
				name ="*"+name.substring(name.length()-1,name.length());
			}
			return name;
		}
    	return "";
    }
    /**
     * 根据身份证获取性别	0女、1男
     * @param idCard
     * @return
     */
    public static int getSex(String idCard){
		int sex = 0;
		if(idCard.length()>15){
			sex = Integer.parseInt(String.valueOf(idCard.charAt(16)));
		}else{
			sex = Integer.parseInt(String.valueOf(idCard.charAt(14)));
		}
		if(sex % 2 == 0){
			return 0;
		}else{
			return 1;
		}
    }
    /**
     * 根据身份证号码获取出生年月日
     * @param idCard
     * @return
     */
    public static String getBirth(String idCard){
    	String birth = idCard.substring(6, 14);
		String year = birth.substring(0,4);
		String month = birth.substring(4,6);
		String day = birth.substring(6,8);
		return year+"-"+month+"-"+day;
    }
    
    /**
     * 格式化银行卡号,隐藏至后四
     * 
     * @param cardNo
     * @return
     */
    public static String formatBankCardNo1(String cardNo){
    	if(StringUtils.isEmpty(cardNo) ){
    		return "";
    	}
    	StringBuffer sb = new StringBuffer();
    	sb.append("**************")
    		.append(cardNo.substring(cardNo.length()-4));
    	return sb.toString();
    }
    
    /**
     * 保留后四
     * 
     * @param cardNo
     * @return
     */
    public static String formatBankCardNo2(String cardNo){
    	if(StringUtils.isEmpty(cardNo) ){
    		return "";
    	}
    	StringBuffer sb = new StringBuffer();
    	sb.append(cardNo.substring(cardNo.length()-4));
    	return sb.toString();
    }
    
    /**
     * 格式化手机号码
     * 
     * @param cardNo
     * @return
     */
    public static String formatMobile(String mobile){
    	if(StringUtils.isEmpty(mobile) ){
    		return "";
    	}
    	if(mobile.length() <= 7){
    		return mobile;
    	}
    	StringBuffer sb = new StringBuffer();
    	sb.append(mobile.substring(0, 3)).append("****").append(mobile.substring(mobile.length()-4, mobile.length()));
    	return sb.toString();
    }
    
    /**
     * 获取银行卡后四位
     * 
     * @param cardNo
     * @return
     */
    public static String getBankCardRight4(String cardNo){
    	if(StringUtils.isEmpty(cardNo) ){
    		return "";
    	}
    	if(cardNo.length() <= 4){
    		return cardNo;
    	}
    	
    	return cardNo.substring(cardNo.length()-4, cardNo.length());
    }
    
    
    
    /**
     * 格式化中文：返回＊ 打头的字符串
     * @param value
     * @return
     */
    public static String formatChinaiese(String value) {
    	if(StringUtils.isEmpty(value)){
    		return "*";
    	}
    	return "*"+value.substring(1,value.length());
    }
    
    public static boolean checkNum(String barcode) {
    	if (null == barcode || barcode.length() != 19) {
			return false;
		}
    	
    	try {
    		Long.parseLong(barcode);
		} catch (Exception e) {
			return false;
		}
    	
    	int checkNo = Integer.valueOf(String.valueOf(barcode.charAt(18)));
    	int count = 0;
    	for (int i=0;i<=17;i++) {
    		int p = Integer.valueOf(String.valueOf(barcode.charAt(18 -(i+1)))) * (2 * i + 1);
    		int q = p / 10;
    		int r = p - q * 10;
    		count += (q + r);
    	}
    	int value = (count / 10 + 1) * 10 - count; 
    	return checkNo == value % 10;
    }
    /**
     * 返回两个数组的交集
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] arrJoin(String[] arr1, String[] arr2) {   
        Map<String, Boolean> map = new HashMap<String, Boolean>();   
        LinkedList<String> list = new LinkedList<String>();   
        for (String str : arr1) {   
            if (!map.containsKey(str)) {   
                map.put(str, Boolean.FALSE);   
            }   
        }   
        for (String str : arr2) {   
            if (map.containsKey(str)) {   
                map.put(str, Boolean.TRUE);   
            }   
        }   
  
        for (Entry<String, Boolean> e : map.entrySet()) {   
            if (e.getValue().equals(Boolean.TRUE)) {   
                list.add(e.getKey());   
            }   
        }   
  
        String[] result = {};   
        return list.toArray(result);   
    }
    /**
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     * @param obj
     * @return
     */
    public static boolean isNullOrEmpty(Object obj) {  
        if (obj == null)  
            return true;  
  
        if (obj instanceof CharSequence)  
            return ((CharSequence) obj).length() == 0;  
  
        if (obj instanceof Collection)  
            return ((Collection) obj).isEmpty();  
  
        if (obj instanceof Map)  
            return ((Map) obj).isEmpty();  
  
        if (obj instanceof Object[]) {  
            Object[] object = (Object[]) obj;  
            if (object.length == 0) {  
                return true;  
            }  
            boolean empty = true;  
            for (int i = 0; i < object.length; i++) {  
                if (!isNullOrEmpty(object[i])) {  
                    empty = false;  
                    break;  
                }  
            }  
            return empty;  
        }  
        return false;  
    }  
    
   /* public static void main(String [] args){
    	System.out.println(StringUtils.formatBankCardNo("62660987999999978998767"));
    	System.out.println(StringUtils.getBankCardRight4("62660987999999978998767"));
    	
    	System.out.println(StringUtils.formatMobile("15914119964"));
    	
    	System.out.println("是否为整数："+StringUtils.isNumber("2.9"));
    	
	    System.out.println(RandomStringUtils.random(32, true, true));
    	
    }*/
    
}
