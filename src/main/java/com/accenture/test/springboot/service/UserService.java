package com.accenture.test.springboot.service;

import com.accenture.test.springboot.entity.User;
import com.accenture.test.springboot.util.UserErrorException;

public interface UserService {
    Iterable<User> getAllUser(int max_record, int offset) throws Exception;
    User insert(User user) throws UserErrorException;
    User getById(Long id) throws Exception;
    User updateUser(User user, Long id) throws Exception;
    void deleteUser(Long id) throws Exception;
    User refreshUser(Long id) throws Exception;
}
