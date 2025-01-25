package com.Musicom.api.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public static class PageNotFoundException extends NotFoundException {
        public PageNotFoundException(int page) {
            super("Page " + page + " not found");
        }
    }
}
