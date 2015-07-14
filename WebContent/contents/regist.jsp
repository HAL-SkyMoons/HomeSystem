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
		<link rel="stylesheet" type="text/css" href="../../css/PlanRegister.css">
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
						<div id="planDate"></div>
					</div>
					<div id="title">
						<textarea id="titleValue" rows="1" name="homeContentTitle" maxlength="100"></textarea>
					</div>
					<div id="planDetail">
						<div id="startEnd">
							実施期間：<br/>
							<select name="startYear">
								<c:forEach begin="1950" end="2020" step="1" varStatus="status">
									<option value="${status.index}">${status.index}</option>
								</c:forEach>
							</select>
							年
							<select name="startMonth">
								<c:forEach begin="1" end="12" step="1" varStatus="status">
									<option value="${status.index}">${status.index}</option>
								</c:forEach>
							</select>
							月
							<select name="startDay">
								<c:forEach begin="1" end="31" step="1" varStatus="status">
									<option value="${status.index}">${status.index}</option>
								</c:forEach>
							</select>
							日
							<select name="startHour">
								<c:forEach begin="0" end="23" step="1" varStatus="status">
									<option value="${status.index}">${status.index}</option>
								</c:forEach>
							</select>
							時
							<select name="startMinute">
								<c:forEach begin="0" end="59" step="1" varStatus="status">
									<option value="${status.index}">${status.index}</option>
								</c:forEach>
							</select>
							分～<br/>
							<select name="endYear">
								<c:forEach begin="1950" end="2020" step="1" varStatus="status">
									<option value="${status.index}">${status.index}</option>
								</c:forEach>
							</select>
							年
							<select name="endMonth">
								<c:forEach begin="1" end="12" step="1" varStatus="status">
									<option value="${status.index}">${status.index}</option>
								</c:forEach>
							</select>
							月
							<select name="endDay">
								<c:forEach begin="1" end="31" step="1" varStatus="status">
									<option value="${status.index}">${status.index}</option>
								</c:forEach>
							</select>
							日
							<select name="endHour">
								<c:forEach begin="0" end="23" step="1" varStatus="status">
									<option value="${status.index}">${status.index}</option>
								</c:forEach>
							</select>
							時
							<select name="endMinute">
								<c:forEach begin="0" end="59" step="1" varStatus="status">
									<option value="${status.index}">${status.index}</option>
								</c:forEach>
							</select>
							分<br/>
							<input type="checkbox" name="addEndDatetime" value="false"/>終了日を登録しない
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
					<c:forEach var="bigGenre" items="${bigGenreList}">
						<span class="trigger">&#9661;${bigGenre.bigGenreName }</span>
						<div class="toggle_container">
							<table>
								<tr>
									<c:set var="count" value="0" />
									<c:forEach var="genre" items="${genreList}" varStatus="status">
										<c:if test="${genre.bigGenreId == bigGenre.bigGenreId}">
											<td><label>
												<input type="checkbox" name="genre" value="${genre.genreId }:${genre.genreName }">${genre.genreName }
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
					<input type="submit" value="登録をする" class="btn btn-2 btn-2c">
				</div>
			</div>
		</form>
	</body>
<script>
	autosize(document.querySelectorAll('textarea'));
</script>
</html>