package com.study_buddy.study_buddy.dto;

public class Login {
	private String email;
	private String hashedPassword;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hash) {
		this.hashedPassword = hash;
	}
}
