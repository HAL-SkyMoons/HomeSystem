<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@page import="jp.ac.hal.skymoons.beans.UserBean"%>
<%@page import="jp.ac.hal.skymoons.beans.BatchBean"%>
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
<title>ホメ</title>
<link rel="stylesheet" type="text/css"
	href="../js/image-picker/image-picker.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="../css/bootstrap-responsive.css">
<script src="../js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="../js/image-picker/image-picker.js" type="text/javascript"></script>
<script src="../js/image-picker/image-picker.min.js"
	type="text/javascript"></script>
<script src="../js/masonry.min.js" type="text/javascript"></script>
</head>
<body>
	<form action="/HomeSystem/fc/Home" method="post">
		<table>
			<tr>
				<th>From：</th>
				<td>
					<%
						UserBean fromUser = (UserBean)request.getAttribute("fromUser");
													out.println(fromUser.getLastName() + fromUser.getFirstName());
													out.println("<input type=\"hidden\" name=\"fromId\" value=\""+ fromUser.getUserId() +"\">");
					%>
				</td>

			</tr>
			<tr>
				<th>To：</th>
				<td>
					<%
						UserBean toUser = (UserBean)request.getAttribute("toUser");
																	out.print(toUser.getLastName() + toUser.getFirstName());
																	out.print("<input type=\"hidden\" name=\"toId\" value=\""+ toUser.getUserId() +"\">");
					%>
				</td>
			</tr>
			<tr>
				<th>バッチ：</th>
				<td><div class="picker">
						<select class='image-picker show-html' name="batchId">
							<%
								ArrayList<BatchBean> batchList = (ArrayList<BatchBean>) request.getAttribute("batchList");
																																	for (BatchBean batch : batchList) {
																																		out.print("<option data-img-src='../images/batch/" + batch.getBatchId() + ".png' value='" + batch.getBatchId() + "'>" + batch.getBatchName() + "</option>");
																																	}
							%>
						</select>
					</div></td>
			</tr>
			<tr>
				<th>ポイント：</th>
				<td><input type="number" name="point" min="1" max="5" value="1"></td>
			</tr>
			<tr>
				<th>コメント：</th>
				<td><textarea rows="" cols="" name="comment"></textarea></td>
			</tr>
			<tr>
				<th>コンテンツタイトル：</th>
				<td>
					<%
						if(request.getParameter("contentsId") == null){
																			out.println("なし");
																			out.println("<input type=\"hidden\" name=\"contentsId\" value=\"0\">" );
																		}else{
																			out.println(request.getAttribute("contentTitle"));
																			out.println("<input type=\"hidden\" name=\"contentsId\" value=\""+request.getParameter("contentsId")+"\">" );
																		}
					%>
				</td>
			</tr>

		</table>

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

		<input type="submit" name="submit" value="送信">
	</form>




</body>
</html>