package com.accenture.test.springboot.service;

import com.accenture.test.springboot.entity.User;
import com.accenture.test.springboot.entity.UserSetting;
import com.accenture.test.springboot.repo.UserRepo;
import com.accenture.test.springboot.repo.UserSettingRepo;
import com.accenture.test.springboot.util.Constant;
import com.accenture.test.springboot.util.ErrorResponse;
import com.accenture.test.springboot.util.Helper;
import com.accenture.test.springboot.util.UserNotFoundException;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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
    public List<User> getAllUser(int max_records, int offset) throws Exception {
        return userRepo.findAll(max_records,offset);
    }

    @Override
    public User insert(User user) throws UserNotFoundException {
        field_validation(user);
        user.setCreated_time(Instant.now());
        user.setUpdated_time(Instant.now());
        userRepo.save(user);

        user.setUserSetting(defaultUserSetting(user));
        return user;
    }

    @Override
    public User getById(Long id) throws Exception {
        User user = userRepo.findByIdAndActive(id);
        return user;
    }

    @Override
    public User updateUser(User user,Long id) throws Exception {
        field_validation(user);
        User existingUser = getById(id);
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

    public boolean ssnExist(String ssn){
        Integer userCount = userRepo.findUserWithSSN(ssn);
        return userCount > 0;
    }

    public void field_validation(User user) throws UserNotFoundException {
        String ssn = user.getSsn();
        if(ssn == null || !ssn.matches("-?\\d+")){
            throw new UserNotFoundException(Constant.MESSAGE_INVALID_FIELD_OR_VALUE + "SSN", Constant.CODE_NON_UNIQUE,HttpStatus.CONFLICT.name());
        }
        if(ssn.length() < 16){
            for (int i = ssn.length(); i < 16; i++) {
                ssn = "0"+ ssn;
            }
            user.setSsn(ssn);
        }
        if(ssn != null && ssn.matches("-?\\d+") && ssnExist(ssn)){
            throw new UserNotFoundException(Constant.MESSAGE_NON_UNIQUE_SSN, Constant.CODE_NON_UNIQUE,HttpStatus.CONFLICT.name());
        }

        if(user.getFirst_name() == null){
            throw new UserNotFoundException(Constant.MESSAGE_INVALID_FIELD_OR_VALUE + "first_name", Constant.CODE_NON_UNIQUE,HttpStatus.CONFLICT.name());
        }

        if(user.getFamily_name() == null){
            throw new UserNotFoundException(Constant.MESSAGE_INVALID_FIELD_OR_VALUE + "family_name", Constant.CODE_NON_UNIQUE,HttpStatus.CONFLICT.name());
        }

        if(user.getBirth_date() == null){
            throw new UserNotFoundException(Constant.MESSAGE_INVALID_FIELD_OR_VALUE + "birth_date", Constant.CODE_NON_UNIQUE,HttpStatus.CONFLICT.name());
        }
    }
}
