package com.project.habitat.backend.exception;

public enum ExceptionMessage {

    USER_DOES_NOT_EXIST("User does not exist"),
    VALIDATION_FAILED("Validation failed"),
    ;

    final private String message;
    ExceptionMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return message;
    }
}
