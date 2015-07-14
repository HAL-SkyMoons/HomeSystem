<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeeBatchBean" %>
<%@page import="java.util.*" %>
<%@page import="java.text.*" %>
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
	//バッジタブ切り替え
	function badgeChange(tabName){
		document.getElementById("totalTab").style.display = 'none';
		document.getElementById("monthTab").style.display = 'none';
		document.getElementById("yearTab").style.display = 'none';
		//指定箇所の出力
		document.getElementById(tabName).style.display = 'block';
	}

	$(document).ready(function() {
				//Hide (Collapse) the toggle containers on load
				$("#bigGenreList").hide();

				//Switch the "Open" and "Close" state per click then slide up/down (depending on open/close state)
				$("#trigger").click(function() {
					$(this).toggleClass("active").next().slideToggle("slow");
					return false; //Prevent the browser jump to the link anchor
				});

			});
	$(document).ready(function() {
		//Hide (Collapse) the toggle containers on load
		$("#bigGenreList2").hide();

		//Switch the "Open" and "Close" state per click then slide up/down (depending on open/close state)
		$("#trigger2").click(function() {
			$(this).toggleClass("active").next().slideToggle("slow");
			return false; //Prevent the browser jump to the link anchor
		});

	});
</script>
<%
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("S");
	String milliSec =  sdf.format(date);
%>
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
						<li class="employeeImage"><img src="../images/employees/${employeeDetail.employeeId}.jpg?<%=milliSec %>"></li>
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
			<h4 id="trigger">BigGenre1</h4>
			<div class="bigGenre1List" id="bigGenreList">
			<ul>
			<c:forEach var="employeeGenre" items="${employeeGenreDetail}" varStatus="status">
				<c:if test="${employeeGenre.bigGenreId ==1}">
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
				</c:if>
			</c:forEach>
			</ul>
			</div>
			<h4 id="trigger2">BigGenre2</h4>
			<div class="bigGenre2List" id="bigGenreList2">
				<ul>
				<c:forEach var="employeeGenre" items="${employeeGenreDetail}" varStatus="status">
					<c:if test="${employeeGenre.bigGenreId ==2}">
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
					</c:if>
				</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<div class="rightContents">
		<div class="radarChart">
			<canvas id="radar" height="400" width="540"></canvas>
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
		<div class="badgeView">
			<div class="badgeTabs">
				<a href="javascript:badgeChange('totalTab')" class="totalTab"><h4>入社以来</h4></a>
				<a href="javascript:badgeChange('monthTab')" class="monthTab"><h4>1ケ月間</h4></a>
				<a href="javascript:badgeChange('yearTab')" class="yearTab"><h4>1年間</h4></a>
			</div>
			<div class="badgeTotal" id="totalTab">
			<table class="badgeTable">
				<tr>
				<c:forEach var="employeeBadge" items="${employeeBadgeDetail}" varStatus="status">
					<td class="badgeTd"><img src="../images/batch/${employeeBadge.badgeImgPath}.png" class="badgeImg"> × ${employeeBadge.badgeCount}</td>
					<c:if test="${status.count%6 ==0}">
						</tr>
						<tr>
					</c:if>
				</c:forEach>
			</table>
			</div>
			<div class="badgeMonth" id="monthTab">
			<table class="badgeTable">
				<c:forEach var="employeeBadgeMonth" items="${employeeBadgeMonth}" varStatus="status">
					<td class="badgeTd"><img src="../images/batch/${employeeBadgeMonth.badgeImgPath}.png" class="badgeImg"> × ${employeeBadgeMonth.badgeCount}</td>
					<c:if test="${status.count%6 ==0}">
						</tr>
						<tr>
					</c:if>
				</c:forEach>
			</table>
			</div>
			<div class="badgeYear" id="yearTab">
			<table class="badgeTable">
				<c:forEach var="employeeBadgeYear" items="${employeeBadgeYear}" varStatus="status">
					<td class="badgeTd"><img src="../images/batch/${employeeBadgeYear.badgeImgPath}.png" class="badgeImg"> × ${employeeBadgeYear.badgeCount}</td>
					<c:if test="${status.count%6 ==0}">
						</tr>
						<tr>
					</c:if>
				</c:forEach>
			</table>
			</div>
		</div>
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
					<li class="activity"><a href="EmployeePage?employeeId=${employeeHomeLog.employeeId}">${employeeHomeLog.targetName}さん</a>へホメポイントを付与しました</li>
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
						<li class="activity">企画：<a href="PlanDetail?planId=${employeePlan.planId}&detail=view">${employeePlan.planTitle}</a>を発案しました</li>
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
						<li class="activity">${employeePlanComment.plannerName}さんの<a href="PlanDetail?planId=${employeePlanComment.planId}&detail=view">${employeePlanComment.planName}</a>にコメントしました</li>
					</ul>
				<hr>
				</c:forEach>
			</div>
		</div>
			<script type="text/javascript">
				activityChange('homeTab');
				badgeChange('totalTab');
			</script>
	</div>
</div>
</body>
</html>