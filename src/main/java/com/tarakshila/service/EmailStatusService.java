package com.tarakshila.service;

import java.util.List;

import com.tarakshila.entity.EmailStatus;

public interface EmailStatusService {
	public void saveEmailStatus(EmailStatus emailStatus);

	public String emailConfirmationReply(String code, String applicantEmailId);

	public List<EmailStatus> findAll(int pageNumber, int pageSize);
}
