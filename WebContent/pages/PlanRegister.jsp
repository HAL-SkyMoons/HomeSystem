<%@page import="jp.ac.hal.skymoons.beans.UserBean"%>
<%@page import="jp.ac.hal.skymoons.beans.GenreBean"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企画登録</title>
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="../css/bootstrap-responsive.css">
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
	$(function() {
		// 現在日時
		var current = new Date();

		// 年
		var year_val = current.getFullYear();
		// 月 0（1月）～11（12月）
		var month_val = current.getMonth() + 1;
		// 日
		var day_val = current.getDate();

		var hour_val = current.getHours();

		var minutes_val = Math.floor(current.getMinutes()/10)*10;

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

		$('select[name=endYear] option[value=' + year_val + ']').prop('selected',
				true);
		$('select[name=endMonth] option[value=' + month_val + ']').prop(
				'selected', true);
		$('select[name=endDay] option[value=' + day_val + ']').prop('selected',
				true);

		$('select[name=endHour] option[value=' + hour_val + ']').prop('selected',
				true);
		$('select[name=endMinutes] option[value=' + minutes_val + ']').prop('selected',
				true);

		setStartDay();
		setEndDay();

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
<%
	UserBean user = (UserBean) request.getAttribute("user");
%>
<body>
	<form action="/HomeSystem/fc/PlanRegister" method="post">
		<table>
			<tr>
				<th>企画者：</th>
				<td>
					<%
						out.println(user.getLastName() + user.getFirstName());
					%><input type="hidden" name="planner"
					value="<%out.print(user.getUserId());%>">
				</td>
			</tr>
			<tr>
				<th>企画名：</th>
				<td><input type="text" name="planTitle"></td>
			</tr>
			<tr>
				<th>企画内容：</th>
				<td><textarea col="30" rows="10" name="planComment"></textarea></td>
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
		<input type="submit" name="submit" id="submit" value="登録"><br>
		<%
			ArrayList<GenreBean> genreList = (ArrayList<GenreBean>) request
					.getAttribute("genreList");
			for (GenreBean genre : genreList) {
				out.println("<label><input type=\"checkbox\" name=\"genre\" value=\""
						+ genre.getGenreId()
						+ ":"
						+ genre.getGenreName()
						+ "\">" + genre.getGenreName() + "</label>");
			}
		%>
	</form>
</body>
</html>