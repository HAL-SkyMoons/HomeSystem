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
	<form action="/HomeSystem/fc/batchRegister" method="post" enctype="multipart/form-data">
		<label>バッチ名：<input type="text" name="batchName"></label><br>
		<label>バッチ説明：<textarea name="batchComment"></textarea></label><br>
		<input type="file" name="image" id="image" accept="image/png" />
		<input type="submit" name="batchRegister" value="登録">
	</form>
</body>
</html>