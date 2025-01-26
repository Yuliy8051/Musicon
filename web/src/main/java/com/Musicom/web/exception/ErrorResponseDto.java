package com.Musicom.web.exception;

import java.util.List;

public record ErrorResponseDto(List<String> errors,
                               int status,
                               String timestamp) {
}
