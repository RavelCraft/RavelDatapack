package com.connexal.raveldatapack.api.exceptions;

public class CustomRecipeException extends RavelDatapackException {
    public CustomRecipeException(String message) {
        super(message);
    }

    public CustomRecipeException(String message, Throwable cause) {
        super(message, cause);
    }
}
