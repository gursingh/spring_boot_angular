package com.tarakshila.repository;

import java.util.List;

import com.tarakshila.entity.EmailStatus;

public interface EmailStatusRepository {
	public EmailStatus save(EmailStatus emailStatus);

	public EmailStatus findByUniqueCodeAndApplicant(String code,
			long applicantId);

	public EmailStatus findByUniqueToken(String authToken);

	public List<EmailStatus> findAll(int pageNumber, int pageSize);

	public List<EmailStatus> findByApplicantId(long applicantId);

	public long countById();

	public void updateEmailStatusByApplicantEmail(String emailId, boolean status);
}