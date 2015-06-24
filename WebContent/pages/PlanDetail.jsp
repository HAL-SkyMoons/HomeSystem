<%@page import="java.text.SimpleDateFormat"%>
<%@page import="jp.ac.hal.skymoons.beans.FileBean"%>
<%@page import="jp.ac.hal.skymoons.beans.UserBean"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanPointBean"%>
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
<title>企画詳細</title>
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="../css/PlanDetail.css">
<script src="../js/jquery-2.1.4.min.js"></script>
<script src="../js/masonry.min.js"></script>

</head>
<%
	Utility util = new Utility();
	PlanBean plan = (PlanBean) request.getAttribute("planDetail");
	ArrayList<GenreBean> genres = (ArrayList<GenreBean>) request
			.getAttribute("genre");
	ArrayList<CommentBean> commentList = (ArrayList<CommentBean>) request
			.getAttribute("commentList");
	PlanPointBean planPoint = (PlanPointBean) request
			.getAttribute("planPoint");
	UserBean user = (UserBean) request.getAttribute("user");

	ArrayList<FileBean> fileList = (ArrayList<FileBean>) request
			.getAttribute("fileList");

	int[] points = (int[]) request.getAttribute("points");

	String good = "";
	String hold = "";
	String bad = "";

	switch (planPoint.getPoint()) {
	case 1:
		good = "disabled";
		break;
	case 2:
		hold = "disabled";
		break;
	case 3:
		bad = "disabled";
		break;
	}
