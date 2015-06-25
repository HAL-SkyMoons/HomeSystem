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
<link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css">
<title>Employee Page</title>
<script type="text/javascript" src="../js/Chart.js-master/Chart.js"></script>
<script>
	//ホメボタン
	function ImageUp(employeeId) {
		window.open("/HomeSystem/fc/Home?toUser="+employeeId,"window2","width=1000,height=500");
	}
	//履歴タブ切り替え
	function activityChange(tabName){
		document.getElementById("homeTab").style.display = 'none';
		document.getElementById("planTab").style.display = 'none';
		document.getElementById("planCommentTab").style.display = 'none';
		//指定箇所の出力
		document.getElementById(tabName).style.display = 'block';
	}
</script>
<%
	//チャート出力用配列の準備
	String[] chartName = (String[])request.getAttribute("chartName");
	int[] chartCount = (int[])request.getAttribute("chartCount");
	if(chartName==null && chartCount==null){
		 System.out.println("配列取得失敗");
	}

%>
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
	<c:if test="${sessionId != employeeDetail.employeeId}">
		<a href="javascript:ImageUp('${employeeDetail.employeeId}');" ><input type="button" value="この人を褒める"></a>
	</c:if>
	<canvas id="radar" height="250" width="350"></canvas>
	</c:forEach>
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
				    	 }}%>]
			    }
			  ]
			}
			var myRadar = new Chart(document.getElementById("radar").getContext("2d")).Radar(radarChartData,{scaleShowLabels : false, pointLabelFontSize : 15});
</script>


	<div class="genreExperience">
	<h4>経験ジャンル</h4>
	<c:forEach var="employeeGenre" items="${employeeGenreDetail}" varStatus="status">
	<c:choose>
		<c:when test="${status.index <3}">
			<p style="font-size: 20px;">${employeeGenre.genreName}</p><br/>
		</c:when>
		<c:when test="${3<status.index && status.index<5}">
			<p style="font-size: 15px;">${employeeGenre.genreName}</p><br/>
		</c:when>
		<c:when test="${5<status.index }">
			<p style="font-size: 10px;">${employeeGenre.genreName}</p><br/>
		</c:when>
	</c:choose>
	</c:forEach>
	</div>
	<div class="activities">
		<div id="tabs">
			<a href="javascript:activityChange('homeTab')" class="homeTab">ホメホメ履歴</a>
			<a href="javascript:activityChange('planTab')" class="planTab">企画投稿履歴</a>
			<a href="javascript:activityChange('planCommentTab')" class="planCommentTab">企画コメント履歴</a>
		</div>
		<div id="homeTab" class="activityTab">
		<c:if test="${empty employeeHomeLogDetail}" var="flgA"/>
			<c:if test="${flgA == true}">
				<h4>まだホメホメ活動はありません</h4>
			</c:if>
			<ul>
			<c:forEach var="employeeHomeLog" items="${employeeHomeLogDetail}">
				<li>${employeeHomeLog.days}に${employeeHomeLog.targetName}さんへホメポイントを付与しました</li>
			</c:forEach>
			</ul>
		</div>
		<div id="planTab" class="activityTab">
		<c:if test="${empty employeePlanDetail}" var="flgB"/>
			<c:if test="${flgB == true}">
				<h4>まだ企画投稿活動はありません</h4>
			</c:if>
			<ul>
			<c:forEach var="employeePlan" items="${employeePlanDetail}">
				<li>${employeePlan.days}に企画：${employeePlan.planTitle}を発案しました</li>
			</c:forEach>
			</ul>
		</div>
		<div id="planCommentTab" class="activityTab">
		<c:if test="${empty employeePlanCommentDetail}" var="flgC"/>
			<c:if test="${flgC ==true}">
				<h4>まだ企画コメント活動はありません</h4>
			</c:if>
			<ul>
			<c:forEach var="employeePlanComment" items="${employeePlanCommentDetail}">
				<li>${employeePlanComment.days}に${employeePlanComment.plannerName}さんの${employeePlanComment.planName}にコメントしました</li>
			</c:forEach>
			</ul>
		</div>
	</div>
		<script type="text/javascript">
			activityChange('homeTab');
		</script>
</body>
</html>