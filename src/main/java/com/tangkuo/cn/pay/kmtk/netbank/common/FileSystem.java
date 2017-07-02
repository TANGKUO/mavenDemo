package com.tangkuo.cn.pay.kmtk.netbank.common;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;


/**
 * 文件系统服务, 读/写
 * 
 */
public class FileSystem {
	private Logger log = LoggerFactory.getLogger(FileSystem.class);

	@Resource
	private String filePrefixPath;

	/**
	 * 方法说明：写
	 * 
	 * @param data
	 * @param targetPath
	 */
	@Async
	public void write(byte[] data, String targetPath) {
		if (StringUtils.isEmpty(filePrefixPath) || null == data || data.length == 0) {
			return;
		}
		if (!filePrefixPath.endsWith(File.separator)) {
			filePrefixPath += File.separator;
		}
		if (targetPath.startsWith(File.separator)) {
			targetPath = targetPath.substring(1);
		}
		String dirPath = targetPath.substring(0,
				targetPath.lastIndexOf(File.separator) + 1);
		File dir = new File(filePrefixPath + dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new ByteArrayInputStream(data);
			out = new FileOutputStream(new File(filePrefixPath + targetPath));
			int b = 0;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 方法说明：读取
	 * 
	 * @param targetPath
	 * @return
	 */
	public byte[] read(String targetPath) {
		if (StringUtils.isEmpty(targetPath)) {
			return null;
		}
		if (!filePrefixPath.endsWith(File.separator)) {
			filePrefixPath += File.separator;
		}
		if (targetPath.startsWith(File.separator)) {
			targetPath = targetPath.substring(1);
		}
		if (log.isDebugEnabled()) {
			log.debug("下载文件目录: " + filePrefixPath + targetPath);
		}
		File file = new File(filePrefixPath + targetPath);
		if (!file.exists()) {
			if (log.isDebugEnabled()) {
				log.debug("无法找到下载文件目录: " + filePrefixPath + targetPath);
			}
			return null;
		}

		BufferedInputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			out = new ByteArrayOutputStream();
			int b = 0;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			if (log.isDebugEnabled()) {
				log.debug("读取文件大小: " + out.size());
			}
			return out.toByteArray();
		} catch (IOException e) {
			if (log.isErrorEnabled()) {
				log.error("文件读取失败!", e);
			}
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				if (log.isErrorEnabled()) {
					log.error("文件读取流关闭失败!", e);
				}
			}
		}
		return null;
	}

	/**
	 * 读取，返回输入流
	 * 
	 * @param targetPath
	 * @return
	 * @throws FileNotFoundException
	 */
	public InputStream readFile(String targetPath) throws FileNotFoundException {
		if (StringUtils.isEmpty(filePrefixPath)) {
			return null;
		}
		if (!filePrefixPath.endsWith(File.separator)) {
			filePrefixPath += File.separator;
		}
		if (targetPath.startsWith(File.separator)) {
			targetPath = targetPath.substring(1);
		}
		File file = new File(filePrefixPath + targetPath);
		if (!file.exists()) {
			return null;
		}
		return new BufferedInputStream(new FileInputStream(file));
	}
}
