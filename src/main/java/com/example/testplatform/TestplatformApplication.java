package com.example.testplatform;

import com.example.testplatform.mapper.UserMapper;
import com.example.testplatform.model.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
@MapperScan("com.example.testplatform.mapper")
public class TestplatformApplication implements CommandLineRunner{

    @Autowired
	private UserMapper userMapper;

	public static void main(String[] args) {
		SpringApplication.run(TestplatformApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setId(Long.parseLong("1"));
		user.setUsername("tangxi");
		user.setName("tang");
		user.setPassword("123456");
		user.setEmail("tangxi669@163.com");
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		userMapper.insertUser(user);

	}
}
