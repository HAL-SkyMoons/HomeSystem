<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table>
		<c:forEach var="genre" items="${genreList}">
			<tr>
				<td>${genre.genreName}</td>
				<td>${genre.bigGenreName}</td>
				<td><form action="/HomeSystem/fc/genreChange" method="post">
						<input type="submit" name="change" value="編集">
						<input type="hidden" name="genreId" value="${genre.genreId}">
					</form></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>