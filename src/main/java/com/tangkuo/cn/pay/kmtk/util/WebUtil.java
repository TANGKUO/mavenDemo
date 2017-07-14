package com.tangkuo.cn.pay.kmtk.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Session;
import com.tangkuo.cn.pay.kmtk.netbank.adapter.util.JsonUtil;
import com.verywork.framework2.web.json.JsonMessage;
import com.verywork.framework2.web.servlet.HttpServletHolder;

import ocx.AESWithJCE;

/**
 * 工具类
 *
 */
public final class WebUtil {

	private static final Logger log = LoggerFactory.getLogger(WebUtil.class);

	/**
	 * 
	 * @param response
	 * @param jms
	 */
	public static Object toResponse(HttpServletResponse response, JsonMessage jms) {
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.toJSONString(jms));
		} catch (IOException e1) {
			if (log.isDebugEnabled()) {
				log.debug("系统异常", e1);
			}
		} finally {
			if (null != writer) {
				writer.flush();
				writer.close();
			}
		}
		return null;
	}

	public static Object toResponse(HttpServletResponse response, String content) {
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(content);
		} catch (IOException e1) {
			if (log.isDebugEnabled()) {
				log.debug("系统异常", e1);
			}
		} finally {
			if (null != writer) {
				writer.flush();
				writer.close();
			}
		}
		return null;
	}

	/**
	 * 获得当前请求 request
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return HttpServletHolder.getCurrentRequest();
	}

	/**
	 * 获得当前请求 response
	 * 
	 * @return
	 */
	public static HttpServletResponse getResponse() {
		return HttpServletHolder.getCurrentResponse();
	}

	/**
	 * 获取当前 Session
	 * 
	 * @return
	 */
	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * 获取请求浏览器信息
	 * 
	 * @return
	 */
	public static String getUserAgent() {
		HttpServletRequest request = getRequest();
		return request.getHeader("user-agent");
	}

	/**
	 * 获取门户地址
	 * 
	 * @return
	 */
	public static String getWebpath() {
		HttpServletRequest request = getRequest();
		String path = request.getContextPath();
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	}

	/**
	 * 获得调用IP
	 * 
	 * @return
	 */
	public static String getIpAddr() {
		HttpServletRequest request = getRequest();
		if (null != request) {
			String ip = request.getHeader("x-forwarded-for");
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				return request.getRemoteAddr();
			}
			return ip;
		}
		return null;
	}

	public static boolean isValidAgent() {
		String userAgent = getUserAgent();
		if (!StringUtils.isEmpty(userAgent)) {
			log.debug("通过userAgent解析出机型：" + userAgent);
			if (userAgent.indexOf("Windows") == -1) {
				return false;
			}
			if (userAgent.indexOf("Chrome") >= 0 && userAgent.indexOf("Edge") == -1) {
				return false;
			}
		}

		return true;
	}

	public static boolean passwordCtrchck() {
		String userAgent = getUserAgent();
		if (!StringUtils.isEmpty(userAgent)) {
			log.debug("通过userAgent解析出机型：" + userAgent);
			if (userAgent.indexOf("Chrome") >= 0 && userAgent.indexOf("Edge") == -1) {
				return false;
			}
			/*
			 * if(userAgent.indexOf("Trident") >= 0){ return false; }
			 */
		}
		return true;
	}

}
