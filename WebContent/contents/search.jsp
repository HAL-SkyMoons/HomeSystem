<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
					<td><input type="radio" name="searchMode" value="homeContentTitle" checked/>記事名</td>
					<td><input type="radio" name="searchMode" value="genreId"/>ジャンル選択</td>
					<td><input type="radio" name="searchMode" value="employeeId"/>投稿社員</td>
					<td><input type="radio" name="searchMode" value="all"/>全件検索</td>
				</tr>
				<tr>
					<td>キーワード：<input type="text" name="keyword"/></td>
					<td>ジャンル<br/>
						<c:forEach items="${genreList}" var="j">
							<input type="checkbox" name="genreId" value="${j.genreId}">${j.bigGenreName}-${j.genreName}<br/>
						</c:forEach>
					</td>
					<td>社員
						<select name="employeeId">
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