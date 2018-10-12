package com.example.testplatform.controller;

import com.example.testplatform.payload.ApiResponse;
import com.example.testplatform.payload.SignupRequest;
import com.example.testplatform.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @PostMapping(value = "/signup",produces = "application/json",consumes = "application/json")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignupRequest request) {
        return new AuthService().registerUser(request);
    }

}
