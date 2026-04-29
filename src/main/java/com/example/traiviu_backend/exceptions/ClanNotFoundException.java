package com.example.traiviu_backend.exceptions;

public class ClanNotFoundException extends RuntimeException {

    public ClanNotFoundException(String message) {
        super(message);
    }
}