package com.tangkuo.cn.pay.kmtk.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @ClassName: ScheduleExecuteTask
 * @Description: (这里用一句话描述这个类的作用)
 * @author tangkuo
 * @date 2017年7月16日 下午5:47:44
 *
 */

public class ScheduleExecuteTask {

	public ScheduledExecutorService scheduExecutor = Executors.newScheduledThreadPool(10);

	// 启动计时器
	public void lanuchTimer() {
		Runnable command = new Runnable() {
			public void run() {
				System.out.println("========gogogogogogogogogogogogogogo========");
			}
		};

		scheduExecutor.schedule(command, 1, TimeUnit.SECONDS);
	}

	public static void main(String[] args) {
		ScheduleExecuteTask task = new ScheduleExecuteTask();

		task.lanuchTimer();
	}

}
