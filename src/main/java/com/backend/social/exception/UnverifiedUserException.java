package com.backend.social.exception;

public class UnverifiedUserException extends RuntimeException{
    public UnverifiedUserException(String message) {
        super(message);
    }
}
