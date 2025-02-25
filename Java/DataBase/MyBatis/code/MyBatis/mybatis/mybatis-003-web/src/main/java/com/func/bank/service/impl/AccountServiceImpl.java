package com.func.bank.service.impl;

import com.func.bank.dao.AccountDao;
import com.func.bank.dao.impl.AccountDaoImpl;
import com.func.bank.exception.AppException;
import com.func.bank.exception.MoneyNotEnoughException;
import com.func.bank.pojo.Account;
import com.func.bank.service.AccountService;
import com.func.bank.utils.SqlSessionUtil;
import com.func.javassist.GenerateDaoByJavassist;
import org.apache.ibatis.session.SqlSession;

public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao = (AccountDao) GenerateDaoByJavassist.getMapper(SqlSessionUtil.openSession(), AccountDao.class);
    @Override
    public void transfer(double money, String fromAct, String toAct) throws AppException, MoneyNotEnoughException {
        Account fromAccount = accountDao.selectByActno(fromAct);
        if (fromAccount.getBalance() < money) {
            throw new MoneyNotEnoughException("对不起, 余额不足");
        }
        // 能运行到这, 说明余额是够的
        try {
            // 修改账户余额
            SqlSession sqlSession = SqlSessionUtil.openSession();
            Account toAccount = accountDao.selectByActno(toAct);
            toAccount.setBalance(toAccount.getBalance() + money);
            fromAccount.setBalance(fromAccount.getBalance() - money);
            accountDao.update(fromAccount);
            accountDao.update(toAccount);
            sqlSession.commit();
            SqlSessionUtil.close(sqlSession);
        } catch (Exception e) {
            throw new AppException("App发生未知错误");
        }
    }
}
