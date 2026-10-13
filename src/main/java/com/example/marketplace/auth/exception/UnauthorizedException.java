package com.example.marketplace.auth.exception;

public class UnauthorizedException extends RuntimeException {

    private static final String message = "Unauthenticated user.";

    public UnauthorizedException(){
        super(message);
    }

    public UnauthorizedException(Exception e){
        super(message, e);
    }
}
