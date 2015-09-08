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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE htm>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>企画詳細</title>
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="../css/PlanDetail.css">
<link rel="stylesheet" type="text/css" href="../js/colorbox/colorbox.css">
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
<body>
	<div id="wrapper">
		<div id="plan">
			<div id="planHeader">
				<div id="id">No.${planDetail.planId}</div>
				<div id="planDate">
					企画日：
					<fmt:formatDate value="${planDetail.planDatetime}" pattern="yyyy年MM月dd日" />

				</div>
			</div>
			<div id="title"><span id="titleValue">${planDetail.planTitle}</span></div>
			<div id="planDetail">
				<div id="startEnd">
					実施予定日：<br>
					<fmt:formatDate value="${planDetail.startDate}" pattern="yyyy年MM月dd日 KK時mm分" />
					&nbsp;&sim;&nbsp;
					<fmt:formatDate value="${planDetail.endDate}" pattern="yyyy年MM月dd日 KK時mm分" />
					<table id="evaluation">
						<c:choose>
							<c:when test="${planPoint.point == 1}">
								<c:set var="good" value="disabled" />
							</c:when>
							<c:when test="${planPoint.point == 3}">
								<c:set var="bad" value="disabled" />
							</c:when>
						</c:choose>
						<tr>
							<td>
								<form action="/HomeSystem/fc/PlanDetail" method="post">
									<input type="submit" name="evaluationSubmit" class="btn btn-2 btn-2c point" value="いいね" ${good}>
									<input type="hidden" name="evaluation" value="1">
									<input type="hidden" name="planId" value="${planDetail.planId}">
								</form>
							</td>
							<td><form action="/HomeSystem/fc/PlanDetail" method="post">
									<input type="submit" name="evaluationSubmit" class="btn btn-2 btn-2c point" value="ダメだね" ${bad}>
									<input type="hidden" name="evaluation" value="3">
									<input type="hidden" name="planId" value="${planDetail.planId}">
								</form></td>
						</tr>
						<tr>
							<td>
								<div class="point">${points[0]}</div>
							</td>
							<td>
								<div class="point">${points[2]}</div>
							</td>
						</tr>
					</table>
				</div>
				<div id="planner">
					企画者
					<div id="img">
						<img src="../images/employees/${planDetail.planner}.jpg">
					</div>
					${planDetail.plannerName}

				</div>
			</div>
			<div id="planComment">${Utility.nlToBR(planDetail.planComment)}</div>
			<div id="genre">
				登録ジャンル<br>
				<c:forEach var="genre" items="${genre}">
					<div class="genres">
						<a href="/HomeSystem/fc/PlanList?search=検索&genre=${genre.genreId}">${genre.genreName}</a>
					</div>
				</c:forEach>
			</div>
			<form action="/HomeSystem/fc/PlanEdit" method="post">
				<input type="hidden" name="planId" value="${planDetail.planId}">
				<c:if test="${planDetail.planner == user.userId && planDetail.executeFlag == 0}">
					<input type="submit" name="editSubmit" value="編集" class="btn btn-2 btn-2c edit" />
				</c:if>
			</form>
			<hr>
			<h3>添付ファイル</h3>
			<div id="files">
				<c:forEach var="file" items="${fileList}">
					<div class="file">
						<div class="fileImage">
							<img src="${Utility.getFileImage(file.dataName)}">
						</div>
						<c:if test="${planDetail.planner == user.userId && planDetail.executeFlag == 0}">
							<div class="del">
								<form action="/HomeSystem/fc/PlanDetail" method="post">
									<input type="image" src="../images/icon/del.png" width="15" height="15" name="fileDelete" value="削除" />
									<input type="hidden" name="planId" value="${planDetail.planId}" />
									<input type="hidden" name="dataNo" value="${file.dataNo}" />
									<input type="hidden" name="fileName" value="${file.dataName}" />
									<input type="hidden" name="fileDelete" value="del" />
								</form>
							</div>
						</c:if>
						<div class="fileName">${file.dataName}</div>

						<form action="/HomeSystem/fc/PlanDetail" method="post">
							<input type="hidden" name="planId" value="${planDetail.planId}" />
							<input type="hidden" name="path" value="/files/plan/master/${file.planId}/${file.dataNo}/${file.dataName}" />
							<input type="hidden" name="fileName" value="${file.dataName}" />
							<input type="submit" name="download" value="ダウンロード" class="btn btn-2 btn-2c download" />
						</form>
					</div>
				</c:forEach>
			</div>
			<div id="upload">
				<c:if test="${planDetail.planner == user.userId && planDetail.executeFlag == 0}">
					<form action="/HomeSystem/fc/PlanDetail" method="post" enctype="multipart/form-data">
						<input type="hidden" name="planId" value="${planDetail.planId}" />
						<input type="file" name="file" id="file" onchange="$('#fake_input_file').text($(this)[0].files[0].name)" />
						<input type="button" class="btn btn-2 btn-2c uploadBtn" value="ファイル選択" onclick="$('#file').click();" />
						<span id="fake_input_file"></span> <br>
						<input type="submit" name="upload" value="送信" class="btn btn-2 btn-2c uploadBtn">
					</form>
				</c:if>
			</div>
		</div>
		<hr>
		<form action="/HomeSystem/fc/PlanDetail" enctype="multipart/form-data" method="post">
			<input type="hidden" name="planId" value="${planDetail.planId}">
			<input type="hidden" name="commentUserId" value="${user.userId}">
			<table>
				<tr>
					<td>ユーザ名： ${user.lastName}${user.firstName}</td>
				</tr>
				<tr>
					<td>コメント：</td>
				</tr>
				<tr>
					<td><textarea name="comment" id="comment"></textarea></td>

				</tr>
				<tr>
					<td>添付ファイル：</td>
				</tr>
			</table>
			<input type="file" name="file" id="file1" style="display: none;" onchange="$('#fake_input_file_1').text($(this)[0].files[0].name)">
			<input type="button" class="btn btn-2 btn-2c uploadBtn" value="ファイル選択" onClick="$('#file1').click();">
			<span id="fake_input_file_1"></span> <br>
			<input type="file" name="file" id="file2" style="display: none;" onchange="$('#fake_input_file_2').text($(this)[0].files[0].name)">
			<input type="button" class="btn btn-2 btn-2c uploadBtn" value="ファイル選択" onClick="$('#file2').click();">
			<span id="fake_input_file_2"></span> <br>
			<input type="file" name="file" id="file3" style="display: none;" onchange="$('#fake_input_file_3').text($(this)[0].files[0].name)">
			<input type="button" class="btn btn-2 btn-2c uploadBtn" value="ファイル選択" onClick="$('#file3').click();">
			<span id="fake_input_file_3"></span> <br>
			<input type="submit" name="commentSubmit" value="送信" class="btn btn-2 btn-2c commentSubmit">
		</form>

		<hr>
		<div id="comments">
			<c:set var="isPlanner" value="false" />
			<c:forEach var="comment" items="${commentList}">
				<c:if test="${comment.deleteFlag == 0}">
					<div class="commentData">
						<c:if test="${planDetail.planner == comment.commentUser }">
							<c:set var="isPlanner" value="true" />
						</c:if>
						<c:if test="${planDetail.planner != comment.commentUser }">
							<c:set var="isPlanner" value="false" />
						</c:if>
						<c:if test="${isPlanner}">
							<div class="planner">
						</c:if>
						<c:if test="${!isPlanner}">
							<div class="gest">
						</c:if>
						<div class="face">
							<img src="../images/employees/${comment.commentUser}.jpg">
						</div>
						<div class="name">${comment.commentName}</div>
						<div class="home">
							<c:if test="${user.userId != comment.commentUser }">
								<a href="/HomeSystem/fc/Home?toUser=${comment.commentUser}" class="iframe"> <input type="button" name="submit" class="btn btn-2 btn-2c" value="褒める" />
								</a>
							</c:if>
						</div>
					</div>

					<c:if test="${isPlanner}">
						<div class="plannerComment">
					</c:if>
					<c:if test="${!isPlanner}">
						<div class="gestComment">
					</c:if>
					<div class="commentHeader">
						<div class="commentNo">
							${comment.commentNo }
							<c:if test="${comment.commentUser == user.userId && comment.deleteFlag == 0}">
								<div class="commentDelete">
									<form action="/HomeSystem/fc/PlanDetail" method="post">
										<input type="hidden" name="planId" value='${planDetail.planId}'>
										<input type="hidden" name="commentNo" value='${comment.commentNo}'>
										<input type="image" name="delete" src="../images/icon/del.png" value="削除" class="commentDel" />
									</form>
								</div>
							</c:if>
						</div>
						<div class="commentBody">
							<c:if test="${comment.deleteFlag == 0}">
								${Utility.nlToBR(comment.comment) }
							</c:if>
							<c:if test="${comment.deleteFlag != 0}">
								このコメントは削除されました
							</c:if>
						</div>
						<div class="commentDate">${comment.commentDatetime }</div>
					</div>
					<div class="commentFooter">
						<div class="commentFile">
							<c:forEach var="file" items="${commentFileList }">
								<c:if test="${comment.commentNo == file.commentNo}">
									<div class="file">
										<div class="fileImage">
											<img src="${Utility.getFileImage(file.dataName)}" />
										</div>
										<c:if test="${comment.commentUser == user.userId && planDetail.executeFlag == 0}">
											<div class="del">
												<form action="/HomeSystem/fc/PlanDetail" method="post">
													<input type="image" src="../images/icon/del.png" width="15" height="15" name="commentFileDelete" value="削除" />
													<input type="hidden" name="planId" value="${planDetail.planId}" />
													<input type="hidden" name="dataNo" value="${file.dataNo}" />
													<input type="hidden" name="fileName" value="${file.dataName}" />
													<input type="hidden" name="commentNo" value="${file.commentNo}" />
													<input type="hidden" name="commentFileDelete" value="del" />
												</form>
											</div>
										</c:if>
										<div class="fileName">${file.dataName}</div>
										<form action="/HomeSystem/fc/PlanDetail" method="post">
											<input type="hidden" name="planId" value="${planDetail.planId}" />
											<input type="hidden" name="path" value="/files/plan/comment/${file.planId}/${file.commentNo}/${file.dataNo}/${file.dataName}" />
											<input type="hidden" name="fileName" value="${file.dataName}" />
											<input type="submit" name="download" value="ダウンロード" class="btn btn-2 btn-2c download" />
										</form>
									</div>
								</c:if>
							</c:forEach>
						</div>
