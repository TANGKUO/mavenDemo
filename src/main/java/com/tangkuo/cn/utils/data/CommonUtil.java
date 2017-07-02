package com.tangkuo.cn.utils.data;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.applet.Applet;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
* @ClassName: CommonUtil
* @Description: (通用工具类 )
* @author tangkuo
* @date 2017年7月2日 下午2:23:15
*
 */
@SuppressWarnings("unchecked")
public class CommonUtil {

    // 默认日期格式
    public static final String DATE_FMT = "yyyy-MM-dd"; // 日期

    public static final String TIME_FMT = "HH:mm:ss"; // 时间

    public static final String DATE_TIME_FMT = "yyyy-MM-dd HH:mm:ss"; // 日期时间

//	private static final Gson gson = new Gson();	//gson对象

    // 验证的正则表达式
    private static final String REG_ALPHA = "^[a-zA-Z]+$";

    private static final String REG_ALPHANUM = "^[a-zA-Z0-9]+$";

    private static final String REG_NUMBER = "^\\d+$";

    private static final String REG_INTEGER = "^[-+]?[1-9]\\d*$|^0$/";

    private static final String REG_FLOAT = "[-\\+]?\\d+(\\.\\d+)?$";

    private static final String REG_PHONE = "^((\\(\\d{2,3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}(\\-\\d{1,4})?$";

    private static final String REG_MOBILE = "^((\\+86)|(86))?(1)\\d{10}$";

    private static final String REG_QQ = "^[1-9]\\d{4,10}$";

    private static final String REG_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    private static final String REG_ZIP = "^[1-9]\\d{5}$";

    private static final String REG_IP = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    private static final String REG_URL = "^(http|https|ftp):\\/\\/(([A-Z0-9][A-Z0-9_-]*)(\\.[A-Z0-9][A-Z0-9_-]*)+)(:(\\d+))?\\/?/i";

    private static final String REG_CHINESE = "^[\\u0391-\\uFFE5]+$";

    private static final String REG_MONEY = "[\\-\\+]?\\d+(\\.\\d+)?$";

    private final static byte[] APP_ENC_IV = { 0x45, 0x32, 0x33, 0x31, 0x41, 0x35, 0x42, 0x39 };

    /** 通过正则表达验证 */
    public static boolean matchesByRegex(String regex, String value){
        if(isNull(regex, value)){
            return false;
        }
        return Pattern.matches(regex, value);
    }

