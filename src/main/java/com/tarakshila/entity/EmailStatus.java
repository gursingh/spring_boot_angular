package com.tarakshila.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "email_status")
public class EmailStatus {
	private long id;
	private String uniqueCode;
	private boolean clicked;
	private boolean replied;
	private Applicant applicant;
	private Date creationDate;
	private Date updationDate;
	private String uniqueToken;
	private String applicantName;
	private String emailId;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	public long getId() {
		return id;
	}

	public String getUniqueCode() {
		return uniqueCode;
	}

	public boolean isClicked() {
		return clicked;
	}

	public boolean isReplied() {
		return replied;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "applicantId")
	public Applicant getApplicant() {
		return applicant;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public void setReplied(boolean replied) {
		this.replied = replied;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdationDate() {
		return updationDate;
	}

	public void setUpdationDate(Date updationDate) {
		this.updationDate = updationDate;
	}

	public String getUniqueToken() {
		return uniqueToken;
	}

	public void setUniqueToken(String uniqueToken) {
		this.uniqueToken = uniqueToken;
	}

	@Transient
	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	@Transient
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
}
