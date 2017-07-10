package com.tangkuo.cn.pay.zftk.common.http;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.DnsResolver;

/**
 * 对DNS解析进行缓存处理，并定时对缓存的数据进行重新解析校验
 * @author tsm
 *
 */
public class CacheDnsResolver implements DnsResolver, Runnable {
	private static Log log = LogFactory.getLog(CacheDnsResolver.class);
	
	public static final int MAX_CACHE_SIZE = 100000;
	private static ConcurrentLRUCache<String, InetAddressData> DNSCacheData = null;
	
	public CacheDnsResolver(int maxSize) {
		init(maxSize);
	}
	
	public CacheDnsResolver() {
		init(MAX_CACHE_SIZE);
	}
	
	private void init(int size) {
		int maxSize = size>0 ? size : MAX_CACHE_SIZE ;			
		int minSize = (int) (maxSize  * 0.9);
		int acceptableSize = (int) (maxSize  * 0.95);
		int initialSize = (int) (maxSize  * 0.5);
		
		DNSCacheData = new ConcurrentLRUCache<String, InetAddressData>(maxSize, minSize, acceptableSize, initialSize, true, false, null);
	}

	@Override
	/**
	 * 缓存中5分钟内的数据直接返回，否则重新解析新地址，有异常直接返回缓存数据
	 */
	public InetAddress[] resolve(String hostName) throws UnknownHostException {
		InetAddressData addressData = DNSCacheData.get(hostName);
		if(addressData != null) {			
			long time = System.currentTimeMillis() - addressData.getCheckTime();
			if(time <= 300*1000L) {
				return addressData.getAddressArray();		
			}			
		}
		
		try {
			InetAddress[] addressArray = InetAddress.getAllByName(hostName);			
			if(addressData != null) {
				//更新
				addressData.setUseTime(System.currentTimeMillis());
				updateAddressData(addressData, addressArray);
			} else {
				//新增
				InetAddressData data = new InetAddressData();
				data.setUseTime(System.currentTimeMillis());
				data.setCheckTime(System.currentTimeMillis());
				data.setAddressArray(addressArray);
				DNSCacheData.put(hostName, data);
			}			
			
			return addressArray;
		} catch(UnknownHostException ex) {
			if(addressData != null) {
				return addressData.getAddressArray();
			} else {
				throw ex;
			}
		}		
	}
	
	@Override
	public void run() {
		try {
			checkDNS();
			
		} catch (Throwable ex) {
			log.error("check DNS error", ex);
		}
				
	}
	
	private void checkDNS() {		
		log.info("begin check DNS,size is " + DNSCacheData.getMap().size());
		
		Iterator<Object> keyIter = DNSCacheData.getMap().keySet().iterator();
		while(keyIter.hasNext()) {
			String hostName = (String)keyIter.next();
			InetAddressData addressData = DNSCacheData.get(hostName);
			//清除240小时未使用过的数据
			long time = System.currentTimeMillis() - addressData.getUseTime();
			if(time >= 240*3600*1000L) {
				log.info("remove host " + hostName);
				DNSCacheData.remove(hostName);
				continue;
			}
			
			//重新检测DNS，如果InetAddress值不相同则更新
			try {
				InetAddress[] addressArray = InetAddress.getAllByName(hostName);
				updateAddressData(addressData, addressArray);
				
			} catch(Exception ex) {
				log.error(ex.getMessage(),ex);
			}
		}
		
		log.info("end check DNS");
	}
	
	private void updateAddressData(InetAddressData addressData, InetAddress[] newAddressArray) {
		addressData.setCheckTime(System.currentTimeMillis());
		
		if(addressData.getAddressArray().length != newAddressArray.length) {
			log.warn("size is not equal,update dns: " + newAddressArray);
			addressData.setAddressArray(newAddressArray);
			return;
		}
		
		InetAddress[] sourceAddress = addressData.getAddressArray();
		Map<String,String> addressMap = new HashMap<String,String>();
		for(int i=0; i<sourceAddress.length; i++) {
			addressMap.put(sourceAddress[i].toString(), null);
		}
		for(int i=0; i<newAddressArray.length; i++) {
			String address = newAddressArray[i].toString();
			log.info("check address: " + address);
			if(!addressMap.containsKey(address)) {
				log.warn("update dns: " + address);
				addressData.setAddressArray(newAddressArray);
				break;
			}
		}
	}
	
	private class InetAddressData {
		private InetAddress[] addressArray;
		private long useTime;
		private long checkTime;
		
		public InetAddress[] getAddressArray() {
			return addressArray;
		}
		public void setAddressArray(InetAddress[] addressArray) {
			this.addressArray = addressArray;
		}
		public long getUseTime() {
			return useTime;
		}
		public void setUseTime(long useTime) {
			this.useTime = useTime;
		}
		public long getCheckTime() {
			return checkTime;
		}
		public void setCheckTime(long checkTime) {
			this.checkTime = checkTime;
		}				
	}	

}
