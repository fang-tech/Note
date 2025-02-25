package com.func.bank.web.controller;

import com.func.bank.exception.AppException;
import com.func.bank.exception.MoneyNotEnoughException;
import com.func.bank.pojo.Account;
import com.func.bank.service.AccountService;
import com.func.bank.service.impl.AccountServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/transfer")
public class AccountController extends HttpServlet {
    private AccountService accountService = new AccountServiceImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String fromActno = req.getParameter("fromActno");
        String toActno = req.getParameter("toActno");
        Double money = Double.valueOf (req.getParameter("money"));
        PrintWriter out = resp.getWriter();
        try {
            accountService.transfer(money, fromActno, toActno);
            out.print("<h1>转账成功</h1>");
        } catch (AppException e) {
            out.print(e.getMessage());
        } catch (MoneyNotEnoughException e) {
            out.print(e.getMessage());
        }
    }
}
