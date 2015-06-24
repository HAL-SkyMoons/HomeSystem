<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	SessionController sessionController = new SessionController(request);
	String url = sessionController.checkUserSession();
	if(url != null) {
		response.sendRedirect(url);
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>コンテンツ検索</title>
	</head>
	<body>
		<h1>検索条件の指定</h1>
		<form action="../../fc/contents/list" method="post">
			<table border="1">
				<tr>
					<td>記事名検索ワード</td>
					<td>記事内容検索ワード</td>
					<td>ジャンル選択</td>
					<td>投稿社員</td>
				</tr>
				<tr>
					<td>キーワード：<input type="text" name="titleKeyword"/></td>
					<td>キーワード：<input type="text" name="commentKeyword"/></td>
					<td>ジャンル<br/>
						<c:forEach items="${genreList}" var="j">
							<input type="checkbox" name="genreId" value="${j.genreId}">${j.bigGenreName}-${j.genreName}<br/>
						</c:forEach>
					</td>
					<td>社員
						<select name="employeeId">
							<option value="">指定なし</option>
							<c:forEach items="${employeeList}" var="j">
								<option value="${j.employeeId}">${j.firstName}${j.lastName}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>順序
						<input type="radio" name="orderColumn" value="home_content_id" checked/>作成順
						<input type="radio" name="orderColumn" value="home_content_datetime"/>日付順
						<input type="radio" name="orderColumn" value="home_content_title"/>タイトル順
						<input type="radio" name="orderColumn" value="employee_id"/>社員順
					</td>
					<td>
						<input type="radio" name="orderMode" value="asc" checked/>昇順
						<input type="radio" name="orderMode" value="desc"/>降順
					</td>
					<td><input type="submit" value="検索"></td>
				</tr>
			</table>
		</form>
	</body>
</html>