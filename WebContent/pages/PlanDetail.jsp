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
<link rel="stylesheet" type="text/css"
	href="../js/colorbox/colorbox.css">
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../js/masonry.min.js"></script>
<script type="text/javascript" src="../js/autosize/autosize.js"></script>
<script type="text/javascript" src="../js/colorbox/jquery.colorbox.js"></script>
<script type="text/javascript">
	//　イメージポップアップ表示
	$(function() {
		$(".iframe").colorbox({
			iframe : true,
			width : "500px",
			height : "90%",
			opacity : 0.7
		});
	});
</script>
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
					out.print("<div id=\"img\"><img src=\"../images/employees/"
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
					<td><textarea name="comment" id="planComment"></textarea></td>

				</tr>

			</table>

			<input type="submit" name="commentSubmit" value="送信"
				class="btn btn-2 btn-2c commentSubmit"> <input type="hidden"
				name="planId" value="<%out.print(plan.getPlanId());%>"> <input
				type="hidden" name="commentUserId"
				value="<%out.print(user.getUserId());%>">
		</form>

		<hr>
		<!-- 		<div id="comments"> -->
		<!-- 			<div class="commentData"> -->
		<!-- 				<div class="planner"> -->
		<!-- 					<div class="face"> -->
		<!-- 						<img src="../images/employees/E0001.jpg"> -->
		<!-- 					</div> -->
		<!-- 					<div class="name">木村拓哉</div> -->
		<!-- 					<div class="home"> -->
		<!-- 						<input type="button" class="btn btn-2 btn-2c" value="ホメ"> -->
		<!-- 					</div> -->
		<!-- 				</div> -->
		<!-- 				<div class="plannerComment"> -->
		<!-- 					<div class="commentHeader"> -->
		<!-- 						<div class="commentNo">No.1</div> -->
		<!-- 						<div class="commentBody"> -->
		<!-- 							コメント<br>コメント<br>コメント<br>コメント<br>コメント<br>コメント<br>コメント -->
		<!-- 						</div> -->
		<!-- 						<div class="commentDate">2015-06-24</div> -->
		<!-- 					</div> -->
		<!-- 					<div class="commentFooter"> -->
		<!-- 						<div class="commentFile"> -->
		<!-- 							<div class="file"> -->
		<!-- 								<div class="fileImage"> -->
		<!-- 									<img src="../images/icon/txt.png"> -->
		<!-- 								</div> -->
		<!-- 								<div class="del"> -->
		<!-- 									<form action="/HomeSystem/fc/PlanDetail" method="post"> -->
		<!-- 										<input type="image" src="../images/icon/del.png" width="15" -->
		<!-- 											height="15" name="fileDelete" value="削除"><input -->
		<!-- 											type="hidden" name="planId" value="1" /><input type="hidden" -->
		<!-- 											name="dataNo" value="1" /><input type="hidden" -->
		<!-- 											name="fileName" value="データベース変更依頼.txt" /><input -->
		<!-- 											type="hidden" name="fileDelete" value="del" /> -->
		<!-- 									</form> -->
		<!-- 								</div> -->
		<!-- 								<div class="fileName">データベース変更依頼.txt</div> -->
		<!-- 								<form action="/HomeSystem/fc/PlanDetail" method="post"> -->
		<!-- 									<input type="hidden" name="planId" value="1" /><input -->
		<!-- 										type="hidden" name="path" -->
		<!-- 										value="C:\School\IH4C\ケーススタディ\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\HomeSystem\files\plan\master\1\1\データベース変更依頼.txt" /><input -->
		<!-- 										type="hidden" name="fileName" value="データベース変更依頼.txt" /><input -->
		<!-- 										type="submit" name="download" value="ダウンロード" -->
		<!-- 										class="btn btn-2 btn-2c download" /> -->
		<!-- 								</form> -->
		<!-- 							</div> -->

		<!-- 						</div> -->
		<!-- 					</div> -->
		<!-- 				</div> -->
		<!-- 			</div> -->
		<!-- 			<div class="commentData"> -->
		<!-- 				<div class="gest"> -->
		<!-- 					<div class="face"> -->
		<!-- 						<img src="../images/employees/E0001.jpg"> -->
		<!-- 					</div> -->
		<!-- 					<div class="name">木村拓哉</div> -->
		<!-- 					<div class="home"> -->
		<!-- 						<input type="button" class="btn btn-2 btn-2c" value="ホメ"> -->
		<!-- 					</div> -->
		<!-- 				</div> -->
		<!-- 				<div class="gestComment"> -->
		<!-- 					<div class="commentHeader"> -->
		<!-- 						<div class="commentNo">No.1</div> -->
		<!-- 						<div class="commentBody"> -->
		<!-- 							コメント<br>コメント<br>コメント<br>コメント<br>コメント -->
		<!-- 						</div> -->
		<!-- 						<div class="commentDate">2015-06-24</div> -->
		<!-- 					</div> -->
		<!-- 					<div class="commentFooter"></div> -->
		<!-- 				</div> -->
		<!-- 			</div> -->

		<!-- 		</div> -->
		<div id="comments">
			<%
				boolean isPlanner = false;
						for (CommentBean comment : commentList) {

							if (comment.getDeleteFrag() == 0) {

								out.println("<div class=\"commentData\">");
								if (plan.getPlanner().equals(comment.getCommentUser())) {
									isPlanner = true;
								} else {
									isPlanner = false;
								}

								if (isPlanner) {
									out.println("<div class=\"planner\">");
								} else {
									out.println("<div class=\"gest\">");
								}

								out.println("<div class=\"face\"><img src=\"../images/employees/"
										+ comment.getCommentUser() + ".jpg\"></div>");
								out.println("<div class=\"name\">"
										+ comment.getCommentName() + "</div>");
								out.println("<div class=\"home\">");

								if (!user.getUserId().equals(comment.getCommentUser())) {
									out.println("<a href=\"/HomeSystem/fc/Home?toUser="+ comment.getCommentUser()+"\" class=\"iframe\"><input type=\"button\" name=\"submit\" class=\"btn btn-2 btn-2c\" value=\"ホメ\"/></a>");
								} else {
									//out.println("<input type=\"button\" name=\"submit\" class=\"btn btn-2 btn-2c\" disabled=\"disabled\" value=\"ホメ\"/>");
								}

								out.println("</div>");//planner or gest

								out.println("</div>");//planner or gest

								if (isPlanner) {
									out.println("<div class=\"plannerComment\">");
								} else {
									out.println("<div class=\"gestComment\">");
								}
								out.println("<div class=\"commentHeader\">");

								out.println("<div class=\"commentNo\">");
								out.println(comment.getCommentNo());

								if (comment.getCommentUser().equals(user.getUserId())
										&& comment.getDeleteFrag() == 0) {
									out.println("<div class=\"commentDelete\"><form action=\"/HomeSystem/fc/PlanDetail\" method=\"post\"><input type=\"hidden\" name=\"planId\" value=\""
											+ comment.getPlanID()
											+ "\"><input type=\"hidden\" name=\"commentNo\" value=\""
											+ comment.getCommentNo()
											+ "\"><input type=\"image\" src=\"../images/icon/del.png\" name=\"delete\" value=\"削除\" class=\"commentDel\"></form></div>");
								}

								out.println("</div>");//commentNo

								out.println("<div class=\"commentBody\">");
								if (comment.getDeleteFrag() == 0) {
									//コメント表示
									out.println(util.nlToBR(comment.getComment()));
								} else {
									//コメント非表示
									out.println("このコメントは削除されました");

								}
								out.println("</div>");//commentBody

								out.println("<div class=\"commentDate\">");
								out.println(comment.getCommentDatetime());
								out.println("</div>");//commentDate

								out.println("</div>");//commentHeader

								out.println("<div class=\"commenFootert\">");

								out.println("<div class=\"commentFile\">");
								out.println("</div>");//commentFile

								out.println("</div>");//commenFootert

								out.println("</div>");//plannerComment or gestComment
								out.println("</div>");//commentData

							}//コメント削除

						}
			%>
		</div>
	</div>
</body>
<script>
	autosize(document.querySelectorAll('textarea'));
</script>
</html>