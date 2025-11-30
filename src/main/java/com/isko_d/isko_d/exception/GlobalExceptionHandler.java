package com.isko_d.isko_d.exception;

import com.isko_d.isko_d.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, ?>> handleNotFound(NotFoundException exception, HttpServletRequest request) {
        return new NotFoundResponse(exception.getMessage(), request).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, ?>> handleGenericException(Exception exception, HttpServletRequest request) {
        return new ServerErrorResponse(exception.getMessage(), request).build();
    }
}
