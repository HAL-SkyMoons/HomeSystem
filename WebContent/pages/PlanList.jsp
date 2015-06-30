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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html">
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>企画一覧</title>
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="../css/PlanList.css">
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
</head>
<body>
	<div class="wrapper">
		<table class="search">
			<tr>
				<th>キーワード</th>
				<form action="/HomeSystem/fc/PlanList" method="post">
					<td class="searchDataColumn">
						<input type="text" class="keyword" name="keyword" value="${searchKeyword}">
					</td>
					<td class="searchBtnColumn">
						<input type="submit" name="search" class="btn btn-2 btn-2c searchBtn" value="検索">
					</td>
				</form>
			</tr>
			<tr>
				<th>詳細検索</th>
				<form action="/HomeSystem/fc/PlanSearch" method="post">
					<td class="searchDetailColumn">
						<c:if test="${searchPlanner != null}">
							<div class="detailData">
								<span class="detailTitle">企画者:</span>
								<span class="detailInside">${searchPlannerName}</span>
							</div>
						</c:if>
						<c:if test="${endPlan != null}">
							<div class="detailData">
								<span class="detailTitle">オプション:</span>
								<span class="detailInside">終了済企画を含む</span>
							</div>
						</c:if>
						<c:if test="${order != null}">
							<div class="detailData">
								<span class="detailTitle">順序:</span>
								<span class="detailInside">

									<c:choose>
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
							</div>
						</c:if>

						<c:if test="${searchGenre != null}">
							<div class="detailData">
								<span class="detailTitle">ジャンル:</span>
								<c:forEach var="genre" items="${searchGenre}">

									<span class="detailInside">${genre.genreName}</span>

								</c:forEach>
							</div>

						</c:if>
					</td>
					<td class="searchBtnColumn">
						<input type="submit" name="searchDetail" class="btn btn-2 btn-2c searchDetailBtn" value="詳細検索">
					</td>
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
								<td>
									<fmt:formatDate value="${plan.startDate}" pattern="yyyy年MM月dd日 KK時mm分" />
									&nbsp;&sim;&nbsp;
									<fmt:formatDate value="${plan.endDate}" pattern="yyyy年MM月dd日 KK時mm分" />
								</td>
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
								<td>
									<c:forEach var="genre" items="${genreMap[plan.planId]}" varStatus="status">
										<a href="/HomeSystem/fc/PlanList?search=検索&genre=${genre.genreId}"> <c:out value="${genre.genreName}" />
										</a>
										<c:if test="${!status.last}">
											<c:out value="," />
										</c:if>
									</c:forEach>
								</td>
							</tr>

						</table>
					</div>
					<div class="right">
						<div class="evaluation">
							<span class="like">
								<img src="../images/icon/like.png">いいね:
								<c:out value="${pointMap[plan.planId].goodCount}" default="0" />
							</span>
							/
							<span class="dislike">
								<img src="../images/icon/dislike.png">ダメだね：
								<c:out value="${pointMap[plan.planId].badCount}" default="0" />
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
</body>
</html>