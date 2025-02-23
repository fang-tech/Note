package org.springboot.sb3003externalconfig.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
public class Address {
    private String zipcode;
    private String city;
    private String  street;
}
