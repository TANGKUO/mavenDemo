package com.tangkuo.cn.pay.kmtk.netbank.common;

import java.io.Serializable;

public class TtyErrorMesg implements Serializable {

	/**
	 * serialVersionUID:(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = -7327479782782423213L;

	/**
	 * 通用的异常信息
	 * */
	public static final String DEFAULT_ERRORMESG = "System error,Please contact the admin.";
	
	public static final String NULLPARAM_ERRORMESG = "Null Parameters Error.";
	
}

