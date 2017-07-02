package com.tangkuo.cn.pay.kmtk.netbank.common;

/**
 * 验证器
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
