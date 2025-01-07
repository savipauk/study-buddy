package com.study_buddy.study_buddy.dto;

public class Login {
	private String email;
	private String password;
	private String username;
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() { return password;}

	public void setPassword(String password) { this.password = password; }

	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }
}
