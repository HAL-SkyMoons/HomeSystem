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
	<link rel="stylesheet" type="text/css" href="/HomeSystem/SystemAdmin/css/base.css">
	<link rel="stylesheet" type="text/css" href="/HomeSystem/SystemAdmin/css/error/error.css">
	<title>システムエラー</title>
</head>

<body>
	<div id="mainBox">
		<h1>システムエラー</h1>
		<p>処理中にエラーが発生した為、機能が利用できません。<br />
		再度確認の上、エラー画面が表示される場合はシステム管理者に連絡してください。</p>
		<a href="<%= url %>"><div id ="btn">戻る</div></a>
	</div>
</body>
</html>