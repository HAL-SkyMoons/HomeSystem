<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset=UTF-8">
<title>Employee Page</title>
<script type="text/javascript">
	function ImageUp(employeeId) {
		window.open("/HomeSystem/fc/Home?toUser="+employeeId,"window2","width=1000,height=500");
	}
</script>

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
	<canvas id="radar" height="450" width="500"></canvas>
<script>
var radarChartData = {
		  labels : ["Eating","Drinking","Sleeping","Designing","Coding","Partying","Running"],
		  datasets : [
		    {
		      fillColor : "rgba(220,220,220,0.5)",
		      strokeColor : "rgba(220,220,220,1)",
		      pointColor : "rgba(220,220,220,1)",
		      pointStrokeColor : "#fff",
		      data : [65,59,90,81,56,55,40]
		    },
		    {
		      fillColor : "rgba(151,187,205,0.5)",
		      strokeColor : "rgba(151,187,205,1)",
		      pointColor : "rgba(151,187,205,1)",
		      pointStrokeColor : "#fff",
		      data : [28,48,40,19,96,27,100]
		    }
		  ]
		}
		var myRadar = new Chart(document.getElementById("radar").getContext("2d")).Radar(radarChartData,{scaleShowLabels : false, pointLabelFontSize : 10});
</script>
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