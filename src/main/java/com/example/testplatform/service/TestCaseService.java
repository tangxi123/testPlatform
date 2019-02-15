package com.example.testplatform.service;

import com.example.testplatform.handler.CustomResponseErrorHandler;
import com.example.testplatform.exception.CustomException;
import com.example.testplatform.mapper.TCasesMapper;
import com.example.testplatform.model.Apis;
import com.example.testplatform.model.Page;
import com.example.testplatform.payload.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@MapperScan("com.example.testplatform.TCaseMapper")
public class TestCaseService {
    @Autowired
    TCasesMapper tCasesMapper;

    /**
     * 获取所有测试用例
     *
     * @return
     */
    public ResponseEntity<List<Apis>> getAllTestcases() {
        return new ResponseEntity<List<Apis>>(tCasesMapper.getAllTestcases(), HttpStatus.OK);

    }

    /**
     * 分页返回测试用例
     *
     * @param testName
     * @param pageSize
     * @param pageNo
     * @return
     */
    public ResponseEntity<Page<List<Apis>>> getTestcasesByPage(String testName, int pageSize, int pageNo) {
        Page page = new Page();
        List<Apis> allTestCases = testName == null ? tCasesMapper.getAllTestcases() : tCasesMapper.selectApisByName(testName);
        int totalCount = allTestCases.size();
        int totalPageNo = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        if (totalPageNo <= 0) {
            return new ResponseEntity<Page<List<Apis>>>(page, HttpStatus.OK);
        } else {
            List<Apis> pageTestcases = null;
            if (pageNo <= totalPageNo - 1) {
                page.setPageNo(pageNo);
                pageTestcases = allTestCases.subList(pageNo * pageSize, pageNo * pageSize + 10);
            } else {
                page.setPageNo(totalPageNo);
                pageTestcases = allTestCases.subList((totalPageNo - 1) * pageSize, totalCount);
            }
            page.setTotalCount(totalCount);
            page.setTotalPageNo(totalPageNo);
            page.setPageItems(pageTestcases);
            return new ResponseEntity<Page<List<Apis>>>(page, HttpStatus.OK);
        }

    }

    /**
     * 新建测试用例
     *
     * @param request
     * @return
     * @throws RestClientException
     */
    public ResponseEntity<?> createTestCase(Apis request) throws RestClientException {
        request.setCreatedAt(LocalDateTime.now());
        request.setUpdatedAt(request.getCreatedAt());
        try {
            int result = tCasesMapper.insertTestcase(request);
            return new ResponseEntity<ApiResponse>(new ApiResponse(200, "成功插入测试用例"), HttpStatus.OK);
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(400, "测试用例插入失败"), HttpStatus.OK);
    }

    /**
     * 修改测试用例
     * @param request
     * @return
     * @throws RestClientException
     */
    public ResponseEntity<?> updateTestCase(Apis request) throws RestClientException {
        Long id = request.getId();
        int isIdExists = tCasesMapper.countTestcaseById(id);
        if (isIdExists > 0) {
            try {
                request.setUpdatedAt(LocalDateTime.now());
                tCasesMapper.updateTestcase(request);
                return new ResponseEntity<ApiResponse>(new ApiResponse(200, "成功更新测试用例"), HttpStatus.OK);
            } catch (DuplicateKeyException e) {
                e.printStackTrace();
                ;
            }
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(400, "测试用例更新失败"), HttpStatus.OK);
    }

    /**
     * 根据Id获取测试用例
     * @param id
     * @return
     */
    public ResponseEntity<?> getOneTestcase(Long id) {
        Apis apis = tCasesMapper.selectApisById(id);
        System.out.println(apis.getTestModule());
        return new ResponseEntity<Apis>(apis, HttpStatus.OK);
    }

    /**
     * 根据名字获取测试用例
     * @param name
     * @return
     */
    public ResponseEntity<?> getTestcasesByName(String name) {
        List<Apis> apis = tCasesMapper.selectApisByName(name);
        return new ResponseEntity<>(apis, HttpStatus.OK);
    }

    /**
     * 根据描述获取测试用例
     * @param descs
     * @return
     */
    public ResponseEntity<?> getTestcasesByDescs(String descs) {
        List<Apis> apis = tCasesMapper.selectApisByDescs(descs);
        return new ResponseEntity<>(apis, HttpStatus.OK);
    }

    /**
     * 根据Id删除测试用例
     * @param id
     * @return
     */
    public ResponseEntity<?> deleteOneTestcase(Long id) {
        int delCount = tCasesMapper.deleteTestcaseById(id);
        System.out.println("删除数据条数： " + delCount);
        return new ResponseEntity<String>("删除成功", HttpStatus.OK);
    }

    /**
     * 测试测试用例--暂废弃
     * @param request
     * @return
     */
    public ResponseEntity<?> testATestCase(Apis request) {
        String testname = request.getTestname();
        Apis apis = tCasesMapper.selectApisByTestname(request.getTestname());
        URI url = apis.getUrl();
        Map<String, String> parameters = apis.getParameters();
        String expected = null;
        try {
            expected = new ObjectMapper().writeValueAsString(apis.getExpected());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        RestTemplate rest = new RestTemplate();
        String actual = null;
        try {
            rest.setErrorHandler(new CustomResponseErrorHandler());
            ResponseEntity<ApiResponse> responseEntity = rest.postForEntity(url, parameters, ApiResponse.class);
            actual = new ObjectMapper().writeValueAsString(responseEntity.getBody());
        } catch (CustomException e) {
            e.printStackTrace();
            actual = e.getBody();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("expected" + expected);
        System.out.println("actual" + actual);
        if (expected != null && actual != null && expected.equals(actual)) {
            tCasesMapper.updateTestResult(testname, actual, true, LocalDateTime.now());
            return new ResponseEntity<ApiResponse>(new ApiResponse(200, "测试通过"), HttpStatus.OK);
        }
        tCasesMapper.updateTestResult(testname, actual, false, LocalDateTime.now());
        return new ResponseEntity<ApiResponse>(new ApiResponse(400, "测试失败"), HttpStatus.OK);

    }

    /**
     * 执行测试用例
     *
     * @return
     */
    public ResponseEntity<?> execTestCase() {
        XmlSuite suite = new XmlSuite();
        suite.setName("testcase-suites");
        XmlTest test = new XmlTest(suite);
        test.setName("queryByIdTest");
        List<XmlClass> classes = new ArrayList<>();
        classes.add(new XmlClass(("org.tangxi.testplatform.api.testcase.testcase.testcase.querybyid.QueryTest")));
        test.setXmlClasses(classes);

        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);

        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.run();//  执行这条语句报错了..

        return new ResponseEntity<String>("测试用例执行成功", HttpStatus.OK);
    }
}
