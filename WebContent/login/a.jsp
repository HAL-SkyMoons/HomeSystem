<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>管理者用ログイン</title>
	</head>
	<body>
		<h1>管理者用ログインページ</h1>
		<form action="/HomeSystem/fc/login/administrator" method="POST">
<%
	if(request.getAttribute("message") != null) {
		out.println("<p>" + request.getAttribute("message") + "</p>");
	}
%>
			<p>LoginID:<input type="text" name="id" size="24"></p>
			<p>Password:<input type="password" name="pass" size="24"></p>
			<p><input type="submit" name="submit" value="Login"></p>
		</form>
	</body>
</html>