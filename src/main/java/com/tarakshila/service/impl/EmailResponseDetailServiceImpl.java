package com.tarakshila.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tarakshila.entity.Applicant;
import com.tarakshila.entity.EmailResponseDetail;
import com.tarakshila.entity.EmailStatus;
import com.tarakshila.repository.ApplicantRepository;
import com.tarakshila.repository.EmailResponseDetailRepository;
import com.tarakshila.repository.EmailStatusRepository;
import com.tarakshila.service.EmailResponseDetailService;

@Service
@Transactional
public class EmailResponseDetailServiceImpl implements
		EmailResponseDetailService {
	@Autowired
	private EmailResponseDetailRepository emailResponseDetailRepository;
	@Autowired
	private EmailStatusRepository emailStatusRepository;
	@Autowired
	private ApplicantRepository applicantRepository;

	@Override
	public String updateEmailResponseDetail(
			EmailResponseDetail emailResponseDetail, String token) {
		EmailStatus emailStatus = emailStatusRepository
				.findByUniqueToken(token);
		if (emailStatus.getUniqueToken() != null
				&& token.equals(emailStatus.getUniqueToken())) {
			emailResponseDetailRepository.save(emailResponseDetail);
			emailStatus.setUniqueToken(null);
			emailStatusRepository.save(emailStatus);
			return "success";
		}
		return null;
	}

	@Override
	public String saveEmailResponseDetail(
			EmailResponseDetail emailResponseDetail) {
		emailResponseDetailRepository.save(emailResponseDetail);
		Applicant applicant = applicantRepository
				.findByEmailId(emailResponseDetail.getEmail());
		if (applicant != null) {
			List<EmailStatus> emailStatusList = emailStatusRepository
					.findByApplicantId(applicant.getId());
			if (emailStatusList != null)
				for (EmailStatus emailStatus : emailStatusList) {
					emailStatus.setReplied(true);
					emailStatusRepository.save(emailStatus);
				}

		}
		return "sucess";
	}

}
