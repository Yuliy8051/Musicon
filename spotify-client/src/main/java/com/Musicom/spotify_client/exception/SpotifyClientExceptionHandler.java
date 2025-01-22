package com.Musicom.spotify_client.exception;

import com.Musicom.spotify_client.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class SpotifyClientExceptionHandler {
    @ExceptionHandler(SpotifyClientException.class)
    public ResponseEntity<ErrorResponseDto> handleEncryptionException(SpotifyClientException ex) {
        log.error(ex.getMessage(), ex);
        return createErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponseDto> createErrorResponse(String message, HttpStatus status) {
        ErrorResponseDto response = new ErrorResponseDto(
                message,
                status.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, status);
    }
}
