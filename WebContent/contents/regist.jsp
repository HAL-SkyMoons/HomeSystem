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
<script type="text/javascript" src="/HomeSystem/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="/HomeSystem/js/colorbox/jquery.colorbox.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<script type="text/javascript" src="../../js/jquery-2.1.4.min.js"></script>
		<script type="text/javascript" src="../../js/autosize/autosize.js"></script>
<c:if test="${scriptMessage != null}" >${scriptMessage}</c:if>
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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" type="text/css" href="../../css/reset.css">
		<link rel="stylesheet" type="text/css" href="../../css/bootstrap.css">
		<link rel="stylesheet" type="text/css" href="../../css/bootstrap-theme.min.css">
		<link rel="stylesheet" type="text/css" href="../../css/ContentsCommon.css">
		<link rel="stylesheet" type="text/css" href="../../css/ContentsRegist.css">
		<link rel="stylesheet" type="text/css" href="/HomeSystem/css/style.css">
		<title>コンテンツ登録</title>

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

		<c:set var="i" value="${registData}"/>
		<div id="wrapper">
			<h1>コンテンツ登録</h1>
			<form action="addition" method="post">
				<div id="contents">
					<div id="contentsHeader">
						<br/>
					</div>
					<div id="title">
						<textarea id="titleValue" rows="1" name="homeContentTitle" placeholder="コンテンツタイトル" maxlength="100" required></textarea>
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
								<img src="../../images/employees/${i.employeeId}.jpg" alt="投稿者" class="js-replace-no-image">
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
							${i.lastName}${i.firstName}
						</div>
					</div>
					<div id="contentsComment">
						<textarea id="contentsCommentValue" rows="1" placeholder="コンテンツ内容" name="homeContentComment" required></textarea>
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
					<input type="submit" name="submit" class="btn btn-2 btn-2c contents_submit" id="submit" value="登録">
				</div>
			</form>
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