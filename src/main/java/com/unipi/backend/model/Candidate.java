package com.unipi.backend.model;



import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(
		name = "candidate"
)
@PrimaryKeyJoinColumn(
		name = "id"
)
public class Candidate extends User {
	private String birthDate;
	private String notes;

	public Candidate() {
	}

	public Candidate(String username, String password, String firstName, String lastName, String email, String birthDate, String notes) {
		super(username, password, firstName, lastName, email);
		this.birthDate = birthDate;
		this.notes = notes;
	}

	public String getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
