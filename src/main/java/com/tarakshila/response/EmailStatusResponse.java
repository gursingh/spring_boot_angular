package com.tarakshila.response;

import java.util.List;

import com.tarakshila.entity.EmailStatus;

public class EmailStatusResponse {
	private List<EmailStatus> emailStatus;
	private long totalCount;

	public List<EmailStatus> getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(List<EmailStatus> emailStatus) {
		this.emailStatus = emailStatus;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
}
