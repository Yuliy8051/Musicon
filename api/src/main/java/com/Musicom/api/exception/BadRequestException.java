package com.Musicom.api.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    public static class UrlNotUniqueException extends BadRequestException {
        public UrlNotUniqueException(String urlOwner) {
            super(urlOwner + " url is not unique");
        }
    }
}
