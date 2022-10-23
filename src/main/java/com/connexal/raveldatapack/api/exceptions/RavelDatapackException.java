package com.connexal.raveldatapack.api.exceptions;

public class RavelDatapackException extends RuntimeException {
    public RavelDatapackException(String message) {
        super(message);
    }

    public RavelDatapackException(String message, Throwable cause) {
        super(message, cause);
    }
}
