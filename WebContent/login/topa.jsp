<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	SessionController sessionController = new SessionController(request);
	String url = sessionController.checkAdministratorSession();
	if(url != null) {
		response.sendRedirect("/HomeSystem" + url);
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>管理者用トップページ</title>
	</head>
	<body>
		<h1>管理者用トップページ</h1>
		<p>ログイン成功</p>
		<p>セッション確認実行済み</p>
		<hr>
		<a href="/HomeSystem/fc/staf/list"><p>社員ユーザ管理</p></a>
		<a href="/HomeSystem/fc/customer/list"><p>顧客ユーザ管理</p></a>
		<a href="/HomeSystem/fc/administrator"><p>管理者ユーザ管理</p></a>
	</body>
</html>