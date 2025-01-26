package com.Musicom.api.exception;

public class NotImageLinkException extends RuntimeException {
    public NotImageLinkException(String link) {
        super("Link " + link + " does not point at an image");
    }
}
