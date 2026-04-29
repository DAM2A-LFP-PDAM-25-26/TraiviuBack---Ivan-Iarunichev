package com.example.traiviu_backend.exceptions;

public class ClanAccessDeniedException extends RuntimeException {

    public ClanAccessDeniedException(String message) {
        super(message);
    }
}