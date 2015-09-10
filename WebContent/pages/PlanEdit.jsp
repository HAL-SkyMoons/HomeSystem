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
<title>企画編集</title>
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="../css/PlanEdit.css">
<%
    PlanBean plan = (PlanBean) request.getAttribute("plan");
%>
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../js/autosize/autosize.js"></script>
<script type="text/javascript">
	$(function() {
		// 現在日時
		var current = new Date(
<%out.print(plan.getStartDate().getTime());%>
	);
		var end = new Date(
<%out.print(plan.getEndDate().getTime());%>
	);

		// 年
		var year_val = current.getFullYear();
		// 月 0（1月）～11（12月）
		var month_val = current.getMonth() + 1;
		// 日
		var day_val = current.getDate();

		var hour_val = current.getHours();

		var minutes_val = Math.floor(current.getMinutes() / 10) * 10;

		// 年
		var end_year_val = end.getFullYear();
		// 月 0（1月）～11（12月）
		var end_month_val = end.getMonth() + 1;
		// 日
		var end_day_val = end.getDate();

		var end_hour_val = end.getHours();

		var end_minutes_val = Math.floor(end.getMinutes() / 10) * 10;

		// デフォルト
		$('select[name=startYear] option[value=' + year_val + ']').prop(
				'selected', true);
		$('select[name=startMonth] option[value=' + month_val + ']').prop(
				'selected', true);
		$('select[name=startDay] option[value=' + day_val + ']').prop(
				'selected', true);

		$('select[name=startHour] option[value=' + hour_val + ']').prop(
				'selected', true);
		$('select[name=startMinutes] option[value=' + minutes_val + ']').prop(
				'selected', true);

		$('select[name=endYear] option[value=' + end_year_val + ']').prop(
				'selected', true);
		$('select[name=endMonth] option[value=' + end_month_val + ']').prop(
				'selected', true);
		$('select[name=endDay] option[value=' + end_day_val + ']').prop(
				'selected', true);

		$('select[name=endHour] option[value=' + end_hour_val + ']').prop(
				'selected', true);
		$('select[name=endMinutes] option[value=' + end_minutes_val + ']')
				.prop('selected', true);

		setStartDay();
		setEndDay();
		inputCheck();

		// 年/月 選択
		$('select[name=startYear], select[name=startMonth]').change(function() {
			setStartDay();
		});

		$('select[name=endYear], select[name=endMonth]').change(function() {
			setEndDay();
		});

		$(
				'select[name=endYear], select[name=endMonth], select[name=endDay], select[name=endHour], select[name=endMinutes],select[name=startYear], select[name=startMonth], select[name=startDay], select[name=startHour], select[name=startMinutes]')
				.change(function() {
					inputCheck();
				});

		/**
		 * 日プルダウンの制御
		 */
		function setStartDay() {
			year_val = $('select[name=startYear]').val();
			month_val = $('select[name=startMonth]').val();

			// 指定月の末日
			var t = 31;
			// 2月
			if (month_val == 2) {
				//　4で割りきれる且つ100で割りきれない年、または400で割り切れる年は閏年
				if (Math.floor(year_val % 4) == 0
						&& Math.floor(year_val % 100) != 0
						|| Math.floor(year_val % 400) == 0) {
					t = 29;
				} else {
					t = 28;
				}
				// 4,6,9,11月
			} else if (month_val == 4 || month_val == 6 || month_val == 9
					|| month_val == 11) {
				t = 30;
			}

			// 初期化
			$('select[name=startDay] option').remove();
			for (var i = 1; i <= t; i++) {
				if (i == day_val) {
					$('select[name=startDay]').append(
							'<option value="' + i + '" selected>' + i
									+ '</option>')
				} else {
					$('select[name=startDay]').append(
							'<option value="' + i + '">' + i + '</option>');
				}

			}
		}

		function setEndDay() {
			year_val = $('select[name=endYear]').val();
			month_val = $('select[name=endMonth]').val();

			// 指定月の末日
			var t = 31;
			// 2月
			if (month_val == 2) {
				//　4で割りきれる且つ100で割りきれない年、または400で割り切れる年は閏年
				if (Math.floor(year_val % 4) == 0
						&& Math.floor(year_val % 100) != 0
						|| Math.floor(year_val % 400) == 0) {
					t = 29;
				} else {
					t = 28;
				}
				// 4,6,9,11月
			} else if (month_val == 4 || month_val == 6 || month_val == 9
					|| month_val == 11) {
				t = 30;
			}

			// 初期化
			$('select[name=endDay] option').remove();
			for (var i = 1; i <= t; i++) {
				if (i == day_val) {
					$('select[name=endDay]').append(
							'<option value="' + i + '" selected>' + i
									+ '</option>')
				} else {
					$('select[name=endDay]').append(
							'<option value="' + i + '">' + i + '</option>');
				}

			}

		}

		function inputCheck() {
			var startDate = new Date($('select[name=startYear]').val(), $(
					'select[name=startMonth]').val(),
					$('select[name=startDay]').val(), $(
							'select[name=startHour]').val(), $(
							'select[name=startMinutes]').val());
			var endDate = new Date($('select[name=endYear]').val(), $(
					'select[name=endMonth]').val(), $('select[name=endDay]')
					.val(), $('select[name=endHour]').val(), $(
					'select[name=endMinutes]').val());

			if (endDate < startDate) {
				$('#submit').prop('disabled', true);
				var info = document.getElementById('dateInfo');
				info.innerHTML = '※期間が正しくありません'

				$("#startEnd").css("background-color",
						"rgba(255, 221, 221, 0.7)");
			} else {
				$('#submit').prop('disabled', false);
				var info = document.getElementById('dateInfo');
				info.innerHTML = ''

				$("#startEnd").css("background-color", "");
			}
		}
	});

	function formCheck() {

		if (window.confirm('送信してよろしいですか？')) { // 確認ダイアログを表示

			return true; // 「OK」時は送信を実行

		} else { // 「キャンセル」時の処理

			return false; // 送信を中止

		}

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
</head>
<body>
	<div id="wrapper">
		<form action="/HomeSystem/fc/PlanDetail" method="post">
			<div id="plan">
				<div id="planHeader">
					<div id="id">
						No.${plan.planId}
						<input type="hidden" name="planId" value="${plan.planId}">
					</div>
					<div id="planDate">
						<jsp:useBean id="date" class="java.util.Date" />
						企画日：
						<fmt:formatDate value="${plan.planDatetime}" pattern="yyyy年MM月dd日" />
					</div>
				</div>
				<div id="title">
					<textarea id="titleValue" rows="1" name="planTitle" placeholder="企画タイトル" maxlength="100" required="required">${plan.planTitle}</textarea>
				</div>
				<div id="planDetail">
					<div id="startEnd">
						<div>企画実施日</div>
						<select name="startYear">
							<%
							    for (int i = 2000; i <= 2100; i++) {
											out.println("<option value=\"" + i + "\">" + i + "</option>");
										}
							%>
						</select>
						年
						<SELECT name="startMonth">
							<%
							    for (int i = 1; i <= 12; i++) {
											out.println("<option value=\"" + i + "\">" + i + "</option>");
										}
							%>
						</SELECT>
						月
						<SELECT name="startDay">
							<%
							    for (int i = 1; i <= 31; i++) {
											out.println("<option value=\"" + i + "\">" + i + "</option>");
										}
							%>
						</select>
						日
						<SELECT name="startHour">
							<%
							    for (int i = 1; i <= 24; i++) {
											out.println("<option value=\"" + i + "\">" + i + "</option>");
										}
							%>
						</select>
						時
						<SELECT name="startMinutes">
							<%
							    for (int i = 0; i < 60; i += 10) {
											out.println("<option value=\"" + i + "\">" + i + "</option>");
										}
							%>

						</select>
						分 <br>
						<div class="centre">&#x7C;</div>
						<select name="endYear">
							<%
							    for (int i = 2000; i <= 2100; i++) {
											out.println("<option value=\"" + i + "\">" + i + "</option>");
										}
							%>
						</select>
						年
						<SELECT name="endMonth">
							<%
							    for (int i = 1; i <= 12; i++) {
											out.println("<option value=\"" + i + "\">" + i + "</option>");
										}
							%>
						</SELECT>
						月
						<SELECT name="endDay">
							<%
							    for (int i = 1; i <= 31; i++) {
											out.println("<option value=\"" + i + "\">" + i + "</option>");
										}
							%>
						</select>
						日
						<SELECT name="endHour">
							<%
							    for (int i = 1; i <= 24; i++) {
											out.println("<option value=\"" + i + "\">" + i + "</option>");
										}
							%>
						</select>
						時
						<SELECT name="endMinutes">
							<%
							    for (int i = 0; i < 60; i += 10) {
											out.println("<option value=\"" + i + "\">" + i + "</option>");
										}
							%>

						</select>
						分 <br>
						<div id="dateInfo"></div>
					</div>
					<div id="planner">
						企画者
						<div id="img">
							<img src="../images/employees/${plan.planner}.jpg">
						</div>
						<div class="flameDiv">
							<img src="../images/flame/${plan.level}.png" class="employeeFlame">
						</div>
						${plan.plannerName}
						<input type="hidden" name="planner" value="${plan.planner}">
					</div>
				</div>
				<div id="planComment">
					<textarea id="planCommentValue" rows="1" name="planComment" placeholder="企画内容" maxlength="1000">${plan.planComment}</textarea>
				</div>
				<br>
				<c:forEach var="bigGenre" items="${bigGenreList}">
					<span class="trigger">&#9661;${bigGenre.bigGenreName }</span>
					<div class="toggle_container">
						<table>
							<tr>
								<c:set var="count" value="0" />
								<c:forEach var="genre" items="${genreList}" varStatus="status">

									<c:if test="${genre.bigGenreId == bigGenre.bigGenreId}">

										<td><label> <c:if test="${planGenre.containsKey(genre.genreId)}">
													<input type="checkbox" name="genre" value="${genre.genreId }" checked="checked">${genre.genreName }
									</c:if> <c:if test="${!planGenre.containsKey(genre.genreId)}">
													<input type="checkbox" name="genre" value="${genre.genreId }">${genre.genreName }
									</c:if>

										</label></td>
										<c:set var="count" value="${count+1}" />
									</c:if>
									<c:if test="${count%3 ==0}">
							</tr>
							<tr>
								</c:if>
								</c:forEach>
							</tr>
						</table>
					</div>
					<br>

				</c:forEach>
			</div>
			<div class="btnZone">
				<input type="submit" name="edit" class="btn btn-2 btn-2c submit" id="submit" value="登録">
			</div>
		</form>
		<form action="/HomeSystem/fc/PlanDetail" method="post" onSubmit="return formCheck()">
			<div class="btnZone">
				<input type="submit" name="planEnd" class="btn btn-2 btn-2c planEnd" value="企画終了">
				<input type="hidden" name="planId" value="${plan.planId }">
			</div>
		</form>
	</div>

</body>
<script>
	autosize(document.querySelectorAll('textarea'));
</script>
</html>