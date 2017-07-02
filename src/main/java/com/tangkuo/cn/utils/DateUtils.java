package com.tangkuo.cn.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 
 * 描述:此类用于取得当前日期相对应的月初，月末，季初，季末，年初，年末，返回值均为String字符串 1、得到当前日期 today() 2、得到当前月份月初
 * thisMonth() 3、得到当前月份月底 thisMonthEnd() 4、得到当前季度季初 thisSeason() 5、得到当前季度季末
 * thisSeasonEnd() 6、得到当前年份年初 thisYear() 7、得到当前年份年底 thisYearEnd() 8、判断输入年份是否为闰年
 * leapYear
 * 
 * 注意事项: 日期格式为：xxxx-yy-zz (eg: 2017-2-05)
 * 
 * 实例:
 * 
 */
public class DateUtils {

	private int x; 

	private int y; 

	private int z; 

	private Calendar localTime; 

    private DateUtils() {
        this.localTime = Calendar.getInstance();
    }
    
    private DateUtils(Date date) {
        this.localTime = Calendar.getInstance();
        this.localTime.setTime(date);
    }
    
    public  static DateUtils getInstance()
    {
		
    	DateUtils dateUtils =  new DateUtils();
    	
    	return dateUtils;
    	
    }
    
    public  static DateUtils getInstance(Date date)
    {
		
    	DateUtils dateUtils =  new DateUtils(date);
    	
    	return dateUtils;
    	
    }

	/**
	 * 功能：得到当前日期 格式为：xxxx-yy-zz (eg: 2017-12-05)<br>
	 * 
	 * @return String
	 * @author pure
	 */
    public String today() {
        String strY = null;
        String strZ = null;
        this.x = this.localTime.get(Calendar.YEAR);
        this.y = this.localTime.get(Calendar.MONTH) + 1;
        this.z = this.localTime.get(Calendar.DATE);
        strY = this.y >= 10 ? String.valueOf(this.y) : ("0" + this.y);
        strZ = this.z >= 10 ? String.valueOf(this.z) : ("0" + this.z);
        return this.x + "-" + strY + "-" + strZ;
    }

	/**
	 * 功能：得到当前月份月初 格式为：xxxx-yy-zz (eg: 2017-12-01)<br>
	 * 
	 * @return String
	 * @author pure
	 */
    public String thisMonth() {
        String strY = null;
        this.x = this.localTime.get(Calendar.YEAR);
        this.y = this.localTime.get(Calendar.MONTH) + 1;
        strY = this.y >= 10 ? String.valueOf(this.y) : ("0" + this.y);
        return this.x + "-" + strY + "-01";
    }

	/**
	 * 功能：得到当前月份月底 格式为：xxxx-yy-zz (eg: 2017-12-31)<br>
	 * 
	 * @return String
	 * @author pure
	 */
    public String thisMonthEnd() {
        String strY = null;
        String strZ = null;
        boolean leap = false;
        this.x = this.localTime.get(Calendar.YEAR);
        this.y = this.localTime.get(Calendar.MONTH) + 1;
        if (this.y == 1 || this.y == 3 || this.y == 5 || this.y == 7 || this.y == 8 || this.y == 10 || this.y == 12) {
            strZ = "31";
        }
        if (this.y == 4 || this.y == 6 || this.y == 9 || this.y == 11) {
            strZ = "30";
        }
        if (this.y == 2) {
            leap = leapYear(this.x);
            if (leap) {
                strZ = "29";
            }
            else {
                strZ = "28";
            }
        }
        strY = this.y >= 10 ? String.valueOf(this.y) : ("0" + this.y);
        return this.x + "-" + strY + "-" + strZ;
    }

	/**
	 * 功能：得到当前季度季初 格式为：xxxx-yy-zz (eg: 2017-10-01)<br>
	 * 
	 * @return String
	 * @author pure
	 */
    public String thisSeason() {
        String dateString = "";
        this.x = this.localTime.get(Calendar.YEAR);
        this.y = this.localTime.get(Calendar.MONTH) + 1;
        if (this.y >= 1 && this.y <= 3) {
            dateString = this.x + "-" + "01" + "-" + "01";
        }
        if (this.y >= 4 && this.y <= 6) {
            dateString = this.x + "-" + "04" + "-" + "01";
        }
        if (this.y >= 7 && this.y <= 9) {
            dateString = this.x + "-" + "07" + "-" + "01";
        }
        if (this.y >= 10 && this.y <= 12) {
            dateString = this.x + "-" + "10" + "-" + "01";
        }
        return dateString;
    }

	/**
	 * 功能：得到当前季度季末 格式为：xxxx-yy-zz (eg: 2017-12-31)<br>
	 * 
	 * @return String
	 * @author pure
	 */
    public String thisSeasonEnd() {
        String dateString = "";
        this.x = this.localTime.get(Calendar.YEAR);
        this.y = this.localTime.get(Calendar.MONTH) + 1;
        if (this.y >= 1 && this.y <= 3) {
            dateString = this.x + "-" + "03" + "-" + "31";
        }
        if (this.y >= 4 && this.y <= 6) {
            dateString = this.x + "-" + "06" + "-" + "30";
        }
        if (this.y >= 7 && this.y <= 9) {
            dateString = this.x + "-" + "09" + "-" + "30";
        }
        if (this.y >= 10 && this.y <= 12) {
            dateString = this.x + "-" + "12" + "-" + "31";
        }
        return dateString;
    }

