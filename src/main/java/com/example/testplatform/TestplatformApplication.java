package com.example.testplatform;

import com.example.testplatform.mapper.UserMapper;
import com.example.testplatform.model.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;

@SpringBootApplication
@ComponentScan(basePackages = {"org.tangxi.testplatform.api.testcase", "com.example.testplatform"})

@MapperScan("com.example.testplatform.mapper")
public class TestplatformApplication {

    public static void main(String[] args) {

        SpringApplication.run(TestplatformApplication.class, args);


    }


}
