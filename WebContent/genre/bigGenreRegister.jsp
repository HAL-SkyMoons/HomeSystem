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
<form action="/HomeSystem/fc/bigGenreRegister" method="post">
<label>大ジャンル名：<input type="text" name="bigGenreName"></label>
<input type="submit" name="bigGenreRegister" value="登録">
</form>
</body>
</html>