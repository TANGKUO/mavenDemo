package com.tangkuo.cn.pay.kmtk.netbank.adapter.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * ClassName: CharUtil <br/>
 * Java判断一个字符串是否有中文一般情况是利用Unicode编码(CJK统一汉字的编码区间：0x4e00–0x9fbb)的正则来做判断，
 * 但是其实这个区间来判断中文不是非常精确，因为有些中文的标点符号比如：，。等等是不能识别的。
 */
public class CharUtil {
	
	/** 
	* @Title: isChinese 
	* @Description: 根据Unicode编码完美的判断中文汉字和符号
	* @param char c
	* @return boolean   返回类型 
	*/
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	/** 
	* @Title: isChinese 
	* @Description: 完整的判断中文汉字和符号
	* @param String strName
	* @return boolean   返回类型 
	*/
	public static boolean isChinese(String strName) {
			char[] ch = strName.toCharArray();
			for (int i = 0; i < ch.length; i++) {
				char c = ch[i];
				if (isChinese(c)) {
					return true;
				}
			}
			return false;
		}

	/** 
	* @Title: isChineseByREG 
	* @Description: 只能判断部分CJK字符（CJK统一汉字）
	* @param String str
	* @return boolean   返回类型 
	*/
	public static boolean isChineseByREG(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
		return pattern.matcher(str.trim()).find();
	}

	/** 
	* @Title: isChineseByName 
	* @Description: 只能判断部分CJK字符（CJK统一汉字）
	* @param String str
	* @return boolean   返回类型 
	*/
	public static boolean isChineseByName(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		// 大小写不同：\\p 表示包含，\\P 表示不包含
		// \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
		String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
		Pattern pattern = Pattern.compile(reg);
		return pattern.matcher(str.trim()).find();
	}
	
	/** 
	* @Title: replaceChineseEncode 
	* @Description: 用charset编码加密并替换字符串里面的中文(默认为UTF8)
	* @param String str
	* @return String 返回类型 
	*/
	@SuppressWarnings("deprecation")
	public static String replaceChineseEncode(String str,String charset) {
		if (StringUtils.isEmpty(str)){
			return "";
		}
		if (StringUtils.isEmpty(charset)) {
			charset = "UTF-8";
		}
		StringBuilder sb = new StringBuilder();
		char[] ch = str.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if(CharUtil.isChinese(c)) {
				try {
					sb.append(URLEncoder.encode(c+"", charset));
				} catch (UnsupportedEncodingException e) {
					sb.append(URLEncoder.encode(c+""));
				}
			}else{
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/** 
	* @Title: replaceChineseEncodeGbk 
	* @Description: 用GBK编码加密替换字符串里面的中文
	* @param String str
	* @return String 返回类型 
	*/
	public static String replaceChineseEncodeGbk(String str) {
		return replaceChineseEncode(str,"GBK");
	}
	
	/** 
	* @Title: replaceChineseEncodeUTF8 
	* @Description: 用UTF8编码加密替换字符串里面的中文
	* @param String str
	* @return String 返回类型 
	*/
	public static String replaceChineseEncodeUTF8(String str) {
		return replaceChineseEncode(str,"UTF-8");
	}
	
	
	public static void main(String[] args) {
		String[] strArr = new String[] { "www.micmiu.com",
				"!@#$%^&*()_+{}[]|\"'?/:;<>,.", "！￥……（）——：；“”‘’《》，。？、", "不要啊",
				"やめて", "韩佳人", "???" };
		for (String str : strArr) {
			System.out.println("===========> 测试字符串：" + str);
			System.out.println("正则判断结果：" + isChineseByREG(str) + " -- "
					+ isChineseByName(str));
			System.out.println("Unicode判断结果 ：" + isChinese(str));
			System.out.println("详细判断列表：");
			char[] ch = str.toCharArray();
			for (int i = 0; i < ch.length; i++) {
				char c = ch[i];
				System.out.println(c + " --> " + (isChinese(c) ? "是" : "否"));
			}
		}
	}

}
