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
	<table>
		<c:forEach var="bigGenre" items="${bigGenreList}">
			<tr>
				<td>${bigGenre.bigGenreName}</td>
				<td><form action="/HomeSystem/fc/bigGenreChange" method="post">
					<input type="submit" name="change" value="編集">
					<input type="hidden" name="bigGenreId" value="${bigGenre.bigGenreId}">
				</form></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>