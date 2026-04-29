package com.example.traiviu_backend.exceptions;

public class InvalidInviteCodeException extends RuntimeException {

    public InvalidInviteCodeException(String message) {
        super(message);
    }
}