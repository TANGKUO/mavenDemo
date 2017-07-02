/**
 * Copyright (c) 2011-2015 All Rights Reserved.
 */
package com.kame.micropay.commons;

/**
 * 验证器
 * @author zengxx
 * @version $Id: Validator.java 2015年8月20日 下午1:52:09 $
 */
public interface Validator {
	
	/**
	 * 方法: 验证	
	 * @param target
	 * @param response
	 * @return
	 */
	boolean validate(TtyResponse response, Object... target);
	
}
