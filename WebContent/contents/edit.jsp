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
		<title>コンテンツ編集</title>
	</head>
	<body>
		<c:set var="i" value="${editData}"/>
		<div id="wrapper">
			<form action="update" method="post">
				<input type="hidden" name="homeContentId" value="${i.homeContentId}">
				<div id="plan">
					<div id="planHeader">
						<div id="id">No.&nbsp;${i.homeContentId}</div>
						<div id="planDate">企画日時：${i.planDatetime}</div>
					</div>
					<div id="title">
						コンテンツタイトル：<input type="text" name="homeContentTitle" value="${i.homeContentTitle}">
					</div>
					<div id="planDetail">
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
							分～<br/>
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
							企画者
							<div id="img">
								<img src="../../images/employees/${i.employeeId}.jpg" width="100" height="100">
							</div>
							${i.lastName}&nbsp;${i.firstName}
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
						<form action="/HomeSystem/fc/contents/edit" method="post">
							<input type="image" src="../../images/icon/del.png" width="15" name="fileDelete" value="削除"/>
							<input type="hidden" name="homeContentId" value="${j.homeContentId}"/>
							<input type="hidden" name="homeDataNo" value="${j.homeDataNo}"/>
							<input type="hidden" name="fileName" value="${j.homeDataName}"/>
							<input type="hidden" name="fileDelete" value="削除" class="btn btn-2 btn-2c"/>
						</form>
					</div>
				</c:forEach>
				<div id="upload">
					<form method="POST" enctype="multipart/form-data" action="/HomeSystem/fc/contents/edit?homeContentId=${i.homeContentId}">
						<input type="hidden" name="homeContentId" value="${i.homeContentId}" />
						<input type="file" name="file" />
						<input type="submit" name="upload" value="送信" class="btn btn-2 btn-2c"/>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>