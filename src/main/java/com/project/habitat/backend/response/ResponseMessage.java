package com.project.habitat.backend.response;

public enum ResponseMessage {
    LOGIN_SUCCESSFUL("Login successful"),
    AUTHENTICATION_FAILED("Authentication Failed"),
    ACCESS_DENIED("Access Denied"),
    TODO_STARTED("Todo started"),
    TODO_PAUSED("Todo paused"),
    ;
    private final String message;
    ResponseMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return message;
    }
}
