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
		<table border="1">
			<form action="../fcon/content/search" method="post">
				キーワード：<input type="text" name="keyword"/>
				<input type="radio" name="searchMode" value="all"/>全件検索
				<input type="radio" name="searchMode" value="homeContentTitle"/>記事名
				<input type="submit" value="文字列検索">
				
				順序
				<input type =>
				<input type="radio" name="searchMode" value="employeeId"/>
				<input type="radio" name="searchMode" value="homeContentTitle"/>
			
				ジャンル検索：
				<input type="radio" name="searchMode" value="genreId"/>
				
				<select name="bigGenreId">
					<c:set var="cnt" value="0"/>
					<c:forEach items="${bigGenreList}" var="i">
						<c:if test="${cnt == 0}">
							<option value="${i.bigGenreId}" selected>${i.bigGenreName}</option>
						</c:if>
						<c:if test="${!cnt == 0}" >
							<option value="${i.bigGenreId}">${i.bigGenreName}</option>
						</c:if>
						<c:set var="cnt" value="1"/>
					</c:forEach>
				</select>
				
				<td>ジャンル
					<c:forEach items="${genreList}" var="i">
						<input type="checkbox" name="genre" value="${j.genreId}">${j.genreName}<br/>
					</c:forEach>
				</td>
			</form>
		</table>
	</body>
</html>