<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>コンテンツ登録</title>
	</head>
	<body>
		<form action="addition" method="post">
			<table border="1">
				<tr>
					<td>コンテンツ名：<input type="text"></td>
					<td>実施日時：<br/>
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
					<td>コンテンツタイトル：<input type="text" name="homeContentTitle"></td>
					<td>コンテンツ内容：<br/>
						<textarea name="homeContentComment"></textarea>
					</td>
					<td>添付資料：</td>
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
						<input type="submit" value="追加する">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>