package com.forte.challenge.exception;

import com.forte.challenge.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .errors(null)
                .message(ex.getMessage())
                .success(Boolean.FALSE)
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGlobalException(Exception ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .errors(null)
                .message("Ocurrió un error en el servidor")
                .success(Boolean.FALSE)
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
