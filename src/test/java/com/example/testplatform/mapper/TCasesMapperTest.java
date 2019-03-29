package com.example.testplatform.mapper;

import com.example.testplatform.TestplatformApplication;
import com.example.testplatform.model.Apis;
import com.example.testplatform.model.checkpoint.CheckPoint;
import com.example.testplatform.model.checkpoint.CheckPointType;
import org.junit.Before;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;
@SpringBootTest(classes = TestplatformApplication.class)
public class TCasesMapperTest extends AbstractTestNGSpringContextTests {

    @Autowired
    TCasesMapper tCasesMapper;

    private Apis testCase;
    @BeforeTest
    public void init() throws Exception{
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type","application/json");

        List<String> preActions = new ArrayList<>();
        preActions.add("插入一条数据");
        preActions.add("获取插入数据的id");

        List<String> postActions = new ArrayList<>();
        postActions.add("删除一条数据测试");

        CheckPointTypeImp checkPointTypeImp = new CheckPointTypeImp();
        checkPointTypeImp.setStrCheckPointType("STREQUAL");
        CheckPointImp checkPointImp = new CheckPointImp();
        checkPointImp.setType("StrCheckPoint");
        checkPointImp.setCheckPointType(checkPointTypeImp);
        checkPointImp.setCheckKey("testname");
        checkPointImp.setExpected("test");
        List<CheckPoint> checkPoints = new ArrayList<>();
        checkPoints.add(checkPointImp);




        testCase = new Apis();
        testCase.setSuite("测试");
        testCase.setTestModule("测试模块");
        testCase.setTestName("测试名字");
        testCase.setDescs("测试描述");
        testCase.setMethod(HttpMethod.GET);
        testCase.setUrl(new URI("/testcase/uri"));
        testCase.setHeaders(headers);
        testCase.setParameters("参数");
        testCase.setPreActionNames(preActions);
        testCase.setPostActionNames(postActions);
        testCase.setCheckPoints(checkPoints);
    }


    @Test
    public void testInsertTestcase() {
        int result = tCasesMapper.insertTestcase(testCase);
        assertEquals(result,1);
    }

    @AfterTest
    public void deleteInsetedTestcase(){
        tCasesMapper.deleteTestcaseByTestName("测试名字");
    }

    @Test
    public void testSelectApisByExpected() throws Exception {
    }

    @Test
    public void testSelectApisByTestname() throws Exception {
    }

    @Test
    public void testSelectApisById() throws Exception {
    }

    @Test
    public void testCountTestcaseById() throws Exception {
    }

    @Test
    public void testUpdateTestcase() throws Exception {
    }

    @Test
    public void testSelectApisByName() throws Exception {
    }

    @Test
    public void testSelectApisByDescs() throws Exception {
    }

    @Test
    public void testDeleteTestcaseByTestname() throws Exception {
    }

    @Test
    public void testDeleteTestcaseById() throws Exception {
    }

    @Test
    public void testUpdateTestResult() throws Exception {
    }

    @Test
    public void testGetAllTestcases() throws Exception {
    }

    @Test
    public void testQueryByFields() throws Exception {
    }

    @Test
    public void testUpdateTestCaseTestResult() throws Exception {
    }

    @Test
    public void testQueryTestCaseTestResult() throws Exception {
    }

    class CheckPointTypeImp implements CheckPointType{
        private String strCheckPointType;

        public String getStrCheckPointType() {
            return strCheckPointType;
        }

        public void setStrCheckPointType(String strCheckPointType) {
            this.strCheckPointType = strCheckPointType;
        }
    }

    class CheckPointImp implements CheckPoint{
        private String type;
        private CheckPointType checkPointType;
        private String checkKey;
        private String expected;

        @Override
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public CheckPointType getCheckPointType() {
            return checkPointType;
        }

        public void setCheckPointType(CheckPointType checkPointType) {
            this.checkPointType = checkPointType;
        }

        @Override
        public String getCheckKey() {
            return checkKey;
        }

        public void setCheckKey(String checkKey) {
            this.checkKey = checkKey;
        }

        @Override
        public String getExpected() {
            return expected;
        }

        public void setExpected(String expected) {
            this.expected = expected;
        }
    }
}