package com.example.testplatform.mapper;

import com.example.testplatform.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
public interface UserMapper {
    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO users( username, name, password, email, created_At, updated_At) VALUES (#{username},#{name},#{password},#{email},#{createdAt},#{updatedAt})")
    int insertUser(User user);

    @Select("SELECT EXISTS(SELECT 1 FROM users WHERE username = #{username})")
    Boolean existsByUsername(@Param("username") String username);

    @Select("SELECT EXISTS(SELECT 1 FROM users WHERE email = #{email})")
    Boolean existsByEmail(@Param("email") String email);

    @Select("SELECT * FROM users WHERE username = #{username} AND password = #{password}")
    User loginWithUsername(@Param("username") String username, @Param("password") String password);

    @Select("SELECT * FROM users WHERE email = #{email} AND password = #{password}")
    User loginWithEmail(@Param("email") String emial, @Param("password") String password);


    @Insert("INSERT user_roles(user_id, role_id) VALUES (#{user_id}, #{role_id})")
    int insertUserRole(@Param("user_id") Long userid, @Param("role_id") Integer role_id);


}
