package org.springboot.sb3004ssm.repository;

import org.springboot.sb3004ssm.model.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Person record);

    int insertSelective(Person record);

    Person selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Person record);

    int updateByPrimaryKey(Person record);
}