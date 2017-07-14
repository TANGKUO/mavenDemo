package com.tangkuo.cn.pay.zftk.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tangkuo.cn.pay.kmtk.netbank.common.Constants;

public class ValidateSignServlet extends HttpServlet {
	private static Log log = LogFactory.getLog(ValidateSignServlet.class);
	private static final long serialVersionUID = -864126239384046143L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) {
		String message="";
		boolean validateResult=false;
		try {
			String plainText = req.getParameter("plainText");
			String signedData = req.getParameter("signedData");
			log.debug("validate sign:plainText is => " + plainText);
			log.debug("validate sign:signedData is => " + signedData);
			if (null == plainText) {
				throw new RuntimeException("plainText is null");
			}
			if (null == signedData) {
				throw new RuntimeException("signedData is null");
			}
			RSAWithHardware rsa = RSAUtil.getInstance();
			validateResult = rsa.validateSignByPubKey(Constants.MERCHANT_ID, plainText, signedData);
			if(validateResult) {
				message = "validate sign success!";
			}else{
				message = "validate sign failed!";
			}
			log.debug("validate result => " + validateResult);
		} catch (Exception e) {
			log.error("validate sign failed",e);
			message = "validate sign failed!reason:"+e.getMessage();
		}
		PrintWriter out = null;
		try {
			String data = "validateResult="+validateResult+"&message="+message;
			out = resp.getWriter();
			out.print(data);
			out.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}
}
