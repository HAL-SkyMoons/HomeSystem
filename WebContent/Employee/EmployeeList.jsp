<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="../css/employeeList.css">
<link rel="stylesheet" type="text/css" href="../js/colorbox/colorbox.css">
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../js/colorbox/jquery.colorbox.js"></script>
<title>社員一覧ページ</title>
<script type="text/javascript">
	// 	function ImageUp(employeeId) {
	// 		window.open("/HomeSystem/fc/Home?toUser="+employeeId,"window2","width=1000,height=500");
	// 	}
	window.onload = function() {
		document.getElementById('bigGenreList1').style.display = 'none';
		document.getElementById('bigGenreList2').style.display = 'none';
	}
	$(function() {
		$(".iframe").colorbox({
			iframe : true,
			width : "500px",
			height : "90%",
			opacity : 0.7,
			fixed : true
		});
	});
	//ジャンルタブ切り替えメソッド
	function tabChange(tabName) {
		var state = '';
		if (document.getElementById(tabName).style.display == 'none') {
			state = 'block';
		} else {
			state = 'none';
		}
		document.getElementById(tabName).style.display = state;
	}

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
<%
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("S");
    String milliSec = sdf.format(date);
%>
</head>
<body>
	<div class="contents">
		<div class="searchPart">
			<form action="/HomeSystem/fc/EmployeeList" method="POST">
				<div class="departmentSearch">
					<div class="departmentTitle">部署</div>
					<select name="departmentId" class="departmentSelect">
						<option value="0">--部署--</option>
						<c:forEach var="department" items="${departments}">
							<option value="${department.departmentId}">${department.departmentName}</option>
						</c:forEach>
					</select>
				</div>
				<br>
				<div class="genreList">
					<div class="genreTitle">ジャンル</div>
					<c:forEach var="bigGenre" items="${bigGenreList}">
						<span class="trigger">&#9661;${bigGenre.bigGenreName }</span>
						<div class="toggle_container">
							<c:forEach var="genre" items="${genres}" varStatus="status">

								<c:if test="${genre.bigGenreId == bigGenre.bigGenreId}">

									<label> <c:if test="${searchGenre.containsKey(genre.genreId)}">
											<input type="checkbox" name="genre" value="${genre.genreId }" checked="checked">${genre.genreName }
									</c:if> <c:if test="${!searchGenre.containsKey(genre.genreId)}">
											<input type="checkbox" name="genre" value="${genre.genreId }">${genre.genreName }
									</c:if>
									</label>
									<br>
								</c:if>
							</c:forEach>
						</div>
						<div class="br"></div>
					</c:forEach>
				</div>
				<input type="submit" name="submit" value="検索" class="btn btn-2 btn-2c genreButton">
			</form>
		</div>
		<div class="employeeListPart">
			<h1 class="employeeListTitle">社員一覧</h1>
			<table class="employeeList">
				<tr>
					<c:forEach var="employee" items="${employees}" varStatus="status">
						<td class="employeeColumn">
							<div class="employeeImage">
								<c:choose>
									<c:when test="${sessionId == employee.employeeId}">
										<a href="./EmployeeMyPage"> <img src="../images/employees/${employee.employeeId}.jpg?"></a>
							</div> ${employee.departmentName} <br> <a href="./EmployeeMyPage" class="employeeName">${employee.employeeName}</a> </c:when> <c:when test="${sessionId != employee.employeeId }">
								<a href="./EmployeePage?employeeId=${employee.employeeId}"> <img src="../images/employees/${employee.employeeId}.jpg?<%=milliSec %>"></a>
								</div> ${employee.departmentName} <br>
								<a href="./EmployeePage?employeeId=${employee.employeeId}" class="employeeName">${employee.employeeName}</a>
							</c:when> </c:choose><br> <c:if test="${sessionId != employee.employeeId}">
								<a class="iframe" href="/HomeSystem/fc/Home?toUser=${employee.employeeId}"><input type="button" value="この人を褒める" class="btn btn-2 btn-2c homeButton"></a>
								<br />
							</c:if> <!-- 							<ul> --> <%-- 								<c:forEach var="employeeGenre" items="${employee.employeeGenre}"> --%> <%-- 									<li>${employeeGenre}</li> --%> <%-- 								</c:forEach> --%> <!-- 							</ul> -->
						</td>
						<c:if test="${status.count%5 ==0}">
				</tr>
				<tr>
					</c:if>
					</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>
