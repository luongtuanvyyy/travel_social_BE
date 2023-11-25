package com.app.exception;

public class EntityNotFountException extends RuntimeException{
    String message;

    public EntityNotFountException (String message) {
        this.message = message;
    }
}
