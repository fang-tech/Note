package com.func.springmvc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;

@Data
public class User {
    private String username;
    private String password;
    private String sex;
    private String[] hobby;
}