    /** 可以用于判断Object,String,Map,Collection,String,Array是否为空 */
    public static boolean isNull(Object value) {
        if (value == null) {
            return true;
        } else if(value instanceof String){
            if(((String)value).trim().replaceAll("\\s", "").equals("")){
                return true;
            }
        }else if(value instanceof Collection) {
            if(((Collection)value).isEmpty()){
                return true;
            }
        } else if (value instanceof Object[]) {
            Object[] object = (Object[]) value;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNull(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        } else if(value.getClass().isArray()) {
            if(Array.getLength(value) == 0){
                return true;
            }
        } else if(value instanceof Map) {
            if(((Map)value).isEmpty()){
                return true;
            }
        } else {
            return false;
        }
        return false;

    }



    public static boolean isNull(Object value, Object... items){
        if (isNull(value) || isNull(items)) {
            return true;
        }
        for (Object item : items) {
            if (isNull(item)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotNull(Object value){

        return !isNull(value);
    }

    public static boolean isNotNull(Object value, Object... items){

        return !isNull(value,items);
    }


    public static boolean Null(Object value, Object... items){
        if (isNull(value) || isNull(items)) {
            return true;
        }
        for (Object item : items) {
            if (isNull(item)) {
                return true;
            }
        }
        return false;
    }




    public static boolean isAllEmpty(String... items){
        if (isNull(items)) {
            return true;
        }
        for (Object item : items) {
            if (isNotNull(item)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将数组转换成List<Map<String,Object>>对象
     * @param array 要转换的数组
     * @return List<Map<String,Objct>>
     */
    public static List<Map<String, Object>> arrayToList(String[] array) {
        List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < array.length; i++) {
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("value", i);
            item.put("text", array[i]);
            items.add(item);
        }
        return items;
    }

    /**
     * linwenming Mar 30, 2012
     * @return Map<String,Object>
     */
    public static Map<String, Object> arrayToMap(String[] array) {
        Map<String,Object> maps = new HashMap<String,Object>();
        for (int i = 0; i < array.length; i++) {
            maps.put(String.valueOf(i), array[i]);
        }
        return maps;
    }

    public static Map<String, Object> arrToMap(String[] array) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < array.length; i++) {
            map.put(array[i], array[i]);
        }
        return map;
    }
    public static boolean isAlpha(String value) {
        if(isNull(value))return false;
        return Pattern.matches(REG_ALPHA, value);
    }

    public static boolean isAlphanum(String value) {
        if(isNull(value))return false;
        return Pattern.matches(REG_ALPHANUM, value);
    }

    public static boolean isNumber(String value) {
        if(isNull(value))return false;
        return Pattern.matches(REG_NUMBER, value);
    }

    public static boolean isInteger(String value) {
        if(isNull(value))return false;
        return Pattern.matches(REG_INTEGER, value);
    }

    public static boolean isFloat(String value) {
        if(isNull(value))return false;
        return Pattern.matches(REG_FLOAT, value);
    }

    public static boolean isMoney(String value) {
        if(isNull(value))return false;
        return Pattern.matches(REG_MONEY, value);
    }

    public static boolean isPhone(String value) {
        if(isNull(value))return false;
        return Pattern.matches(REG_PHONE, value);
    }

    public static boolean isMobile(String value) {
        if(isNull(value))return false;
        return Pattern.matches(REG_MOBILE, value);
    }

    public static boolean isEmail(String value) {
        if(isNull(value))return false;
        return Pattern.matches(REG_EMAIL, value);
    }

    public static boolean isQQ(String value) {

        return Pattern.matches(REG_QQ, value);
    }

    public static boolean isZip(String value) {

        return Pattern.matches(REG_ZIP, value);
    }

    public static boolean isIP(String value) {

        return Pattern.matches(REG_IP, value);
    }

    public static boolean isURL(String value) {

        return Pattern.matches(REG_URL, value);
    }

    public static boolean isChinese(String value) {

        return Pattern.matches(REG_CHINESE, value);
    }

    public static String[] splitToArray(String str, String separatorChar,boolean removeDuplicateFlag) {
        String[] ret = null;
        if (isNull(str) || null == separatorChar) {
            return null;
        }


        ret = str.split(separatorChar);
        //用set集合过滤重复的关键字
        Set<String> set = new HashSet<String>();
        if(removeDuplicateFlag){
            for (String string : ret) {
                if(!"".equals(string)){
                    set.add(string.trim());
                }
            }

        }

        Object[] tempArrays = set.toArray();

        ret = new String[tempArrays.length];
        for (int i = 0 ;i <tempArrays.length;i++) {
            ret[i] = (String)tempArrays[i];
        }
        return ret;
    }

    public static String[] splitToArray(String str, String separatorChar) {
        // Performance tuned for 2.0 (JDK1.4)
        if (isNull(str)) {
            return null;
        }
        return str.split(separatorChar);
    }


    /** 验证是否为合法身份证 */
    public static boolean isIdcard(String value) {
        value = value.toUpperCase();
        if (!(Pattern.matches("^\\d{17}(\\d|X)$", value)||Pattern.matches("\\d{15}$", value))) {
            return false;
        }
        int provinceCode = Integer.parseInt(value.substring(0,2));
        if (provinceCode < 11 || provinceCode > 91) {
            return false;
        }
        return true;
    }

    public static boolean isDate(String value) {
        if(value==null || value.isEmpty())
            return false;
        try {
            new SimpleDateFormat().parse(value);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** 获取日期 */
    public static Date getCurrentDateTime() {

        return getCurrentDateTime(DATE_TIME_FMT);
    }

    public static Date getCurrentDate(){

        return getCurrentDate(DATE_FMT);
    }

    public static Date getCurrentTime(){

        return getCurrentTime(TIME_FMT);
    }

    public static Date getCurrentDateTime(String fmt) {

        return  dateStrToDate(fmt,getCurrentDateTimeStr(fmt));
    }

    public static Date getCurrentDate(String fmt){

        return  dateStrToDate(fmt,getCurrentDateStr(fmt));
    }

    public static Date getCurrentTime(String fmt){

        return  dateStrToDate(fmt,getCurrentTimeStr(fmt));
    }

    public static String getCurrentDateTimeStr() {

        return getCurrentDateTimeStr(DATE_TIME_FMT);
    }

    public static String getCurrentTimeStr() {

        return getCurrentTimeStr(TIME_FMT);
    }

    public static String getCurrentDateStr() {

        return getCurrentDateStr(DATE_FMT);
    }

    public static String getCurrentDateTimeStr(String fmt) {

        String temp = new SimpleDateFormat(fmt).format(new Date());

        return temp;
    }

    public static String getCurrentTimeStr(String fmt) {

        String temp = new SimpleDateFormat(fmt).format(new Date());

        return temp;
    }

    public static String getCurrentDateStr(String fmt) {

        String temp = new SimpleDateFormat(fmt).format(new Date());

        return temp;
    }

    public static String dateToDateStr(Date date) {

        String temp = new SimpleDateFormat(DATE_TIME_FMT).format(date);

        return temp;
    }

    public static String dateToDateStr(String fmt, Date date) {

        String temp = new SimpleDateFormat(fmt).format(date);

        return temp;
    }

    /** 转换为日期对象 */
    public static Date dateStrToDate(String date) {
        Date temp = null;
        try {
            temp = new SimpleDateFormat(DATE_FMT).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /** 字符串转换为日期时间格式对象 */
    public static Date dateStrToDatetime(String date) {
        if (date == null) {
            return null;
        }
        Date temp = null;
        try {
            temp = new SimpleDateFormat(DATE_TIME_FMT).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static Date dateStrToDate(String fmt, String date) {
        Date temp = null;
        try {
            temp = new SimpleDateFormat(fmt).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /** 格式化日期 */
    public static String formatDateTime(Date date) {

        return formatDateTime(DATE_TIME_FMT,date);
    }

    public static String formatDateTime(String fmt, Date date) {
        if (isNull(fmt) || isNull(date)) {
            return null;
        }
        String temp = new SimpleDateFormat(fmt).format(date);

        return temp;
    }

    public static String formatTime(Date date){
        return formatTime(TIME_FMT,date);
    }

    public static String formatTime(String fmt, Date date) {
        if (isNull(fmt) || isNull(date)) {
            return null;
        }
        String temp = new SimpleDateFormat(fmt).format(date);

        return temp;
    }

    public static String formatDate(Date date){
        return formatDate(DATE_FMT,date);
    }

    public static String formatDate(String fmt, Date date) {
        if (isNull(fmt) || isNull(date)) {
            return null;
        }
        String temp = new SimpleDateFormat(fmt).format(date);

        return temp;
    }

    public static String formatNumber(String fmt, Object value) {
        if (isNull(fmt) || isNull(value)) {
            return null;
        }
        String temp = new DecimalFormat(fmt).format(value);

        return temp;
    }

    /**交换两个日期 */
    public static void changeDate(Date date1,Date date2){
        if (isNull(date1,date2)) {
            return;
        }
        //判断两个时间的大小
        Calendar c1 =Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 =Calendar.getInstance();
        c2.setTime(date2);
        if (c1.after(c2)) {
            date1 = c2.getTime();
            date2 = c1.getTime();
        }
    }

    /** 比较两个日期相差的年数 */
    public static int compareYear(Date date1,Date date2){
        if (isNull(date1)||isNull(date2)) {
            return 0;
        }
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);

        if (c1.equals(c2)) {
            return 0;
        }

        if (c1.after(c2)) {
            Calendar temp = c1;
            c1 = c2;
            c2 = temp;
        }
        //计算差值
        int result = c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR);
        return result;
    }

    /** 比较两个日期相差的天数 */
    public static int compareDay(Date date1, Date date2) {
        if (date1 == null || date2 == null)
            return 0;

        Calendar d1 = Calendar.getInstance();
        d1.setTime(date1);
        Calendar d2 = Calendar.getInstance();
        d2.setTime(date2);
        if (d1.after(d2)) {
            Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
		/*
		 * 经过上面的处理，保证d2在d1之后
		 * 下面这个days可能小于0，因为d2和d1可能不在同一年里，这样的话虽然d1的年份小，但其在一年中的"第几天"却可能比d2大。
		 */
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {//如果不在同一年
            d1 = (Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
				/*
				 * 给定此 Calendar 的时间值，返回指定日历字段可能拥有的最大值。
				 * 例如，在某些年份中，MONTH 字段的实际最大值是 12，而在希伯来日历系统的其他年份中，该字段的实际最大值是 13。
				 * DAY_OF_YEAR：闰年366？
				 */
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        return days;

    }

    /** 比较两个日期相差的周数 */
    public static int compareWeek(Date date1,Date date2){
        if (isNull(date1)||isNull(date2)) {
            return 0;
        }
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);

        if (c1.equals(c2)) {
            return 0;
        }

        if (c1.after(c2)) {
            Calendar temp = c1;
            c1 = c2;
            c2 = temp;
        }
        //计算差值
        int result = c2.get(Calendar.WEEK_OF_MONTH)-c1.get(Calendar.WEEK_OF_MONTH);
        return result;
    }

    /** 比较两个日期相差的月数 */
    public static int compareMonth(Date date1, Date date2) {
        if (date1 == null || date2 == null)
            return 0;

        int iMonth = 0;
        int flag = 0;
        try {
            Calendar objCalendarDate1 = Calendar.getInstance();
            objCalendarDate1.setTime(date1);

            Calendar objCalendarDate2 = Calendar.getInstance();
            objCalendarDate2.setTime(date2);

            if (objCalendarDate2.equals(objCalendarDate1))
                return 0;
            if (objCalendarDate1.after(objCalendarDate2)) {
                Calendar temp = objCalendarDate1;
                objCalendarDate1 = objCalendarDate2;
                objCalendarDate2 = temp;
            }

            if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR))
                iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1.get(Calendar.YEAR))* 12 + objCalendarDate2.get(Calendar.MONTH) - flag)- objCalendarDate1.get(Calendar.MONTH);
            else
                iMonth = objCalendarDate2.get(Calendar.MONTH)- objCalendarDate1.get(Calendar.MONTH) - flag;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return iMonth;
    }

    /** 比较两个日期相差的秒数 */
    public static long compareTime(Date date1, Date date2) {
        if (date1 == null || date2 == null)
            return 0;

        Calendar c = Calendar.getInstance();

        c.setTime(date1);
        long l1 = c.getTimeInMillis();

        c.setTime(date2);
        long l2 = c.getTimeInMillis();

        return (l2 - l1) / 1000;
    }

    //设置时间
    private static Date addDateTime(Date date,int type,int num){
        if (date == null) {
            return null;
        }
        //初始化日历对象
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        //根据类型添加
        switch(type){
            case 1:		//添加年
                cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)+num);
                break;
            case 2:		//添加月
                cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+num);
                break;
            case 3:		//添加日
                cal.set(Calendar.DATE, cal.get(Calendar.DATE)+num);
                break;
            case 4:		//添加时
                cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)+num);
                break;
            case 5:		//添加分
                cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE)+num);
                break;
            case 6:		//添加秒
                cal.set(Calendar.SECOND, cal.get(Calendar.SECOND)+num);
                break;
        }

        //返回操作结果
        return cal.getTime();
    }


    //减一
    private static Date subDateTime(Date date,int type,int num){
        if (date == null) {
            return null;
        }
        //初始化日历对象
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        //根据类型添加
        switch(type){
            case 1:		//添加年
                cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)-num);
                break;
            case 2:		//添加月
                cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-num);
                break;
            case 3:		//添加日
                cal.set(Calendar.DATE, cal.get(Calendar.DATE)-num);
                break;
            case 4:		//添加时
                cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)-num);
                break;
            case 5:		//添加分
                cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE)-num);
                break;
            case 6:		//添加秒
                cal.set(Calendar.SECOND, cal.get(Calendar.SECOND)-num);
                break;
        }

        //返回操作结果
        return cal.getTime();
    }

    //设置日期时间
    private static Date setDateTime(Date date,int type,int num){
        if (date == null) {
            return null;
        }
        //初始化日历对象
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        //根据类型添加
        switch(type){
            case 1:		//添加年
                cal.set(Calendar.YEAR, num);
                break;
            case 2:		//添加月
                cal.set(Calendar.MONTH, num);
                break;
            case 3:		//添加日
                cal.set(Calendar.DATE, num);
                break;
            case 4:		//添加时
                cal.set(Calendar.HOUR_OF_DAY, num);
                break;
            case 5:		//添加分
                cal.set(Calendar.MINUTE, num);
                break;
            case 6:		//添加秒
                cal.set(Calendar.SECOND, num);
                break;
        }

        //返回操作结果
        return cal.getTime();
    }

    /** 设置年、月、日 */
    public static Date setYMD(Date date,int year,int month,int day){

        return setYear(setMonth(setDate(date,day),month),year);
    }
    public static Date setYear(Date date,int num){
        return addDateTime(date,1,num);
    }
    public static Date setMonth(Date date,int num){
        return addDateTime(date,2,num);
    }
    public static Date setDate(Date date,int num){
        return addDateTime(date,3,num);
    }
    /** 设置时、分、秒 */
    public static Date setHMS(Date date,int hour,int minute,int second){

        return setHour(setMinute(setSecond(date,second),minute),hour);
    }
    public static Date setHour(Date date,int num){
        return setDateTime(date,4,num);
    }
    public static Date setMinute(Date date,int num){
        return setDateTime(date,5,num);
    }
    public static Date setSecond(Date date,int num){
        return setDateTime(date,6,num);
    }

    /** 添加年、月、日、时、分、秒 */
    public static Date addYear(Date date,int num){
        return addDateTime(date,1,num);
    }
    public static Date addMonth(Date date,int num){
        return addDateTime(date,2,num);
    }
    public static Date addDate(Date date,int num){
        return addDateTime(date,3,num);
    }
    public static Date subDate(Date date,int num){
        return subDateTime(date,3,num);
    }
    /** 添加年、月、日 */
    public static Date addYMD(Date date,int year,int month,int day){

        return addYear(addMonth(addDate(date,day),month),year);
    }
    public static Date addHour(Date date,int num){
        return addDateTime(date,4,num);
    }
    public static Date addMinute(Date date,int num){
        return addDateTime(date,5,num);
    }
    public static Date addSecond(Date date,int num){
        return addDateTime(date,6,num);
    }
    /** 添加时、分、秒 */
    public static Date addHMS(Date date,int hour,int minute,int second){

        return addHour(addMinute(addSecond(date,second),minute),hour);
    }

    /** MD5加密 */
    public static String md5(String value) {
        StringBuilder result = new StringBuilder();

        try {
            // 实例化MD5加载类
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            // 得到字节数据
            byte[] data = md5.digest(value.getBytes("UTF-8"));

            result.append(byte2hex(data));

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 返回结果
        return result.toString().toUpperCase();
    }

    public static String byte2hex(byte[] data) {

        StringBuilder result = new StringBuilder();

        for (byte b : data) {
            // 将二进制转换成字符串
            String temp = Integer.toHexString(b & 0XFF);
            // 追加加密后的内容
            if (temp.length() == 1) { // 判断字符长度
                result.append("0").append(temp);
            } else {
                result.append(temp);
            }
        }

        return result.toString();
    }

    public static String substring(String str,int len){

        return substring(str,len,null);
    }

    public static String substring(String str, int len, String replaceChar) {

        return substring(str,0,len,replaceChar);
    }

    public static String substring(String str, int startIndex, int len, String replaceChar) {
        String temp = str;

        if (!isNull(str) && str.length() > len) {
            temp = str.substring(startIndex, len+startIndex) + (isNull(replaceChar) ? "" : replaceChar);
        }

        return temp;
    }

    public static String htmlEncode(String value) {
        String result = "";
        if (!isNull(value)) {
            result = value.replaceAll("&", "&amp;").replaceAll(">", "&gt;").replaceAll("<", "&lt;").replaceAll("\"", "&quot;").replaceAll(" ", "&nbsp;").replaceAll("\r?\n", "<br/>");
        }
        return result;
    }

    public static String htmlDecode(String value) {
        String result = "";
        if (!isNull(value)) {
            result = value.replaceAll("&amp;", "&").replaceAll("&gt;", ">").replaceAll("&lt;", "<").replaceAll("&quot;", "\"").replace("&nbsp;", " ");
        }
        return result;
    }

    /** 字符串编码(默认使用UTF-8) */
    public static String stringEncode(String value){
        return stringEncode(value,"UTF-8");
    }

    public static String stringEncode(String value,String encoding){
        String result = null;
        if (!isNull(value)) {
            try {
                if (isNull(encoding)) {
                    encoding = "UTF-8";
                }
                result = new String(value.getBytes("ISO-8859-1"),encoding);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 格式式化字符串
     * 允许使用{0}{1}...作为为占位符
     * @param value 要格式化的字符串
     * @param args 点位符的值
     * @return 格式化后的字符串
     */
    public static String stringFormat(String value, Object... args) {
        // 判断是否为空
        if (isNull(value) || isNull(args)) {
            return value;
        }
        String result = value;
        Pattern p = Pattern.compile("\\{(\\d+)\\}");
        Matcher m = p.matcher(value);
        while (m.find()) {
            // 获取{}里面的数字作为匹配组的下标取值
            int index = Integer.parseInt(m.group(1));
            // 这里得考虑数组越界问题，{1000}也能取到值么？？
            if (index < args.length) {
                // 替换，以{}数字为下标，在参数数组中取值
                result = result.replace(m.group(), args[index].toString());
            }
        }
        return result;
    }

    public static String leftPad(String value, int len, char c) {
        if (isNull(value, len, c)) {
            return value;
        }
        int v = len - value.length();
        for (int i = 0; i < v; i++)
            value = c + value;
        return value;
    }

    public static String rightPad(String value, int len, char c) {
        if (isNull(value, len, c)) {
            return value;
        }
        int v = len - value.length();
        for (int i = 0; i < v; i++)
            value += c;
        return value;
    }

    /** 处理对象的String类型的属性值进行html编码 */
    public static void objectHtmlEncode(Object object) {
        if (!isNull(object)) {
            Method[] mList = object.getClass().getMethods();
            for (Method method : mList) {
                // 方法名
                String mName = method.getName();
                // 得到方法的方法值类型
                String mRetrunType = method.getReturnType().getSimpleName();
                // 得到方法的参数个数
                int mParamSize = method.getParameterTypes().length;
                // 判断方法值是否是String并参数个数为0
                if (mRetrunType.equals("String") && mParamSize == 0) {
                    // 判断方法是否是以get开头
                    if (mName.startsWith("get")) {
                        // 得到相对应的set方法
                        Method setMethod = null;
                        String setMethodName = "set" + mName.substring(3);
                        // 只有一个String类型的参数
                        Class[] paramClass = { String.class };
                        try {
                            setMethod = object.getClass().getMethod(setMethodName, paramClass);
                            // 判断set方法的返回值是否为空
                            if (!setMethod.getReturnType().getSimpleName().equals("void")) {
                                continue; // 查看下一个方法
                            }
                        } catch (SecurityException e) {
                            continue; // 查看下一个方法
                        } catch (NoSuchMethodException e) {
                            continue; // 查看下一个方法
                        }
                        Object[] params = null;
                        try {
                            // 得到方法的值
                            String mValue = method.invoke(object, params).toString();
                            // 对值进行html编码
                            mValue = htmlEncode(mValue);
                            // 编码后重新赋值
                            params = new Object[] { mValue };
                            setMethod.invoke(object, params);
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /** 根据属性名得到属性值(entity中必需存在get,set相应方法) */
    public static Object getPropValue(Object entity,String propName){
        Object result = null;
        //判断对象和属性名是否为空
        if (isNull(entity)||isNull(propName)) {
            return result;
        }else{
            try {
                //调用方法得到get方法值
                Method getMethod = entity.getClass().getMethod(propName.trim());
                result = getMethod.invoke(entity	);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /** 得到gson对象 */
//	public static Gson getGson(){
//		return gson;
//	}

    /** 生成随机UUID */
    public static String genUUID(){
        UUID uuid = UUID.randomUUID();
        String temp = uuid.toString();
        return temp.replaceAll("-", "").toUpperCase();
    }

    /** 随机数字 */
    public static String getRandNum(int len) {
        String result = "";
        String temp = genUUID().replaceAll("[A-Z]+", "");

        int tempLen = temp.length();
        if (len > 32) {
            len = 32;
        }
        if (tempLen < len) {
            result = rightPad(temp, len, '0');
        } else {
            for (int i = 0; i < len; i++) {
                int rnd = new Random().nextInt(tempLen);
                result += temp.charAt(rnd);
            }
        }

        return result;
    }


    /** 保存文件 */
    public static void saveToFile(File target, String distPath) throws IOException {
        if (isNull(target,distPath)) {
            return;
        }
        File distFile = new File(distPath);
        //确保文件所在的文件夹都存在
        distFile.getParentFile().mkdirs();

        //输入流
        InputStream is = new BufferedInputStream(new FileInputStream(target));
        //输出流
        OutputStream os = new BufferedOutputStream(new FileOutputStream(distFile));
        //每次读取的大小
        byte[] size = new byte[1024];
        //流长度
        int len = 0;
        //循环读取
        while((len = is.read(size)) != -1){
            os.write(size, 0, len);
        }
        os.flush();
        os.close();
        is.close();
    }

    public static void saveToFile(InputStream target, String distPath) throws IOException {
        if (isNull(target,distPath)) {
            return;
        }
        File distFile = new File(distPath);
        //确保文件所在的文件夹都存在
        distFile.getParentFile().mkdirs();

        //输入流
        InputStream is = new BufferedInputStream(target);
        //输出流
        OutputStream os = new BufferedOutputStream(new FileOutputStream(distFile));
        //每次读取的大小
        byte[] size = new byte[1024];
        //流长度
        int len = 0;
        //循环读取
        while((len = is.read(size)) != -1){
            os.write(size, 0, len);
        }
        os.flush();
        os.close();
        is.close();
    }

    /** 得到Cookie */
    public static Cookie getCookie(String name, HttpServletRequest request){
        Cookie[] cks = request.getCookies();
        if(cks != null){
            for(Cookie cookie : cks){
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }

        return null;
    }

    /** 创建Cookie */
    public static void setCookie(String name, String value, Integer maxAge, HttpServletResponse response){
        Cookie cookie =new Cookie(name,value);
        if (!isNull(maxAge)) {
            cookie.setPath("/");
            cookie.setMaxAge(maxAge.intValue());
        }
        response.addCookie(cookie);
    }

    /** 创建Cookie */
    public static void setCookie(String name, String value, HttpServletResponse response){
        setCookie(name, value, null, response);
    }

    /** 删除Cookie */
    public static void delCookie(String name, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /** 输出JSON数据到流中 */
    public static void outJson(Object target, HttpServletResponse response) throws IOException{
        PrintWriter out = response.getWriter();
//		out.write(gson.toJson(target));
        out.flush();
        out.close();
    }

    /** 得到对象的字符串 */
    public static String getStr(Object obj){
        if(isNull(obj)){
            return "";
        }else{
            if (obj.getClass().isArray()) {
                //处理数组
//				return gson.toJson(obj);
            }else if(obj instanceof Map){
                //处理Map对象
                return obj.toString();
            }

            return obj.toString();
        }
    }

    /** 使用正则表达查询字符串 */
    public static List<String> findStr(Object target, String regex){
        if(isNull(target,regex)){
            return null;
        }
        Pattern pattern = Pattern.compile(regex);		//正则表达式
        Matcher matcher = pattern.matcher(target.toString());	//操作的字符串
        List<String> tmp = new ArrayList<String>();
        while (matcher.find()) {
            tmp.add(matcher.group());
        }
        return tmp;
    }



    /**
     * 直接删除非空目录
     * @param dir File对象
     */
    public static void deleteDirectory(File dir){
        if(dir == null || !dir.exists() || !dir.isDirectory())
            return; // 检查参数
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirectory(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    /**
     * 直接删除非空目录
     * @param dirPath 要删除的目录的绝对路径
     */
    public static void deleteDirectory(String dirPath){
        File dir = new File(dirPath);
        deleteDirectory(dir);
    }

    /**
     * 使用Spring上传文件
     * @param request 包含上传文件的request对象
     * @param fileField 文件域的名字(默认：file)
     * @param fileDir 上传文件储存的目录(默认：upload)
     * @param types 允许上传的文件类型(默认为所有)
     * @return 上传后生成的文件名
     */
    public static String uploadFile(DefaultMultipartHttpServletRequest request, String fileField, String fileDir, String[] types){
        String file_name = "";
        // 文件上传
        CommonsMultipartFile file = (CommonsMultipartFile) request.getFile(isNull(fileField) ? "file" : fileField);
        if (file != null && !file.isEmpty()) {
            //对文件类型的判断
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1).toLowerCase();
            if (isNotNull(types)) {
                boolean flag = false;
                for (String type : types) {
                    if (type.toLowerCase().equals(suffix)) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    return null;
                }
            }
            //判断结束

            file_name = System.currentTimeMillis() + "." + suffix;
            //文件名
            String file_dir = request.getSession().getServletContext().getRealPath(isNull(fileDir) ? "upload" : fileDir) + File.separator;
            //保存到本地
            File fFile = new File(file_dir);		//文件的目录
            File uFile = new File(fFile,file_name);		//上传的文件
            try {
                fFile.mkdirs();
                FileCopyUtils.copy(file.getBytes(), uFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //返回结果
        return file_name;
    }

    /**
     * 用途：改变图片尺寸
     *
     * @param oldUrl 原图片文件绝对路径（含文件名）
     * @param newUrl 目标图片文件绝对路径（含文件名）
     * @param width 目标图片文件宽度
     * @param height 目标图片文件高度
     * @param proportion 是否等比例缩放
     * @return
     * @throws Exception
     *
     * 此方法支持的图片文件格式有：jpg、gif、png
     * 不支持的图片文件格式有：bmp
     * 其它图片文件格式未测试
     */
    public static boolean resizeImg(String oldUrl, String newUrl, int width, int height, boolean proportion) throws Exception {
        File fileIn = new File(oldUrl);
        File fileOut = new File(newUrl);
        FileOutputStream tempout = null;
        try {
            tempout = new FileOutputStream(fileOut);
        } catch (Exception ex) {
            throw ex;
        }
        Image img = null;
        Applet app = new Applet();
        MediaTracker mt = new MediaTracker(app);
        try {
            img = ImageIO.read(fileIn);
            mt.addImage(img, 0);
            mt.waitForID(0);
        } catch (Exception e) {
            throw e;
        }

        int old_w = img.getWidth(null);
        int old_h = img.getHeight(null);
        if (old_w == -1) {
            return false;
        } else if (height <= 0 && width <= 0) {
            return false;
        } else {
            int new_w;
            int new_h;
            if (height <= 0) {
                new_w = width;
                new_h = (int) Math.round(1.0 * new_w * old_h / old_w);
            } else if (width <= 0) {
                new_h = height;
                new_w = (int) Math.round(1.0 * new_h * old_w / old_h);
            } else if (proportion == true) {//判断是否等比例缩放
                //计算比率
                double rate1 = 1.0 * old_w / width;
                double rate2 = 1.0 * old_h / height;
                if (rate1 > rate2) {
                    new_w = width;
                    new_h = (int) Math.round(1.0 * new_w * old_h / old_w);
                } else {
                    new_h = height;
                    new_w = (int) Math.round(1.0 * new_h * old_w / old_h);
                }
            } else {
                new_w = width;
                new_h = height;
            }

            BufferedImage buffImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
            Graphics g = buffImg.createGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, new_w, new_h);
            g.drawImage(img, 0, 0, new_w, new_h, null);
            g.dispose();
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(tempout);
            try {
                encoder.encode(buffImg);
                tempout.close();
            } catch (IOException ex) {
                throw ex;
            }
        }
        return true;
    }


    /**
     * 用途：改变图片尺寸
     * @paramoldUrl 远程文件url
     * @param newUrl 目标图片文件 绝对路径（含文件名）
     * @param width 目标图片文件宽度
     * @param height 目标图片文件高度
     * @param proportion 是否等比例缩放
     * @return
     * @throws Exception
     *
     * 此方法支持的图片文件格式有：jpg、gif、png
     * 不支持的图片文件格式有：bmp
     * 其它图片文件格式未测试
     */
    public static boolean resizeImg2(String imgurl, String newUrl, int width, int height, boolean proportion) throws Exception {
        //        System.out.println("存放的路径==="+newUrl);

//    	 URL url = 	new URL(imgurl);
//    	 HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
//    	 DataInputStream in = new DataInputStream(connection.getInputStream());
//    	
//    	 DataOutputStream out = new DataOutputStream(new FileOutputStream(newUrl));
//    	
//    	 byte[] buffer = new byte[4096]; 
//    	 int count = 0;  
//         while ((count = in.read(buffer)) > 0)/* 将输入流以字节的形式读取并写入buffer中 */  
//         {  
//              out.write(buffer, 0, count);  
//          }
//         out.close(); 
//         in.close();  
//         connection.disconnect(); 

//       System.out.println("/************保存图片完成,压缩开始**************/"); 
        URL url2 = new URL(imgurl);
        BufferedImage src = ImageIO.read(url2);
        //  HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
        //  DataInputStream in2 = new DataInputStream(connection2.getInputStream());
        //  BufferedImage src = javax.imageio.ImageIO.read(in);
        int old_w = src.getWidth(null);
        int old_h = src.getHeight(null);
        if (old_w == -1) {
            return false;
        } else if (height <= 0 && width <= 0) {
            return false;
        } else {
            int new_w;
            int new_h;
            if (height <= 0) {
                new_w = width;
                new_h = (int) Math.round(1.0 * new_w * old_h / old_w);
            } else if (width <= 0) {
                new_h = height;
                new_w = (int) Math.round(1.0 * new_h * old_w / old_h);
            } else if (proportion == true) {//判断是否等比例缩放
                //计算比率
                double rate1 = 1.0 * old_w / width;
                double rate2 = 1.0 * old_h / height;
                if (rate1 > rate2) {
                    new_w = width;
                    new_h = (int) Math.round(1.0 * new_w * old_h / old_w);
                } else {
                    new_h = height;
                    new_w = (int) Math.round(1.0 * new_h * old_w / old_h);
                }
            } else {
                new_w = width;
                new_h = height;
            }

            BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(src,0,0,new_w,new_h,null);
            g.dispose();
            File f = new File(newUrl);
            ImageIO.write(tag, "jpeg", f);
        }
        return true;
    }


    /**
     * 保存图片
     * @param imgurl
     * @param newurl
     * @return
     * @throws Exception
     */
    public static boolean savePicmsg(String imgurl,String newurl)throws Exception{
        URL url2 = new URL(imgurl);
        BufferedImage src = ImageIO.read(url2);
        int old_w = src.getWidth(null);
        int old_h = src.getHeight(null);
        BufferedImage tag = new BufferedImage(old_w, old_h, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.drawImage(src,0,0,old_w,old_h,null);
        g.dispose();
        File f = new File(newurl);
        f.getParentFile().mkdirs();
        ImageIO.write(tag, "jpeg", f);
        return true;
    }

    /** 隐藏部分验证码数据 */
    public static String hideCode(String isHide, String target){
        // 0.不隐藏 1.隐藏
        if (!"0".equals(isHide)) {
            return target;
        }

        int code_len = 0;
        if (target != null) {
            target = target.replaceAll("\\s", "");
            code_len = target.length();
        }else{
            return "";
        }

        if ( code_len > 8) {
            String s1 = target.substring(0, 4);
            String s2 = target.substring(4, code_len-4).replaceAll("\\w", "*");
            String s3 = target.substring(code_len-4, code_len);
            if(s2.length()>6){
                s2 = s2.substring(0,6);
            }
            String code = s1+s2+s3;

            return code;
        }else{
            return target.replaceAll("\\w", "*");
        }
    }

    /** 隐藏部分验证码数据 */
    public static String hideCode(String target){
        return hideCode("0", target);
    }

    /** 月的第一天 */
    public static Date getMonthFirstDay(int i) {
        // i = 0 是本月 1 上一月
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -i);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }
    /** 月的最后一天 */
    public static Date getMonthLastDay(int i) {

        // i = 0 是本月 1 上一月
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -i);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.roll(Calendar.DAY_OF_MONTH, -1);

        return c.getTime();
    }

    /**连接到登录接口并获取数据*/
    public static String getLoginInfo(String urlStr) {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
//                    connection.setConnectTimeout(15000);
//                    connection.setReadTimeout(15000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            //connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.connect();
//                    DataOutputStream out = new DataOutputStream(connection.getOutputStream()); 
//                    out.write(content.getBytes("utf-8")); //用utf-8的编码方式传递参数，否则中文会出现乱码
//                    out.flush(); 
//                    out.close(); 
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }



    /** 通过文件相对路径得到Web项目下的绝对路径 */
    public static String getPath(String path){
        URL url = CommonUtil.class.getResource(path);
        if (url != null) {
            String abs_path = CommonUtil.class.getResource(path).toString();
            abs_path = abs_path.substring(abs_path.indexOf("/")+1);
            return abs_path;
        }
        return null;
    }

    /** placeid1是否包含placeid2 */
    public static boolean containsPlaceid(Object placeid1, Object placeid2){
        String id1 = getStr(placeid1);
        String id2 = getStr(placeid2);

        if (isNotNull(id1) && !id1.startsWith(";")) {
            id1 = ";"+id1;
        }
        if (isNotNull(id1) && !id1.endsWith(";")) {
            id1 += ";";
        }
        if (isNotNull(id2) && !id2.startsWith(";")) {
            id2 = ";"+id2;
        }
        if (isNotNull(id2) && !id2.endsWith(";")) {
            id2 += ";";
        }
        //不包含
        if(CommonUtil.isNotNull(id1,id2) && !id1.contains(id2)){
            return false;
        }
        //包含
        return true;
    }


    /**
     * 获取当前时间字符串  如:201212061456123
     * @return
     */
    public static String get14CurrentDateTimeStr() {
        Calendar currTime = Calendar.getInstance();
        String year = String.valueOf(currTime.get(Calendar.YEAR));
        String month = String.valueOf(currTime.get(Calendar.MONTH) + 1);
        String day = String.valueOf(currTime.get(Calendar.DATE));
        String hour = String.valueOf(currTime.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(currTime.get(Calendar.MINUTE));
        String second = String.valueOf(currTime.get(Calendar.SECOND));
        StringBuffer temp = new StringBuffer();

        temp.append(year);
        if (month.length() == 1)
            temp.append("0");
        temp.append(month);
        if (day.length() == 1) {
            temp.append("0");
        }
        temp.append(day);
        if (hour.length() == 1) {
            temp.append("0");
        }
        temp.append(hour);
        if (minute.length() == 1) {
            temp.append("0");
        }
        temp.append(minute);
        if (second.length() == 1) {
            temp.append("0");
        }
        temp.append(second);

        return temp.toString();
    }

    /**
     * 获取项目物理路径
     */
    public static String getProjectPath(){
        String path = CommonUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        if(path.indexOf("WEB-INF")>0){
            path = path.substring(0,path.indexOf("/WEB-INF"));
        }
        if(!path.endsWith("/")){
            path = path+"/";
        }
        if(path.startsWith("/")){
            path = path.substring(1);
        }
        return path;
    }



    /**
     * 合并路径
     * @param path1
     * @param path2
     * @return
     */
    public static String addPath(String path1, String path2) {
        String path = "";
        if (CommonUtil.isNotNull(path1) && CommonUtil.isNotNull(path2)) {
            if (path1.endsWith("/") && path2.startsWith("/")) {
                path = path1 + path2.substring(1);
            } else if (path1.endsWith("/") || path2.startsWith("/")) {
                path = path1 + path2;
            } else {
                path = path1 + "/" + path2;
            }
        } else {
            if (CommonUtil.isNull(path1)) {
                return path2;
            } else if (CommonUtil.isNull(path2)) {
                return path1;
            }
        }
        return path;
    }


    // 测试
    public static void main(String[] args) throws Exception {
        String 	id1 = "11;12";
        Long 	id2 = 1L;
        System.out.println(containsPlaceid(id1, id2));
    }

    /** 检查两个日期是否是同一天 */
    public static boolean isSameDay(Date date1,Date date2){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return (cal1.get(cal1.YEAR) == cal2.get(cal2.YEAR) && cal1.get(cal1.DAY_OF_YEAR) == cal2.get(cal2.DAY_OF_YEAR));
    }


    /**
     * @desc: 对象转二进制数组
     * @param obj
     * @return
     * @auther: 陈军
     * @mail：chenjun@hyxt.com
     * @date: 2014-5-20 下午02:33:28
     */
    public static byte[] objectToByte(Object obj) {
        byte[] byteArray = null;
        if (obj != null) {
            ByteArrayOutputStream byteos = new ByteArrayOutputStream();
            ObjectOutputStream objos = null;
            try {
                objos = new ObjectOutputStream(byteos);
                objos.writeObject(obj);
                byteArray = byteos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeStream(objos);
                closeStream(byteos);
            }
        }
        return byteArray;
    }

    /**
     * @desc: 二进制数组转对象
     * @param bytes
     * @return
     * @throws Exception
     * @auther: 陈军
     * @mail：chenjun@hyxt.com
     * @date: 2014-5-20 下午02:33:50
     */
    public static Object bytesToObject(byte[] bytes)throws Exception{
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream sln = new ObjectInputStream(in);
        return sln.readObject() ;
    }

    /**
     * @desc: 关闭流
     * @param stream
     * @auther: 陈军
     * @mail：chenjun@hyxt.com
     * @date: 2014-5-20 下午02:34:03
     */
    private static void closeStream(Object stream) {
        if (stream != null) {
            try {
                if (stream instanceof InputStream) {
                    ((InputStream) stream).close();
                } else if (stream instanceof OutputStream) {
                    ((OutputStream) stream).close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将姓名电话号码等字符串用*代替一部分
     * @param str
     * @return
     */
    public static String replaceChar(String str){
        if(null == str)
            return "";
        int count = str.length();
        if(count == 1)
            return str+="*";
        StringBuffer strb = new StringBuffer(str);
        if(count == 2)
            strb.replace(1, 2, "*");
        if(count == 3)
            strb.replace(1, 2, "*");
        if(count > 3 && count < 9)
            strb.replace(1, count-1, "***");
        if(count > 9 && count < 11)
            strb.replace(3, count-3, "****");
        if(count >= 11)
            strb.replace(3, count-4, "*****");
        return strb.toString();
    }
    /**
     * 解密字段
     * @param value  值
     * @param key    密钥
     * @return
     */
    public static byte[] decryptField(byte[] value, String key) {
        if (value == null || value.length <= 0 || CommonUtil.isNull(key))
            return value;

        byte[] result = null;
        try {
            String algorithm = "DESede/CBC/PKCS5Padding";
            SecretKey seckey = new SecretKeySpec(key.getBytes("UTF-8"), "DESede");
            Cipher cipher = Cipher.getInstance(algorithm);
            IvParameterSpec spec = new IvParameterSpec(APP_ENC_IV);
            cipher.init(Cipher.DECRYPT_MODE, seckey, spec);
            result = cipher.doFinal(value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }




    /////////////////////////////2015-01-28//////////////////////////////
    //获取前num天的日期
    public static Date  getDateSpan(int num){
        // 初始化日历对象
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) -num);
        //cal.getTime();
        // String datespan =  CommonUtil.dateToDateStr("yyyy-MM-dd", cal.getTime());
        return cal.getTime();
    }

    /**
     * 获取指定日期前num数的日期对
     * @param date
     * @param num
     * @return
     */
    public static List<Map<String, Object>> getDateQueryList(Date date,int num){
        List<Map<String, Object>> dateQueryList = new ArrayList<Map<String, Object>>();
        Date tmpBeginTime = date;
        for (int i = 0; i < num; i++) {
            Map<String, Object> dateQuery = new HashMap<String, Object>();
            // 创建日历对象
            Calendar c = Calendar.getInstance();
            c.setTime(tmpBeginTime);
            // 开始时间
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 0, 0, 0);
            dateQuery.put("beginTime", c.getTime()); // 开始时间
            // 结束时间
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 23, 59, 59);
            dateQuery.put("endTime", c.getTime()); // 结束时间
            // 天数减1
            tmpBeginTime = CommonUtil.addDate(tmpBeginTime, 1);
            // 添加
            dateQueryList.add(dateQuery);
        }
        return dateQueryList;
    }

    /**
     * 根据日期获取时间断内的日期数字
     * @param dBegin
     * @param dEnd
     * @return
     */
    public static  List<Map<String, Object>> findDates(Date dBegin, Date dEnd,String flag) {
        List<Map<String, Object>> dateQueryList = new ArrayList<Map<String, Object>>();
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        if(!flag.equals("1")){
            while (dEnd.after(calBegin.getTime())||isSameDay(dEnd,calBegin.getTime())) {
                Map<String, Object> dateQuery = new HashMap<String, Object>();
                // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
                // 开始时间
                calBegin.set(calBegin.get(Calendar.YEAR), calBegin.get(Calendar.MONTH), calBegin.get(Calendar.DATE), 0, 0, 0);
                dateQuery.put("beginTime", calBegin.getTime()); // 开始时间
                // 结束时间
                calBegin.set(calBegin.get(Calendar.YEAR), calBegin.get(Calendar.MONTH), calBegin.get(Calendar.DATE), 23, 59, 59);
                dateQuery.put("endTime", calBegin.getTime()); // 结束时间
                dateQueryList.add(dateQuery);
                calBegin.add(Calendar.DAY_OF_MONTH, 1);
            }
        }else{
            for(int i=0 ; i< 24; i++){
                Map<String, Object> dateQuery = new HashMap<String, Object>();
                Calendar nowBegin = Calendar.getInstance();
                nowBegin.setTime(new Date());
                nowBegin.set(nowBegin.get(Calendar.YEAR), nowBegin.get(Calendar.MONTH), nowBegin.get(Calendar.DATE), i, 0, 0);
                dateQuery.put("beginTime", nowBegin.getTime()); // 开始时间
                // 结束时间
                nowBegin.set(nowBegin.get(Calendar.YEAR), nowBegin.get(Calendar.MONTH), nowBegin.get(Calendar.DATE), i, 59, 59);
                dateQuery.put("endTime", nowBegin.getTime()); // 结束时间
                dateQueryList.add(dateQuery);
            }
        }
        return dateQueryList;
    }

    /**
     * 字符串拼接
     * @param str
     * @return
     */
    public static String strJoint (String str) {
        StringBuffer sbStr = new StringBuffer("");
        String newStr =  "";
        if (isNotNull(str)) {
            String[] list = str.split(",");
            for (int i = 0; i < list.length; i++) {
                sbStr.append("'").append(list[i]).append("',");
            }
            if (isNotNull(sbStr.toString())) {
                newStr = sbStr.substring(0,sbStr.length()-1);
            }
        }
        return newStr;
    }

    public static String getProvinceName(String provinceName) {
        String str = "";
        if ("北京".equals(provinceName) || "上海".equals(provinceName) || "天津".equals(provinceName) || "重庆".equals(provinceName)) {
            str = "";
        } else {
            str = provinceName + "省";
        }
        return str;
    }




    /**
     * 进行加法运算
     * @param d1
     * @param d2
     * @return
     */
    public static double add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.add(b2).doubleValue();
    }

    /**
     * 进行减法运算
     * @param d1
     * @param d2
     * @return
     */
    public static double sub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 进行乘法运算
     * @param d1
     * @param d2
     * @return
     */
    public static double mul(double d1, double d2){
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 进行除法运算
     * @param d1
     * @param d2
     * @param len
     * @return
     */
    public static double div(double d1,double d2,int len) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2,len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 进行四舍五入操作
     * @param d
     * @param len
     * @return
     */
    public static double round(double d,int len) {
        BigDecimal b1 = new BigDecimal(d);
        BigDecimal b2 = new BigDecimal(1);
        // 任何一个数字除以1都是原数字
        // ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作
        return b1.divide(b2, len,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
