package com.accenture.test.springboot.util;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class CustomControllerAdvice {

    @ExceptionHandler(NullPointerException.class) // exception handled
    public ResponseEntity<ErrorResponse> handleNullPointerExceptions(Exception e) {
        HttpStatus status = HttpStatus.NOT_FOUND; // 404

        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage(), status.value()), status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptions(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage(), status.value()), status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> dataNotFoundException(Exception e){
        HttpStatus status = HttpStatus.NOT_FOUND; // 500
        return new ResponseEntity<>(new ErrorResponse(status,Constant.MESSAGE_NO_DATA_WITH_ID, 3000), status);
    }
}
