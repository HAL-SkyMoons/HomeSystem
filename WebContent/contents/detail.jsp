<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	SessionController sessionController = new SessionController(request);
	String url = sessionController.checkUserSession();
	if(url != null) {
		response.sendRedirect(url);
	}
%>
<!DOCTYPE html>
<c:if test="${scriptMessage != null}" >${scriptMessage}</c:if>
<html>
	<script type="text/javascript" src="../../js/autosize/autosize.js"></script>
	<script type="text/javascript" src="../../js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../../js/colorbox/jquery.colorbox.js"></script>
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
	<head>
		<link rel="stylesheet" type="text/css" href="../../css/reset.css">
		<link rel="stylesheet" type="text/css" href="../../css/bootstrap.css">
		<link rel="stylesheet" type="text/css" href="../../css/bootstrap-theme.min.css">
		<link rel="stylesheet" type="text/css" href="../../css/PlanDetail.css">
		<link rel="stylesheet" type="text/css" href="../../js/Magnific-Popup/magnific-popup.css">
		<link rel="stylesheet" type="text/css" href="../../js/colorbox/colorbox.css">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>コンテンツ詳細</title>
	</head>
	<body>
		<c:set var="i" value="${detailList}"/>
		<div id="wrapper">
			<div id="plan">
				<div id="planHeader">
					<div id="id">
						No.${i.homeContentId}
					</div>
					<div id="planDate">
						開始日時：${i.startDatetime}
					</div>
				</div>
				<div id="title">
					${i.homeContentTitle}
				</div>
				<div id="planDetail">
					<div id="startEnd">
						開始日時：<br>
						${i.startDatetime}～${i.endDatetime}
						<c:if test="${i.employeeId != i.userId}">
							<a class="iframe" href="/HomeSystem/fc/Home?toUser=${i.employeeId}&contentsId=${i.homeContentId}" ><input type="button" value="ホメる" class="btn btn-2 btn-2c"></a>
						</c:if>
					</div>
					<div id="planner">
						企画者
						<div id="img">
							<img src="../../images/employees/${i.employeeId}.jpg" width="100" height="100">
						</div>
						${i.firstName}${i.lastName}
					</div>
				</div>
				<div id="planComment">
					${i.homeContentComment}
				</div>
				<div id="genre">
					登録ジャンル<br>
					<c:set var="cnt" value="0"/>
					<c:forEach items="${i.genreName}" var="genreName">
						<div class="genres"><a href="./list?genreId=${i.genreId[cnt]}"><c:out value="${genreName}"/></a></div>
						<c:set var="cnt" value="${cnt + 1}"/>
					</c:forEach>
				</div>
				<c:if test="${i.endDatetime == null && i.employeeId == i.userId}" >
					<form action="./edit" method="post">
						<input type="hidden" name="homeContentId" value="${i.homeContentId}"/>
						<input type="submit" value="編集" class="btn btn-2 btn-2c edit"/>
					</form>
				</c:if>
	
	
	
				<hr>
				<h3>添付ファイル</h3>
				<div id="files">
					<c:forEach items="${dataList}" var="j">
						<div class="file">
							<div vlass="fileImage">
								<img src="${j.fileImagePath}" width="50" height="50">
							</div>
							<div class="fileName">${j.homeDataName}</div>
							<form action="/HomeSystem/fc/contents/detail" method="post">
								<input type="hidden" name="homeContentId" value="${j.homeContentId}">
								<input type="hidden" name="path" value="../files/contents/master/${j.homeContentId}/${j.homeDataNo}/${j.homeDataName}" />
								<input type="hidden" name="fileName" value="${j.homeDataName}"/>
								<input type="submit" name="download" value="ダウンロード" class="btn btn-2 btn-2c download">
							</form>
						</div>
					</c:forEach>
				</div>
				<div id="upload">
				</div>
				
				<c:if test="${i.employeeId != i.userId}">
					<a class="iframe" href="/HomeSystem/fc/Home?toUser=${i.employeeId}&contentsId=${i.homeContentId}" ><input type="button" value="ホメる" class="btn btn-2 btn-2c"></a>
				</c:if>
				
				<div class="commentData">
					<c:forEach items="${homeLogList}" var="homeLog">
						<div class="commentData">
							<c:if test="${i.employeeId == homeLog.homeUser}"><c:set var="fromUser" value="true"/></c:if>
							<c:if test="${i.employeeId != homeLog.homeUser}"><c:set var="fromUser" value="false"/></c:if>
							<c:if test="${fromUser}"><div class="planner"></c:if>
							<c:if test="${!fromUser}"><div class="gest"></c:if>
								<div class="face"><img src="../../images/employees/${homeLog.homeUser}.jpg"></div>
								<div class="name">${homeLog.homeUserFirstName}${homeLog.homeUserLastName}</div>
								<div class="home">
									<c:if test="${i.userId != homeLog.homeUser && i.employeeId != i.userId}">
										<a class="iframe" href="/HomeSystem/fc/Home?toUser=${homeLog.homeUser}&contentsId=${i.homeContentId}"><input type="button" value="ホメる" class="btn btn-2 btn-2c"></a>
									</c:if>
								</div>
								
							</div>
							<c:if test="${fromUser}"><div class="plannerComment"></c:if>
							<c:if test="${!fromUser}"><div class="gestComment"></c:if>
							<div class="commentHeader">
								<div class="commentNo"></div>
								<div id="comments"></div>
								<div class="commentBody"></div>
								<div class="commentDate">${homeLog.homeDatetime}</div>
							</div>
							<div class="commenFootert">
								<div class="commentFile">
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
			<!--
			<hr>
			<form action="/HomeSystem/fc/PlanDetail" method="post">
				<table>
					<tr>
						<td>ユーザ名： ${i.lastName}${i.firstName}</td>
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
					name="planId" value="planId"> <input
					type="hidden" name="commentUserId"
					value="getUserId">
			</form>
			-->
			<hr>
		</div>	
		<!--
		<c:if test="${i.deleteFlag == '1'}" ><h2>データが既に削除されています。</h2></c:if>
		<c:if test="${i.deleteFlag != '1'}" >
			<table border="1">
				<tr>
					<td colspan="2">
						<c:if test="${i.endDatetime == null && i.employeeId == i.userId}" >
							<form action="./edit" method="post">
								<input type="hidden" name="homeContentId" value="${i.homeContentId}"/>
								<input type="submit" value="編集"/>
							</form>
						</c:if>
						<c:if test="${i.endDatetime != null || i.employeeId != i.userId}" >
							<c:out value="編集不可" />
						</c:if>
					</td>
				</tr>
				<tr>
					<td>コンテンツ名：</td>
					<td>${i.homeContentTitle}</td>
				</tr>
				<tr>
					<td>実施日時：</td>
					<td>${i.startDatetime}</td>
				</tr>
				<tr>
					<td>終了日：</td>
					<td>
						<c:if test="${i.endDatetime != null}" >${i.endDatetime}</c:if>
						<c:if test="${i.endDatetime == null}" >未定</c:if>
					</td>
				</tr>
				<tr>
					<td>投稿者名：</td>
					<td><a href="./list?employeeId=${i.employeeId}">${i.firstName}${i.lastName}</a></td>
				</tr>
				<tr>
					<td>内容：<br/>
					<td>
						${i.homeContentComment}
					</td>
				</tr>
				<tr>
					<td>大ジャンル：</td>
					<td>
						<c:set var="cnt" value="0"/>
						<c:forEach items="${i.bigGenreName}" var="bigGenreName">
							<a href="./list?bigGenreId=${i.bigGenreId[cnt]}"><c:out value="${bigGenreName}"/></a>
							<c:set var="cnt" value="${cnt + 1}"/>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td>ジャンル：</td>
					<td>
						<c:set var="cnt" value="0"/>
						<c:forEach items="${i.genreName}" var="genreName">
							<a href="./list?genreId=${i.genreId[cnt]}"><c:out value="${genreName}"/></a>
							<c:set var="cnt" value="${cnt + 1}"/>
						</c:forEach>
					</td>
				</tr>
			</table>
			
			<table>
				<tr>
					<th>No</th>
					<th>画像</th>
					<th>ファイル名</th>
					<th>ダウンロード</th>
				</tr>
				<c:forEach items="${dataList}" var="j">
					<tr>
						<td>${j.homeDataNo}</td>
						<td><img src="${j.fileImagePath}" width="50" height="50"></td>
						<td>${j.homeDataName}</td>
						<td>
							<form action="/HomeSystem/fc/contents/detail" method="post">
								<input type="hidden" name="homeContentId" value="${j.homeContentId}">
								<input type="hidden" name="path" value="../files/contents/master/${j.homeContentId}/${j.homeDataNo}/${j.homeDataName}" />
								<input type="hidden" name="fileName" value="${j.homeDataName}"/>
								<input type="submit" name="download" value="ダウンロード">
							</form>
						</td>
					</tr>
				</c:forEach>
			</table>
			
			<c:if test="${i.employeeId != i.userId}">
				<a class="iframe" href="/HomeSystem/fc/Home?toUser=${i.employeeId}&contentsId=${i.homeContentId}" ><input type="button" value="ホメる"></a>
			</c:if>
			<c:forEach items="${homeLogList}" var="homeLog">
				<table border="1">
					<tr>
						<td>ほめられユーザー：
							<a href="">${homeLog.homeTargetFirstName}${homeLog.homeTargetLastName}</a>
						</td>
						<td>ほめユーザー：
							<a href="">${homeLog.homeUserFirstName}${homeLog.homeUserLastName}</a>
						</td>
						<td>ほめられ日時：${homeLog.homeDatetime}</td>
						<td>バッジ：${homeLog.batchId}</td>
						<td>付与ポイント：${homeLog.homePoint}</td>
						<td>ほめコメント${homeLog.homeComment}</td>
					</tr>
				</table>
			</c:forEach>
		</c:if>
		-->
	</body>
</html>