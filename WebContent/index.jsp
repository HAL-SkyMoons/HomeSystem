<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	SessionController sessionController = new SessionController(request);
	String url = sessionController.checkUserSession();
	if (url != null) {
		response.sendRedirect(url);
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="./css/reset.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="./css/bootstrap-theme.min.css">
<title>トップ</title>
</head>
<<<<<<< HEAD
<body>

	<a href="./Employee/EmployeeList.jsp">マイページ</a>
	<a href="./HomeSystem/fc/logout/user">ログアウト</a>


	<a href="./Employee/EmployeeList.jsp">社員一覧</a>
	<a href="/HomeSystem/fc/PlanList">企画一覧</a>
	<a href="/HomeSystem/fc/contents/list">ホメホメコンテンツ（過去事例）一覧・検索</a>
	<a href="/HomeSystem/fc/ranking/topnum">バッジ獲得数ランキング(顧客・社員・管理者)</a>
</body>
</html>
=======
<body>


	<a href="./Employee/EmployeeList.jsp">マイページ</a>
	<a href="./HomeSystem/fc/logout/user">ログアウト</a>


	<a href="./Employee/EmployeeList.jsp">社員一覧</a>
	<a href="/HomeSystem/fc/PlanList">企画一覧</a>
	<a href="/HomeSystem/fc/contents/list">ホメホメコンテンツ（過去事例）一覧・検索</a>
	<a href="/HomeSystem/fc/ranking/topnum">バッジ獲得数ランキング(顧客・社員・管理者)</a>
</body>
</html>
>>>>>>> branch 'miyamoto' of https://github.com/HAL-SkyMoons/HomeSystem.git
