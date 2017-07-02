package com.kame.micropay.commons.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 采用的是apache commons-net架包中的ftp工具类实现的
 * 
 * @author chiyong
 * 
 */
public class FtpCollectionUtil {
	private String username;
	private String password;
	private String ftpHostName;
	private int port = 21;
	private FTPClient ftpClient = new FTPClient();
	private FileOutputStream fos = null;
	private final Logger log = LoggerFactory.getLogger(getClass());

	public FtpCollectionUtil(String username, String password, String ftpHostName, int port) {
		super();
		this.username = username;
		this.password = password;
		this.ftpHostName = ftpHostName;
		this.port = port;
	}

	/**
	 * 建立连接b
	 */
	private void connect() {
		try {
			log.info("开始连接");
			// 连接
			ftpClient.connect(ftpHostName, port);
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
			}
			// 登录
			ftpClient.login(username, password);
			ftpClient.setBufferSize(256);

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			ftpClient.setControlEncoding("utf8");
			log.info("登录成功！");
			log.info("开始登录！");
		} catch (SocketException e) {
			log.error("", e);
		} catch (IOException e) {
			log.error("", e);
		}

	}

	/**
	 * 关闭输入输出流
	 * 
	 * @param fos
	 */
	private void close(FileOutputStream fos) {
		try {
			if (fos != null) {
				fos.flush();
				fos.close();
			}

			ftpClient.logout();
			log.info("退出登录");
			ftpClient.disconnect();
			log.info("关闭连接");
		} catch (IOException e) {
			log.error("关闭异常:", e);
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param ftpFileName
	 * @param localDir
	 */
	public void down(String ftpFileName, String localDir) {
		connect();
		downFileOrDir(ftpFileName, localDir);
		close(fos);
	}

	private void downFileOrDir(String ftpFileName, String localDir) {
		try {
			File file = new File(ftpFileName);

			File temp = new File(localDir);

			if (!temp.exists()) {
				temp.mkdirs();
			}
			log.debug("远程文件路径: ["+file.getAbsolutePath()+"]");
			log.debug("本地文件路径: ["+temp.getAbsolutePath()+"]");
			// 判断是否是目录
			if (isDir(ftpFileName)) {
				String[] names = ftpClient.listNames();
				for (int i = 0; i < names.length; i++) {
					log.debug("["+names[i] + "] ^^^^^^^^^^^^^^ ");
					if (isDir(names[i])) {
						downFileOrDir(ftpFileName + '/' + names[i], localDir
								+ File.separator + names[i]);
						ftpClient.changeToParentDirectory();
					} else {
						File localfile = new File(localDir + File.separator
								+ names[i]);
						if (!localfile.exists()) {
							fos = new FileOutputStream(localfile);
							ftpClient.retrieveFile(names[i], fos);

						} else {
							log.info("开始删除isDir(ftpFileName)==true文件,路径："+localfile.getAbsolutePath());
							localfile.delete();
							log.info("文件已经删除");
							fos = new FileOutputStream(localfile);
							ftpClient.retrieveFile(ftpFileName, fos);

						}

					}
				}
			} else {

				File localfile = new File(localDir + File.separator + file.getName());
				if (!localfile.exists()) {
					fos = new FileOutputStream(localfile);
					ftpClient.retrieveFile(ftpFileName, fos);

				} else {
					log.info("开始删除isDir(ftpFileName)==false文件,路径："+localfile.getAbsolutePath());
					localfile.delete();
					log.info("文件已经删除");
					fos = new FileOutputStream(localfile);
					ftpClient.retrieveFile(ftpFileName, fos);

				}
				ftpClient.changeToParentDirectory();

			}

			log.info("下载成功！");
		} catch (SocketException e) {
			log.error("连接失败！", e);
		} catch (IOException e) {
			log.error("下载失败！", e);
		}

	}

	// 判断是否是目录
	public boolean isDir(String fileName) {
		try {
			// 切换目录，若当前是目录则返回true,否则返回true。
			boolean falg = ftpClient.changeWorkingDirectory(fileName);
			return falg;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("判断失败", e);
		}

		return false;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFtpHostName() {
		return ftpHostName;
	}

	public void setFtpHostName(String ftpHostName) {
		this.ftpHostName = ftpHostName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public static void main(String[] args) {
		/**
		 * apache common-net实现的
		 */
		FtpCollectionUtil ftpUtil = new FtpCollectionUtil("webfocus1",
				"webfocus1", "192.168.0.2", 21);

		// /home/webfocus1/apache-tomcat-6.0.37/webapps/NEZA_ROOT/要下载的文件夹。
		ftpUtil.down(
				"/home/webfocus1/apache-tomcat-6.0.37/webapps/NEZA_ROOT/",
				"D://a");

	}
}