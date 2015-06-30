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
<!DOCTYPE htm>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>企画一覧</title>
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="../css/PlanSearch.css">
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
		<form action="/HomeSystem/fc/PlanList" method="post">
			<table class="search">
				<tr>
					<th class="searchTitle">キーワード</th>
					<td class="searchForm">
						<input type="text" name="keyword" class="keyword" />
					</td>
					<td class="help"></td>
				</tr>
				<tr>
					<th class="searchTitle">企画者</th>
					<td class="searchForm">
						<select name="planner">
							<option value="">---指定なし---</option>
							<c:forEach var="employee" items="${employeeList}">
								<option value="${employee.userId}">${employee.lastName}&nbsp;${employee.firstName}</option>
							</c:forEach>
						</select>
					</td>
					<td class="help"></td>
				</tr>
				<tr>
					<th class="searchTitle">オプション</th>
					<td class="searchForm">
						<label><input type="checkbox" name="endPlan" value="check">終了企画を含める</label>
					</td>
					<td class="help"></td>
				</tr>
				<tr>
					<th class="searchTitle">順序</th>
					<td class="searchForm">
						<select name="order">
							<option value="">---指定なし---</option>
							<option value="dateDesc">投稿日時が新しい順</option>
							<option value="dateAsc">投稿日時が古い順</option>
							<option value="commentDesc">コメントが多い順</option>
							<option value="commentAsc">コメントが少ない順</option>
							<option value="evaluationDesc">評価数が多い順</option>
							<option value="evaluationAsc">評価数が少ない順</option>
							<option value="likeDesc">いいねが多い順</option>
							<option value="likeAsc">いいねが少ない順</option>
							<option value="startDesc">企画開始日時が新しい順</option>
							<option value="startAsc">企画開始日時が古い順順</option>
							<option value="periodDesc">企画期間が長い順</option>
							<option value="periodAsc">企画期間が短い順</option>
						</select>
					</td>
					<td class="help"></td>
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

									<td>
										<label><input type="checkbox" name="genre" value="${genre.genreId }">${genre.genreName }</label>
									</td>
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

			<div class="btnZone"><input type="submit" name="search" class="btn btn-2 btn-2c searchBtn" value="検索"></div>

		</form>
	</div>
</body>
</html>