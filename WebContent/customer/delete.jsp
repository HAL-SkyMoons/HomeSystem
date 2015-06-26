<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>削除</title>
	</head>
	<body>
		<h1>削除</h1>
		<p>本当に削除しますか？</p>
		<p>※削除したデータは復元できません。</p>
		<p>
			<form action="/HomeSystem/fc/customer/detail" method="POST">
				<input type="hidden" name="id" value="<%= request.getParameter("id").toString() %>">
				<input type="submit" name="submit" value="いいえ">
			</form>
		</p>
		<p>
			<form action="/HomeSystem/fc/customer/deletecustomer" method="POST">
				<input type="hidden" name="id" value="<%= request.getParameter("id").toString() %>">
				<input type="submit" value="はい">
			</form>
		</p>
	</body>
</html>