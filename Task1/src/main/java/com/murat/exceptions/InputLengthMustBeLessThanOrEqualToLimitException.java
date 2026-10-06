package com.murat.exceptions;

public class InputLengthMustBeLessThanOrEqualToLimitException extends RuntimeException {
    public InputLengthMustBeLessThanOrEqualToLimitException(String message) {
        super(message);
    }
}
