package com.tangkuo.cn.utils.string;

import java.util.UUID;
/**
 * 
* @ClassName: UuidGenerator
* @Description: (UUID 生成器)
* @author tangkuo
* @date 2017年7月2日 下午2:49:11
*
 */
public class UuidGenerator {


	/**
	 * 
	* @Title: getUuidGenerator
	* @Description: (生成唯一标示uuid)
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public static String getUuidGenerator(){
		String uuid = UUID.randomUUID().toString();   
		return uuid.replaceAll("-", "");  
	}

	/**
	 * 根据proxyId生成商家Id
	 * 
	 * @return
	 */
	public static String getMerchantIdByProxyId(String porxyId) {
		return new StringBuilder(porxyId).append(StringUtil.getRandomStrNum(2))
				.toString();
	}
}
