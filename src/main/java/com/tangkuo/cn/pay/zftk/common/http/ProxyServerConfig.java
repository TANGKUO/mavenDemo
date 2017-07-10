package com.tangkuo.cn.pay.zftk.common.http;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tangkuo.cn.utils.configuration.PropertiesUtils;
import com.tangkuo.cn.utils.configuration.Resources;


/**
 * 
* @ClassName: ProxyServerConfig
* @Description: 
* (1.由于jdk1.8不支持某些SSL证书，需要用代理服务进行转发，配置不支持SSL证书的域名及代理服务地址
 * 2.http请求失败，通过代理服务重发一次)
 */
public class ProxyServerConfig {	
	
	private static Log log = LogFactory.getLog(ProxyServerConfig.class);
	private final static String propertiesFileName = "proxyServer_domainNames.properties";
	public static List<String> domainNameLists = new ArrayList<String>();
	public static List<String> proxyServerAddressList = new ArrayList<String>();
	private static int currentProxyServerIndex = 0;

	/**
	 * 初始化加载配置文件
	 */
	public static void init() {
		try {
			URL url = Resources.getResourceURL(propertiesFileName);
			//存在配置文件
			if(url != null) {
				Map<String, Object> propertiesFileNameMap = PropertiesUtils.getProperties(propertiesFileName);
				Set<String> keySet = propertiesFileNameMap.keySet();
				for (String key : keySet) {
					if("proxyServerAddress".equals(key)) {
						String addressStr = (String)propertiesFileNameMap.get(key);
						String[] addressArray = StringUtils.split(addressStr, " ");
						for(String str : addressArray) {
							proxyServerAddressList.add("http://"+str+"/httpPostServlet");
						}
						currentProxyServerIndex = proxyServerAddressList.size()-1;
					} else {
						domainNameLists.add(key);
						log.info(new StringBuilder("merchantId:").append(propertiesFileNameMap.get(key)).append(",domainName:").append(key)
								.append(" added to domainNameLists,this domainName need to use proxy to send httpPost request!"));
					}		
				}
			}		
			
			log.info("ProxyServerConfig inited.");
		} catch (Exception e) {
			log.error(e);
		}		
	}

	/**
	 * 判断url是否在配置的域名中间
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isProxyDomainName(String url) {
		try {
			String urlHost = new URL(url).getHost();
			return domainNameLists.contains(urlHost);
		} catch (MalformedURLException e) {
			log.error(e);
			return false;
		}
	}
	
	/*
	 * 获取代理服务地址：简单的轮询
	 */
	public static String getProxyServer() {
		int totalSize = proxyServerAddressList.size();
		currentProxyServerIndex = (currentProxyServerIndex + 1) % totalSize ;
		
		if(currentProxyServerIndex >= totalSize) {
			currentProxyServerIndex = 0;
		}
		return proxyServerAddressList.get(currentProxyServerIndex);
	}	

}
