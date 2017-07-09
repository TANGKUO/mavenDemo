package com.tangkuo.cn.pay.zftk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextHelper {
	private static Logger logger = LoggerFactory.getLogger(TextHelper.class);

	/**
	 * 
	* @Title: getAllLine
	* @Description: (这里用一句话描述这个方法的作用)
	* @param @param filepath
	* @param @return    设定文件
	* @return List<String>    返回类型
	* @throws
	 */
	public static List<String> getAllLine(String filepath) {
		InputStream is = TextHelper.class.getResourceAsStream(filepath);
		if (is == null) {
			throw new RuntimeException(filepath + " is not find.");
		}
		InputStreamReader isr;
		try {
			isr = new InputStreamReader(is, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("unsupported encoding. defualt is utf-8");
		}
		BufferedReader br = new BufferedReader(isr);
		List<String> result = new ArrayList<String>();
		String str;
		try {
			while ((str = br.readLine()) != null) {
				result.add(str);
			}
			br.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public static void main(String[] args) {
		List<String> re = getAllLine("/valueList.cfg");
		for (String a : re) {
			System.out.println(a);
		}
		logger.info("--------ok");
	}
}
