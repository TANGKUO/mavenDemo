package com.tangkuo.cn.pay.kmtk.netbank.adapter.util;


/**
 * com.bocnet.util.Base64
 * @description 
 */
public final class Base64 {
	private static final Base64Coder coder = new Base64Coder();

    public static String encode(byte abyte0[]) {
    	return coder.encode(abyte0);
    }

    public static byte[] decode(String s) {
    	return coder.decode(s);
    }
}

