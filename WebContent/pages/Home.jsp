<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@page import="jp.ac.hal.skymoons.beans.UserBean"%>
<%@page import="jp.ac.hal.skymoons.beans.BatchBean"%>
<%@page import="jp.ac.hal.skymoons.beans.GenreBean"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ホメ</title>

<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="../js/image-picker/image-picker.css">
<link rel="stylesheet" type="text/css" href="../css/Home.css">
<script src="../js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="../js/image-picker/image-picker.js" type="text/javascript"></script>
<script src="../js/image-picker/image-picker.min.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/autosize/autosize.js"></script>
</head>
<body>
	<div id="wrapper">
		<form action="/HomeSystem/fc/Home" method="post">

			<div id="touser">
				<div id="face">
					<img src="../images/employees/${toUser.userId}.jpg">
				</div>
				<div id="name">
					<h3>
						<input type="hidden" name="fromId" value="${fromUser.userId}">
						<p>${toUser.lastName}${toUser.firstName}</p>
						<input type="hidden" name="toId" value="${toUser.userId}">
					</h3>
					<div id="content">
						【関連コンテンツ】<br>
						<c:if test="${contentTitle == null}">
							<p>なし</p>
							<input type="hidden" name="contentsId" value="0">
						</c:if>

						<c:if test="${contentTitle != null}">
							<p>${contentTitle}</p>
							<input type="hidden" name="contentsId" value="${contentsId}">
						</c:if>
					</div>
					<div id="point">
						評価ポイント：
						<input type="number" name="point" min="1" max="10" value="1">
					</div>
				</div>

			</div>

			<div id="batch">
				<select class='image-picker show-html' name="batchId">
					<c:forEach var="batch" items="${batchList}">
						<option data-img-src='../images/batch/${batch.batchId}.png' value='${batch.batchId}'>${batch.batchName}</option>
					</c:forEach>
				</select>
			</div>
			<div id="comment">
				コメント：<br>
				<textarea name="comment" maxlength="500"></textarea>
			</div>
			<input type="submit" name="submit" class="btn btn-2 btn-2c submit" value="送信">


		</form>
	</div>
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
	<script>
		autosize(document.querySelectorAll('textarea'));
	</script>
</body>
</html>