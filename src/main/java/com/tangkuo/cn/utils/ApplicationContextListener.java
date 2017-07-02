package com.tangkuo.cn.utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * web容器监听器
 * @ClassName: applicationContextListener 
 * @Description: (web容器监听器) 
 */
public class ApplicationContextListener extends ContextLoaderListener implements ServletContextListener{
	private static Log log = LogFactory.getLog(ApplicationContextListener.class);

	/**
	 * web容器初始化时，加载配置文件工具类(获取spring同一上下文)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try{
			log.info("applicationContextListener.contextInitialized begin");
			ServletContext application = event.getServletContext();
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(application);
			if (context != null) {
				ApplicationContextUtil.setApplicationContext(context);
				ApplicationContextUtil.setInitialized(true);
			}
			log.info("applicationContextListener.contextInitialized end");
		}catch (Exception e) {
			log.error("applicationContextListener.contextInitialized错误: ", e);
		}
	}
}
