package com.example.testplatform.mapper;

import com.example.testplatform.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    int insertUser(User user);
}
