package com.accenture.test.springboot.service;

import com.accenture.test.springboot.entity.User;
import com.accenture.test.springboot.entity.UserSetting;
import com.accenture.test.springboot.repo.UserRepo;
import com.accenture.test.springboot.repo.UserSettingRepo;
import com.accenture.test.springboot.util.Constant;
import com.accenture.test.springboot.util.Helper;
import com.accenture.test.springboot.util.UserErrorException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<User> getAllUser(int max_records, int offset) {
        return userRepo.findAll(max_records,offset);
    }

    @Override
    public User insert(User user) throws UserErrorException {
        field_validation(user);
        user.setCreated_time(Instant.now());
        user.setUpdated_time(Instant.now());
        userRepo.save(user);

        user.setUserSetting(defaultUserSetting(user));
        return user;
    }

    @Override
    public User getById(Long id){
        User user = userRepo.findByIdAndActive(id);
        if(user == null){
            throw new EntityNotFoundException();
        }
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
    public void deleteUser(Long id){
        User user = getById(id);
        user.setIs_active(false);
        user.setDelete_time(Instant.now());

        userRepo.save(user);
    }

    @Override
    public User refreshUser(Long id){
        User user = userRepo.findByIdAndInActive(id);
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

    public void field_validation(User user) throws UserErrorException {
        StringBuilder ssn = new StringBuilder(user.getSsn());
        if(ssn == null || !ssn.toString().matches("-?\\d+")){
            throw new UserErrorException(Constant.MESSAGE_INVALID_FIELD_OR_VALUE + "SSN", Constant.CODE_INVALID_FIELD_OR_VALUE,HttpStatus.CONFLICT.name());
        }
        if(ssn.length() < 16){
            for (int i = ssn.length(); i < 16; i++) {
                ssn.insert(0, "0");
            }
            user.setSsn(ssn.toString());
        }
        if(ssn != null && ssn.toString().matches("-?\\d+") && ssnExist(ssn.toString())){
            throw new UserErrorException(Constant.MESSAGE_NON_UNIQUE_SSN, Constant.CODE_INVALID_FIELD_OR_VALUE,HttpStatus.CONFLICT.name());
        }

        if(user.getFirst_name() == null){
            throw new UserErrorException(Constant.MESSAGE_INVALID_FIELD_OR_VALUE + "first_name", Constant.CODE_INVALID_FIELD_OR_VALUE,HttpStatus.CONFLICT.name());
        }

        if(user.getFamily_name() == null){
            throw new UserErrorException(Constant.MESSAGE_INVALID_FIELD_OR_VALUE + "family_name", Constant.CODE_INVALID_FIELD_OR_VALUE,HttpStatus.CONFLICT.name());
        }

        if(user.getBirth_date() == null){
            throw new UserErrorException(Constant.MESSAGE_INVALID_FIELD_OR_VALUE + "birth_date", Constant.CODE_INVALID_FIELD_OR_VALUE,HttpStatus.CONFLICT.name());
        }
    }
}
