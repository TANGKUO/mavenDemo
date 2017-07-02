/**
 * Copyright (c) 2011-2014 All Rights Reserved.
 */
package com.kame.micropay.commons;

/**
 * 类说明：全局常量<br>
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
 * |	xavier 曾宪新	|		2014年4月25日		|	
 * ——————————————————————————————————————————————————————————————————
 * </pre>
 * 
 * @author xavier 曾宪新(Xavier.zeng)
 * 
 *         CreateDate: 2014年4月25日
 */

public final class Constants {

    public static final String SIGN_TYPE = "sign_type";

    public static final String SIGN_TYPE_RSA = "RSA";

    public static final String SIGN_TYPE_MD5WithRSA = "MD5WithRSA";
    
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    /** 默认时间格式 **/
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    public static final String DATE_TIME_FORMAT_EXTEND = "yyyyMMdd";

    /** Date默认时区 **/
    public static final String DATE_TIMEZONE = "GMT+8";

    /** UTF-8字符集 **/
    public static final String CHARSET_UTF8 = "UTF-8";

    /** GBK字符集 **/
    public static final String CHARSET_GBK = "GBK";
    
    /** GB2312字符集 **/
    public static final String CHARSET_GB2312 = "GB2312";
    
    /** ISO-8859-1 编码 */
	public static final String CHARSET_ISO_8859_1 = "ISO-8859-1";

    /** JSON 应格式 */
    public static final String FORMAT_JSON = "json";

    /** XML 应格式 */
    public static final String FORMAT_XML = "xml";

    /** MD5签名方式 */
    public static final String SIGN_METHOD_MD5 = "md5";

    /** MD5签名方式 */
    public static final String SIGN_METHOD_DSA = "dsa";

    /** MD5签名方式 */
    public static final String SIGN_METHOD_RSA = "rsa";

    /** HMAC签名方式 */
    public static final String SIGN_METHOD_HMAC = "hmac";
    
    /** AES加密 **/
    public static final String ENCRYPT_AES = "AES";

    /** DES加密 **/
    public static final String ENCRYPT_DES = "DES";
    
    /** DES加密 **/
    public static final String DESEDE = "DESede";
    
    /** AES加密算法 **/
    public static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";
    
    /** RSA加密算法 **/
	public static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";

    /** 返回的错误码 */
    public static final String ERROR_RESPONSE = "error_response";
    public static final String ERROR_CODE = "code";
    public static final String ERROR_MSG = "msg";
    public static final String ERROR_SUB_CODE = "sub_code";
    public static final String ERROR_SUB_MSG = "sub_msg";

	public static final String BOC_B2B_PFXCER = null;

	public static final String BOC_B2B_PFXPASS = null;

	public static final String BOC_B2B_SZBOCCER = null;

	public static final String SETTLE_DOWNLOAD_PATH = null;

	public static final String SETTLE_DOWNLOAD_FILETYPE = null;
}
