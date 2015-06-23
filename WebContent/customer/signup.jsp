<%@page import="java.util.HashMap"%>
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
	}
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
	if(value != null) {
		if(value.get("company").equals("") == false) {
			out.print("value='" + value.get("company").toString() + "'");
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
	if(value != null) {
		if(value.get("lastname").equals("") == false) {
			out.print("value='" + value.get("lastname").toString() + "'");
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
	if(value != null) {
		if(value.get("firstname").equals("") == false) {
			out.print("value='" + value.get("firstname").toString() + "'");
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
	if(value != null) {
		if(value.get("id").equals("") == false) {
			out.print("value='" + value.get("id").toString() + "'");
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