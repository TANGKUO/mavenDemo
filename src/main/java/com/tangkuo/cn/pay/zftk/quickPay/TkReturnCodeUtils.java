package com.tangkuo.cn.pay.zftk.quickPay;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tangkuo.cn.utils.configuration.PropertiesUtils;

/**
 * 
 * @ClassName: ReturnCodeUtil
 * @Description: 银行错误码转换
 */

public class TkReturnCodeUtils {
	private final static String EXCEPTION_INFO_FILE_PATH = "returnCode/exceptionInfo.properties";
	private final static String BANK_RETURN_CODE_FILE_PATH = "returnCode/bankReturnCode.properties";

	private static Map<String, Object> exceptionInfoMap = null;
	private static Map<String, Object> bankReturnCodeMap = null;

	static {
		exceptionInfoMap = PropertiesUtils.getProperties(EXCEPTION_INFO_FILE_PATH);
		bankReturnCodeMap = PropertiesUtils.getProperties(BANK_RETURN_CODE_FILE_PATH);
	}

	/**
	 * 获取转换后的错误信息
	 * <p>
	 * 如能找到对应的错误码，则返回转换后的错误信息，否则返回原错误信息
	 * 
	 * @param code
	 * @return
	 */
	public static String[] getExceptionInfo(String bankCode, String retCode, String retMsg) {
		String[] retErrorInfo = { retCode, retMsg };

		if (StringUtils.isEmpty(retCode)) {
			return retErrorInfo;
		}

		String key = bankCode + "&" + retCode;
		Object errorCodeObj = bankReturnCodeMap.get(key);

		if (null == errorCodeObj) {
			return retErrorInfo;
		}

		String errorCode = errorCodeObj.toString();

		retErrorInfo[0] = errorCode;
		retErrorInfo[1] = String.valueOf(exceptionInfoMap.get(errorCode));

		return retErrorInfo;
	}
}
