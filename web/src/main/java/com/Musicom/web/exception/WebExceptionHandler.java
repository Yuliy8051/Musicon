package com.Musicom.web.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public String handleApiException(ApiException ex, Model model) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(ex.getMessage());

        String message = jsonNode.get("message").asText();
        int status = jsonNode.get("status").asInt();
        LocalDateTime timestampSample = LocalDateTime.parse(jsonNode.get("timestamp").asText());

        LocalDate timestampDate = timestampSample.toLocalDate();
        LocalTime timestampTime = timestampSample.toLocalTime();
        String timestamp = timestampDate + " " +
                timestampTime.getHour() + ":" + timestampTime.getMinute() + ":" + timestampTime.getSecond();

        model.addAttribute("message", message);
        model.addAttribute("status", status);
        model.addAttribute("timestamp", timestamp);
        return "error-page";
    }

}
