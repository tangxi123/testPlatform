package com.example.testplatform.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import com.example.testplatform.common.Response;
import com.example.testplatform.common.onCreate;
import com.example.testplatform.common.onUpdate;
import com.example.testplatform.mapper.TCasesMapper;
import com.example.testplatform.model.Apis;
import com.example.testplatform.model.Page;
import com.example.testplatform.payload.ApiEntityRequest;
import com.example.testplatform.payload.ApiResponse;
import com.example.testplatform.payload.ApiTestRequest;
import com.example.testplatform.service.ParameterService;
import com.example.testplatform.service.PrePostActionService;
import com.example.testplatform.service.TestCaseService;

import com.example.testplatform.test.TestExec;
import com.example.testplatform.util.TestCaseExecution;
import com.github.pagehelper.PageInfo;
import com.jayway.restassured.path.json.JsonPath;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tangxi.testplatform.api.testcase.util.JacksonUtil;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
//@RequestMapping("/testcases")
public class TestCaseController {
    private static final Logger LOG = LoggerFactory.getLogger(TestCaseController.class);

    @Autowired
    TestCaseService testCaseService;

    @Autowired
    ParameterService parameterService;

    @Autowired
    PrePostActionService prePostActionService;

    @Autowired
    TCasesMapper tCasesMapper;


    /**
     * 查询所有测试用例
     *
     * @return
     */
    @GetMapping("/testcases")
    @CrossOrigin
    public Response<List<Apis>> getAllTestcases() {
        if (LOG.isTraceEnabled()) {
            LOG.trace(">> TestCaseController.getAllTestcases()");
        }
        return testCaseService.getAllTestcases();
    }

    /**
     * 分页返回测试用例
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @CrossOrigin
    @GetMapping("/testcases/query/list")
    public Response<PageInfo<Apis>> getTestcasesByPage(int pageNum, int pageSize) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(">> TestCaseController.getTestcasesByPage()");
            LOG.trace("传入参数pageNum: {}，pageSize: {}", pageNum, pageSize);
        }
        return testCaseService.getTestcasesByPage(pageNum, pageSize);
    }

    /**
     * 新建测试用例
     *
     * @param request
     * @return
     */
    @CrossOrigin
    @Validated(onCreate.class)
    @PostMapping(value = "/testcases/create", produces = "application/json", consumes = "application/json")
    public Response<String> createTestCase(@Valid @RequestBody Apis request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(">> TestCaseController.createTestCase()");
            LOG.trace("传入参数request: {}", request);
        }
        return testCaseService.createTestCase(request);
    }

    /**
     * 修改测试用例
     *
     * @param request
     * @return
     */
    @CrossOrigin
    @Validated(onUpdate.class)
    @PutMapping(value = "/testcases/update", produces = "application/json", consumes = "application/json")
    public Response<String> updateTestCase(@Valid @RequestBody Apis request) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(">> TestCaseController.updateTestCase()");
            LOG.trace("传入参数request: {}", request);
        }
        return testCaseService.updateTestCase(request);
    }

    /**
     * 根据Id获取测试用例
     *
     * @param id
     * @return
     */
    @CrossOrigin
    @GetMapping("/testcases/{id}")
    public Response<?> getOneTestcase(@PathVariable int id) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(">> TestCaseController.getOneTestcase()");
            LOG.trace("传入参数id: {}", id);
        }
        return testCaseService.getOneTestcase(id);
    }

    /**
     * 根据id,测试名字，测试描述，获取测试用例分页查询
     *
     * @param params
     * @return
     */
    @CrossOrigin
    @GetMapping("testcases/query/")
    public Response<PageInfo<Apis>> getTestcasesByName(@RequestBody Map<String, Object> params) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(">> TestCaseController.getTestcasesByName()");
            LOG.trace("传入参数params: {}", JacksonUtil.toJson(params));
        }
        return testCaseService.getTestcasesByName(params);
    }

    /**
     * 根据Id删除测试用例
     *
     * @param id
     * @return
     */
    @CrossOrigin
    @DeleteMapping("delete/{id}")
    public Response<?> deleteOneTestcase(@PathVariable Long id) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(">> TestCaseController.deleteOneTestcase()");
            LOG.trace("传入参数id: {}", id);
        }
        return testCaseService.deleteOneTestcase(id);
    }

    /**
     * 简单测试测试用例--暂废弃
     * @param request
     * @return
     */
//    @PostMapping(value = "/test",produces = "application/json",consumes = "application/json")
//    public ResponseEntity<?> testATestCase(@RequestBody Apis request){
//        return testCaseService.testATestCase(request);
//    }

    /**
     * 执行测试用例
     *
     * @return
     */
    @CrossOrigin
    @GetMapping("/exectestcase")
    public ResponseEntity<?> execTestCase() {
        return testCaseService.execTestCase();
    }

    /**
     * 根据Id执行测试用例
     *
     * @param id
     * @return
     */
    @CrossOrigin
    @GetMapping("testcases/exectestcase/{id}")
    public Response<?> execTestCaseById(@PathVariable int id) {
        LOG.trace(">> TestCaseController.execTestCaseById()");
        LOG.trace("传入参数id: {}", id);
        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("id", String.valueOf(id));
            XmlSuite suite = new XmlSuite();
            suite.setParameters(params);
            XmlTest test = new XmlTest(suite);
            List<XmlClass> classes = new ArrayList<>();
            classes.add(new XmlClass("com.example.testplatform.test.TestExec"));
            test.setXmlClasses(classes);
            List<XmlSuite> suites = new ArrayList<XmlSuite>();
            suites.add(suite);
            TestNG tng = new TestNG();
            tng.setXmlSuites(suites);
            tng.run();
            Map<String, Object> testResults = new HashMap<>();
//            Apis apis = tCasesMapper.selectApisById(id);
            testResults = tCasesMapper.queryTestCaseTestResult(id);
            return new Response<>(200, testResults, "测试用例执行完成");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new Response<>(500, null, e.getMessage());
        }
    }


    //日志测试
    @GetMapping("/testcases/logs")
    public Response<?> log() {
        LOG.trace(">> log()");
        LOG.info("A INFO MESSAGE");
        LOG.warn("A WARN MESSAGE");
        LOG.error("AN ERROR MESSAGE");
        LOG.trace("<< log()");
        return null;

    }


}
