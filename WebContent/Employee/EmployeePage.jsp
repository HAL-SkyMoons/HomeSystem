<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeeBatchBean"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>Employee Page</title>
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<!-- <link rel="stylesheet" type="text/css" href="../css/employeePage.css"> -->
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="../css/employeeMyPage.css">
<link rel="stylesheet" type="text/css" href="../js/colorbox/colorbox.css">
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../js/colorbox/jquery.colorbox.js"></script>
<%
    //チャート出力用配列の準備
			String[] chartName = (String[]) request.getAttribute("chartName");
			int[] chartCount = (int[]) request.getAttribute("chartCount");
			//月間
			String[] chartNameMonth = (String[]) request
					.getAttribute("chartNameMonth");
			int[] chartCountMonth = (int[]) request
					.getAttribute("chartCountMonth");
			//年間
			String[] chartNameYear = (String[]) request
					.getAttribute("chartNameYear");
			int[] chartCountYear = (int[]) request
					.getAttribute("chartCountYear");
			//通算
			String[] chartNameTotal = (String[]) request
					.getAttribute("chartNameTotal");
			int[] chartCountTotal = (int[]) request
					.getAttribute("chartCountTotal");
%>
<script type="text/javascript" src="../js/Chart.js-master/Chart.js"></script>
<script>
	$(function() {
		$(".iframe").colorbox({
			iframe : true,
			width : "500px",
			height : "90%",
			opacity : 0.7,
			fixed : true
		});
	});
	//グラフ切り替え(月間)
	function graphChangeMonth() {
		var radarChartData = {
			labels : [
<%if (chartNameMonth.length != 0 && chartCountMonth.length != 0) {
				for (int i = 0; i < chartNameMonth.length;) {
					out.print("\"" + chartNameMonth[i] + "\"");
					i++;
					if (i == chartCountMonth.length) {
						break;
					}
					out.print(",");
				}
			}%>
	],
			datasets : [ {
				fillColor : "rgba(200,50,50,0.8)",
				strokeColor : "rgba(255,0,0,1)",
				pointColor : "rgba(255,0,0,1)",
				pointStrokeColor : "#fff",
				data : [
<%if (chartNameMonth.length != 0 && chartCountMonth.length != 0) {
				for (int i = 0; i < chartCountMonth.length;) {
					out.print(chartCountMonth[i]);
					i++;
					if (i == chartCountMonth.length) {
						break;
					}
					out.print(",");
				}
			}%>
	]
			} ]
		}
		var myRadar = new Chart(document.getElementById("radar").getContext(
				"2d")).Radar(radarChartData, {
			scaleShowLabels : true,
			scaleFontSize : 15,
			scaleLineColor : "rgba(0,0,0,0.9)",
			pointLabelFontSize : 20,
		});
	}
	//グラフ切り替え(年間)
	function graphChangeYear() {
		var radarChartData = {
			labels : [
<%if (chartNameYear.length != 0 && chartCountYear.length != 0) {
				for (int i = 0; i < chartNameYear.length;) {
					out.print("\"" + chartNameYear[i] + "\"");
					i++;
					if (i == chartCountYear.length) {
						break;
					}
					out.print(",");
				}
			}%>
	],
			datasets : [ {
				fillColor : "rgba(200,50,50,0.8)",
				strokeColor : "rgba(255,0,0,1)",
				pointColor : "rgba(255,0,0,1)",
				pointStrokeColor : "#fff",
				data : [
<%if (chartNameYear.length != 0 && chartCountYear.length != 0) {
				for (int i = 0; i < chartCountYear.length;) {
					out.print(chartCountYear[i]);
					i++;
					if (i == chartCountYear.length) {
						break;
					}
					out.print(",");
				}
			}%>
	]
			} ]
		}
		var myRadar = new Chart(document.getElementById("radar").getContext(
				"2d")).Radar(radarChartData, {
			scaleShowLabels : true,
			scaleFontSize : 15,
			scaleLineColor : "rgba(0,0,0,0.9)",
			pointLabelFontSize : 20,
		});
	}
	//グラフ切り替え(通算)
	function graphChangeTotal() {
		var radarChartData = {
			labels : [
<%if (chartNameTotal.length != 0 && chartCountTotal.length != 0) {
				for (int i = 0; i < chartNameTotal.length;) {
					out.print("\"" + chartNameTotal[i] + "\"");
					i++;
					if (i == chartCountTotal.length) {
						break;
					}
					out.print(",");
				}
			}%>
	],
			datasets : [ {
				fillColor : "rgba(200,50,50,0.8)",
				strokeColor : "rgba(255,0,0,1)",
				pointColor : "rgba(255,0,0,1)",
				pointStrokeColor : "#fff",
				data : [
<%if (chartNameTotal.length != 0 && chartCountTotal.length != 0) {
				for (int i = 0; i < chartCountTotal.length;) {
					out.print(chartCountTotal[i]);
					i++;
					if (i == chartCountTotal.length) {
						break;
					}
					out.print(",");
				}
			}%>
	]
			} ]
		}
		var myRadar = new Chart(document.getElementById("radar").getContext(
				"2d")).Radar(radarChartData, {
			scaleShowLabels : true,
			scaleFontSize : 15,
			scaleLineColor : "rgba(0,0,0,0.9)",
			pointLabelFontSize : 20,
		});
	}

	//履歴タブ切り替え
	function activityChange(tabName) {
		document.getElementById("homeTab").style.display = 'none';
		document.getElementById("planTab").style.display = 'none';
		document.getElementById("planCommentTab").style.display = 'none';
		//指定箇所の出力
		document.getElementById(tabName).style.display = 'block';

		document.getElementById("homeTabStyle").style.backgroundColor = "#ccc";
		document.getElementById("planTabStyle").style.backgroundColor = "#ccc";
		document.getElementById("planCommentTabStyle").style.backgroundColor = "#ccc";
		document.getElementById(tabName + "Style").style.backgroundColor = '#fff';

	}
	//バッジタブ切り替え
	function badgeChange(tabName) {
		document.getElementById("totalTab").style.display = 'none';
		document.getElementById("monthTab").style.display = 'none';
		document.getElementById("yearTab").style.display = 'none';
		//指定箇所の出力
		document.getElementById(tabName).style.display = 'block';

		document.getElementById("totalTabStyle").style.backgroundColor = "#ccc";
		document.getElementById("monthTabStyle").style.backgroundColor = "#ccc";
		document.getElementById("yearTabStyle").style.backgroundColor = "#ccc";
		document.getElementById(tabName + "Style").style.backgroundColor = '#fff';

		var countTotal =
<%out.print(chartNameTotal.length);%>
	;
		var countMonth =
<%out.print(chartNameMonth.length);%>
	;
		var countYear =
<%out.print(chartNameYear.length);%>
	;

		var max = Math.max(countTotal, countMonth, countYear);

		var count = Math.ceil(max / 5);
		if (count == 0) {
			count = 1;
		}
		var height = count * 71 + 21 + 'px';

		document.getElementById("totalTab").style.height = height;
		document.getElementById("monthTab").style.height = height;
		document.getElementById("yearTab").style.height = height;

	}

	$(document).ready(function() {

		//Hide (Collapse) the toggle containers on load
		$(".toggle_container").hide();

		//Switch the "Open" and "Close" state per click then slide up/down (depending on open/close state)
		$(".trigger").click(function() {
			$(this).toggleClass("active").next().slideToggle("slow");
			return false; //Prevent the browser jump to the link anchor
		});

	});
