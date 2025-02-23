package com.func.bank.service;

import com.func.bank.exception.AppException;
import com.func.bank.exception.MoneyNotEnoughException;
import com.func.bank.pojo.Account;

public interface AccountService {

    /**
     * 从from用户转账到to用户
     * @param money 转账的金额
     * @param fromAct 扣钱方j
     * @param toAct 加钱方
     * @throws AppException App发生异常
     * @throws MoneyNotEnoughException 转帐方余额不足
     */
    void transfer(double money, String fromAct, String toAct) throws AppException, MoneyNotEnoughException;}
