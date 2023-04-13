package com.accenture.test.springboot.service;

import com.accenture.test.springboot.entity.User;
import com.accenture.test.springboot.entity.UserSetting;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserSettingService {
    public User updateUserSettings(Long id, List<Map<String,String>> userSettings) throws Exception;
}
