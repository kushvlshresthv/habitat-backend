package com.project.habitat.backend.exception;

public class TodoNotCompletedException extends RuntimeException{
    public TodoNotCompletedException() {
        super(ExceptionMessage.TODO_NOT_COMPLETED.toString());
    }
}
