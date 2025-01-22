package com.Musicom.spotify_client.exception;

public class TokenFetchingException extends RuntimeException {
    public TokenFetchingException() {
        super("The value of token provided by Spotify API is null");
    }
}
