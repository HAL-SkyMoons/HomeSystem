<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	SessionController sessionController = new SessionController(request);
	String url = sessionController.checkUserSession();
	if (url != null) {
		response.sendRedirect("/HomeSystem" + url);
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/reset.css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/style.css">
<title>トップ</title>
</head>
<body>
	<header>
		<div id="logo">
			<a href="/HomeSystem/index.jsp"><img src="/HomeSystem/images/logo.png" /></a>
		</div>
		<div id="mypage">
			<form action="/HomeSystem/fc/EmployeeMyPage">
				<input type="submit" class="btn btn-2 btn-2c submit" value="マイページ">
			</form>
		</div>
		<div id="logout">
			<form action="/HomeSystem/fc/logout/user">
				<input type="submit" class="btn btn-2 btn-2c submit" value="ログアウト">
			</form>
		</div>
		<ul>

			<li class="menu1"><a href="/HomeSystem/fc/EmployeeList">社員</a></li>
			<li class="menu2"><a href="/HomeSystem/fc/PlanList">企画</a></li>
			<li class="menu2"><a href="/HomeSystem/fc/contents/list">ホメホメコンテンツ</a></li>
			<li class="menu3"><a href="/HomeSystem/fc/ranking/topnum">ランキング</a></li>

		</ul>
	</header>
</body>
</html>


