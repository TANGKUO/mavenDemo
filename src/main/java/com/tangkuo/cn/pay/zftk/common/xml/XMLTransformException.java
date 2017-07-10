package com.tangkuo.cn.pay.zftk.common.xml;

import com.tangkuo.cn.pay.zftk.common.exception.BaseRuntimeException;
/**
 * 
* @ClassName: XMLTransformException
* @Description: TODO(这里用一句话描述这个类的作用)
 */
public class XMLTransformException extends BaseRuntimeException {
	private static final long serialVersionUID = 2325300429266549778L;
	
	public XMLTransformException(String errorCode) {
		super(errorCode);
	}

	public XMLTransformException(String message,Throwable cause) {
		super(message, cause);
	}
	
	public XMLTransformException(Throwable cause) {
		super(cause);
	}
	
	public XMLTransformException(String errorCode, String message,Throwable cause) {
		super(errorCode, message, cause);
	}

}
