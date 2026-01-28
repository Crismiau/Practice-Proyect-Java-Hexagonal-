package com.example.MedicCenter.infrastructure.adapters.in.rest.exceptions;

import com.example.MedicCenter.domain.exception.BusinessRuleException;
import com.example.MedicCenter.domain.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.slf4j.MDC;

import java.net.URI;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        log.warn("Resource not found: {}", ex.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setType(URI.create("https://mediccenter.com/errors/not-found"));
        return addCustomProperties(problemDetail, request);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ProblemDetail handleBusinessRuleException(BusinessRuleException ex, WebRequest request) {
        log.warn("Business rule violation: {}", ex.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY,
                ex.getMessage());
        problemDetail.setTitle("Business Rule Violation");
        problemDetail.setType(URI.create("https://mediccenter.com/errors/business-rule"));
        return addCustomProperties(problemDetail, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        String detail = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.warn("Validation failed: {}", detail);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");
        problemDetail.setTitle("Constraint Violation");
        problemDetail.setType(URI.create("https://mediccenter.com/errors/validation"));
        problemDetail.setProperty("errors", detail);
        return addCustomProperties(problemDetail, request);
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleRuntimeException(RuntimeException ex) {
        if (ex.getMessage().contains("Invalid credentials") || ex.getMessage().contains("User not found")) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                    HttpStatus.UNAUTHORIZED,
                    ex.getMessage());
            problemDetail.setTitle("Authentication Failed");
            return problemDetail;
        }
        return handleGeneralException(ex);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later.");
        problemDetail.setType(URI.create("https://mediccenter.com/errors/internal"));
        problemDetail.setTitle("Internal Server Error");
        return problemDetail;
    }

    private ProblemDetail addCustomProperties(ProblemDetail problemDetail, WebRequest request) {
        String traceId = UUID.randomUUID().toString();
        MDC.put("traceId", traceId);

        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("traceId", traceId);
        problemDetail.setInstance(URI.create(request.getDescription(false).replace("uri=", "")));

        return problemDetail;
    }
}
