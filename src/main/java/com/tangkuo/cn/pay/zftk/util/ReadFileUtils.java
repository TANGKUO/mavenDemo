package com.tangkuo.cn.pay.zftk.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: ReadFileUtils
 * @Description: (读取文件工具类实现)
 * @author tangkuo
 * @date 2017年7月9日 下午7:39:17
 *
 */
public class ReadFileUtils {
	private static Logger logger = LoggerFactory.getLogger(ReadFileUtils.class);

	// 根据key读取value
	public static String readValue(String filePath, String key) {
		Properties ps = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			ps.load(in);
			String value = ps.getProperty(key);
			logger.info("ReadFileUtils.readValue key:" + key + ", value:" + value);
			return value;
		} catch (Exception e) {
			logger.error("ReadFileUtils.readValue is error, return errorMessage:", e);
			return StringUtils.EMPTY;
		}
	}
}
