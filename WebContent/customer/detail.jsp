<%@page import="com.sun.jndi.toolkit.url.Uri"%>
<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%!
	@SuppressWarnings("unchecked")
	private HashMap<String, String> getValue(HttpServletRequest request) {
		return (HashMap<String, String>)request.getAttribute("value");
	}
%>
<%
	SessionController sessionController = new SessionController(request);
	String url = sessionController.checkAdministratorSession();
	if(url != null) {
		response.sendRedirect("/SessionErrorPage");
		return;
	}
	if(request.getAttribute("value") == null) {
		response.sendRedirect("/HomeSystem/fc/customer/list");
		return;
	}
	HashMap<String, String> value = getValue(request);
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF=8">
		<title>顧客詳細</title>
	</head>
	<body>
		<h1>顧客詳細</h1>
		<h2><a href="/HomeSystem/fc/customer/list">戻る</a></h2>
		<h2>
			<form action="/HomeSystem/fc/customer/edit" method="POST">
				<input type="hidden" name="id" value="<%= value.get("id").toString() %>">
				<input type="submit" value="編集">
			</form>
		</h2>
		<h2>
			<form action="/HomeSystem/customer/delete.jsp" method="POST">
				<input type="hidden" name="id" value="<%= value.get("id").toString() %>">
				<input type="submit" value="削除">
			</form>
		</h2>
		<table border="1">
			<tr>
				<th>企業名</th>
				<th>姓</th>
				<th>名</th>
				<th>ログインID</th>
				<th>パスワード</th>
			</tr>
			<tr>
				<td><%= value.get("company").toString() %></td>
				<td><%= value.get("lastname").toString() %></td>
				<td><%= value.get("firstname").toString() %></td>
				<td><%= value.get("id").toString() %></td>
				<td><%= value.get("password").toString() %></td>
			</tr>
		</table>
	</body>
</html>