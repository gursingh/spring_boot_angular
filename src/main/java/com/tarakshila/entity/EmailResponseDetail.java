package com.tarakshila.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EmailResponseDetail {
	private Long id;
	private String name;
	private String cxo;
	private String salary;
	private String timeEst;

	private String email;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCxo() {
		return cxo;
	}

	public void setCxo(String cxo) {
		this.cxo = cxo;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getTimeEst() {
		return timeEst;
	}

	public void setTimeEst(String timeEst) {
		this.timeEst = timeEst;
	}
}
