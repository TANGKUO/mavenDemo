package com.tangkuo.cn.pay.zftk.common.xml;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
/**
 * 
* @ClassName: XPPParser
* @Description: TODO(这里用一句话描述这个类的作用)
 */
public class XPPParser {
	private final static Log log = LogFactory.getLog(XPPParser.class);
	
	private static XmlPullParserFactory parserFactory = null;
	
	static {
		try {
			parserFactory = XmlPullParserFactory.newInstance();
			parserFactory.setNamespaceAware(true);
		} catch (XmlPullParserException e) {
			System.out.println("XPPParser initialize error!!");
		}
	}
	
	/**
	 * 解析XML格式留，获取所有子元素名和值
	 * @param reader
	 * @return name和value值
	 */
	public static Map<String,String> parse(Reader reader) {
		Map<String,String> map = new HashMap<String,String>();
		
		try {			
			XmlPullParser xpp = parserFactory.newPullParser();
			xpp.setInput(reader);
			
			int eventType = xpp.getEventType();	
			String currentTag = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if(eventType == XmlPullParser.START_TAG) {
					currentTag = xpp.getName();
				} else if(eventType == XmlPullParser.TEXT) {
					map.put(currentTag, xpp.getText());
				}
				eventType = xpp.next();
			}			
			
		} catch (Exception ex) {
			log.error("xml parse exception.", ex);
		}
		
		return map;
	}
	
	public static Map<String,String> parse(String strXML) {
		return parse(new StringReader(strXML));
	}	
	

}
