package com.tangkuo.cn.utils.string;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


public class StringTools {
	
	/***
	 * 
	 * @Title: isFiveOrSixChar 
	 * @Description: 五或六位字符校验  校验代金券密码
	 * @param  str String
	 * @return boolean 返回类型 
	 * @throws
	 */
	public static boolean isFiveOrSixChar(String str){
		if(!Pattern.matches("^[0-9a-zA-Z]{5,6}$", str)){
			return false;
		}
		return true;
	}
	
	/***
	 * 
	 * @Title: isNineOrTenNum 
	 * @Description: 九或十位数字校验   校验代金券账号
	 * @param  str String
	 * @return boolean 返回类型 
	 * @throws
	 */
	public static boolean isNineOrTenNum(String str){
		if(!Pattern.matches("^[0-9]{9,10}$", str)){
			return false;
		}
		return true;
	}
	
	/***
	 * 
	 * @Title: isOneToFourNum 
	 * @Description: 一至四位数字校验   校验代金券前缀
	 * @param  str String
	 * @return boolean 返回类型 
	 * @throws
	 */
	public static boolean isOneToFourNum(String str){
		if(!Pattern.matches("^[0-9]{1,4}$", str)){
			return false;
		}
		return true;
	}
	
	/***
	 * 
	 * @Title: isOneToEightNum 
	 * @Description: 一至八位数字校验   校验代金券金额
	 * @param  str String
	 * @return boolean 返回类型 
	 * @throws
	 */
	public static boolean isOneToEightNum(String str){
		if(!Pattern.matches("^([1-9]{1}[0-9]{0,7})?$", str)){
			return false;
		}
		return true;
	}
	
	 /***
     * 
     * @Title: isEmail 
     * @Description: (邮箱校验) 
     * @param  str String
     * @return boolean 返回类型 
     * @throws
     */
    public static boolean isEmail(String str){
		if(!Pattern.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", str)){
			return false;
		}
		return true;
	}
    
    /***
     * 
     * @Title: isMobile 
     * @Description: (邮箱手机号码) 
     * @param  str String
     * @return boolean 返回类型 
     * @throws
     */
    public static boolean isMobile(String str){
    	if(!Pattern.matches("1[3|4|5|8][0-9]{9}", str)){
    		return false;
    	}
    	return true;
    }
    
	
	static Map<String,String> html_specialchars_table = new Hashtable<String,String>();
	static {
	        html_specialchars_table.put("&lt;", "<");
	        html_specialchars_table.put("&gt;", ">");
	        html_specialchars_table.put("&amp;", "&");
	        html_specialchars_table.put("&#39;", "'");
	}
	static String htmlDecode(String s){
	        Enumeration<String> en = ((Hashtable<String,String>) html_specialchars_table).keys();
	        while(en.hasMoreElements()){
	                String key = en.nextElement();
	                String val = html_specialchars_table.get(key);
	                s = s.replaceAll(key, val);
	        }
	        return s;
	}
	
	static int[] weight={7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};    //十七位数字本体码权重
	static String[] validate={ "1","0","X","9","8","7","6","5","4","3","2"};    //mod11,对应校验码字符值    
    
    public static String getValidateCode(String id17){
        int sum=0;
        int mode=0;
        for(int i=0;i<id17.length();i++){
            sum=sum+Integer.parseInt(String.valueOf(id17.charAt(i)))*weight[i];
        }
        mode=sum%11;
        return validate[mode];
    }
	
	public static String encrytMobile(String mobile){
		String result = mobile;
		Pattern p = Pattern.compile("^(\\d{3})(\\d*)(\\d{3})$");
		Matcher m = p.matcher(mobile);
		if(m.find() && m.groupCount() == 3){
			result = m.group(1) + m.group(2).replaceAll("\\d", "*") + m.group(3);
		}
		return result;
	}
	
	public static boolean isPassword(String str){
		if(!Pattern.matches("[\\w]{4,}", str)){
			return false;
		}
		
		if(!Pattern.matches(".*[^\\d]+.*", str) || !Pattern.matches(".*[^a-zA-Z]+.*", str) || !Pattern.matches(".*[^_]+.*", str)){
			return false;
		}
		return true;
	}
	
	public static String encrytEmail(String email){
		String result = email;
		Pattern p = Pattern.compile("(.)(.*)(.@.*)");
		Matcher m = p.matcher(email);
		if(m.find() && m.groupCount() == 3){
			result = m.group(1) + m.group(2).replaceAll(".", "*") + m.group(3);
		}
		return result;
	}
	
	public static String encryEmailOrMobile(String emailOrMobile){
		if(StringUtils.isBlank(emailOrMobile)){
			return "";
		}
		
		if(emailOrMobile.indexOf("@") > 0){
			return encrytEmail(emailOrMobile);
		}else{
			return encrytMobile(emailOrMobile);
		}
	}
	
