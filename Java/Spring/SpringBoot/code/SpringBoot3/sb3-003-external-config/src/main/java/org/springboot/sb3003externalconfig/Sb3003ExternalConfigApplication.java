package org.springboot.sb3003externalconfig;

import org.springboot.sb3003externalconfig.bean.MyApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;

@ConfigurationPropertiesScan(basePackages = "org.springboot.sb3003externalconfig.bean")
@ImportResource("classpath:/config/applicationContext.xml")
@SpringBootApplication
public class Sb3003ExternalConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sb3003ExternalConfigApplication.class, args);
    }

}
