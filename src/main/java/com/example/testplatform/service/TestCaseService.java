package com.example.testplatform.service;

import com.example.testplatform.common.Response;
import com.example.testplatform.controller.TestCaseController;
import com.example.testplatform.handler.CustomResponseErrorHandler;
import com.example.testplatform.exception.CustomException;
import com.example.testplatform.mapper.TCasesMapper;
import com.example.testplatform.model.Apis;
import com.example.testplatform.model.Page;
import com.example.testplatform.payload.ApiResponse;
import com.example.testplatform.test.TestExec;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.specification.RequestSpecification;
import junit.framework.TestCase;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.tangxi.testplatform.api.testcase.util.JacksonUtil;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.keystore;

@Service
@MapperScan("com.example.testplatform.TCaseMapper")
public class TestCaseService {
    private static final Logger LOG = LoggerFactory.getLogger(TestCaseService.class);

    @Autowired
    TCasesMapper tCasesMapper;

    @Autowired
    ParameterService parameterService;



    /**
     * 获取所有测试用例
     *
     * @return
     */
    public Response<List<Apis>> getAllTestcases() {
        if(LOG.isTraceEnabled()) {
            LOG.trace(">> TestCaseService.getAllTestcases()");
        }
        try{
            List<Apis> testCases = tCasesMapper.getAllTestcases();
            LOG.debug("查询到的测试用例列表testcases:{}",testCases);
            return new Response<>(200,testCases,null);
        }catch (Exception e) {
            LOG.error(e.getMessage(),e);
            return new Response<>(500,null, e.getMessage());
        }

    }

    /**
     * 分页返回测试用例
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Response<PageInfo<Apis>> getTestcasesByPage(int pageNum, int pageSize) {
        if(LOG.isTraceEnabled()) {
            LOG.trace(">> TestCaseService.getTestcasesByPage");
            LOG.trace("传入参数pageNum: {}，pageSize: {}",pageNum,pageSize);
        }
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Apis> testCases = tCasesMapper.getAllTestcases();
            LOG.debug("查询到的测试用例列表testcases:{}",testCases);
            PageInfo<Apis> pageInfo = new PageInfo<>(testCases);
            return new Response<>(200, pageInfo, "获取测试用例列表成功");
        }catch (Exception e){
            LOG.error(e.getMessage(),e);
            return new Response<>(500,null,e.getMessage());
        }
        }


    /**
     * 新建测试用例
     *
     * @param request
     * @return
     * @throws RestClientException
     */
    public Response<String> createTestCase(Apis request){
        if(LOG.isTraceEnabled()) {
            LOG.trace(">> TestCaseService.createTestCase");
        }
        request.setCreatedAt(LocalDateTime.now());
        request.setUpdatedAt(request.getCreatedAt());
        try {
            if(LOG.isTraceEnabled()){
                LOG.trace("传入到tCasesMapper.insertTestcase()方法的参数request: {}",request);
            }
            tCasesMapper.insertTestcase(request);
            return new Response<>(200,null,"测试用例插入成功");
        }catch (DuplicateKeyException e){
            LOG.error(e.getMessage(),e);
            return new Response<>(400,null,"测试用例名字重复");
        }catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage(),e);
            return new Response<>(500,null,e.getMessage());
        }
    }

    /**
     * 修改测试用例
     * @param request
     * @return
     * @throws RestClientException
     */
    public Response<String> updateTestCase(Apis request) {
        if(LOG.isTraceEnabled()) {
            LOG.trace(">> TestCaseService.updateTestCase");
        }
        try {
            request.setUpdatedAt(LocalDateTime.now());
            if(LOG.isTraceEnabled()){
                LOG.trace("传入到tCasesMapper.updateTestcase()方法的参数request: {}",request);
            }
            int updateCount = tCasesMapper.updateTestcase(request);
            if(updateCount == 1){
                return new Response<>(200,null,"测试用例更新成功");
            }else{
                return new Response<>(400,null,"测试用例更新失败");
            }
        } catch (DuplicateKeyException e) {
            LOG.error(e.getMessage(),e);
            return new Response<>(400,null,e.getMessage());
        }catch (Exception e){
            LOG.error(e.getMessage(),e);
            return new Response<>(500,null,e.getMessage());
        }
        }

    /**
     * 根据Id获取测试用例
     * @param id
     * @return
     */
    public Response<?> getOneTestcase(int id) {
        if(LOG.isTraceEnabled()) {
            LOG.trace(">> TestCaseService.getOneTestcase()");
            LOG.trace("传入参数id: {}",id);
        }
        try{
            Apis testCase = tCasesMapper.selectApisById(id);
            LOG.debug("查询到的测试用例为：{}",testCase);
            return new Response<>(200,testCase,"获取测试用例成功");
        }catch (Exception e){
            LOG.error(e.getMessage(),e);
            return new Response<>(500,null,e.getMessage());
        }
    }

    /**
     * 根据名字获取测试用例
     * @param params
     * @return
     */
    public Response<PageInfo<Apis>> getTestcasesByName(Map<String,Object> params) {
        if(LOG.isTraceEnabled()) {
            LOG.trace(">> TestCaseService.getTestcasesByName()");
            LOG.trace("传入参数params: {}", JacksonUtil.toJson(params));
        }
        try {
            List<Apis> apis = tCasesMapper.queryByFields(params);
            LOG.trace("查询到的测试用例列表：{}",apis);
            PageInfo<Apis> testCases = new PageInfo<>(apis);
            LOG.trace("返回的分页列表为：{}",testCases);
            return new Response<>(200, testCases, "查询成功");
        }catch (Exception e){
            LOG.error(e.getMessage(),e);
            return new Response<>(500, null, e.getMessage());
        }
    }


    /**
     * 根据Id删除测试用例
     * @param id
     * @return
     */
    public Response<?> deleteOneTestcase(Long id) {
//        try {
            int delCount = tCasesMapper.deleteTestcaseById(id);
            System.out.println("删除数据条数： " + delCount);
            return new Response<String>(200, null, "删除成功");
//        }catch (Exception e){
//            e.printStackTrace();
//            return new Response<>(500,null,e.getMessage());
//        }
    }

    /**
     * 测试测试用例--暂废弃
     * @param request
     * @return
     */
