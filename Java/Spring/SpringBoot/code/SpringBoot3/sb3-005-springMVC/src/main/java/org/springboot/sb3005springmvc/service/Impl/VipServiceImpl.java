package org.springboot.sb3005springmvc.service.Impl;

import org.springboot.sb3005springmvc.model.Vip;
import org.springboot.sb3005springmvc.repository.VipMapper;
import org.springboot.sb3005springmvc.service.VipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("vipService")
public class VipServiceImpl implements VipService {

    @Autowired
    private VipMapper vipMapper;
    @Override
    public Vip getById(Long id) {
        return vipMapper.selectByPrimaryKey(id);
    }
}
