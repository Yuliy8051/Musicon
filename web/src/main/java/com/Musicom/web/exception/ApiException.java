package com.Musicom.web.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
