package com.study_buddy.study_buddy.model;

public enum StudyRole {
	UNDEFINED("UNDEFINED"),
	STUDENT("STUDENT"),
	PROFESSOR("PROFESSOR"),
	ADMIN ("ADMIN"),
	UNASSIGNED("UNASSIGNED");

	private final String value;

	StudyRole(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static StudyRole fromRole(String value) {
		for (StudyRole studyRole : StudyRole.values()) {
			if (studyRole.value.equalsIgnoreCase(value)) {
				return studyRole;
			}
		}
		throw new IllegalArgumentException("Unknown value: " + value);
	}
}
