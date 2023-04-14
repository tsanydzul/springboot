package com.accenture.test.springboot.util;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class ErrorHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerExceptions(Exception e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(new ErrorResponse(status.name(), e.getMessage(), status.value()), status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptions(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(new ErrorResponse(status.name(), Constant.MESSAGE_SYSTEM_ERROR, status.value()), status);
    }

    @ExceptionHandler(UserErrorException.class)
    public ResponseEntity<ErrorResponse> userErrorException(UserErrorException e){
        return new ResponseEntity<>(new ErrorResponse(e.status, e.message, e.code), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFoundException(EntityNotFoundException e){
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(new ErrorResponse(status.name(), Constant.MESSAGE_NO_DATA_WITH_ID, Constant.CODE_DATA_NOT_FOUND), status);
    }
}
