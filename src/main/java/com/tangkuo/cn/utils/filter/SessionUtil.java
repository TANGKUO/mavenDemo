package com.tangkuo.cn.utils.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tangkuo.cn.utils.data.CommonUtil;

public class SessionUtil implements IMessageConstant{
	
	/**
	 * 设置用户的登录信息
	 */
	public static boolean setMgtUser(HttpServletRequest request,UserEntity userVo){
		try{
			request.getSession().setAttribute(MGT_LOGIN_USER_SESSION_KEY, userVo);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 用户是否登录
	 */
	public static boolean isMgtSessionLogin(HttpServletRequest request){
		try{
			HttpSession objsesion = request.getSession();
			if(null == objsesion) return false;
			Object obj=objsesion.getAttribute(MGT_LOGIN_USER_SESSION_KEY);
			if(null == obj) return false;
			else{
                UserEntity userVo = (UserEntity)obj;
				if(CommonUtil.isNull(userVo.getId()) ||CommonUtil.isNull(userVo.getLoginName())){
					return false;
				}else return true;
				
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 判断Session数据是否存在
	 */
	public static boolean sessionObjExist(HttpServletRequest request,String sessionKey){
		try{
			if(CommonUtil.isNull(sessionKey)) return false;
			Object obj = request.getSession().getAttribute(sessionKey);
			if(null == obj) return false;
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	

	
	/**
	 * 获取登录用户信息
	 */
	public static UserEntity getMgtUserVo(HttpServletRequest request){
		try{
			Object obj = request.getSession().getAttribute(MGT_LOGIN_USER_SESSION_KEY);
			if(null == obj) return null;
			else{
                UserEntity userVo = (UserEntity)obj;
				return userVo;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	
	}
	 /**
	  * 保存信息到SESSION
	  */
	 public static void setDataToSession(HttpServletRequest request,String key,Object value){
		 request.getSession().setAttribute(key,value);
	 }
	 
	 
	 /**
	  * 获取session信息
	  */
	 @SuppressWarnings("unchecked")
	public static <T>T getDataFromSession(HttpServletRequest request,String key,Class<T> clazz){
		 Object obj = request.getSession().getAttribute(key);
		 if(obj == null ) return null;
		 return (T)obj;
	 }
	 
	 /**
	  * 移除session 属性
	  */
	public static void removeSessionAttribut(HttpServletRequest request,String key){
		 request.getSession().removeAttribute(key);
	}
	
	 /**
	  * 移除登陆用户
	  */
	public static void removeSessionUser(HttpServletRequest request){
		 removeSessionAttribut(request,MGT_LOGIN_USER_SESSION_KEY);
		 
	}
	
	
}
