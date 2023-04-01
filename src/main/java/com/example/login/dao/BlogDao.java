package com.example.login.dao;

import com.example.login.entity.Blog;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class BlogDao {
    private final SqlSession sqlSession;

    @Inject
    public BlogDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Blog> getBlogs(Integer page, Integer size, Integer UserId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", UserId);
        parameters.put("offset", (page - 1) * size);
        parameters.put("limit", size);
        return sqlSession.selectList("selectBlog", parameters);
    }

    public int count(Integer userId) {
        return sqlSession.selectOne("countBlog", userId);
    }
}
