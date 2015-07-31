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
	<a href="/HomeSystem/fc/batchRegister"><input type="submit" value="追加"></a>
	<table>
		<c:forEach var="batch" items="${batchList}">
			<tr>
				<td><img src="../images/batch/${batch.batchId}.png?${time}"></td>
				<td>${batch.batchName}</td>
				<td>${batch.batchComment}</td>
				<td>
					<form action="/HomeSystem/fc/batchChange" method="post">
						<input type="submit" name="change" value="変更">
						<input type="hidden" name="batchId" value="${batch.batchId}">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>