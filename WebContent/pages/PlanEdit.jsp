<%@page import="jp.ac.hal.skymoons.util.Utility"%>
<%@page import="jp.ac.hal.skymoons.beans.CommentBean"%>
<%@page import="jp.ac.hal.skymoons.beans.GenreBean"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
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
	<form action="/HomeSystem/fc/PlanDetail" method="post">
		<table>
			<%
				PlanBean plan = (PlanBean) request.getAttribute("plan");
					ArrayList<GenreBean> genreList = (ArrayList<GenreBean>) request
							.getAttribute("genreList");
					ArrayList<GenreBean> planGenre = (ArrayList<GenreBean>) request
							.getAttribute("planGenre");
			%>
			<tr>
				<th>企画ID</th>
				<td>
					<%
						out.println(plan.getPlanId());
					%> <input type="hidden" name="planId"
					value=<%out.println(plan.getPlanId());%>>
				</td>
			</tr>
			<tr>
				<th>企画名</th>
				<td><input type="text" name="planTitle"
					value="<%out.println(plan.getPlanTitle());%>"></td>
			</tr>
			<tr>
				<th>企画者</th>
				<td>
					<%
						out.println(plan.getPlannerName());
					%>
				</td>
			</tr>
			<tr>
				<th>企画日</th>
				<td>
					<%
						out.println(plan.getPlanDatetime());
					%>
				</td>
			</tr>
			<tr>
				<th>企画内容</th>
				<td>
					<%
						out.println("<textarea name=\"planComment\">"
											+ plan.getPlanComment() + "</textarea>");
					%>
				</td>
			</tr>
		</table>
		<input type="submit" name="edit" value="登録">
		<hr>
		<%
			int genreIndex = 0;
			int genreMaxIndex = planGenre.size();
			for(GenreBean genre : genreList){
				//入力されたジャンルがない　または　既存ジャンルにチェック済み
				if(genreMaxIndex == genreIndex){
					out.println("<label><input type=\"checkbox\" name=\"genre\" value=\""+  genre.getGenreId()  +"\">" + genre.getGenreName() + "</label>");
				}else{
					//既存ジャンルIDと出力ジャンルIDが一致
					if(planGenre.get(genreIndex).getGenreId() == genre.getGenreId()){
						out.println("<label><input type=\"checkbox\" name=\"genre\" value=\""+  genre.getGenreId()  +"\" checked=\"checked\">" + genre.getGenreName() + "</label>");
						genreIndex ++;
					}else{
						out.println("<label><input type=\"checkbox\" name=\"genre\" value=\""+  genre.getGenreId()  +"\">" + genre.getGenreName() + "</label>");
					}
				}

			}
		%>
	</form>
</body>
</html>