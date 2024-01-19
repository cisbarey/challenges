package com.forte.challenge.exception;

import com.forte.challenge.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<ApiResponse<Object>> handleReservationException(ReservationException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .errors(null)
                .message(ex.getMessage())
                .success(Boolean.FALSE)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
