package com.gbill.createfinalconsumerbill.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import shareddtos.billmodule.exception.ErrorResponse;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidTokenException(
        InvalidTokenException ex,
        HttpServletRequest request
    ){
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestemp(LocalDateTime.now())
            .status(HttpStatus.UNAUTHORIZED.value())
            .error("Invalid token")
            .message(ex.getMessage())
            .path(request.getRequestURI())
            .details(List.of(ex.getMessage()))
            .build();

        log.error("Invalid token", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(ConnectionFaildAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handlerConnectionFaildAuthEntityExeption(
        ConnectionFaildAuthenticationException ex,
        HttpServletRequest request
    ){
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestemp(LocalDateTime.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error("Connection faild ")
            .message(ex.getMessage())
            .path(request.getRequestURI())
            .details(List.of(ex.getMessage()))
            .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidUserException(
        InvalidUserException ex,
        HttpServletRequest request
    ){
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestemp(LocalDateTime.now())
            .status(HttpStatus.UNAUTHORIZED.value())
            .error("Unauthorized user")
            .message(ex.getMessage())
            .path(request.getRequestURI())
            .details(List.of(ex.getMessage()))
            .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

}
