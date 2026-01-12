package com.project.habitat.backend.exception;

import lombok.Getter;

@Getter
public class InvalidRatingException extends RuntimeException {
    final private String message;

    public InvalidRatingException(ExceptionMessage message) {
        this.message = message.toString();
    }
}