</script>
<%
    Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("S");
			String milliSec = sdf.format(date);
%>
</head>
<body>
	<div class="contents">
		<h1 class="employeePageTitle">社員ページ</h1>
		<div class="leftContents">
			<div class="employeeStatus">
				<c:forEach var="employeeDetail" items="${employeeDetail}">
					<ul class="employeeData">
						<li class="employeeImage"><div class="imageDiv">
								<img src="../images/employees/${employeeDetail.employeeId}.jpg?<%=milliSec %>" class="employeeImage">
							</div></li>
						<li class="departmentName">${employeeDetail.departmentName}</li>
						<li class="employeeName">${employeeDetail.employeeName}</li>
						<hr>
						<li class="employeeLevel">レベル${employeeDetail.level}</li>
					</ul>
					<c:if test="${sessionId != employeeDetail.employeeId}">
						<a class="iframe" href="/HomeSystem/fc/Home?toUser=${employeeDetail.employeeId}"><input type="submit" value="この人を褒める" class="btn btn-2 btn-2c homeButton"></a>
					</c:if>
				</c:forEach>
			</div>
			<div class="comment">
				<div class="title">一言コメント</div>
				<hr>
				<c:forEach var="employeeDetail" items="${employeeDetail}">
					<div class="employeeComment">${Utility.nlToBR(employeeDetail.employeeComment)}</div>
				</c:forEach>
			</div>
			<div class="genreExperience">
				<h4 class="experienceTitle">経験ジャンル</h4>
				<c:forEach var="bigGenre" items="${bigGenreList}">
					<span class="trigger">&#9661;${bigGenre.bigGenreName }</span>
					<div class="toggle_container">
						<ul>
							<c:forEach var="employeeGenre" items="${employeeGenreDetail}" varStatus="status">
								<c:if test="${employeeGenre.bigGenreId ==bigGenre.bigGenreId}">
									<c:choose>
										<c:when test="${status.index <2}">
											<li style="font-size: 20px; color: #ff6666;">${employeeGenre.genreName}：${employeeGenre.genreCount}回</li>
										</c:when>
										<c:when test="${2<=status.index && status.index<4}">
											<li style="font-size: 20px; color: #6666ff;">${employeeGenre.genreName}：${employeeGenre.genreCount}回</li>
										</c:when>
										<c:when test="${5<=status.index}">
											<li style="font-size: 20px;">${employeeGenre.genreName}：${employeeGenre.genreCount}回</li>
										</c:when>
									</c:choose>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="rightContents">
			<div class="radarChart">
				<h3>
					<img src="../images/icon/order.png" class="icon" />貰ったバッチでの能力診断
				</h3>
				<hr>
				<canvas id="radar" height="400" width="540"></canvas>
			</div>
			<script>
				var radarChartData = {
					labels : [
			<%if (chartName.length != 0 && chartCount.length != 0) {
				for (int i = 0; i < chartName.length;) {
					out.print("\"" + chartName[i] + "\"");
					i++;
					if (i == chartCount.length) {
						break;
					}
					out.print(",");
				}
			}%>
				],
					datasets : [ {
						fillColor : "rgba(200,50,50,0.8)",
						strokeColor : "rgba(255,0,0,1)",
						pointColor : "rgba(255,0,0,1)",
						pointStrokeColor : "#fff",
						data : [
			<%if (chartName.length != 0 && chartCount.length != 0) {
				for (int i = 0; i < chartCount.length;) {
					out.print(chartCount[i]);
					i++;
					if (i == chartCount.length) {
						break;
					}
					out.print(",");
				}
			}%>
				]
					} ]
				}
				var myRadar = new Chart(document.getElementById("radar")
						.getContext("2d")).Radar(radarChartData, {
					scaleShowLabels : true,
					scaleFontSize : 15,
					scaleLineColor : "rgba(0,0,0,0.9)",
					pointLabelFontSize : 20,
				});
			</script>
			<div class="badgeView">
				<div class="badgeTabs" id="badgeTabs">
					<a href="javascript:badgeChange('totalTab');graphChangeTotal();" class="totalTab" id="totalTabStyle">入社以来</a> <a href="javascript:badgeChange('monthTab');graphChangeMonth();" class="monthTab" id="monthTabStyle">1ケ月間</a> <a href="javascript:badgeChange('yearTab');graphChangeYear();" class="yearTab" id="yearTabStyle">1年間</a>
				</div>
				<div id="totalTab" class="badgeTotal">
					<c:if test="${empty employeeBadgeDetail}" var="flgA" />
					<c:if test="${flgA == true}">
						<h4 class="notActivity">まだバッジの取得はありません</h4>
					</c:if>
					<c:if test="${flgA == false}">
						<hr />
						<table>
							<tr>
								<c:forEach var="employeeBadge" items="${employeeBadgeDetail}" varStatus="status">
									<td class="badgeTd"><img src="../images/batch/${employeeBadge.badgeImgPath}.png" class="badgeImg"> × ${employeeBadge.badgeCount}</td>
									<c:if test="${status.count%5 == 0 && !status.last}">
							</tr>
						</table>
						<hr />
						<table>
							<tr>
								</c:if>
								<c:if test="${status.last && status.count%5 !=0}">
									<c:forEach var="i" begin="1" end="${5-status.count%5}" step="1">
										<td></td>
									</c:forEach>
								</c:if>

								</c:forEach>
						</table>
						<hr />
					</c:if>
				</div>
				<div id="monthTab" class="badgeMonth">
					<c:if test="${empty employeeBadgeMonth}" var="flgA" />
					<c:if test="${flgA == true}">
						<h4 class="notActivity">一か月前までにバッジの取得はありません</h4>
					</c:if>
					<c:if test="${flgA == false}">
						<hr />
						<table>
							<tr>
								<c:forEach var="employeeBadge" items="${employeeBadgeDetail}" varStatus="status">
									<td class="badgeTd"><img src="../images/batch/${employeeBadge.badgeImgPath}.png" class="badgeImg"> × ${employeeBadge.badgeCount}</td>
									<c:if test="${status.count%5 == 0 && !status.last}">
							</tr>
						</table>
						<hr />
						<table>
							<tr>
								</c:if>
								<c:if test="${status.last && status.count%5 !=0}">
									<c:forEach var="i" begin="1" end="${5-status.count%5}" step="1">
										<td></td>
									</c:forEach>
								</c:if>

								</c:forEach>
						</table>
						<hr />
					</c:if>
				</div>
				<div id="yearTab" class="badgeYear">
					<c:if test="${empty employeeBadgeYear}" var="flgA" />
					<c:if test="${flgA == true}">
						<h4 class="notActivity">一年前までにバッジの取得はありません</h4>
					</c:if>
					<c:if test="${flgA == false}">
						<hr />
						<table>
							<tr>
								<c:forEach var="employeeBadge" items="${employeeBadgeDetail}" varStatus="status">
									<td class="badgeTd"><img src="../images/batch/${employeeBadge.badgeImgPath}.png" class="badgeImg"> × ${employeeBadge.badgeCount}</td>
									<c:if test="${status.count%5 == 0 && !status.last}">
							</tr>
						</table>
						<hr />
						<table>
							<tr>
								</c:if>
								<c:if test="${status.last && status.count%5 !=0}">
									<c:forEach var="i" begin="1" end="${5-status.count%5}" step="1">
										<td></td>
									</c:forEach>
								</c:if>

								</c:forEach>
						</table>
						<hr />
					</c:if>
				</div>
			</div>
			<div class="activities">
				<div class="tabs">
					<a href="javascript:activityChange('homeTab')" class="homeTab" id="homeTabStyle">ホメホメ履歴</a> <a href="javascript:activityChange('planTab')" class="planTab" id="planTabStyle">企画投稿履歴</a> <a href="javascript:activityChange('planCommentTab')" class="planCommentTab" id="planCommentTabStyle">企画コメント履歴</a>
				</div>
				<div id="homeTab" class="activityTab">
					<hr>
					<c:if test="${empty employeeHomeLogDetail}" var="flgA" />
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
					<c:if test="${empty employeePlanDetail}" var="flgB" />
					<c:if test="${flgB == true}">
						<h4 class="notActivity">まだ企画投稿活動はありません</h4>
					</c:if>

					<c:forEach var="employeePlan" items="${employeePlanDetail}">
						<ul>
							<li class="days">${employeePlan.days}</li>
							<li class="activity">企画：<a href="PlanDetail?planId=${employeePlan.planId}&detail=view">${employeePlan.planTitle}</a>を発案しました
							</li>
						</ul>
						<hr>
					</c:forEach>

				</div>
				<div id="planCommentTab" class="activityTab">
					<hr>
					<c:if test="${empty employeePlanCommentDetail}" var="flgC" />
					<c:if test="${flgC ==true}">
						<h4 class="notActivity">まだ企画コメント活動はありません</h4>
					</c:if>
					<c:forEach var="employeePlanComment" items="${employeePlanCommentDetail}">
						<ul>
							<li class="days">${employeePlanComment.days}</li>
							<li class="activity">${employeePlanComment.plannerName}さんの<a href="PlanDetail?planId=${employeePlanComment.planId}&detail=view">${employeePlanComment.planName}</a>にコメントしました
							</li>
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
		<div class="clear"></div>
	</div>
</body>
</html>
