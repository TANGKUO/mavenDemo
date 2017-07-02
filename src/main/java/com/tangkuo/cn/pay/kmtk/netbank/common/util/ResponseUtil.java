package com.tangkuo.cn.pay.kmtk.netbank.common.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class ResponseUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ResponseUtil.class);
	
	/**
	 * response 返回JSON格式字符串
	 * @param response
	 * @param value
	 */
	public static void addValue(HttpServletResponse response, String value){
		if(value != null) {
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			PrintWriter out  = null;
			try {
				out = response.getWriter();
				out.print(value);
			} catch (IOException e) {
				if (logger.isDebugEnabled()) {
					logger.debug("response输出Json异常", e);
				}
			}finally{
				if(out != null){
					out.flush();
					out.close();
				}
			}
		}
	}
	
	
	/**
	 * response 返回JSON格式字符串
	 * @param response
	 * @param object
	 */
	public static void addValue(HttpServletResponse response, Object object){
		if (object != null){
			String jsonstr  = JSONObject.toJSONString(object);
			ResponseUtil.addValue(response, jsonstr);
		}
	}
	

}
