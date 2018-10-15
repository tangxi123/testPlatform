package com.example.testplatform.service;

import com.example.testplatform.payload.ApiResponse;
import com.example.testplatform.payload.SignupRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @Test
    public void registerUser() {
        SignupRequest request = new SignupRequest();
        request.setName("tangxi");
        request.setUsername("tangxi");
        request.setPassword("123456");
        request.setConfirmedPassword("123456");
        request.setEmail("tangxi669@163.com");
        ResponseEntity<ApiResponse> apiResponse = authService.registerUser(request);
        System.out.println(apiResponse.toString());
    }





}