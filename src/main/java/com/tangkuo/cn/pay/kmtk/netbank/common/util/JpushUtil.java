package com.kame.micropay.commons.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.Notification.Builder;

import com.kame.micropay.commons.config.properties.Property;

/**
 * 极光推送工具类
 * @author Administrator
 *
 */
public class JpushUtil {

        private static Logger log = LoggerFactory.getLogger(JpushUtil.class);
        
	    //极光推送配置 resources.properties
		private static final String appKey = Property.getProperty("jpush_appkey");
		private static final String masterSecret = Property.getProperty("jpush_mastersecret");
		//true：生产环境，false:开发环境
		private static final boolean apnsProduction = Boolean.valueOf(Property.getProperty("jpush_apns_production"));
		
		//定义最大重新推送次数
		private static final int MAX_RETRY_SEND = 3;
		
		
		/**
		 * 发送消息-指定用户接收
		 * @param alias
		 * @param title
		 * @param content
		 */
		public static void sendMessageByAlias(String alias,String title,String content){
			log.info("推送消息，alias:{}，title:{},content:{}",alias,title,content);
			JPushClient jpushClient = new JPushClient(masterSecret, appKey, MAX_RETRY_SEND);
	        PushPayload payload = PushPayload.newBuilder()
	                .setPlatform(Platform.all())
	                .setAudience(Audience.alias(alias))
	                .setMessage(Message.newBuilder().setMsgContent(content).build())
	                .build();
	        payload.resetOptionsApnsProduction(apnsProduction);
	        try {
	            PushResult result = jpushClient.sendPush(payload);
	            log.info("推送结果,Result: " + result);
	            
	        } catch (APIConnectionException e) {
	        	log.error("连接极光推送服务失败---"+e);
	            
	        } catch (APIRequestException e) {
	        	log.error("推送消息失败---",e);
	        }
		}
		
		/**
		 * 发送消息-所有用户接收
		 * @param title
		 * @param content
		 */
		public static void sendPushAll(String title,String content){
			log.info("推送消息，user:All,title:{},content:{}",title,content);
			JPushClient jpushClient = new JPushClient(masterSecret, appKey, MAX_RETRY_SEND);
	        PushPayload payload = PushPayload.newBuilder()
	                .setPlatform(Platform.all())
	                .setAudience(Audience.all())
	                .setMessage(Message.newBuilder().setMsgContent(content).build())
	                .build();
	        payload.resetOptionsApnsProduction(apnsProduction);
	        try {
	            PushResult result = jpushClient.sendPush(payload);
	            log.info("推送结果,Result: " + result);
	            
	        } catch (APIConnectionException e) {
	        	log.error("连接极光推送服务失败---"+e);
	            
	        } catch (APIRequestException e) {
	        	log.error("推送消息失败---",e);
	        }
		}
		
		/**
		 * 发送通知-指定用户接收
		 * @param alias
		 * @param title
		 * @param content
		 */
		public static void sendNotifyByAlias(String[] alias,String title,String content,Map<String, String> extraMap){
			log.info("推送消息，alias:{}，title:{},content:{}",alias,title,content);
			JPushClient jpushClient = new JPushClient(masterSecret, appKey, MAX_RETRY_SEND);
			Builder builder  = null;
			//添加附件字段
			if (null != extraMap) {
				builder = Notification.newBuilder().
				addPlatformNotification(IosNotification.newBuilder().setAlert(title).addExtras(extraMap).build())
				.addPlatformNotification(AndroidNotification.newBuilder().setAlert(title).addExtras(extraMap).build());
			}else{
				builder = Notification.newBuilder().setAlert(title);
			}
			
			PushPayload payload = PushPayload.newBuilder()
            .setPlatform(Platform.all())
            .setAudience(Audience.alias(alias))
            .setNotification(builder.build())
            .build();
			payload.resetOptionsApnsProduction(apnsProduction);
	        try {
	            PushResult result = jpushClient.sendPush(payload);
	            log.info("推送结果,Result: " + result);
	            
	        } catch (APIConnectionException e) {
	        	log.error("连接极光推送服务失败---"+e);
	            
	        } catch (APIRequestException e) {
	        	log.error("推送消息失败---",e);
	        }
		}
		
		/**
		 * 发送通知-所有用户接收
		 * @param title
		 * @param content
		 */
		public static void sendNotifyAll(String title,String content){
			log.info("推送消息，user:All,title:{},content:{}",title,content);
			JPushClient jpushClient = new JPushClient(masterSecret, appKey, MAX_RETRY_SEND);
	        PushPayload payload = PushPayload.alertAll(content);
	        payload.resetOptionsApnsProduction(apnsProduction);
	        try {
	            PushResult result = jpushClient.sendPush(payload);
	            log.info("推送结果,Result: " + result);
	            
	        } catch (APIConnectionException e) {
	        	log.error("连接极光推送服务失败---"+e);
	            
	        } catch (APIRequestException e) {
	        	log.error("推送消息失败---",e);
	        }
		}
		
		/**
		 * 发送消息-指定一个或多个用户
		 * @param alias
		 * @param title
		 * @param content
		 */
		public static void sendPush(String[] alias,String title,String content){
			log.info("推送消息，alias:{}，title:{},content:{}",alias,title,content);
			JPushClient jpushClient = new JPushClient(masterSecret, appKey, MAX_RETRY_SEND);
	        PushPayload payload = PushPayload.newBuilder()
	                .setPlatform(Platform.all())
	                .setAudience(Audience.alias(alias))
	                .setMessage(Message.newBuilder().setMsgContent(content).build())
	                .build();
	        payload.resetOptionsApnsProduction(apnsProduction);
	        try {
	            PushResult result = jpushClient.sendPush(payload);
	            log.info("推送结果,Result: " + result);
	            
	        } catch (APIConnectionException e) {
	        	log.error("连接极光推送服务失败---"+e);
	            
	        } catch (APIRequestException e) {
	        	log.error("推送消息失败---",e);
	        }
		}
		
}
