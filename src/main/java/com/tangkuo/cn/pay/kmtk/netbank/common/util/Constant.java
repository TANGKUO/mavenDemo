package com.tangkuo.cn.pay.kmtk.netbank.common.util;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	// 短信返回值通用错误码
	private static Map<String, String> smsMap = new HashMap<String, String>();

	public static String getSmsMsg(String smsCode) {
		if (smsMap.containsKey(smsCode)) {
			return smsMap.get(smsCode);
		} else {
			return smsCode + "未知错误，请到短信平台官网进行查看！";
		}
	}

	// 终端类型枚举类
	public static final String TERMINAL_ENUM = "class com.guocui.tty.api.enums.TerminalEnum";
	// 图片所属源枚举类
	public static final String SOURCE_TYPE = "class com.guocui.tty.settings.enums.SourceType";
	// 会员状态枚举类
	public static final String MEMBER_STATUS = "class com.guocui.tty.authc.enums.MemberStatus";
	// 在线状态枚举类
	public static final String MEM_LINE_STATUS = "class com.guocui.tty.authc.enums.MemLineStatus";
	// 会员类型枚举类
	public static final String MEMBER_TYPE = "class com.guocui.tty.member.enums.MemberType";
	// 图片的使用枚举类
	public static final String USE_TYPE = "class com.guocui.tty.settings.enums.UseType";
	// 图片的详细使用枚举类
	public static final String SUB_USE_TYPE = "class com.guocui.tty.settings.enums.SubUseType";
	// 产品类型
	public static final String PRO_TYPE = "class com.guocui.tty.news.enums.ProductType";
	// 业务类型
	public static final String BUSI_TYPE = "class com.guocui.tty.order.enums.BusinessType";
	// 订单类型
	public static final String ORD_STATUS = "class com.guocui.tty.order.enums.OrderStatus";
	// 支付状态
	public static final String PAY_STATUS = "class com.guocui.tty.pay.enums.PayStatus";
	// 币种类型
	public static final String CCY_STATUS = "class com.guocui.tty.pay.enums.CcyType";
	// 支付类型
	public static final String PAYMENT_TYPE = "class com.guocui.tty.pay.enums.PaymentType";
	// 性别
	public static final String SEX_TYPE = "class com.guocui.tty.member.enums.SexType";
	// 评论状态
	public static final String CHK_STATUS = "class com.guocui.tty.order.enums.ChkStatus";
	// 广告状态
	public static final String AD_STATUS = "class com.guocui.tty.news.enums.AdStatus";
	// 讯息类型
	public static final String TIP_TYPE = "class com.guocui.tty.news.enums.TipType";
	// 促销类型
	public static final String RECOM_TYPE = "class com.guocui.tty.product.enums.RecommendType";
	// s审核状态
	public static final String ADJUST_STATUS = "class com.guocui.tty.member.enums.AdjustStatus";
	// 审核类型
	public static final String ADJUST_TYPE = "class com.guocui.tty.member.enums.AdjustType";
	// 小费类型
	public static final String FEE_REASON = "class com.guocui.tty.order.enums.FeeReason";
	// 退款状态
	public static final String REFUND_STATUS = "class com.guocui.tty.order.enums.RefundStatus";
	// 银行状态
	public static final String BANK_STATUS = "class com.guocui.tty.settings.enums.BankStatus";
	// 广告类型
	public static final String AD_TYPE = "class com.guocui.tty.news.enums.AdType";
	// 子业务类型
	public static final String SUB_BUSI_TYPE = "class com.guocui.tty.order.enums.SubBusinessType";
	// 产品状态
	public static final String PRODUCT_STATUS = "class com.guocui.tty.news.enums.PublishStatus";

	public static final String SERI_ID = "serialVersionUID";
	// 系统访问URL
	public static final String SYSTEM_URL = Property.getProperty("SYSTEM_URL");
	// 登录账号参数
	public static final String LOGIN_ID = "loginId";
	// 随机码配置参数
	public static final String RADOM_SEC_TIME = "RADOM_SEC_TIME";
	// 验证码最大有效时长参数
	public static final int MAX_SEC = Integer.parseInt(Property.getProperty(RADOM_SEC_TIME));
	// 平台商户10000
	public static final String PLAT_MERCHANT_ID = "10000";
	// token参数定义
	public static final String USER_AGENT = "userAgent";
	public static final String BAIDU_USER = "userId";
	public static final String CHANNEL_ID = "channelId";

	// token开关参数
	public static final String TOKEN_TAB_P = "TOKEN_TAB";
	// token开关值
	public static final String TOKEN_TAB = Property.getProperty(TOKEN_TAB_P);
	// radom开关参数
	public static final String RADOM_TAB_P = "RADOM_TAB";
	// radom开关值
	public static final String RADOM_TAB = Property.getProperty(RADOM_TAB_P);
	// sign开关参数
	public static final String SIGN_TAB_P = "SIGN_TAB";
	// sign开关值
	public static final String SIGN_TAB = Property.getProperty(SIGN_TAB_P);
	// token开
	public static final String TOKEN_ON = "on";
	// token关
	public static final String TOKEN_OFF = "off";
	// SIGN开
	public static final String SIGN_ON = "on";
	// SIGN关
	public static final String SIGN_OFF = "off";
	// RADOM开
	public static final String RADOM_ON = "on";
	// RADOM关
	public static final String RADOM_OFF = "off";
	// token配置变量参数
	public static final String TOKEN_DAY_TIME = "TOKEN_DAY_TIME";
	// token配置变量参数值
	public static final int DAY_STEP = Integer.parseInt(Property.getProperty(TOKEN_DAY_TIME));
	// 验证码key前缀标志符
	public static final String CHECK_NUM_TAG = "CNT_";
	// 顶级文件目录
	public static final String BASE_PATH = "file";
	// windows ngnix服务文件目录
	public static final String FILE_SYS_PATH = "filesystem.basepath";
	public static final String WIN_NGNIX_PATH = Property.getProperty(FILE_SYS_PATH);
	// 静态顶级文件目录
	public static final String BASE_STATIC_PATH = "static";
	// file文本框参数
	public static final String FORM_FILE = "file";
	// 订单记录ID参数
	public static final String ORDER_ID = "orderId";
	// 订单记录NO参数
	public static final String ORDER_NO = "orderNo";
	// 订单消费验证码
	public static final String ORDER_CHK_NUM = "orderChkNum";
	// 银卡绑定记录ID参数
	public static final String BANK_ID = "bankId";
	// 公告 记录ID参数
	public static final String TIP_ID = "tipId";
	// 收藏记录ID参数
	public static final String COLLECT_ID = "collectId";
	// 优惠记录ID参数
	public static final String PREFER_ID = "preferentialId";
	// 订单记录ID参数
	public static final String CRITICISM_ID = "criticismId";
	// 订单商品记录ID参数
	public static final String ITEM_ID = "itemId";
	// 会员（登录）记录ID参数
	public static final String MEMBER_ID = "memberId";
	// 商户详细记录ID参数
	public static final String MERCHANT_ID = "merchantId";
	// 普通会员详细记录ID参数
	public static final String INFORMATION_ID = "informationId";
	// 顾问详细记录ID参数
	public static final String ADVISOR_ID = "counselorId";
	// 顾问详细价格参数
	public static final String ADVISOR_WORTH = "worth";
	// 商品详细记录ID参数
	public static final String PRODUCT_ID = "productId";
	// 商品类型记录ID参数
	public static final String PRODUCT_TYPE_ID = "productTypeId";
	// 房间详细记录ID参数
	public static final String ROOM_ID = "roomId";
	// 房间类型类型记录ID参数
	public static final String ROOM_TYPE_ID = "roomTypeId";
	// 退款ID参数
	public static final String REFUND_ID = "refundId";
	// 退款说明参数
	public static final String REFUND_DESC = "rfdDesc";
	// 手机号参数
	public static final String MOBILE = "mobile";
	// 图片文件ID参数
	public static final String IMG_ID = "imgId";
	// 广告ID参数
	public static final String AD_ID = "adId";
	// 金额区间配置ID参数
	public static final String AMT_CONFIG_ID = "amtConfigId";
	// 图片文件ID参数
	public static final String URL = "url";
	// 订单业务类型参数
	public static final String BUSINESS_TYPE = "businessType";
	// 业务类型集合参数
	public static final String BT_LIST = "btList";
	// 订单业务类型参数
	public static final String FEE_REASON_PARAM = "feeReason";
	// 会员类型参数
	public static final String MEM_TYPE_PARAM = "memberType";
	// 退款状态
	public static final String REFUND_STATUS_PARAM = "refundStatus";
	// 状态
	public static final String STATUS_PARAM = "status";
	// 订单状态参数
	public static final String ORDER_STATUS = "orderStatus";
	// 可用状态参数
	public static final String USE_STATUS = "useStatus";
	// 商品类型父节点ID参数
	public static final String PARENT_ID = "parentId";
	// 城市记录ID参数
	public static final String CITY_ID = "cityId";
	// 支付密码参数
	public static final String MEMBER_PAY_PWD = "paypass";
	// 新密码参数
	public static final String NEW_PWD = "newPwd";
	// 旧密码参数
	public static final String OLD_PWD = "oldPwd";
	// 默认图片
	public static final String IMG_DEFAULT = "/file/file_default.png";
	// 文件的绝对路径参数
	public static final String PATH = "path";
	// 图片所属记录ID参数
	public static final String SOURCE_OBJ_ID = "sourceObjId";
	// 所属记录ID参数
	public static final String BELONG_OBJ_ID = "belongObjId";
	// 图片的用途参数
	public static final String USE_TYPE_PARAM = "useType";
	// 图片的用途参数
	public static final String SUB_USE_TYPE_PARAM = "subUseType";
	// 文件资源类型参数
	public static final String SOURCE_TYPE_PARAM = "sourceType";
	// 接口配置名称前缀
	public static final String GATE_TAG = "GATE";
	// 接口方法参数
	public static final String GATE_MD = "mtd";
	// 应用类型参数
	public static final String GATE_AP = "app";
	// 接口方法登录人
	public static final String LOGIN_NAME = "loginName";
	// 系统统一分隔符
	public static final String GATE_SP = "`";
	// 签名参数
	public static final String GATE_SN = "sign";
	// 终端类型密钥后缀
	public static final String KEY_SUFFIX = "_KEY";
	// 随机码键
	public static final String RADOM_KEY = "key";
	// 随机码值
	public static final String RADOM_VALUE = "value";
	// 源ID参数
	public static final String O_ID = "oId";
	// 新ID参数
	public static final String N_ID = "nId";
	// 每页大小参数
	public static final String PAGE_SIZE = "pageSize";
	// 页码参数
	public static final String PAGE_NO = "pageNo";
	// 批量属性字段
	public static final String ITEMS = "items";
	// 金额参数
	public static final String AMOUNT = "amount";
	// 开始日期参数
	public static final String START_DATE = "startDate";
	// 结束日期参数
	public static final String END_DATE = "endDate";
	// 收款方费率
	public static final String SELL_FEE_PERCENT = "SELL_FEE_PERCENT";
	// 付款方费率
	public static final String BUY_FEE_PERCENT = "BUY_FEE_PERCENT";
	// 枚举类未知表示常量
	public static final String ENUM_NULL_VALUE = "未知";
	// 公告已过期常量
	public static final String EXPIRE_DATE_VALUE = "已过期";
	// 公告未生效常量
	public static final String INACTIVE_DATE_VALUE = "未生效";
	// 公告已生效常量
	public static final String ACTIVE_DATE_VALUE = "已生效";
	// 开发制定默认密码常量
	public static final String DEV_DEFAULT_PWD_VALUE = "123456";
	// 下载app支持的系统类参数
	public static final String OS_TYPE = "os_type";
	// 下载app路径参数
	public static final String APP_DL_PATH = "app_dl_path";
	// http请求头UA参数
	public static final String UA = "user-agent";

	static {
		smsMap.put("105100", "短信服务请求异常");
		smsMap.put("105101", "url关键参数为空");
		smsMap.put("105102", "号码不合法");
		smsMap.put("105103", "没有通道类别");
		smsMap.put("105104", "该类别为冻结状态");
		smsMap.put("105105", "没有足够金额");
		smsMap.put("105106", "不是国内手机号码并且不是国际电话");
		smsMap.put("105107", "黑名单");
		smsMap.put("105108", "含非法关键字");
		smsMap.put("105109", "该通道类型没有第三方通道");
		smsMap.put("105110", "短信模板ID不存在");
		smsMap.put("105111", "短信模板未审核通过");
		smsMap.put("105112", "短信模板替换个数与实际参数个数不匹配");
		smsMap.put("105113", "短信模板ID为空");
		smsMap.put("105114", "短信内容为空");
		smsMap.put("105115", "短信类型长度应为1");
		smsMap.put("105116", "同一天同一用户不能发超过3条相同的短信");
		smsMap.put("105117", "模板ID含非法字符");
		smsMap.put("105118", "短信模板有替换内容，但参数为空");
		smsMap.put("105119", "短信模板替换内容过长，不能超过70个字符");
		smsMap.put("105120", "手机号码不能超过100个");
		smsMap.put("105121", "短信模板已删除");
		smsMap.put("105122", "同一天同一用户不能发超过10条验证码");
		smsMap.put("105123", "短信模板名称为空");
		smsMap.put("105124", "短信模板内容为空");
		smsMap.put("105125", "创建短信模板失败");
		smsMap.put("105126", "短信模板名称错误");
		smsMap.put("105127", "短信模板内容错误");
		smsMap.put("105128", "短信模板id为空");
		smsMap.put("105129", "短信模板id不存在");
		smsMap.put("103123", "未上线应用不能超过100个client");
		smsMap.put("103124", "已经是开通状态");
		smsMap.put("103125", "子账号余额不足");
		smsMap.put("103126", "未上线应用或demo只能使用白名单中号码");
		smsMap.put("103127", "测试demo不能创建子账号");
		smsMap.put("105130", "30秒内不能连续发同样的内容");
		smsMap.put("105131", "30秒内不能给同一号码发送相同模板消息");
		smsMap.put("105132", "验证码短信参数长度不能超过10位");
	}

}
