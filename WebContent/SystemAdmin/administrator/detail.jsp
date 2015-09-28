<%@page import="jp.ac.hal.skymoons.systemadmin.beans.AdministratorBean"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String message = null;
	AdministratorBean result = null;
	if(request.getAttribute("message") != null) {
		message = (String)request.getAttribute("message");
	} else {
		result = (AdministratorBean)request.getAttribute("result");
	}
%>
<!DOCTYPE>
<html lang="ja">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>管理者ユーザ詳細</title>
</head>

<body>
	<h1>管理者ユーザ詳細</h1>
	
	<p><a href="/HomeSystem/fc/SystemAdmin/menu">メニュー</a><p>
	<p><a href="/HomeSystem/fc/SystemAdmin/administrator/list">管理者ユーザ一覧</a></p>
	<p><a href="/HomeSystem/fc/SystemAdmin/administrator/edit">編集</a></p>
	<p><a href="/HomeSystem/fc/SystemAdmin/administrator/delete">削除</a></p>
	<p><a href="/HomeSystem/fc/SystemAdmin/logout">ログアウト</a></p>
	
<%
	if(message != null) {
		out.println("<p>" + message + "</p>");
		out.println("<p><a href='/HomeSystem/fc/SystemAdmin/administrator/list'>戻る</a></p>");
	} else {
		out.println("<p>姓:" + result.getLast_name() + "</p>");
		out.println("<p>名:" + result.getFirst_name() + "</p>");
		out.println("<p>姓(ふりがな):" + result.getHuri_last_name() + "</p>");
		out.println("<p>名(ふりがな):" + result.getHuri_first_name() + "</p>");
		out.println("<p>ID:" + result.getAdministrator_id() + "</p>");
		out.println("<p>パスワード:" + result.getPassword() + "</p>");
	}
%>

</body>
</html>