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
<script src="../js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="../js/uploadThumbnail.js" type="text/javascript"></script>
</head>
<body>
	<form action="/HomeSystem/fc/batchChange" method="post" enctype="multipart/form-data">
		<input type="hidden" name="batchId" value="${detail.batchId}"><br>
		<label>バッチ名：<input type="text" name="batchName" value="${detail.batchName }"></label><br>
		<label>バッチ説明：<textarea name="batchComment">${detail.batchComment}</textarea></label><br>
		<input type="file" name="image" id="image" accept="image/*" />
		<input type="submit" name="batchChange" value="登録">
	</form>
</body>
</html>