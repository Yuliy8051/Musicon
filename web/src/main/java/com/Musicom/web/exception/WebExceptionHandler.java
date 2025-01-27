package com.Musicom.web.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public String handleApiException(ApiException ex, Model model) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(ex.getMessage());

        String message = jsonNode.get("message").asText();
        int status = jsonNode.get("status").asInt();

        String timestamp = parseTimestamp(LocalDateTime.parse(jsonNode.get("timestamp").asText()));

        model.addAttribute("message", message);
        model.addAttribute("status", status);
        model.addAttribute("timestamp", timestamp);
        return "error-page";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, Model model) {
        List<String> errors = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        ErrorResponseDto response = new ErrorResponseDto(errors, 400, parseTimestamp(LocalDateTime.now()));
        model.addAttribute("response", response);
        return "hibernate-error-page";
    }

    private String parseTimestamp(LocalDateTime timestampSample) {
        LocalDate timestampDate = timestampSample.toLocalDate();
        LocalTime timestampTime = timestampSample.toLocalTime();
        return timestampDate + " " +
                timestampTime.getHour() + ":" + timestampTime.getMinute() + ":" + timestampTime.getSecond();
    }
}
