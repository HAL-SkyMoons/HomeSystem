<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@page import="jp.ac.hal.skymoons.beans.UserBean"%>
<%@page import="jp.ac.hal.skymoons.beans.BatchBean"%>
<%@page import="jp.ac.hal.skymoons.beans.GenreBean"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ホメ</title>

<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css"
	href="../js/image-picker/image-picker.css">
<link rel="stylesheet" type="text/css" href="../css/Home.css">
<script src="../js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="../js/image-picker/image-picker.js" type="text/javascript"></script>
<script src="../js/image-picker/image-picker.min.js"
	type="text/javascript"></script>
</head>
<body>
	<form action="/HomeSystem/fc/Home" method="post">
		<div id="wrapper">
			<div id="touser">
				<div id="face">
					<img src="../images/employees/E0001.jpg">
				</div>
				<div id="name">
					<h3>
						<%
						UserBean fromUser = (UserBean)request.getAttribute("fromUser");
						out.println("<input type=\"hidden\" name=\"fromId\" value=\""+ fromUser.getUserId() +"\">");
							UserBean toUser = (UserBean)request.getAttribute("toUser");
										out.print(toUser.getLastName() + toUser.getFirstName());
										out.print("<input type=\"hidden\" name=\"toId\" value=\""+ toUser.getUserId() +"\">");
						%>
					</h3>
					<div id="content">
						関連コンテンツ：
						<%
						if(request.getParameter("contentsId") == null){
										out.println("なし");
										out.println("<input type=\"hidden\" name=\"contentsId\" value=\"0\">" );
									}else{
										out.println(request.getAttribute("contentTitle"));
										out.println("<input type=\"hidden\" name=\"contentsId\" value=\""+request.getParameter("contentsId")+"\">" );
									}
					%>
					</div>
					<div id="point">
						評価ポイント：
						<input type="number" name="point" min="1" max="10" value="1">
					</div>
				</div>

			</div>

			<div id="batch">
				<select class='image-picker show-html' name="batchId">
					<%
						ArrayList<BatchBean> batchList = (ArrayList<BatchBean>) request.getAttribute("batchList");
												for (BatchBean batch : batchList) {
													out.print("<option data-img-src='../images/batch/" + batch.getBatchId() + ".png' value='" + batch.getBatchId() + "'>" + batch.getBatchName() + "</option>");
												}
					%>
				</select>
			</div>
			<div id="comment">
				コメント：<br>
				<textarea name="comment" maxlength="1000"></textarea>
			</div>
			<input type="submit" name="submit" class="btn btn-2 btn-2c submit"
				value="送信">
		</div>

	</form>
	<script type="text/javascript">
		jQuery("select.image-picker").imagepicker({
			hide_select : false,
		});

		jQuery("select.image-picker.show-labels").imagepicker({
			hide_select : false,
			show_label : true,
		});

		var container = jQuery("select.image-picker.masonry").next(
				"ul.thumbnails");
		container.imagesLoaded(function() {
			container.masonry({
				itemSelector : "li",
			});
		});
	</script>
</body>
</html>