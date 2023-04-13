package com.accenture.test.springboot.service;

import com.accenture.test.springboot.entity.User;
import com.accenture.test.springboot.repo.UserRepo;
import com.accenture.test.springboot.util.Constant;
import com.accenture.test.springboot.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserSettingServiceImpl implements UserSettingService{

    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;

    @Override
    public User updateUserSettings(Long id, List<Map<String, String>> userSettings) throws Exception {
        try{
            User user = userService.getById(id);
            user.getUserSetting().forEach(it -> {
                userSettings.forEach(map -> {
                    String value = map.get(it.get_key());
                    if(value != null && !it.get_key().equals(Helper.Env.WIDGET_ORDER.getDesc())){
                        if((value.equals("false") || value.equals("true"))){
                            it.set_value(value);
                        }else{
//                           new Exception("error");
                        }
                    }else{
                        if(value != null && Helper.WidgetOrderValidation(value)){
                            it.set_value(value);
                        }else{
//                           new Exception("error");
                        }
                    }
                });
            });
            userRepo.save(user);
            return user;
        }catch (Exception e){
            throw  new Exception(e.getMessage());
        }
    }


}
