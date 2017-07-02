package com.tangkuo.cn.pay.kmtk.netbank.common.util;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Random;
/**
 * 
* @ClassName: Utils
* @Description: ()
* @author tangkuo
* @date 2017年7月2日 下午1:55:00
*
 */
public class Utils {

	/** 可以用于判断Object,String,Map,Collection,String,Array是否为空 */
    public static boolean isNull(Object value) {
        if (value == null) {
            return true;
        } else if(value instanceof String){
            if(((String)value).trim().replaceAll("\\s", "").equals("")){
                return true;
            }
        }else if(value instanceof Collection) {
            if(((Collection)value).isEmpty()){
                return true;
            }
        } else if (value instanceof Object[]) {
            Object[] object = (Object[]) value;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNull(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        } else if(value.getClass().isArray()) {
            if(Array.getLength(value) == 0){
                return true;
            }
        } else if(value instanceof Map) {
            if(((Map)value).isEmpty()){
                return true;
            }
        } else {
            return false;
        }
        return false;

    }



    public static boolean isNull(Object value, Object... items){
        if (isNull(value) || isNull(items)) {
            return true;
        }
        for (Object item : items) {
            if (isNull(item)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotNull(Object value){

        return !isNull(value);
    }

    public static boolean isNotNull(Object value, Object... items){

        return !isNull(value,items);
    }


    public static boolean Null(Object value, Object... items){
        if (isNull(value) || isNull(items)) {
            return true;
        }
        for (Object item : items) {
            if (isNull(item)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 进行加法运算
     * @param d1
     * @param d2
     * @return
     */
    public static double add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.add(b2).doubleValue();
    }
    
    /**
     * 方法说明：加法运算
     * 
     * @param x
     * @param y
     * @return
     */
    public static long addLong(long x, long y) {
        BigInteger bigX = new BigInteger(String.valueOf(x));
        BigInteger bigY = new BigInteger(String.valueOf(y));
        return bigX.add(bigY).longValue();
    }

    /**
     * 进行减法运算
     * @param d1
     * @param d2
     * @return
     */
    public static double sub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 进行乘法运算
     * @param d1
     * @param d2
     * @return
     */
    public static double mul(double d1, double d2){
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 进行除法运算
     * @param d1
     * @param d2
     * @param len
     * @return
     */
    public static double div(double d1,double d2,int len) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2,len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 进行四舍五入操作
     * @param d
     * @param len
     * @return
     */
    public static double round(double d,int len) {
        BigDecimal b1 = new BigDecimal(d);
        BigDecimal b2 = new BigDecimal(1);
        // 任何一个数字除以1都是原数字
        // ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作
        return b1.divide(b2, len,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
	 * 生成订单流水号，格式为日期+时间
	 */
    public static String RecordIDGenerater() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(new Date());
    }
    
    /***
	 * 产生一个随机数
	 * @param i
	 * @return
	 */
	public static int random(int i){
		Random  r=new Random();
		int k=r.nextInt(i);
		while(k<1000){
			k=r.nextInt(i);
		}
		return k;
	}

}
