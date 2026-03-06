package com.example.supermarket.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UnknownProductException.class)
    public ProblemDetail handleUnknownProduct(UnknownProductException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setTitle("Invalid product");
        detail.setDetail(exception.getMessage());
        return detail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setTitle("Validation failed");
        detail.setDetail("Request body contains invalid values");

        List<Map<String, Object>> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(this::toErrorItem)
                .toList();
        detail.setProperty("errors", errors);
        return detail;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setTitle("Validation failed");
        detail.setDetail("Request contains invalid values");
        List<Map<String, Object>> errors = exception.getConstraintViolations().stream()
                .map(cv -> Map.<String, Object>of(
                        "field", cv.getPropertyPath().toString(),
                        "message", cv.getMessage(),
                        "rejectedValue", cv.getInvalidValue()))
                .toList();
        detail.setProperty("errors", errors);
        return detail;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleInvalidJson(HttpMessageNotReadableException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setTitle("Invalid JSON");
        detail.setDetail("Request body could not be parsed");
        Throwable cause = exception.getMostSpecificCause();
        if (cause != null && cause.getMessage() != null) {
            detail.setProperty("cause", cause.getMessage());
        }
        return detail;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ProblemDetail handleResponseStatus(ResponseStatusException exception) {
        HttpStatus status = HttpStatus.valueOf(exception.getStatusCode().value());
        ProblemDetail detail = ProblemDetail.forStatus(status);
        detail.setTitle(status.getReasonPhrase());
        detail.setDetail(exception.getReason() == null ? status.getReasonPhrase() : exception.getReason());
        return detail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException exception) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setTitle("Validation failed");
        detail.setDetail(exception.getMessage());
        return detail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpected(Exception exception) {
        LOGGER.error("Unhandled exception", exception);
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        detail.setTitle("Unexpected error");
        detail.setDetail("An unexpected error occurred");
        return detail;
    }

    private Map<String, Object> toErrorItem(FieldError fieldError) {
        return Map.<String, Object>of(
                "field", fieldError.getField(),
                "message", fieldError.getDefaultMessage() == null ? "Invalid value" : fieldError.getDefaultMessage(),
                "rejectedValue", fieldError.getRejectedValue());
    }
}
