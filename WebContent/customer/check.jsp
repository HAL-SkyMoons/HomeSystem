<%@page import="java.util.HashMap"%>
<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%!
	@SuppressWarnings("unchecked")
	HashMap<String, String> getValue(HttpSession session) {
		return (HashMap<String, String>)session.getAttribute("CustomerAddValue");
	}
%>
<%
	HashMap<String, String> value = null;
	if(session.getAttribute("CustomerAddValue") != null) {
		value = getValue(session);
	} else {
		response.sendRedirect("/HomeSystem/fc/customer/add");
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>確認</title>
	</head>
	<body>
		<h1>確認</h1>
		
		<p>企業名=><%= value.get("company").toString() %></p>
		<p>姓=><%= value.get("lastname").toString() %></p>
		<p>名=><%= value.get("firstname").toString() %></p>
		<p>ログインID=><%= value.get("id").toString() %></p>
		<p>パスワード=>
<%
	for(int i = 0; i < value.get("password").toString().length(); i++) {
		out.print("*");
	}
%>
		</p>
		
		<h2><a href="/HomeSystem/fc/customer/add">戻る</a></h2>
		<h2><a href="/HomeSystem/fc/customer/insert">登録</a></h2>
	</body>
</html>