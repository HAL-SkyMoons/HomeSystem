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
<script type="text/javascript" src="../../js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../../js/autosize/autosize.js"></script>
<script>
$(document).ready(function() {

	//Hide (Collapse) the toggle containers on load
	$(".toggle_container").hide();

	//Switch the "Open" and "Close" state per click then slide up/down (depending on open/close state)
	$(".trigger").click(function() {
		$(this).toggleClass("active").next().slideToggle("slow");
		return false; //Prevent the browser jump to the link anchor
	});

});
</script>
<c:if test="${scriptMessage != null}" >${scriptMessage}</c:if>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="../../css/reset.css">
		<link rel="stylesheet" type="text/css" href="../../css/bootstrap.css">
		<link rel="stylesheet" type="text/css" href="../../css/bootstrap-theme.min.css">
		<link rel="stylesheet" type="text/css" href="../../css/ContentsCommon.css">
		<link rel="stylesheet" type="text/css" href="../../css/ContentsEdit.css">
		<link rel="stylesheet" type="text/css" href="../../js/Magnific-Popup/magnific-popup.css">
		<link rel="stylesheet" type="text/css" href="../../js/colorbox/colorbox.css">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>コンテンツ編集</title>
	</head>
	<body>
		<c:set var="i" value="${editData}"/>
		<div id="wrapper">
			<form action="update" method="post">
				<input type="hidden" name="homeContentId" value="${i.homeContentId}">
				<div id="contents">
					<div id="contentsHeader">
						<div id="id">No.&nbsp;${i.homeContentId}</div>
						<div id="planDate">企画日時：${i.planDatetime}</div>
					</div>
					<div id="title">
						<textarea id="titleValue" rows="1" name="homeContentTitle" maxlength="100">${i.homeContentTitle}</textarea>
					</div>
					<div id="contentsDetail">
						<div id="startEnd">
							実施期間：<br/>
							<select name="startYear">
								<c:forEach begin="1950" end="2020" step="1" varStatus="status">
									<c:if test="${i.startYear == status.index}">
										<option value="${status.index}" selected>${status.index}</option>
									</c:if>
									<c:if test="${i.startYear != status.index}">
										<option value="${status.index}">${status.index}</option>
									</c:if>
								</c:forEach>
							</select>
							年
							<select name="startMonth">
								<c:forEach begin="1" end="12" step="1" varStatus="status">
									<c:if test="${i.startMonth == status.index}">
										<option value="${status.index}" selected>${status.index}</option>
									</c:if>
									<c:if test="${i.startMonth != status.index}">
										<option value="${status.index}">${status.index}</option>
									</c:if>
								</c:forEach>
							</select>
							月
							<select name="startDay">
								<c:forEach begin="1" end="31" step="1" varStatus="status">
									<c:if test="${i.startDay == status.index}">
										<option value="${status.index}" selected>${status.index}</option>
									</c:if>
									<c:if test="${i.startDay != status.index}">
										<option value="${status.index}">${status.index}</option>
									</c:if>
								</c:forEach>
							</select>
							日
							<select name="startHour">
								<c:forEach begin="0" end="23" step="1" varStatus="status">
									<c:if test="${i.startHour == status.index}">
										<option value="${status.index}" selected>${status.index}</option>
									</c:if>
									<c:if test="${i.startHour != status.index}">
										<option value="${status.index}">${status.index}</option>
									</c:if>
								</c:forEach>
							</select>
							時
							<select name="startMinute">
								<c:forEach begin="0" end="59" step="1" varStatus="status">
									<c:if test="${i.startMinute == status.index}">
										<option value="${status.index}" selected>${status.index}</option>
									</c:if>
									<c:if test="${i.startMinute != status.index}">
										<option value="${status.index}">${status.index}</option>
									</c:if>
								</c:forEach>
							</select>
							分
							<div class="centre">&#x7C;</div>
							<select name="endYear">
								<c:forEach begin="1950" end="2020" step="1" varStatus="status">
									<c:if test="${i.endYear == status.index}">
										<option value="${status.index}" selected>${status.index}</option>
									</c:if>
									<c:if test="${i.endYear != status.index}">
										<option value="${status.index}">${status.index}</option>
									</c:if>
								</c:forEach>
							</select>
							年
							<select name="endMonth">
								<c:forEach begin="1" end="12" step="1" varStatus="status">
									<c:if test="${i.endMonth == status.index}">
										<option value="${status.index}" selected>${status.index}</option>
									</c:if>
									<c:if test="${i.endMonth != status.index}">
										<option value="${status.index}">${status.index}</option>
									</c:if>
								</c:forEach>
							</select>
							月
							<select name="endDay">
								<c:forEach begin="1" end="31" step="1" varStatus="status">
									<c:if test="${i.endDay == status.index}">
										<option value="${status.index}" selected>${status.index}</option>
									</c:if>
									<c:if test="${i.endDay != status.index}">
										<option value="${status.index}">${status.index}</option>
									</c:if>
								</c:forEach>
							</select>
							日
							<select name="endHour">
								<c:forEach begin="0" end="23" step="1" varStatus="status">
									<c:if test="${i.endHour == status.index}">
										<option value="${status.index}" selected>${status.index}</option>
									</c:if>
									<c:if test="${i.endHour != status.index}">
										<option value="${status.index}">${status.index}</option>
									</c:if>
								</c:forEach>
							</select>
							時
							<select name="endMinute">
								<c:forEach begin="0" end="59" step="1" varStatus="status">
									<c:if test="${i.endMinute == status.index}">
										<option value="${status.index}" selected>${status.index}</option>
									</c:if>
									<c:if test="${i.endMinute != status.index}">
										<option value="${status.index}">${status.index}</option>
									</c:if>
								</c:forEach>
							</select>
							分<br/>
							<input type="checkbox" name="addEndDatetime" value="true"/>終了日を登録する
						</div>
						<div id="planner">
							投稿者
							<div id="img">
								<img src="../../images/employees/${i.employeeId}.jpg" alt="投稿者">
							</div>
							<c:if test="${i.level != null}">
								<c:set var="level" value="${i.level}"/>
								<c:if test="${i.level > 12}">
									<c:set var="level" value="12"/>
								</c:if>
								<div class="flameDiv">
									<img src="../../images/flame/${level}.png" class="employeeFlame">
								</div>
							</c:if>
							${i.lastName}&nbsp;${i.firstName}
						</div>
					</div>
					<div id="contentsComment">
						<textarea id="contentsCommentValue" rows="1" placeholder="コンテンツ内容" name="homeContentComment">${i.homeContentComment}</textarea>
					</div>
					<c:forEach var="bigGenre" items="${bigGenreList}">
						<span class="trigger">&#9661;${bigGenre.bigGenreName }</span>
						<div class="toggle_container">
							<table>
								<tr>
									<c:set var="count" value="0" />
									<c:forEach var="genre" items="${genreList}" varStatus="status">
										<c:if test="${genre.bigGenreId == bigGenre.bigGenreId}">
											<td><label>
											<c:if test="${searchGenre.containsKey(genre.genreId)}">
												<input type="checkbox" name="genre" value="${genre.genreId }:${genre.genreName }" checked="checked">${genre.genreName }
											</c:if>
											<c:if test="${!searchGenre.containsKey(genre.genreId)}">
												<input type="checkbox" name="genre" value="${genre.genreId }:${genre.genreName }">${genre.genreName }
											</c:if>
											</label></td>
											<c:set var="count" value="${count+1}" />
										</c:if>
										<c:if test="${count%3 ==0}">
											</tr>
											<tr>
										</c:if>
									</c:forEach>
								</tr>
							</table>
						</div>
						<br>
					</c:forEach>
					<br/>
					<input type="submit" value="コンテンツを更新する" class="btn btn-2 btn-2c">
				</div>
			</form>
			<form action="delete" method="post">
				<input type="hidden" name="homeContentId" value="${i.homeContentId}">
				<input type="hidden" name="employeeId" value="${i.employeeId}">
				<input type="submit" value="コンテンツを削除する" class="btn btn-2 btn-2c" />
			</form>
			<hr>
			<h3>添付ファイル</h3>
			<div id="files">
				<c:forEach items="${dataList}" var="j">
					<div class="file">
						<td>${j.homeDataNo}</td>
						<div vlass="fileImage"><img src="${j.fileImagePath}" width="50" height="50"></div>
						<div class="fileName">${j.homeDataName}</div>
						<form action="/HomeSystem/fc/contents/edit" method="post">
							<input type="hidden" name="homeContentId" value="${j.homeContentId}"/>
							<input type="hidden" name="path" value="../files/contents/master/${j.homeContentId}/${j.homeDataNo}/${j.homeDataName}" />
							<input type="hidden" name="fileName" value="${j.homeDataName}"/>
							<input type="submit" name="download" value="ダウンロード" class="btn btn-2 btn-2c download"/>
						</form>
						<div class="del">
							<form action="/HomeSystem/fc/contents/edit" method="post">
								<input type="image" src="../../images/icon/del.png" width="15" name="fileDelete" value="削除"/>
								<input type="hidden" name="homeContentId" value="${j.homeContentId}"/>
								<input type="hidden" name="homeDataNo" value="${j.homeDataNo}"/>
								<input type="hidden" name="fileName" value="${j.homeDataName}"/>
								<input type="hidden" name="fileDelete" value="削除" class="btn btn-2 btn-2c"/>
							</form>
						</div>
					</div>
				</c:forEach>
				<div id="upload">
					<form method="POST" enctype="multipart/form-data" action="/HomeSystem/fc/contents/edit?homeContentId=${i.homeContentId}">
						<input type="hidden" name="homeContentId" value="${i.homeContentId}" />
						<input type="file" name="file" id="file" onchange="$('#fake_input_file').text($(this)[0].files[0].name)" />
						<input type="button" class="btn btn-2 btn-2c uploadBtn" value="ファイル選択" onclick="$('#file').click();" />
						<span id="fake_input_file"></span> <br>
						<input type="submit" name="upload" value="送信" class="btn btn-2 btn-2c uploadBtn">
					</form>
				</div>
			</div>
		</div>
	</body>
<script>
	autosize(document.querySelectorAll('textarea'));
</script>
</html>