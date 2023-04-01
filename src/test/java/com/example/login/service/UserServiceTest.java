package com.example.login.service;

import com.example.login.entity.User;
import com.example.login.dao.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    BCryptPasswordEncoder mockitoEncoder;
    @Mock
    UserMapper mockitoUserMapper;
    @InjectMocks
    UserService userService;

    @Test
    public void save() {
        when(mockitoEncoder.encode("myPassword")).thenReturn("myEncodedPassword");

        userService.save("myUser", "myPassword");

        Mockito.verify(mockitoUserMapper).save("myUser", "myEncodedPassword");
    }

    @Test
    void getUserByUsername() {
        userService.getUserByUsername("myUser");

        Mockito.verify(mockitoUserMapper).findUserByUsername("myUser");
    }

    @Test
    public void throwExceptionWhenUserNotExists() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("myUser"));
    }

    @Test
    void returnUserWhenUserExists() {
        when(mockitoUserMapper.findUserByUsername("myUser"))
                .thenReturn(new User(123, "myUser", "myEncodedPassword"));

        UserDetails userDetails = userService.loadUserByUsername("myUser");

        Assertions.assertEquals("myUser", userDetails.getUsername());
        Assertions.assertEquals("myEncodedPassword", userDetails.getPassword());
    }
}
