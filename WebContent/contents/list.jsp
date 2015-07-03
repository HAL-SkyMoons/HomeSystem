<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	SessionController sessionController = new SessionController(request);
	String url = sessionController.checkUserSession();
	if(url != null) {
		response.sendRedirect(url);
	}
%>
<!DOCTYPE html>
<c:if test="${scriptMessage != null}" >${scriptMessage}</c:if>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>コンテンツリスト</title>
	</head>
	<body>
		<table border="1">
			<tr>
				<form action="list" method="post">
					<td>文字列：<input type="text" name="titleKeyword"></td>
					<td><input type="submit" value="タイトル検索"></td>
				</form>
				<td>
					<a href="./search">検索条件指定画面へ</a>
				</td>
			</tr>
		</table>
		<table border="1">
			<c:forEach items="${contentsList}" var="i">
			<tr>
				<td>
					<a href="./detail?homeContentId=${i.homeContentId}">
						コンテンツ名：${i.homeContentTitle}
					</a>
				</td>
				<td>実施日時：${i.startDatetime}</td>
				<td>投稿者名：<a href="./list?employeeId=${i.employeeId}">${i.firstName}${i.lastName}</a></td>
				<td>大ジャンル：
					<c:set var="cnt" value="0"/>
					<c:forEach items="${i.bigGenreName}" var="bigGenreName">
						<a href="./list?bigGenreId=${i.bigGenreId[cnt]}"><c:out value="${bigGenreName}"/></a>
						<c:set var="cnt" value="${cnt + 1}"/>
					</c:forEach>
				</td>
				<td>ジャンル：
					<c:set var="cnt" value="0"/>
					<c:forEach items="${i.genreName}" var="genreName">
						<a href="./list?genreId=${i.genreId[cnt]}"><c:out value="${genreName}"/></a>
						<c:set var="cnt" value="${cnt + 1}"/>
					</c:forEach>
				</td>
			</tr>
			</c:forEach>
		</table>
	</body>
</html>