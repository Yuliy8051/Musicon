package com.Musicom.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(NotFoundException ex) {
        log.info("NotFoundException was thrown. Exception message: '{}'", ex.getMessage());
        return createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
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
