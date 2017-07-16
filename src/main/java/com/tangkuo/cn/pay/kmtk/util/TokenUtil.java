package com.tangkuo.cn.pay.kmtk.util;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tangkuo.cn.pay.kmtk.netbank.common.util.Constant;
import com.tangkuo.cn.pay.kmtk.netbank.common.util.Md5Utils;


/**
 * 通信token工具类
 */
public class TokenUtil {
	
	private static char[] str = { 'a', 'Q', 'R', 'S', '3', 'T', 'U', 'V', 'W', 'b', 'c', 'd', 'e', 'k', 'l', 'm', 'n',
			'o', '6', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'f', 'g', 'h', 'i', 'j', 'B', 'C',
			'4', 'D', 'E', 'F', 'G', 'H', 'I', 'J', '5', 'K', 'L', 'M', '7', 'N', 'O', 'P', 'X', 'Y', 'Z', '2' };

	/**
	 * 生产token：由登录账号、终端类型和时间戳进过MD5生产
	 * 
	 * @param loginId 登录账号
	 * @param app 手机唯一标识
	 * @return
	 */
	public static String createToken(String loginId) {
		String agent = "andorid";
		String head = getRequest().getHeader(Constant.UA);// 请求头UA信息
		if (head.toLowerCase().contains("iphone")) {
			agent = "ios";
		}
		return Md5Utils.encryptMD5(loginId + agent + System.currentTimeMillis());
	}
	
	/**
	 * 获取当前请求
	 * @return
	 */
	private static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
	}
	
	/**
	 * 生成随机密码
	 *
	 * @param pwd_len
	 *            生成的密码的总长度
	 * @return 密码的字符串
	 */
	public static String random(int length) {
		// 35是因为数组是从0开始的，26个字母+7个数字
		final int maxNum = 58;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
	
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		while (count < length) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(random.nextInt(maxNum)); // 生成的数最大为33-1
			if (i >= 0 && i < str.length) {
				sb.append(str[i]);
				count++;
			}
		}
		return sb.toString();
	}

	/**
	 * 创建服务器时间
	 * @return
	 */
	public static long currentTimeMillis() {
		return Utilities.millisToSeconds(System.currentTimeMillis());
	}
}
