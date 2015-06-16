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
			<c:forEach items="${contentsList}" var="i">
			<tr>
				<td>
					<a href="./detail?homeContentId=${i.homeContentId}">
						コンテンツ名：${i.homeContentTitle}
					</a>
				</td>
				<td>日時：${i.homeContentDatetime}</td>
				<td>投稿者名：${i.firstName}${i.lastName}</td>
				<td>大ジャンル：
					<c:forEach items="${i.bigGenreName}" var="bigGenreName">
						<c:out value="${bigGenreName}"/>
					</c:forEach>
				</td>
				<td>ジャンル：
					<c:forEach items="${i.genreName}" var="genreName">
						<c:out value="${genreName}"/>
					</c:forEach>
				</td>
			</tr>
			</c:forEach>
		</table>
	</body>
</html>