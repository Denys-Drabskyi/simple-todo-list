package com.example.demo.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String MESSAGE = "message";
    private static final String PATH = "path";

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(Exception exception, WebRequest request) {
        Map<String, Object> errorResponseBody = buildErrorResponseMap(request, HttpStatus.NOT_FOUND, Collections.singletonList(exception.getMessage()));
        return new ResponseEntity<>(errorResponseBody, HttpStatus.NOT_FOUND);
    }

    private Map<String, Object> buildErrorResponseMap(WebRequest request, HttpStatusCode status, List<String> errorMessages) {
        Map<String, Object> errorResponseMap = new LinkedHashMap<>();
        errorResponseMap.put(TIMESTAMP, LocalDateTime.now());
        errorResponseMap.put(STATUS, status.value());
        errorResponseMap.put(ERROR, status);
        errorResponseMap.put(MESSAGE, errorMessages);
        errorResponseMap.put(PATH, request.getDescription(false));
        return errorResponseMap;
    }
}
