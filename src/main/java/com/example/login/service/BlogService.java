package com.example.login.service;

import com.example.login.dao.BlogDao;
import com.example.login.entity.Blog;
import com.example.login.entity.BlogResult;
import com.example.login.entity.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BlogService {
    private BlogDao blogDao;
    private UserService userService;

    @Inject
    public BlogService(BlogDao blogDao, UserService userService) {
        this.blogDao = blogDao;
        this.userService = userService;
    }

    public BlogResult getBlogs(Integer page, Integer size, Integer userId) {
        try {
            List<Blog> blogs = blogDao.getBlogs(page, size, userId);
            blogs.forEach(blog -> {
                User user = userService.getUserById(blog.getUserId());
                blog.setUser(user);
            });
            int count = blogDao.count(userId);

            int pageCount = count % size == 0 ? count / size : count / size + 1;
            return BlogResult.successBlogs(blogs, page, size, pageCount);
        } catch (Exception e) {
            return BlogResult.failure("系统异常");
        }

    }
}
