<%@page import="jp.ac.hal.skymoons.beans.customer.CustomerUsersBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	CustomerUsersBean record = (CustomerUsersBean)session.getAttribute("CustomerAddValue");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>顧客登録</title>
	</head>
	<body>
		<h1>顧客登録</h1>
<%
	if(request.getAttribute("message") != null) {
		out.println(request.getAttribute("message").toString());
	}
%>
		<form action="/HomeSystem/fc/customer/add" method="POST">
			<table border="1">
				
				<tr>
					<th>企業名</th>
					<td>
						全角・半角英数字<br />
						２０文字以内<br />
						例:株式会社ハルシステムズ
					</td>
					<td>
						<input type="text" name="company" maxlength="20"
<%
	if(record != null) {
		if(record.getCustomer_company().equals("") == false) {
			out.print("value='" + record.getCustomer_company() + "'");
		}
	}
%>
						>
					</td>
				</tr>
				
				<tr>
					<th>姓</th>
					<td>
						全角<br />
						６文字以内<br />
						例:山田
					</td>
					<td>
						<input type="text" name="lastname" maxlength="6"
<%
	if(record != null) {
		if(record.getLast_name().equals("") == false) {
			out.print("value='" + record.getLast_name() + "'");
		}
	}
%>
						>
					</td>
				</tr>
				
				<tr>
					<th>名</th>
					<td>
						全角<br />
						６文字以内<br />
						例:太郎
					</td>
					<td>
						<input type="text" name="firstname" maxlength="6"
<%
	if(record != null) {
		if(record.getFirst_name().equals("") == false) {
			out.print("value='" + record.getFirst_name() + "'");
		}
	}
%>
						>
					</td>
				</tr>
				
				<tr>
					<th>ログインID</th>
					<td>
						半角英数字<br />
						８～２０文字内<br />
						例:a7DF9rEv
					</td>
					<td>
						<input type="text" name="id" maxlength="24"
<%
	if(record != null) {
		if(record.getUser_id().equals("") == false) {
			out.print("value='" + record.getUser_id() + "'");
		}
	}
%>
						>
					</td>
				</tr>
				
				<tr>
					<th>パスワード</th>
					<td>
						半角英数字<br />
						８～２０文字内<br />
						例:G43yu41L
					</td>
					<td><input type="password" name="password" maxlength="24"></td>
				</tr>
				
				<tr>
					<th>パスワード(確認)</th>
					<td>
						半角英数字<br />
						８～２０文字内<br />
						例:パスワードと同じ
					</td>
					<td><input type="password" name="password2" maxlength="24"></td>
				</tr>
				
			</table>
			<p><input type="submit" name="submit" value="確認"></p>		
		</form>
	</body>
</html>