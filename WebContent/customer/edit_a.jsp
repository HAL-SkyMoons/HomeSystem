<%@page import="jp.ac.hal.skymoons.beans.customer.CustomerUsersBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	CustomerUsersBean record = (CustomerUsersBean)request.getAttribute("record");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>編集</title>
	</head>
	<body>
		<h1>編集</h1>
		
		<hr>
		
		<form action="/HomeSystem/fc/customer/detail" method="POST">
			<input type="hidden" name="id" value="<%= record.getUser_id() %>">
			<input type="submit" value="戻る">
		</form>
		
		<hr>

<%
	String message = (String)request.getAttribute("message");
	if(message != null) {
		out.println(message + "<hr>");
	}
%>

		<p>姓:<%= record.getLast_name() %>　　名:<%= record.getFirst_name() %></p>
		<form action="/HomeSystem/fc/customer/edit" method="POST">
			<table border="1">
				<tr>
					<th>姓</th>
					<td>全角<br />６文字以内<br />例:山田</td>
					<td><input type="text" name="lastname" maxlength="6"></td>
				</tr>
				<tr>
					<th>名</th>
					<td>全角<br />６文字以内<br />例:山田</td>
					<td><input type="text" name="firstname" maxlength="6"></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="<%= record.getUser_id() %>">
			<input type="submit" name="namebtn" value="変更">
		</form>
		
		<hr>
		
		<p>企業名:<%= record.getCustomer_company() %></p>
		<form action="/HomeSystem/fc/customer/edit" method="POST">
			<table border="1">
				<tr>
					<th>企業名</th>
					<td>全角・半角英数字<br />２０文字以内<br />例:株式会社ハルシステムズ</td>
					<td><input type="text" name="company" maxlength="20"></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="<%= record.getUser_id() %>">
			<input type="submit" name="companybtn" value="変更">
		</form>
		
		<hr>
		
		<p>パスワード:<%= record.getPassword() %></p>
		<form action="/HomeSystem/fc/customer/edit" method="POST">
			<table border="1">
				<tr>
					<th>パスワード</th>
					<td>半角英数字<br />８～２０文字内<br />例:a7DF9rEv
					</td>
					<td><input type="text" name="password" maxlength="20"></td>
				</tr>
				<tr>
					<th>パスワード(確認)</th>
					<td>半角英数字<br />８～２０文字内<br />例:a7DF9rEv
					</td>
					<td><input type="text" name="password2" maxlength="20"></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="<%= record.getUser_id() %>">
			<input type="submit" name="passwordbtn" value="変更">
		</form>
	</body>
</html>