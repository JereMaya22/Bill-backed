package com.gbill.createfinalconsumerbill.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gbill.createfinalconsumerbill.modeldto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHanlder {


    //handler exception when the json is invalid
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> MessageNotReadableExceptionHanlder(
        HttpMessageNotReadableException ex,
        HttpServletRequest request
    ){
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error(ex.getMessage())
            .message("Invalid JSON")
            .path(request.getRequestURI())
            .build();

        log.warn("Invalid JSON: {}", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    //Generic handler exception
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponse> handlerGenericException(
        GenericException ex,
        HttpServletRequest request
    ){
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error(ex.getMessage())
            .message("Internal Error")
            .path(request.getRequestURI())
            .build();

        log.warn("Internal Error: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
