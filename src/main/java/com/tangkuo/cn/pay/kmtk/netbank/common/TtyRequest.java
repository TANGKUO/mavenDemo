/**
 * Copyright (c) 2011-2014 All Rights Reserved.
 */
package com.kame.micropay.commons;

import java.io.Serializable;

/**
 * 类说明：请求接口<br>
 * @author xavier
 * @version $Id: TtyRequest.java 2015年4月5日 下午2:28:55 $
 */
public interface TtyRequest<T extends TtyResponse> extends Serializable {

    /**
     * 方法说明：参数校验<br>
     * 
     * @throws TtyException
     */
    public void check() throws TtyException;

}
