<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="jp.ac.hal.skymoons.beans.ranking.BatchBean"%>
<%@page import="jp.ac.hal.skymoons.beans.ranking.TopNumRankingBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	@SuppressWarnings("unchecked") List<TopNumRankingBean> list = (List<TopNumRankingBean>)request.getAttribute("list");
	int minYear = Integer.parseInt((String)request.getAttribute("year"));
	@SuppressWarnings("unchecked") List<BatchBean> batchList = (List<BatchBean>)request.getAttribute("batchList");
	String batch = (String)request.getAttribute("batch");
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
	
	// START
	out.println("<form action='/HomeSystem/fc/ranking/topnum' method='get'>");

	out.println("<p>絞り込み</p>");
		
	// バッチ指定
	out.println("<select name='batch'>");
	out.println("<option value='0' selected>全て</option>");
	if(batch == null) {
		// 前回バッチ指定無し
		for(int i = 0; i < batchList.size(); i++) {
			out.println("<option value='" + batchList.get(i).getBatch_id() + "'>" + batchList.get(i).getBatch_name() + "</option>");
		}
	} else {
		// 前回バッチ指定有り
		for(int i = 0; i < batchList.size(); i++) {
			out.print("<option value='" + batchList.get(i).getBatch_id() + "'");
			if(batch.equals(String.valueOf(batchList.get(i).getBatch_id()))) {
				out.print(" selected");
			}
			out.println(">" + batchList.get(i).getBatch_name() + "</option>");
		}
	}
	out.println("</select>");
		
	// 年指定
	out.println("<select name='year'>");
	out.println("<option value='0' selected>指定無し</option>");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	int nowYear = Integer.parseInt(sdf.format(new Date()));
	nowYear = 210;
	if(year == null) {
		// 前回年指定無し
		for (int i = nowYear; i >= minYear; i--) {
			out.println("<option value='" + i + "'>" + i + "</option>");
		}
	} else {
		// 前回年指定有り
		for (int i = nowYear; i >= minYear; i--) {
			out.print("<option value='" + i + "'");
			if(Integer.parseInt(year) == i) {
				out.print(" selected");
			}
		out.println(">" + i + "</option>");
		}
	}
	out.println("</select>年");
		
	// 月指定
	out.println("<select name='month'>");
	out.println("<option value='0' selected>指定無し</option>");
	if(month == null) {
		// 前回月指定無し
		for(int i = 1; i <= 12; i++) {
			out.println("<option value='" + i + "'>" + i + "</option>");
		}
	} else {
		// 前回月指定有り
		for(int i = 1; i <= 12; i++) {
			out.print("<option value='" + i + "'");
			if(month.equals(String.valueOf(i))) {
				out.print(" selected");
			}
			out.println(">" + i + "</option>");
		}
	}
	out.println("</select>月");
		
	// 送信ボタン
	out.println("<input type='submit' name='submit' value='表示'>");
	// END
	out.println("</form>");



// ==========================================================================================
//  ランキングリスト出力
// ==========================================================================================

	if(list.size() != 0) {
		out.print("<table border='1'>"
			+ "<tr>"
			+ "<th>順位</th>"
			+ "<th>獲得数</th>"
			+ "<th>名前</th>"
			+ "</tr>");
		// 順位管理
		int num = 0;
		int outnum = 1;
		long value = 0;
		for(int i = 0; i < list.size(); i++) {
			num ++;
			if(value != list.get(i).getValue()) {
				outnum = num;
				value = list.get(i).getValue();
			}
			out.println("<tr><td>" + outnum + "</td>"
				+ "<td>" + list.get(i).getValue() + "</td>"
				+ "<td>" + list.get(i).getName() + "</td></tr>");
		}
		out.println("</table>");
	} else {
		out.println("<p>データがありません。</p>");
	}



// ==========================================================================================
//  ページ番号生成
// ==========================================================================================

	/*
	// 表示ページ数
	int outpages = 5;
	// 今のページ番号
	int nowpages = 1;
	if(request.getParameter("page") != null) {
		nowpages = Integer.parseInt(request.getParameter("page"));
	}

	System.out.println(list.size());
	*/
%>
	</body>
</html>
