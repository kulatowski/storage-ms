package com.ku.storagems.exception;

public class UserFileNotFoundException extends RuntimeException {

    public UserFileNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
