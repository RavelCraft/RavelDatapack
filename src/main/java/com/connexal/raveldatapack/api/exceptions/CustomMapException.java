package com.connexal.raveldatapack.api.exceptions;

public class CustomMapException extends RavelDatapackException {
    public CustomMapException(String message) {
        super(message);
    }

    public CustomMapException(String message, Throwable cause) {
        super(message, cause);
    }
}
