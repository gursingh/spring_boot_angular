package com.tarakshila.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "applicant")
public class Applicant {
	private Long id;
	private String name;
	private String phoneNumber;
	@Column(unique = true)
	private String emailId;
	private Set<EmailStatus> emailStatus = new HashSet<EmailStatus>();
	private boolean mailSent;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	@OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<EmailStatus> getEmailStatus() {
		return emailStatus;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public void setEmailStatus(Set<EmailStatus> emailStatus) {
		this.emailStatus = emailStatus;
	}

	public boolean isMailSent() {
		return mailSent;
	}

	public void setMailSent(boolean mailSent) {
		this.mailSent = mailSent;
	}

}
