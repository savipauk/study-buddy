package com.study_buddy.study_buddy.dto;


import com.study_buddy.study_buddy.model.StudyRole;

import java.util.Map;

public class Register {
	private String email;
	private String firstName;
	private String lastName;
	private String hashedPassword;
	private StudyRole studyRole;
	private String username;
	private String profile_picture;
	private String description;

	public Map<String,String> responseRegister(String message){
		Map<String, String> response;
		response = Map.of(
				"firstName", this.getFirstName(),
				"lastName", this.getLastName(),
				"email", this.getEmail(),
				"studyRole", this.getStudyRole().toString(),
				"username", this.getUsername(),
				"registration", message
		);
		return response;
	}

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

	public StudyRole getStudyRole() {return studyRole;}

	public void setStudyRole(StudyRole studyRole) {
		this.studyRole = studyRole;
	}

	public String getUsername() { return username; }

	public void setUsername(String username) { this.username = username; }

	public String getProfile_picture() { return profile_picture; }

	public void setProfile_picture(String profile_picture) { this.profile_picture = profile_picture; }

	public String getDescription() { return description; }

	public void setDescription(String description) { this.description = description; }
}
