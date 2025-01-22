package com.Musicom.spotify_client.dto;

import java.time.LocalDateTime;

public record ErrorResponseDto(String message,
                               int status,
                               LocalDateTime timestamp) {
}

