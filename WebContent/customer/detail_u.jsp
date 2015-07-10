<%@page import="jp.ac.hal.skymoons.beans.customer.CustomerUsersBean"%>
<%@page import="com.sun.jndi.toolkit.url.Uri"%>
<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	if(request.getAttribute("record") == null) {
		response.sendRedirect("/HomeSystem/error/system.jsp");
		return;
	}
	CustomerUsersBean record = (CustomerUsersBean)request.getAttribute("record");
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

		<table border="1">
			<tr>
				<th>企業名</th>
				<th>姓</th>
				<th>名</th>
			</tr>
			<tr>
				<td><%= record.getCustomer_company() %></td>
				<td><%= record.getLast_name() %></td>
				<td><%= record.getFirst_name() %></td>
			</tr>
		</table>
	</body>
</html>