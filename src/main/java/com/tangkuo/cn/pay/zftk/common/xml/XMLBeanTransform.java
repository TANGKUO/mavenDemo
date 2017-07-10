package com.tangkuo.cn.pay.zftk.common.xml;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
* @ClassName: XMLBeanTransform
* @Description: (这里用一句话描述这个类的作用)
*
 */
public class XMLBeanTransform {
	private static Logger logger = LoggerFactory.getLogger(XMLBeanTransform.class);
	
	/**
	 * 
	* @Title: transformXML2Object
	* @Description: (这里用一句话描述这个方法的作用)
	* @param @param ins
	* @param @param clasz
	* @return Object    返回类型
	* @throws
	 */
	public static Object transformXML2Object(InputStream ins, Class<?> clasz) {
		JAXBContext context = null;
		Unmarshaller ums = null;
		try {
			context = JAXBContext.newInstance(clasz.getPackage().getName());
			ums = context.createUnmarshaller();
			ums.setEventHandler(new XLSValidationEventHandler());
			return ums.unmarshal(ins);
		} catch(JAXBException ex) {
			String errorMsg = new StringBuilder().append("transform xml to Bean error,class name:").append(clasz.getName()).toString();
			logger.error(errorMsg, ex);
			throw new XMLTransformException(errorMsg, ex);			
		}		
	}
	
	/**
	 * 
	* @Title: transformXML2Object
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param ins
	* @param @param clasz
	* @param @param closeIns
	* @return Object    返回类型
	* @throws
	 */
	public static Object transformXML2Object(InputStream ins, Class<?> clasz, boolean closeIns) {
		JAXBContext context = null;
		Unmarshaller ums = null;
		try {
			context = JAXBContext.newInstance(clasz.getPackage().getName());
			ums = context.createUnmarshaller();
			ums.setEventHandler(new XLSValidationEventHandler());
			return ums.unmarshal(ins);
		} catch(JAXBException ex) {
			String errorMsg = new StringBuilder().append("transform xml to Bean error,class name:").append(clasz.getName()).toString();
			logger.error(errorMsg, ex);
			throw new XMLTransformException(errorMsg, ex);
		} finally {
			if(closeIns && (ins!=null)) {
				try {
					ins.close();
				} catch(Exception ex) { }				
			}
		}
	}
	
	/**
	 * 
	* @Title: transformObject2XML
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param object
	* @param @param ous    设定文件
	* @return void    返回类型
	* @throws
	 */
	public static void transformObject2XML(Object object, OutputStream ous) {
		JAXBContext context = null;
		Marshaller ms = null;
		try {
			context = JAXBContext.newInstance(object.getClass().getPackage().getName());
			ms = context.createMarshaller();
			ms.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ms.marshal(object, ous);
		} catch(JAXBException ex) {
			String errorMsg = new StringBuilder().append("transform object to XML error,object:").append(object.toString()).toString();
			logger.error(errorMsg, ex);
			throw new XMLTransformException(errorMsg, ex);
		}
	}
	
	/**
	 * 
	* @Title: transformObject2XML
	* @Description: (这里用一句话描述这个方法的作用)
	* @param @param object
	* @param @param ous
	* @param @param closeOus    设定文件
	* @return void    返回类型
	* @throws
	 */
	public static void transformObject2XML(Object object, OutputStream ous, boolean closeOus) {
		JAXBContext context = null;
		Marshaller ms = null;
		try {
			context = JAXBContext.newInstance(object.getClass().getPackage().getName());
			ms = context.createMarshaller();
			ms.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ms.marshal(object, ous);
		} catch(JAXBException ex) {
			String errorMsg = new StringBuilder().append("transform object to XML error,object:").append(object.toString()).toString();
			logger.error(errorMsg, ex);
			throw new XMLTransformException(errorMsg, ex);
		} finally {
			if(closeOus && (ous != null)) {
				try {
					ous.close();
				} catch(Exception e) { }
			}
		}
	}
	
	public static void transformObject2XML(Object object, OutputStream ous, String encoding) {
		JAXBContext context = null;
		Marshaller ms = null;
		try {
			context = JAXBContext.newInstance(object.getClass().getPackage().getName());
			ms = context.createMarshaller();
			ms.setProperty(Marshaller.JAXB_ENCODING, encoding);
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ms.marshal(object, ous);
		} catch(JAXBException ex) {
			String errorMsg = new StringBuilder().append("transform object to XML error,encoding:").append(encoding).append(" object:").append(object.toString()).toString();
			logger.error(errorMsg, ex);
			throw new XMLTransformException(errorMsg, ex);
		}
	}
	
	protected static class XLSValidationEventHandler implements	ValidationEventHandler {
		
		public boolean handleEvent(ValidationEvent event) {
			if (event.getSeverity() > ValidationEvent.WARNING) {
				// log.fatal(validationEvent.getMessage());
				return false;
			}
			return true; // continue unmarshalling
		}
	}

	

}
