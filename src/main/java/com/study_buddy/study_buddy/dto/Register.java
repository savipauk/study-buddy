package com.study_buddy.study_buddy.dto;


import com.study_buddy.study_buddy.model.Gender;
import com.study_buddy.study_buddy.model.StudyRole;

import java.time.LocalDate;
import java.util.Map;

public class Register {
	private String email;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private StudyRole role;
	private String description;
	private String profilePicture;
	private LocalDate dateOfBirth;
	private String city;
	private Gender gender;
	private String phoneNumber;

	public Map<String,String> responseRegister(String message){
		Map<String, String> response;
		response = Map.of(
				"firstName", this.getFirstName(),
				"lastName", this.getLastName(),
				"email", this.getEmail(),
				"studyRole", this.getRole().toString(),
				"username", this.getUsername(),
				"phoneNumber", this.getPhoneNumber(),
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String hash) {
		this.password = hash;
	}

	public StudyRole getRole() {return role;}

	public void setRole(StudyRole studyRole) {
		this.role = studyRole;
	}

	public String getUsername() { return username; }

	public void setUsername(String username) { this.username = username; }

	public String getProfilePicture() { return profilePicture; }

	public void setProfilePicture(String profile_picture) { this.profilePicture = profile_picture; }

	public String getDescription() { return description; }

	public void setDescription(String description) { this.description = description; }

	public LocalDate getDateOfBirth() { return dateOfBirth; }

	public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

	public String getCity() { return city; }

	public void setCity(String city) { this.city = city; }

	public Gender getGender() { return gender; }

	public void setGender(Gender gender) { this.gender = gender; }

	public String getPhoneNumber() { return phoneNumber; }

	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
