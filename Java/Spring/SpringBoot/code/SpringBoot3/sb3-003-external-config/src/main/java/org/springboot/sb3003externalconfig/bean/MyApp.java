package org.springboot.sb3003externalconfig.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "app")
@Data
public class MyApp {
    private String name;
    private Integer age;
    private String email;

}
