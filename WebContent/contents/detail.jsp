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
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>コンテンツ詳細</title>
	</head>
	<c:if test="${scriptMessage != null}" >${scriptMessage}</c:if>
	<body>
		<c:set var="i" value="${detailList}"/>
		<c:if test="${i.deleteFlag == '1'}" ><h2>データが既に削除されています。</h2></c:if>
		<c:if test="${i.deleteFlag != '1'}" >
			<table border="1">
				<tr>
					<td colspan="2">
						<c:if test="${i.endDate == null && i.employeeId == i.userId}" >
							<form action="./edit" method="post">
								<input type="hidden" name="homeContentId" value="${i.homeContentId}"/>
								<input type="submit" value="編集"/>
							</form>
						</c:if>
						<c:if test="${i.endDate != null || i.employeeId != i.userId}" >
							<c:out value="編集不可" />
						</c:if>
					</td>
				</tr>
				<tr>
					<td>コンテンツ名：</td>
					<td>${i.homeContentTitle}</td>
				</tr>
				<tr>
					<td>実施日時：</td>
					<td>${i.homeContentDatetime}</td>
				</tr>
				<tr>
					<td>終了日：</td>
					<td>
						<c:if test="${i.endDate != null}" >${i.endDate}</c:if>
						<c:if test="${i.endDate == null}" >未定</c:if>
					</td>
				</tr>
				<tr>
					<td>投稿者名：</td>
					<td><a href="./list?employeeId=${i.employeeId}">${i.firstName}${i.lastName}</a></td>
				</tr>
				<tr>
					<td>内容：<br/>
					<td>
						${i.homeContentComment}
					</td>
				</tr>
				<tr>
					<td>大ジャンル：</td>
					<td>
						<c:forEach items="${i.bigGenreName}" var="bigGenreName">
							<c:out value="${bigGenreName}"/>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td>ジャンル：</td>
					<td>
						<c:set var="cnt" value="0"/>
						<c:forEach items="${i.genreName}" var="genreName">
							<a href="./list?genreId=${i.genreId[cnt]}"><c:out value="${genreName}"/></a>
							<c:set var="cnt" value="${cnt + 1}"/>
						</c:forEach>
					</td>
				</tr>
			</table>
			
			<table>
				<tr>
					<th>No</th>
					<th>画像</th>
					<th>ファイル名</th>
					<th>ダウンロード</th>
				</tr>
				<c:forEach items="${dataList}" var="j">
					<tr>
						<td>${j.homeDataNo}</td>
						<td><img src="${j.fileImagePath}" width="50" height="50"></td>
						<td>${j.homeDataName}</td>
						<td>
							<form action="/HomeSystem/fc/contents/detail" method="post">
								<input type="hidden" name="homeContentId" value="${j.homeContentId}">
								<input type="hidden" name="path" value="../files/contents/master/${j.homeContentId}/${j.homeDataNo}/${j.homeDataName}" />
								<input type="hidden" name="fileName" value="${j.homeDataName}"/>
								<input type="submit" name="download" value="ダウンロード">
							</form>
						</td>
					</tr>
				</c:forEach>
			</table>
			
			<c:forEach items="${homeLogList}" var="homeLog">
				<table border="1">
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
				</table>
			</c:forEach>
		</c:if>
	</body>
</html>