package com.tarakshila.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tarakshila.entity.Applicant;
import com.tarakshila.repository.ApplicantRepository;
import com.tarakshila.service.ApplicantService;

@Service
@Transactional
public class ApplicantServiceImpl implements ApplicantService {
	@Autowired
	private ApplicantRepository applicantRepository;

	@Override
	public void saveApplicant(Applicant applicant) {
		applicantRepository.save(applicant);
	}

	@Override
	public Applicant findByEmailId(String emailId) {
		return applicantRepository.findByEmailId(emailId);
	}

}
