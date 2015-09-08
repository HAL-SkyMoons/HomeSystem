<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- ↓追記1 -->
<meta http-equiv="content-style-type" content="text/css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/reset.css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/style.css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/logincs.css">
<!-- ↑追記1 -->
<title>顧客・社員用ログインページ</title>
</head>
<body>
	<!-- ↓追記2 -->
	<header></header>
	<img src="../images/home2.png">
	<div id="box">
		<!-- ↑追記2 -->
		<h1>顧客・社員用ログインページ</h1>
		<form action="/HomeSystem/fc/login/user" method="POST">
			<%
				if (request.getAttribute("message") != null) {
					out.println("<p>" + request.getAttribute("message") + "</p>");
				}
			%>
			<p>
				LoginID:<input type="text" name="id" size="24">
			</p>
			<p>
				Password:<input type="password" name="pass" size="24">
			</p>
			<p>
				<input type="submit" name="submit" value="ログイン" class="btn btn-2 btn-2c">
			</p>

		</form>


	</div>

</body>
</html>