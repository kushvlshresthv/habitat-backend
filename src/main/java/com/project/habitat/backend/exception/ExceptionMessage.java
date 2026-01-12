package com.project.habitat.backend.exception;

public enum ExceptionMessage {

    USER_DOES_NOT_EXIST("User does not exist"),
    VALIDATION_FAILED("Validation failed"),
    INVALID_RATING("Rating should be an integer between 0 and 10"),
    ;

    final private String message;
    ExceptionMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return message;
    }
}
