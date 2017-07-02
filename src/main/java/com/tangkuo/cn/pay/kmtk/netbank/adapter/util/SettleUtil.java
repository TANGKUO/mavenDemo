package com.kame.micropay.netbank.service.adapter.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kame.micropay.commons.FileUtil;
import com.kame.micropay.commons.FileUtil.FilePermissionLevel;
import com.kame.micropay.commons.TtyException;
import com.kame.micropay.commons.config.properties.Property;
import com.kame.micropay.commons.util.DateUtil;
import com.kame.micropay.netbank.enums.TradeType;
import com.kame.micropay.netbank.service.commons.Constants;


/** 
* @ClassName: SettleUtil 
* @Description: 对账工具类
* @author fubin
* @date 2015年9月8日 下午3:03:02 
*  
*/ 
public class SettleUtil {
	
	private static final Logger log = LoggerFactory.getLogger(SettleUtil.class);
	
	public static String rootPath = Property.getProperty(Constants.SETTLE_DOWNLOAD_PATH);
	
	public static String fileType = Property.getProperty(Constants.SETTLE_DOWNLOAD_FILETYPE);
	
	/** 
	* @Title: getTradeType 
	* @Description: 根据银行返回的交易类型进行转换
	* @param @param type
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/ 
	public static String getTradeType(String type) {
		if("Sale".equals(type)) return TradeType.B2C_PAY.name(); //abc
		if("21".equals(type)) return TradeType.B2C_PAY.name(); //yspay
		if("22".equals(type)) return TradeType.B2C_SINGLEREFUND.name(); //yspay
		
		return StringUtils.EMPTY;
	}
	
	public static void generateSettleFile(String bankCode,Date settleDate,String data) throws TtyException {
		if(StringUtils.isEmpty(bankCode) || settleDate == null || StringUtils.isEmpty(data)){
			log.error("the parameters is not allow null.");
			return ;
		}
		//首先，检查根路径和文件后缀是否存在，没有赋值一个默认的
		if(StringUtils.isEmpty(rootPath)){
			rootPath = "/apps/kmpay/download/";
		}
		if(StringUtils.isEmpty(fileType)){
			fileType = "TXT";
		}
		//根据根目录来检测是否存在，不存在就创建
		StringBuilder file = new StringBuilder(rootPath);
		file.append(File.separatorChar);
		file.append(bankCode);
		String _settleDateStr = DateUtil.format(settleDate, DateUtil.YYYYMMDD);
		//file.append(File.separatorChar);
		//file.append(_settleDateStr);
		File dir = new File(file.toString());
		//将目录赋值全部权限
		FileUtil.updateFilePermission(dir.getParentFile(), FilePermissionLevel.ALL);
		if(!dir.exists()){
			if(!dir.getParentFile().exists()){
				dir.mkdirs();
				if(log.isInfoEnabled()){
					log.info("生成银行返回的对账数据文件目录,: "+dir.getAbsolutePath());
				}
			}else{				
				dir.mkdir();
			}
		}
		//将目录赋值全部权限
		FileUtil.updateFilePermission(dir, FilePermissionLevel.ALL);
		//拼成文件
		file.append(File.separatorChar);
		file.append(_settleDateStr+"."+fileType.toUpperCase());
		//生成文件.
		try {
			File f = new File(file.toString());
			if(f.exists()){
				f.delete();
			}
			f.createNewFile();
			if(log.isInfoEnabled()){
				log.info("生成银行返回的对账数据文件,: "+f.getAbsolutePath());
			}
			FileUtils.write(f, data);
		} catch (IOException e) {
			throw new TtyException(e);
		}
	}
	
	/** 
	* @Title: generateSettletFile 
	* @Description: 根据银行返回的数据生成对账的数据文件
	* @param bankCode : 银行编码
	* @param settleDate ： 对账日期
	* @param data ： 对账返回的数据
	* @return void    返回类型 
	* @throws TtyException 生成文件异常 
	* @author Bill Huang
	* @see http://blog.csdn.net/qiaqia609/article/details/11048463
	* @see http://docs.oracle.com/javase/6/docs/api/java/io/File.html
	*/
	public static void generateSettleFile(String bankCode, String gateWayName, Date settleDate, String data) throws TtyException{
		if(StringUtils.isEmpty(bankCode) || StringUtils.isEmpty(gateWayName) || settleDate == null || StringUtils.isEmpty(data)){
			log.error("the parameters is not allow null.");
			return ;
		}
		//首先，检查根路径和文件后缀是否存在，没有赋值一个默认的
		if(StringUtils.isEmpty(rootPath)){
			rootPath = "/apps/kmpay/download/";
		}
		if(StringUtils.isEmpty(fileType)){
			fileType = "TXT";
		}
		//根据根目录来检测是否存在，不存在就创建
		StringBuilder file = new StringBuilder(rootPath);
		file.append(File.separatorChar);
		file.append(bankCode);
		file.append(File.separatorChar);
		file.append(gateWayName);
		String _settleDateStr = DateUtil.format(settleDate, DateUtil.YYYYMMDD);
		//file.append(File.separatorChar);
		//file.append(_settleDateStr);
		File dir = new File(file.toString());
		//将目录赋值全部权限
		FileUtil.updateFilePermission(dir.getParentFile(), FilePermissionLevel.ALL);
		if(!dir.exists()){
			if(!dir.getParentFile().exists()){
				dir.mkdirs();
				if(log.isInfoEnabled()){
					log.info("生成银行返回的对账数据文件目录,: "+dir.getAbsolutePath());
				}
			}else{				
				dir.mkdir();
			}
		}
		//将目录赋值全部权限
		FileUtil.updateFilePermission(dir, FilePermissionLevel.ALL);
		//拼成文件
		file.append(File.separatorChar);
		file.append(_settleDateStr+"."+fileType.toUpperCase());
		//生成文件.
		try {
			File f = new File(file.toString());
			if(f.exists()){
				f.delete();
			}
			f.createNewFile();
			if(log.isInfoEnabled()){
				log.info("生成银行返回的对账数据文件,: "+f.getAbsolutePath());
			}
			FileUtils.write(f, data);
		} catch (IOException e) {
			throw new TtyException(e);
		}
	}
	
	/** 
	* @Title: generateSettletFile 
	* @Description: 根据银行返回的数据生成对账的数据文件
	* @param bankCode : 银行编码
	* @param settleDate ： 对账日期
	* @param data ： 对账返回的数据
	* @return void    返回类型 
	* @throws TtyException 生成文件异常 
	* @author Bill Huang
	*/
	public static void generateSettleFile(String bankCode, String gateWayName, String settleDate, String data) throws TtyException{
		if(StringUtils.isEmpty(bankCode) || StringUtils.isEmpty(gateWayName) || StringUtils.isEmpty(settleDate) || StringUtils.isEmpty(data)){
			log.error("the parameters is not allow null.");
			return ;
		}
		try{
			Date _settleDate = DateUtil.parse(settleDate, DateUtil.YYYYMMDD);
			generateSettleFile(bankCode, gateWayName, _settleDate, data);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new TtyException(e);
		}
	}
	
/*	public String getDownloadPath(String bankCode, InputStream in) {
		try {
			File file = new File(Property.getProperty("SETTLE.DOWNLOAD.PATH") + "/" + bankCode + "/" 
					+ DateUtil.getCDateString(DateUtil.YYYYMMDDHHMMSS));
			if(!file.exists()) file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
}
