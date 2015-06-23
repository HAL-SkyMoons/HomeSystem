<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>コンテンツ編集</title>
	</head>
	<c:if test="${scriptMessage != null}" >${scriptMessage}</c:if>
	<body>
		<c:set var="i" value="${editData}"/>
		<form action="delete" method="post">
			<input type="hidden" name="homeContentId" value="${i.homeContentId}">
			<input type="submit" value="削除する"/>
		</form>
		<form action="update" method="post">
			<table border="1">
				<tr>
					<td>コンテンツ名：<input type="text" name="homeContentTitle" value="${i.homeContentTitle}"></td>
					<td>日時：${i.homeContentDatetime}<br/>
						<select name="contentsYear">
							<c:forEach begin="1950" end="2020" step="1" varStatus="status">
								<option value="${status.index}">${status.index}</option>
							</c:forEach>
						</select>
						年
						<select name="contentsMonth">
							<c:forEach begin="1" end="12" step="1" varStatus="status">
								<option value="${status.index}">${status.index}</option>
							</c:forEach>
						</select>
						月
						<select name="contentsDay">
							<c:forEach begin="1" end="31" step="1" varStatus="status">
								<option value="${status.index}">${status.index}</option>
							</c:forEach>
						</select>
						日
						<select name="contentsHour">
							<c:forEach begin="0" end="23" step="1" varStatus="status">
								<option value="${status.index}">${status.index}</option>
							</c:forEach>
						</select>
						時
						<select name="contentsMinute">
							<c:forEach begin="0" end="59" step="1" varStatus="status">
								<option value="${status.index}">${status.index}</option>
							</c:forEach>
						</select>
						分
					</td>
					<td>コンテンツ内容：<br/>
						<textarea name="homeContentComment">${i.homeContentComment}</textarea>
					</td>
					<td>ジャンル<br/>
						<c:forEach items="${genreList}" var="j">
							<c:set var="check" value="" />
							<c:forEach items="${i.genreId}" var="id">
								<c:if test="${j.genreId == id}" >
									<c:set var="check" value="checked" />
								</c:if>
							</c:forEach>
							<input type="checkbox" name="genreId" value="${j.genreId}" ${check}>${j.bigGenreName}-${j.genreName}<br/>
						</c:forEach>
					</td>

					<td>
						<input type="hidden" name="homeContentId" value="${i.homeContentId}">
						<input type="submit" value="編集完了">
					</td>
				</tr>
			</table>
			
		</form>
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
						<form action="/HomeSystem/fc/contents/edit?homeContentId=${i.homeContentId}" method="post">
							<input type="hidden" name="homeContentId" value="${j.homeContentId}">
							<input type="hidden" name="path" value="../files/contents/master/${j.homeContentId}/${j.homeDataNo}/${j.homeDataName}" />
							<input type="hidden" name="fileName" value="${j.homeDataName}"/>
							<input type="submit" name="download" value="ダウンロード">
						</form>
					</td>
					<td>
						<form action="/HomeSystem/fc/contents/edit?homeContentId=${i.homeContentId}" method="post">
							<input type="submit" src="../../images/icon/del.png" width="15" name="fileDelete" value="削除">
							<input type="hidden" name="homeContentId" value="${j.homeContentId}">
							<input type="hidden" name="homeDataNo" value="${j.homeDataNo}">
							<input type="hidden" name="fileName" value="${j.homeDataName}">
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
		<form method="POST" enctype="multipart/form-data" action="/HomeSystem/fc/contents/edit?homeContentId=${i.homeContentId}">
			<input type="hidden" name="homeContentId" value="${i.homeContentId}" />
			<input type="file" name="file" />
			<input type="submit" name="upload" value="送信"/>
		</form>
	</body>
</html>