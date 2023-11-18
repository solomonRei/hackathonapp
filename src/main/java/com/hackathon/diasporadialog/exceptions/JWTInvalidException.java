package com.hackathon.diasporadialog.exceptions;

public class JWTInvalidException extends RuntimeException {

    public JWTInvalidException(String message) {
        super(message);
    }

    public JWTInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
