package com.kame.micropay.commons;

/**
 * @ClassName: CodeDesc
 * @Description: 定义APP接口所有请求响应编码
 * @author Tomsion
 * @date 2015年10月28日 下午10:10:18
 * 
 */
public enum CodeDesc {
	
	/**
	 * 成功
	 */
	SUCCESS("0000", "成功"), 
	
	/**
	 * 程序异常
	 */
	EXCEPTION("99999", "网络异常,请稍后再试!"), 
	
	/**
	 * 失败
	 */
	FAIL("10001", "处理失败,请刷新重试!"), 
	
	/**
	 * 无结果
	 */
	NO_RESULT("10004", "无结果"), 
	
	
	/**
	 * 参数错误
	 */
	PARAME_EMPTY("10003", "输入有误,请重新输入"), 
	
	/**
	 * 用户名不存在
	 */
	USERNAME_NOEXIST("10005", "用户名不存在,请重新输入或注册新账号!"),
	
	/**
	 * 用户名或密码错误
	 */
	INVALID_USERNAME_PASSWORD("10008", "密码有误,请重新输入"), 
	
	/**
	 * 用户名或密码错误 (2 - 4次失败)
	 */
	INVALID_USERNAME_PASSWORD_TIMES("10008", "密码有误,请重新输入或找回密码"), 
	
	/**
	 * 账户多次密码出错, 账户已锁定
	 */
	USER_LOCK("10059", "密码多次输错, 请%d小时后再试或找回密码"),
	
	/**
	 * 账户已冻结
	 */
	USER_UNABLE("10057", "账户已冻结或锁定"),
	/**
	 * 账户已注销
	 */
	USER_CANCEL("10058","账户已注销,请更换账号"),
	
	/**
	 * 支付密码错误
	 */
	INVALID_ACCOUNT_PAYPASS("10075", "支付密码不正确,您还可以输入%d次"),
	
	
	/**
	 * 验证码已发送
	 */
	SEND_CHECKCODE_SUCCEED("80011", "验证码已发送"), 
	/**
	 * 发送失败
	 */
	SEND_CHECKCODE_FAIL("80012", "验证码发送失败"), 
	/**
	 * 验证码无效
	 */
	INVALID_CHECKCODE("10013", "无效的验证码"),

	
	/**
	 * 会员已存在
	 */
	MEMBER_EXISTS("10018", "此账号已被使用"),
	
	/**
	 * 订单不存在
	 */
	INVALID_ORDER_NO("10022", "无效的订单编号"),
	
	
	
	
	
	
	/**
	 * 图片不能为空
	 */
	NOTEMPTYIMAGE("10029","图片不能为空"),

	/**
	 * 新密码和确认密码不一致
	 */
	NANDRPASSWORD("10031","新密码和确认密码不一致"),
	/**
	 * 登录密码不能与支付密码相同
	 */
	LOGIN_PAY_NOT("10032","登录密码不能与支付密码相同,请重新输入"),
	/**
	 * 登录密码不能与支付密码相同
	 */
	PAY_LOGIN_NOT("10030","支付密码不能与登录密码相同,请重新输入"),
	
	
	//***********************银行卡*******************************
	/**
	 * 银行卡号已存在
	 */
	EXITSTBANKCARD("10033", "银行卡号已存在"),
	/**
	 * 银行卡号不存在
	 */
	INVALID_BANK_CARD("10034", "您还未绑定银行卡, 请先绑定银行卡"),
	
	INVALID_CARD_BIN("10035", "无法识别该银行卡,请换银行卡再试"),
	
	
	
	/**
	 * 消息不存在
	 */
	MESSAGNOTEXITS("10038","消息不存在"),

	/**
	 * 订单已锁，请稍后操作
	 */
	LOCKEDORDER("10047","订单已锁，请稍后操作"),
	
	/**
	 * 支付单不存在
	 */
	NOEXITSTPAYMENT("10051", "支付单不存在"),
	/**
	 * 订单已支会
	 */
	ORDERPAYED("10052", "订单已支付"),

	/**
	 * 手机号与银行预留的不一致
	 */
	PHONENOBANKCARD("10054", "手机预留号错误，请核实手机号码"),
	/**
	 * 身份信息错误
	 */
	IDCARDERROR("10055", "身份信息错误，请核实身份信息"),
	
	
	

	
	/**
	 * 密码出错已达上限，请稍后登录。
	 */
	INVALID_PASSWORD_ERR("10060","密码出错已达上限，请稍后登录"),
	
