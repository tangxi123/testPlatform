package com.example.testplatform.service;

import com.example.testplatform.mapper.ParamMapper;
import com.example.testplatform.model.ParameterWrapper;
import com.example.testplatform.model.parametertype.Parameter;
import com.example.testplatform.model.parametertype.ParameterType;
import com.example.testplatform.model.parametertype.SqlParameter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParameterServiceTest {
    @Autowired
    ParameterService parameterService;

//    @Test
//    public void createParameterTest(){
//        //设置sql类型参数
//        SqlParameter sqlParameter = new SqlParameter();
//        sqlParameter.setHost("localhost");
//        sqlParameter.setPort("3306");
//        sqlParameter.setDatabase("tplatform");
//        sqlParameter.setUser("root");
//        sqlParameter.setPassword("tx123456");
//        sqlParameter.setSql("SELECT id FROM zsi_test_case LIMIT 1");
//        sqlParameter.setParam("id");
//
//        //设置parameterWrapper参数
//        ParameterWrapper parameterWrapper = new ParameterWrapper();
//        parameterWrapper.setName("testCaseId");
//        parameterWrapper.setDesc("从数据库中获取第一条测试用例Id");
//        parameterWrapper.setType(ParameterType.SQL);
//        parameterWrapper.setValue(sqlParameter);
//
//        parameterService.createParameter(parameterWrapper);

//    }

}