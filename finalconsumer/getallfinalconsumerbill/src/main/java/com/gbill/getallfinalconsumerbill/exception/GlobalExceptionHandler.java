package com.gbill.getallfinalconsumerbill.exception;

import java.time.LocalDateTime;

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
    
    //factura no encontrada
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerNotFoundException(
        NotFoundException ex,
        HttpServletRequest request
    ){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestemp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Bill not found")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        log.error("Bill not found {}: ", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
