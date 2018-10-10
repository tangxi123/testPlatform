package com.example.testplatform.service;

import com.example.testplatform.payload.ApiResponse;
import com.example.testplatform.payload.SignupRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public ResponseEntity<?> registerUser(SignupRequest request){
        if(request.getUsername().equals("tangxi")){
            return ResponseEntity.ok(new ApiResponse(200,"用户注册成功!"));
        }
        if(request.getUsername().equals("tang")){
                return new ResponseEntity(new ApiResponse(400,"用户注册失败!"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(new ApiResponse(404,"用户注册失败!"), HttpStatus.BAD_REQUEST);
    }

}
