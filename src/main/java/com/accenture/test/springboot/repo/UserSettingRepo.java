package com.accenture.test.springboot.repo;

import com.accenture.test.springboot.entity.UserSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSettingRepo extends JpaRepository<UserSetting, Long> {
}
