package com.tangkuo.cn.data;

import java.util.concurrent.TimeUnit;

public class TimeUnitUtils {
	//时间单位定义
	public final static int TIMEUNIT_MILLISECOND = 0; //毫秒
	public final static int TIMEUNIT_SECOND = 1; //秒
	public final static int TIMEUNIT_MINUTE = 2; //分钟
	public final static int TIMEUNIT_HOUR = 3; //小时
	public final static int TIMEUNIT_DAY = 4;  //天

	public static TimeUnit getTimeUnit(int unit) {
		switch (unit) {
		case TIMEUNIT_MILLISECOND:
			return TimeUnit.MILLISECONDS;
		case TIMEUNIT_SECOND:
			return TimeUnit.SECONDS;
		case TIMEUNIT_MINUTE:
			return TimeUnit.MINUTES;
		case TIMEUNIT_HOUR:
			return TimeUnit.HOURS;
		case TIMEUNIT_DAY:
			return TimeUnit.DAYS;

		default:
			return TimeUnit.SECONDS;
		}
	}
	
	public static long calculateMillSecond(int timeValue , int timeUnit) {
		switch(timeUnit) {
		  case TIMEUNIT_MILLISECOND:
			  return timeValue;
		  case TIMEUNIT_SECOND:
		       return TimeUnit.SECONDS.toMillis(timeValue);
		  case TIMEUNIT_MINUTE:
		       return TimeUnit.MINUTES.toMillis(timeValue);
		  case TIMEUNIT_HOUR:
		       return TimeUnit.HOURS.toMillis(timeValue);
		  case TIMEUNIT_DAY:
		       return TimeUnit.DAYS.toMillis(timeValue);
			  
		  default :
			  return 0;
		}
	}

}
