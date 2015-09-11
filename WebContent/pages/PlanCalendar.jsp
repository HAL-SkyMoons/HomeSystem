<%@page import="java.util.ArrayList"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>

<%@page import="java.util.HashMap"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeeBatchBean"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeePageBean"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="jp.ac.hal.skymoons.util.Utility"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<!DOCTYPE htm>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>企画カレンダー</title>
<%
	ArrayList<PlanBean> planList = (ArrayList<PlanBean>)request.getAttribute("planList");
%>
<!-- CSS -->
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
<link href="../js/fullcalendar/css/fullcalendar.min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/style.css">
<!-- JavaScript -->
<script type="text/javascript" src="../js/fullcalendar/js/moment.js"></script>
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../js/fullcalendar/js/jquery-ui.custom.min.js"></script>
<script type="text/javascript" src="../js/fullcalendar/js/fullcalendar.min.js"></script>
<script type="text/javascript" src="../js/fullcalendar/js/ja.js"></script>
<script type="text/javascript">
	//画像差し替えメソッド
	$(document).ready(function() {
		$('.js-replace-no-image').error(function() {
			$(this).attr({
				src : '../images/icon/NoImage.png'
			});
		});
	});
</script>

<script>
	$(document)
			.ready(
					function() {

						$('#calendar')
								.fullCalendar(
										{
											header : {
												left : 'prev,next today',
												center : 'title',
												right : 'month,agendaWeek,agendaDay'

											},
											editable : false,
											eventLimit : true, // allow "more" link when too many events
											events : [
<%for (PlanBean plan : planList) {
		out.println("{");
		out.println("title: '" + plan.getPlanTitle() + "',");
		out.println("url: 'http://localhost:8080/HomeSystem/fc/PlanDetail?planId="
			+ plan.getPlanId() + "&detail=true',");
		out.println("start: '" + plan.getStartDate() + "',");
		out.println("end: '" + plan.getEndDate() + "',");
		out.println("backgroundColor: ' #ffd280',");
		out.println("borderColor: ' #ffa600',");
		out.println("textColor: ' #666'");

		out.println("},");
	    }%>
	]
										});

					});
</script>
<style>
/* body { */
/* 	padding: 0; */
/* 	font-family: "Lucida Grande", Helvetica, Arial, Verdana, sans-serif; */
/* 	font-size: 14px; */
/* } */
#calendar {
	max-width: 900px;
	margin: 0 auto;
}
</style>
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
			<div id="waapper" style="width:1000px; margin:0 auto;" >
				<h1>企画カレンダー</h1>
				<div id="calendar"></div>
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