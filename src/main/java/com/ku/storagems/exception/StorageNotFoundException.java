package com.ku.storagems.exception;

public class StorageNotFoundException extends RuntimeException {

    public StorageNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
