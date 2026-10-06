package com.murat.exceptions;

public class LimitMustBeGreaterThanZeroException extends RuntimeException {
    public LimitMustBeGreaterThanZeroException(String message) {
        super(message);
    }
}
