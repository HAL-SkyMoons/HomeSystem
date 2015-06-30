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
<c:if test="${scriptMessage != null}" >${scriptMessage}</c:if>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="../../css/reset.css">
		<link rel="stylesheet" type="text/css" href="../../css/bootstrap.css">
		<link rel="stylesheet" type="text/css" href="../../css/bootstrap-theme.min.css">
		<link rel="stylesheet" type="text/css" href="../../css/PlanDetail.css">
		<link rel="stylesheet" type="text/css" href="../../js/Magnific-Popup/magnific-popup.css">
		<link rel="stylesheet" type="text/css" href="../../js/colorbox/colorbox.css">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>コンテンツ登録</title>
	</head>
	<body>
		<form action="addition" method="post">
			<div id="wrapper">
				<div id="plan">
					<div id="planHeader">
						<div id="id"></div>
						<div id="planDate">
							開始日時：<br/>
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
						</div>
					</div>
					<div id="title">
						コンテンツタイトル：<input type="text" name="homeContentTitle">
					</div>
					<div id="planDetail">
						<div id="startEnd">
							実施期間：<br>
							～
						</div>
						<div id="planner">
							企画者
							<div id="img">
								<img src="../../images/employees/E0001.jpg" width="100" height="100">
							</div>
							きむらたくや
						</div>
					</div>
					<div id="planComment">
						コンテンツ内容：<br/>
						<textarea name="homeContentComment"></textarea>
					</div>
					<div id="genre">
						登録ジャンル<br>
						<c:forEach items="${genreList}" var="j">
							<c:set var="check" value="" />
							<c:forEach items="${i.genreId}" var="id">
								<c:if test="${j.genreId == id}" >
									<c:set var="check" value="checked" />
								</c:if>
							</c:forEach>
							<div class="genres">
								<input type="checkbox" name="genreId" value="${j.genreId}" ${check}>${j.bigGenreName}-${j.genreName}<br/>
							</div>
						</c:forEach>
					</div>
					<input type="submit" value="登録をする"　class="btn btn-2 btn-2c">
				</div>
			</div>
		</form>
	</body>
</html>