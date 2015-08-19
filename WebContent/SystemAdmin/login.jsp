<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE>
<html lang="ja">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="/HomeSystem/SystemAdmin/css/base.css">
	<link rel="stylesheet" type="text/css" href="/HomeSystem/SystemAdmin/css/login.css">
	<title>ホメシステム管理者ログイン</title>
</head>

<body>
	<div id="MainBox">
		<div id="TitleBox">
			<h1>ホメシステム管理者ログイン</h1>
		</div>
		<div id="LoginBox">
			<form action="/HomeSystem/fc/SystemAdmin/login" method="post">
				<div id="LoginBoxA1"><input class="InputA" type="text" name="id" maxlength="21" placeholder="ログインIDを入力してください"></div>
				<div id="LoginBoxA2"><input class="InputA" type="password" name="password" maxlength="21" placeholder="パスワードを入力してください"></div>
				<div id="LoginBoxA3">
					<div id="LoginBoxA3-1">
						<%
							if(request.getAttribute("message") != null) {
								out.println("<p class='ErrorText'>" + request.getAttribute("message").toString() + "</p>");
							} else {
								out.println("<p>ログインしてください。</p>");
							}
						%>
					</div>
					<div id="LoginBoxA3-2"><input id="SubmitBtn" type="submit" name="submit" value="ログイン"></div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>