package org.example.controller;

import org.example.exception.UserCreationRetryException;
import org.example.exception.UserDeletionRetryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR = "error";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        var errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getDefaultMessage() != null ? err.getDefaultMessage() : "Invalid input")
                .toList();

        Map<String, Object> body = Map.of(
                TIMESTAMP, ZonedDateTime.now(),
                STATUS, HttpStatus.BAD_REQUEST.value(),
                ERROR, errorMessages
        );

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(UserCreationRetryException.class)
    public ResponseEntity<Object> handleUserCreationRetry(UserCreationRetryException ex) {
        return buildErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, "Failed to create user. Please try again later.");
    }

    @ExceptionHandler(UserDeletionRetryException.class)
    public ResponseEntity<Object> handleUserDeletionRetry(UserDeletionRetryException ex) {
        return buildErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, "Failed to delete user. Please try again later.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String errorMessage) {
        Map<String, Object> body = Map.of(
                TIMESTAMP, ZonedDateTime.now(),
                STATUS, status.value(),
                ERROR, errorMessage
        );
        return ResponseEntity.status(status).body(body);
    }
}
