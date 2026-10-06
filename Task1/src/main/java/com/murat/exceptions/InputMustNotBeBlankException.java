package com.murat.exceptions;

public class InputMustNotBeBlankException extends RuntimeException {
    public InputMustNotBeBlankException(String message) {
        super(message);
    }
}
