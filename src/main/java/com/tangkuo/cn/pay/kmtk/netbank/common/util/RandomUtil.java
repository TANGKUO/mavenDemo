package com.tangkuo.cn.pay.kmtk.netbank.common.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 
* @ClassName: RandomUtil
* @Description: (随机数工具)
* @author tangkuo
* @date 2017年7月2日 下午1:49:34
*
 */
public class RandomUtil {

    /**
     * 随机生成六位长度的手机验证码
     * 
     * @return
     */
    public static String createRandom() {
    	if (Constant.RADOM_OFF.equals(Constant.RADOM_TAB)) {
			return Constant.DEV_DEFAULT_PWD_VALUE;
		}
    	return RandomStringUtils.randomNumeric(6);
    }
    
    /**
     * 8位验证消费码
     * @return
     */
    public static String createOrderChkNum() {
    	if (Constant.RADOM_OFF.equals(Constant.RADOM_TAB)) {
			return Constant.DEV_DEFAULT_PWD_VALUE;
		}
		return RandomStringUtils.randomNumeric(8);
    }

   /* public static void main(String[] args) {
		//System.out.println(RandomStringUtils.random(32, true, true));
	}*/
}
