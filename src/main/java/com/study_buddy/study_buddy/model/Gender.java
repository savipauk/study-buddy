package com.study_buddy.study_buddy.model;

public enum Gender {
	NOTDEFINED("NOTDEFINED"),
	MALE("M"),
	FEMALE("F");

	private final String value;

	Gender(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static Gender fromGender(String value) {
		for (Gender gender : Gender.values()) {
			if (gender.value.equalsIgnoreCase(value)) {
				return gender;
			}
		}
		throw new IllegalArgumentException("Unknown value: " + value);
	}
}
