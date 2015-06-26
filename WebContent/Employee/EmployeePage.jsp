<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeeBadgeBean" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/employeePage.css">
<link rel="stylesheet" type="text/css"
	href="../js/colorbox/colorbox.css">
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../js/colorbox/jquery.colorbox.js"></script>
<title>Employee Page</title>
<script type="text/javascript" src="../js/Chart.js-master/Chart.js"></script>
<script>
	//ホメボタン
// 	function ImageUp(employeeId) {
// 		window.open("/HomeSystem/fc/Home?toUser="+employeeId,"window2","width=1000,height=500");
// 	}

	//今後追加するもの
	//社員プロフィール変更(顔写真)
	//
	$(function() {
		$(".iframe").colorbox({
			iframe : true,
			width : "500px",
			height : "90%",
			opacity : 0.7
		});
	});
	//履歴タブ切り替え
	function activityChange(tabName){
		document.getElementById("homeTab").style.display = 'none';
		document.getElementById("planTab").style.display = 'none';
		document.getElementById("planCommentTab").style.display = 'none';
		//指定箇所の出力
		document.getElementById(tabName).style.display = 'block';
	}
</script>

</head>
<body>
<%
	//チャート出力用配列の準備
	String[] chartName = (String[])request.getAttribute("chartName");
	int[] chartCount = (int[])request.getAttribute("chartCount");
	if(chartName==null && chartCount==null){
		 System.out.println("配列取得失敗");
	}

%>
<div class="contents">
	<h1 class="employeePageTitle">社員ページ</h1>
	<div class="leftContents">
		<div class="employeeStatus">
				<c:forEach var="employeeDetail" items="${employeeDetail}">
					<ul class="employeeData">
						<li class="employeeImage"><img src="../images/employees/${employeeDetail.employeeId}.jpg"></li>
						<li class="employeeComment">${employeeDetail.employeeComment}</li>
						<li class="employeeName">${employeeDetail.employeeName}</li>
						<li class="departmentName">${employeeDetail.departmentName}</li>
						<li class="employeeLevel">レベル：${employeeDetail.level}</li>
						<li class="employeeExperience">経験値：${employeeDetail.experience}</li>
					</ul>
				<c:if test="${sessionId != employeeDetail.employeeId}">
					<a class="iframe" href="/HomeSystem/fc/Home?toUser=${employeeDetail.employeeId}" ><input type="button" value="この人を褒める" class="homeButton"></a>
				</c:if>
				</c:forEach>
		</div>
		<div class="genreExperience">
			<h4 class="experienceTitle">経験ジャンル</h4>
			<div class="experienceList">
			<ul>
			<c:forEach var="employeeGenre" items="${employeeGenreDetail}" varStatus="status">
				<c:choose>
					<c:when test="${status.index <2}">
						<li style="font-size: 20px; color:#ff6666;">${employeeGenre.genreName}：${employeeGenre.genreCount}回</li>
					</c:when>
					<c:when test="${2<=status.index && status.index<4}">
						<li style="font-size: 20px; color:#6666ff;">${employeeGenre.genreName}：${employeeGenre.genreCount}回</li>
					</c:when>
					<c:when test="${5<=status.index}">
						<li style="font-size: 20px;">${employeeGenre.genreName}：${employeeGenre.genreCount}回</li>
					</c:when>
				</c:choose>
			</c:forEach>
			</ul>
			</div>
		</div>
	</div>
	<div class="rightContents">
		<div class="radarChart">
			<canvas id="radar" height="500" width="700"></canvas>
		</div>
			<script>
				var radarChartData = {
					labels : [<% if(chartName.length !=0 && chartCount.length !=0){
						for (int i = 0; i < chartName.length;) {
							out.print("\""+chartName[i]+"\"");
							i++;
							if(i==chartCount.length){
								break;
							}
								out.print(",");
						}
						}%>],
					datasets : [
						{
						fillColor : "rgba(200,50,50,0.8)",
						strokeColor : "rgba(255,0,0,1)",
						pointColor : "rgba(255,0,0,1)",
						pointStrokeColor : "#fff",
						data : [<% if(chartName.length !=0 && chartCount.length !=0){
							for (int i = 0; i < chartCount.length;) {
								out.print(chartCount[i]);
								i++;
								if(i==chartCount.length){
									break;
								}
								out.print(",");
							}
							}%>]
						}
					]
				}
				var myRadar = new Chart(document.getElementById("radar").getContext("2d"))
					.Radar(radarChartData,{
						scaleShowLabels : true, scaleFontSize : 15, scaleLineColor: "rgba(0,0,0,0.9)", pointLabelFontSize : 20, });
				</script>
		<div class="activities">
			<div class="tabs">
				<a href="javascript:activityChange('homeTab')" class="homeTab">ホメホメ履歴</a>
				<a href="javascript:activityChange('planTab')" class="planTab">企画投稿履歴</a>
				<a href="javascript:activityChange('planCommentTab')" class="planCommentTab">企画コメント履歴</a>
			</div>
			<div id="homeTab" class="activityTab">
			<hr>
			<c:if test="${empty employeeHomeLogDetail}" var="flgA"/>
				<c:if test="${flgA == true}">
					<h4 class="notActivity">まだホメホメ活動はありません</h4>
				</c:if>
				<c:forEach var="employeeHomeLog" items="${employeeHomeLogDetail}">
				<ul>
					<li class="days">${employeeHomeLog.days}</li>
					<li class="activity">${employeeHomeLog.targetName}さんへホメポイントを付与しました</li>
				</ul>
				<hr>
				</c:forEach>

			</div>
			<div id="planTab" class="activityTab">
			<hr>
			<c:if test="${empty employeePlanDetail}" var="flgB"/>
				<c:if test="${flgB == true}">
					<h4 class="notActivity">まだ企画投稿活動はありません</h4>
				</c:if>

				<c:forEach var="employeePlan" items="${employeePlanDetail}">
					<ul>
						<li class="days">${employeePlan.days}</li>
						<li class="activity">企画：${employeePlan.planTitle}を発案しました</li>
					</ul>
					<hr>
				</c:forEach>

			</div>
			<div id="planCommentTab" class="activityTab">
			<hr>
			<c:if test="${empty employeePlanCommentDetail}" var="flgC"/>
				<c:if test="${flgC ==true}">
					<h4 class="notActivity">まだ企画コメント活動はありません</h4>
				</c:if>
				<c:forEach var="employeePlanComment" items="${employeePlanCommentDetail}">
					<ul>
						<li class="days">${employeePlanComment.days}</li>
						<li class="activity">${employeePlanComment.plannerName}さんの${employeePlanComment.planName}にコメントしました</li>
					</ul>
				<hr>
				</c:forEach>
			</div>
		</div>
			<script type="text/javascript">
				activityChange('homeTab');
			</script>
	</div>
</div>
</body>
</html>