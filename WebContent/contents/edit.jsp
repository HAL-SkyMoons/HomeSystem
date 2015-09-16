<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeeBatchBean"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeePageBean"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="jp.ac.hal.skymoons.util.Utility"%>
<%
	SessionController sessionController = new SessionController(request);
	String url = sessionController.checkUserSession();
	if(url != null) {
		response.sendRedirect(url);
	}
%>
<%
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("S");
	String milliSec = sdf.format(date);
	ArrayList<EmployeePageBean> employeeDates = (ArrayList<EmployeePageBean>) request
			.getAttribute("employeeDetail");
	EmployeePageBean employeeDate = employeeDates.get(0);
	int level = employeeDate.getLevel();
	if (level > 12) {
		level = 12;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript" src="/HomeSystem/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="/HomeSystem/js/autosize/autosize.js"></script>
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
<script type="text/javascript">
	//画像差し替えメソッド
	$(document).ready(function() {
	    $('.js-replace-no-image').error(function() {
	        $(this).attr({
	        	src: '/HomeSystem/images/icon/NoImage.png'
	        });
	    });
	});
</script>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="../../css/reset.css">
		<link rel="stylesheet" type="text/css" href="../../css/bootstrap.css">
		<link rel="stylesheet" type="text/css" href="../../css/bootstrap-theme.min.css">
		<link rel="stylesheet" type="text/css" href="../../css/ContentsCommon.css">
		<link rel="stylesheet" type="text/css" href="../../css/ContentsEdit.css">
		<link rel="stylesheet" type="text/css" href="../../js/Magnific-Popup/magnific-popup.css">
		<link rel="stylesheet" type="text/css" href="../../js/colorbox/colorbox.css">
		<link rel="stylesheet" type="text/css" href="/HomeSystem/css/style.css">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>コンテンツ編集</title>
	</head>

		<!--******************************** こっからへっだー ***********************************-->
	<div id="allwrap">
		<div id="headerline">line</div>
		<header>

			<div id="logo">
				<a href="/HomeSystem/fc/Index"><img src="/HomeSystem/images/logo.png" /></a>
			</div>

			<div id="headerright">
				<c:forEach var="employeeDetail" items="${employeeDetail}">
					<div id="headerstr">
						<label id="headername">${employeeDetail.employeeName}さん</label><br> <label id="headerlevel">レベル${employeeDetail.level}</label>
					</div>
					<div id="headerimage">
						<img src="/HomeSystem/images/employees/${employeeDetail.employeeId}.jpg?<%=milliSec%>" class="js-replace-no-image">
						<div id="headerflame">
							<img src="/HomeSystem/images/flame/<%=level%>.png?<%=milliSec%>">
						</div>
					</div>
				</c:forEach>
				<div id="headerbutton">
					<div id="mypage">
						<form action="/HomeSystem/fc/EmployeeMyPage">
							<input type="submit" class="btn btn-2 btn-2c submit" value="マイページ">
						</form>
					</div>
					<div id="logout">
						<form action="/HomeSystem/fc/logout/user">
							<input type="submit" class="btn btn-2 btn-2c submit" value="ログアウト">
						</form>
					</div>
				</div>

			</div>

			<ul id="headermenu">
				<li class="menu1"><a href="/HomeSystem/fc/EmployeeList">社員一覧</a></li>
				<li class="menu2"><a href="#">企画</a>
					<ul>
						<li><a href="/HomeSystem/fc/PlanList">企画一覧</a></li>
						<li><a href="/HomeSystem/fc/PlanRegister">企画登録</a></li>
						<li><a href="/HomeSystem/fc/PlanCalendar">企画カレンダー</a></li>
					</ul></li>
				<li class="menu3"><a href="#">ホメホメコンテンツ</a>
					<ul>
						<li><a href="/HomeSystem/fc/contents/list">コンテンツ一覧</a></li>
						<li><a href="/HomeSystem/fc/contents/regist">コンテンツ登録</a></li>
					</ul></li>
				<li class="menu4"><a href="/HomeSystem/fc/ranking/topnum">ランキング</a></li>
			</ul>

		</header>
		<div id="allcontents">
			<!--*********************************ここまでへっだー ***********************************-->

	<body>
		<c:set var="i" value="${editData}"/>
		<div id="wrapper">
			<h1>コンテンツ編集</h1>
			<form action="update" method="post">
				<input type="hidden" name="homeContentId" value="${i.homeContentId}">
				<div id="contents">
					<div id="contentsHeader">
						<div id="id">No.&nbsp;${i.homeContentId}</div>
						<div id="planDate">企画日時：${i.planDatetime}</div>
					</div>
					<div id="title">
						<textarea id="titleValue" rows="1" name="homeContentTitle" maxlength="100" required>${i.homeContentTitle}</textarea>
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
								<img src="/HomeSystem/images/employees/${i.employeeId}.jpg" alt="投稿者" class="js-replace-no-image">
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
						<textarea id="contentsCommentValue" rows="1" placeholder="コンテンツ内容" name="homeContentComment" required>${i.homeContentComment}</textarea>
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

			<!--*********************************ここからふったー ***********************************-->
		</div>
		<footer>
			<div id="footertop">
				<a href="#top">▲ページTOPへ</a>
			</div>

			<div id="footermenu">
				<div id="footermenuin">
					<div id="footermenu1">
						<h4>社員</h4>
						<p>
							<a href="/HomeSystem/fc/EmployeeList">社員一覧</a>
						</p>
						<p>
							<a href="/HomeSystem/fc/EmployeeMyPage">マイページ</a>
						</p>
					</div>
					<div id="footermenu2">
						<h4>企画</h4>
						<p>
							<a href="/HomeSystem/fc/PlanList">企画一覧</a>
						</p>
						<p>
							<a href="/HomeSystem/fc/PlanRegister">企画登校</a>
						</p>
						<p>
							<a href="/HomeSystem/fc/PlanCalendar">企画カレンダー</a>
						</p>
					</div>
					<div id="footermenu3">
						<h4>ホメホメコンテンツ</h4>
						<p>
							<a href="/HomeSystem/fc/contents/list">コンテンツ一覧</a>
						</p>
						<p>
							<a href="/HomeSystem/fc/contents/regist">コンテンツ登録</a>
						</p>
					</div>
					<div id="footermenu4">
						<h4>ランキング</h4>
						<p>
							<a href="/HomeSystem/fc/ranking/topnum">ランキング</a>
						</p>
					</div>
				</div>
			</div>
			<div id="footerline">Copyright &copy; 2015-2016 SkyMoons All Rights Reserved.</div>
		</footer>
	</div>
	<!--*********************************ここまでふったー ***********************************-->

	</body>
<script>
	autosize(document.querySelectorAll('textarea'));
</script>
</html>