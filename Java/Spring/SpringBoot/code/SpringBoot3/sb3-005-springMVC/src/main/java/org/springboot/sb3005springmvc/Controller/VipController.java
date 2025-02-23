package org.springboot.sb3005springmvc.Controller;

import org.springboot.sb3005springmvc.model.Vip;
import org.springboot.sb3005springmvc.service.VipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VipController {

    @Autowired
    private VipService vipService;

    @GetMapping("/vip/{id}")
    public String detailById(@PathVariable("id") Long id) {
        Vip byId = vipService.getById(id);
        return byId.toString();
    }


}
