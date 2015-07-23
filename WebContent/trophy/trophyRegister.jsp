<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
<script src="../js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="../js/addForm.js" type="text/javascript"></script>
<script src="../js/uploadThumbnail.js" type="text/javascript"></script>
</head>
<body>
	<form action="/HomeSystem/fc/trophyRegister" method="post" enctype="multipart/form-data">
		<table id="forms">
			<tr><td>トロフィー名：</td><td><input type="text" name="trophyName"></td></tr>
			<tr><td>トロフィー説明：</td><td><textarea name="trophyComment"></textarea></td></tr>
			<tr><td><input type="file" name="image" id="image" accept="image/*" /></td></tr>


			<tr id="form_0">
				<td>条件</td>
				<td><select id="batchId_0" name="batchId_0">
						<c:forEach var="batch" items="${batchList}">
							<option value="${batch.batchId}">${batch.batchName}</option>
						</c:forEach>
					</select></td>
				<td><input type="number" min="1" max="2147483647" value="1" name="count_0" id="count_0"></td>
				<td><button type="button" id="btn_del" value="0" style="visibility:hidden">削除</button></td>
			</tr>
		</table>
		<button type="button" id="btn_add" value="">条件追加</button><br>
		<input type="submit" name="trophyRegister" value="登録">
	</form>


</body>
</html>