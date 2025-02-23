package org.springboot.sb3004ssm;

import org.mybatis.spring.annotation.MapperScan;
import org.springboot.sb3004ssm.model.Person;
import org.springboot.sb3004ssm.model.Vip;
import org.springboot.sb3004ssm.repository.PersonMapper;
import org.springboot.sb3004ssm.repository.VipMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@MapperScan("org.springboot.sb3004ssm.repository")
@SpringBootApplication
public class Sb3004SsmApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Sb3004SsmApplication.class, args);
		VipMapper vipMapper = applicationContext.getBean("vipMapper", VipMapper.class);
		Vip vip = vipMapper.selectByPrimaryKey(1L);
		System.out.println(vip);
		applicationContext.close();
	}

}
