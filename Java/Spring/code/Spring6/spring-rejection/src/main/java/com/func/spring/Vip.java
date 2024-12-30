package com.func.spring;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Data
@Service("vip")
public class Vip {
    @Value("vipN")
    private String name;
}
