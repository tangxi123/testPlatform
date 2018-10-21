package com.example.testplatform.service;

import com.example.testplatform.mapper.TestCaseMapper;
import com.example.testplatform.model.Apis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestATestCaseServiceTest {
    @Autowired
    TestCaseMapper mapper;

    @Test
    public void testATestCase() {
        Apis apis = mapper.selectApisByTestname("WhenUsernameAndPasswordTrueThenLogin");
        apis.getUrl();
        apis.getParameters();
    }
}