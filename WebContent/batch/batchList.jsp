<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<a href="/HomeSystem/fc/batchRegister"><input type="submit" value="追加"></a>
	<table>
		<c:forEach var="batch" items="${batchList}">
			<tr>
				<td><img src="../images/batch/${batch.batchId}.png"></td>
				<td>${batch.batchName}</td>
				<td>${batch.batchComment}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>