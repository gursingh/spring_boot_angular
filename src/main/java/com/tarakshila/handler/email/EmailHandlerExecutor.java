package com.tarakshila.handler.email;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.tarakshila.service.EmailStatusService;

public class EmailHandlerExecutor {
	private static EmailHandlerExecutor emailHandlerExecutor = null;
	private BlockingQueue<Runnable> blockingQueue = null;
	private CustomThreadPoolExecutor executor = null;

	private EmailHandlerExecutor() {
		try {
			Resource resource = new ClassPathResource("thread.properties");
			Properties props;
			props = PropertiesLoaderUtils.loadProperties(resource);
			blockingQueue = new ArrayBlockingQueue<Runnable>(
					Integer.parseInt(props.getProperty("MESSAGE_QUEUE_SIZE")));
			executor = new CustomThreadPoolExecutor(
					Integer.parseInt(props
							.getProperty("MESSAGE_CORE_POOL_SIZE")),
					Integer.parseInt(props.getProperty("MESSAGE_MAX_POOL_SIZE")),
					5000, TimeUnit.MILLISECONDS, blockingQueue);
		} catch (IOException e) {
			blockingQueue = new ArrayBlockingQueue<Runnable>(50);
			executor = new CustomThreadPoolExecutor(10, 20, 5000,
					TimeUnit.MILLISECONDS, blockingQueue);
		}
	}

	public static EmailHandlerExecutor getInstance() {
		if (emailHandlerExecutor == null) {
			emailHandlerExecutor = new EmailHandlerExecutor();
			emailHandlerExecutor.initiateHandler();
		}
		return emailHandlerExecutor;
	}

	private void initiateHandler() {
		executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable r,
					ThreadPoolExecutor executor) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				executor.execute(r);
			}
		});
		// Let start all core threads initially
		executor.prestartAllCoreThreads();
	}

	public void sendMessage(List<EmailInfoBean> emailInfoBeans,
			EmailStatusService emailStatusService) {
		executor.execute(new MailThread(emailInfoBeans, executor,
				emailStatusService));
	}

}
