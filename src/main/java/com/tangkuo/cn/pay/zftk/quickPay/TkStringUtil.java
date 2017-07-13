package com.tangkuo.cn.pay.zftk.quickPay;

/**
 * @ClassName StringUtil
 * @Description
 */

public class TkStringUtil {
	
	public final static String fileSplit = System.getProperty("file.separator");
	
	public static boolean isEmpty(String str) {
		if (null == str || 0 == str.length() || "null".equals(str)) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @Title: fill
	 * @Description:
	 * @param way
	 *            补齐方向，0-左补0，1-右补空格
	 * @param length
	 *            补足长度
	 * @param str
	 *            源字符串
	 * @return String
	 */
	public static String fill(int way, int length, String str) {
		if (isEmpty(str))
			str = "";

		int lengthTemp = length - TkInstructionUtil.getStrLen(str);
		if (0 == way) {
			for (int i = 0; i < lengthTemp; i++) {
				str = "0" + str;
			}
		} else if (1 == way) {
			for (int i = 0; i < lengthTemp; i++) {
				str += " ";
			}
		}

		return str;
	}
}
