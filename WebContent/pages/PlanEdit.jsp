<%@page import="jp.ac.hal.skymoons.util.Utility"%>
<%@page import="jp.ac.hal.skymoons.beans.CommentBean"%>
<%@page import="jp.ac.hal.skymoons.beans.GenreBean"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企画編集</title>
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="../css/bootstrap-theme.min.css">
<%
	PlanBean plan = (PlanBean) request.getAttribute("plan");
		ArrayList<GenreBean> genreList = (ArrayList<GenreBean>) request
				.getAttribute("genreList");
		ArrayList<GenreBean> planGenre = (ArrayList<GenreBean>) request
				.getAttribute("planGenre");
%>
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
	$(function() {
		// 現在日時
		var current = new Date(<% out.print(plan.getStartDate().getTime()); %>);
		var end = new Date(<% out.print(plan.getEndDate().getTime()); %>);

		// 年
		var year_val = current.getFullYear();
		// 月 0（1月）～11（12月）
		var month_val = current.getMonth() + 1;
		// 日
		var day_val = current.getDate();

		var hour_val = current.getHours();

		var minutes_val = Math.floor(current.getMinutes()/10)*10;

		// 年
		var end_year_val = end.getFullYear();
		// 月 0（1月）～11（12月）
		var end_month_val = end.getMonth() + 1;
		// 日
		var end_day_val = end.getDate();

		var end_hour_val = end.getHours();

		var end_minutes_val = Math.floor(end.getMinutes()/10)*10;

		// デフォルト
		$('select[name=startYear] option[value=' + year_val + ']').prop('selected',
				true);
		$('select[name=startMonth] option[value=' + month_val + ']').prop(
				'selected', true);
		$('select[name=startDay] option[value=' + day_val + ']').prop('selected',
				true);

		$('select[name=startHour] option[value=' + hour_val + ']').prop('selected',
				true);
		$('select[name=startMinutes] option[value=' + minutes_val + ']').prop('selected',
				true);

		$('select[name=endYear] option[value=' + end_year_val + ']').prop('selected',
				true);
		$('select[name=endMonth] option[value=' + end_month_val + ']').prop(
				'selected', true);
		$('select[name=endDay] option[value=' + end_day_val + ']').prop('selected',
				true);

		$('select[name=endHour] option[value=' + end_hour_val + ']').prop('selected',
				true);
		$('select[name=endMinutes] option[value=' + end_minutes_val + ']').prop('selected',
				true);

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

		$('select[name=endYear], select[name=endMonth], select[name=endDay], select[name=endHour], select[name=endMinutes],select[name=startYear], select[name=startMonth], select[name=startDay], select[name=startHour], select[name=startMinutes]').change(function() {
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
			var startDate = new Date($('select[name=startYear]').val(),$('select[name=startMonth]').val(),$('select[name=startDay]').val(),$('select[name=startHour]').val(),$('select[name=startMinutes]').val());
			var endDate = new Date($('select[name=endYear]').val(),$('select[name=endMonth]').val(),$('select[name=endDay]').val(),$('select[name=endHour]').val(),$('select[name=endMinutes]').val());

			if(endDate < startDate){
				$('#submit').prop('disabled',true);
			}else{
				$('#submit').prop('disabled',false);
			}
		}
	});
</script>
</head>
<body>
	<form action="/HomeSystem/fc/PlanDetail" method="post">
		<table>

			<tr>
				<th>企画ID</th>
				<td>
					<%
						out.println(plan.getPlanId());
					%> <input type="hidden" name="planId"
					value=<%out.println(plan.getPlanId());%>>
				</td>
			</tr>
			<tr>
				<th>企画名</th>
				<td><input type="text" name="planTitle"
					value="<%out.println(plan.getPlanTitle());%>"></td>
			</tr>
			<tr>
				<th>企画者</th>
				<td>
					<%
						out.println(plan.getPlannerName());
					%>
				</td>
			</tr>
			<tr>
				<th>企画日</th>
				<td>
					<%
						out.println(plan.getPlanDatetime());
					%>
				</td>
			</tr>
			<tr>
				<th>企画内容</th>
				<td>
					<%
						out.println("<textarea name=\"planComment\">"
								+ plan.getPlanComment() + "</textarea>");
					%>
				</td>
			</tr>
			<tr>
				<th>実施予定日：</th>
				<td><select name="startYear">
						<%
							for (int i = 2000; i <= 2100; i++) {
								out.println("<option value=\"" + i + "\">" + i + "</option>");
							}
						%>
				</select> 年 <SELECT name="startMonth">
						<%
							for (int i = 1; i <= 12; i++) {
								out.println("<option value=\"" + i + "\">" + i + "</option>");
							}
						%>
				</SELECT> 月 <SELECT name="startDay">
						<%
							for (int i = 1; i <= 31; i++) {
								out.println("<option value=\"" + i + "\">" + i + "</option>");
							}
						%>
				</select> 日
				<SELECT name="startHour">
						<%
							for (int i = 1; i <= 24; i++) {
								out.println("<option value=\"" + i + "\">" + i + "</option>");
							}
						%>
				</select> 時 <SELECT name="startMinutes">
						<%
							for (int i = 0; i < 60; i+=10) {
								out.println("<option value=\"" + i + "\">" + i + "</option>");
							}
						%>

				</select> 分
				～
				<select name="endYear">
						<%
							for (int i = 2000; i <= 2100; i++) {
								out.println("<option value=\"" + i + "\">" + i + "</option>");
							}
						%>
				</select> 年 <SELECT name="endMonth">
						<%
							for (int i = 1; i <= 12; i++) {
								out.println("<option value=\"" + i + "\">" + i + "</option>");
							}
						%>
				</SELECT> 月 <SELECT name="endDay">
						<%
							for (int i = 1; i <= 31; i++) {
								out.println("<option value=\"" + i + "\">" + i + "</option>");
							}
						%>
				</select> 日
				<SELECT name="endHour">
						<%
							for (int i = 1; i <= 24; i++) {
								out.println("<option value=\"" + i + "\">" + i + "</option>");
							}
						%>
				</select> 時 <SELECT name="endMinutes">
						<%
							for (int i = 0; i < 60; i+=10) {
								out.println("<option value=\"" + i + "\">" + i + "</option>");
							}
						%>

				</select> 分
				</td>
			</tr>
		</table>
		<input type="submit" name="edit" id="submit" value="登録">
		<hr>
		<%
			int genreIndex = 0;
			int genreMaxIndex = planGenre.size();
			for (GenreBean genre : genreList) {
				//入力されたジャンルがない　または　既存ジャンルにチェック済み
				if (genreMaxIndex == genreIndex) {
					out.println("<label><input type=\"checkbox\" name=\"genre\" value=\""
							+ genre.getGenreId()
							+ "\">"
							+ genre.getGenreName()
							+ "</label>");
				} else {
					//既存ジャンルIDと出力ジャンルIDが一致
					if (planGenre.get(genreIndex).getGenreId() == genre
							.getGenreId()) {
						out.println("<label><input type=\"checkbox\" name=\"genre\" value=\""
								+ genre.getGenreId()
								+ "\" checked=\"checked\">"
								+ genre.getGenreName() + "</label>");
						genreIndex++;
					} else {
						out.println("<label><input type=\"checkbox\" name=\"genre\" value=\""
								+ genre.getGenreId()
								+ "\">"
								+ genre.getGenreName() + "</label>");
					}
				}

			}
		%>
	</form>
</body>
</html>