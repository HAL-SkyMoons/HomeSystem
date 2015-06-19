<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	SessionController sessionController = new SessionController(request);
	String url = sessionController.checkUserSession();
	if(url != null) {
		response.sendRedirect(url);
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>顧客・社員用トップページ</title>
	</head>
	<body>
		<h1>顧客・社員用トップページ</h1>
		<p>ログイン成功</p>
		<p>セッション確認実行済み</p>
		<a href="../../pages/windowTest.html">ホメ</a>
		<a href="/HomeSystem/fc/PlanList">企画一覧</a>
		<a href="/HomeSystem/fc/PlanRegister">企画登録</a>
	</body>
</html>
