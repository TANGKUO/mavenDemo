/**
 * Copyright (c) 2011-2014 All Rights Reserved.
 */
package com.kame.micropay.commons;

/**
 * 异常类<br>
 * 
 * @author xavier
 * @version $Id: ResultCode.java 2014年8月28日 下午6:21:20 $
 */
public class ResultCode {

    /**
     * 系统错误
     */
    public static final String ERROR = "500";

    /**
     * 处理成功
     */
    public static final String SUCCEED = "200";

    /**
     * 签名错误
     */
    public static final String SIGN_ERR = "700";

    /**
     * 数据不匹配
     */
    public static final int MACTH_ERR = 1212;
    
    /**
     * JSON返回格式内容
     */
    public static final String JSON_FAMART = "{\"%s\":\"%s\"}";

    /**
     * 数据库异常
     */
    public static final String QUERY_EXCEPTION = "query.exception";
    public static final String UPDATE_EXCEPTION = "update.exception";
    public static final String DELETE_EXCEPTION = "delete.exception";
    public static final String INSERT_EXCEPTION = "insert.exception";

    /**
     * 申请异常代码
     */
    public static final String NOT_EXIST_POTENTIAL_CLIENT = "not_exist_potential_client";
    public static final String FINANCING_APPLY_HANDLING = "financing_apply_handling";
    public static final String FINANCING_APPLY_REFUSED = "financing_apply_refused";
    public static final String FINANCING_APPLY_SUCCED = "financing_apply_succed";
    public static final String FINANCING_APPLY_NO_REG_SFPAY = "financing_apply_reg_sfpay";
    public static final String FINANCING_APPLY_INVALID_BRAND = "financing_apply_invalid_brand";

    /**
     * 退款异常代码
     */
    public static final String FINANCING_REFUNDED_SUCCEED = "financing_refunded_succeed";
    public static final String FINANCING_REFUNDED_HANDLING = "financing_refunded_handling";
    public static final String FINANCING_REFUNDED_FAIL_CONFIGING = "financing_refunded_fail_configing";

}
