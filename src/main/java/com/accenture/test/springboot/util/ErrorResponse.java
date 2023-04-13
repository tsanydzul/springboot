package com.accenture.test.springboot.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import java.util.Date;

@Getter
@Setter
public class ErrorResponse{
    private int code;

    private String status;

    private String message;

    public ErrorResponse(String httpStatus, String message, int code) {
        this.code = code;
        this.status = httpStatus;
        this.message = message;
    }
}