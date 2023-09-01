//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.unipi.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Appointment {
	@Id
	@GeneratedValue(
			strategy = GenerationType.IDENTITY
	)
	private long id;
	private long candidateId;
	private long recruiterId;
	private String location;
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	private LocalDateTime dateTime;

	public Appointment() {
	}

	public Appointment(long candidateId, long recruiterId, String location, LocalDateTime dateTime) {
		this.candidateId = candidateId;
		this.recruiterId = recruiterId;
		this.location = location;
		this.dateTime = dateTime;
	}

	public long getCandidateId() {
		return this.candidateId;
	}

	public void setCandidateId(long candidateId) {
		this.candidateId = candidateId;
	}

	public long getRecruiterId() {
		return this.recruiterId;
	}

	public void setRecruiterId(long recruiterId) {
		this.recruiterId = recruiterId;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDateTime getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
}
