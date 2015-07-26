package com.bendani.bibliomania.generic.exception;

public class NoInternetConnectionError extends RuntimeException {

    public NoInternetConnectionError() {
        super("Device is not connected to the internet");
    }
}
