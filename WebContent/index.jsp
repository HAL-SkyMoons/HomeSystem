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
%>
<%
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
	<header>
		<div id="logo">
			<a href="/HomeSystem/fc/login/user"><img src="/HomeSystem/images/logo.png" /></a>
		</div>
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

		<!-- ユーザ情報描画-------------------------------------------------------------------------------------------------- -->

		<div class="employeeStatus">
			<c:forEach var="employeeDetail" items="${employeeDetail}">


				<div class="imageDiv">
					<img src="../images/employees/${employeeDetail.employeeId}.jpg?<%=milliSec%>" class="employeeImage">
				</div>
				<div class="flameDiv">
					<img src="../images/flame/<%=level%>.png?<%=milliSec%>" class="employeeFlame">
				</div>

				${employeeDetail.departmentName}</li>
				${employeeDetail.employeeName}
				レベル${employeeDetail.level}
				${employeeDetail.experience-employeeDetail.nowExperience}/${employeeDetail.nextExperience-employeeDetail.nowExperience}XP
				<progress value="${employeeDetail.experience-employeeDetail.nowExperience}" max="${employeeDetail.nextExperience-employeeDetail.nowExperience}">
					<span>${(employeeDetail.experience-employeeDetail.nowExperience) / (employeeDetail.nextExperience-employeeDetail.nowExperience) *100}</span>%
				</progress>

			</c:forEach>
		</div>

		<ul>


			<li class="menu1"><a href="/HomeSystem/fc/EmployeeList">社員</a></li>
			<li class="menu2"><a href="/HomeSystem/fc/PlanList">企画</a></li>
			<li class="menu2"><a href="/HomeSystem/fc/contents/list">ホメホメコンテンツ</a></li>
			<li class="menu3"><a href="/HomeSystem/fc/ranking/topnum">ランキング</a></li>

		</ul>
	</header>
</body>
</html>


