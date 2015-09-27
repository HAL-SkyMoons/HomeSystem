<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE>
<html lang="ja">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>ホメシステム管理者ログイン</title>
</head>

<body>
	<h1>ホメシステム管理者ログイン</h1>
		<form action="/HomeSystem/fc/SystemAdmin/login" method="post">
		<p>・ログインID</p>
		<p><input class="Input1" type="text" name="id" maxlength="21" placeholder="ログインIDを入力してください"></p>
		<p>・パスワード</p>
		<p><input class="Input1" type="password" name="password" maxlength="21" placeholder="パスワードを入力してください"></p>
		<p><input id="SubmitBtn" type="submit" name="submit" value="ログイン"></p>
		</form>
		
		<%
			if(request.getAttribute("message") != null) {
				out.println("<p>" + request.getAttribute("message").toString() + "</p>");
			} else {
				out.println("<p>ログインしてください。</p>");
			}
		%>
		
</body>
</html>