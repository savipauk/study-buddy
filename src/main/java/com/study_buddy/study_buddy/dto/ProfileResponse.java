package com.study_buddy.study_buddy.dto;

import com.study_buddy.study_buddy.model.StudyRole;

import java.time.LocalDate;

public class ProfileResponse {
	// private Long userId;
	private String email;
	private String firstName;
	private String lastName;
	private StudyRole role;
	private String description;
	// private String accessToken;
	private String profilePicture;
	private String city;

	// public String getAccessToken() {
	// return accessToken;
	// }
	//
	// public void setAccessToken(String accessToken) {
	// this.accessToken = accessToken;
	// }

	public StudyRole getRole() {
		return role;
	}

	public void setRole(StudyRole role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//public Long getUserId() {
	//	return userId;
	//}
	//
	//public void setUserId(Long userId) {
	//	this.userId = userId;
	//}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
}
