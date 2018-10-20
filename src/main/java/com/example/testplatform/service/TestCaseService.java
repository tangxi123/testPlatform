package com.example.testplatform.service;

import com.example.testplatform.handler.CustomResponseErrorHandler;
import com.example.testplatform.exception.CustomException;
import com.example.testplatform.mapper.TestCaseMapper;
import com.example.testplatform.model.Apis;
import com.example.testplatform.payload.ApiEntityRequest;
import com.example.testplatform.payload.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@MapperScan("com.example.testplatform.mapper")
public class TestCaseService {
    @Autowired
    TestCaseMapper mapper;

    public ResponseEntity<?> createTestCase(ApiEntityRequest request) throws RestClientException {
        String testname = request.getTestname();
        HttpMethod method = request.getMethod();
        URI url = request.getUrl();
        Map<String, String> headers = request.getHeaders();
        String accept = headers.get("Accept");
        String contentType = headers.get("Content-Type");
        Map<String, String> parameters = request.getParameters();
        Map<String, ?> expected = request.getExpected();
        boolean expectedSucess = (Boolean) expected.get("success");
        String expectedMessage = (String)expected.get("message");

        Apis apis = new Apis();
        apis.setTestname(testname);
        apis.setUrl(url);
        apis.setHeaders(headers);
        apis.setMethod(method);
        apis.setParameters(parameters);
        apis.setExpected(expected);
        apis.setCreatedAt(LocalDateTime.now());
        apis.setUpdatedAt(LocalDateTime.now());


        int result = mapper.insertTestcase(apis);
        if(result == 1){
            return new ResponseEntity<ApiResponse>(new ApiResponse(true,"成功插入测试用例"),HttpStatus.OK);
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(false,"测试用例插入失败"),HttpStatus.BAD_GATEWAY);
//        //根据expected查询Apis对象
//        try {
//            String expe = new ObjectMapper().writeValueAsString(apis.getExpected());
//            List<Apis> apises = mapper.selectApisByExpected(expe);
//            for(Apis ap : apises){
//                System.out.println(ap.getTestname());
//            }
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }





//        RestTemplate rest = new RestTemplate();
//
//        ResponseEntity<ApiResponse> response = null;
//        //请求服务的时候，若出现异常，抛出自定义异常，打印出异常栈，异常返回内容，异常信息，最后将异常内容返回
//        try {
//            rest.setErrorHandler(new CustomResponseErrorHandler());
//            response = rest.postForEntity(url, parameters, ApiResponse.class);
//        } catch (CustomException e) {
//            e.printStackTrace();
//            System.out.println(e.getBody());
//            System.out.println(e.getMessage());
//
//            return new ResponseEntity<String>(e.getBody(),HttpStatus.BAD_REQUEST);
//        }
//
////        if(expectedSucess && expectedMessage.equals("登录成功")){
////            return new ResponseEntity<ApiResponse>(new ApiResponse(true,"测试用例通过"),HttpStatus.OK);
////        }
//        return response;


    }
}
