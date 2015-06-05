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
			<c:set var="i" value="${contentList}"/>
			<tr>
				<td>コンテンツ名：<input type="text" value="${i.homeContentTitle}"></td>
				<td>コンテンツ内容：<input type="text" value="${i.homeContent}"></td>
				<td>日時：${i.homeContentDatetime}</td>
				<td>コンテンツ内容：${i.homeContentComment}</td>
				<td>添付資料：<a href="${i.homeSource}">${i.homeSource}</a></td>
				<td>ジャンル
					<c:forEach items="${genreList}" var="i">
						<input type="checkbox" name="genre" value="${j.genreId}">${j.genreName}<br/>
					</c:forEach>
				</td>
				<td><input type="submit" value="編集完了"></td>
			</tr>
		</table>
	</body>
</html>