<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeeBatchBean"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeePageBean"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeeListBean"%>
<%@page import="jp.ac.hal.skymoons.util.Utility"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="../css/employeeList.css">
<link rel="stylesheet" type="text/css" href="../js/colorbox/colorbox.css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/style.css">
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../js/colorbox/jquery.colorbox.js"></script>
<title>社員一覧ページ</title>
<script type="text/javascript">
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
	//画像差し替えメソッド
	$(document).ready(function() {
	    $('.js-replace-no-image').error(function() {
	        $(this).attr({
	            src: '../images/icon/NoImage.png'
	        });
	    });
	});
</script>
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
								<c:choose>
									<c:when test="${sessionId == employee.employeeId}">
										<div class="employeeImage">
											<a href="./EmployeeMyPage">
												<img src="../images/employees/${employee.employeeId}.jpg?<%=milliSec %>" class="js-replace-no-image">
											</a>
											<div class="employeeFlame">
												<a href="./EmployeeMyPage">
													<c:if test="${employee.level>12}" var="flameFlg"/>
													<c:if test="${flameFlg}">
														<img src="../images/flame/12.png">
													</c:if>
													<c:if test="${!flameFlg}">
														<img src="../images/flame/${employee.level}.png"/>
													</c:if>
												</a>
											</div>
										</div>
										 ${employee.departmentName} <br> <a href="./EmployeeMyPage" class="employeeName">${employee.employeeName}</a>
									</c:when>
									<c:when test="${sessionId != employee.employeeId }">
									 	<div class="employeeImage">
											<a href="./EmployeePage?employeeId=${employee.employeeId}">
												<img src="../images/employees/${employee.employeeId}.jpg?<%=milliSec %>" class="js-replace-no-image">
											</a>
											<div class="employeeFlame">
										 		 <a href="./EmployeePage?employeeId=${employee.employeeId}">
																<c:if test="${employee.level>12}" var="flameFlg"/>
																<c:if test="${flameFlg}">
																	<img src="../images/flame/12.png">
																</c:if>
																<c:if test="${!flameFlg}">
																	<img src="../images/flame/${employee.level}.png"/>
																</c:if>
												</a>
											</div>
										</div>
										 ${employee.departmentName} <br>
										<a href="./EmployeePage?employeeId=${employee.employeeId}" class="employeeName">${employee.employeeName}</a>
									</c:when>
								 </c:choose>
								 <br> <c:if test="${sessionId != employee.employeeId}">
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

