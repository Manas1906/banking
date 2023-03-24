package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BankDao;
import dto.BankAccount;
import dto.BankTransaction;

@WebServlet("/withdraw")
public class Withdraw extends HttpServlet{
@Override
protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	double amt=Double.parseDouble(req.getParameter("amt"));
	
	long acno=(long) req.getSession().getAttribute("acno");
	BankDao bankDao=new BankDao();
	
	BankAccount account=bankDao.find(acno);
	
	if(amt>account.getAmount()) {
		res.getWriter().print("<h1>Insufficient Balance</h1>");
		req.getRequestDispatcher("AccountHome.jsp").include(req, res);
	}else {
		if(amt>account.getAclimit()) {
			res.getWriter().print("<h1>Out of limit enteramount within "+account.getAclimit()+"</h1>");
			req.getRequestDispatcher("AccountHome.jsp").include(req, res);
		}else {
		account.setAmount(account.getAmount()-amt);
		BankTransaction bankTransaction=new BankTransaction();
		bankTransaction.setDeposit(0);
		bankTransaction.setWithdraw(amt);
		bankTransaction.setBalance(account.getAmount());
		bankTransaction.setDatetime(LocalDateTime.now());
		
		List<BankTransaction> list=account.getTransactions();
		list.add(bankTransaction);
		
		account.setTransactions(list);
		bankDao.update(account);
		
		res.getWriter().print("<h1>Amount Withdrawn Successfully</h1>");
		req.getRequestDispatcher("AccountHome.jsp").include(req, res);
	}
}
}
}