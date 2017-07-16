package com.tangkuo.cn.pay.zftk.util;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.tangkuo.cn.pay.zftk.common.IPUtils;
import com.tangkuo.cn.utils.MD5;



public class StringUtils {

	
	/**
	 * 获取请求头，并将javaScript过滤转义
	 * @param request
	 * @param headName
	 * @return
	 */
	public static String getRequestHead(HttpServletRequest request, String headName) {
		String headStr = null;
		if(null != request) {
			headStr = request.getHeader(headName);
			if(StringUtils.isNotEmpty(headStr)) {
				headStr = StringEscapeUtils.escapeJavaScript(headStr);
			}			
		}
		
		return headStr;
	}	
	
	/**
	 * 判断字符串是否只包含数字、字母
	 * @param validString
	 * @return
	 */
	public static boolean isChar(String validString) {
		byte[] tempbyte = validString.getBytes();
		for (int i = 0; i < validString.length(); i++) {
			if ((tempbyte[i] < 48) || ((tempbyte[i] > 57) & (tempbyte[i] < 65))
					|| (tempbyte[i] > 122)
					|| ((tempbyte[i] > 90) & (tempbyte[i] < 97))) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean checkDate(String checkValue)  {  
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    @SuppressWarnings("unused")
		Date d = null;   
	    try  
	   {  
	     d = dateFormat.parse(checkValue);    
	   }  
	    catch(Exception e)  
	   {   
	     return false;  
	   }  
	   String eL= "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-9]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";  
	   Pattern p = Pattern.compile(eL);   
	   Matcher m = p.matcher(checkValue);   
	   boolean b = m.matches();  
       return b;
	}
	
	public static boolean IsUrl(String str)    
	{    
	    String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";    
	    Pattern p = Pattern.compile(regex);   
	    Matcher m = p.matcher(str);   
	    boolean b = m.matches();
	    return b;
	}
	
	public static boolean IsDomain(String str)    
	{    
		Pattern p=Pattern.compile("(http(s)?://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");
	    Matcher m = p.matcher(str);   
	    boolean b = m.matches();
	    return b;
	}
	
	public static boolean IsIp(String str)
	{
		Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");	
		Matcher m = pattern.matcher(str);   
	    boolean b = m.matches();
	    return b;
	}
	
	public static boolean isNumber(String validString) {
		byte[] tempbyte = validString.getBytes();
		for (int i = 0; i < validString.length(); i++) {
			if ((tempbyte[i] < 48) || (tempbyte[i] > 57)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean floatnumber(String validString) {

		try {
			Float.parseFloat(validString);
			if ((Float.parseFloat(validString)) <= 0) {
				return false;
			}
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
	
	public static boolean isMoney(String txMoney) {
		boolean vilid = false;
		try {
			if (txMoney.indexOf(".") > -1) {
				String temp = txMoney;
				txMoney = txMoney.substring(0, txMoney.indexOf("."));
				temp = temp.substring(temp.indexOf("."));

				if (temp.length() - 1 >= 2) {
					temp = temp.substring(1, 3);
				} else {
					if (temp.length() - 1 != 0) {
						temp = temp.substring(1);
					}
				}
				if (!temp.equals(".")) {
					if (txMoney.equals("")) {
						txMoney = "0";
					}
					txMoney = txMoney + "." + temp;
				}
			}
			vilid = true;
		} catch (NumberFormatException ex) {
		}
		return vilid;
	}
	

	/**
	 * 判断日期格式是否正确 实现逻辑： 1.将String类型转换成Date，如果不能转换，肯定错误
	 * 2.将转换后的date转换成String，如果不等，
	 * 则逻辑错误：如format为"yyyy-MM-dd",date为"2006-02-31",转换为日期后再转换回字符串为
	 * "2006-03-03",说明格式虽然对,但日期逻辑上不对.
	 * 
	 * @param date
	 *            需校验的字符串型数据
	 * @param format
	 *            校验格式
	 * @return
	 */
	public static boolean checkDate(String date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		Date temDate = null;
		try {
			temDate = df.parse(date);
		} catch (Exception e) {
			// 如果不能转换,肯定是错误格式
			return false;
		}
		String reStr = df.format(temDate);
		return date.equals(reStr);
	}

	/**
	 * 检查金额格式 ： 形如“xxx.xx”，其中xx全为数字
	 * @param orderAmount
	 * @return
	 */
	public static boolean checkAmount(String orderAmount) {
		Pattern p = Pattern.compile("^(([1-9]\\d*)|([0]))(\\.\\d{1,2})?$");
		Matcher m = p.matcher(orderAmount);
		return m.matches();
	}
	
	/**
	 * 判断字符串内容是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public static String generateForm(Map paramsMap, String address) {
		StringBuilder sb = new StringBuilder();
		sb.append("<Form name=\"PayForm\" Action=\"").append(address).append("\" method=\"post\">\n");
		if(null != paramsMap && 0 < paramsMap.size()){
			Set keys = paramsMap.keySet();
			for (Object object : keys) {
				sb.append("<input type=\"hidden\" name=\"").append(object).append("\" Value=\"").append(paramsMap.get(object)).append("\" />\n");
			}			
		}
		sb.append("</Form>\n");
		return sb.toString();
	}
	@SuppressWarnings("unchecked")
	public static Map<String, String> reqMapToMap(Map map) {
		// 提出Map值到另一map
		Map<String, String> tmpRstMap = new HashMap<String, String>();
		Set tmpKeySet = map.keySet();
		for (Iterator tmpIt = tmpKeySet.iterator(); tmpIt.hasNext();) {
			Object tmpKey = tmpIt.next();
			Object tmpVal = map.get(tmpKey);
			if (tmpVal instanceof Object[]) {
				tmpVal = ((Object[]) tmpVal)[0];
			}
			tmpRstMap.put(tmpKey.toString(), tmpVal.toString());
		}

		return tmpRstMap;
	}
	
	/**
	 * 获取请求头内容，为了防止敏感数据的泄露，去掉获取所有请求参数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getRequestParamts(HttpServletRequest request) {
		StringBuilder params = new StringBuilder("");
		Enumeration e = request.getHeaderNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String value = request.getHeader(name);
			params.append(name).append("=").append(value);
			params.append("|");
		}
		return params.toString();
	}
	
	/**
	 * 对象序列化后加密
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String encryptObject(Object obj, String key) throws Exception {
		String returnStr = "";
		//序列化
		ISerializer serializer = SerializerFactory.getSerializer();
		//加密
		EncryptWithDES encrypt = new EncryptWithDES(key);
		String temOrder = serializer.toJSONString(obj);
		returnStr = encrypt.encrypt(temOrder);
		
		return returnStr;
	}
	/**
     * 获取图片路径
     * 
     * @param pic
     * @return
     */
    public static String getPicPath(String pic,HttpServletRequest request) {
        String pictureCode = ConfigConstants.PICTURECODE;
        String picPath = null;
        String picPathPrefix = pic.substring(0, pic.lastIndexOf("/"));
        String picPathSuffix = pic.substring(pic.lastIndexOf("/") + 1, pic.length());
        picPath = ConfigConstants.MERCHANTLOOKPATH + File.separator + picPathPrefix + File.separator+ picPathSuffix;
        picPath = getPictureUrl(picPath, pictureCode, ConfigConstants.MERCHANTLOOKPATH,request);
        return picPath;
    }
	/**
	 * 图片预览URL
	 * 
	 * @param url 例如:https://192.168.1.223/proxy/20140529/c952106c059a48a092750aea87cb8cc7.jpg<br/>
	 *            或者20140529/c952106c059a48a092750aea87cb8cc7.jpg<br/>
	 * @param password
	 * @param lookPath 在图片服务器的查看路径。 例如:https://192.168.1.223/merchant
	 * @return
	 */
	public static String getPictureUrl(String url, String password, String lookPath,HttpServletRequest request) {
		if (StringUtils.isBlank(url) || StringUtils.isBlank(password)||StringUtils.isBlank(lookPath)) {
			return null;
		}
		String[] lookPathArr;
		StringBuilder urlPassBuilder = new StringBuilder();
		String[] urlArr;
		
		if (File.separator.equals("/")) {
			urlArr = url.split("/");
		} else {
			urlArr = url.split("\\\\");
		}
		lookPathArr = lookPath.split("/");
		// 图片路径不符合规则
		if (urlArr.length < 2) {
			return null;
		}
		String lookPathName = lookPathArr[lookPathArr.length - 1].toLowerCase();
		StringBuilder encryptUrl = new StringBuilder();
		encryptUrl.append(File.separator).append(lookPathName).
		append(File.separator).append(urlArr[urlArr.length - 2]).
		append(File.separator).append(urlArr[urlArr.length - 1]);
		String merchantIP = IPUtils.getClientIP(request);
		urlPassBuilder.append(password).append("&").append(merchantIP).append("&").append(encryptUrl.toString());
		MD5 md5 = new MD5();
		return new StringBuilder().append(url).append("?code=").append(md5.getMD5ofStr(urlPassBuilder.toString()).toLowerCase()).toString();
	}
	
}
