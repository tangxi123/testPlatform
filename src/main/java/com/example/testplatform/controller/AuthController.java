package com.example.testplatform.controller;

import com.example.testplatform.payload.ApiResponse;
import com.example.testplatform.payload.LoginRequest;
import com.example.testplatform.payload.SignupRequest;
import com.example.testplatform.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping(value = "/signup",produces = "application/json",consumes = "application/json")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignupRequest request) {
        return authService.registerUser(request);
    }

//    @PostMapping(value = "/signin",produces = "application/json",consumes = "application/json")
//    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request){
//        return authService.authenticateUser(request);
//    }


}
