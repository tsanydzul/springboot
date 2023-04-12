package com.accenture.test.springboot.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/users")
public class UserController {

    @GetMapping
    public String getAllUsers(){
        return "Hello World";
    }

    @PostMapping
    public String createNewUser(){
        return "Hello World Create";
    }
}
