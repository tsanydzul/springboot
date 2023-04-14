package com.accenture.test.springboot;

import com.accenture.test.springboot.entity.User;
import com.accenture.test.springboot.repo.UserRepo;
import com.accenture.test.springboot.repo.UserSettingRepo;
import com.accenture.test.springboot.service.UserServiceImpl;
import com.accenture.test.springboot.util.UserErrorException;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserSettingRepo userSettingRepo;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setup() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse("1996-02-22");

        user = new User();
        user.setSsn("0000000000002945");
        user.setFirst_name("Jon");
        user.setFamily_name("Dou");
        user.setBirth_date(date);
    }

    @Test
    public void givenUserObject_whenSave_thenReturnUserObject() throws UserErrorException {
        given(userRepo.save(user)).willReturn(user);

        User savedUser = userService.insert(user);
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getCreated_time()).isNotNull();
        Assertions.assertThat(savedUser.getUpdated_time()).isNotNull();
        Assertions.assertThat(savedUser.getIs_active()).isTrue();
    }

    @Test
    public void givenUserObject_thenInsert_whenFirstNameEmpty_thenReturnException() throws UserErrorException {
        user.setFirst_name(null);
        UserErrorException exception = assertThrows(UserErrorException.class, () -> {
            userService.insert(user);
        });

        String expectedMessage = "Invalid Value for first_name";
        String actualMessage = exception.message;

        Integer expectedCode = 30001;
        Integer actualCode = exception.code;

        assertTrue(actualMessage.contains(expectedMessage));
        assertTrue(expectedCode.equals(actualCode));
    }

    @Test
    public void givenUserObject_thenInsert_whenFamilyNameEmpty_thenReturnException() throws UserErrorException {
        user.setFirst_name("Jon");
        user.setFamily_name(null);
        UserErrorException exception = assertThrows(UserErrorException.class, () -> {
            userService.insert(user);
        });

        String expectedMessage = "Invalid Value for family_name";
        String actualMessage = exception.message;

        Integer expectedCode = 30001;
        Integer actualCode = exception.code;

        assertTrue(actualMessage.contains(expectedMessage));
        assertTrue(expectedCode.equals(actualCode));
    }

    @Test
    public void givenUserObject_thenInsert_whenSSNNotValid_thenReturnException() throws UserErrorException {
        user.setFamily_name("Doe");
        user.setSsn("abc");
        UserErrorException exception = assertThrows(UserErrorException.class, () -> {
            userService.insert(user);
        });

        String expectedMessage = "Invalid Value for SSN";
        String actualMessage = exception.message;

        Integer expectedCode = 30001;
        Integer actualCode = exception.code;

        assertTrue(actualMessage.contains(expectedMessage));
        assertTrue(expectedCode.equals(actualCode));
    }

    @Test
    public void givenUserObject_thenInsert_whenSSNNot16Digits_thenShouldPass() throws UserErrorException {
        given(userRepo.save(user)).willReturn(user);

        user.setSsn("1234");
        User savedUser = userService.insert(user);
        Assertions.assertThat(savedUser.getSsn()).isEqualTo("0000000000001234");
    }

    @Test
    public void givenSavedUser_whenGetByID_thenReturnUserObject() throws Exception {
        given(userRepo.findByIdAndActive(1L)).willReturn(user);

        User savedUser = userService.getById(1L);
        Assertions.assertThat(savedUser.getSsn()).isEqualTo("0000000000002945");
    }

    @Test
    public void givenSavedUserInactive_whenGetByID_thenReturnEmpty() throws Exception {
        given(userRepo.findByIdAndActive(100L)).willReturn(null);

        User savedUser = userService.getById(100L);
        Assertions.assertThat(savedUser).isEqualTo(null);
    }

    @Test
    public void givenUpdateUser_userActive_thenShouldPass() throws Exception {
        user.setId(1L);
        given(userRepo.findByIdAndActive(1L)).willReturn(user);
        given(userRepo.save(user)).willReturn(user);

        User userUpdated = userService.updateUser(user,1l);

        Assertions.assertThat(userUpdated).isEqualTo(user);
    }

    @Test
    public void givenFindUserObject_userInActive_thenFail() throws Exception {
        //no data with id 100 in the db
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.updateUser(user, 100L);
        });
    }


}
