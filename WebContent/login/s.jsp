<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
/**
 * 社員用ログインページ。
 * @author YAMAZAKI GEN
 * @since 2015/05/22
 * @version 1.0
**/
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>社員用ログイン</title>
	</head>
	<body>
		<h1>社員用ログインページ</h1>
		<form action="/HomeSystem/fc/login/administrator" method="POST">
			<p>ログイン認証に失敗しました。</p>
			<p>LoginID:<input type="text" name="id"></p>
			<p>Password:<input type="password" name="pass"></p>
			<p><input type="submit" name="submit" value="Login"></p>
		</form>
	</body>
</html>