package com.connexal.raveldatapack.api.exceptions;

public class CustomItemException extends RavelDatapackException {
    public CustomItemException(String message) {
        super(message);
    }

    public CustomItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
