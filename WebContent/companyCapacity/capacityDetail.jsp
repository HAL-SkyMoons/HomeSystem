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
	<table>
		<tr>
			<td>画像</td>
			<td><img src="../images/companyCapacity/${capacity.capacityId}.png?${time}"></td>
		</tr>
		<tr>
			<td>名前</td>
			<td>${capacity.capacityName}</td>
		</tr>
		<tr>
			<td>説明</td>
			<td>${capacity.capacityComment}</td>
		</tr>
		<c:forEach var="detail" items="${detail}">
			<tr>
				<td><img src="../images/batch/${detail.batchId}.png?${time}"></td>
				<td>${detail.batchName}</td>
				<td>${detail.typeCount}</td>
			</tr>
		</c:forEach>
	</table>
	<form action="/HomeSystem/fc/companyCapacityChange" method="post">
		<input type="hidden" name="capacityId" value="${capacity.capacityId}">
		<input type="submit" name="change" value="変更">
	</form>
</body>
</html>