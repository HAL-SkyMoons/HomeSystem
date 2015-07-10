<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	SessionController sessionController = new SessionController(request);
	String url = sessionController.checkUserSession();
	if(url != null) {
		response.sendRedirect(url);
	}
%>
<!DOCTYPE html>
<script type="text/javascript" src="../../js/jquery-2.1.4.min.js"></script>
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
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>コンテンツ検索</title>
		<link rel="stylesheet" type="text/css" href="../../css/reset.css">
		<link rel="stylesheet" type="text/css" href="../../css/bootstrap.css">
		<link rel="stylesheet" type="text/css" href="../../css/bootstrap-theme.min.css">
		<link rel="stylesheet" type="text/css" href="../../css/PlanSearch.css">
	</head>
	<body>
		<div class="wrapper">
			<form action="list" method="post">
				<table class="search">
					<tr>
						<th class="searchTitle">キーワード</th>
						<td class="searchForm"><input type="text" name="keyword" class="keyword" value="${searchKeyword}" /></td>
						<td class="help"></td>
					</tr>
					<tr>
						<th class="searchTitle">企画者</th>
						<td class="searchForm">
							<select name="employeeId">
								<option value="">---指定なし---</option>
								<c:forEach items="${employeeList}" var="j">
									
									<c:if test="${searchPlanner != null && searchPlanner == j.employeeId}">
										<option value="${j.employeeId}" selected="selected">${j.lastName}&nbsp;${j.firstName}</option>
									</c:if>
									<c:if test="${searchPlanner == null || searchPlanner != j.employeeId}">
										<option value="${j.employeeId}">${j.lastName}&nbsp;${j.firstName}</option>
									</c:if>
								</c:forEach>
							</select>
						<td class="help"></td>
					</tr>
					<tr>
						<th class="searchTitle">オプション</th>
						<td class="searchForm">
							<label>
								<c:if test="${endContent != null}">
									<input type="checkbox" name="endContent" value="true" checked="checked"/>
								</c:if> <c:if test="${endContent == null}">
									<input type="checkbox" name="endContent" value="true"/>
								</c:if> 終了していないコンテンツのみを検索
							</label>
							<label>
								<c:if test="${existPlan != null}">
									<input type="checkbox" name="existPlan" value="true" checked="checked"/>
								</c:if> <c:if test="${existPlan == null}">
									<input type="checkbox" name="existPlan" value="true"/>
								</c:if> 企画が存在する物のみを検索
							</label>
						</td>
						<td class="help"></td>
					</tr>
					<tr>
						<th class="searchTitle">順序</th>
						<td class="searchForm">
							<select name="order">
								<option value="">---指定なし---</option>
								<c:forEach var="order" items="${orderList}">
									<c:if test="${searchOrder != null && order.key == searchOrder}">
										<option value="${order.key}" selected="selected">${order.value}</option>
									</c:if>
									<c:if test="${searchOrder == null || order.key != searchOrder}">
										<option value="${order.key}">${order.value}</option>
									</c:if>
								</c:forEach>
							</select>
						</td>
						<td class="help"></td>
					</tr>
				</table>
	
				<c:forEach var="bigGenre" items="${bigGenreList}">
					<span class="trigger">&#9661;${bigGenre.bigGenreName}</span>
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
	</body>
</html>