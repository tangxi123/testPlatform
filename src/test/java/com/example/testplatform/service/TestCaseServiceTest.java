package com.example.testplatform.service;

import com.example.testplatform.payload.ApiEntityRequest;
import com.example.testplatform.payload.ApiResponse;
import com.example.testplatform.payload.SignupRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TestCaseServiceTest {
    @Autowired
    TestCaseService testCaseService;

    @Test
    public void createTestCase() throws JsonProcessingException {
        RestTemplate rest = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

//        Map<String, String> map = new HashMap<String, String>();
//        map.put("username", "tester12");
//        map.put("name", "tester12");
//        map.put("email", "tang@123412.com");
//        map.put("password", "123456");
//        map.put("confirmedPassword", "123456");

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("tester01");
        signupRequest.setName("tester01");
        signupRequest.setEmail("tang@123450.com1");
        signupRequest.setPassword("123456");
        signupRequest.setConfirmedPassword("123456");


//        String body = new ObjectMapper().writeValueAsString(map);
//
//        HttpEntity<String> entity = new HttpEntity<>(body,headers);

//        String response = rest.postForObject("http://localhost:8080/api/auth/signup",entity,String.class);

        ResponseEntity<String> response = rest.postForEntity("http://localhost:8080/api/auth/signup",signupRequest,String.class);



    }
}