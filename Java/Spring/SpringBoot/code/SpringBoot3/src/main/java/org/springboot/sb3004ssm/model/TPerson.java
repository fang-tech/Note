package org.springboot.sb3004ssm.model;

import java.io.Serializable;
import lombok.Data;

/**
 * t_person
 */
@Data
public class TPerson implements Serializable {
    private Integer id;

    private String name;

    private String cardNumber;

    private String birth;

    private static final long serialVersionUID = 1L;
}