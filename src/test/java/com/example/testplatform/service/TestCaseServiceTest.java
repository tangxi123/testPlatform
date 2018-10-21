package com.example.testplatform.service;

import com.example.testplatform.mapper.TestCaseMapper;
import com.example.testplatform.model.Apis;
import com.example.testplatform.payload.ApiEntityRequest;
import com.example.testplatform.payload.ApiResponse;
import com.example.testplatform.payload.SignupRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCaseServiceTest {
    @Autowired
    TestCaseService testCaseService;

    @Autowired
    TestCaseMapper testCaseMapper;


    ApiEntityRequest request = new ApiEntityRequest();

    @Before
    public void initApiEntityRequest(){
        try {
            request.setUrl(new URI("http://localhost:8080"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        request.setTestname("whenApisNormalThenInsertTestCaseSuccess");
        request.setMethod(HttpMethod.POST);
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept","Application/json");
        headers.put("Content-Type","Application/json");
        request.setHeaders(headers);
        Map<String,String> parameters= new HashMap<>();
        parameters.put("username","tangxi");
        parameters.put("password","123456");
        request.setParameters(parameters);
        Map<String,Object> expected = new HashMap<>();
        expected.put("success",true);
        expected.put("message","登录成功");
        request.setExpected(expected);


    }

    @Test
    public void whenApisNormalThenInsertTestCaseSuccess() {
        ResponseEntity<?> result = testCaseService.createTestCase(request);
        ApiResponse apiResponse = (ApiResponse) result.getBody();

        Assert.assertEquals(200,result.getStatusCodeValue());
        Assert.assertEquals(true,apiResponse.getSuccess());
        Assert.assertEquals("成功插入测试用例",apiResponse.getMessage());

    }

    @Test
    public void whenTestnameReapetedThenInsertTestCaseFail(){
        request.setTestname("WhenUsernameAndPasswordTrueThenLogin");
        ResponseEntity<?> result = testCaseService.createTestCase(request);
        ApiResponse apiResponse = (ApiResponse) result.getBody();

        Assert.assertEquals(200,result.getStatusCodeValue());
        Assert.assertEquals(false,apiResponse.getSuccess());
        Assert.assertEquals("测试用例插入失败",apiResponse.getMessage());
        request.setTestname(null);

    }

    @After
    public void deleteInsetedTestcase(){
        testCaseMapper.deleteTestcaseByTestname(request.getTestname());
        System.out.println("删除成功");
    }
}