	/**
	 * 功能：得到当前年份年初 格式为：xxxx-yy-zz (eg: 2017-01-01)<br>
	 * 
	 * @return String
	 * @author pure
	 */
	public String thisYear() {
        this.x = this.localTime.get(Calendar.YEAR);
        return this.x + "-01" + "-01";
    }

	/**
	 * 功能：得到当前年份年底 格式为：xxxx-yy-zz (eg: 2017-12-31)<br>
	 * 
	 * @return String
	 * @author pure
	 */
    public String thisYearEnd() {
        this.x = this.localTime.get(Calendar.YEAR);
        return this.x + "-12" + "-31";
    }

	/**
	 * 功能：判断输入年份是否为闰年<br>
	 * 
	 * @param year
	 * @return 是：true 否：false
	 * @author pure
	 */
    public boolean leapYear(int year) {
        boolean leap;
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) leap = true;
                else leap = false;
            }
            else leap = true;
        }
        else leap = false;
        return leap;
    }

	/**
	 * 功能：得到当前日期的后面第几天 格式为：xxxx-yy-zz (eg: 2017-12-05)<br>
	 * 
	 * @return Date
	 * @author ouyang
	 */
    public Date nextday(Date currDate,int i) {
    	Calendar cNow = Calendar.getInstance();     
    	cNow.setTime(currDate);	
    	cNow.add(5,+i);	
    	cNow.set(cNow.get(Calendar.YEAR),cNow.get(Calendar.MONTH),cNow.get(Calendar.DATE));			
    	return cNow.getTime();
    }

	// 日期差
    public int daysBetween(Date now, Date returnDate) {
        Calendar cNow = Calendar.getInstance();
        Calendar cReturnDate = Calendar.getInstance();
        cNow.setTime(now);
        cReturnDate.setTime(returnDate);
        setTimeToMidnight(cNow);
        setTimeToMidnight(cReturnDate);
        long todayMs = cNow.getTimeInMillis();
        long returnMs = cReturnDate.getTimeInMillis();
        long intervalMs = todayMs - returnMs;
        return millisecondsToDays(intervalMs);
      }

	// 毫秒换算成天数
      private int millisecondsToDays(long intervalMs){
        return (int) (intervalMs / (1000 * 86400));
      }

	// 两个日期毫秒差
      private void setTimeToMidnight(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
      }
  	public static String format(Date date) {
		String str = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		str = sdf.format(date);
		return str;
	}
  	public static String formatString(Date date) {
		String str = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		str = sdf.format(date);
		return str;
	}
  	public static Date formatDate(String date) {
		Date dd = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			dd = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dd;
	}
  	
 	public static Date toStringformatDate(String date) {
		Date dd = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			dd = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dd;
	}
  	
	public static String format1(Date date) {
		String str = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		str = sdf.format(date);
		return str;
	}
	public static String formatCurrent(Date date) {
		String str = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		str = sdf.format(date);
		return str;
	}
	public static String formatCurrentDate(Date date) {
		String str = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		str = sdf.format(date);
		return str;
	}
	
	public static String formatCurrentTime(Date date) {
		String str = null;
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		str = sdf.format(date);
		return str;
	}

	/**
	 * 功能：得到当前年份年初 格式为：xxxx-yy-zz (eg: 2017-01-01)<br>
	 * 
	 * @return String
	 * @author pure
	 */
	public static String lastYear(String format) {
		int x = 0;
		if ("yyyymmdd".equals(format)) {
			x = Calendar.getInstance().get(Calendar.YEAR) - 1;
			return x + "01" + "01";
		} else {
			x = Calendar.getInstance().get(Calendar.YEAR) - 1;
			return x + "-01" + "-01";
		}
	}

	/**
	 * 功能：得到当前年份年底 格式为：xxxx-yy-zz (eg: 2017-12-31)<br>
	 * 
	 * @return String
	 * @author pure
	 */
	public static String lastYearEnd(String format) {
		int x = 0;
		if ("yyyymmdd".equals(format)) {
			x = Calendar.getInstance().get(Calendar.YEAR) - 1;
			return x + "12" + "31";
		} else {
			x = Calendar.getInstance().get(Calendar.YEAR) - 1;
			return x + "-12" + "-31";
		}
	}
	/**
	 * 获得近几个月 时间
	 */
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
	
	public Calendar getLocalTime() {
		return localTime;
	}

	public void setLocalTime(Calendar localTime) {
		this.localTime = localTime;
	}	
	/*public static void main(String[] args) {
		System.out.println(formatString(formatDate("20130101")));
	}*/	
}