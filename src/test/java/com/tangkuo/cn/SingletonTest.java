package com.tangkuo.cn;

import org.junit.Test;

import com.tangkuo.cn.Singleton.Singleton1;
import com.tangkuo.cn.Singleton.Singleton2;
import com.tangkuo.cn.Singleton.Singleton3;
import com.tangkuo.cn.Singleton.Singleton4;
import com.tangkuo.cn.Singleton.Singleton5;
import com.tangkuo.cn.Singleton.Singleton6;
import com.tangkuo.cn.Singleton.Singleton7;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: www.tk.com</p>   
 * @author   tangkuo
 * @date    2017年3月19日 下午5:35:40
 */
public class SingletonTest {
	
	@Test
	public void testSingleton() {
		System.out.println(Singleton1.getInstance() == Singleton1.getInstance());
		System.out.println(Singleton2.getInstance() == Singleton2.getInstance());
		System.out.println(Singleton3.getInstance() == Singleton3.getInstance());
		System.out.println(Singleton4.getInstance() == Singleton4.getInstance());
		System.out.println(Singleton5.getInstance() == Singleton5.getInstance());
		System.out.println(Singleton6.INSTANCE == Singleton6.INSTANCE);
		System.out.println(Singleton7.getInstance() == Singleton7.getInstance());
	}
}
