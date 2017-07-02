package com.tangkuo.cn.pay.kmtk.netbank.adapter.util;

public interface Coder {

	public String encode(byte[] data);

	public byte[] decode(String string);

}