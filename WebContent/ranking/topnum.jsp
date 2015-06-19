<%@page import="jp.ac.hal.skymoons.beans.TopNumRankingBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	@SuppressWarnings("unchecked") List<TopNumRankingBean> list = (List<TopNumRankingBean>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>総獲得数ランキング</title>
	</head>
	<body>
		<h1>総獲得数ランキング</h1>
		
		<form action="/HomeSystem/fc/ranking/topnum">
			<p>日付指定</p>
			<select name="year">
				<option>指定無し</option>
				<option value="2016">2016</option>
				<option value="2015">2015</option>
				<option value="2014">2014</option>
				<option value="2013">2013</option>
			</select>年
			<select name="month">
				<option>指定無し</option>
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
			</select>月
			<input type="submit" name="submit" value="表示">
		</form>
<%
	if(list != null) {
		out.print("<table border='1'>"
			+ "<tr>"
			+ "<th>順位</th>"
			+ "<th>獲得数</th>"
			+ "<th>名前</th>"
			+ "</tr>");
		int num = 0;
		int outnum = 1;
		long value = 0;
		for(int i = 0; i < list.size(); i++) {
			num ++;
			if(value != list.get(i).getValue()) {
				outnum = num;
				value = list.get(i).getValue();
			}
			out.print("<tr><td>" + outnum + "</td>"
				+ "<td>" + list.get(i).getValue() + "</td>"
				+ "<td>" + list.get(i).getName() + "</td></tr>");
		}
		out.println("</table>");
	} else {
		out.println("<p>データがありません。</p>");
	}
%>
		<p>ページ切り替え</p>
		1  2  3  4  5  6  7
	</body>
</html>