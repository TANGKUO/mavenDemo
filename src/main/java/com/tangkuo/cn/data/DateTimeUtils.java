package com.tangkuo.cn.data;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;


public class DateTimeUtils extends DateUtils {
	
	public static final String UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";	
	
	public static String formatUTC(Date date) {
		if(date == null) {
			return null;
		}
		return DateFormatUtils.format(date, UTC_FORMAT);
	}
	
	/**
	 * 得到未来最近的日期
	 * @param hourMinSec 格式：小时:分:秒
	 * @return
	 */
	public static Calendar getNearCalendarByDay(String hourMinSec) {
		String[] times = StringUtils.split(hourMinSec, ':');		
	
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(times[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(times[1]));
		calendar.set(Calendar.SECOND, Integer.parseInt(times[2]));
		
		if(calendar.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
			int day = calendar.get(Calendar.DAY_OF_MONTH)+1;
			calendar.set(Calendar.DAY_OF_MONTH, day);
		}
		
		return calendar;
	}	
	//当天开始时间
	public static Date getDayStart() {
		Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。   
		cal.add(Calendar.DAY_OF_YEAR, -1);//取当前日期的前一天. 
		return cal.getTime();
	}
	//当天结束时间
	public static Date getDayEnd() {
		return new Date();
	}
	//当前时间往前一周
	public static Date getWeekStart() {
		Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。   
		cal.add(Calendar.WEEK_OF_YEAR, -1);//取当前日期的前一周. 
		return cal.getTime();
	}

	// 当前时间往前一月
	public static Date getMonthStart() {
		Calendar cal = Calendar.getInstance();// 使用默认时区和语言环境获得一个日历。
		cal.add(Calendar.MONTH, -1);// 取当前日期的前一月.
		return cal.getTime();
	}

	// 当前时间往后一月
	public static Date getMonthEnd() {
		Calendar cal = Calendar.getInstance();// 使用默认时区和语言环境获得一个日历。
		cal.add(Calendar.MONTH, 1);// 取当前日期的后一月.
		return cal.getTime();
	}

	//设定时间
	public static String getDayCustomer(int calendar,int step,String format){
		Calendar cal = Calendar.getInstance();   
		cal.add(calendar, step);
		return DateFormatUtils.format(cal.getTime(), format);
	}
	
	//格式时间
	public static Date getDate(String source,String... format) throws ParseException{
		return parseDate(source,format);
	}
	
	//step步长，flag为true初false尾，format格式 
	public static String getCurrentWeek(int step,boolean flag,String format){
		Calendar calendar = Calendar.getInstance();			
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.add(Calendar.WEEK_OF_YEAR, step);
		if(!flag){
			calendar.add(Calendar.DAY_OF_YEAR,6);
		}
        return DateFormatUtils.format(calendar.getTime(), format);
	}
	
	//step步长，flag为true初false尾，format格式 
	public static String getCurrentMonth(int step,boolean flag,String format){
		Calendar calendar = Calendar.getInstance();     
		calendar.add(Calendar.MONTH, step);
		if(flag){
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		}else{
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
        return DateFormatUtils.format(calendar.getTime(), format);
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(getCurrentMonth(-1,true,"yyyy-MM-dd 00:00:00"));
    	System.out.println(getCurrentMonth(-1,false,"yyyy-MM-dd HH:mm:ss"));
    	
    	System.out.println(getCurrentWeek(-1,true,"yyyy-MM-dd 00:00:00"));
    	System.out.println(getCurrentWeek(-1,false,"yyyy-MM-dd HH:mm:ss"));
	}
}
