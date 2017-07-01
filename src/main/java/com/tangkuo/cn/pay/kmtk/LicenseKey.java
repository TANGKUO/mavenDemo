package com.tangkuo.cn.pay.kmtk;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LicenseKey {
	
	private static byte[] arrayOfByte = { -84, -19, 0, 5, 115, 114, 0, 20, 106, 97, 118, 97, 46, 115, 101, 99, 117, 114, 105, 116, 121, 46, 75, 101, 121, 82, 101, 112, -67, -7, 79, -77, -120, -102, -91, 67, 2, 0, 4, 76, 0, 9, 97, 108, 103, 111, 114, 105, 116, 104, 109, 116, 0, 18, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 91, 0, 7, 101, 110, 99, 111, 100, 101, 100, 116, 0, 2, 91, 66, 76, 0, 6, 102, 111, 114, 109, 97, 116, 113, 0, 126, 0, 1, 76, 0, 4, 116, 121, 112, 101, 116, 0, 27, 76, 106, 97, 118, 97, 47, 115, 101, 99, 117, 114, 105, 116, 121, 47, 75, 101, 121, 82, 101, 112, 36, 84, 121, 112, 101, 59, 120, 112, 116, 0, 3, 82, 83, 65, 117, 114, 0, 2, 91, 66, -84, -13, 23, -8, 6, 8, 84, -32, 2, 0, 0, 120, 112, 0, 0, 0, -94, 48, -127, -97, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -127, -115, 0, 48, -127, -119, 2, -127, -127, 0, -68, -10, 19, 93, 64, -106, -67, -13, -20, 64, -10, 82, 73, -116, -60, 94, 18, 47, 62, -72, -43, -32, 54, -22, 90, 91, 24, -55, -123, -8, 107, -48, -34, 96, -80, 81, 7, -38, 108, -27, -59, -84, -126, 81, 60, -90, 92, -6, 12, 9, -42, -50, 93, -84, -75, 84, -94, 49, -73, -88, 107, 76, -17, -49, 19, 43, -84, 27, -31, 110, 46, -55, 29, -60, 24, -38, 60, -41, -103, 5, -20, 89, -110, -9, -115, 12, 11, 96, 21, 68, 125, 69, 52, -31, -96, 124, -46, 0, 110, 19, -24, 41, 70, 114, 126, Byte.MAX_VALUE, 98, -7, -84, 28, -23, -101, -118, -27, 71, -61, -127, 18, 112, -76, -32, -80, -53, -116, 39, -21, -56, 35, 2, 3, 1, 0, 1, 116, 0, 5, 88, 46, 53, 48, 57, 126, 114, 0, 25, 106, 97, 118, 97, 46, 115, 101, 99, 117, 114, 105, 116, 121, 46, 75, 101, 121, 82, 101, 112, 36, 84, 121, 112, 101, 0, 0, 0, 0, 0, 0, 0, 0, 18, 0, 0, 120, 114, 0, 14, 106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 69, 110, 117, 109, 0, 0, 0, 0, 0, 0, 0, 0, 18, 0, 0, 120, 112, 116, 0, 6, 80, 85, 66, 76, 73, 67 };
	private static String proName = "TKSZ2017";
	private static String TDSign = "54EAD7D31CFBD92609464D4DB104F10C5A0C6BC3916FC2EDB0781B3B2BC0489174BDFED128F3183228A63F964D1C0C5108E88453A8D7D24DF2127F6537FBF6033182FED087B9B0FDE421027B211C08A37F195985D1CDF6F66F04A5CB2730A8BE0AD67059C12357B26362C70C4893A74053468CD67F293F8CBB4ED99630C3756C";
	
	private static String EXPIRY_DATE = "2018-12-30";
	private static String MAC = "00-50-56-c0-00-08;B8-CA-3A-F6-7D-F4;B8-CA-3A-F6-7D-F5;B8-CA-3A-F6-7D-F6;B8-CA-3A-F6-7D-F7;C8-1F-66-CE-59-BF;C8-1F-66-CE-59-C0;C8-1F-66-CE-59-C1;C8-1F-66-CE-59-C2";
	private static String Version = "TKSZ2017";
	
	
	public static void main(String[] args) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("proName", proName);
		params.put("EXPIRY_DATE", EXPIRY_DATE);
		params.put("MAC", MAC);
		params.put("Version", Version);
		
		StringBuffer sb = new StringBuffer();
		Iterator<String> iter = params.keySet().iterator();
		while (iter.hasNext()) {
			String str = (String) iter.next();
			if (!str.equals("TDSIGN")) {
				sb.append((String) params.get(str));
			}
		}
		
		try {
			sign(sb.toString().getBytes("UTF-8"), toByte(TDSign));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(getMac());
		
	}
	
	private static void sign(byte[] bytes, byte[] signbyte) throws Exception {
		ObjectInputStream localObjectInputStream = null;
		try {
			ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
			localObjectInputStream = new ObjectInputStream(localByteArrayInputStream);
			
			PublicKey localPublicKey = (PublicKey) localObjectInputStream.readObject();
			Signature localSignature = Signature.getInstance("SHA1withRSA");
			localSignature.initVerify(localPublicKey);
			localSignature.update(bytes);
			if (!localSignature.verify(signbyte)) {
				throw new Exception("License验证不正确");
			}
		} catch (Exception e) {
			throw new Exception("License验证不正确，请停止侵权使用", e);
		} finally {
			localObjectInputStream.close();
			
		}
		
	}

	private static void checkMac(String paramString) throws Exception {
		String[] arrayOfString = paramString.split(";");
		int i = 0;
		for (int j = 0; j < arrayOfString.length; j++) {
			if ((!arrayOfString[j].isEmpty()) && (!arrayOfString[j].trim().equals("")) && (arrayOfString[j].toUpperCase().equals(getMac().toUpperCase()))) {
				i = 1;
				break;
			}
		}
		if (i == 0) {
			throw new Exception("MAC地址校验错误，请使用注册的硬件运行本程序注册mac" + paramString + " 本机地址：" + getMac());
		}
	}
	
	private static String getMac() {
		try {
			Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
			while (enumeration.hasMoreElements()) {
				String str = "";
				StringBuffer sb = new StringBuffer();
				NetworkInterface localNetworkInterface = (NetworkInterface) enumeration.nextElement();
				if ((!localNetworkInterface.isLoopback())
						&& (!localNetworkInterface.isPointToPoint())
						&& (!localNetworkInterface.isVirtual())
						&& (localNetworkInterface.isUp())) {
					byte[] arrayOfByte = localNetworkInterface.getHardwareAddress();
					if ((arrayOfByte != null) && (arrayOfByte.length != 0)) {
						for (int i = 0; i < arrayOfByte.length; i++) {
							str = Integer.toHexString(arrayOfByte[i] & 0xFF);
							if (str.length() == 1) {
								str = '0' + str;
							}
							sb.append(str + "-");
						}
						str = sb.toString();
						str = str.substring(0, str.length() - 1);
						return str;
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] toByte(String str) {
		int i = str.length() / 2;
		byte[] bytes = new byte[i];
		for (int j = 0; j < i; j++) {
			bytes[j] = ((byte) Integer.parseInt(str.substring(j * 2, j * 2 + 2), 16));
		}
		return bytes;
	}
	
}
