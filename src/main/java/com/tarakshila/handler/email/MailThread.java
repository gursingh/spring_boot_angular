package com.tarakshila.handler.email;

import java.util.List;

import com.tarakshila.service.EmailStatusService;

public class MailThread implements Runnable {
	private int numberOfTry;
	private List<EmailInfoBean> emailInfoBeans = null;
	private CustomThreadPoolExecutor executor = null;
	private EmailStatusService emailStatusService;

	public MailThread(List<EmailInfoBean> messageInfoBeans,
			CustomThreadPoolExecutor executor,
			EmailStatusService emailStatusService) {
		this.emailInfoBeans = messageInfoBeans;
		this.executor = executor;
		this.emailStatusService = emailStatusService;
	}

	public List<EmailInfoBean> getMessageInfoBean() {
		return this.emailInfoBeans;
	}

	@Override
	public void run() {
		try {
			numberOfTry++;
			EmailUtil.sendMessage(emailInfoBeans, emailStatusService);
		} catch (Exception e) {
			if (numberOfTry < 4) {
				executor.execute(this);
			}
		}
	}
}
