package com.study_buddy.study_buddy.model;

public enum Status {
    OPEN("OPEN"),
    CLOSED("CLOSED"),
    IN_PROGRESS("IN_PROGRESS"),
    REJECTED("REJECTED"),
    ACTIVE("ACTIVE"),
    DEACTIVATED("DEACTIVATED");


    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Status fromStatus(String value) {
        for (Status status : Status.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
