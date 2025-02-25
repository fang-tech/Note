package org.springboot.sb3004ssm.repository;

import org.springboot.sb3004ssm.model.Vip;
import org.springframework.stereotype.Repository;

@Repository
public interface VipMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Vip record);

    int insertSelective(Vip record);

    Vip selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Vip record);

    int updateByPrimaryKey(Vip record);
}