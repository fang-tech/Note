package com.func.spring.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Data
public class Person {
    private List<String> names;
    private Set<String> addr;
    private Map<Integer, String> phones;
    private Properties properties;
}
