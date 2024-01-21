package com.forte.challenge.exception;

import com.forte.challenge.dto.response.ApiForteResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiForteResponse> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        ApiForteResponse response = ApiForteResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiForteResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = "El recurso no puede ser creado debido a una violación de restricciones de integridad.";

        if(ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException){
            org.hibernate.exception.ConstraintViolationException cve = (org.hibernate.exception.ConstraintViolationException) ex.getCause();

            if(cve.getConstraintName() != null && cve.getConstraintName().contains("email")) {
                message = "El correo electrónico ya está en uso.";
            }
        }
        ApiForteResponse response = ApiForteResponse.builder()
                .message(message)
                .status(HttpStatus.CONFLICT.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiForteResponse> handleGlobalException(Exception ex) {
        ApiForteResponse response = ApiForteResponse.builder()
                .message("Ocurrió un error en el servidor")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
