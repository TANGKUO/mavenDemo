package com.tangkuo.cn.utils.data;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
* @ClassName: DateUtil
* @Description: (日期工具类)
* @author tangkuo
* @date 2017年7月2日 下午2:18:10
*
 */
public class DateUtil {


    /**
     * 获取指定的日志是星期几
     *
     * @param date
     * @return
     */
    public static int getWeekDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        return dayOfWeek;
    }


    /**
     * 判断currDate是否在startdate与enddate之间
     *
     * @param currDate  当前日期
     * @param startdate 日期范围开始
     * @param enddate   日期范围截止
     * @return
     */
    public static boolean isInMiddle(Date currDate, Date startdate, Date enddate) {
        boolean result = false;
        if (null == currDate || null == startdate || null == enddate) {
            return result;
        }
        long currentTimeVal = currDate.getTime();
        result = ((currentTimeVal >= startdate.getTime())
                && (currentTimeVal < enddate.getTime()));
        return result;
    }

    /**
     * 获取指定的日志是星期几
     *
     * @param date
     * @return
     */
    public static String getWeekDayString(Date date) {
        String weekString = "";
        final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        weekString = dayNames[dayOfWeek - 1];
        return weekString;
    }

    /**
     * 按照年月日 星期几的格式输入某天的日期
     *
     * @param date
     * @return
     */
    public static String getDayString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String tdate = sdf.format(date);
        tdate += " " + getWeekDayString(date);
        return tdate;
    }

    public static String amOrPm(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        int hour = Integer.valueOf(sdf.format(date));
        if (hour <= 12) {
            return "上午";
        } else {
            return "下午";
        }
    }

    // 返回日期型yyyy-MM-dd HH:mm:ss
    public static Date getDateTimeByStr(String date) {
        Date date1 = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (date != null) date1 = sdf.parse(date);
        } catch (Exception e) {
            System.out.println("将字符串转换成yyyy-MM-dd HH:mm:ss日期出错");
            e.printStackTrace();
        }
        return date1;
    }

    public static Date getDateTimeByString(String date) {
        Date date1 = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            if (date != null) date1 = sdf.parse(date);
        } catch (Exception e) {
            System.out.println("将字符串转换成yyyy-MM-dd HH:mm:ss日期出错");
            e.printStackTrace();
        }
        return date1;
    }

    public static Date getDateByStr(String date) {
        Date date1 = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (date != null) date1 = sdf.parse(date);
        } catch (Exception e) {
            System.out.println("将字符串转换成yyyy-MM-dd日期出错");
            e.printStackTrace();
        }
        return date1;
    }

    // 返回字符串"yyyy-MM-dd"
    public static String getDateStrByDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    // 返回字符串"yyyy-MM-dd"
    public static String getDateStrByDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param fmt
     * @return
     */
    public static String getDateString(Date date, String fmt) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.format(date);
    }

    /**
     * 返回字符串"yyyy.MM.dd"
     *
     * @param date
     * @return
     */
    public static String getDateStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        return sdf.format(date);
    }
	/**
	 * 返回字符串"yyyy.MM.dd"
	 *
	 * @param date
	 * @return
	 */
	public static String getDateStrYMDay(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

    /**
     * 添加时、分、秒
     */
    public static Date addHMS(Date date, int hour, int minute, int second) {

        return addHour(addMinute(addSecond(date, second), minute), hour);
    }

    public static Date addMinute(Date date, int num) {
        return addDateTime(date, 5, num);
    }

    // 设置时间
    public static Date addDateTime(Date date, int type, int num) {
        if (date == null) {
            return null;
        }
        // 初始化日历对象
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        // 根据类型添加
        switch (type) {
            case 1: // 添加年
                cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + num);
                break;
            case 2: // 添加月
                cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + num);
                break;
            case 3: // 添加日
                cal.set(Calendar.DATE, cal.get(Calendar.DATE) + num);
                break;
            case 4: // 添加时
                cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + num);
                break;
            case 5: // 添加分
                cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + num);
                break;
            case 6: // 添加秒
                cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) + num);
                break;
        }

        // 返回操作结果
        return cal.getTime();
    }

    public static Date addSecond(Date date, int num) {
        return addDateTime(date, 6, num);
    }

    public static Date addHour(Date date, int num) {
        return addDateTime(date, 4, num);
    }

    /**
     * 返回字符串"yyyy-MM-dd"
     *
     * @param date
     * @return
     */
    public static String getDateStrForSql(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 返回字符串"yyyy-MM-dd"
     *
     * @param timestamp
     * @return
     */
    public static String getTimeStrForSql(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(timestamp);
    }

    /**
     * 返回字符串"yyyy-MM-dd"
     *
     * @param timestamp
     * @return
     */
    public static String getTimeDateStrForSql(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(timestamp);
    }

    public static String getDateStr2(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    public static String getDateStr3(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    // 返回本周的第一天
    public static String getWeekFirstDay() {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
        return df.format(cal.getTime());
    }

    // 返回本周的最后一天
    public static String getWeekLastDay() {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return df.format(cal.getTime());
    }

    /**
     * 获取两个日期之间的日期
     * @param startDateStr
     * @param endDateStr
     * @return
     * @throws java.text.ParseException
     */
    public static List getSomeDate(String startDateStr,String endDateStr) throws ParseException {
        List list = new ArrayList();
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = df.parse(startDateStr);
        startCalendar.setTime(startDate);
        Date endDate = df.parse(endDateStr);
        endCalendar.setTime(endDate);
        while (true) {
            startCalendar.add(Calendar.DAY_OF_MONTH, 1);
            if (startCalendar.getTimeInMillis() < endCalendar.getTimeInMillis()) {
                list.add(df.format(startCalendar.getTime()));
                System.out.println(df.format(startCalendar.getTime()));
            } else {
                break;
            }
        }
        return list;
    }

    /**
     * 获取开始时间结束时间
     * @param request
     * @param type
     * @param pageStartDate
     * @param pageEndDate
     * @return
     */
    public static Map getStartEndDate(HttpServletRequest request,String type,String pageStartDate,String pageEndDate) {
        Calendar c = Calendar.getInstance();
        Map map = new HashMap();
        String startDate = "";//开始时间
        String endDate = "";//结束时间
        if ("1".equals(type)) {//获取今日数据
            startDate = DateUtil.getDateStrByDate(new Date());
            endDate = startDate;
        } else if ("2".equals(type)) {//最近一周
            c.add(Calendar.DAY_OF_MONTH, -1);
            endDate = CommonUtil.dateToDateStr(CommonUtil.DATE_FMT,c.getTime());
            c.add(Calendar.DAY_OF_MONTH, -6);
            startDate = CommonUtil.dateToDateStr(CommonUtil.DATE_FMT,c.getTime());
        } else if ("3".equals(type)) {//最近一个月
            c.add(Calendar.DAY_OF_MONTH, -1);
            endDate = CommonUtil.dateToDateStr(CommonUtil.DATE_FMT,c.getTime());
            c.add(Calendar.DAY_OF_MONTH, -29);
            startDate = CommonUtil.dateToDateStr(CommonUtil.DATE_FMT,c.getTime());
        } else {//自定义时间
            startDate = request.getParameter(pageStartDate);
            endDate = request.getParameter(pageEndDate);
        }
        startDate += " 00:00:00";
        endDate += " 23:59:59";
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        return map;
    }

    public static void main(String[] args) {
        System.out.println(getDateStrForSql(new Date()));
        System.out.println("本周第一天:"+DateUtil.getWeekFirstDay());
        System.out.println("本周最后一天:"+DateUtil.getWeekLastDay());
        System.out.println(getDateStr(new Date()));
//		SimpleDateFormat 格式=new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
//		Calendar 日历=Calendar.getInstance(Locale.CHINA);   
//		//其余的行不变只加入这一行即可，设定每周的起始日。  
//		日历.setFirstDayOfWeek(Calendar.MONDAY); 
//		//以周1为首日   
//		日历.setTimeInMillis(System.currentTimeMillis());
//		//当前时间 System.out.println("当前时间:"+格式.format(日历.getTime()));
//		日历.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); 
//		System.out.println("周一时间:"+格式.format(日历.getTime())); 
//		日历.add(Calendar.DAY_OF_WEEK, 6);
//		System.out.println("周日时间:"+格式.format(日历.getTime()));
    }
    
    
		/*public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		for(int i=18;i<30;i++)
		{
			c.set(2012, 10, i);
			System.out.println(getWeekDayString(c.getTime()));
		}
		c.set(2012, 10, 16);
		System.out.println(getDayString(c.getTime()));
		System.out.println(amOrPm(new Date()));
	}*/
}
