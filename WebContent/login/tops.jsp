<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
/**
 * 社員用トップページ
 * @author YAMAZAKI GEN
 * @since 2015/06/02
 * @version 1.0
**/

SessionController sessionController = new SessionController(request);
String url = sessionController.checkStaffSession();
if(url != null) {
	response.sendRedirect(url);
}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>社員用トップページ</title>
	</head>
	<body>
		<h1>社員用トップページ</h1>
		<p>ログイン成功</p>
		<p>セッション確認実行済み</p>
	</body>
</html>