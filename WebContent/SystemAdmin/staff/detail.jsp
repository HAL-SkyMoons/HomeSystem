<%@page import="jp.ac.hal.skymoons.systemadmin.beans.StaffBean"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String message = null;
	StaffBean result = null;
	if(request.getAttribute("message") != null) {
		message = (String)request.getAttribute("message");
	} else {
		result = (StaffBean)request.getAttribute("result");
	}
%>
<!DOCTYPE>
<html lang="ja">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>社員ユーザ詳細</title>
</head>

<body>
	<h1>社員ユーザ詳細</h1>
	
	<p><a href="/HomeSystem/fc/SystemAdmin/menu">メニュー</a><p>
	<p><a href="/HomeSystem/fc/SystemAdmin/staff/list">社員ユーザ一覧</a></p>
	<p><a href="/HomeSystem/fc/SystemAdmin/staff/edit">編集</a></p>
	<p><a href="/HomeSystem/fc/SystemAdmin/staff/delete">削除</a></p>
	<p><a href="/HomeSystem/fc/SystemAdmin/logout">ログアウト</a></p>

<%
	if(message != null) {
		out.println("<p>" + message + "</p>");
		out.println("<p><a href='/HomeSystem/fc/SystemAdmin/administrator/list'>戻る</a></p>");
	} else {
		out.println("<p>姓:" + result.getLast_name() + "</p>");
		out.println("<p>名:" + result.getFirst_name() + "</p>");
		out.println("<p>姓(ふりがな):" + result.getLast_name_kana() + "</p>");
		out.println("<p>名(ふりがな):" + result.getFirst_name_kana() + "</p>");
		out.println("<p>所属部署:" + result.getDepartment_name() + "</p>");
		out.println("<p>ID:" + result.getUser_id() + "</p>");
		out.println("<p>パスワード:" + result.getPassword() + "</p>");
	}
%>

</body>
</html>