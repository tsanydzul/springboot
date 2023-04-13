package com.accenture.test.springboot.service;

import com.accenture.test.springboot.entity.User;

public interface UserService {
    public Iterable<User> getAllUser();
    public Iterable<User> getAllUser(int max_record, int offset);
    public User insert(User user);
    public User getById(Long id) throws Exception;
    public User updateUser(User user, Long id) throws Exception;

    public void deleteUser(Long id) throws Exception;

    public User refreshUser(Long id) throws Exception;
}
