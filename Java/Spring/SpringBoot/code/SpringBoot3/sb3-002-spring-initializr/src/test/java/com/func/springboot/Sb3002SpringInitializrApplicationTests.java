package com.func.springboot;

import com.func.springboot.service.UserService;
import com.func.springboot.service.impl.UserServiceImpl;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Sb3002SpringInitializrApplicationTests {

	@Autowired
	private UserService userService;
	@Test
	void contextLoads() {
		userService.save();
	}

}
