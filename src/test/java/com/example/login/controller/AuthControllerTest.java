package com.example.login.controller;

import com.example.login.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    private MockMvc mvc;

    @Mock
    private UserService userService;
    @Mock
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(new AuthController(userService, authenticationManager)).addFilter(((request, response, chain) -> {
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        })).build();
    }

    @Test
    void testLogin() throws Exception {
        RequestBuilder requestBuilder;
        // 未登录时，/auth 接口返回未登录状态
        mvc.perform(get("/auth")).andExpect(status().isOk()).andExpect(result -> {
            // System.out.println(result.getResponse().getContentAsString());
            Assertions.assertTrue(result.getResponse().getContentAsString().contains("用户没有登录"));
        });

        // 使用 /auth/login 登录
        Map<String, String> usernamePassword = new HashMap<>();
        usernamePassword.put("username", "myUser");
        usernamePassword.put("password", "myPassword");

        Mockito.when(userService.loadUserByUsername("myUser")).thenReturn(new User("myUser", bCryptPasswordEncoder.encode("myPassword"), Collections.emptyList()));
        Mockito.when(userService.getUserByUsername("myUser")).thenReturn(new com.example.login.entity.User(1, "myUser", bCryptPasswordEncoder.encode("myPassword")));

        requestBuilder = post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(usernamePassword));
        MvcResult result = mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        HttpSession session = result.getRequest().getSession();

        // 登录后，/auth 接口返回登录状态
        assert session != null;
        mvc.perform(get("/auth").session((MockHttpSession) session)).andExpect(status().isOk()).andExpect(result1 -> {
            System.out.println(result1.getResponse().getContentAsString());
            Assertions.assertTrue(result1.getResponse().getContentAsString().contains("myUser"));
        });
    }
}
