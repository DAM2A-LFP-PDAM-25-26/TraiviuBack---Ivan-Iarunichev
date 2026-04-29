package com.example.traiviu_backend.exceptions;

public class AlreadyClanMemberException extends RuntimeException {

    public AlreadyClanMemberException(String message) {
        super(message);
    }
}