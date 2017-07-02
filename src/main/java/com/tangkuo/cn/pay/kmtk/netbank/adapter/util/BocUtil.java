package com.tangkuo.cn.pay.kmtk.netbank.adapter.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangkuo.cn.pay.kmtk.netbank.common.util.DateUtil;
import com.tangkuo.cn.pay.kmtk.netbank.common.util.Property;
import com.tangkuo.cn.utils.string.StringUtil;

/** 
* @ClassName: BocUtil 
* @Description: 中行工具类
*  
*/
@Component
public class BocUtil extends AbstractApiServiceAdapter {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	// 发送xml  解决中文传输长度问题
	public final String str = "                                                                                  ";
	
	// 签到数据
	public Map<String, String> signMap = new HashMap<String, String>();
	
	@Resource
	private ICachedService cachedService;
	
	// 签到token
	private final String BOC_TOKEN = "boc_signin";
	
	/** 
	* @Title: signIn 
	* @Description: 签到
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/ 
	@SuppressWarnings("unchecked")
	public void signIn() {
		if(!this.cachedService.containsKey(this.BOC_TOKEN)) {
			this.signMap.put("trnid", String.valueOf(System.currentTimeMillis()).substring(1,13));
			String data = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
				.append("<bocb2e version=\"120\" locale=\"zh_CN\">")
					.append("<head>")
						.append("<termid>").append(Property.getProperty("BOC.B2E.TERMID")).append("</termid>")
						.append("<trnid>").append(this.signMap.get("trnid")).append("</trnid>")
						.append("<custid>").append(Property.getProperty("BOC.B2E.CUSTID")).append("</custid>") // 商户号
						.append("<cusopr>").append(Property.getProperty("BOC.B2E.CUSOPR")).append("</cusopr>") // 操作员代码
						.append("<trncod>").append("b2e0001").append("</trncod>")
					.append("</head>")
					.append("<trans><trn-b2e0001-rq><b2e0001-rq>")
						.append("<custdt>").append(DateUtil.getCDateString(DateUtil.YYYYMMDDHHMMSS)).append("</custdt>") // 客户端日期时间
						.append("<oprpwd>").append(Property.getProperty("BOC.B2E.OPRPWD")).append("</oprpwd>") // 登录密码
					.append("</b2e0001-rq></trn-b2e0001-rq></trans>") 
				.append("</bocb2e>").toString();
			log.debug("签到-->请求报文: {}", data);
			
			String resultXml = this.socketResponse(data);
			log.debug("收到签到返回的报文：" + resultXml);
			Document doc = BankDocumentUtil.parseXml(resultXml);
			String rspcod = doc.selectSingleNode("bocb2e/trans/trn-b2e0001-rs/status/rspcod").getText();
			String rspmsg = doc.selectSingleNode("bocb2e/trans/trn-b2e0001-rs/status/rspmsg").getText();
			if(StringUtil.isNotEmpty(rspcod) && "B001".equals(rspcod)){
				log.info("签到成功了！！ ");
				String termid = doc.selectSingleNode("bocb2e/head/termid").getText();
				String custid = doc.selectSingleNode("bocb2e/head/custid").getText();
				String cusopr = doc.selectSingleNode("bocb2e/head/cusopr").getText();
				String  token = doc.selectSingleNode("bocb2e/trans/trn-b2e0001-rs/token").getText();
				//String  serverdt = doc.selectSingleNode("bocb2e/trans/trn-b2e0001-rs/serverdt").getText();
				this.signMap.put("rspcod", rspcod);
				this.signMap.put("rspmsg", rspmsg);
				this.signMap.put("token", token);
				this.signMap.put("custid", custid);
				this.signMap.put("cusopr", cusopr);
				this.signMap.put("termid", termid);
				//this.signMap.put("serverdt", serverdt);
				log.info("签到的前置机地址：" + termid);
				this.cachedService.add(this.BOC_TOKEN, this.signMap);
				return;
			}
			this.signMap = new HashMap<String, String>();
			log.info("签到失败，rspcod=" + rspcod + "，rspmsg" + rspmsg);
		} else {
			this.signMap = (Map<String, String>) this.cachedService.get(this.BOC_TOKEN);
			if(null == this.signMap || this.signMap.isEmpty()) {
				this.reSignIn();
			} else if(!this.signMap.containsKey("trnid")) {
				this.reSignIn();
			}
			log.info("获取缓存中的签到数据{}", this.signMap);
		}
	}
	
	/** 
	* @Title: reSignIn 
	* @Description: 重新签到
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/ 
	public void reSignIn() {
		log.info("token失败，重新签到。");
		this.cachedService.remove(this.BOC_TOKEN);
		// 签到
		this.signIn();
	}
	
	/** 
	* @Title: signOut 
	* @Description: 签退
	* @param @param param    设定文件 
	* @return void    返回类型 
	* @throws 
	*/ 
	public void signOut() {
		/*params.put("trncod", "b2e0002");
		String data = new StringBuffer()
			.append("<trans><trn-b2e0002-rq><b2e0002-rq>")
				.append("<custdt>").append(DateUtil.getCDateString(DateUtil.YYYYMMDDHHMMSS)).append("</custdt>") // 客户端日期时间
			.append("</b2e0002-rq></trn-b2e0002-rq></trans>").toString();
		String responseXml = this.socketResponse(this.requestXml(params, data));
		log.info("收到签退返回的报文：" + responseXml);
		Document doc = BankDocumentUtil.parseXml(responseXml);
		String rspcod = doc.selectSingleNode("bocb2e/trans/trn-b2e0002-rs/status/rspcod").getText();
		if(StringUtil.isNotEmpty(rspcod) && "B001".equals(rspcod)){
			log.info("签退成功！ ");
		}*/
	}
	
