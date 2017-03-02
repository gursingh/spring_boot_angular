package com.tarakshila.repository.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tarakshila.entity.EmailStatus;
import com.tarakshila.repository.EmailStatusRepository;

@Repository
public class EmailStatusRepositoryImpl implements EmailStatusRepository {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public EmailStatus save(EmailStatus emailStatus) {
		Session session = sessionFactory.getCurrentSession();
		session.save(emailStatus);
		return emailStatus;
	}

	@Override
	public EmailStatus findByUniqueCodeAndApplicant(String code,
			long applicantId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(EmailStatus.class);
		cr.add(Restrictions.eq("uniqueCode", code));
		cr.createAlias("applicant", "a").add(
				Restrictions.eq("applicant.id", applicantId));
		return (EmailStatus) cr.uniqueResult();
	}

	@Override
	public EmailStatus findByUniqueToken(String authToken) {
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(EmailStatus.class);
		cr.add(Restrictions.eq("uniqueToken", authToken));
		return (EmailStatus) cr.uniqueResult();
	}

	@Override
	public List<EmailStatus> findAll(int pageNumber, int pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Query selectQuery = session
				.createSQLQuery("select e_status.id as id,replied,clicked, a.name as name, a.email_id as emailId,e_status.creation_date as creationDate from email_status e_status inner join applicant a on e_status.applicant_id=a.id");
		selectQuery.setFirstResult((pageNumber) * pageSize);
		selectQuery.setMaxResults(pageSize);
		@SuppressWarnings("unchecked")
		List<Object[]> lastPage = selectQuery.list();
		List<EmailStatus> emailStatusList = new ArrayList<EmailStatus>();
		for (Object[] tuple : lastPage) {
			EmailStatus emailStatus = new EmailStatus();
			emailStatus.setId(((BigInteger) tuple[0]).longValue());
			emailStatus.setReplied((boolean) tuple[1]);
			emailStatus.setClicked((boolean) tuple[2]);
			emailStatus.setApplicantName((String) tuple[3]);
			emailStatus.setEmailId((String) tuple[4]);
			emailStatus.setCreationDate((Date) tuple[5]);
			emailStatusList.add(emailStatus);
		}
		return emailStatusList;
	}

	@Override
	public EmailStatus findByApplicantId(long applicantId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(EmailStatus.class, "emailstatus");
		c.createAlias("emailstatus.applicant", "applicant"); // inner join by
																// default
		c.add(Restrictions.eq("applicant.id", applicantId));
		return (EmailStatus) c.uniqueResult();
	}

	@Override
	public long countById() {
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(EmailStatus.class);
		return (long) c.setProjection(Projections.rowCount()).uniqueResult();
	}
}
