package com.tangkuo.cn.utils;

import com.tangkuo.cn.utils.configuration.PropertiesUtils;

public class ConfigUtil {

	private static final String FILENAME_CONFIGINFO = "fileDirectory.properties";
	
	
	// 商家文件上传地址
	public static String MERCHANT_PATH = null;
	// 代理商文件上传地址
	public static String PROXY_PATH = null;
	// 会员文件上传地址
	public static String MERBER_PATH= null;
	// 商家文件查看地址
	public static String MERCHANT_LOOK_PATH = null;
	// 代理商文件查看地址
	public static String PROXY_LOOK_PATH = null;
	// 商家收款通道地址
	public static String MERCHANT_PAY_CHANNEL = null;
	
	//会员文件查看地址
	public static String MEMBER_LOOK_PATH = null;
	
	// 二维码文件上传地址
	public static String QRCODE_UPLOAD_PATH = null;
	
	//二维码文件查看地址
	public static String QRCODE_LOOK_PATH = null;
	
	//上传文件默认保存路径
	public static String DEFAULT_UPLOAD_PATH = "/usr/local/dpp_file";
	
	//临时文件父目录
	public static String TMP_FILE_PARENT_PATH = null;
	
	//文件分块大小（字节），默认102400，即100KB
	public static long FILE_SPLIT_SIZE = 102400;
	
	public static String DOWNLOAD_FILE_PATH=null;
	
	//商家证书保存路径
	public static String MERCHANT_CERTIFICATE_PATH = null;
	
	static {
		initParameters();
	}

	private static void initParameters() {
		MERBER_PATH=PropertiesUtils.getProperty(FILENAME_CONFIGINFO,"merberUploadPath");
		MERCHANT_PATH = PropertiesUtils.getProperty(FILENAME_CONFIGINFO,"merchantUploadPath");
		PROXY_PATH = PropertiesUtils.getProperty(FILENAME_CONFIGINFO,"proxyUploadPath");
		MERCHANT_LOOK_PATH = PropertiesUtils.getProperty(FILENAME_CONFIGINFO,"merchantLookPath");
		PROXY_LOOK_PATH = PropertiesUtils.getProperty(FILENAME_CONFIGINFO,"proxyLookPath");
		
		MERCHANT_PAY_CHANNEL = PropertiesUtils.getProperty(FILENAME_CONFIGINFO,"merchantPayChannel");
		
		DEFAULT_UPLOAD_PATH = PropertiesUtils.getProperty(FILENAME_CONFIGINFO,"defaultUploadPath");
		
		MEMBER_LOOK_PATH = PropertiesUtils.getProperty(FILENAME_CONFIGINFO,"memberLookPath");
		
		QRCODE_UPLOAD_PATH = PropertiesUtils.getProperty(FILENAME_CONFIGINFO,"qrCodeUploadPath");
		QRCODE_LOOK_PATH= PropertiesUtils.getProperty(FILENAME_CONFIGINFO,"qrCodeLookPath");
		
		TMP_FILE_PARENT_PATH = PropertiesUtils.getProperty(FILENAME_CONFIGINFO,"tmpFileParentPath");
		
		DOWNLOAD_FILE_PATH=PropertiesUtils.getProperty(FILENAME_CONFIGINFO,"downloadFilePath");
		
		MERCHANT_CERTIFICATE_PATH = PropertiesUtils.getProperty(FILENAME_CONFIGINFO,"merchantCertificatePath");
		
		try {
			if(!"".equals(PropertiesUtils.getProperty(FILENAME_CONFIGINFO,"fileSplitSize")))
				FILE_SPLIT_SIZE = Long.parseLong(PropertiesUtils.getProperty(FILENAME_CONFIGINFO,"fileSplitSize"));
		} catch (Exception e) {
			FILE_SPLIT_SIZE = 102400;
		}
	}
}
