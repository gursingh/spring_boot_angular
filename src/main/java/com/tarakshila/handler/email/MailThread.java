package com.tarakshila.handler.email;

public class MailThread implements Runnable {
	private int numberOfTry;
	private EmailInfoBean emailInfoBean = null;
	private CustomThreadPoolExecutor executor = null;

	public MailThread(EmailInfoBean messageInfoBean,
			CustomThreadPoolExecutor executor) {
		this.emailInfoBean = messageInfoBean;
		this.executor = executor;
	}

	public EmailInfoBean getMessageInfoBean() {
		return this.emailInfoBean;
	}

	@Override
	public void run() {
		try {
			numberOfTry++;
			EmailUtil.sendMessage(emailInfoBean);
		} catch (Exception e) {
			if (numberOfTry < 4) {
				executor.execute(this);
			}
		}
	}
}
