package com.func.bank.dao;

import com.func.bank.pojo.Account;

public interface AccountDao {
    /**
     * 通过用户名查询用户信息
     * @param actno 用户名
     * @return Account对象
     */
    Account selectByActno(String actno);

    /**
     * 更新账户信息
     * @param act 账户信息
     * @return 更新成功返回1, 失败返回0
     */
    int update(Account act);


}
