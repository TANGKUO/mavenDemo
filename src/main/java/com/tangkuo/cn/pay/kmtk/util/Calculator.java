package com.tangkuo.cn.pay.kmtk.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import org.apache.commons.lang3.StringUtils;

public final class Calculator {

    /**
     * 方法说明：加法运算
     * 
     * @param x
     * @param y
     * @return
     */
    public static double add(double x, double y) {
        BigDecimal bigX = new BigDecimal(x);
        BigDecimal bigY = new BigDecimal(y);
        return bigX.add(bigY).doubleValue();
    }

    /**
     * 方法说明：减法运算
     * 
     * @param x
     * @param y
     * @return
     */
    public static double subtract(double x, double y) {
        BigDecimal bigX = new BigDecimal(x);
        BigDecimal bigY = new BigDecimal(y);
        return bigX.subtract(bigY).doubleValue();
    }

    /**
     * 方法说明： 乘法运算
     * 
     * @param x
     * @param y
     * @return
     */
    public static double multiply(double x, double y) {
        BigDecimal bigX = new BigDecimal(String.valueOf(x));
        BigDecimal bigY = new BigDecimal(String.valueOf(y));
        return bigX.multiply(bigY).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }

    /**
     * 方法说明： 乘法运算
     * 
     * @param x
     * @param y
     * @return
     */
    public static long multiply2(long x, double y) {
        BigDecimal bigX = new BigDecimal(String.valueOf(x));
        BigDecimal bigY = new BigDecimal(String.valueOf(y));
        return bigX.multiply(bigY).setScale(2, BigDecimal.ROUND_HALF_EVEN).longValue();
    }

    /**
     * 方法说明： 除法运算(两位小数，四舍五入)
     * 
     * @param x
     * @param y 为0则返回0
     * @return
     */
    public static double divide(double x, double y) {
        if (y == 0) {
            return 0;
        }
        BigDecimal bigX = new BigDecimal(String.valueOf(x));
        BigDecimal bigY = new BigDecimal(String.valueOf(y));
        return bigX.divide(bigY, 2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }

    /**
     * 方法说明：减法运算
     * 
     * @param x
     * @param y
     * @return
     */
    public static long subtractLong(long x, long y) {
        BigInteger bigX = new BigInteger(String.valueOf(x));
        BigInteger bigY = new BigInteger(String.valueOf(y));
        return bigX.subtract(bigY).longValue();
    }

    /**
     * 方法说明：金额元转为分<br>
     * 
     * @param amount
     * @return
     */
    public static long jmoney(String amount) {
        if (StringUtils.isEmpty(amount)) {
            return 0;
        }
        if (amount.indexOf(",") != -1) {
            amount = amount.replace(",", "");
        }
        BigDecimal decimal = new BigDecimal(amount);
        return decimal.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_EVEN).longValue();
    }

    /**
     * 方法说明：金额元转为分<br>
     * 
     * @param amount
     * @return
     */
    public static long jmoney(double amount) {
        if (amount <= 0) {
            return 0;
        }
        BigDecimal decimal = new BigDecimal(amount);
        return decimal.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_EVEN).longValue();
    }

    /**
     * 方法说明：金额分转为元 除法运算(两位小数，四舍五入)
     * 
     * @param amount 金额（单位为分）
     * @param n 小数点位数
     * @return
     */
    public static String fmoney(long amount, int n) {
        BigDecimal decimal = new BigDecimal(amount);
        return String.valueOf(decimal.divide(new BigDecimal(100), n, RoundingMode.HALF_UP));
    }

    /**
     * 方法说明：金额分转为元, 2小数点位数 除法运算(两位小数，四舍五入)
     * 
     * @param amount 金额（单位为分）
     * @return
     */
    public static double fmoney(long amount) {
        if (amount <= 0) {
            return 0D;
        }
        BigDecimal decimal = new BigDecimal(amount);
        return decimal.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 方法说明：金额分转为元 除法运算(两位小数，四舍五入)
     * 
     * @param amount 金额（单位为分）
     * @param n 小数点位数
     * @return
     */
    public static String fmoney(String amount, int n) {
        if (StringUtils.isEmpty(amount)) {
            return "0.00";
        }
        BigDecimal decimal = new BigDecimal(amount);
        return String.valueOf(decimal.divide(new BigDecimal(100), n, RoundingMode.HALF_UP));
    }

    /**
     * 方法说明：金额分转为万元 除法运算(两位小数，四舍五入)
     * 
     * @param amount 金额（单位为分）
     * @param n 小数点位数
     * @return
     */
    public static String wmoney(String amount, int n) {
        if (StringUtils.isEmpty(amount)) {
            return "0.00";
        }
        BigDecimal decimal = new BigDecimal(amount);
        return String.valueOf(decimal.divide(new BigDecimal(1000000), n, RoundingMode.HALF_UP));
    }

    /**
     * 方法说明：金额万元转为分 乘法运算
     * 
     * @param amount 金额（单位为万）
     * @return
     */
    public static long wmoney(String amount) {
        if (StringUtils.isEmpty(amount)) {
            return 0;
        }
        BigDecimal decimal = new BigDecimal(amount);
        return decimal.multiply(new BigDecimal(1000000)).setScale(0, BigDecimal.ROUND_HALF_EVEN).longValue();
    }

}
