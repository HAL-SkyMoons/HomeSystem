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
		<a href="/HomeSystem/fc/PlanCalendar">企画カレンダー</a>
		<a href="/HomeSystem/fc/contents/list">ホメホメコンテンツ（過去事例）一覧・検索</a>
		<a href="/HomeSystem/fc/contents/search">ホメホメコンテンツ（過去事例）詳細検索</a>
		<a href="/HomeSystem/fc/contents/regist">ホメホメコンテンツ（過去事例）登録</a>
		<a href="/HomeSystem/fc/contents/detail?homeContentId=1">ホメホメコンテンツ（過去事例）詳細</a>
		<a href="/HomeSystem/fc/contents/edit?homeContentId=1">ホメホメコンテンツ（過去事例）編集</a>
	</body>
</html>
