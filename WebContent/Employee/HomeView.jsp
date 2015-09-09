<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ホメコメント閲覧</title>
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="../css/HomeView.css">
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../js/masonry.min.js"></script>
<script type="text/javascript">
	jQuery(window).load(function() {
		jQuery('.contents').masonry({
			itemSelector : '.home',
			columnWidth : 500
		});
	});
</script>
</head>
<body>
	<div class="wrapper">
		<div class="title"><img src="../images/icon/message.png"><h1 class="titleName">あなた宛のホメコメント</h1></div>
		<hr>
		<div class="contents">

			<c:forEach var="homeData" items="${homeList}">
				<div class="home">
					<div class="user">
						<div class="face">
							<c:if test="${homeData.classFlag == 1}">
								<a href="/HomeSystem/fc/EmployeePage?employeeId=${homeData.homeUser}"> <img src="../images/employees/${homeData.homeUser}.jpg">
								</a>
							</c:if>
							<c:if test="${homeData.classFlag == 0}">
								<img src="../images/icon/guest.png">
							</c:if>
						</div>
						<div class="flameDiv">
							<c:if test="${homeData.classFlag == 1}">
								<c:set var="level" value="${homeData.level}" />
								<c:if test="${homeData.level > 12}">
									<c:set var="level" value="12" />
								</c:if>
								<a href="/HomeSystem/fc/EmployeePage?employeeId=${homeData.homeUser}"> <img src="../images/flame/${level}.png" class="flame">
								</a>
							</c:if>
						</div>
						<c:if test="${homeData.classFlag == 1}">
							<a href="/HomeSystem/fc/EmployeePage?employeeId=${homeData.homeUser}">
								<div class="name">${homeData.homeUserLastName}${homeData.homeUserFirstName}</div>
							</a>
						</c:if>
						<c:if test="${homeData.classFlag == 0}">
							<div class="name">${homeData.homeUserLastName}${homeData.homeUserFirstName}</div>
						</c:if>
					</div>
					<div class="comment">
						<div class="commentBody">${homeData.homeComment}</div>
						<div class="commentDate">${homeData.homeDatetime}</div>
					</div>
				</div>
			</c:forEach>

		</div>
</body>
</html>