	/** 
	* @Title: socketResponse 
	* @Description: 业务请求处理
	* @param @param busXml
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/ 
	public String  socketResponse(String busXml) {
		Socket  socket=null;
		try{
			socket = new Socket(Property.getProperty("BOC.B2E.SERVEIP"), Integer.valueOf(Property.getProperty("BOC.B2E.PORT"))); 
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			String xml = busXml;
			wr.write("POST " + Property.getProperty("BOC.B2E.METHOD") + " HTTP/1.0\r\n");
			wr.write("Content-Length: "+xml.length()+"\r\n");
			wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
			wr.write("\r\n");
			wr.write(xml);
			wr.flush();
	
			BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			String line;
			String responseData = "";
			while ((line = rd.readLine()) != null) {
				responseData+=line;
			}
			responseData= responseData.substring(responseData.indexOf("<?"));
			return responseData;
		} catch(Exception e) {
			e.printStackTrace();
			log.error("向银行前置机发送支付信息失败",e);
		}
		try{
			socket.close();
		}catch(Exception e){
			e.printStackTrace();
			log.error("向银行前置机发送支付信息异常",e);
		}finally{
			log.info("调用支行信息接口结束了");
		}
		return null;
	}
	
	/** 
	* @Title: requestXml 
	* @Description: 请求报文
	* @param @param orderNo
	* @param @param body
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/ 
	public String requestXml(String body) {
		String data = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
			.append("<bocb2e version=\"120\" locale=\"zh_CN\">")
				.append("<head>")
					.append("<termid>").append(this.signMap.get("termid")).append("</termid>")// 客户端产生的报文编号
					.append("<trnid>").append(this.signMap.get("trnid")).append("</trnid>")// 企业在中行网银系统的客户编码
					.append("<custid>").append(this.signMap.get("custid")).append("</custid>")// 企业操作员代码
					.append("<cusopr>").append(this.signMap.get("cusopr")).append("</cusopr>")// 业务类型
					.append("<trncod>").append(this.signMap.get("trncod")).append("</trncod>") // 交易验证标识，签到时生成、签退时注销
					.append("<token>").append(this.signMap.get("token")).append("</token>")
				.append("</head>")
				.append(body)
			.append("</bocb2e>").toString();
		log.debug("中行" + this.signMap.get("trncod") + "请求报文: {}", data);
		
		return data;
	}
}
