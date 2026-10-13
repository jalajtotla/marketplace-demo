package com.example.marketplace.auth.exception;

public class NoAuthorizationData extends RuntimeException {

    private static final String message = "Missing authentication data.";

    public NoAuthorizationData(){
        super(message);
    }
}
