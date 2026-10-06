package com.murat.exceptions;

public class LimitMustBeGreaterThanZeroException extends InvalidInputException {
    public LimitMustBeGreaterThanZeroException(String message) {
        super(message);
    }
}
