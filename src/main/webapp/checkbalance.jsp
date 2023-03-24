<%@page import="dto.Customer"%>
<%@page import="dto.BankAccount"%>
<%@page import="dao.BankDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% long acno=(long) session.getAttribute("acno");
BankDao bankDao=new BankDao();
BankAccount account=bankDao.find(acno);
Customer customer=account.getCustomer();
%>
<h1>Hello <%if(customer.getGender().equals("Male")){ %>Mr.<%}else{ %>Ms.<%} %><%=customer.getName() %></h1>
<h1>Your <%=account.getType() %>account balance is<%=account.getAmount() %></h1>
</body>
</html>