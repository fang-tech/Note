package org.springboot.sb3005springmvc.service;

import org.springboot.sb3005springmvc.model.Vip;
import org.springframework.beans.factory.annotation.Value;

public interface VipService {
    Vip getById(Long id);
}
