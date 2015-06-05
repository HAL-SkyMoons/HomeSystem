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
			<c:set var="i" value="${detailList}"/>
			<tr>
				<td>
					<form action="./edit?id=${i.homeContentId}" method="post">
						<input type="submit" value="編集">
					</form>
				</td>
				<td>コンテンツ名：${i.homeContentTitle}</td>
				<td>日時：${i.homeContentDatetime}</td>
				<td>投稿者名：${i.firstName}${i.lastName}</td>
				<td>大ジャンル：
					<c:forEach items="${i.bigGenreName}" var="j">
						<c:out value="j"/>
					</c:forEach>
				</td>
				<td>ジャンル：
					<c:forEach items="${i.genreName}" var="j">
						<c:out value="j"/>
					</c:forEach>
				</td>
				<td>コンテンツ内容：${i.homeContentComment}</td>
				<td>添付資料：
					<a href="${i.homeSourceNo}">${i.homeSourceName}</a>
				</td>
			</tr>
		</table>
	</body>
</html>