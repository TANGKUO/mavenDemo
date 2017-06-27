package com.tangkuo.cn.pay.kmtk.netbank;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.CharSet;

import com.tangkuo.cn.pay.Constants;
import com.tangkuo.cn.pay.TtyException;

/*
 * 
* @ClassName: StringFormat
* @Description: (字符串格式化工具类)
* @author tangkuo
* @date 2017年6月27日 下午11:06:22
*
 */
public class StringFormatUtils implements Constants{

    /** 
     * 格式化输出 字符串  
     * [*]左对齐,右补空格 
     *  
     * @param str 
     * @param length : 输出长度 
     * @return 
     * @throws UnsupportedEncodingException 
     */  
    public static String formatSpace(String str, int length) throws UnsupportedEncodingException {
    	String param = "";
    	if(null != str){
    		param = str;
    	}
    	//参数转为ISO_8859_1编码，中文占2个字节
		byte[] buff = param.getBytes(Constants.CHARSET_GBK); 
		String tmp = new String(buff,Constants.CHARSET_ISO_8859_1);
		//判断参数超长
    	if(tmp.length() > length){
    		StringBuilder sb = new StringBuilder();
    		sb.append("参数值[").append(str).append("]超过标准长度[").append(length).append("]!");
    		throw new TtyException("failure",sb.toString());
    	}
    	
        String format = "%-" + (length < 1 ? 1 : length) + "s";
        String formatedStr = String.format(format, tmp);
        //从ISO_8859_1转回GBK
        String  result = new String(formatedStr.getBytes(Constants.CHARSET_ISO_8859_1),Constants.CHARSET_GBK);
        return result;
    }
  
    /** 
     * 格式化输出 整数  
     * [*]右对齐,左补0 
     *  
     * @param num 
     * @param length : 输出长度 
     * @return 
     */  
    public static String format0Right(long num, int length) {
		//判断参数超长
    	if(String.valueOf(num).length() > length){
    		StringBuilder sb = new StringBuilder();
    		sb.append("参数值[").append(num).append("]超过标准长度[").append(length).append("]!");
    		throw new TtyException(sb.toString());
    	}
    	
        String format = "%0" + (length < 1 ? 1 : length) + "d";
        return String.format(format, num);
    }
  
    /** 
     * 格式化输出 浮点数 
     * [*]右对齐,左补0 
     *  
     * @param d 
     * @param minLength : 最小输出长度 
     * @param precision : 小数点后保留位数 
     * @return 
     */  
    public static String format0Right(double d, int minLength, int precision) {  
        String format = "%0" + (minLength < 1 ? 1 : minLength) + "."  
                + (precision < 0 ? 0 : precision) + "f";  
        return String.format(format, d);  
    }  
    
	/**
	 * 方法说明：<br>
	 * 屏蔽卡号打印
	 */
	public static String getCardLog(String cardNo) {
		if(null == cardNo || cardNo.length() == 0) {
			return "";
		}
		StringBuilder meta = new StringBuilder();
		int len = cardNo.length();
		if(len <= 4) {
			return "****";
		}else if(len > 4 && len <=8 ){
			return meta.append(cardNo.substring(0, len-4)).append("****").toString();
		}else{
			return meta.append(cardNo.substring(0, len-8)).append("****").append(cardNo.substring(len-4)).toString();
		}
	}
	
    /** 
     * 先转为ISO_8859_1编码格式，再字符串计算长度
     *  
     * @param str 
     * @return 
     */  
	public static int countLength(String str) throws UnsupportedEncodingException{
		byte[] buff = str.getBytes(Constants.CHARSET_GBK);  
		String encodedStr = new String(buff,Constants.CHARSET_ISO_8859_1);
		return encodedStr.length();
	}
	

}
