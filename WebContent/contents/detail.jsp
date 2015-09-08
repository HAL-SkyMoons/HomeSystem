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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:if test="${scriptMessage != null}" >${scriptMessage}</c:if>
<html>
	<script type="text/javascript" src="../../js/autosize/autosize.js"></script>
	<script type="text/javascript" src="../../js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../../js/colorbox/jquery.colorbox.js"></script>
	<script type="text/javascript">
		//　イメージポップアップ表示
		$(function() {
			$(".iframe").colorbox({
				iframe : true,
				width : "500px",
				height : "90%",
				opacity : 0.7
			});
		});
	</script>
	<head>
		<link rel="stylesheet" type="text/css" href="../../css/reset.css">
		<link rel="stylesheet" type="text/css" href="../../css/bootstrap.css">
		<link rel="stylesheet" type="text/css" href="../../css/bootstrap-theme.min.css">
		<link rel="stylesheet" type="text/css" href="../../css/ContentsCommon.css">
		<link rel="stylesheet" type="text/css" href="../../css/ContentsDetail.css">
		<link rel="stylesheet" type="text/css" href="../../js/Magnific-Popup/magnific-popup.css">
		<link rel="stylesheet" type="text/css" href="../../js/colorbox/colorbox.css">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>コンテンツ詳細</title>
	</head>
	<body>
		<c:set var="i" value="${detailList}"/>
		<div id="wrapper">
			<div id="contents">
				<c:if test="${i.endDatetime == '未完了' && i.employeeId == i.userId}" >
					<form action="./edit" method="post">
						<input type="hidden" name="homeContentId" value="${i.homeContentId}"/>
						<input type="submit" value="編集" class="btn btn-2 btn-2c edit"/>
					</form>
				</c:if>
				<div id="contentsHeader">
					<div id="id">
						No.${i.homeContentId}
					</div>
					<div id="planDate">
						企画日時：${i.planDatetime}
					</div>
				</div>
				<div id="title">
					<span id="titleValue"><c:out value="${i.homeContentTitle}"/></span>
				</div>
				<div id="contentsDetail">
					<div id="startEnd">
						実施期間：<br>
						${i.startDatetime}&nbsp;&nbsp;&sim;&nbsp;&nbsp;${i.endDatetime}
					</div>
					<div id="planner">
						投稿者
						<div id="img">
							<img src="../../images/employees/${i.employeeId}.jpg" alt="投稿者">
						</div>
						<c:set var="level" value="${i.level}"/>
						<c:if test="${i.level > 12}">
							<c:set var="level" value="12"/>
						</c:if>
						<div class="flameDiv">
							<img src="../../images/flame/${level}.png" class="employeeFlame">
						</div>
						${i.lastName}${i.firstName}<br/>
						<c:if test="${i.employeeId != i.userId}">
							<a class="iframe" href="/HomeSystem/fc/Home?toUser=${i.employeeId}&contentsId=${i.homeContentId}" ><input type="button" value="ホメる" class="btn btn-2 btn-2c"></a>
						</c:if>
					</div>
				</div>
				<div id="contentsComment">
					<c:out value="${i.homeContentComment}"/>
				</div>
				<div id="genre">
					登録ジャンル<br>
					<c:set var="cnt" value="0"/>
					<c:forEach items="${i.genreName}" var="genreName">
						<div class="genres"><a href="./list?genreId=${i.genreId[cnt]}"><c:out value="${genreName}"/></a></div>
						<c:set var="cnt" value="${cnt + 1}"/>
					</c:forEach>
				</div>
	
				<hr>
				<c:if test="${dataList != null}">
					<h3>添付ファイル</h3>
					<div id="files">
						<c:forEach items="${dataList}" var="j">
							<div class="file">
								<div vlass="fileImage">
									<img src="${j.fileImagePath}" width="50" height="50">
								</div>
								<div class="fileName">${j.homeDataName}</div>
								<form action="/HomeSystem/fc/contents/detail" method="post">
									<input type="hidden" name="homeContentId" value="${j.homeContentId}">
									<input type="hidden" name="path" value="../files/contents/master/${j.homeContentId}/${j.homeDataNo}/${j.homeDataName}" />
									<input type="hidden" name="fileName" value="${j.homeDataName}"/>
									<input type="submit" name="download" value="ダウンロード" class="btn btn-2 btn-2c download">
								</form>
							</div>
						</c:forEach>
					</div>
					<hr>
				</c:if>
				
				<div class="commentData">
					<c:set var="homeLogCount" value="1"/>
					<c:forEach items="${homeLogList}" var="homeLog">
						<div class="commentData">
							<c:if test="${i.employeeId == homeLog.homeUser}"><c:set var="fromUser" value="true"/></c:if>
							<c:if test="${i.employeeId != homeLog.homeUser}"><c:set var="fromUser" value="false"/></c:if>
							<c:if test="${fromUser}"><div class="planner"></c:if>
							<c:if test="${!fromUser}"><div class="gest"></c:if>
								<div class="face"><img src="../../images/employees/${homeLog.homeUser}.jpg"></div>
								<c:set var="level" value="${homeLog.level}"/>
								<c:if test="${homeLog.level > 12}">
									<c:set var="level" value="12"/>
								</c:if>
								<div class="flameFaceDiv">
									<img src="../../images/flame/${level}.png" class="employeeFlame">
								</div>
								<div class="name">${homeLog.homeUserLastName}${homeLog.homeUserFirstName}</div>
								<div class="home">
									<c:if test="${i.userId != homeLog.homeUser && i.employeeId != i.userId}">
										<a class="iframe" href="/HomeSystem/fc/Home?toUser=${homeLog.homeUser}&contentsId=${i.homeContentId}"><input type="button" value="ホメる" class="btn btn-2 btn-2c"></a>
									</c:if>
								</div>
								
							</div>
							<c:if test="${fromUser}"><div class="plannerComment"></c:if>
							<c:if test="${!fromUser}"><div class="gestComment"></c:if>
								<div class="commentHeader">
									<div class="commentNo">No.${homeLogCount}</div>
									<div id="comments"></div>
									<div class="commentBody">${homeLog.homeComment}</div>
									<div class="commentDate">${homeLog.homeDatetime}</div>
								</div>
								<div class="commenFootert">
									<div class="commentFile"></div>
								</div>
							</div>
						</div>
						<c:set var="homeLogCount" value="${homeLogCount + 1}"/>
					</c:forEach>
				</div>
			</div>
		</div>
	</body>
</html>