package com.example.MyPersonalContactManager.exceptions;

public class InvalidLoginPasswordException extends RuntimeException {
    public InvalidLoginPasswordException(String msg) {
        super(msg);
    }
}
