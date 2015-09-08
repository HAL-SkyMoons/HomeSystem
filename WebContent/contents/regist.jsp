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
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" type="text/css" href="../../css/bootstrap.css">
		<link rel="stylesheet" type="text/css" href="../../css/bootstrap-theme.min.css">
		<link rel="stylesheet" type="text/css" href="../../css/ContentsCommon.css">
		<link rel="stylesheet" type="text/css" href="../../css/ContentsRegist.css">
		<title>コンテンツ登録</title>
		
		<script type="text/javascript" src="../../js/jquery-2.1.4.min.js"></script>
		<script type="text/javascript" src="../../js/autosize/autosize.js"></script>
		<script type="text/javascript">
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
	</head>
	<body>
		<c:set var="i" value="${registData}"/>
		<div id="wrapper">
			<form action="addition" method="post">
				<div id="contents">
					<div id="contentsHeader">
						<br/>
					</div>
					<div id="title">
						<textarea id="titleValue" rows="1" name="homeContentTitle" placeholder="コンテンツタイトル" maxlength="100"></textarea>
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
							<input type="checkbox" name="addEndDatetime" value="false"/>終了日を登録しない
						</div>
						<div id="planner">投稿者
							<div id="img">
								<img src="../../images/employees/${i.employeeId}.jpg" alt="投稿者">
							</div>
							${i.lastName}${i.firstName}
						</div>
					</div>
					<div id="contentsComment">
						<textarea id="contentsCommentValue" rows="1" placeholder="コンテンツ内容" name="homeContentComment"></textarea>
					</div>
					<br/>
					<c:forEach var="bigGenre" items="${bigGenreList}">
						<span class="trigger">&#9661;${bigGenre.bigGenreName }</span>
						<div class="toggle_container">
							<table>
								<tr>
									<c:set var="count" value="0" />
									<c:forEach var="genre" items="${genreList}" varStatus="status">
										<c:if test="${genre.bigGenreId == bigGenre.bigGenreId}">
											<td><label>
												<input type="checkbox" name="genreId" value="${genre.genreId}">${genre.genreName }
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
				</div>
				<div class="btnZone">
					<input type="submit" name="submit" class="btn btn-2 btn-2c submit" id="submit" value="登録">
				</div>
			</form>
		</div>
	</body>
<script>
	autosize(document.querySelectorAll('textarea'));
</script>
</html>