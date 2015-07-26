package com.bendani.bibliomania.login.exception;

public class UserNotLoggedInException extends RuntimeException {
    public UserNotLoggedInException() {
        super("No user logged in");
    }
}
