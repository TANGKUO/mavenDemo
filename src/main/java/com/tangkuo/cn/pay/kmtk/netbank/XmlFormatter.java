package com.tangkuo.cn.pay.kmtk.netbank;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.xml.serialize.XMLSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
/**
 * 
 * @ClassName: XmlFormatter
 * @Description: (解析xml工具类)
 * @author tangkuo
 * @date 2017年6月27日 下午5:35:40
 *
 */
public class XmlFormatter {

	private static Logger log = LoggerFactory.getLogger(XmlFormatter.class);

	/**
	 * 
	* @Title: parseXmlFile
	* @Description: TODO(解析xml文件.)
	* @param @param in
	* @param @return    设定文件
	* @return Document    返回类型
	* @throws
	 */
	private static Document parseXmlFile(String in) {
		if (StringUtils.isEmpty(in)) {
			return null;
		}
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(in));
			return db.parse(is);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 
	* @Title: format
	* @Description: TODO(格式化xml文件.)
	* @param @param unformattedXml
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public static String format(String unformattedXml) {
		if (StringUtils.isEmpty(unformattedXml)) {
			return StringUtils.EMPTY;
		}
		try {
			final Document document = parseXmlFile(unformattedXml);
			if (document == null) {
				return StringUtils.EMPTY;
			}
			org.apache.xml.serialize.OutputFormat format = new org.apache.xml.serialize.OutputFormat(document);
			format.setLineWidth(65);
			format.setIndenting(true);
			format.setIndent(2);
			Writer out = new StringWriter();
			XMLSerializer serializer = new XMLSerializer(out, format);
			serializer.serialize(document);
			return out.toString();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return StringUtils.EMPTY;
		}
	}

}