package com.example.login.controller;

import com.example.login.entity.Result;
import com.example.login.entity.User;
import com.example.login.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Map;

@Controller
public class AuthController {
    private UserService userService;
    private AuthenticationManager authenticationManager;

    @Inject
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/auth")
    @ResponseBody
    public Object auth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loginUser = userService.getUserByUsername(authentication == null ? null : authentication.getName());
        if (loginUser == null) {
            return Result.success("用户没有登录", false, null);
        } else {
            return Result.success("获取用户信息成功", true, loginUser);
        }
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public Result register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if ("".equals(username) || "".equals(password)) {
            return Result.failure("用户名和密码不能为空");
        }
        if (username.length() < 1 || username.length() > 15) {
            return Result.failure("用户名不符合要求");
        }
        if (password.length() < 6 || password.length() > 16) {
            return Result.failure("密码不符合要求");
        }
        try {
            userService.save(username, password);
        } catch (DuplicateKeyException e) {
            return Result.failure("用户已存在");
        }
        return Result.success("注册成功", false);
    }

    @GetMapping("/auth/logout")
    @ResponseBody
    public Result logout() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User loginUser = userService.getUserByUsername(username);
        if (loginUser == null) {
            return Result.failure("用户没有登录");
        } else {
            SecurityContextHolder.clearContext();
            return Result.success("注销成功", false);
        }
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, Object> usernameAndPassword) {
        String username = (String) usernameAndPassword.get("username");
        String password = (String) usernameAndPassword.get("password");
        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return Result.failure("用户不存在");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password);
        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            User loginUser = userService.getUserByUsername(username);
            return Result.success("登录成功", true, loginUser);
        } catch (BadCredentialsException e) {
            return Result.failure("密码不正确");
        }
    }


}
