package com.example.login.config;

import com.example.login.mapper.UserMapper;
import com.example.login.service.OrderService;
import com.example.login.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfig {
    // @Bean
    // public UserService userService() {
    //     return new UserService();
    // }

    @Bean
    public OrderService orderService(UserService userService) {
        return new OrderService(userService);
    }
}
