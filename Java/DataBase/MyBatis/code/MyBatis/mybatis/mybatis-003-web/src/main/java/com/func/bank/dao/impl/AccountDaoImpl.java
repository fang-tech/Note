package com.func.bank.dao.impl;

import com.func.bank.dao.AccountDao;
import com.func.bank.pojo.Account;
import com.func.bank.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

public class AccountDaoImpl implements AccountDao {
    @Override
    public Account selectByActno(String actno) {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        Account account = sqlSession.selectOne("selectByActno", actno);
        return account;
    }

    @Override
    public int update(Account act) {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        int retVal = sqlSession.update("update", act);
        return retVal;
    }
}
