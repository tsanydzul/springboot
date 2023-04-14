package com.accenture.test.springboot.util;

import com.accenture.test.springboot.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> listUser(List<User> user_data, int max_records, int offset, HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_data", user_data);
        map.put("max_records", max_records);
        map.put("offset", offset);

        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> singleUser(User user_data, HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_data", user_data);
        map.put("user_settings", user_data.fetchUserSettings());

        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> deleteSuccess(HttpStatus status) {
        return new ResponseEntity<>(status);
    }

}