package com.tarakshila.service;

import com.tarakshila.entity.Applicant;

public interface ApplicantService {
	public void saveApplicant(Applicant applicant);

	public Applicant findByEmailId(String emailId);
}
