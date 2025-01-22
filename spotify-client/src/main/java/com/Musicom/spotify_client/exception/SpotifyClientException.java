package com.Musicom.spotify_client.exception;

public class SpotifyClientException extends RuntimeException {
    public SpotifyClientException(String message) {
        super(message);
    }

    public static class ThreadSleepException extends SpotifyClientException {
        public ThreadSleepException(String message) {
            super(message);
        }
    }

    public static class TokenFetchingException extends SpotifyClientException {
        public TokenFetchingException() {
            super("The value of token provided by Spotify API is null");
        }
    }
}
