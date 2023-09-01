
package com.unipi.backend.model;


import javax.persistence.Entity;

@Entity
public class Recruiter extends User {
	private String token;

	public Recruiter() {
	}

	public Recruiter(String username, String password, String firstName, String lastName, String email, String token) {
		super(username, password, firstName, lastName, email);
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