%>
<body>
	<div id="wrapper">
		<div id="plan">
			<div id="planHeader">
				<div id="id">
					No.<%
					out.print(plan.getPlanId());
				%>
				</div>
				<div id="planDate">
					企画日：<%
					out.print(new SimpleDateFormat("yyyy年MM月dd日").format(plan
							.getPlanDatetime()));
				%>
				</div>
			</div>
			<div id="title">
				<%
					out.print(plan.getPlanTitle());
				%>
			</div>
			<div id="planDetail">
				<div id="startEnd">
					実施予定日：<br>
					<%
						if (plan.getStartDate() != null) {
							out.print(new SimpleDateFormat("yyyy年MM月dd日 HH時mm分")
									.format(plan.getStartDate()));
						}
						if (plan.getEndDate() != null) {
							out.print("～");
							out.print(new SimpleDateFormat("yyyy年MM月dd日 HH時mm分")
									.format(plan.getEndDate()));
						}
					%>
					<table>
						<tr>
							<td>
								<form action="/HomeSystem/fc/PlanDetail" method="post">
									<input type="submit" name="evaluationSubmit"
										class="btn btn-2 btn-2c point" value="いいね"
										<%out.println(good);%>> <input type="hidden"
										name="evaluation" value="1"> <input type="hidden"
										name="planId" value=<%out.println(plan.getPlanId());%>>
								</form>
							</td>
							<td>

								<form action="/HomeSystem/fc/PlanDetail" method="post">
									<input type="submit" name="evaluationSubmit"
										class="btn btn-2 btn-2c point" value="保留"
										<%out.println(hold);%>> <input type="hidden"
										name="evaluation" value="2"> <input type="hidden"
										name="planId" value=<%out.println(plan.getPlanId());%>>
								</form>
							</td>
							<td><form action="/HomeSystem/fc/PlanDetail" method="post">
									<input type="submit" name="evaluationSubmit"
										class="btn btn-2 btn-2c point" value="ダメだね"
										<%out.println(bad);%>> <input type="hidden"
										name="evaluation" value="3"> <input type="hidden"
										name="planId" value=<%out.println(plan.getPlanId());%>>
								</form></td>
						</tr>
						<tr>
							<td>
								<div class="point">
									<%
										out.println(points[0]);
									%>
								</div>
							</td>
							<td>
								<div class="point">
									<%
										out.println(points[1]);
									%>
								</div>
							</td>
							<td>
								<div class="point">
									<%
										out.println(points[2]);
									%>
								</div>
							</td>
						</tr>
					</table>
				</div>
				<div id="planner">
					企画者
					<%
					out.print("<div id=\"img\"><img src=\"../img/employees/"
							+ plan.getPlanner()
							+ ".jpg\" width=\"100\" height=\"100\"></div>");
					out.print(plan.getPlannerName());
				%>
				</div>
			</div>
			<div id="planComment">
				<%
					out.println(util.nlToBR(plan.getPlanComment()));
				%>
			</div>
			<div id="genre">
				登録ジャンル<br>
				<%
					for (GenreBean genre : genres) {
						out.println("<div class=\"genres\"><a href=\"/HomeSystem/fc/PlanList?search=検索&genre="
								+ genre.getGenreId() + "\">");
						out.println(genre.getGenreName());
						out.println("</a></div>");
					}
				%>
			</div>
			<form action="/HomeSystem/fc/PlanEdit" method="post">
				<%
					out.println("<input type=\"hidden\" name=\"planId\" value=\""
							+ plan.getPlanId() + "\">");
					if (plan.getPlanner().equals(user.getUserId())) {
						out.println("<input type=\"submit\" name=\"editSubmit\" value=\"編集\" class=\"btn btn-2 btn-2c edit\">");
					}
				%>
			</form>



			<hr>
			<h3>添付ファイル</h3>
			<div id="files">
				<%
					for (FileBean file : fileList) {
						out.println("<div class=\"file\">");
						out.println("<div class=\"fileImage\">");
						out.println("<img src=\""
								+ util.getFileImage(file.getDataName()) + "\">");
						out.println("</div>");
						if (plan.getPlanner().equals(user.getUserId())) {
							out.println("<div class=\"del\">");
							out.println("<form action=\"/HomeSystem/fc/PlanDetail\" method=\"post\"><input type=\"image\" src=\"../images/icon/del.png\" width=\"15\" height=\"15\" name=\"fileDelete\" value=\"削除\"><input type=\"hidden\" name=\"planId\" value=\""
									+ plan.getPlanId()
									+ "\"/><input type=\"hidden\" name=\"dataNo\" value=\""
									+ file.getDataNo()
									+ "\"/><input type=\"hidden\" name=\"fileName\" value=\""
									+ file.getDataName()
									+ "\"/><input type=\"hidden\" name=\"fileDelete\" value=\"del\"/></form>");
							out.println("</div>");
						}
						out.println("<div class=\"fileName\">" + file.getDataName()
								+ "</div>");
						String path = request.getServletContext().getRealPath(
								"/files/plan/master/" + file.getPlanId() + "/"
										+ file.getDataNo() + "/" + file.getDataName());
						out.println("<form action=\"/HomeSystem/fc/PlanDetail\" method=\"post\"><input type=\"hidden\" name=\"planId\" value=\""
								+ plan.getPlanId()
								+ "\"/><input type=\"hidden\" name=\"path\" value=\""
								+ path
								+ "\"/><input type=\"hidden\" name=\"fileName\" value=\""
								+ file.getDataName()
								+ "\"/><input type=\"submit\" name=\"download\" value=\"ダウンロード\" class=\"btn btn-2 btn-2c download\"/></form>");
						System.out.println(path);

						out.println("</div>");
					}
				%>
			</div>
			<div id="upload">
				<%
					if (plan.getPlanner().equals(user.getUserId())) {
						out.println("<form method=\"POST\" enctype=\"multipart/form-data\" action=\"/HomeSystem/fc/PlanDetail\">");
						out.println("<input type=\"hidden\" name=\"planId\" value="
								+ plan.getPlanId() + " />");
						// 						out.println("<input type=\"file\" name=\"file\" />");

						out.println("<input type=\"file\" name=\"file\" id=\"file\" style=\"display:none;\" onchange=\"$('#fake_input_file').text($(this)[0].files[0].name)\">");
						out.println("<input type=\"button\" class=\"btn btn-2 btn-2c uploadBtn\" value=\"ファイル選択\" onClick=\"$('#file').click();\">");
						out.println("<span id=\"fake_input_file\"></span>");
						out.println("<br>");
						out.println("<input type=\"submit\" name=\"upload\" value=\"送信\" class=\"btn btn-2 btn-2c uploadBtn\"/>");
						out.println("</form>");
					}
				%>
			</div>
		</div>
		<hr>
		<form action="/HomeSystem/fc/PlanDetail" method="post">
			<table>
				<tr>
					<td>ユーザ名： <%
						out.print(user.getLastName() + user.getFirstName());
					%>
					</td>
				</tr>
				<tr>
					<td>コメント</td>
				</tr>
				<tr>
					<td><textarea name="comment" id="comment"></textarea></td>
				</tr>
			</table>
			<input type="submit" name="commentSubmit" value="送信"
				class="btn btn-2 btn-2c commentSubmit"> <input type="hidden"
				name="planId" value="<%out.print(plan.getPlanId());%>"> <input
				type="hidden" name="commentUserId"
				value="<%out.print(user.getUserId());%>">
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

						System.out.print(comment.getCommentUser() + ":"
								+ user.getUserId());

						if (comment.getCommentUser().equals(user.getUserId())) {
							out.println("<td><form action=\"/HomeSystem/fc/PlanDetail\" method=\"post\"><input type=\"hidden\" name=\"planId\" value=\""
									+ comment.getPlanID()
									+ "\"><input type=\"hidden\" name=\"commentNo\" value=\""
									+ comment.getCommentNo()
									+ "\"><input type=\"submit\" name=\"delete\" value=\"削除\" class=\"btn btn-2 btn-2c\"></form></td>");
						} else {
							out.println("<td></td>");
						}

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
	</div>
</body>
</html>