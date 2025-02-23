package org.springboot.sb3004ssm.repository;

import org.springboot.sb3004ssm.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Person record);

    int insertSelective(Person record);

    Person selectByPrimaryKey(Integer id);

    List<Person> selectAll();

    int updateByPrimaryKeySelective(Person record);

    int updateByPrimaryKey(Person record);
}