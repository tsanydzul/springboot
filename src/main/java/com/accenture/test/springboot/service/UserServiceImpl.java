package com.accenture.test.springboot.service;

import com.accenture.test.springboot.entity.User;
import com.accenture.test.springboot.entity.UserSetting;
import com.accenture.test.springboot.repo.UserRepo;
import com.accenture.test.springboot.repo.UserSettingRepo;
import com.accenture.test.springboot.util.Constant;
import com.accenture.test.springboot.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserSettingRepo userSettingRepo;

    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @Override
    public List<User> getAllUser(int max_records, int offset) {
        return userRepo.findAll(max_records,offset);
    }

    @Override
    public User insert(User user) {
        user.setCreated_time(Instant.now());
        user.setUpdated_time(Instant.now());
        userRepo.save(user);

        user.setUserSetting(defaultUserSetting(user));
        return user;
    }

    @Override
    public User getById(Long id) throws Exception {
        return userRepo.getReferenceById(id);
    }

    @Override
    public User updateUser(User user,Long id) throws Exception {
        User existingUser = getById(id);
        if(user.getFirst_name() != null) existingUser.setFirst_name(user.getFirst_name());
        if(user.getMiddle_name() != null) existingUser.setMiddle_name(user.getMiddle_name());
        if(user.getFamily_name() != null) existingUser.setFamily_name(user.getFamily_name());
        if(user.getBirth_date() != null) existingUser.setBirth_date(user.getBirth_date());
        existingUser.setUpdated_time(Instant.now());
        userRepo.save(existingUser);
        return existingUser;
    }

    @Override
    public void deleteUser(Long id) throws Exception {
        User user = getById(id);
        user.setIs_active(false);
        user.setDelete_time(Instant.now());

        userRepo.save(user);
    }

    @Override
    public User refreshUser(Long id) throws Exception {
        User user = getById(id);
        user.setIs_active(true);
        user.setDelete_time(null);

        userRepo.save(user);
        return user;
    }

    public Set<UserSetting> defaultUserSetting(User user){
        Set<UserSetting> dataUserSet = new HashSet<>();
        for(Helper.Env env : Helper.Env.values()) {
            UserSetting userSetting = new UserSetting();
            userSetting.setUser(user);
            if (env == Helper.Env.WIDGET_ORDER) {
                userSetting.set_key(env.getDesc());
                userSetting.set_value(Constant.DEFAULT_VALUE_WIDGET_ORDER);
            } else {
                userSetting.set_key(env.getDesc());
                userSetting.set_value(Constant.DEFAULT_VALUE_USER_SETTING_LOV);
            }
            dataUserSet.add(userSetting);
            userSettingRepo.save(userSetting);
        }
        return  dataUserSet;
    }
}
