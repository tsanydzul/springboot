package com.accenture.test.springboot.service;

import com.accenture.test.springboot.entity.User;
import com.accenture.test.springboot.repo.UserRepo;
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
                        }
                    }else{
                        if(value != null && widgetOrderValidation(value)) {
                            it.set_value(value);
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

    public Boolean widgetOrderValidation(String input) {
        for (int i = 1; i <= 5; i++) {
            String s = String.valueOf(input.charAt(i - 1));
            if(i%2 == 1){
                int value = Integer.parseInt(s);
                if(!(value > 0 && value <6)) {
                    return false;
                }
            }else if(!s.equals(",")){
                return false;
            }
        }
        return true;
    }

}
