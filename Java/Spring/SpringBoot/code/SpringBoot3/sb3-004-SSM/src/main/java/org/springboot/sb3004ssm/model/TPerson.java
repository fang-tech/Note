package org.springboot.sb3004ssm.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Builder
@Data
public class TPerson {
    private String name;
    private Integer age;
    private Long id;
    @Singular("addMate")
    private List<String> mate;
    public static void main(String[] args) {
        TPersonBuilder builder = TPerson.builder();
        TPerson fang = builder.name("fang").age(212).id(2L).addMate("test").addMate("mate").build();
        System.out.println(fang);
    }
}
