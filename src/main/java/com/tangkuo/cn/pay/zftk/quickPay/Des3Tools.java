package com.tangkuo.cn.pay.zftk.quickPay;

import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Des3Tools {
	private static final String ALGORITHM = "DESede"; // 定义 加密算法,可用

	// DES,DESede,Blowfish

	// keybyte为加密密钥，长度为24字节 //src为被加密的数据缓冲区（源）

	public static CipherOutputStream encryptMode(
			OutputStream outputStream,String threeDesKey) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(threeDesKey.getBytes(), ALGORITHM);
			// 加密
			Cipher c1 = Cipher.getInstance(ALGORITHM);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return new CipherOutputStream(outputStream, c1);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	public static CipherInputStream decryptMode(
			InputStream inputStream,String threeDesKey) {
		try { // 生成密钥
			SecretKey deskey = new SecretKeySpec(threeDesKey.getBytes(), ALGORITHM); // 解密
			Cipher c1 = Cipher.getInstance(ALGORITHM);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return new CipherInputStream(inputStream, c1);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}
}
