package com.tangkuo.cn.pay.kmtk.netbank.adapter.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.SignatureException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tangkuo.cn.pay.kmtk.netbank.common.util.Property;


public class SecurityAgent {

	private static Log logger = LogFactory.getLog(SecurityAgent.class);

	public static void verifyPKCS7(String signature, String plainText) throws Exception {
		logger.info("signature:"+signature);
		logger.info("plainText:"+plainText);
		try {
			InputStream VerifyCertStream;
			InputStream rootCertStresm;
			/*String rootCertPath=PropertiesLoader.getProperty("BOC_RootCer");
			String pubCertPath=PropertiesLoader.getProperty("BocMcpCer");*/
			
			
			String rootCertPath="";
			//String pubCertPath="E:/certificate/mcpboc_verifySignTest.cer";
			String pubCertPath=Property.getProperty("ROOT_CERT")+Property.getProperty("BOC.QPAY.DEBIT.CERPATH");
			try {
				logger.info("pubCertPath:"+pubCertPath);
				VerifyCertStream = new FileInputStream(pubCertPath);
				rootCertStresm=new FileInputStream(rootCertPath);
				//DER:使用PKCS7模式
				PKCSTool tool = PKCSTool.getVerifier(rootCertStresm, VerifyCertStream);
				tool.p7Verify(signature,plainText.getBytes("UTF-8"));
			}catch(SignatureException e){
				logger.error(e.getMessage());
				throw e;
			}catch (Exception e) {
				logger.error(e);
				throw e;
			}
		} catch (Exception e) {
			logger.error("plainText=" + plainText + "\nsignature=" + signature);
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	public static String signPKCS7(String plainText,String caName,String caPass) {
		String signature = "SimSignature";
		try {
			PKCSTool tool = PKCSTool.getSigner(plainText,caName,caPass, "PKCS7");
			byte[] data = plainText.getBytes("UTF-8");
			signature = tool.p7Sign(data);
			signature = signature.replaceAll("\r", "");
			signature = signature.replaceAll("\n", "");
		} catch (Exception e) {
			logger.error(e, e);
		}
		return signature;
	}
	//加签
	public static String signPKCS1(String plainText,String caPass) {
		String signature = "SimSignature";
		try {
			//String pfxCertPath=PropertiesLoader.getProperty("MchtMcpPfx");
			
			//String pfxCertPath="E:/certificate/测试私钥95566SZ010000451_密码11111112.pfx";
			String pfxCertPath=Property.getProperty("ROOT_CERT")+Property.getProperty("BOC.QPAY.DEBIT.PFXPATH");
			PKCSTool tool = PKCSTool.getSigner(pfxCertPath,caPass,caPass,null);
			byte[] data = plainText.getBytes("UTF-8");
			signature = tool.p1Sign(data);
			signature = signature.replaceAll("\r", "");
			signature = signature.replaceAll("\n", "");
			logger.info("plainText:"+plainText);
			logger.info("signature:"+signature);
		} catch (Exception e) {
			logger.error(e, e);
		}
		return signature;
	}
	
	//验签
	public static void verifyPKCS1(String signature, String plainText) throws Exception {
		logger.info("signature:"+signature);
		logger.info("plainText:"+plainText);
		try {
			InputStream VerifyCertStream;
			//String pubCertPath=PropertiesLoader.getProperty("BocMcpCer");
			
			//String pubCertPath="E:/certificate/mcpboc_verifySignTest.cer";
			String pubCertPath=Property.getProperty("ROOT_CERT")+Property.getProperty("BOC.QPAY.DEBIT.CERPATH");
			System.out.println(pubCertPath);
			try {
				logger.info("pubCertPath:"+pubCertPath);
				VerifyCertStream = new FileInputStream(pubCertPath);
				//DER:使用PKCS1模式
				PKCSTool tool = PKCSTool.getVerifier(null, VerifyCertStream);
				tool.p1Verify(signature,plainText.getBytes("UTF-8"));
			}catch(SignatureException e){
				logger.error(e.getMessage());
				throw e;
			}catch (Exception e) {
				logger.error(e);
				throw e;
			}
		} catch (Exception e) {
			logger.error("plainText=" + plainText + "\nsignature=" + signature);
			logger.error(e.getMessage(), e);
			throw e;
		}
	}


	public static void main(String[] ars) {
		String plainText = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><response><head><responseCode>OK</responseCode><responseInfo>OK</responseInfo></head></response>";
		String data = "Z88ScaQFY+MO+1XGSbR+o+r/35vMoNcX5GOy/RM2vTChUU5pQj1HfVhXSHwIjDKBn0oYuC/nBrtqvjYwN+MQDeCraebSwxkGbHUZgmmY8urOa4UPwxtsB1XGtHz5Lg+z8NUxOiruEvTOZFvPCfbHnYQVy/QNIBjiPueD8co4VrIhXSOG4wYF6pF3Y3XCDnQGOYBbPLzpCxHtVfGHQLRpB+9NX77b6lDSBmNGR7alAYhZazdF+hojgAzvajDEeBCh6litfdutove1ycPz2tc+JLZm/9v1NLzzKTN4PDNpyeSiAuloJ58GE/PnwAAOgvMmOxS48eygKeS71J2kfvUYxw==";
		try {
			verifyPKCS1(data,plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
