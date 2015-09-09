<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeeBatchBean"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeePageBean"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="jp.ac.hal.skymoons.util.Utility"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	SessionController sessionController = new SessionController(request);
	String url = sessionController.checkUserSession();
	if (url != null) {
		response.sendRedirect("/HomeSystem" + url);
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
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/reset.css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/style.css">
<title>トップ</title>
</head>
<body>
	<!--******************************** こっからへっだー ***********************************-->
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
					<img src="/HomeSystem/images/employees/${employeeDetail.employeeId}.jpg?<%=milliSec%>">
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
		<div id="headermenu">
			<ul>
				<li class="menu1"><a href="/HomeSystem/fc/EmployeeList">社員</a></li>
				<li class="menu2"><a href="/HomeSystem/fc/PlanList">企画</a></li>
				<li class="menu3"><a href="/HomeSystem/fc/contents/list">ホメホメコンテンツ</a></li>
				<li class="menu4"><a href="/HomeSystem/fc/ranking/topnum">ランキング</a></li>
			</ul>
		</div>
	</header>
	<!--*********************************ここまでへっだー ***********************************-->
</body>
</html>


