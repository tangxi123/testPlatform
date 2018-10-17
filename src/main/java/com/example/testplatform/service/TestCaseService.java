package com.example.testplatform.service;

import com.example.testplatform.payload.ApiEntityRequest;
import com.example.testplatform.payload.ApiResponse;
import com.example.testplatform.payload.LoginRequest;
import com.example.testplatform.payload.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Book;
import java.net.URI;
import java.net.URL;
import java.util.Map;

@Service
public class TestCaseService {



    public ResponseEntity<ApiResponse> createTestCase(ApiEntityRequest request) throws RestClientException{
//        String testname = request.getTestname();
//        HttpMethod method = request.getMethod();
//        URI url = request.getUrl();
//        Map<String,String> headers = request.getHeaders();
//        String accept = headers.get("Accept");
//        String contentType = headers.get("Content-Type");
////        Map<String,?> body = request.getBody();
////        Object username = body.get("usernameOrEmail");
////        Object password = body.get("password");
//
        RestTemplate rest = new RestTemplate();
//
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsernameOrEmail("tangxi");
//        loginRequest.setPassword("123456");
//
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("tester");
        signupRequest.setName("tester");
        signupRequest.setEmail("tang@12345");
        signupRequest.setPassword("123456");
        signupRequest.setConfirmedPassword("123456");

        HttpEntity<SignupRequest> entity = new HttpEntity<>(signupRequest);



//        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
//        map.add("username", "tester");
//        map.add("name", "tester");
//        map.add("email", "tang@1234.com");
//        map.add("password", "123456");
//        map.add("confirmedPassword", "123456");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        final HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map,headers);

        ResponseEntity<ApiResponse> response;
        response = rest.postForEntity("http://localhost:8080/api/auth/signup",entity,ApiResponse.class);
        response.getBody().toString();


        return response;

    }
}
