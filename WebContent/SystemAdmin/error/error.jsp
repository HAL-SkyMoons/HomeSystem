<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String url = "/HomeSystem/fc/SystemAdmin/login";
	if(request.getAttribute("url") != null) {
		url = request.getAttribute("url").toString();
	}
%>
<!DOCTYPE">
<html lang="ja">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>システムエラー</title>
</head>

<body>
	<h1>システムエラー</h1>
	<p>処理中にエラーが発生した為、機能が利用できません。<br />
	再度確認の上、エラー画面が表示される場合はシステム管理者に連絡してください。</p>
	<p><a href="<%= url %>">戻る</a></p>
</body>
</html>