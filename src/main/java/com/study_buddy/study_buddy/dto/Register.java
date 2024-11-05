package com.study_buddy.study_buddy.dto;

public class Register {
	private String email;
	private String firstName;
	private String lastName;
	private String hashedPassword;
	private StudyRole studyRole;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hash) {
		this.hashedPassword = hash;
	}

	public StudyRole getStudyRole() {
		return studyRole;
	}

	public void setStudyRole(StudyRole studyRole) {
		this.studyRole = studyRole;
	}
}
