package com.example.testplatform.controller;

import com.example.testplatform.model.ParameterWrapper;
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
public class ParameterControllerTest {
    @Autowired
    ParameterController parameterController;

    @Test
    public void createParameter() {
        //设置sql类型参数
        SqlParameter sqlParameter = new SqlParameter();
        sqlParameter.setHost("localhost");
        sqlParameter.setPort("3306");
        sqlParameter.setDatabase("tplatform");
        sqlParameter.setUser("root");
        sqlParameter.setPassword("tx123456");
        sqlParameter.setSql("SELECT id FROM zsi_test_case LIMIT 1");
        sqlParameter.setParam("id");

        //设置parameterWrapper参数
        ParameterWrapper parameterWrapper = new ParameterWrapper();
        parameterWrapper.setName("testCaseIds");
        parameterWrapper.setDesc("从数据库中获取第一条测试用例Id");
        parameterWrapper.setType(ParameterType.SQL);
        parameterWrapper.setValue(sqlParameter);

        parameterController.createParameter(parameterWrapper);
    }
}