package com.example.login.service;

import com.example.login.entity.User;
import com.example.login.dao.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    private final BCryptPasswordEncoder passwordEncoder;
    // private final Map<String, User> users = new ConcurrentHashMap<>();
    private final UserMapper userMapper;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserMapper userMapper) {
        this.passwordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
        // save("admin", "123456");
    }

    public User getUserById(Integer userId) {
        return this.userMapper.getUserById(userId);
    }

    public void save(String username, String password) {
        userMapper.save(username, passwordEncoder.encode(password));
        // users.put(username, new User(1, username, passwordEncoder.encode(password)));
    }

    public String getPassword(String username) {
        return getUserByUsername(username).getEncryptedPassword();
    }

    public User getUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    public User geUserById(Integer id) {
        return userMapper.findUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " 不存在！");
        }
        String password = user.getEncryptedPassword();
        return new org.springframework.security.core.userdetails.User(username, password, Collections.emptyList());
    }
}