//    public ResponseEntity<?> testATestCase(Apis request) {
//        String testname = request.getTestname();
//        Apis apis = tCasesMapper.selectApisByTestname(request.getTestname());
//        URI url = apis.getUrl();
//        String parameters = apis.getParameters();
//        String expected = null;
//        try {
//            expected = new ObjectMapper().writeValueAsString(apis.);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        RestTemplate rest = new RestTemplate();
//        String actual = null;
//        try {
//            rest.setErrorHandler(new CustomResponseErrorHandler());
//            ResponseEntity<ApiResponse> responseEntity = rest.postForEntity(url, parameters, ApiResponse.class);
//            actual = new ObjectMapper().writeValueAsString(responseEntity.getBody());
//        } catch (CustomException e) {
//            e.printStackTrace();
//            actual = e.getBody();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        System.out.println("expected" + expected);
//        System.out.println("actual" + actual);
//        if (expected != null && actual != null && expected.equals(actual)) {
//            tCasesMapper.updateTestResult(testname, actual, true, LocalDateTime.now());
//            return new ResponseEntity<ApiResponse>(new ApiResponse(200, "测试通过"), HttpStatus.OK);
//        }
//        tCasesMapper.updateTestResult(testname, actual, false, LocalDateTime.now());
//        return new ResponseEntity<ApiResponse>(new ApiResponse(400, "测试失败"), HttpStatus.OK);
//
//    }

    /**
     * 执行测试用例
     *
     * @return
     */
    public ResponseEntity<?> execTestCase() {
//        XmlSuite suite = new XmlSuite();
//        suite.setName("testcase-suites");
//        XmlTest test = new XmlTest(suite);
//        test.setName("queryByIdTest");
//        List<XmlClass> classes = new ArrayList<>();
//        classes.add(new XmlClass(("org.tangxi.testplatform.api.testcase.testcase.testcase.querybyid.QueryTest")));
//        test.setXmlClasses(classes);
//
//        List<XmlSuite> suites = new ArrayList<>();
//        suites.add(suite);
//
//        TestNG tng = new TestNG();
//        tng.setXmlSuites(suites);
//        tng.run();//  执行这条语句报错了..
//
//        return new ResponseEntity<String>("测试用例执行成功", HttpStatus.OK);

        //测试执行测试用例
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG testng = new TestNG();
        testng.setTestClasses(new Class[] { TestExec.class });
        testng.addListener(tla);
        testng.run();

        return null;
    }

    /**
     * 根据Id执行测试用例
     * @param id
     * @return
     */
    public ResponseEntity<?> execTestCaseById(int id){
        //1.根据id获取数据表中的测试用例
        //getTestCaseById(id);
        //2.如果有前置动作，执行前置动作
        //execPre();
        //3.测试执行，如果参数里有$,则对参数进行替换
        //execTest();
        //4.校验检查点
        //execVerify();
        //5.如果有后置动作，执行后置动作
        //execPost();

        return null;

    }

    /**
     * 根据Id返回TestCase
     * @param id
     * @return
     */
    public Apis getTestCaseById(int id){
        Apis testCase = tCasesMapper.selectApisById(id);
        return testCase;
    }
}
