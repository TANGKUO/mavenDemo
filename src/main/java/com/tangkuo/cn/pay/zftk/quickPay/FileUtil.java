package com.tangkuo.cn.pay.zftk.quickPay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @描述：上传、下载文件；压缩、解压缩文件；加密、解密文件；对url进行加密；得到文件摘要
 */
public class FileUtil {
	static Log log = LogFactory.getLog(FileUtil.class);

	/**
	 * 文件加密
	 */
	public static boolean fileEncode(String sourceFileUrl, String distFileUrl, String threeDesKey) {
		OutputStream outputStream = null;
		CipherOutputStream cos = null;

		try {
			File sourceFile = new File(sourceFileUrl);
			File encryptFile = new File(distFileUrl);
			outputStream = new FileOutputStream(encryptFile);
			// 对文件进行3des 加密
			cos = Des3Tools.encryptMode(outputStream, threeDesKey);

			IOUtils.copy(new FileInputStream(sourceFile), cos);
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(cos);
		}
		return true;

	}

	/**
	 * 文件解密
	 */
	public static boolean fileDecode(String sourceFileUrl, String distFileUrl, String threeDesKey) {
		// Config con = new Config();
		InputStream inputStream = null;
		CipherInputStream cos = null;
		try {
			inputStream = new FileInputStream(sourceFileUrl);
			// 对文件进行3des 解密
			cos = Des3Tools.decryptMode(inputStream, threeDesKey);
			IOUtils.copy(cos, new FileOutputStream(distFileUrl));
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(cos);
		}
		return true;
	}

}
