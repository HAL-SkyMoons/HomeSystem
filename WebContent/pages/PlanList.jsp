<%@page import="jp.ac.hal.skymoons.beans.UserBean"%>
<%@page import="jp.ac.hal.skymoons.beans.GenreBean"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企画一覧</title>
</head>
<body>
	<form action="/HomeSystem/fc/PlanList" method="post">
		<label>キーワード：<input type="text" name="keyword" value="<% if(request.getAttribute("searchKeyword")!=null)out.print(request.getAttribute("searchKeyword")); %>"></label><br>
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
		<br>
		企画者：
		<select name="planner">
			<option value="">--指定なし--
			<%
				for(UserBean employee :employeeList){
					if(employee.getUserId().equals(request.getAttribute("searchPlanner"))){
						out.println("<option value=\""+ employee.getUserId() +"\" selected>"+ employee.getLastName() + employee.getFirstName());
					}else{
						out.println("<option value=\""+ employee.getUserId() +"\">"+ employee.getLastName() + employee.getFirstName());
					}

				}

			%>
		</select> <br> <input type="submit" name="search" value="検索">
	</form>
	<hr>
	<table>
		<tr>
			<th>企画名</th>
			<th>企画者</th>
			<th>企画日</th>
			<th>詳細</th>
		</tr>
		<%
			ArrayList<PlanBean> planList = (ArrayList<PlanBean>) request
					.getAttribute("planList");
			for (PlanBean plan : planList) {
				out.println("<tr>");
				out.println("<td>" + plan.getPlanTitle() + "</td>");
				out.println("<td>" + plan.getPlannerName() + "</td>");
				out.println("<td>" + plan.getPlanDatetime() + "</td>");
				out.println("<td><form action=\"/HomeSystem/fc/PlanDetail\" method=\"post\"><input type=\"submit\" name=\"detail\" value=\"詳細\"><input type=\"hidden\" name=\"planId\" value=\""
						+ plan.getPlanId() + "\"></form></td>");
				out.println("</tr>");
			}
		%>
	</table>
</body>
</html>