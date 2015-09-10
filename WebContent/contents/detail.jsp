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
<script type="text/javascript" src="../../js/autosize/autosize.js"></script>
<script type="text/javascript" src="../../js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../../js/colorbox/jquery.colorbox.js"></script>
<c:if test="${scriptMessage != null}" >${scriptMessage}</c:if>
<script type="text/javascript">
	//画像差し替えメソッド
	$(document).ready(function() {
	    $('.js-replace-no-image').error(function() {
	        $(this).attr({
	            src: '/HomeSystem/images/employees/NoImage.png'
	        });
	    });
	});
</script>
<html>
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
		<link rel="stylesheet" type="text/css" href="/HomeSystem/css/style.css">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>コンテンツ詳細</title>
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
	
		<c:set var="i" value="${detailList}"/>
		<div id="wrapper">
			<h1>コンテンツ詳細</h1>
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
						<c:if test="${i.classFlag == 1}">
							<c:if test="${i.userId == i.employeeId}">
								<a href="/HomeSystem/fc/EmployeeMyPage">
							</c:if>
							<c:if test="${i.userId != i.employeeId}">
								<a href="/HomeSystem/fc/EmployeePage?employeeId=${i.employeeId}">
							</c:if>
						</c:if>
							<div id="img">
								<img src="../../images/employees/${i.employeeId}.jpg" alt="投稿者" class="js-replace-no-image">
							</div>
							<c:if test="${i.level != null}">
								<c:set var="level" value="${i.level}"/>
								<c:if test="${i.level > 12}">
									<c:set var="level" value="12"/>
								</c:if>
								<div class="flameDiv">
									<img src="/HomeSystem/images/images/flame/${level}.png" class="employeeFlame">
								</div>
							</c:if>
							${i.lastName}${i.firstName}<br/>
						<c:if test="${i.classFlag == 1}">
							</a>
						</c:if>
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
							
							
							<c:if test="${homeLog.classFlag == 1}">
								<c:if test="${i.userId == homeLog.homeUser}">
									<a href="/HomeSystem/fc/EmployeeMyPage">
								</c:if>
								<c:if test="${i.userId != homeLog.homeUser}">
									<a href="/HomeSystem/fc/EmployeePage?employeeId=${i.employeeId}">
								</c:if>
							</c:if>
								<c:if test="${fromUser}"><div class="planner"></c:if>
								<c:if test="${!fromUser}"><div class="gest"></c:if>
									
									<div class="face"><img src="../../images/employees/${homeLog.homeUser}.jpg class="js-replace-no-image""></div>
									<c:if test="${homeLog.level != null}">
										<c:set var="level" value="${homeLog.level}"/>
										<c:if test="${homeLog.level > 12}">
											<c:set var="level" value="12"/>
										</c:if>
										<div class="flameFaceDiv">
											<img src="../../images/flame/${level}.png" class="employeeFlame">
										</div>
									</c:if>
									<div class="name">${homeLog.homeUserLastName}${homeLog.homeUserFirstName}</div>
									<div class="home">
										<c:if test="${i.userId != homeLog.homeUser && i.employeeId != i.userId}">
											<a class="iframe" href="/HomeSystem/fc/Home?toUser=${homeLog.homeUser}&contentsId=${i.homeContentId}"><input type="button" value="ホメる" class="btn btn-2 btn-2c"></a>
										</c:if>
									</div>
								</div>
							<c:if test="${homeLog.classFlag == 1}">
								</a>
							</c:if>
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
</html>