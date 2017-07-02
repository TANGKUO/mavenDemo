package com.tangkuo.cn.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 配置文件工具类
 * @ClassName: applicationContextUtil 
 * @Description: (配置文件工具类) 
 */
public class ApplicationContextUtil{
	private static Logger log = LoggerFactory.getLogger(ApplicationContextUtil.class);
	
	private final static String BEAN_FILE = "classpath*:applicationContext*.xml";
	
	private static boolean initialized;
	
	private static ApplicationContextUtil instance;
	
	private static ApplicationContext applicationContext;
	
	/**
	 * 获取bean
	 * @Title: getBean 
	 * @Description: (获取bean) 
	 * @param beanName
	 * @return
	 */
	public Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
	
	/**
	 * 构造器
	 * <p>Title: </p> 
	 * <p>Description: 构造类</p>
	 */
	private ApplicationContextUtil(){
		if(!initialized){
			load();
			setInitialized(true);
		}
	}
	
	/**
	 * 是否初始化，用于外部调用
	 * @Title: setInitialized 
	 * @Description: (是否初始化，用于外部调用) 
	 * @param initialized
	 */
	public static void setInitialized(boolean initialized) {
		ApplicationContextUtil.initialized = initialized;
	}
	
	/**
	 * bean文件，用于外部调用
	 * @Title: setApplicationContext 
	 * @Description: () 
	 * @param applicationContext
	 */
	public static void setApplicationContext(ApplicationContext applicationContext) {
		ApplicationContextUtil.applicationContext = applicationContext;
	}
	
	/**
	 * 加载配置文件
	 * @Title: load 
	 * @Description: (加载配置文件)
	 */
	private void load(){
		try {
			log.info("applicationContextUtil.load begin");
			applicationContext = new ClassPathXmlApplicationContext(BEAN_FILE);
			log.info("applicationContextUtil.load end");
		} catch (BeansException be) {
			log.error("applicationContextUtil.load加载配置文件错误："+be);
		}
	}
	
	/**
	 * 单例
	 * @Title: getInstance 
	 * @Description: (单例) 
	 * @return
	 */
	synchronized public static ApplicationContextUtil getInstance(){
		if(instance == null){
			instance = new ApplicationContextUtil();
		}
		return instance;
	}
	
	
}
