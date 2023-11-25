package com.app.exception;

public class TokenException extends RuntimeException{
    String message;

    public TokenException (String message) {
        this.message = message;
    }
}
