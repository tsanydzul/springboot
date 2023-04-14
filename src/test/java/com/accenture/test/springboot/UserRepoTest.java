package com.accenture.test.springboot;

import com.accenture.test.springboot.entity.User;
import com.accenture.test.springboot.repo.UserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@DataJpaTest
public class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @Test
    public void saveUser() throws ParseException {
        User user = createUser();
        userRepo.save(user);

        Assertions.assertThat(user.getId()).isGreaterThan(0);
    }

    @Test
    public void getUser() throws ParseException {
        User user = createUser();
        userRepo.save(user);
        User userRetrieve = userRepo.findByIdAndActive(1L);
        Assertions.assertThat(userRetrieve.getId()).isEqualTo(1L);
    }

    @Test
    public void getUserInActive() throws ParseException {
        User user = createUser();
        user.setIs_active(false);
        userRepo.save(user);
        User userRetrieve = userRepo.findByIdAndActive(1L);
        Assertions.assertThat(userRetrieve).isEqualTo(null);
    }

    @Test
    public void getUserLimitOne() throws ParseException {
        User user = createUser();
        userRepo.save(user);
        List<User> userList = userRepo.findAll(1,0);
        Assertions.assertThat(userList.size()).isEqualTo(1);
    }

    @Test
    public void createDuplicateSSN() throws ParseException {
        User user1 = createUser();
        User user2 = createUser();

        userRepo.save(user1);
        userRepo.save(user2);
        List<User> userList = userRepo.findAll(1,0);
        Assertions.assertThat(userList.size()).isEqualTo(1);
    }

    public User createUser() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse("1996-02-22");

        User user = new User();
        user.setSsn("0000000000002945");
        user.setFirst_name("Jon");
        user.setFamily_name("Dou");
        user.setBirth_date(date);
        user.setIs_active(true);
        user.setCreated_time(Instant.now());
        user.setUpdated_time(Instant.now());

        return user;
    }
}
