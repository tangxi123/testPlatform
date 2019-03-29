package com.example.testplatform.mapper;

import com.example.testplatform.model.PrePostActionWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tangxi.testplatform.api.testcase.util.JacksonUtil;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PrePostMapperTest {
    @Autowired
    PrePostMapper prePostMapper;

    @Test
    public void insertPrePostAction() {
    }

    @Test
    public void insertSqlPrePostAction() {
    }

    @Test
    public void selectPrePostActionWrapperById() {
        PrePostActionWrapper prePostActionWrapper = prePostMapper.selectPrePostActionWrapperById(2);
        String prePostStr = JacksonUtil.toJson(prePostActionWrapper);
        System.out.println(prePostStr);
    }

    @Test
    public void selectPrePostActionWrapperByName() {
    }
}