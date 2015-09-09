<%@page import="jp.ac.hal.skymoons.beans.ranking.BatchBean"%>
<%@page import="jp.ac.hal.skymoons.beans.ranking.TopNumRankingBean"%>
<%@page import="java.util.List"%>
<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    @SuppressWarnings("unchecked")
    List<TopNumRankingBean> list = (List<TopNumRankingBean>) request
		    .getAttribute("list");
    @SuppressWarnings("unchecked")
    List<String> yearList = (List<String>) request
		    .getAttribute("yearList");
    @SuppressWarnings("unchecked")
    List<BatchBean> batchList = (List<BatchBean>) request
		    .getAttribute("batchList");
    String batch = (String) request.getAttribute("batch");
    String year = (String) request.getAttribute("year");
    String month = (String) request.getAttribute("month");
    SessionController sessionController = new SessionController(request);
%>
<!DOCTYPE htm>
<html lang="ja">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../../css/reset.css">
<link rel="stylesheet" type="text/css" href="../../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../../css/topnum.css">
<title>ランキング</title>
</head>
<body>


	<div class="wrapper">
		<div class="title">
			<img class="rankLogo" src="../../images/rank/rank.png" />
			<h1 class="titleName">ランキング</h1>
		</div>

		<%
		    // ==========================================================================================
		    //  絞り込みフォームの生成
		    // ==========================================================================================

		    out.print("<div class=\"sort\">");
		    // START
		    out.println("<form action='/HomeSystem/fc/ranking/topnum' method='get'>");

		    // バッチ指定
		    out.println("<select name='batch'>");
		    out.println("<option value='0' selected>総合</option>");
		    if (batch == null) {
				// 前回バッチ指定無し
				for (int i = 0; i < batchList.size(); i++) {
				    out.println("<option value='"
					    + batchList.get(i).getBatch_id() + "'>"
					    + batchList.get(i).getBatch_name() + "</option>");
				}
		    } else {
				// 前回バッチ指定有り
				for (int i = 0; i < batchList.size(); i++) {
				    out.print("<option value='"
					    + batchList.get(i).getBatch_id() + "'");
				    if (batch.equals(String.valueOf(batchList.get(i)
					    .getBatch_id()))) {
					out.print(" selected");
				    }
				    out.println(">" + batchList.get(i).getBatch_name()
					    + "</option>");
				}
		    }
		    out.println("</select>");

		    // 年指定
		    out.println("<select name='year'>");
		    out.println("<option value='0' selected>指定無し</option>");
		    if (year == null) {
				// 前回年指定無し
				for (String value : yearList) {
				    out.println("<option value='" + value + "'>" + value
					    + "</option>");
				}
		    } else {
				// 前回年指定有り
				for (String value : yearList) {
				    out.print("<option value='" + value + "'");
				    if (year.equals(value)) {
					out.print(" selected");
				    }
				    out.println(">" + value + "</option>");
				}
		    }
		    out.println("</select>年");

		    // 月指定
		    out.println("<select name='month'>");
		    out.println("<option value='0' selected>指定無し</option>");
		    if (month == null) {
				// 前回月指定無し
				for (int i = 1; i <= 12; i++) {
				    out.println("<option value='" + i + "'>" + i + "</option>");
				}
		    } else {
				// 前回月指定有り
				for (int i = 1; i <= 12; i++) {
				    out.print("<option value='" + i + "'");
				    if (month.equals(String.valueOf(i))) {
					out.print(" selected");
				    }
				    out.println(">" + i + "</option>");
				}
		    }
		    out.println("</select>月");

		    // 送信ボタン
		    out.println("<input type='submit' name='submit' value='表示' class='btn btn-2 btn-2c submitBtn'>");
		    // END
		    out.println("</form><br>");

		    out.println("</div>");

		    // ==========================================================================================
		    //  ランキングリスト出力
		    // ==========================================================================================

		    out.print("<div class=\"rankingName\">");
		    if (year != null) {
				out.print(year + "年&nbsp;");
		    }

		    if (month != null) {
				out.print(month + "月&nbsp;");
		    }

		    if (batch == null) {
				out.print("総合");
		    } else {
				out.print(batchList.get(Integer.valueOf(batch)).getBatch_name());
		    }

		    out.print("ランキング");

		    out.print("</div>");

		    // 		    out.print("<div class=\"ranking\">");

		    if (list.size() != 0) {

				// 順位管理
				int num = 0;
				int outnum = 1;
				long value = 0;
				for (int i = 0; i < list.size(); i++) {
				    num++;
				    if (value != list.get(i).getValue()) {
					outnum = num;
					value = list.get(i).getValue();
				    }

				    if (outnum > 10) {
					break;
				    }

				    int level = list.get(i).getLevel();
				    if (level > 12) {
					level = 12;
				    }

				    String employeePageUrl = "";
				    String userId = sessionController.getUserId();

				    if (userId.equals(list.get(i).getId())) {
					employeePageUrl = "/HomeSystem/fc/EmployeeMyPage";
				    } else {
					employeePageUrl = "/HomeSystem/fc/EmployeePage?employeeId="
						+ list.get(i).getId();
				    }

				    out.print("<div class=\"ranking\">");

				    out.print("<div class=\"rows\">");

				    out.print("<div class=\"rank\">");
				    out.print("<img src=\"../../images/rank/" + outnum
					    + ".png\">");
				    out.print("</div>");

				    out.print("<div class=\"face\">");
				    out.print("<a href=\"" + employeePageUrl
					    + "\"><img src=\"../../images/employees/"
					    + list.get(i).getId() + ".jpg\"></a>");

				    out.print("</div>");

				    out.print("<a href=\"" + employeePageUrl + "\">");

				    out.print("<div class=\"flameDiv\"><img src=\"/HomeSystem/images/flame/"
					    + level + ".png\" class=\"employeeFlame\"></div>");

				    out.print("</a>");

				    out.print("<div class=\"detail\">");
				    out.print("<div class=\"department\">"
					    + list.get(i).getDepartment() + "</div>");
				    out.print("<a href=\"" + employeePageUrl
					    + "\"><div class=\"employeeName\">"
					    + list.get(i).getName() + "</div></a>");
				    out.print("<div class=\"count\">" + list.get(i).getValue()
					    + "個</div>");

				    out.print("</div>");

				    out.print("</div>");

				    out.print("</div>");

				    out.print("<hr>");

				}

				// 				out.print("</div>");

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
	</div>
</body>
</html>
