package com.hackathon.diasporadialog.exceptions;

public class UserNotGrantedToDoActionException extends RuntimeException {

    public UserNotGrantedToDoActionException(String message) {
        super(message);
    }
}
