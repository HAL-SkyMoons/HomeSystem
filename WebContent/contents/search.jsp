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
					<td>検索ワード</td>
					<td>ジャンル選択</td>
					<td>投稿社員</td>
				</tr>
				<tr>
					<td>キーワード：<input type="text" name="keyword"/></td>
					<td>ジャンル<br/>
						<c:forEach items="${genreList}" var="j">
							<input type="checkbox" name="genreId" value="${j.genreId}">${j.bigGenreName}-${j.genreName}<br/>
						</c:forEach>
					</td>
					<td>大ジャンル<br/>
						<c:forEach items="${bigGenreList}" var="j">
							<input type="checkbox" name="bigGenreId" value="${j.bigGenreId}">${j.bigGenreName}<br/>
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
					<td>並び替え
						<select name="orderColumn">
							<option value="hc.home_content_id asc" selected>作成新しい順</option>
							<option value="hc.home_content_id desc">作成古い順</option>
							<option value="hc.home_content_datetime asc">開始日時新しい順</option>
							<option value="hc.home_content_datetime desc">開始日時古い順</option>
							<option value="hc.end_date,asc">終了日新しい順</option>
							<option value="hc.end_date,desc">終了日古い順</option>
							<option value="hc.home_content_title asc">タイトル昇順</option>
							<option value="hc.home_content_title desc">タイトル降順</option>
							<option value="u.first_name, u.last_name asc">社員名昇順</option>
							<option value="u.first_name, u.last_name desc">社員名降順</option>
						</select>
					</td>
					<td>
						<input type="checkbox" name="contentEnded"/>終了したものも検索
					</td>
					<td><input type="submit" value="検索"></td>
				</tr>
			</table>
		</form>
	</body>
</html>