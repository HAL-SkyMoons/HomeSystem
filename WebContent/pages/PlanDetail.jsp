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
	<form action="/HomeSystem/fc/PlanEdit" method="post">
		<table>
			<%
				Utility util = new Utility();
				PlanBean plan = (PlanBean) request.getAttribute("planDetail");
				ArrayList<GenreBean> genres = (ArrayList<GenreBean>) request
						.getAttribute("genre");
				ArrayList<CommentBean> commentList = (ArrayList<CommentBean>) request
						.getAttribute("commentList");
			%>
			<tr>
				<th>企画ID</th>
				<td>
					<%
						out.println(plan.getPlanId());
					%>
				</td>
			</tr>
			<tr>
				<th>企画名</th>
				<td>
					<%
						out.println(plan.getPlanTitle());
					%>
				</td>
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
						out.println(util.nlToBR(plan.getPlanComment()));
					%>
				</td>
			</tr>
			<tr>
				<th>ジャンル</th>
				<td>
					<%
						for (GenreBean genre : genres) {
							out.println("<a href=\"/HomeSystem/fc/PlanList?search=検索&genre="
									+ genre.getGenreId() + "\">");
							out.println(genre.getGenreName());
							out.println("</a>");
						}
					%>
				</td>
			</tr>
		</table>
		<%
			out.println("<input type=\"hidden\" name=\"planId\" value=\"" + plan.getPlanId() + "\">");
		%>
		<input type="submit" name="editSubmit" value="編集">
	</form>
	<hr>
	<form action="/homeSystem/fc/PlanDetail" method="post">
		<input type="submit" name="">
	</form>

	<hr>
	<form action="/HomeSystem/fc/PlanDetail" method="post">
		<label>名前：XXXX</label><br> <label>コメント：<textarea
				name="comment"></textarea></label><br> <input type="submit"
			name="commentSubmit" value="送信"> <input type="hidden"
			name="planId" value="<%out.print(plan.getPlanId());%>"> <input
			type="hidden" name="commentUserId" value="E0001">
	</form>
	<hr>
	<table>
		<tr>
			<td>コメント番号</td>
			<td>コメントユーザー</td>
			<td>コメント</td>
			<td>コメント日</td>
			<td>削除</td>
		</tr>
		<%
			for (CommentBean comment : commentList) {
				out.println("<tr>");
				if (comment.getDeleteFrag() == 0) {
					out.println("<td>" + comment.getCommentNo() + "</td>");
					out.println("<td>" + comment.getCommentName() + "</td>");
					out.println("<td>" + util.nlToBR(comment.getComment())
							+ "</td>");
					out.println("<td>" + comment.getCommentDatetime() + "</td>");
					out.println("<td><form action=\"/HomeSystem/fc/PlanDetail\" method=\"post\"><input type=\"hidden\" name=\"planId\" value=\""
							+ comment.getPlanID()
							+ "\"><input type=\"hidden\" name=\"commentNo\" value=\""
							+ comment.getCommentNo()
							+ "\"><input type=\"submit\" name=\"delete\" value=\"削除\"></form></td>");
				} else {
					out.println("<td>" + comment.getCommentNo() + "</td>");
					out.println("<td>" + comment.getCommentName() + "</td>");
					out.println("<td>このコメントは削除されました</td>");
					out.println("<td>" + comment.getCommentDatetime() + "</td>");
					out.println("<td></td>");

				}

				out.println("</tr>");
			}
		%>
	</table>
</body>
</html>