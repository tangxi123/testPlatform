package com.example.testplatform.mapper;

import com.example.testplatform.model.ParameterWrapper;
import com.example.testplatform.model.parametertype.SqlParameter;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tangxi.testplatform.api.testcase.util.JacksonUtil;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ParamMapperTest {
    @Autowired
    ParamMapper paramMapper;

    @Test
    public void insertParameter() {
    }

    @Test
    public void insertSqlParamter() {
    }

    @Test
    public void selectParameterWrapperById() {
        ParameterWrapper pw = paramMapper.selectParameterWrapperById(17);
        System.out.println(JacksonUtil.toJson(pw));
        ParameterWrapper pws = JacksonUtil.fromJson(JacksonUtil.toJson(pw), new TypeReference<ParameterWrapper>(){});
        System.out.println(JacksonUtil.toJson(pws));
    }

    @Test
    public void selectParameterWrapperByName() {
        ParameterWrapper pw = paramMapper.selectParameterWrapperByName("testCaseId");
        System.out.println(JacksonUtil.toJson(pw));
        ParameterWrapper pws = JacksonUtil.fromJson(JacksonUtil.toJson(pw), new TypeReference<ParameterWrapper>(){});
        System.out.println(JacksonUtil.toJson(pws));
    }

    @Test
    public void deserizeParamWrapper(){

    }
}