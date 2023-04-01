package com.example.login.service;

import com.example.login.dao.BlogDao;
import com.example.login.entity.BlogResult;
import com.example.login.entity.LoginResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {
    @Mock
    BlogDao blogDao;

    @InjectMocks
    BlogService blogService;

    @Test
    public void getBlogsFromDb() {
        blogService.getBlogs(1, 10, null);
        Mockito.verify(blogDao).getBlogs(1, 10, null);
    }

    @Test
    public void returnFailureWhenExceptionThrown() {
        when(blogDao.getBlogs(anyInt(), anyInt(), any())).thenThrow(new RuntimeException());

        BlogResult result = blogService.getBlogs(1, 0, null);

        Assertions.assertEquals("fail", result.getStatus());
        Assertions.assertEquals("系统异常", result.getMsg());
    }
}
