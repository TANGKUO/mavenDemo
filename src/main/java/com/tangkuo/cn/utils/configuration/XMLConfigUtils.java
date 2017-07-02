package com.tangkuo.cn.utils.configuration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
/**
 * 
* @ClassName: XMLConfigUtils
* @Description: (XMLConfigUtils)
* @author tangkuo
* @date 2017年7月2日 下午5:04:35
*
 */
public class XMLConfigUtils {
	private static Log log = LogFactory.getLog(XMLConfigUtils.class);
	
	private static Map<String,XMLConfiguration> configMap = new HashMap<String,XMLConfiguration>();
	
	public static XMLConfiguration initConfigFile(String configFileName) throws ConfigurationException {
		XMLConfiguration config = new XMLConfiguration();
		config.setDelimiterParsingDisabled(true);
		config.setFileName(configFileName);
		config.load();
		configMap.put(configFileName, config);
		return config;
	}
	
	public static Object getProperty(String configFileName, String key) {
		XMLConfiguration config = configMap.get(configFileName);
		try {
			if(config == null) {
				config = initConfigFile(configFileName);
			}
			return config.getProperty(key);
			
		} catch(ConfigurationException ex) {
			log.error("get XML property value error,file name:"+configFileName+" key:"+key, ex);
			return null;
		}
	}
	
	public static Map<String, Object> getProperties(String configFileName) {
		XMLConfiguration config = configMap.get(configFileName);
		try {
			if(config == null) {
				config = initConfigFile(configFileName);
			}
			Map<String, Object> propMap = new HashMap<String, Object>();
			Iterator keysIter = config.getKeys();
			while(keysIter.hasNext()) {
				String key = (String)keysIter.next();
				propMap.put(key, config.getProperty(key));
			}
			return propMap;
		} catch(ConfigurationException ex) {
			log.error("get all XML property value error,file name:"+configFileName, ex);
			return null;
		}
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String xmlFileName = "F:\\test\\testTK.xml";
			XMLConfiguration config = initConfigFile(xmlFileName);
			
			Iterator iter = config.getKeys();
			while(iter.hasNext()) {
				System.out.println(iter.next());
			}
			Map map = XMLConfigUtils.getProperties(xmlFileName);
			Iterator keyIter = map.keySet().iterator();
			while(keyIter.hasNext()) {
				String key = (String)keyIter.next();
				Object obj = map.get(key);
				System.out.println(key + " " +obj);
			}
			
		} catch(Exception ex) {
			System.out.println(ex);
		}
		
	}

}
