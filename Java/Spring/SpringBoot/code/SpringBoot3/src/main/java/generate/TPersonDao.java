package generate;

import generate.TPerson;
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