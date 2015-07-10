<%@page import="jp.ac.hal.skymoons.beans.ranking.TopNumRankingBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	@SuppressWarnings("unchecked") List<TopNumRankingBean> list = (List<TopNumRankingBean>)request.getAttribute("list");
	@SuppressWarnings("unchecked") List<String> year_list = (List<String>)request.getAttribute("year_list");
	@SuppressWarnings("unchecked") List<String> badgeList = (List<String>)request.getAttribute("badgeList");
	String year = (String)request.getAttribute("year");
	String month = (String)request.getAttribute("month");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>バッジ獲得数ランキング</title>
	</head>
	<body>
		<h1>バッジ獲得数ランキング</h1>
		
<%
// ==========================================================================================
//  絞り込みフォームの生成
// ==========================================================================================
	
	if(year_list.size() != 0) {
		out.println("<form action='/HomeSystem/fc/ranking/topnum' method='get'>");
		out.println("<p>日付指定</p>");
		
		
		
		out.println("<select name='badge'");
		out.println("<option value='0'>全て</option>");
		for(String value : badgeList) {
			
		}
		out.println("</select>");
		
		
		
		out.println("<select name='year'>");
		out.println("<option value='0'>指定無し</option>");
		for(String value : year_list) {
			out.print("<option value='" + value + "'");
			if(year != null) {
				if(year.equals(value)) {
					out.print(" selected");
				}
			}
			out.println(">" + value + "</option>");
		}
		out.println("</select>年");
		
		
		
		out.println("<select name='month'>");
		out.println("<option value='0'>指定無し</option>");
		for(int i = 1; i <= 12; i++) {
			out.print("<option value='" + i + "'");
			System.out.println("A");
			if(month != null) {
				System.out.println("B");
				if(month.equals(i)) {
					out.print(" selected");
				}
			}
			out.println(">" + i + "</option>");
		}
		out.println("</select>月");
		
		
		
		out.println("<input type='submit' name='submit' value='表示'>");
		out.println("</form>");
	}



// ==========================================================================================
//  ランキングリスト出力
// ==========================================================================================

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