package com.tangkuo.cn.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

	private static Logger log = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 创建文件
	 * 
	 * @param filePath
	 * */
	public static boolean createFile(String filePath) {
		try {
			File file = new File(filePath);
			if(!file.exists()) {
				file.mkdirs();
			}
			
			return true;
		} catch (Exception e) {
			log.info("创建路径" + filePath + "失败！" + e.getMessage());
			return false;
		}
	}
	
	/**
	 * 创建文件
	 * 
	 * @param destFileName
	 *            目标文件完整路径
	 * */
	public static boolean createFile(String destFileName, int flag) {
		File file = new File(destFileName);
		if (file.exists()) {
			file.delete();
			createFile(destFileName, flag);
			return true;
		}

		// 判断目标文件所在的目录是否存在
		if (!file.getParentFile().exists()) {
			// System.out.println("创建" + file.getName() + "所在目录不存在，正在创建！");
			if (!file.getParentFile().mkdirs()) {// 判断父文件夹是否存在，如果存在则表示创建成功否则不成功
				log.info("创建目标文件所在的目录失败！");
				return false;
			}
		}
		// 创建目标文件
		try {
			if (file.createNewFile()) {// 调用createNewFile方法，判断创建指定文件是否成功
				log.info("创建文件" + destFileName + "成功！");
				return true;
			} else {
				log.info("创建文件" + destFileName + "失败！");
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.info("创建文件" + destFileName + "失败！" + e.getMessage());
			return false;
		}
	}

	/**
	 *获取指定目录下文件列表
	 * 
	 * @param dir 目标目录
	 */
	public static List<String> listFile(String dir) {
		List<String> fileList = new ArrayList<String>();
		if (!dir.endsWith(File.separator)) {
			dir += File.separator;
		}
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				log.info("存在异常文件夹："+ files[i].getPath());
				deleteFile(files[i].getPath());
			}else {
				fileList.add(files[i].getName());
			}
		}
		return fileList;
	}

	/**
	 * 删除文件
	 * 
	 * @param fileDir
	 *            删除单个文件
	 */
	public static boolean deleteFile(String fileDir) {
		File file = new File(fileDir);
		if (file.exists()) {
			file.delete();
			log.info("删除" + fileDir + "成功！");
			return true;
		}
		return false;
	}
	
}
