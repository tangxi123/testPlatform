package com.example.testplatform.controller;

import com.example.testplatform.model.Apis;
import com.example.testplatform.model.Page;
import com.example.testplatform.payload.ApiEntityRequest;
import com.example.testplatform.payload.ApiResponse;
import com.example.testplatform.payload.ApiTestRequest;
import com.example.testplatform.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/testcase")
public class TestCaseController {
    @Autowired
    TestCaseService testCaseService;

    /**
     * 查询所有测试用例
     * @return
     */
    @GetMapping("/all")
    @CrossOrigin
    public ResponseEntity<List<Apis>> getAllTestcases(){
        return testCaseService.getAllTestcases();
    }

    /**
     * 分页返回测试用例
     * @param testName
     * @param pageSize
     * @param pageNo
     * @return
     */
    @CrossOrigin
    @GetMapping("/query")
    public ResponseEntity<Page<List<Apis>>> getTestcasesByPage(String testName, int pageSize, int pageNo){
        return testCaseService.getTestcasesByPage(testName,pageSize,pageNo);
    }

    /**
     * 新建测试用例
     * @param request
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/create",produces = "application/json",consumes = "application/json")
    public ResponseEntity<?> createTestCase(@RequestBody Apis request){
        return testCaseService.createTestCase(request);
    }

    /**
     * 修改测试用例
     * @param request
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/update",produces = "application/json",consumes = "application/json")
    public ResponseEntity<?> updateTestCase(@RequestBody Apis request){
        return testCaseService.updateTestCase(request);
    }

    /**
     * 根据Id获取测试用例
     * @param id
     * @return
     */
    @CrossOrigin
    @GetMapping("id/{id}")
    public ResponseEntity<?> getOneTestcase(@PathVariable Long id){
        return testCaseService.getOneTestcase(id);
    }

    /**
     * 根据name获取测试用例
     * @param name
     * @return
     */
    @CrossOrigin
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getTestcasesByName(@PathVariable String name){
        return testCaseService.getTestcasesByName(name);
    }

    /**
     * 根据描述获取测试用例
     * @param descs
     * @return
     */
    @CrossOrigin
    @GetMapping("/descs/{descs}")
    public ResponseEntity<?> getTestcasesByDescs(@PathVariable String descs){
        return testCaseService.getTestcasesByDescs(descs);
    }

    /**
     * 根据Id删除测试用例
     * @param id
     * @return
     */
    @CrossOrigin
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteOneTestcase(@PathVariable Long id){
        return testCaseService.deleteOneTestcase(id);
    }

    /**
     * 简单测试测试用例--暂废弃
     * @param request
     * @return
     */
    @PostMapping(value = "/test",produces = "application/json",consumes = "application/json")
    public ResponseEntity<?> testATestCase(@RequestBody Apis request){
        return testCaseService.testATestCase(request);
    }

    /**
     * 执行测试用例
     * @return
     */
    @CrossOrigin
    @GetMapping("/exectestcase")
    public ResponseEntity<?> execTestCase(){
        return testCaseService.execTestCase();
    }
}
