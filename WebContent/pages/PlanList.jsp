<%@page import="java.util.Iterator"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanPointsBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="jp.ac.hal.skymoons.beans.UserBean"%>
<%@page import="jp.ac.hal.skymoons.beans.GenreBean"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeeBatchBean"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeePageBean"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<!DOCTYPE html">
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>企画一覧</title>
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="../css/PlanList.css">
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

	function change() {
		document.getElementById('outputKeyword').value = document.forms['formKeyword'].elements['inputKeyword'].value;
	}
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
		<table class="search">
			<tr>
				<th>キーワード</th>
				<form action="/HomeSystem/fc/PlanList" method="post" id="formKeyword">
					<td class="searchDataColumn"><input type="text" class="keyword" id="inputKeyword"  name="keyword" value="${searchKeyword}" onchange="javascript:change();"></td>
					<td class="searchBtnColumn"><input type="submit" name="search" class="btn btn-2 btn-2c searchBtn" value="検索"></td>
				</form>
			</tr>
			<tr>
				<th>詳細検索</th>
				<form action="/HomeSystem/fc/PlanSearch" method="post">
					<input type="hidden" name="searchKeyword" id="outputKeyword" value="${searchKeyword}">
					<td class="searchDetailColumn"><c:if test="${searchPlanner != null}">
							<div class="detailData">
								<span class="detailTitle">企画者:</span> <span class="detailInside">${searchPlannerName}</span>
								<input type="hidden" name="searchPlanner" value="${searchPlanner}" />
							</div>
						</c:if> <c:if test="${endPlan != null}">
							<div class="detailData">
								<span class="detailTitle">オプション:</span> <span class="detailInside">終了済企画を含む</span>
								<input type="hidden" name="endPlan" value="${endPlan}" />
							</div>
						</c:if> <c:if test="${order != null}">
							<div class="detailData">
								<span class="detailTitle">順序:</span> <span class="detailInside"> <c:choose>
										<c:when test="${order == 'dateDesc'}">投稿日時が新しい順</c:when>
										<c:when test="${order == 'dateAsc'}">投稿日時が古い順</c:when>
										<c:when test="${order == 'commentDesc'}">コメントが多い順</c:when>
										<c:when test="${order == 'commentAsc'}">コメントが少ない順</c:when>
										<c:when test="${order == 'evaluationDesc'}">評価数が多い順</c:when>
										<c:when test="${order == 'evaluationAsc'}">評価数が少ない順</c:when>
										<c:when test="${order == 'likeDesc'}">いいねが多い順</c:when>
										<c:when test="${order == 'likeAsc'}">いいねが少ない順</c:when>
										<c:when test="${order == 'startDesc'}">企画開始日時が新しい順</c:when>
										<c:when test="${order == 'startAsc'}">企画開始日時が古い順順</c:when>
										<c:when test="${order == 'periodDesc'}">企画期間が長い順</c:when>
										<c:when test="${order == 'periodAsc'}">企画期間が短い順</c:when>
									</c:choose>
								</span>
								<input type="hidden" name="searchOrder" value="${order}" />
							</div>
						</c:if> <c:if test="${searchGenre != null}">
							<div class="detailData">
								<span class="detailTitle">ジャンル:</span>
								<c:forEach var="genre" items="${searchGenre}">

									<span class="detailInside">${genre.genreName}</span>
									<input type="hidden" name="searchGenre" value="${genre.genreId}" />


								</c:forEach>

							</div>

						</c:if></td>
					<td class="searchBtnColumn"><input type="submit" name="searchDetail" class="btn btn-2 btn-2c searchDetailBtn" value="詳細検索"></td>
				</form>
			</tr>


		</table>
		<hr>

		<div id="list">
			<c:forEach var="plan" items="${planList}">
				<div class="data">
					<div class="title">
						<a href="javascript:Post(${plan.planId})">${plan.planTitle}</a>
					</div>
					<hr>
					<div class="left">
						<table>
							<tr>
								<th class="name">企画者</th>
								<td>${plan.plannerName}</td>
							</tr>
							<tr>
								<th class="period">期間</th>
								<td><fmt:formatDate value="${plan.startDate}" pattern="yyyy年MM月dd日 KK時mm分" /> &nbsp;&sim;&nbsp; <fmt:formatDate value="${plan.endDate}" pattern="yyyy年MM月dd日 KK時mm分" /></td>
							</tr>
							<tr>
								<th class="comments">コメント数</th>
								<td>${plan.commentCount}</td>
							</tr>
							<tr>
								<th class="evaluations">総評価数</th>
								<td>${plan.pointCount}</td>
							</tr>
							<tr>
								<th class="genre">ジャンル</th>
								<td><c:forEach var="genre" items="${genreMap[plan.planId]}" varStatus="status">
										<a href="/HomeSystem/fc/PlanList?search=検索&genre=${genre.genreId}"> <c:out value="${genre.genreName}" />
										</a>
										<c:if test="${!status.last}">
											<c:out value="," />
										</c:if>
									</c:forEach></td>
							</tr>

						</table>
					</div>
					<div class="right">
						<div class="evaluation">
							<span class="like"> <img src="../images/icon/like.png">いいね: <c:out value="${pointMap[plan.planId].goodCount}" default="0" />
							</span> / <span class="dislike"> <img src="../images/icon/dislike.png">ダメだね： <c:out value="${pointMap[plan.planId].badCount}" default="0" />
							</span>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		<form name="form1" method="post" action="/HomeSystem/fc/PlanDetail">
			<input type="hidden" name="detail">
			<input type="hidden" name="planId">
		</form>
		<hr>
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