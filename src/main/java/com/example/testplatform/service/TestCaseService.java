package com.example.testplatform.service;

import com.example.testplatform.handler.CustomResponseErrorHandler;
import com.example.testplatform.exception.CustomException;
import com.example.testplatform.mapper.TestCaseMapper;
import com.example.testplatform.model.Apis;
import com.example.testplatform.payload.ApiEntityRequest;
import com.example.testplatform.payload.ApiResponse;
import com.example.testplatform.payload.ApiTestRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.Null;
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

        try{
        int result = mapper.insertTestcase(apis);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true,"成功插入测试用例"),HttpStatus.OK);
        }catch (DuplicateKeyException e){
            e.printStackTrace();
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(false,"测试用例插入失败"),HttpStatus.OK);
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





    }

    public ResponseEntity<?> testATestCase(ApiTestRequest request){
        String testname = request.getTestname();
        Apis apis = mapper.selectApisByTestname(request.getTestname());
        URI url = apis.getUrl();
        Map<String,String> parameters= apis.getParameters();
        String expected = null;
        try {
            expected = new ObjectMapper().writeValueAsString(apis.getExpected());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        RestTemplate rest = new RestTemplate();
        String actual = null;
        try{
            rest.setErrorHandler(new CustomResponseErrorHandler());
            ResponseEntity<ApiResponse> responseEntity = rest.postForEntity(url,parameters,ApiResponse.class);
            actual = new ObjectMapper().writeValueAsString(responseEntity.getBody());
        }catch (CustomException e){
            e.printStackTrace();
            actual = e.getBody();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("expected"+expected);
        System.out.println("actual"+actual);
        if(expected !=null && actual !=null && expected.equals(actual)){
            mapper.updateTestResult(testname,actual,true,LocalDateTime.now());
            return new ResponseEntity<ApiResponse>(new ApiResponse(true,"测试通过"),HttpStatus.OK);
        }
        mapper.updateTestResult(testname,actual,false,LocalDateTime.now());
        return  new ResponseEntity<ApiResponse>(new ApiResponse(true,"测试失败"),HttpStatus.OK);

    }
}
