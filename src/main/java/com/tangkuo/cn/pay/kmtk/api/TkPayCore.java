package com.tangkuo.cn.pay.kmtk.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class TkPayCore {

    /** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (StringUtils.isEmpty(value) || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("signType")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
    	// 第一步：把字典按Key的字母顺序排序,参数使用TreeMap已经完成排序
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        
        // 第二步：把所有参数名和参数值串在一起
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            String value = params.get(key);
            if (!StringUtils.isEmpty(value)) {
            	sb.append(key).append("=").append(value);
            }
        }
        return sb.toString();
    }
    
    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String newCreateLinkString(Map<String, Object> params) {
    	// 第一步：把字典按Key的字母顺序排序,参数使用TreeMap已经完成排序
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        
        // 第二步：把所有参数名和参数值串在一起
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            Object value = params.get(key);
            if (null != value) {
            	sb.append(key).append("=").append(value);
            }
        }
        return sb.toString();
    }
}
