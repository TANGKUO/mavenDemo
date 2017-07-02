package com.tangkuo.cn.pay.kmtk.netbank.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5Utils {

    private static Logger log = LoggerFactory.getLogger(Md5Utils.class);
    private static final String SIGN_TYPE = "MD5";
    private static final String CHARSET_NAME = "UTF-8";
    public static final String salt = "3zsAa6W9gfSMMhPSlQTdWFUSHY3LS8Vb";
    

    /**
     * MD5加密
     * 
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String encryptMD5(byte[] data) {
        RequestCheckUtils.checkNotEmpty(data, "md5 data");
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance(SIGN_TYPE);
        } catch (NoSuchAlgorithmException e) {
            if (log.isDebugEnabled()) {
                log.debug("md5 加密异常", e);
            }
        }
        md5.update(data);
        return byte2hex(md5.digest());
    }

    /**
     * MD5加密
     * 
     * @param str
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String encryptMD5(String str) {
        RequestCheckUtils.checkNotEmpty(str, "md5 data");
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance(SIGN_TYPE);
            md5.update((str + salt).getBytes(CHARSET_NAME));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("md5 加密异常", e);
            }
        }
        return byte2hex(md5.digest());
    }

    /**
     * MD5加盐加密
     * 
     * @param str
     * @param salt
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String encryptMD5(String str, String salt) {
        RequestCheckUtils.checkNotEmpty(str, "md5 data");
        RequestCheckUtils.checkNotEmpty(salt, "md5 salt");
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance(SIGN_TYPE);
            md5.update((str + salt).getBytes(CHARSET_NAME));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("md5 加密异常", e);
            }
        }
        return byte2hex(md5.digest());
    }

    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }

        return sign.toString();
    }

    public static byte[] hex2byte(String str) {
        if (str == null) {
            return null;
        }
        str = str.trim();
        int len = str.length();
        if (len <= 0 || len % 2 == 1) {
            return null;
        }
        byte[] b = new byte[len / 2];
        try {
            for (int i = 0; i < str.length(); i += 2) {
                b[(i / 2)] = (byte) Integer.decode("0x"
                                                   + str.substring(i, i + 2)).intValue();
            }
            return b;
        } catch (Exception e) {
        }
        return null;
    }

    /*public static void main(String args[]) {
        System.out.println("start");
        
        String data = "app=MEMBERcity=深圳市loginName=13424269212mtd=com.guocui.tty.api.web.MemberControllor.getRecMerchantpageNo=1pageSize=20prov=广东省";
        
        try {
            //System.out.println(encryptMD5(data, "123456"));
            System.out.println(encryptMD5("111111"));
            //System.out.println(encryptMD5("123456".getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }*/
}
