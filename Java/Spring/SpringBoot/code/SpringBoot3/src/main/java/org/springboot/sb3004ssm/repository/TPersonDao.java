package org.springboot.sb3004ssm.repository;

import org.springboot.sb3004ssm.model.TPerson;
import org.springframework.stereotype.Repository;

@Repository
public interface TPersonDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TPerson record);

    int insertSelective(TPerson record);

    TPerson selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TPerson record);

    int updateByPrimaryKey(TPerson record);
}