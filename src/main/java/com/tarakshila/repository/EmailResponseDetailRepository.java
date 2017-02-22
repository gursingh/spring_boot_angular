package com.tarakshila.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tarakshila.entity.EmailResponseDetail;

@Repository
public interface EmailResponseDetailRepository extends
		CrudRepository<EmailResponseDetail, Long> {
	@SuppressWarnings("unchecked")
	public EmailResponseDetail save(EmailResponseDetail emailResponseDetail);
}
