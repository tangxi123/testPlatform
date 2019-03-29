package com.example.testplatform.service;

import com.example.testplatform.mapper.PrePostMapper;
import com.example.testplatform.model.PrePostActionWrapper;
import com.example.testplatform.model.prepostactiontype.PrePostActionType;
import com.example.testplatform.model.prepostactiontype.SqlPrePostAction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PrePostActionServiceTest {
    @Autowired
    PrePostMapper prePostMapper;

    @Autowired
    PrePostActionService prePostActionService;

//    @Test
//    public void createPrePostAction() {
//        SqlPrePostAction sqlPrePostAction = new SqlPrePostAction();
//        sqlPrePostAction.setDatabase("tplatform");
//        sqlPrePostAction.setHost("localhost");
//        sqlPrePostAction.setPort("3306");
//        sqlPrePostAction.setUser("root");
//        sqlPrePostAction.setPassword("tx123456");
//        sqlPrePostAction.setSql("INSERT INTO tplatform.zsi_test_case (testname, method, url, headers, parameters, expected, created_at, updated_at, actual, is_passed, test_at, suite, test_module, descs, status_code) VALUES ('GivenExistedIdThenGetTestCaseDFSF', 'GET', '/testcase/id/', '{\"Content-Type\":\"application/json\",\"Accept\":\"application/json\"}', '{\"id\":\"72\"}', '{\"id\":1}', '2019-02-08 17:54:56', '2019-02-08 18:05:09', '', null, null, 'TestCase', 'QueryById', '根据存在的Id获取测试用例', null);");
//
//        PrePostActionWrapper prePostActionWrapper = new PrePostActionWrapper();
//        prePostActionWrapper.setName("插入一条数据");
//        prePostActionWrapper.setDescs("在执行查询前需要插入一条数据");
//        prePostActionWrapper.setActionType(PrePostActionType.SQL);
//        prePostActionWrapper.setAction(sqlPrePostAction);
//
//        prePostActionService.createPrePostAction(prePostActionWrapper);

//    }
}