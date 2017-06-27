package com.tangkuo.cn.pay.kmtk.netbank;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * 
* @ClassName: SFTPUtil
* @Description: (sftp服务器工具类 　不支持代理)
* @author tangkuo
* @date 2017年6月27日 上午10:53:38
*
 */

public class SFTPUtil {
	private final Logger logger = LoggerFactory.getLogger(SFTPUtil.class);
	private final boolean isDebug = logger.isDebugEnabled();

	private String ftpHost;
	private int ftpPort;
	private String ftpUserName;
	private String ftpPassword;
	private int timeout = 5 * 60 * 1000;

	private ChannelSftp sftp = null;
	private Session session = null;

	public SFTPUtil(String ftpHost, int ftpPort, String ftpUserName,
			String ftpPassword, int timeout) {
		this.ftpHost = ftpHost;
		this.ftpPassword = ftpPassword;
		this.ftpPort = ftpPort;
		this.ftpUserName = ftpUserName;
		if (timeout > 0) {
			this.timeout = timeout;
		}
	}

	/**
	 * 连接sftp服务器
	 * 
	 * @param ftpHost
	 *            主机
	 * @param ftpPort
	 *            端口
	 * @param ftpUserName
	 *            用户名
	 * @param ftpPassword
	 *            密码
	 * @param timeout
	 *            超时时间
	 * @return
	 */
	private void connect() throws Exception {
		try {
			JSch jsch = new JSch(); // 创建JSch对象
			session = jsch.getSession(ftpUserName, ftpHost, ftpPort); // 根据用户名，主机ip，端口获取一个Session对象
			if (isDebug) {
				logger.debug("Session created.");
			}
			if (ftpPassword != null) {
				session.setPassword(ftpPassword); // 设置密码
			}
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config); // 为Session对象设置properties
			session.setTimeout(timeout); // 设置timeout时间
			session.connect(); // 通过Session建立链接
			if (isDebug) {
				logger.debug("Session connected.");
				logger.debug("Opening Channel.");
			}

			Channel channel = session.openChannel("sftp"); // 打开SFTP通道
			channel.connect(); // 建立SFTP通道的连接
			if (isDebug) {
				logger.debug("Connected successfully to ftpHost = " + ftpHost
						+ ",as ftpUserName = " + ftpUserName + ", returning: "
						+ channel);
			}
			sftp = (ChannelSftp) channel;
		} catch (Exception ex) {
			logger.error("连接SFTP异常", ex);
			throw ex;
		}
	}

	/**
	 * 方法说明：<br>
	 * 关闭sftp连接
	 *
	 * @throws Exception
	 */
	private void closeChannel() {
		try {
			if (sftp != null) {
				sftp.disconnect();
			}
			if (session != null) {
				session.disconnect();
			}
		} catch (Exception ex) {
			logger.error("关闭SFTP连接异常", ex);
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 */
	public void upload(String directory, String uploadFile) throws Exception {
		try {
			// 连接sftp
			connect();
			cd(sftp, directory);// 打开目录
			File file = new File(uploadFile);
			sftp.put(new FileInputStream(file), file.getName());
			if (isDebug) {
				logger.debug("上传成功!");
			}
		} catch (Exception ex) {
			logger.error("上传文件异常", ex);
			throw ex;
		} finally {
			// 关闭sftp
			closeChannel();
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @param sftp
	 */
	public void download(String directory, String downloadFile, String saveFile) throws Exception {
		try {
			// 连接sftp
			connect();

			sftp.cd(directory);
			File file = new File(saveFile);
			sftp.get(downloadFile, new FileOutputStream(file));
			if (isDebug) {
				logger.debug("下载文件成功!");
			}
		} catch (Exception ex) {
			logger.error("下载文件异常", ex);
			throw ex;
		} finally {
			// 关闭sftp
			closeChannel();
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @param sftp
	 */
	@SuppressWarnings("unchecked")
	public String downloadByLike(String directory, String likeName, String savePath) throws Exception {
		try {
			// 连接sftp
			connect();

			String fileName = "";
			sftp.cd(directory);
			Vector<LsEntry> vector = sftp.ls(directory);
			for (LsEntry lsEntry : vector) {
				String name = lsEntry.getFilename();
				if (name.indexOf(likeName) != -1) {
					fileName = name;
					break;
				}
			}

			File file = new File(savePath + fileName);
			sftp.get(fileName, new FileOutputStream(file));
			if (isDebug) {
				logger.debug("下载文件成功!");
			}
			
			return fileName;
		} catch (Exception ex) {
			logger.error("下载文件异常", ex);
			throw ex;
		} finally {
			// 关闭sftp
			closeChannel();
		}
	}
	
	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @param sftp
	 */
	public void delete(String directory, String deleteFile) throws Exception {
		try {
			// 连接sftp
			connect();

			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception ex) {
			logger.error("删除文件异常", ex);
			throw ex;
		} finally {
			// 关闭sftp
			closeChannel();
		}
	}

	/**
	 * 方法说明：<br>
	 * 判断文件/文件夹是否存在
	 *
	 * @param sftp
	 * @param fileDir
	 * @return　false不存在
	 */
	@SuppressWarnings("rawtypes")
	private boolean exists(ChannelSftp sftp, String fileDir) {
		try {
			Vector content = sftp.ls(fileDir);
			if (content == null) {
				return false;
			}
			return true;
		} catch (SftpException ex) {
			if (isDebug) {
				logger.debug("文件/文件夹不存在:" + ex.getMessage());
			}
			return false;
		}
	}

	/**
	 * 方法说明：<br>
	 * 打开目录，如不存在则创建
	 *
	 * @param sftp
	 * @param fileDir
	 * @throws Exception
	 */
	private void cd(ChannelSftp sftp, String fileDir) throws Exception {
		try {
			fileDir = fileDir.replace("\\", "/");
			String[] dirs = fileDir.split("/");
			if (dirs != null && dirs.length > 0) {
				for (int i = 0; i < dirs.length; i++) {
					String dir = dirs[i];
					if (StringUtils.isEmpty(dir)) {
						continue;
					}
					// 判断目录是否存在
					boolean flag = exists(sftp, dir);
					if (!flag) {
						sftp.mkdir(dir);// 创建目录
					}
					sftp.cd(dir);// 打开目录
				}
			}
		} catch (Exception ex) {
			logger.error("打开目录异常", ex);
			throw ex;
		}

	}

}
