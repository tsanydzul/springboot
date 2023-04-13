package com.accenture.test.springboot.controller;

import com.accenture.test.springboot.entity.User;
import com.accenture.test.springboot.entity.UserSetting;
import com.accenture.test.springboot.service.UserService;
import com.accenture.test.springboot.service.UserSettingService;
import com.accenture.test.springboot.util.ErrorResponse;
import com.accenture.test.springboot.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserSettingService userSettingService;

    @GetMapping
    public ResponseEntity<Object> getAllUsers(@RequestParam(defaultValue = "5") int max_records, @RequestParam(defaultValue = "0") int offset) throws Exception {
        return ResponseHandler.listUser((List<User>) userService.getAllUser(max_records,offset), max_records, offset, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Long id) throws Exception {
        return ResponseHandler.singleUser(userService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createNewUser(@RequestBody User user) throws Exception {
        return ResponseHandler.singleUser(userService.insert(user), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") Long id, @RequestBody User user) throws Exception {
        return ResponseHandler.singleUser(userService.updateUser(user,id),HttpStatus.OK);
    }

    @PutMapping("/{id}/settings")
    public ResponseEntity<Object> updateUserSettings(@PathVariable("id") Long id, @RequestBody List<Map<String,String>> data) throws Exception {
        return ResponseHandler.singleUser(userSettingService.updateUserSettings(id, data), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) throws Exception {
        userService.deleteUser(id);
        return ResponseHandler.deleteSuccess(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}/refresh")
    public ResponseEntity<Object> refreshUser(@PathVariable("id") Long id) throws Exception {
        return ResponseHandler.singleUser(userService.refreshUser(id),HttpStatus.OK);
    }
}
