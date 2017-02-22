package com.tarakshila.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tarakshila.entity.Applicant;

@Repository
public interface ApplicantRepository extends CrudRepository<Applicant, Long> {
	@SuppressWarnings("unchecked")
	public Applicant save(Applicant applicant);

	public Applicant findByEmailId(String emailId);
}
