package com.tarakshila.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tarakshila.entity.Applicant;
import com.tarakshila.entity.EmailStatus;
import com.tarakshila.repository.ApplicantRepository;
import com.tarakshila.repository.EmailStatusRepository;
import com.tarakshila.service.EmailStatusService;

@Service
@Transactional
public class EmailStatusServiceImpl implements EmailStatusService {
	@Autowired
	private EmailStatusRepository emailStatusRepository;
	@Autowired
	private ApplicantRepository applicantRepository;

	@Override
	public void saveEmailStatus(EmailStatus emailStatus) {
		emailStatusRepository.save(emailStatus);
	}

	@Override
	public String emailConfirmationReply(String code, String applicantEmailId) {
		Applicant applicant = applicantRepository
				.findByEmailId(applicantEmailId);
		if (applicant != null) {
			EmailStatus emailStatus = emailStatusRepository
					.findByUniqueCodeAndApplicant(code, applicant.getId());
			if (emailStatus != null) {
				emailStatus.setClicked(true);
				emailStatus.setUpdationDate(new Date());
				emailStatus.setUniqueCode(null);
				String uniqueToken = new ObjectId().toString() + ":"
						+ applicantEmailId;
				emailStatus.setUniqueToken(uniqueToken);
				emailStatusRepository.save(emailStatus);
				return uniqueToken;
			}
		}
		return null;
	}

	@Override
	public List<EmailStatus> findAll(int pageNumber, int pageSize) {
		List<EmailStatus> result = new ArrayList<EmailStatus>();
		List<EmailStatus> emailStatusList = (ArrayList<EmailStatus>) emailStatusRepository
				.findAll(pageNumber, pageSize);
		for (EmailStatus emailStatus : emailStatusList) {
			result.add(emailStatus);
		}
		return result;
	}

	@Override
	public long findTotalCount() {
		return emailStatusRepository.countById();
	}

	@Override
	public void updateEmailStatusByApplicantEmail(String emailId, boolean status) {
		emailStatusRepository.updateEmailStatusByApplicantEmail(emailId, status);
	}

}
