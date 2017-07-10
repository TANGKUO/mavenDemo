package com.tangkuo.cn.data;

public enum TimeUnitCode {
	
	TIMEUNIT_MILLISECOND(0, "毫秒"),
	TIMEUNIT_SECOND(1, "秒"),
	TIMEUNIT_MINUTE(2, "分钟"),
	TIMEUNIT_HOUR(3, "小时"),
	TIMEUNIT_DAY(4, "天"),
	;	
	
	private int code ;
	private String title = null;
	
	TimeUnitCode(int code, String title) {
		this.code = code;
		this.title = title;
	}

	public int getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}
	
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("TimeUnitCode[code=").append(this.code)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}

}
