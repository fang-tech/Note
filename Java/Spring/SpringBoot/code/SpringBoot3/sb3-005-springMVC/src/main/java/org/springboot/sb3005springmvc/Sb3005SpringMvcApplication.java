package org.springboot.sb3005springmvc;

import org.mybatis.spring.annotation.MapperScan;
import org.springboot.sb3004ssm.repository.PersonMapper;
import org.springboot.sb3005springmvc.model.Vip;
import org.springboot.sb3005springmvc.repository.VipMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@MapperScan("org.springboot.sb3005springmvc.repository")
@SpringBootApplication
public class Sb3005SpringMvcApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Sb3005SpringMvcApplication.class, args);
		VipMapper mapper = applicationContext.getBean("vipMapper", VipMapper.class);
		System.out.println(mapper.selectByPrimaryKey(1L));
		Vip vip = mapper.selectByPrimaryKey(1L);
		System.out.println(vip);
	}


}
