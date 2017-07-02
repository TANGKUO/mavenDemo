/**
 * Project Name:kame-commons
 * File Name:TtyErrorCode.java
 * Package Name:com.kame.micropay.commons
 * Date:2015年12月30日下午4:09:25
 * Copyright (c) 2015, Kame-Pay All Rights Reserved.
 *
 */

package com.kame.micropay.commons;

import java.io.Serializable;

/**
 * ClassName: TtyErrorCode <br/>
 * Function: ADD FUNCTION. <br/>
 * Reason: ADD REASON(可选). <br/>
 * date: 2015年12月30日 下午4:09:25 <br/>
 *
 * @author Bill Huang
 * @version 1.1
 * @since JDK 1.7
 */
public class TtyErrorCode implements Serializable {

	/**
	 * serialVersionUID:(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = -1549481863580147549L;

	/**
	 * 通用的异常代码
	 * */
	public static final String DEFAULT_ERRORCODE = "10000";
	
	public static final String NULLPARAM_ERRORCODE = "20000";
	/** 订单号不能为空  */
	public static final String ORDER_NO_EMPTY = "order_no_empty";
	/** 批量订单号不能为空 */
	public static final String ORDER_NO_LIST_EMPTY = "order_no_list_empty";
	/** 原订单号不能为空  */
	public static final String OLD_ORDER_NO_EMPTY = "old_order_no_empty";
	/** 金额不能为空  */
	public static final String AMOUNT_EMPTY = "amount_empty";
	/** 银行编码不能为空  */
	public static final String BANK_CODE_EMPTY = "bank_code_empty";
	/** 银行名称不能为空  */
	public static final String BANK_NAME_EMPTY = "bank_name_empty";
	/** 卡类型不能为空  */
	public static final String CARD_TYPE_EMPTY = "card_type_empty";
	/** 支付渠道类型不能为空  */
	public static final String PAY_CHANNEL_TYPE_EMPTY = "pay_channel_type_empty";
	/** 绑定IP地址不能为空  */
	public static final String IP_ADDRESS_EMPTY = "ip_address_empty";
	/** 页面同步回调地址不能为空  */
	public static final String PAGE_URL_EMPTY = "page_url_empty";
	/** 后台异步回调地址不能为空  */
	public static final String NOTIFY_URL_EMPTY = "notify_url_empty";
	/** 商户页面回调地址不能为空  */
	public static final String MER_URL_EMPTY = "mer_url_empty";
	/** 业务接口类型不能为空  */
	public static final String BUSS_INTERFACE_TYPE_EMPTY = "buss_interface_type_empty";
	/** 查询方式不能为空  */
	public static final String QUERY_TYPE_EMPTY = "query_type_empty";
	/** 账户卡号不能为空  */
	public static final String ACCT_NO_EMPTY = "acct_no_empty";
	/** 账户名称不能为空  */
	public static final String ACCT_NAME_EMPTY = "acct_name_empty";
	/** 收款方联行号不能为空 */
	public static final String ISSON_EMPTY = "isson_empty";
	/** 绑定流水号不能为空 */
	public static final String SN_BINDING_EMPTY = "sn_binding_empty";
	/** 解绑流水号不能为空 */
	public static final String SN_UNBINDING_EMPTY = "sn_unbinding_empty";
	/** 账户号码不能为空 */
	public static final String ACCOUNT_NUMBER_EMPTY = "account_number_empty";
	/** 证件类型不能为空 */
	public static final String ID_TYPE_EMPTY = "id_type_empty";
	/** 证件号码不能为空 */
	public static final String ID_TYPE_NUMBER_EMPTY = "id_type_number_empty";
	/** 手机号码不能为空 */
	public static final String MOBILE_EMPTY = "mobile_empty";
	/** 信用卡有效期不能为空 */
	public static final String CREDIT_LIMIT_DATE_EMPTY = "credit_limit_date_empty";
	/** 信用卡CVN2不能为空 */
	public static final String CREDIT_CVN2_EMPTY = "credit_cvn2_empty";
	/** 交易金额不能小于或等于0 */
	public static final String AMOUNT_LESS_ZERO = "amount_less_zero";
	/** 对账日期不能为空 */
	public static final String SETTLE_DATE_EMPTY = "settle_date_empty";
	/** 手机短信验证码不能为空 */
	public static final String SMS_CODE_EMPTY = "sms_code_empty";
}

