package com.tangkuo.cn.pay.kmtk.netbank;

/**
 * 
* @ClassName: StringUtils_bak
* @Description: (字符串处理工具类)
* @author tangkuo
* @date 2017年6月27日 下午1:21:26
*
 */
public class StringUtilsBank {

	
	/**
	 * c 要填充的字符 
	 * length 填充后字符串的总长度 
	 * content 要格式化的字符串 格式化字符串，左对齐
	 */
	public static String flushLeft(char c, int length, String content) {

		String str = "";
		String cs = "";
		if (content.length() > length) {
			str = content;
		} else {
			for (int i = 0; i < length - content.length(); i++) {
				cs = cs + c;
			}
		}
		str = content + cs;
		return str;
	}
	
	
	/***
	 * 
	 * @param c要填充的字符
	 * @param length填充后的字符串总长度
	 * @param content要格式化的字符串，右对齐
	 * @return
	 */
	public static String flushRight(char c,int length,String content){
		String str = "";
		String tmp = "";
		
		if(content.length() > length){
			str = content;
		}else {
			for(int i = 0; i < length - content.length();i++){
				tmp = tmp + c;
			}
			
		}
		str = tmp + content;
		return str; 
		
	}
}
