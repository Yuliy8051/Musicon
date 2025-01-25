package com.Musicom.api.exception;

import java.time.LocalDateTime;

public record ErrorResponseDto(String message,
                               int status,
                               LocalDateTime timestamp) {
}
