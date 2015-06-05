<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>コンテンツリスト</title>
	</head>
	<body>
		<table border="1">
			<c:forEach items="${contentList}" var="i">
				<tr>
					<td>コンテンツ名：${i.homeContentTitle}</td>
					<td>日時：${i.homeContentDatetime}</td>
					<td>投稿者名：${i.firstName}${i.lastName}</td>
					<td>${i.bigGenreName}</td>
					<td>${i.genreName}</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>