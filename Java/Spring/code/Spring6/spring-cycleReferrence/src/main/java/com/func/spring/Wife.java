package com.func.spring;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Wife {
    private String name;
    private Husband husband;
}
