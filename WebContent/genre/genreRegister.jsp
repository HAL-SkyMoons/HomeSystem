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
<form action="/HomeSystem/fc/genreRegister" method="post">
<label>ジャンル名：<input type="text" name="genreName"></label>
<label>親ジャンル：
	<select name="bigGenreId">
		<c:forEach var="bigGenre" items="${bigGenreList}">
			<option value="${bigGenre.bigGenreId}">${bigGenre.bigGenreName}</option>
		</c:forEach>
	</select>
</label>
<input type="submit" name="genreRegister" value="登録">
</form>
</body>
</html>