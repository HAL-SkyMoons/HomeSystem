<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="content-style-type" content="text/css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/reset.css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/style.css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/logincs.css">
<title>顧客・社員用ログインページ</title>
</head>
<body>
	<header>
		<div id="logo">
			<a href="/HomeSystem/login/cs.jsp"><img src="/HomeSystem/images/logo.png"></a>
		</div>
		<hr>
	</header>
	<div id="boxout">
		<div id="boxcomment">aaaaaaaaaaaaaaa</div>
		<div id="box">
			<h1>顧客・社員用ログイン</h1>
			<hr color="orange">
			<form action="/HomeSystem/fc/login/user" method="POST">
				<p>
					ログインID:<input type="text" name="id" size="24">
				</p>
				<p>
					パスワード:<input type="password" name="pass" size="24">
				</p>
				<%
					if (request.getAttribute("message") != null) {
						out.println("<p>" + request.getAttribute("message") + "</p>");
					}
				%>
				<p>
					<input type="submit" name="submit" value="ログイン" class="btn btn-2 btn-2c">
				</p>
			</form>
		</div>
	</div>

</body>
</html>