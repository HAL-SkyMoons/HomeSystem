<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="jp.ac.hal.skymoons.util.Utility"%>
<%@ page import="jp.ac.hal.skymoons.beans.ContentsDataBean"%>
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
					<c:if test="${i.endDate == null}" >
						<form action="./edit?homeContentId=${i.homeContentId}" method="post">
							<input type="submit" value="編集">
						</form>
					</c:if>
					<c:if test="${i.endDate != null}" >
						<c:out value="編集不可" />
					</c:if>
				</td>
				<td>コンテンツ名：${i.homeContentTitle}</td>
				<td>実施日：${i.homeContentDatetime}</td>
				<td>終了日：
					<c:if test="${i.endDate != null}" >${i.endDate}</c:if>
					<c:if test="${i.endDate == null}" >未定</c:if>
				</td>	
				<td>投稿者名：${i.firstName}${i.lastName}</td>
				<td>大ジャンル：
					<c:forEach items="${i.bigGenreName}" var="bigGenreName">
						<c:out value="${bigGenreName}"/>
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
						<form action="/HomeSystem/fc/contents/detail?homeContentId=${i.homeContentId}" method="post">
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
	</body>
</html>