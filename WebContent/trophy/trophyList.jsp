<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	img {
		width: 100px;
		height: 100px;
	}
</style>
</head>
<body>
	<jsp:useBean id="date" class="java.util.Date" />
	<fmt:formatDate value="${date}" pattern="yyyyMMddHHmmss" var="time" />
	<a href="/HomeSystem/fc/trophyRegister"><input type="submit" value="追加"></a>
	<table>
		<c:forEach var="trophy" items="${trophyList}">
			<tr>
				<td><img src="../images/trophy/${trophy.trophyId}.png?${time}"></td>
				<td>${trophy.trophyName}</td>
				<td>${trophy.trophyComment}</td>
				<td>
					<form action="/HomeSystem/fc/trophyDetail" method="post">
						<input type="hidden" name="trophyId" value="${trophy.trophyId}">
						<input type="submit" name="detail" value="詳細">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>