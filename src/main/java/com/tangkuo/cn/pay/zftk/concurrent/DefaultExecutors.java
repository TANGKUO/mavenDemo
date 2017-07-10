package com.tangkuo.cn.pay.zftk.concurrent;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultExecutors {
	private static Log log = LogFactory.getLog(DefaultExecutors.class);
	
	private final static int DEFAULT_POOL_SIZE = 30;
	
	public static ExecutorService newFixedThreadPool() {
		return Executors.newFixedThreadPool(DEFAULT_POOL_SIZE, defaultThreadFactory());
	}
	
	public static ExecutorService newFixedThreadPool(int nThreads) {
		return Executors.newFixedThreadPool(nThreads, defaultThreadFactory());
	}
	
	public static ScheduledExecutorService newScheduledThreadPool() {
		return Executors.newScheduledThreadPool(DEFAULT_POOL_SIZE, defaultThreadFactory());
	}
	
	public static ScheduledExecutorService newScheduledThreadPool(int nThreads) {
		return Executors.newScheduledThreadPool(nThreads, defaultThreadFactory());
	}
	
	public static ExecutorService newCachedThreadPool(int maximumPoolSize) {
		if(maximumPoolSize < 1) {
			maximumPoolSize = Integer.MAX_VALUE;
		}
		return new ThreadPoolExecutor(0, maximumPoolSize,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                defaultThreadFactory());
	}
	
	public static ExecutorService newCachedThreadPool() {
		return Executors.newCachedThreadPool(defaultThreadFactory());
	}
	
	public static ThreadFactory defaultThreadFactory() {
		return new DefaultThreadFactory();
	}	
	
	static class DefaultThreadFactory implements ThreadFactory {
		final static AtomicInteger poolNumber = new AtomicInteger(1);
		final ThreadGroup group;
		final AtomicInteger threadNumber = new AtomicInteger(1);
		final String namePrefix;
		
		private UncaughtExceptionHandler handler = new UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				log.error("thread error:"+t.getName(), e);
			}
		};
		
		DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null)? s.getThreadGroup() :Thread.currentThread().getThreadGroup();          
            namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
        }
		
		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r,namePrefix + threadNumber.getAndIncrement(),0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            t.setUncaughtExceptionHandler(handler);
            return t;
		}			
	}

}
