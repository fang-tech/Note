package org.springboot.sb3003externalconfig.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "group")
@PropertySource("classpath:a/b/group-info.properties")
@Data
public class Group {
    private String name;
    private String leader;
    private Integer count;
}