	SIGN_EMPTY("10061","签名值不能为空"),
	INVALID_SIGN("10062","签名验证失败"),
	
	SESSIONEXPIRE("10063","会话过期,请重新登录再试"),
	/**
	 * 手机号已绑定
	 */
	YES_BIND_PHONE("10064", "手机号已绑定超过次数"),
	/**
	 * 绑定失败
	 */
	BINDFAIL("10065", "手机号未绑定"),
	
	FILENOTEXITS("10067", "文件不存在"),
	DICNOTEXISTS("10068", "上传目录不存在"),
	FILESIZETOOLARGE("10069", "上传文件大小超过限制"),
	FILETYPEERROR("10070", "上传文件扩展名是不允许的扩展名"),
	NOWRITEAUTHORITY("10071", "上传目录没有写权限"),
	PAYPASSWORDERROR("10072", "支付密码错误"),
	ACCOUNTNOTEXISTS("10073", "资金账号不存在"),
	
	REALNAME_AUTH_ERROR("10076", "实名认证失败"),
	NOREALNAME_AUTH("10079", "未通过实名认证"),
	INVALID_REALNAME_AUTH("10080", "为保证账户资金安全,只能绑定认证用户本人的银行卡"),
	
	
	BANKCODE_BIND_FAIL("10077", "银行卡绑定失败"),
	BANKCODE_UNBINDLING_FAIL("10078", "银行卡解绑失败"),
	NOSUPPORT_BANK("10081","您的银行不支持此功能，请重新输入"),
	BANKBIND("10081","银行卡已绑定"),
	
	TRANSFER_ERROR("20001", "转账人数太多,请稍后再试"),
	TRANSFER_USER_ERROR("20002", "双方账号相同,请核对后重试"),
	NOT_SUFFIC_PAY("20003", "账户余额最多可转%s元"),
	TARGET_USER_EMPTY("20004", "无匹配的账户"),
	
	TRANSFER_NOTICE_SUCCESS("20005", "您的一笔转帐到%s(尾号%s)的交易已成功打款，实际到账以银行为准"),
	TRANSFER_NOTICE_FAIL("20006", "您的一笔转账到%s(尾号%s)的交易失败"),
	
	RECHARGE_ERROR("30001", "充值人数太多,请稍后再试"),
	
	
	
	RED_PKG_COUNT_ERR("40001", "收红包人人数与红包个数不符"),
	RED_PKG_AMT_NOT_ENOUGH("40002", "红包金额分配不足"),
	RED_PKG_SETTING_ERR("40003", "红包参数配置错误"),
	RED_PKG_AMT_TOO_LARGE("40004", "红包金额超出限额"),
	RED_PKG_ACCOUNT_TOO_LARGE("40005", "账户余额不足"),
	RED_PKG_ROB_FAIL("40006", "下手太慢，自己发一个吧！"),
	RED_PUSH("40007", "发红包消息推送"),
	
	WITHDRAW_ERROR("50000", "提现人数太多,请稍后再试"),

	WITHDRAW_NOTICE_SUCCESS("50001", "您的一笔提现到%s(尾号%s)的交易已成功打款，实际到账以银行为准"),
	WITHDRAW_NOTICE_FAIL("50002", "您的一笔提现到%s(尾号%s)的交易失败"),
	CREDIT_PAY_NOTICE_SUCCESS("50003", "您的一笔到%s（尾号%s）的还款已成功到账,请及时查询银行到账信息。"),
//	CREDIT_PAY_NOTICE_FAIL("50004", "您的一笔到%s[%s]的还款银行处理失败，请确认信息后重试"),
	CREDIT_PAY_NOTICE_FAIL("50004", "您的一笔订单号为%s的还款，银行处理失败，请确认信息后重试！"),
	
	
	LOGIN_DISABLED("60000", "登录失效"),
	SYSTEM_MESSAGE("60001", "系统消息"),
	ACTIVITY_MESSAGE("60002", "活动消息"),
	TRADE_MESSAGE("60003", "交易消息"),
	SCAN_RESULT("60004","扫码结果"),
	SCAN_PAYER("60005","扫码人"),
	SYSTEM_NOTICE("60006","系统公告"),
	PAY_RESULT("60007","支付结果"),
	BINDCARD_PUSH("60008","解绑卡推送"),
	;
	
	private String code;
	private String name;

	private CodeDesc(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String code() {
		return code;
	}

	public String text() {
		return name;
	}

}
