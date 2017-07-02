package com.tangkuo.cn.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 格式显示
 * 
 * @ClassName: MoneyUtils
 */
public class MoneyUtils {
	/**
	 * 金额显示国际化格式 55555.33 =55.555.33
	 * 
	 * @Title: format
	 * @param @param num
	 * @param @return 设定参数
	 * @return String 返回类型
	 * @throws
	 */
	public static String format(BigDecimal num) {

		if (null == num || num.doubleValue() == 0) {
			return "0.00";
		}

		NumberFormat f = new DecimalFormat("#,###.##");

		String result = f.format(num);

		if (result.indexOf(".") == -1) {
			result += ".00";
		}

		if (result.charAt(result.length() - 2) == '.') {
			result += "0";
		}

		return result;
	}

	public static void main(String[] args) {
		System.out.println("0 >= " + format(new BigDecimal(0)));
		System.out.println("5.2 >= " + format(new BigDecimal(5.2)));
		System.out.println("6.0 >= " + format(new BigDecimal(6.0)));
		System.out.println("5.1 >= " + format(new BigDecimal(5.1)));
		System.out.println("3.33333333 >= "
				+ format(new BigDecimal(3.33333333)));
		System.out.println("3333333.3333333 >= "
				+ format(new BigDecimal(3333333.3333333)));
	}
}
