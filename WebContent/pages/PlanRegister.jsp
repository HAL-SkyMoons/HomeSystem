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
<title>企画登録</title>
</head>
<%
	UserBean user = (UserBean)request.getAttribute("user");
%>
<body>
	<form action="/HomeSystem/fc/PlanRegister" method="post">
		<table>
			<tr>
				<th>企画者：</th>
				<td><% out.println(user.getLastName() +  user.getFirstName()); %><input type="hidden" name="planner" value="<% out.print(user.getUserId()); %>"></td>
			</tr>
			<tr>
				<th>企画名：</th>
				<td><input type="text" name="planTitle"></td>
			</tr>
			<tr>
				<th>企画内容：</th>
				<td><textarea col="30" rows="10" name="planComment"></textarea></td>
			</tr>
		</table>
		<input type="submit" name="submit" value="登録"><br>
		<%
			ArrayList<GenreBean> genreList = (ArrayList<GenreBean>) request
					.getAttribute("genreList");
			for (GenreBean genre : genreList) {
				out.println("<label><input type=\"checkbox\" name=\"genre\" value=\""+ genre.getGenreId()+":"+ genre.getGenreName() +"\">"+genre.getGenreName() + "</label>");
			}
		%>
	</form>
</body>
</html>