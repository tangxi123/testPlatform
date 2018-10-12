package com.example.testplatform.service;

import com.example.testplatform.payload.ApiResponse;
import com.example.testplatform.payload.SignupRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public ResponseEntity<ApiResponse> registerUser(SignupRequest request){
        if(request.getUsername().equals("tang")){
           return new ResponseEntity<ApiResponse>(new ApiResponse(false,"用户注册失败，存在相同的用户名"),HttpStatus.BAD_REQUEST);
        }
        if(request.getUsername().equals("tangxi")){
                return new ResponseEntity<ApiResponse>(new ApiResponse(false,"用户注册失败，存在相同的邮箱"),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"用户注册成功!"),HttpStatus.OK);
    }

}
