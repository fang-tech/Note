package org.springboot.sb3005springmvc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * t_person
 */
@Data
public class Person implements Serializable {
    private Integer id;

    private String name;

    private String cardNumber;

    private String birth;

    private static final long serialVersionUID = 1L;
}