<!-- 							指定コメントファイルアップロード -->
<!-- 						<div class="commentFileUpload"> -->
<%-- 							<c:if test="${comment.commentUser == user.userId }"> --%>
<!-- 								<form action="/HomeSystem/fc/PlanDetail" method="post" enctype="multipart/form-data"> -->
<%-- 									<input type="hidden" name="commentNo" value="${comment.commentNo}" /> --%>
<%-- 									<input type="hidden" name="planId" value="${planDetail.planId}" /> --%>
<%-- 									<input type="file" name="file" id="commentUploadFile${comment.commentNo}" style="display:none;" onchange="$('#fake_${comment.commentNo}_file').text($(this)[0].files[0].name)" /> --%>
<%-- 									<input type="button" class="btn btn-2 btn-2c uploadBtn" value="ファイル選択" onclick="$('#commentUploadFile${comment.commentNo}').click();" /> --%>
<%-- 									<span id="fake_${comment.commentNo}_file"></span> <br> --%>
<!-- 									<input type="submit" name="upload" value="送信" class="btn btn-2 btn-2c uploadBtn"> -->
<!-- 								</form> -->
<%-- 							</c:if> --%>
<!-- 						</div> -->
					</div>
		</div>
	</div>
	</c:if>
	</c:forEach>
	</div>

	</div>
</body>
<script>
	autosize(document.querySelectorAll('textarea'));
</script>
</html>