package com.connexal.raveldatapack.api.exceptions;

public class CustomDimensionException extends RavelDatapackException {
    public CustomDimensionException(String message) {
        super(message);
    }

    public CustomDimensionException(String message, Throwable cause) {
        super(message, cause);
    }
}
