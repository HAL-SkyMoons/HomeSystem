<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeeBatchBean"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeePageBean"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="jp.ac.hal.skymoons.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ホメコメント閲覧</title>
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="../css/HomeView.css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/style.css">
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../js/masonry.min.js"></script>
<script type="text/javascript">
	jQuery(window).load(function() {
		jQuery('.contents').masonry({
			itemSelector : '.home',
			columnWidth : 500
		});
	});
</script>
<%
	ArrayList<EmployeePageBean> headerEmployeeDatas = (ArrayList<EmployeePageBean>)request.getAttribute("headerEmployeeData");
	EmployeePageBean headerEmployeeData = headerEmployeeDatas.get(0);
	int headerEmployeeLevel = headerEmployeeData.getLevel();
	if(headerEmployeeLevel>12){
		headerEmployeeLevel=12;
	}
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("S");
	String milliSec = sdf.format(date);
%>
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
				<c:forEach var="headerEmployeeData" items="${headerEmployeeData}">
					<div id="headerstr">
						<label id="headername">${headerEmployeeData.employeeName}さん</label><br> <label id="headerlevel">レベル${headerEmployeeData.level}</label>
					</div>
					<div id="headerimage">
						<img src="/HomeSystem/images/employees/${headerEmployeeData.employeeId}.jpg?<%=milliSec%>">
						<div id="headerflame">
							<img src="/HomeSystem/images/flame/<%=headerEmployeeLevel%>.png?<%=milliSec%>">
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
	<div class="wrapper">
		<div class="title"><img src="../images/icon/message.png"><h1 class="titleName">あなた宛のホメコメント</h1></div>
		<a href="/HomeSystem/fc/EmployeeMyPage"><input type='submit' name='submit' value='戻る' class='btn btn-2 btn-2c backBtn'></a>

		<hr>
		<div class="contents">

			<c:forEach var="homeData" items="${homeList}">
				<div class="home">
					<div class="user">
						<div class="face">
							<c:if test="${homeData.classFlag == 1}">
								<a href="/HomeSystem/fc/EmployeePage?employeeId=${homeData.homeUser}"> <img src="../images/employees/${homeData.homeUser}.jpg">
								</a>
							</c:if>
							<c:if test="${homeData.classFlag == 0}">
								<img src="../images/icon/guest.png">
							</c:if>
						</div>
						<div class="flameDiv">
							<c:if test="${homeData.classFlag == 1}">
								<c:set var="level" value="${homeData.level}" />
								<c:if test="${homeData.level > 12}">
									<c:set var="level" value="12" />
								</c:if>
								<a href="/HomeSystem/fc/EmployeePage?employeeId=${homeData.homeUser}"> <img src="../images/flame/${level}.png" class="flame">
								</a>
							</c:if>
						</div>
						<c:if test="${homeData.classFlag == 1}">
							<a href="/HomeSystem/fc/EmployeePage?employeeId=${homeData.homeUser}">
								<div class="name">${homeData.homeUserLastName}${homeData.homeUserFirstName}</div>
							</a>
						</c:if>
						<c:if test="${homeData.classFlag == 0}">
							<div class="name">${homeData.homeUserLastName}${homeData.homeUserFirstName}</div>
						</c:if>
					</div>
					<div class="comment">
						<div class="commentBody">${homeData.homeComment}</div>
						<div class="commentDate">${homeData.homeDatetime}</div>
					</div>
				</div>
			</c:forEach>
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
