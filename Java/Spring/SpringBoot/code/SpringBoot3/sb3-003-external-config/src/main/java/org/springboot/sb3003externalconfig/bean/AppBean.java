package org.springboot.sb3003externalconfig.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties
@Data
public class AppBean {
    private String[] names;
    private List<Product> products;
    private Map<String, Vip> vips;
}

@Data
class Product {
    private String name;
    private double price;
}

@Data
class Vip {
    private String name;
    private Integer age;
}
