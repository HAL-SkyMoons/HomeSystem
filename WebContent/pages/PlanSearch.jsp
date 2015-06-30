<%@page import="java.util.Iterator"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanPointsBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="jp.ac.hal.skymoons.beans.UserBean"%>
<%@page import="jp.ac.hal.skymoons.beans.GenreBean"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企画一覧</title>
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="../css/bootstrap-theme.min.css"
>
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
		<form action="/HomeSystem/fc/PlanList" method="post">
			<label>キーワード：<input type="text" name="keyword"
				value="<%if (request.getAttribute("searchKeyword") != null)
		out.print(request.getAttribute("searchKeyword"));%>"
			></label><br>
			<%
			    ArrayList<GenreBean> genreList = (ArrayList<GenreBean>) request
					    .getAttribute("genreList");
			    String[] searchGenre = (String[]) request
					    .getAttribute("searchGenre");
			    ArrayList<UserBean> employeeList = (ArrayList<UserBean>) request
					    .getAttribute("employeeList");

			    int genreIndex = 0;
			    int genreMaxIndex = 0;

			    if (searchGenre != null)
					genreMaxIndex = searchGenre.length;

			    for (GenreBean genre : genreList) {
					//検索ジャンル指定なし　または　既存検索ジャンル出力済み
					if (genreMaxIndex == genreIndex) {
					    out.println("<label><input type=\"checkbox\" name=\"genre\" value=\""
						    + genre.getGenreId()
						    + "\">"
						    + genre.getGenreName()
						    + "</label>");
					} else {
					    if (Integer.valueOf(searchGenre[genreIndex]) == genre
						    .getGenreId()) {
						out.println("<label><input type=\"checkbox\" name=\"genre\" value=\""
							+ genre.getGenreId()
							+ "\" checked=\"checked\">"
							+ genre.getGenreName() + "</label>");
						genreIndex++;
					    } else {
						out.println("<label><input type=\"checkbox\" name=\"genre\" value=\""
							+ genre.getGenreId()
							+ "\">"
							+ genre.getGenreName() + "</label>");
					    }

					}

			    }
			%>
			<br> 企画者： <select name="planner">
				<option value="">--指定なし--
					<%
				    for (UserBean employee : employeeList) {
						if (employee.getUserId().equals(
							request.getAttribute("searchPlanner"))) {
						    out.println("<option value=\"" + employee.getUserId()
							    + "\" selected>" + employee.getLastName()
							    + employee.getFirstName());
						} else {
						    out.println("<option value=\"" + employee.getUserId()
							    + "\">" + employee.getLastName()
							    + employee.getFirstName());
						}

				    }
				%>

			</select> <br> <input type="submit" name="search" value="検索">
		</form>
		<hr>
		<form>
			<table>
				<tr>
					<th>キーワード：</th>
					<td><input type="text" name="keyword" /></td>
				</tr>
				<tr>
					<th>企画者：</th>
					<td><select>
							<option value="">-指定なし-</option>
							<c:forEach var="employee" items="${employeeList}">
								<option value="${employee.userId}">${employee.lastName}${employee.firstName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th>オプション：</th>
					<td><label><input type="checkbox">終了企画を含める</label></td>
				</tr>
				<tr>
					<th>順序：</th>
					<td><select>
							<option>-指定なし-</option>
							<option>投稿日時が新しい順</option>
							<option>投稿日時が古い順</option>
							<option>コメントが多い順</option>
							<option>コメントが少ない順</option>
							<option>評価数が多い順</option>
							<option>評価数が少ない順</option>
							<option>いいねが多い順</option>
							<option>いいねが少ない順</option>
							<option>企画開始日時が新しい順</option>
							<option>企画開始日時が古い順順</option>
							<option>企画期間が長い順</option>
							<option>企画期間が短い順</option>
					</select></td>
				</tr>
			</table>

			<c:forEach var="bigGenre" items="${bigGenreList}">
				<span class="trigger">&#9661;${bigGenre.bigGenreName }</span>
				<div class="toggle_container">
					<table>
						<tr>
							<c:forEach var="genre" items="${genreList}" varStatus="status">

								<c:choose>
									<c:when test="${genre.bigigGenreId == bigGenre.bigigGenreId}">
										<td><label><input type="checkbox">${genre.getGenreName() }</label></td>
									</c:when>
									<c:otherwise>
                                        ${status.count}-1
                                    </c:otherwise>
								</c:choose>

								<c:if test="${status.count%3 ==0}">
						</tr>
						<tr>
							</c:if>
							</c:forEach>
						</tr>
					</table>
				</div>

			</c:forEach>
			<span class="trigger">&#9661;業務</span>

			<div class="toggle_container">
				<table>
					<tr>
						<c:forEach var="genre" items="${genreList}" varStatus="status">
							<td><label><input type="checkbox">${genre.getGenreName() }</label></td>
							<c:if test="${status.count%5 ==0}">
					</tr>
					<tr>
						</c:if>
						</c:forEach>
					</tr>
				</table>
			</div>

			<br> <span class="trigger">&#9661;非業務</span>

			<div class="toggle_container">
				<table>
					<tr>
						<td>a</td>
						<td>a</td>
						<td>a</td>
					</tr>
					<tr>
						<td>a</td>
						<td>a</td>
						<td>a</td>
					</tr>
					<tr>
						<td>a</td>
						<td>a</td>
						<td>a</td>
					</tr>
				</table>
			</div>
		</form>
</body>
</html>