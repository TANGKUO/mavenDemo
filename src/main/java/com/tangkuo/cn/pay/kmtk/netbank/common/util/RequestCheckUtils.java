package com.tangkuo.cn.pay.kmtk.netbank.common.util;

import com.tangkuo.cn.pay.kmtk.netbank.common.TtyException;

/**
 * 
* @ClassName: RequestCheckUtils
* @Description: (请求参数验证工具类)
* @author tangkuo
* @date 2017年7月2日 下午1:50:42
*
 */

public final class RequestCheckUtils {
    public static final String ERROR_CODE_ARGUMENTS_MISS = "error_code_arguments_miss"; // Missing // Required //
                                                                                        // Arguments
    public static final String ERROR_CODE_ARGUMENTS_INVALID = "error_code_arguments_invalid"; // Invalid // Arguments

    public static void checkNotEmpty(Object value, String fieldName) throws TtyException {
        if (value == null) {
            throw new TtyException(ERROR_CODE_ARGUMENTS_MISS, "", new Object[] {fieldName});
        }
        if (value instanceof String) {
            if (((String) value).trim().length() == 0) {
                throw new TtyException(ERROR_CODE_ARGUMENTS_MISS, "", new Object[] {fieldName});
            }
        }
    }

    public static void checkMaxLength(String value,
                                      int maxLength,
                                      String fieldName) throws TtyException {
        if (value != null) {
            if (value.length() > maxLength) {
                throw new TtyException(ERROR_CODE_ARGUMENTS_INVALID, String.format("client-error:Invalid Arguments:the length of %s can not be larger than %s.", fieldName, maxLength));
            }
        }
    }

    public static void checkMaxListSize(String value,
                                        int maxSize,
                                        String fieldName) throws TtyException {
        if (value != null) {
            String[] list = value.split(",");
            if (list != null && list.length > maxSize) {
                throw new TtyException(ERROR_CODE_ARGUMENTS_INVALID, String.format("client-error:Invalid Arguments:the listsize(the string split by \",\") of %s must be less than %s.", fieldName, maxSize));
            }
        }
    }

    public static void checkMaxValue(Long value, long maxValue, String fieldName) throws TtyException {
        if (value != null) {
            if (value > maxValue) {
                throw new TtyException(ERROR_CODE_ARGUMENTS_INVALID, String.format("client-error:Invalid Arguments:the value of %s can not be larger than %s.", fieldName, maxValue));
            }
        }
    }

    public static void checkMinValue(Long value, long minValue, String fieldName) throws TtyException {
        if (value != null) {
            if (value < minValue) {
                throw new TtyException(ERROR_CODE_ARGUMENTS_INVALID, String.format("client-error:Invalid Arguments:the value of %s can not be less than %s.", fieldName, minValue));
            }
        }
    }
}
