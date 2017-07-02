package com.tangkuo.cn.pay.kmtk.netbank.adapter.util;

import java.util.Date;
import java.util.Map;


/** 
* @ClassName: HandlerAdapterUtil 
* @Description: B2c工具类
*  
*/ 
public class HandlerAdapterUtil {
	/** 
	* @Title: toBankFileDownLog 
	* @Description: 将参数数据复制到对账数据对象中
	* @param @param params
	* @param @return    设定文件 
	* @return SettleData    返回类型 
	* @throws 
	*/ 
	public static BankFileDownLog toBankFileDownLog(Map<String, String> params) {
		
		return null;
	}
	
	/** 
	 * @Title: payResp 
	 * @Description: 返回结果对象
	 * @param     设定文件 
	 * @return QueryPayResp    返回类型 
	 * @throws 
	 */ 
	public static PayResp payResp(String orderNo) {
		PayResp resp = new PayResp();
		resp.setOrderNo(orderNo);
		resp.setResultStatus(ResultStatus.ERROR);
		return resp;
	}
	
	/** 
	* @Title: queryPayResp 
	* @Description: 返回结果对象
	* @param     设定文件 
	* @return QueryPayResp    返回类型 
	* @throws 
	*/ 
	public static QueryResp queryPayResp(String orderNo) {
		QueryResp resp = new QueryResp();
		resp.setOrderNo(orderNo);
		resp.setBankOrderStatus(BankOrderStatus.FIAL);
		return resp;
	}
	
	/** 
	* @Title: callBackPayResp 
	* @Description: 银行回调返回结果对象
	* @param @param orderNo
	* @param @return    设定文件 
	* @return CallBackPayResp    返回类型 
	* @throws 
	*/ 
	public static CallBackPayResp callBackPayResp(String orderNo) {
		CallBackPayResp resp = new CallBackPayResp();
		resp.setOrderNo(orderNo);
		resp.setResultStatus(ResultStatus.ERROR);
		return resp;
	}
	
	/** 
	* @Title: resultResp 
	* @Description: 返回结果对象
	* @param @param orderNo
	* @param @return    设定文件 
	* @return ResultResp    返回类型 
	* @throws 
	*/ 
	public static ResultResp resultResp(String orderNo) {
		ResultResp resp = new ResultResp();
		resp.setOrderNo(orderNo);
		resp.setResultStatus(ResultStatus.ERROR);
		return resp;
	}
	
	/** 
	* @Title: bankFileDownLog 
	* @Description: 银行对账下载日志数据
	* @param @return    设定文件 
	* @return BankFileDownLog    返回类型 
	* @throws 
	*/ 
	public static BankFileDownLog bankFileDownLog(String bankCode, String settleDate, String reCode, String reMsg) {
		return bankFileDownLog(bankCode,settleDate,SettleStatus.SUCCESS,reCode,reMsg);
	}
	
	public static BankFileDownLog bankFileDownLog(String bankCode, String settleDate, SettleStatus sStatus,String reCode, String reMsg){
		BankFileDownLog fileDownLog = new BankFileDownLog();
		fileDownLog.setBankCode(bankCode);
		fileDownLog.setSettleDate(settleDate);
		fileDownLog.setRecode(reCode);
		fileDownLog.setRemsg(reMsg);
		fileDownLog.setDownState(sStatus.name());
		fileDownLog.setCreateTime(new Date());
		return fileDownLog;
	}
}
