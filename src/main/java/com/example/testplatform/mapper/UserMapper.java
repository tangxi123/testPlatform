package com.example.testplatform.mapper;

import com.example.testplatform.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserMapper {
    int insertUser(User user);

    @Select("SELECT EXISTS(SELECT 1 FROM users WHERE username = #{username})")
    Boolean existsByUsername(@Param("username") String username);
}
