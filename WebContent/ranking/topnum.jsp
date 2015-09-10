<%@page import="jp.ac.hal.skymoons.beans.ranking.BatchBean"%>
<%@page import="jp.ac.hal.skymoons.beans.ranking.TopNumRankingBean"%>
<%@page import="java.util.List"%>
<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeeBatchBean"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeePageBean"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="jp.ac.hal.skymoons.util.Utility"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<%
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("S");
	String milliSec = sdf.format(date);
	ArrayList<EmployeePageBean> employeeDates = (ArrayList<EmployeePageBean>) request
			.getAttribute("employeeDetail");
	EmployeePageBean employeeDate = employeeDates.get(0);
	int level = employeeDate.getLevel();
	if (level > 12) {
		level = 12;
	}
%>
<!DOCTYPE htm>
<html lang="ja">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../../css/reset.css">
<link rel="stylesheet" type="text/css" href="../../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="../../css/topnum.css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/style.css">
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>
<title>ランキング</title>
<script type="text/javascript">
//画像差し替えメソッド
$(document).ready(function() {
    $('.js-replace-no-image').error(function() {
        $(this).attr({
            src: '../images/icon/NoImage.png'
        });
    });
});
</script>
</head>
<body>
	<!--******************************** こっからへっだー ***********************************-->
	<div id="allwrap">
		<div id="headerline">line</div>
		<header>

			<div id="logo">
				<a href="/HomeSystem/fc/Index"><img src="/HomeSystem/images/logo.png" /></a>
			</div>

			<div id="headerright">
				<c:forEach var="employeeDetail" items="${employeeDetail}">
					<div id="headerstr">
						<label id="headername">${employeeDetail.employeeName}さん</label><br> <label id="headerlevel">レベル${employeeDetail.level}</label>
					</div>
					<div id="headerimage">
						<img src="/HomeSystem/images/employees/${employeeDetail.employeeId}.jpg?<%=milliSec%>" class="js-replace-no-image">
						<div id="headerflame">
							<img src="/HomeSystem/images/flame/<%=level%>.png?<%=milliSec%>">
						</div>
					</div>
				</c:forEach>
				<div id="headerbutton">
					<div id="mypage">
						<form action="/HomeSystem/fc/EmployeeMyPage">
							<input type="submit" class="btn btn-2 btn-2c submit" value="マイページ">
						</form>
					</div>
					<div id="logout">
						<form action="/HomeSystem/fc/logout/user">
							<input type="submit" class="btn btn-2 btn-2c submit" value="ログアウト">
						</form>
					</div>
				</div>

			</div>

			<ul id="headermenu">
				<li class="menu1"><a href="/HomeSystem/fc/EmployeeList">社員一覧</a></li>
				<li class="menu2"><a href="#">企画</a>
					<ul>
						<li><a href="/HomeSystem/fc/PlanList">企画一覧</a></li>
						<li><a href="/HomeSystem/fc/PlanRegister">企画登録</a></li>
						<li><a href="/HomeSystem/fc/PlanCalendar">企画カレンダー</a></li>
					</ul></li>
				<li class="menu3"><a href="#">ホメホメコンテンツ</a>
					<ul>
						<li><a href="/HomeSystem/fc/contents/list">コンテンツ一覧</a></li>
						<li><a href="/HomeSystem/fc/contents/regist">コンテンツ登録</a></li>
					</ul></li>
				<li class="menu4"><a href="/HomeSystem/fc/ranking/topnum">ランキング</a></li>
			</ul>

		</header>
		<div id="allcontents">
			<!--*********************************ここまでへっだー ***********************************-->

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

				    int rankLevel = list.get(i).getLevel();
				    if (rankLevel > 12) {
					rankLevel = 12;
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
					    + rankLevel + ".png\" class=\"employeeFlame\"></div>");

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
				<!--*********************************ここからふったー ***********************************-->
		</div>
		<footer>
			<div id="footertop">
				<a href="#top">▲ページTOPへ</a>
			</div>

			<div id="footermenu">
				<div id="footermenuin">
					<div id="footermenu1">
						<h4>社員</h4>
						<p>
							<a href="/HomeSystem/fc/EmployeeList">社員一覧</a>
						</p>
						<p>
							<a href="/HomeSystem/fc/EmployeeMyPage">マイページ</a>
						</p>
					</div>
					<div id="footermenu2">
						<h4>企画</h4>
						<p>
							<a href="/HomeSystem/fc/PlanList">企画一覧</a>
						</p>
						<p>
							<a href="/HomeSystem/fc/PlanRegister">企画登校</a>
						</p>
						<p>
							<a href="/HomeSystem/fc/PlanCalendar">企画カレンダー</a>
						</p>
					</div>
					<div id="footermenu3">
						<h4>ホメホメコンテンツ</h4>
						<p>
							<a href="/HomeSystem/fc/contents/list">コンテンツ一覧</a>
						</p>
						<p>
							<a href="/HomeSystem/fc/contents/regist">コンテンツ登録</a>
						</p>
					</div>
					<div id="footermenu4">
						<h4>ランキング</h4>
						<p>
							<a href="/HomeSystem/fc/ranking/topnum">ランキング</a>
						</p>
					</div>
				</div>
			</div>
			<div id="footerline">Copyright &copy; 2015-2016 SkyMoons All Rights Reserved.</div>
		</footer>
	</div>
	<!--*********************************ここまでふったー ***********************************-->
</body>
</html>
