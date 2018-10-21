package com.example.testplatform.controller;

import com.example.testplatform.payload.ApiEntityRequest;
import com.example.testplatform.payload.ApiResponse;
import com.example.testplatform.payload.ApiTestRequest;
import com.example.testplatform.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testcase")
public class TestCaseController {
    @Autowired
    TestCaseService testCaseService;

    @PostMapping(value = "/create",produces = "application/json",consumes = "application/json")
    public ResponseEntity<?> createTestCase(@RequestBody ApiEntityRequest request){
        return testCaseService.createTestCase(request);
    }

    @PostMapping(value = "/test",produces = "application/json",consumes = "application/json")
    public ResponseEntity<?> testATestCase(@RequestBody ApiTestRequest request){
        return testCaseService.testATestCase(request);
    }
}
