package com.tangkuo.cn.utils.filter;


public interface IMessageConstant {

    boolean isDebug=true;

    /**
     * 管理中登录用户的SessionKey
     */
    String MGT_LOGIN_USER_SESSION_KEY= "log_user";

    int userState_ok = 0;
    int userState_lock= 1;
}
