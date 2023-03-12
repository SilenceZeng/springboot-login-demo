package com.example.login.mapper;

import com.example.login.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.Instant;

@Mapper
public interface UserMapper {
    @Select("select * from user where id = #{id}")
    User findUserById(@Param("id") Integer id);

    @Select("select * from user where username = #{username}")
    User findUserByUsername(String username);

    @Insert("insert into user(username, encrypted_password)" +
            "values (#{username}, #{encryptedPassword})")
    void save(String username, String encryptedPassword);
}
