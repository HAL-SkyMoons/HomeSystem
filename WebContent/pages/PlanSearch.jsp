<%@page import="java.util.Iterator"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanPointsBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="jp.ac.hal.skymoons.beans.UserBean"%>
<%@page import="jp.ac.hal.skymoons.beans.GenreBean"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>

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
<meta charset="UTF-8">
<title>企画検索</title>
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="../css/PlanSearch.css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/style.css">
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>

<script type="text/javascript">
	function Post(planId) {
		form1.detail.value = "detail";
		form1.planId.value = planId;
		form1.submit();
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
	<div class="wrapper">
	<h1>詳細検索</h1>
		<form action="/HomeSystem/fc/PlanList" method="post">
			<table class="search">
				<tr>
					<th class="searchTitle">キーワード</th>
					<td class="searchForm"><input type="text" name="keyword" class="keyword" value="${searchKeyword}" /></td>
					<td class="help">
						タイトルと内容にキーワード<br>と<br>一致するものがある企画<br>を表示します。
					</td>
				</tr>
				<tr>
					<th class="searchTitle">企画者</th>
					<td class="searchForm"><select name="planner">
							<option value="">---指定なし---</option>
							<c:forEach var="employee" items="${employeeList}">
								<c:if test="${searchPlanner != null && searchPlanner == employee.userId}">
									<option value="${employee.userId}" selected="selected">${employee.lastName}&nbsp;${employee.firstName}</option>
								</c:if>

								<c:if test="${searchPlanner == null || searchPlanner != employee.userId}">
									<option value="${employee.userId}">${employee.lastName}&nbsp;${employee.firstName}</option>
								</c:if>

							</c:forEach>
						</select></td>
					<td class="help">
						企画者を指定した人のみに<br>限定することが出来ます。
					</td>
				</tr>
				<tr>
					<th class="searchTitle">オプション</th>
					<td class="searchForm"><label> <c:if test="${endPlan != null}">
								<input type="checkbox" name="endPlan" value="check" checked="checked">
							</c:if> <c:if test="${endPlan == null}">
								<input type="checkbox" name="endPlan" value="check">
							</c:if> 終了企画を含める
					</label></td>
					<td class="help">
						検索結果に終了済み企画を<br>含むことが出来ます。
					</td>
				</tr>
				<tr>
					<th class="searchTitle">順序</th>
					<td class="searchForm"><select name="order">
							<option value="">---指定なし---</option>
							<c:forEach var="order" items="${orderList}">

								<c:if test="${searchOrder != null && order.key == searchOrder}">
									<option value="${order.key}" selected="selected">${order.value}</option>
								</c:if>
								<c:if test="${searchOrder  == null || order.key != searchOrder}">
									<option value="${order.key}">${order.value}</option>
								</c:if>


							</c:forEach>
						</select></td>
					<td class="help">
						検索結果の順番を指定した<br>順序に変更します。
					</td>
				</tr>
			</table>

			<c:forEach var="bigGenre" items="${bigGenreList}">
				<span class="trigger">&#9661;${bigGenre.bigGenreName }</span>
				<div class="toggle_container">
					<table>
						<tr>
							<c:set var="count" value="0" />
							<c:forEach var="genre" items="${genreList}" varStatus="status">

								<c:if test="${genre.bigGenreId == bigGenre.bigGenreId}">

									<td><label> <c:if test="${searchGenre.containsKey(genre.genreId)}">
												<input type="checkbox" name="genre" value="${genre.genreId }" checked="checked">${genre.genreName }
									</c:if> <c:if test="${!searchGenre.containsKey(genre.genreId)}">
												<input type="checkbox" name="genre" value="${genre.genreId }">${genre.genreName }
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

			<div class="btnZone">
				<input type="submit" name="search" class="btn btn-2 btn-2c searchBtn" value="検索">
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