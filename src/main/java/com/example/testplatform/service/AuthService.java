package com.example.testplatform.service;

import com.example.testplatform.mapper.UserMapper;
import com.example.testplatform.model.User;
import com.example.testplatform.payload.ApiResponse;
import com.example.testplatform.payload.LoginRequest;
import com.example.testplatform.payload.SignupRequest;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@MapperScan("com.example.testplatform.mapper")
public class AuthService {
//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Autowired
    UserMapper userMapper;

    public ResponseEntity<ApiResponse> registerUser(SignupRequest request){
        if(userMapper.existsByUsername(request.getUsername())){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false,"用户注册失败，存在相同的用户名"),HttpStatus.BAD_REQUEST);
        }
        if(userMapper.existsByEmail(request.getEmail())){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false,"用户注册失败，存在相同的邮箱"),HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPassword(request.getPassword());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userMapper.insertUser(user);


        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"用户注册成功!"),HttpStatus.OK);
    }



}
