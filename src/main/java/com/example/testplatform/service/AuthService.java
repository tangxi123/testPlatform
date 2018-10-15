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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@MapperScan("com.example.testplatform.mapper")
public class AuthService {
//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Autowired
    UserMapper userMapper;
    
    @Transactional
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
        Long userId = user.getId();
        userMapper.insertUserRole(userId,2);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"用户注册成功!"),HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> login(String usernameOrEmail, String password){
        if(userMapper.loginWithUsername(usernameOrEmail,password)!=null){
            return new ResponseEntity<ApiResponse>(new ApiResponse(true,"登录成功"),HttpStatus.OK);
        }else if(userMapper.loginWithEmail(usernameOrEmail,password)!=null){
            return new ResponseEntity<ApiResponse>(new ApiResponse(true,"登录成功"),HttpStatus.OK);
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(false,"登录失败，用户名邮箱或密码错误"),HttpStatus.BAD_REQUEST);
    }



}
