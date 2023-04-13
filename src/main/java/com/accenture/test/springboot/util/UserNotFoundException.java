package com.accenture.test.springboot.util;

public class UserNotFoundException extends Exception{

    public int code;
    public String message;
    public String status;
    public UserNotFoundException(){
        super();
    }
    public UserNotFoundException(String message, int code, String status){
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
