/**
 * Copyright (c) 2011-2014 All Rights Reserved.
 */
package com.kame.micropay.commons;

/**
 * 类说明：异常类<br>
 * 
 * <p>
 * 详细描述：<br>
 * 
 * </p>
 * 
 * <pre>
 * ——————————————————————————————————————————————————————————————————
 * |		修改人		|		修改时间			|		修改原因
 * ——————————————————————————————————————————————————————————————————
 * |	xavier 曾宪新	|		2014年2月28日		|	
 * ——————————————————————————————————————————————————————————————————
 * </pre>
 * 
 * @author xavier 曾宪新(Xavier.zeng)
 * 
 *         CreateDate: 2014年2月28日
 */
public class TtyException extends RuntimeException {

    private static final long serialVersionUID = -238091758285157331L;
    private  final String  ERROR_MSG= "error";

    private String errCode;
    private Object[] args;
    private String errMsg;

    public TtyException() {
        super();
    }

    public TtyException(String message, Throwable cause) {
        super(message, cause);
        this.errMsg = message;
        this.errCode = this.ERROR_MSG;
    }

    public TtyException(String message) {
        super(message);
        this.errMsg = message;
        this.errCode = this.ERROR_MSG;
    }

    public TtyException(Throwable cause) {
        super(cause);
    }

    public TtyException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public TtyException(String errCode, String errMsg, Object[] args) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.args = args;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

}