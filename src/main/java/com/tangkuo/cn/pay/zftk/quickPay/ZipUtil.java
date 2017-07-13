package com.tangkuo.cn.pay.zftk.quickPay;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ZipUtil {
	private static final Log log = LogFactory.getLog(ZipUtil.class);
	
	public static final String DOT = ".";
	public static final String EXT = "zip";
	/**
	 * 压缩文件
	 * 
	 * @param srcfile
	 *            File[] 需要压缩的文件列表
	 * @param zipfile
	 *            File 压缩后的文件
	 */
	public static void zipFiles(List<File> srcfile, File zipfile) {
		byte[] buf = new byte[1024];
		try {
			// Create the ZIP file
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					zipfile));
			// Compress the files
			for (int i = 0; i < srcfile.size(); i++) {
				File file = srcfile.get(i);
				FileInputStream in = new FileInputStream(file);
				// Add ZIP entry to output stream.
				out.putNextEntry(new ZipEntry(file.getName()));
				// Transfer bytes from the file to the ZIP file
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				// Complete the entry
				out.closeEntry();
				in.close();
			}
			// Complete the ZIP file
			out.close();
		} catch (IOException e) {
			log.error("ZipUtil zipFiles exception:" + e);
		}
	}
	
	public static void zipFiles(List<File> srcfile, File zipfile, String encoding) {
		byte[] buf = new byte[1024];
		try {
			if(StringUtils.isBlank(encoding)){
				encoding = "UTF-8";
			}
			// Create the ZIP file
			org.apache.tools.zip.ZipOutputStream out = new org.apache.tools.zip.ZipOutputStream(new FileOutputStream(
					zipfile));
			// Compress the files
			for (int i = 0; i < srcfile.size(); i++) {
				File file = srcfile.get(i);
				FileInputStream in = new FileInputStream(file);
				// Add ZIP entry to output stream.
				out.putNextEntry(new org.apache.tools.zip.ZipEntry(file.getName()));
				// Transfer bytes from the file to the ZIP file
				out.setEncoding(encoding);
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				// Complete the entry
				out.closeEntry();
				in.close();
			}
			// Complete the ZIP file
			out.close();
		} catch (IOException e) {
			log.error("ZipUtil zipFiles exception:" + e);
		}
	}
	
	
	public static void compress(String path, boolean delete) throws Exception {
		File file = new File(path);
		int dotIndex = path.indexOf(".");
		if(dotIndex != -1){
			path = path.substring(0, dotIndex);
		}
		File zipfile = new File(path + DOT + EXT);
		zipFiles(file, zipfile, delete);
	}
	/**
	 * 压缩文件
	 * 
	 * @param srcfile
	 *            File[] 需要压缩的文件列表
	 * @param zipfile
	 *            File 压缩后的文件
	 */
	public static void zipFiles(File srcfile, File zipfile, Boolean delete) {
		byte[] buf = new byte[1024];
		try {
			// Create the ZIP file
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					zipfile));
			// Compress the files			
			FileInputStream in = new FileInputStream(srcfile);
			// Add ZIP entry to output stream.
			out.putNextEntry(new ZipEntry(srcfile.getName()));			
			// Transfer bytes from the file to the ZIP file
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}						
			// Complete the entry
			out.closeEntry();
			in.close();		
			// Complete the ZIP file
			out.close();
			//delete the file
			if (delete) {
				ZipUtil.deleteFile(srcfile);
			}
		} catch (IOException e) {
			log.error("ZipUtil zipFiles exception:" + e);
		}
	}
	/**
	 * 解压缩
	 * 
	 * @param zipfile
	 *            File 需要解压缩的文件
	 * @param descDir
	 *            String 解压后的目标目录
	 */
	public static void unZipFiles(File zipfile, String descDir) {
		try {
			// Open the ZIP file
			ZipFile zf = new ZipFile(zipfile);
			for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {
				// Get the entry name
				ZipEntry entry = ((ZipEntry) entries.nextElement());
				String zipEntryName = entry.getName();
				InputStream in = zf.getInputStream(entry);
				// System.out.println(zipEntryName);
				OutputStream out = new FileOutputStream(descDir + zipEntryName);
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
				// Close the file and stream
				in.close();
				out.close();
			}
		} catch (IOException e) {
			log.error("ZipUtil unZipFiles exception:" + e);
		}
	}

	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		List<File> srcfile = new ArrayList<File>();
		srcfile.add(new File("d:\1.xls"));
		srcfile.add(new File("d:\2.xls"));
		/*File srcfile = new File("d:\\1.xls");*/
		File zipfile = new File("d:\\edm.zip");
		ZipUtil.zipFiles(srcfile, zipfile);
	}

	/**
	 * 下载文件
	 * 
	 * @param response
	 * @param serverPath
	 * @param str
	 * @return
	 */
	public static boolean downloadFile(HttpServletResponse response,
			String serverPath, String str) {
		boolean flag = false;
		try {
			String path = serverPath + str;
			File file = new File(path);
			if (file.exists()) {
				InputStream ins = new FileInputStream(path);
				BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
				OutputStream outs = response.getOutputStream();// 获取文件输出IO流
				BufferedOutputStream bouts = new BufferedOutputStream(outs);
				response.setContentType("application/x-download");// 设置response内容的类型
				response.setHeader("Content-disposition",
						"attachment;filename=" + URLEncoder.encode(str, "GBK"));// 设置头部信息
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				// 开始向网络传输文件流
				while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
					bouts.write(buffer, 0, bytesRead);
				}
				bouts.flush();// 这里一定要调用flush()方法
				ins.close();
				bins.close();
				outs.close();
				bouts.close();
				flag = true;
			} else {
				log.warn("File does not exist url："+path);
				// response.sendRedirect("../error.jsp");
				return false;
			}
		} catch (IOException e) {
			log.error("File download execption", e);
		}
		return flag;
	}
	
	/**
	 * 下载网络文件
	 * 
	 * @param urlStr
	 * @param localPath
	 * @param localName
	 * @return
	 */
	public static boolean downloadHttpFile(String urlStr, String localPath, String localName) {
        try {
        	File file1 = new File(localPath + localName);
			if (file1.exists()) {
				return true;
			}
        	File file = new File(localPath);
			if (!file.exists()) {
				file.mkdirs();
			}
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(localPath + localName);
            byte[] buffer = new byte[1204];
            int bytesum = 0;
            int byteread = 0;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
            }
            return true;
        } catch (Exception e) {
            log.error("downloadHttpFile", e);
            return false;
        }
    }
	
	/**
	 * 下载ZIP文件
	 * 
	 * @param response
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static boolean downloadZipFile(HttpServletResponse response,
			String path, String fileName) {
		boolean flag = false;
		try {			
			File file = new File(path);
			if (file.exists()) {
				InputStream ins = new FileInputStream(path);
				BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
				OutputStream outs = response.getOutputStream();// 获取文件输出IO流
				BufferedOutputStream bouts = new BufferedOutputStream(outs);
				response.setContentType("application/x-download");// 设置response内容的类型
				response.setHeader("Content-disposition",
						"attachment;filename=" + URLEncoder.encode(fileName, "GBK"));// 设置头部信息
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				// 开始向网络传输文件流
				while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
					bouts.write(buffer, 0, bytesRead);
				}
				bouts.flush();// 这里一定要调用flush()方法
				ins.close();
				bins.close();
				outs.close();
				bouts.close();
				flag = true;
			} else {
				log.warn("File does not exist url："+path);
				// response.sendRedirect("../error.jsp");
				return false;
			}
		} catch (IOException e) {
			log.error("File download execption", e);
		}
		return flag;
	}

	/**
	 * 下载文件
	 * 
	 * @param response
	 * @param serverPath
	 * @param str
	 * @return
	 */
	public static boolean deleteFile(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				return file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
			return file.delete();
		} else {
			System.out.println("所删除的文件不存在！" + '\n');
			return false;
		}
	}

	
	/**
	 * @Title: zip 
	 * @Description: 压缩专用，其他程序请勿调用
	 * @param @param sourceFileUrl
	 * @param @param distFileUrl
	 * @param @param fileName
	 * @param @return 设定参数 
	 * @return boolean 返回类型 
	 * @throws
	 */
	public static boolean zip(String sourceFileUrl, String distFileUrl,
			String fileName) {
		OutputStream outputStream = null;
		ZipOutputStream zos = null;
		try {
			// Config con = new Config();
			// 压缩后文件路径
			outputStream = new FileOutputStream(distFileUrl);
			zos = new ZipOutputStream(outputStream);
			// 压缩文件中的文件名字
			zos.putNextEntry(new ZipEntry(new String(fileName)));
			// 文件压缩前路径
			IOUtils.copy(new FileInputStream(new File(sourceFileUrl)), zos);
			// IOUtils.copy(new FileInputStream(sourceFileUrl), zos);

		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			/** 注意关闭流的顺序，不能颠倒 */
			IOUtils.closeQuietly(zos);
			IOUtils.closeQuietly(outputStream);

		}
		return true;
	}
}