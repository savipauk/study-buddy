package com.study_buddy.study_buddy.dto;

public class Login {
	private String email;
	private String hashedPassword;
	private String username;
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hash) {
		this.hashedPassword = hash;
	}

	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }
}
