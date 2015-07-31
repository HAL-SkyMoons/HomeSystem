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
	<form action="/HomeSystem/fc/companyCapacityChange" method="post" enctype="multipart/form-data">
		<input type="hidden" name="capacityId" value="${capacity.capacityId}">
		<table id="forms">
			<tr>
				<td>社内資格名：</td>
				<td><input type="text" name="capacityName" value="${capacity.capacityName}"></td>
			</tr>
			<tr>
				<td>社内資格説明：</td>
				<td><textarea name="capacityComment">${capacity.capacityComment}</textarea></td>
			</tr>
			<tr>
				<td><input type="file" name="image" id="image" accept="image/*" /></td>
			</tr>
			<c:set var="index" value="0" />
			<c:forEach var="detail" items="${detail}">
				<tr id="form_${index}">
					<td>条件</td>
					<td><select id="batchId_${index}" name="batchId_${index}">
							<c:forEach var="batch" items="${batchList}">
								<c:if test="${detail.batchId == batch.batchId}">
									<option value="${batch.batchId}" selected="selected">${batch.batchName}</option>
								</c:if>
								<c:if test="${detail.batchId != batch.batchId}">
									<option value="${batch.batchId}">${batch.batchName}</option>
								</c:if>
							</c:forEach>
						</select></td>
					<td><input type="number" min="1" max="2147483647" value="${detail.typeCount}" name="count_${index}" id="count_${index}"></td>
					<c:if test="${index == 0}">
						<td><button type="button" class="btn_del" value="0" style="visibility: hidden">削除</button></td>
					</c:if>
					<c:if test="${index != 0}">
						<td><button type="button" class="btn_del" value="${index}" style="visibility: visible;">削除</button></td>
					</c:if>

				</tr>
				<c:set var="index" value="${index-1}" />

			</c:forEach>
		</table>
		<button type="button" id="btn_add" value="">条件追加</button>
		<br>
		<input type="submit" name="capacityChange" value="登録">
	</form>


</body>
</html>