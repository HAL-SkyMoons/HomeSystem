<%@page import="java.text.SimpleDateFormat"%>
<%@page import="jp.ac.hal.skymoons.beans.UserBean"%>
<%@page import="jp.ac.hal.skymoons.util.Utility"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>

<%@page import="java.util.HashMap"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeeBatchBean"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeePageBean"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="jp.ac.hal.skymoons.util.Utility"%>

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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企画登録確認</title>
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="../css/PlanConfirmation.css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/style.css">
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
//画像差し替えメソッド
$(document).ready(function() {
    $('.js-replace-no-image').error(function() {
        $(this).attr({
            src: '../images/icon/NoImage.png'
        });
    });
});
</script>
</head>
<%
    Utility util = new Utility();
    PlanBean plan = (PlanBean) request.getAttribute("plan");
    UserBean user = (UserBean) request.getAttribute("user");

    String planner = plan.getPlanner();
    String planTitle = plan.getPlanTitle();
    String planComment = plan.getPlanComment();

    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");

    String startDate = sdf2.format(plan.getStartDate());
    String endDate = sdf2.format(plan.getEndDate());

    int userLevel = user.getLevel();
	if(userLevel > 12)
	{
	    userLevel = 12;
	}
%>
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
	<div id="wrapper">
	<h1>企画登録確認</h1>
		<form action="/HomeSystem/fc/PlanConfirmation" method="post">
			<div id="plan">
				<div id="planHeader">
					<div id="planDate">
						<jsp:useBean id="Date" class="java.util.Date" />
						企画日：
						<fmt:formatDate value="${Date}" pattern="yyyy年MM月dd日" />
					</div>
				</div>
				<div id="title">
					<div id="titleValue">${plan.planTitle}</div>
				</div>
				<div id="planDetail">
					<div id="startEnd">
						<div>企画実施日</div>
						<fmt:formatDate value="${plan.startDate}" pattern="yyyy年MM月dd日 KK時mm分" />
						&nbsp;&sim;&nbsp;
						<fmt:formatDate value="${plan.endDate}" pattern="yyyy年MM月dd日 KK時mm分" />
					</div>
					<div id="planner">
						企画者
						<div id="img">
							<img src="../images/employees/${user.userId}.jpg?<%=milliSec%>" class="js-replace-no-image">
						</div>
						<div class="flameDiv">
							<img src="../images/flame/<%=userLevel%>.png?<%=milliSec%>" class="employeeFlame">
						</div>
						${user.lastName}${user.firstName}
						<input type="hidden" name="planner" value="${user.userId}">
					</div>
				</div>
				<div id="planComment">
					<div id="planCommentValue">${Utility.nlToBR(plan.planComment)}</div>
				</div>
				<br>

			</div>
			<div id="genre">
				登録ジャンル<br>
				<c:forEach var="genre" items="${genreIds}" varStatus="status">
					${genreNames.get(status.index)}
					<input type="hidden" name="id" value="${genre}">
				</c:forEach>

			</div>
			<input type="hidden" name="planner" value="${plan.planner}">
			<input type="hidden" name="planTitle" value="${plan.planTitle}">
			<input type="hidden" name="planComment" value="${plan.planComment}">
			<fmt:formatDate value="${plan.startDate}" pattern="yyyy-MM-dd KK:mm" var="startDate" />
			<input type="hidden" name="startDate" value="${startDate}">
			<fmt:formatDate value="${plan.endDate}" pattern="yyyy-MM-dd KK:mm" var="endDate" />
			<input type="hidden" name="endDate" value="${endDate}">
			<div class="btnZone">
				<input type="submit" name="submit" class="btn btn-2 btn-2c submitBtn" id="submit" value="登録">
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
</html>