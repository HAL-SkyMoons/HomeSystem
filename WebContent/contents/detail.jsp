<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>コンテンツ詳細</title>
	</head>
	<c:if test="${scriptMessage != null}" >${scriptMessage}</c:if>
	<body>
		<table border="1">
			<c:set var="i" value="${detailList}"/>
			<tr>
				<td>
					<form action="./edit?homeContentId=${i.homeContentId}" method="post">
						<input type="submit" value="編集">
					</form>
				</td>
				<td>コンテンツ名：${i.homeContentTitle}</td>
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
				<td>コンテンツ内容：${i.homeContentComment}</td>
				<td>添付資料：
					<c:set var="homeSourceIndex" value="0"/>
					<c:forEach items="${i.homeSourceNo}" var="homeSourceNo">
						<c:forEach items="${i.homeSourceName}" begin="${homeSourceIndex}" end="${homeSourceIndex}" var="homeSourceName">
							<a href='../../files/master/${homeSourceNo}'>${homeSourceName}</a>
						</c:forEach>
						<c:set var="homeSourceIndex" value="${homeSourceIndex + 1}"/>
					</c:forEach>
				</td>
			</tr>
		</table>
		<table border="1">
			<c:forEach items="${homeLogList}" var="homeLog">
				<tr>
					<td>ほめられユーザー：
						<a href="">${homeLog.homeTargetFirstName}${homeLog.homeTargetLastName}</a>
					</td>
					<td>ほめユーザー：
						<a href="">${homeLog.homeUserFirstName}${homeLog.homeUserLastName}</a>
					</td>
					<td>ほめられ日時：${homeLog.homeDatetime}</td>
					<td>バッジ：${homeLog.batchId}</td>
					<td>付与ポイント：${homeLog.homePoint}</td>
					<td>ほめコメント${homeLog.homeComment}</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>