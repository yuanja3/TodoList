package com.bootcamp.todolist.exception;

public class IncompletePayloadException extends RuntimeException {
    public IncompletePayloadException(String message) {
        super(message);
    }
}
