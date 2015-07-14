<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@page import="jp.ac.hal.skymoons.beans.customer.CustomerUsersBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// セッションチェック
	SessionController sessionController = new SessionController(request);
	if(sessionController.checkAdministratorSession2() == false) {
		// セッションが無効
		response.sendRedirect(sessionController.getRedirectSessionErrorPageUrl());
		return;
	}

	// セッションチェック
	CustomerUsersBean record = (CustomerUsersBean)session.getAttribute("CustomerAddValue");
	if(record == null) {
		// セッションが無効
		response.sendRedirect("/HomeSystem/fc/customer/add");
		return;
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>確認</title>
	</head>
	<body>
		<h1>確認</h1>
		
		<p>企業名=><%= record.getCustomer_company() %></p>
		<p>姓=><%= record.getLast_name() %></p>
		<p>名=><%= record.getFirst_name() %></p>
		<p>ログインID=><%= record.getUser_id() %></p>
		<p>パスワード=>
<%
	for(int i = 0; i < record.getPassword().length(); i++) {
		out.print("*");
	}
%>
		</p>
		
		<h2><a href="/HomeSystem/fc/customer/add">戻る</a></h2>
		<h2><a href="/HomeSystem/fc/customer/insert">登録</a></h2>
	</body>
</html>