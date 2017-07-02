package com.tangkuo.cn.pay.kmtk.netbank.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.httpclient.util.DateParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangkuo.cn.pay.kmtk.netbank.common.TtyException;


/**
 * 时间工具类
 * <p>
 * 提供了除{@link org.apache.commons.lang3.time.DateUtils}工具类之外的一些对时间的操作
 * 
 */
public class DateUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(DateUtil.class);

	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	public static final String YYYY1MM1DD = "yyyy/MM/dd";

	public static final String HH1MM1SS = "HH:mm:ss";

	/** 默认的时间样式 */
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static final String YYYYMMDD = "yyyyMMdd";

	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	// 年月日时分表
	public static final String DATA_FORMAT_PATTERN = "yyyyMMddHHmmss";

	// 时分秒
	public static final String DATA_FORMAT_PATTERN_3 = "HHmmss";

	// 年月
	public static final String YYYY_MM = "yyyy-MM";
	// 年月
	public static final String YYYYMM = "yyyyMM";
	/**
	 * 根据指定时间和样式格式化时间
	 * <p>
	 * 如：2014-04-21 21:20:10, yyyy-MM-dd -> 2014-04-21
	 * 
	 * @param date
	 *            指定时间
	 * @param pattern
	 *            指定样式
	 * @return 格式化时间
	 * @see java.text.SimpleDateFormat
	 */
	public static String format(Date date, String pattern) {
		if(date == null){
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	/**
	 * 按照默认样式的格式化指定时间
	 * <p>
	 * 如：2014-04-21 21:20:10 -> 2014-04-21 21:20:10
	 * 
	 * @param date
	 *            指定时间
	 * @return 格式化时间
	 * @see #format(Date, String)
	 * @see #YYYY_MM_DD_HH_MM_SS
	 */
	public static String format(Date date) {
		if (date == null) {
			return "";
		}
		return format(date, YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 按照默认样式的格式获取当前系统时间的前一天
	 * <p>
	 * 如：2014-04-21 21:20:10 -> 2014-04-21 21:20:10
	 * 
	 * @param date
	 *            指定时间
	 * @return 格式化时间
	 * @see #getYesterdayDateFormatSt()
	 * @see #YYYYMMDD
	 */
	public static String getYesterdayDateFormatSt() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1); // 得到前一天
		Date date = calendar.getTime();
		DateFormat df = new SimpleDateFormat(YYYYMMDD);
		return df.format(date);
	}
	//获得当前时间的前后  ?  天
	public static String getDate(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		Date date = calendar.getTime();
		DateFormat df = new SimpleDateFormat(YYYYMMDD);
		return df.format(date);
	}
	
	public static String getDateStrByLong(long time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    Long val = new Long(time);  
	    String d = format.format(val);  
	    return d;
	}
	public static Date getDateByLong(long time) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Long val = new Long(time);  
		String d = format.format(val);  
		Date date=format.parse(d);  
		return date;
	}

	/**
	 * 根据格式化时间和样式解析时间
	 * <p>
	 * 如：2014-04-21, yyyy-MM-dd -> 2014-04-21 00:00:00
	 * 
	 * @param source
	 *            格式化时间
	 * @param pattern
	 *            样式
	 * @return 时间
	 * @throws DateParseException
	 *             格式化出现异常时抛出
	 * @see java.text.SimpleDateFormat
	 */
	public static Date parse(String source, String pattern) {
		if(StringUtils.isEmpty(source)){
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(source);
		} catch (ParseException e) {
			throw new TtyException(e);
		}
	}

	/**
	 * 根据格式化时间、样式和默认时间解析时间
	 * <p>
	 * 若解析出现异常，则返回默认时间
	 * 
	 * @param source
	 *            格式化时间
	 * @param pattern
	 *            样式
	 * @param defaultValue
	 *            默认时间
	 * @return 时间
	 * @see java.text.SimpleDateFormat
	 */
	public static Date parse(String source, String pattern, Date defaultValue) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(source);
		} catch (ParseException e) {
			return defaultValue;
		}
	}

	/**
	 * 得到当前时间的前后日期
	 * 
	 * @param date
	 *            当前时间
	 * @param pattern
	 *            时间格式
	 * @param step
	 *            -1为当前时间后一天 +1为当前时间前一天
	 * @return
	 */
	public static String format(Date date, String pattern, int step) {
		java.util.Calendar rightNow = java.util.Calendar.getInstance();
		java.text.SimpleDateFormat sim = new java.text.SimpleDateFormat(pattern);
		rightNow.add(java.util.Calendar.DAY_OF_MONTH, step);
		return sim.format(rightNow.getTime());
	}

	/**
	 * 时间相减得到间隔天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		if(StringUtils.isEmpty(beginDateStr) || StringUtils.isEmpty(endDateStr)){
			return 0;
		}
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		java.util.Date beginDate;
		java.util.Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime())
					/ (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day + 1;
	}

	/**
	 * 根据格式化时间和默认样式解析时间
	 * <p>
	 * 如：2014-04-21 21:20:10, yyyy-MM-dd HH:mm:ss -> 2014-04-21 21:20:10
	 * 
	 * @param source
	 *            格式化时间
	 * @return 时间
	 * @throws DateParseException
	 *             格式化出现异常时抛出
	 * @see #parse(String, String)
	 * @see #YYYY_MM_DD_HH_MM_SS
	 */
	public static Date parse(String source) {
		return parse(source, YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 根据格式化时间、默认样式和默认时间解析时间
	 * <p>
	 * 若解析出现异常，则返回默认时间
	 * 
	 * @param source
	 *            格式化时间
	 * @param defaultValue
	 *            默认时间
	 * @return 时间
	 * @see #parse(String, String, Date)
	 * @see #YYYY_MM_DD_HH_MM_SS
	 */
	public static Date parse(String source, Date defaultValue) {
		return parse(source, YYYY_MM_DD_HH_MM_SS, defaultValue);
	}

	/**
	 * 获取指定日期的起始时间
	 * <p>
	 * 如：2014-04-21 21:20:10 -> 2014-04-21 00:00:00
	 * 
	 * @param date
	 *            指定日期
	 * @return 起始时间
	 * @see java.util.Calendar
	 */
	public static Date getStartOfDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取当前日期的起始时间
	 * <p>
	 * 如：2014-04-21 21:20:10 -> 2014-04-21 00:00:00
	 * 
	 * @return 当前日期的起始时间
	 * @see #getStartOfDate(Date)
	 */
	public static Date getStartOfToDay() {
		return getStartOfDate(new Date());
	}

	/**
	 * 根据指定时间和一周的第几天获取指定日期当周几的时间
	 * <p>
	 * 如：2014-04-21 21:20:10, {@link java.util.Calendar#TUESDAY} -> 2014-04-22
	 * 21:20:10
	 * 
	 * @param date
	 *            指定时间
	 * @param dayOfWeek
	 *            一周的第几天，{@link java.util.Calendar#DAY_OF_WEEK}
	 * @return 指定时间当周几的时间
	 * @see java.util.Calendar
	 */
	public static Date getDateOfWeek(Date date, int dayOfWeek) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		return calendar.getTime();
	}

	/**
	 * 根据一周的第几天获取当前时间的周几的时间
	 * <p>
	 * 如：2014-04-21 21:20:10, {@link java.util.Calendar#TUESDAY} -> 2014-04-22
	 * 21:20:10
	 * 
	 * @param dayOfWeek
	 *            一周的第几天，{@link java.util.Calendar#DAY_OF_WEEK}
	 * @return 指定日期当周几的时间
	 * @see #getDateOfWeek(Date, int)
	 */
	public static Date getDateOfWeek(int dayOfWeek) {
		return getDateOfWeek(new Date(), dayOfWeek);
	}

	/**
	 * 根据指定日期和一周的第几天获取指定日期当周几的起始时间
	 * <p>
	 * 如：2014-04-21 21:20:10, {@link java.util.Calendar#TUESDAY} -> 2014-04-22
	 * 00:00:00
	 * 
	 * @param date
	 *            指定日期
	 * @param dayOfWeek
	 *            一周的第几天，{@link java.util.Calendar#DAY_OF_WEEK}
	 * @return 指定日期当周几的起始时间
	 * @see #getDateOfWeek(Date, int)
	 * @see #getStartOfDate(Date)
	 */
	public static Date getStartOfWeekDate(Date date, int dayOfWeek) {
		Date dateOfWeek = getDateOfWeek(date, dayOfWeek);
		return getStartOfDate(dateOfWeek);
	}

	/**
	 * 根据一周的第几天获取本周几的起始时间
	 * <p>
	 * 如：2014-04-21 21:20:10, {@link java.util.Calendar#TUESDAY} -> 2014-04-22
	 * 00:00:00
	 * 
	 * @param dayOfWeek
	 *            一周的第几天，{@link java.util.Calendar#DAY_OF_WEEK}
	 * @return 指定日期当周几的起始时间
	 * @see #getDateOfWeek(int)
	 * @see #getStartOfDate(Date)
	 */
	public static Date getStartOfWeekDate(int dayOfWeek) {
		Date dateOfWeek = getDateOfWeek(dayOfWeek);
		return getStartOfDate(dateOfWeek);
	}

	/**
	 * 根据指定时间和一月的第几天获取指定时间当月的第几天的时间
	 * <p>
	 * 如：2014-04-21 21:20:10, 1 -> 2014-04-01 21:20:10
	 * 
	 * @param date
	 *            指定时间
	 * @param dayOfMonth
	 *            一月的第几天，{@link java.util.Calendar#DAY_OF_MONTH}
	 * @return 指定时间当月的第几天的起始时间
	 * @see java.util.Calendar
	 */
	public static Date getDateOfMonth(Date date, int dayOfMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		return calendar.getTime();
	}

	/**
	 * 返回当前时间的年月前后值 比如：2016-01
	 * 
	 * @param step
	 * @return
	 */
	public static String getYearMonthStep(int step) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, step);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		return year + "-" + month;
	}

	/**
	 * 根据一月的第几天获取当前时间月份的第几天的时间
	 * <p>
	 * 如：2014-04-21 21:20:10, 1 -> 2014-04-01 21:20:10
	 * 
	 * @param dayOfMonth
	 *            一月的第几天，{@link java.util.Calendar#DAY_OF_MONTH}
	 * @return 指定时间当月的第几天的起始时间
	 * @see #getDateOfMonth(Date, int)
	 */
	public static Date getDateOfMonth(int dayOfMonth) {
		return getDateOfMonth(new Date(), dayOfMonth);
	}

	/**
	 * 根据指定时间和一月的第几天获取指定时间当月的第几天的起始时间
	 * <p>
	 * 如：2014-04-21 21:20:10, 1 -> 2014-04-01 00:00:00
	 * 
	 * @param date
	 *            指定时间
	 * @param dayOfMonth
	 *            一月的第几天，{@link java.util.Calendar#DAY_OF_MONTH}
	 * @return 指定时间当月的第几天的起始时间
	 * @see #getDateOfMonth(Date, int)
	 * @see #getStartOfDate(Date)
	 */
	public static Date getStartOfMonthDate(Date date, int dayOfMonth) {
		Date dateOfMonth = getDateOfMonth(date, dayOfMonth);
		return getStartOfDate(dateOfMonth);
	}

	/**
	 * 根据一月的第几天获取当前时间月份的第几天的起始时间
	 * <p>
	 * 如：2014-04-21 21:20:10, 1 -> 2014-04-01 00:00:00
	 * 
	 * @param dayOfMonth
	 *            一月的第几天，{@link java.util.Calendar#DAY_OF_MONTH}
	 * @return 指定时间当月的第几天的起始时间
	 * @see #getDateOfMonth(int)
	 * @see #getStartOfDate(Date)
	 */
	public static Date getStartOfMonthDate(int dayOfMonth) {
		Date dateOfMonth = getDateOfMonth(dayOfMonth);
		return getStartOfDate(dateOfMonth);
	}

	/**
	 * 获取指定时间和相隔月份的时间
	 * <p>
	 * 如：2014-04-21 21:20:10, -1 -> 2014-03-21 21:20:10
	 * 
	 * @param date
	 *            指定时间
	 * @param months
	 *            相隔月份，负数表示比指定时间早，0表示当前月，整数表示比指定时间晚
	 * @return 指定时间和相隔月份的时间
	 * @see java.util.Calendar
	 */
	public static Date getDateApartMonths(Date date, int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}

	/**
	 * 获取当前时间的相隔月份的时间
	 * <p>
	 * 如：2014-04-21 21:20:10, 1 -> 2014-05-21 21:20:10
	 * 
	 * @param months
	 *            相隔月份，负数表示比指定时间早，0表示当前月，整数表示比指定时间晚
	 * @return 当前时间的相隔月份的时间
	 * @see #getDateApartMonths(Date, int)
	 */
	public static Date getDateApartMonths(int months) {
		return getDateApartMonths(new Date(), months);
	}

	/**
	 * 获取指定时间和相隔月份的起始时间
	 * <p>
	 * 如：2014-04-21 21:20:10, -1 -> 2014-03-21 00:00:00
	 * 
	 * @param date
	 *            指定时间
	 * @param months
	 *            相隔月份，负数表示比指定时间早，0表示当前月，正数表示比指定时间晚
	 * @return 指定时间和相隔月份的起始时间
	 * @see #getDateApartMonths(Date, int)
	 * @see #getStartOfDate(Date)
	 */
	public static Date getStartDateApartMonths(Date date, int months) {
		Date dateApartMonths = getDateApartMonths(date, months);
		return getStartOfDate(dateApartMonths);
	}

	/**
	 * 获取当前时间的相隔月份的起始时间
	 * <p>
	 * 如：2014-04-21 21:20:10, 1 -> 2014-05-21 00:00:00
	 * 
	 * @param months
	 *            相隔月份，负数表示比指定时间早，0表示当前月，整数表示比指定时间晚
	 * @return 当前时间的相隔月份的时间
	 * @see #getDateApartMonths(int)
	 * @see #getStartOfDate(Date)
	 */
	public static Date getStartDateApartMonths(int months) {
		Date dateApartMonths = getDateApartMonths(months);
		return getStartOfDate(dateApartMonths);
	}

	/**
	 * 指定时间1是否早于指定时间2指定的间隔
	 * 
	 * @param date1
	 *            指定时间1
	 * @param date2
	 *            指定时间2
	 * @param interval
	 *            指定间隔，以毫秒为单位
	 * @return <code>TRUE</code>表示早于
	 */
	public static boolean before(Date date1, Date date2, long interval) {
		return date2.getTime() - date1.getTime() > interval;
	}

	/**
	 * 指定时间1是否早于在指定时间2
	 * 
	 * @param date1
	 *            指定时间1
	 * @param date2
	 *            指定时间2
	 * @return <code>TRUE</code>表示早于
	 */
	public static boolean before(Date date1, Date date2) {
		return before(date1, date2, 0);
	}

	/**
	 * 指定时间1是否晚于指定时间2指定的间隔
	 * 
	 * @param date1
	 *            指定时间1
	 * @param date2
	 *            指定时间2
	 * @param interval
	 *            指定间隔，以毫秒为单位
	 * @return <code>TRUE</code>表示晚于
	 */
	public static boolean after(Date date1, Date date2, long interval) {
		return date1.getTime() - date2.getTime() > interval;
	}

	/**
	 * 指定时间1是否晚于在指定时间2
	 * 
	 * @param date1
	 *            指定时间1
	 * @param date2
	 *            指定时间2
	 * @return <code>TRUE</code>表示晚于
	 */
	public static boolean after(Date date1, Date date2) {
		return after(date1, date2, 0);
	}

	/**
	 * 方法说明：根据指定格式获取时间字符串
	 * 
	 * @param date
	 *            日期对象
	 * @param pattern
	 *            输出格式
	 * @return
	 */
	public static String getDateString(Date date, String pattern) {
		if (null == date) {
			return "";
		}
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		return sf.format(date);
	}

	/**
	 * 方法说明：根据指定格式获取时间字符串
	 * 
	 * @param date
	 *            时间字符串(yyyy-MM-dd HH:mm:ss)
	 * @param pattern
	 *            输出格式
	 * @return
	 */
	public static String getDateString(String dateStr, String pattern)
			throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
		return sf.format(d);
	}

	/**
	 * 方法说明：获得指定格式当前系统时间字符串
	 * 
	 * @param pattern
	 * @return
	 * @throws Exception
	 */
	public static String getCDateString(String pattern) {
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		return sf.format(new Date());
	}

	/**
	 * 
	 * 
	 * 方法说明：将字符串格式日志转化为Date类型
	 * 
	 * @param str
	 * @param pattern
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	public static Date getDateFormatStr(String str, String pattern) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		try {
			return sf.parse(str);
		} catch (Exception e) {
			logger.error("转换日期异常", e);
		}
		return null;
	}

	/**
	 * 
	 * 
	 * 方法说明： 获得指定日期的前指定天数据的日期
	 * 
	 * @param curDate
	 *            当前日期
	 * @param pattern
	 *            转换的格式
	 * @return
	 */
	public static Date getBeforeDate(Date date, int days) {
		Calendar c = Calendar.getInstance();

		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - days);
		return c.getTime();
	}

	/**
	 * 
	 * 方法说明：<br>
	 * 求两个日期之间相差多少分钟
	 * 
	 * @param sourceDate
	 * @param objectDate
	 * @return
	 * @throws Exception
	 */
	public static long dateDiff(Date sourceDate, Date objectDate)
			throws Exception {

		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数

		long diff;

		// 获得两个时间的毫秒时间差异
		diff = objectDate.getTime() - sourceDate.getTime();
		if (diff < 0) {
			return 0;
		}
		long day = diff / nd;// 计算差多少天
		long hour = diff % nd / nh;// 计算差多少小时
		long min = diff % nd % nh / nm;// 计算差多少分钟

		// 输出结果
		long d = day * 24 * 60;
		long h = hour * 60;

		return d + h + min;
	}

	/**
	 * 
	 * 方法说明：<br>
	 * 求两个日期之间相差多少分钟
	 * 
	 * @param sourceDate
	 * @param objectDate
	 * @return
	 * @throws Exception
	 */
	public final static int DATE = 1;
	public final static int HOUR = 2;
	public final static int MINUTE = 3;
	public final static int SECOND = 4;
	public final static int MILLISECOND = 5;

	public static long dateDiff(Date sourceDate, Date objectDate, int field)
			throws Exception {
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;

		// 获得两个时间的毫秒时间差异
		diff = Math.abs(objectDate.getTime() - sourceDate.getTime());
		if (DATE == field) {
			return diff / nd;
		} else if (HOUR == field) {
			return diff / nh;
		} else if (MINUTE == field) {
			return diff / nm;
		} else if (SECOND == field) {
			return diff / ns;
		} else if (MILLISECOND == field) {
			return diff;
		} else {
			throw new Exception("单位(field)不存在.");
		}
	}

	/**
	 * 方法说明：<br>
	 * 获取date后n天的日期
	 * 
	 * @param date
	 * @param l
	 * @return
	 */
	public static Date getAfterDate(Date date, int n) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, n);
			return cal.getTime();
		} catch (Exception ex) {
			logger.error("", ex);
			return null;
		}
	}

	/**
	 * 方法说明：<br>
	 * 获取date后n分钟的日期
	 * 
	 * @param date
	 * @param type
	 *            时间类型.取值如: Calendar.MINUTE
	 * @param n
	 * @return
	 */
	public static Date add(Date date, int type, int n) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(type, n);
			return cal.getTime();
		} catch (Exception ex) {
			logger.error("", ex);
			return null;
		}
	}

	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime());

		return lastDayOfMonth;
	}

	/**
	 * 时间戳转时间
	 * 
	 * @param timestamp
	 * @param format
	 * @return
	 */
	public static String convertTimestampToStr(long timestamp, String format) {
		Date date = new Date(timestamp);
		String strs = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			strs = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strs;
	}
	
	/**
	 * 获取前月第一天
	 * @return
	 */
	public static String getFirstDayOfPreMonth() {
		String firstDay = "";
		try {
			SimpleDateFormat formats = new SimpleDateFormat(DateUtil.YYYYMMDD);
			 //获取前月的第一天
	        Calendar cal_1 = Calendar.getInstance();//获取当前日期 
	        cal_1.add(Calendar.MONTH, -1);
	        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	        firstDay = formats.format(cal_1.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return firstDay;
	}
	
	/**
	 * 获取前月最后一天
	 * @return
	 */
	public static String getLastDayOfPreMonth() {
		 String lastDay = "";
		try {
			SimpleDateFormat formats = new SimpleDateFormat(DateUtil.YYYYMMDD);
	        //获取前月的最后一天
	        Calendar cale = Calendar.getInstance();   
	        cale.set(Calendar.DAY_OF_MONTH,0);//设置为0号,当前日期既为本月最后一天 
	        lastDay = formats.format(cale.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lastDay;
	}
	
}
