<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Employee Page</title>
</head>
<body>
	<h1>社員ページ</h1>
	<c:forEach var="employeeDetail" items="${employeeDetail}">
		<h2>${employeeDetail.employeeName}</h2>
		<ul>
			<li>所属部：${employeeDetail.departmentName}</li>
			<li>一言コメント：${employeeDetail.employeeComment}</li>
			<li>社員ID：${employeeDetail.employeeId}</li>
			<li>レベル：${employeeDetail.level}</li>
			<li>経験値：${employeeDetail.experience}</li>
		</ul>
	</c:forEach>
	<ul>
		<c:forEach var="batchData" items="${employeeBadgeDetail}">
		<li>バッジ名：${batchData.badgeName} 受け取り総数：${batchData.badgeCount} </li>
		</c:forEach>
	</ul>
	<ul>
	<c:forEach var="employeeGenre" items="${employeeGenreDetail}">
		<li>経験ジャンル${employeeGenre.genreName} 経験回数${employeeGenre.genreCount}</li>
	</c:forEach>
	</ul>
	<ul>
	<c:forEach var="employeePlan" items="${employeePlanDetail}">
		<li>${employeePlan.days}に企画：${employeePlan.planTitle}を発案しました</li>
	</c:forEach>
	</ul>
	<ul>
	<c:forEach var="employeePlanComment" items="${employeePlanCommentDetail}">
		<li>${employeePlanComment.days}に${employeePlanComment.plannerName}さんの${employeePlanComment.planName}にコメントしました</li>
	</c:forEach>
	</ul>
	<ul>
	<c:forEach var="employeeHomeLog" items="${employeeHomeLogDetail}">
		<li>${employeeHomeLog.days}に${employeeHomeLog.targetName}さんへホメポイントを付与しました</li>
	</c:forEach>
	</ul>
</body>
</html>