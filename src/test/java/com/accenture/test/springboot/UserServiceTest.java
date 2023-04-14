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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

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
    public void givenUserObject_thenInsert_whenFirstNameEmpty_thenReturnException(){
        user.setFirst_name(null);
        UserErrorException exception = assertThrows(UserErrorException.class, () -> {
            userService.insert(user);
        });

        String expectedMessage = "Invalid Value for first_name";
        String actualMessage = exception.message;

        Integer expectedCode = 30001;
        Integer actualCode = exception.code;

        assertTrue(actualMessage.contains(expectedMessage));
        assertEquals(expectedCode, actualCode);
    }

    @Test
    public void givenUserObject_thenInsert_whenFamilyNameEmpty_thenReturnException(){
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
        assertEquals(expectedCode, actualCode);
    }

    @Test
    public void givenUserObject_thenInsert_whenSSNNotValid_thenReturnException() {
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
        assertEquals(expectedCode, actualCode);
    }

    @Test
    public void givenUserObject_thenInsert_whenSSNNot16Digits_thenShouldPass() throws UserErrorException {
        given(userRepo.save(user)).willReturn(user);

        user.setSsn("1234");
        User savedUser = userService.insert(user);
        Assertions.assertThat(savedUser.getSsn()).isEqualTo("0000000000001234");
    }

    @Test
    public void givenSavedUser_whenGetByID_thenReturnUserObject(){
        given(userRepo.findByIdAndActive(1L)).willReturn(user);

        User savedUser = userService.getById(1L);
        Assertions.assertThat(savedUser.getSsn()).isEqualTo("0000000000002945");
    }

    @Test
    public void givenSavedUserInactive_whenGetByID_thenReturnEmpty(){
        given(userRepo.findByIdAndActive(100L)).willReturn(null);

        User savedUser = userService.getById(100L);
        Assertions.assertThat(savedUser).isEqualTo(null);
    }

    @Test
    public void givenUpdateUser_userActive_thenShouldPass() throws Exception {
        user.setId(1L);
        given(userRepo.findByIdAndActive(1L)).willReturn(user);
        given(userRepo.save(user)).willReturn(user);

        User userUpdated = userService.updateUser(user, 1L);

        Assertions.assertThat(userUpdated).isEqualTo(user);
    }

    @Test
    public void givenUpdateObject_userInActive_thenFail(){
        //no data with id 100 in the db
        when(userRepo.findByIdAndActive(100L)).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> {
            userService.updateUser(user, 100L);
        });
    }

    @Test
    public void givenActiveUser_whenTryToDelete_ThenPass(){
        //query inactive user will lead to return null
        when(userRepo.findByIdAndActive(100L)).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> {
            userService.deleteUser(100L);
        });
    }

    @Test
    public void givenDeletedUser_whenTryToRefresh_ThenPass(){
        user.setIs_active(false);
        when(userRepo.findByIdAndActive(1L)).thenReturn(user);
        when(userRepo.save(user)).thenReturn(user);

        User refreshedUser = userService.refreshUser(1L);

        Assertions.assertThat(refreshedUser.getIs_active()).isEqualTo(true);
        Assertions.assertThat(refreshedUser.getDelete_time()).isNull();
    }

}
