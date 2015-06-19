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

		// デフォルト
		$('select[name=year] option[value=' + year_val + ']').prop('selected',
				true);
		$('select[name=month] option[value=' + month_val + ']').prop(
				'selected', true);
		$('select[name=day] option[value=' + day_val + ']').prop('selected',
				true);
		setDay();

		// 年/月 選択
		$('select[name=year], select[name=month]').change(function() {
			setDay();
		});

		/**
		 * 日プルダウンの制御
		 */
		function setDay() {
			year_val = $('select[name=year]').val();
			month_val = $('select[name=month]').val();

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
			$('select[name=day] option').remove();
			for (var i = 1; i <= t; i++) {
				if (i == day_val) {
					$('select[name=day]').append(
							'<option value="' + i + '" selected>' + i
									+ '</option>')
				} else {
					$('select[name=day]').append(
							'<option value="' + i + '">' + i + '</option>');
				}

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
				<td><select name="year">
						<%
							for (int i = 2000; i <= 2100; i++) {
								out.println("<option value=\"" + i + "\">" + i + "</option>");
							}
						%>
				</select> 年 <SELECT name="month">
						<%
							for (int i = 1; i <= 12; i++) {
								out.println("<option value=\"" + i + "\">" + i + "</option>");
							}
						%>
				</SELECT> 月 <SELECT name="day">
						<%
							for (int i = 1; i <= 31; i++) {
								out.println("<option value=\"" + i + "\">" + i + "</option>");
							}
						%>
				</select> 日
<!-- 				<SELECT name="hour"> -->
						<%
// 							for (int i = 1; i <= 24; i++) {
// 								out.println("<option value=\"" + i + "\">" + i + "</option>");
// 							}
						%>
<!-- 				</select> 時 <SELECT name="minutes"> -->
						<%
// 							for (int i = 0; i < 60; i++) {
// 								out.println("<option value=\"" + i + "\">" + i + "</option>");
// 							}
						%>


<!-- 				</select> 分</td> -->
			</tr>
		</table>
		<input type="submit" name="submit" value="登録"><br>
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