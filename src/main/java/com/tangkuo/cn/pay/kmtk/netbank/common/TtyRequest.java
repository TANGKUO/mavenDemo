package com.tangkuo.cn.pay.kmtk.netbank.common;

import java.io.Serializable;

/**
 * 类说明：请求接口<br>
 */
public interface TtyRequest<T extends TtyResponse> extends Serializable {

    /**
     * 方法说明：参数校验<br>
     * 
     * @throws TtyException
     */
    public void check() throws TtyException;

}
