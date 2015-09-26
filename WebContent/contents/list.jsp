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
<script type="text/javascript" src="../../js/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
	function PostContentsDetail(homeContentId) {
		form1.homeContentId.value = homeContentId;
		form1.submit();
	}
	function PostPlanDetail(planId) {
		form2.detail.value = "detail";
		form2.planId.value = planId;
		form2.submit();
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
<script type="text/javascript">
	//画像差し替えメソッド
	$(document).ready(function() {
		$('.js-replace-no-image').error(function() {
			$(this).attr({
				src : '/HomeSystem/images/icon/NoImage.png'
			});
		});
	});
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="../../css/reset.css">
<link rel="stylesheet" type="text/css" href="../../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../../css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="../../css/ContentsCommon.css">
<link rel="stylesheet" type="text/css" href="../../css/ContentsList.css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/style.css">
<script type="text/javascript" src="/HomeSystem/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="/HomeSystem/js/colorbox/jquery.colorbox.js"></script>
<title>コンテンツリスト</title>
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
				<h1>コンテンツ一覧</h1>
				<table class="search">
					<tr>
						<th>キーワード</th>
						<form action="list" method="post" id="formKeyword">
							<td class="searchDataColumn"><input type="text" class="keyword" id="inputKeyword" name="keyword" value="${keyword}" onchange="javascript:change();"></td>
							<td class="searchBtnColumn"><input type="submit" name="search" class="btn btn-2 btn-2c searchBtn" value="検索"></td>
						</form>
					</tr>
					<tr>
						<th>詳細検索</th>
						<form action="search" method="post">
							<input type="hidden" name="searchKeyword" id="outputKeyword" value="${keyword}">
							<td class="searchDetailColumn"><c:if test="${searchPlanner != null}">
									<div class="detailData">
										<span class="detailTitle">企画者:</span> <span class="detailInside">${searchPlannerName}</span> <input type="hidden" name="searchPlanner" value="${searchPlanner}" />
									</div>
								</c:if> <c:if test="${endContent != null}">
									<div class="detailData">
										<span class="detailTitle">オプション:</span> <span class="detailInside">終了していないコンテンツ</span> <input type="hidden" name="endContent" value="${endContent}" />
									</div>
								</c:if> <c:if test="${existPlan != null}">
									<div class="detailData">
										<span class="detailTitle">オプション:</span> <span class="detailInside">企画が存在する物</span> <input type="hidden" name="existPlan" value="${existPlan}" />
									</div>
								</c:if> <c:if test="${order != null}">
									<div class="detailData">
										<span class="detailTitle">順序:</span> <span class="detailInside"> <c:choose>
												<c:when test="${order == 'dateDesc'}">投稿日時が新しい順</c:when>
												<c:when test="${order == 'dateAsc'}">投稿日時が古い順</c:when>
												<c:when test="${order == 'startDesc'}">企画開始日時が新しい順</c:when>
												<c:when test="${order == 'startAsc'}">企画開始日時が古い順</c:when>
												<c:when test="${order == 'endDesc'}">企画終了日時が新しい順</c:when>
												<c:when test="${order == 'endAsc'}">企画終了日時が古い順</c:when>
												<c:when test="${order == 'titleDesc'}">タイトル名昇順</c:when>
												<c:when test="${order == 'titleAsc'}">タイトル名降順</c:when>
												<c:when test="${order == 'nameDesc'}">社員名昇順</c:when>
												<c:when test="${order == 'nameAsc'}">社員名降順</c:when>
											</c:choose>
										</span> <input type="hidden" name="searchOrder" value="${order}" />
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
					<c:forEach items="${contentsList}" var="i">
						<div class="data">
							<div class="title">
								<a href="javascript:PostContentsDetail(${i.homeContentId})">${i.homeContentTitle}</a>
							</div>
							<hr>
							<div class="left">
								<table>
									<tr>
										<th class="name">企画者</th>
										<td><a href="./list?employeeId=${i.employeeId}">${i.lastName} ${i.firstName}</a></td>
									</tr>
									<tr>
										<th class="period">期間</th>
										<td>${i.startDatetime}&nbsp;&sim;&nbsp;${i.endDatetime}</td>
									</tr>
									<tr>
										<th class="comments">関連ホメ数</th>
										<td>${i.homeCount}</td>
									</tr>
									<tr>
										<th class="genre">ジャンル</th>
										<td><c:set var="cnt" value="0" /> <c:forEach var="genreName" items="${i.genreName}" varStatus="status">
												<a href="./list?genreId=${i.genreId[cnt]}"> <c:out value="${genreName}" />
												</a>
												<c:if test="${!status.last}">
													<c:out value="," />
													<c:set var="cnt" value="${cnt + 1}" />
												</c:if>
											</c:forEach></td>
									</tr>

								</table>
							</div>
							<div class="right">
								<div class="evaluation">
									<c:if test="${i.planId != null}">
										<a href="javascript:PostPlanDetail(${i.planId})">このコンテンツの元になった関連企画</a>
									</c:if>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
				<form name="form1" method="post" action="detail">
					<input type="hidden" name="homeContentId">
				</form>
				<form name="form2" method="post" action="/HomeSystem/fc/PlanDetail">
					<input type="hidden" name="detail"> <input type="hidden" name="planId">
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