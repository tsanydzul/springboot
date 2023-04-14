package com.accenture.test.springboot.util;

import java.util.List;

public class UserErrorException extends Exception{

    public int code;
    public String message;
    public String status;

    public List<String> errorList;
    public UserErrorException(){
        super();
    }
    public UserErrorException(String message, int code, String status){
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public UserErrorException(List<String> errorList, int code, String status){
        this.code = code;
        this.errorList = errorList;
        this.status = status;
    }

}
