package com.study_buddy.study_buddy.model;

public enum LessonType {
    MASS("MASS"),
    ONE_ON_ONE("ONE_ON_ONE");

    private final String value;

    LessonType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static LessonType fromType(String value) {
        for (LessonType lessonType : LessonType.values()) {
            if (lessonType.value.equalsIgnoreCase(value)) {
                return lessonType;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
