package com.tangkuo.cn.pay.zftk.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PayGateWayUtil {
	private static Log log = LogFactory.getLog(PayGateWayUtil.class);

	/**
	 * 判断日期格式是否正确 实现逻辑： 1.将String类型转换成Date，如果不能转换，肯定错误
	 * 2.将转换后的date转换成String，如果不等，
	 * 则逻辑错误：如format为"yyyy-MM-dd",date为"2017-07-31",转换为日期后再转换回字符串为
	 * "2017-07-03",说明格式虽然对,但日期逻辑上不对.
	 * 
	 * @param date
	 *            需校验的字符串型数据
	 * @param format
	 *            校验格式
	 * @return
	 */
	public static boolean checkDate(String date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		Date temDate = null;
		try {
			temDate = df.parse(date);
		} catch (Exception e) {
			// 如果不能转换,肯定是错误格式
			return false;
		}
		String reStr = df.format(temDate);
		return date.equals(reStr);
	}

	/**
	 * 
	* @Title: getQueryParams
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param request
	* @param @return    设定文件
	* @return Map<String,String>    返回类型
	* @throws
	 */
	public static Map<String, String> getQueryParams(HttpServletRequest request) {
		Map<String, String> paramsMap = new HashMap<String, String>();

		String QueryMessage = request.getParameter("QueryMessage");
		String Digest = request.getParameter("Digest");
		String M_ID = request.getParameter("M_ID");

		paramsMap.put("QueryMessage", QueryMessage);
		paramsMap.put("Digest", Digest);
		paramsMap.put("M_ID", M_ID);

		return paramsMap;
	}

	/**
	 * 
	* @Title: getRequest
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param req
	* @param @param strName
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public static String getRequest(HttpServletRequest req, String strName) {
		String reqStr = null;
		if (null != req) {
			reqStr = req.getParameter(strName);
			if (null == reqStr || "".equals(reqStr)) {
				reqStr = "";
			} else {
				reqStr = reqStr.replaceAll(".*([';]+|(--)+).*", " ");
			}
		}
		return reqStr;
	}

	/**
	 * 返回xml信息
	 * 
	 * @param msg
	 *            返回字段数组
	 * @param isQueryFailed
	 *            是否成功查询
	 * @return xml
	 */
	public static String returnXmlForV3(String[] msg, boolean isQueryFailed) {
		StringBuilder sb = new StringBuilder();
		if (isQueryFailed) { // 查询失败
			sb.append("<?xml version='1.0' encoding='UTF-8' ?>").append("<dinpay>").append("<response>")
					.append("<is_success>").append("F").append("</is_success>").append("<error_code>").append(msg[0])
					.append("</error_code>").append("</response>").append("</dinpay>");
		} else { // 查询成功

			sb.append("<?xml version='1.0' encoding='UTF-8' ?>").append("<dinpay>").append("<response>")
					.append("<is_success>").append("T").append("</is_success>").append("<sign_type>").append(msg[8])
					.append("</sign_type>").append("<sign>").append(msg[0]).append("</sign>").append("<trade>")
					.append("<merchant_code>").append(msg[1]).append("</merchant_code>").append("<order_amount>")
					.append(msg[2]).append("</order_amount>").append("<order_no>").append(msg[3]).append("</order_no>")
					.append("<order_time>").append(msg[4]).append("</order_time>").append("<trade_no>").append(msg[5])
					.append("</trade_no>").append("<trade_status>").append(msg[6]).append("</trade_status>")
					.append("<trade_time>").append(msg[7]).append("</trade_time>").append("</trade>")
					.append("</response>").append("</dinpay>");
		}

		return sb.toString();
	}

	/**
	 * 
	* @Title: returnXml
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param OrderData
	* @param @param Sign
	* @param @param Code
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public static String returnXml(String OrderData, String Sign, String Code) {

		StringBuffer buf = new StringBuffer("");
		buf.append("<?xml version='1.0' encoding='UTF-8' ?>").append("<dinpay><orderMessage>").append("<OrderData>")
				.append(OrderData).append("</OrderData>").append("<Sign>").append(Sign).append("</Sign>")
				.append("<Code>").append(Code).append("</Code>").append("</orderMessage></dinpay>");
		return buf.toString();
	}

	/**
	 * 获取请求头内容，为了防止敏感数据的泄露，去掉获取所有请求参数
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestParamts(HttpServletRequest request) {
		StringBuilder params = new StringBuilder("");
		Enumeration e = request.getHeaderNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String value = request.getHeader(name);
			params.append(name).append("=").append(value);
			params.append("|");
		}
		return params.toString();
	}

	/**
	 * 从request对象中获取所有对象，并获取跳转前地址和客户IP
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public static Map<String, String> getRequestParams(HttpServletRequest request) {
		Map<String, String> paramsMap = new HashMap<String, String>();

		// 从request获取跳转前地址和客户IP
		String request_url = getRequestHead(request, "Referer");
		String customerIp = IPUtils.getClientIP(request);
		paramsMap.put("request_url", request_url);
		paramsMap.put("customerIp", customerIp);

		// 循环获取所有参数
		Enumeration attNames = request.getParameterNames();
		while (attNames.hasMoreElements()) {
			String attName = (String) attNames.nextElement();
			paramsMap.put(attName, request.getParameter(attName));
		}
		return paramsMap;
	}

	/**
	 * 获取请求头，并将javaScript过滤转义
	 * 
	 * @param request
	 * @param headName
	 * @return
	 */
	public static String getRequestHead(HttpServletRequest request, String headName) {
		String headStr = null;
		if (null != request) {
			headStr = request.getHeader(headName);
			if (StringUtils.isNotEmpty(headStr)) {
				headStr = StringEscapeUtils.escapeJavaScript(headStr);
			}
		}
		return headStr;
	}

	/**
	 * 获取map中的值 @Title: getRequest @Description: TODO @param @param
	 * mapRequest @param @param strName @param @return 设定参数 @return String
	 * 返回类型 @throws
	 */
	public static String getMapVal(Map<String, String> mapRequest, String strName) {
		String reqStr = null;
		if (null != mapRequest) {
			reqStr = mapRequest.get(strName);
			if (null == reqStr) {
				reqStr = "";
			}
		}
		return reqStr;
	}

	/**
	 * 返回输出 @Title: outPrintContent @Description: TODO @param @param
	 * content @param @param response 设定参数 @return void 返回类型 @throws
	 */
	public static void outPrintContent(HttpServletResponse response, String content) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(content);
			out.flush();
		} catch (IOException e) {
			log.error("outprint content error : " + e);
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}
}