	public static String encryBankNo(String bankNo){
		String result=bankNo;
		Pattern p = Pattern.compile("^(\\d*)(\\d{4})$");
		Matcher m = p.matcher(bankNo);
		if(m.find() && m.groupCount()==2){
			result=m.group(1).replaceAll("\\d", "*")+m.group(2);
		}
		return result;
	}
	public static String encryBankAccount(String bankNo){
		String result=bankNo;
		Pattern p = Pattern.compile("^(\\w*)(\\w{4})$");
		Matcher m = p.matcher(bankNo);
		if(m.find() && m.groupCount()==2){
			result=m.group(1).replaceAll("\\w", "*")+m.group(2);
		}
		return result;
	}
	public static String subBankNo(String bankNo){
		String result=bankNo;
		Pattern p = Pattern.compile("^(\\w*)(\\w{4})$");
		Matcher m = p.matcher(bankNo);
		if(m.find() && m.groupCount()==2){
			result=m.group(1).replaceAll("\\w", "")+m.group(2);
		}
		return result;
	}
	public static String encryChineseName(String chineseName){
		String result = chineseName;
		Pattern p = Pattern.compile("([^u4E00-u9FA5]*)([^u4E00-u9FA5])");
		Matcher m = p.matcher(chineseName);
		if(m.find() && m.groupCount() == 2){
			result = m.group(1).replaceAll(".", "*") + m.group(2);
		}
		return result;
	}
	
	public static String encryMemberRealName(String name){
		StringBuffer firstChar = new StringBuffer(name.substring(0, 1));	
		for (int i=1;i<=name.length()-1;i++) {
			firstChar.append("*");
		}
		return firstChar.toString();
	}
	public static String encryChineseFirstName(String chineseName){
		String result = "*" + chineseName.substring(chineseName.length()-1);
//		Pattern p = Pattern.compile("([^u4E00-u9FA5]{1})([^u4E00-u9FA5]*)");
//		Matcher m = p.matcher(chineseName);
//		if(m.find() && m.groupCount() == 2){
//			result = m.group(1).replaceAll(".", "*") + m.group(2);
//		}
		return result;
	}
	public static String encryBetweenBankNo(String bankNo){
		String result = bankNo;
		Pattern p = Pattern.compile("^(\\d{6})(\\d*)(\\d{4})$");
		Matcher m = p.matcher(bankNo);
		if(m.find() && m.groupCount() == 3){
			result = m.group(1) + "***" + m.group(3);
		}
		return result;
	}
	
	public static String formateBankNo(String bankNo){
		int bankNoLen = bankNo.length();
		int m = bankNoLen%4;
		StringBuffer bankNo_ = new StringBuffer();
		for (int i=0;i<bankNoLen;i++) {
			if(i == 3 || i == 7 || i == 11 || i == 15 || i == 19 || i == 23) 
            { 
				if (m==0&&i>=bankNoLen-4) {
            		bankNo_.append(bankNo.charAt(i));
            	} else {
            		bankNo_.append("*").append(" ");
            	}				 
            }else
            { 
            	if (m==0&&i>=bankNoLen-4) {
            		bankNo_.append(bankNo.charAt(i));
            	} else {
            		if (i>=bankNoLen-m) {
                		bankNo_.append(bankNo.charAt(i)); 
                	} else {
                		bankNo_.append("*"); 
                	}
            	}           	            	
            }
		}
		return bankNo_.toString();
	}
	public static String formateBankAccount(String bankNo){
		int bankNoLen = bankNo.length();
		StringBuffer bankNo_ = new StringBuffer();
		for (int i=0;i<bankNoLen;i++) {
			if(i == 3 || i == 7 || i == 11 || i == 15 || i == 19 || i == 23) 
            { 
            		bankNo_.append(bankNo.charAt(i)).append(" ");
			 
            } else {
            	bankNo_.append(bankNo.charAt(i)); 
            }			
		}
		return bankNo_.toString();
	}
	public static String formatDateStr(int minute){
		StringBuffer dateStr = new StringBuffer();
		int h = minute/60;
		int m = minute%60;
		if (h==0&&m!=0) {
			dateStr.append(m).append("分钟");
		} else if (m==0&&h!=0) {
			dateStr.append(h).append("小时");
		} else if (m!=0&&h!=0) {
			dateStr.append(h).append("小时").append(m).append("分钟");
		}
		return dateStr.toString();
	}
	
    public static boolean isNotBlank(String str) {
        return StringUtils.isNotBlank(str) && !"null".equalsIgnoreCase(str);
    }
    
    /**
     * 将BigDecimal格式化成#.##，最多保留2位小数
     * @param bd
     * @return
     */
    public static String formatBigDecimalTo2Bit(BigDecimal bd){
    	if(!(bd instanceof BigDecimal)){
    		return null;
    	}
    	
    	DecimalFormat df = new DecimalFormat("#.##");
    	return df.format(bd);
    }
    /**
     * 
     * @Title: replaceParam
     * @Description: 参数替换
     * @param @param object
     * @param @param param
     * @param @return 设定参数
     * @return String 返回类型
     * @throws
     */
    public static String replaceParam(String object,String param){
		if(StringUtils.isBlank(param)){
			return null;
		}
		String value = null;
		if(param.indexOf(".")!= -1){
			value = param.replaceAll("\\.", object);
		}
		return value;
	}
    
    /**
	 * 
	 * @Title: isAmoutFormat
	 * @Description: 校验金额
	 * @param @param money
	 * @param @return 设定参数
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isAmoutFormat(String money){
		if (!Pattern.matches("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$", money)) {
			return false;
		}
		return true;
	}
	
	public static String timestampToString(Date ts){
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(null!=ts){
				tsStr = sdf.format(ts);	
			}
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tsStr;
	}
	
	public static String arrayToString (Date ts){
		
		String  date ="";
		try {
			if(null!=ts){
				String str = StringTools.timestampToString(ts);
				String [] straty = str.split(" ");
				date= straty[0];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
    
}
