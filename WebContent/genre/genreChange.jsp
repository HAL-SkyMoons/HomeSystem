<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
</head>
<body>
	<form action="/HomeSystem/fc/genreChange" method="post">
		<label>ジャンル名：<input type="text" name="genreName" value="${detail.genreName}">
		</label> <label>親ジャンル：
			<select name="bigGenreId">
				<c:forEach var="bigGenre" items="${bigGenreList}">
					<c:if test="${bigGenre.bigGenreId == detail.bigGenreId}">
						<option value="${bigGenre.bigGenreId}" selected="selected">${bigGenre.bigGenreName}</option>
					</c:if>
					<c:if test="${bigGenre.bigGenreId != detail.bigGenreId}">
						<option value="${bigGenre.bigGenreId}">${bigGenre.bigGenreName}</option>
					</c:if>
				</c:forEach>
			</select>
		</label>
		<input type="hidden" name="genreId" value="${detail.genreId}">
		<input type="submit" name="genreChange" value="登録">
	</form>
</body>
</html>