/**
 * Project Name:kame-commons
 * File Name:TtyErrorMesg.java
 * Package Name:com.kame.micropay.commons
 * Date:2015年12月30日下午4:17:04
 * Copyright (c) 2015, Kame-Pay All Rights Reserved.
 *
 */

package com.kame.micropay.commons;

import java.io.Serializable;

/**
 * ClassName: TtyErrorMesg <br/>
 * Function: ADD FUNCTION. <br/>
 * Reason: ADD REASON(可选). <br/>
 * date: 2015年12月30日 下午4:17:04 <br/>
 *
 * @author Bill Huang
 * @version 1.1
 * @since JDK 1.7
 */
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

