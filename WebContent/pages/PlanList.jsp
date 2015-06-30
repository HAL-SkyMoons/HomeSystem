<%@page import="java.util.Iterator"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanPointsBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="jp.ac.hal.skymoons.beans.UserBean"%>
<%@page import="jp.ac.hal.skymoons.beans.GenreBean"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html">
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>企画一覧</title>
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="../css/PlanList.css">
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>

<script type="text/javascript">
	function Post(planId) {
		form1.detail.value = "detail";
		form1.planId.value = planId;
		form1.submit();
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
	<div class="wrapper">
		<table class="search">
			<tr>
				<th>キーワード</th>
				<form action="/HomeSystem/fc/PlanList" method="post">
					<td class="searchDataColumn"><input type="text" class="keyword" name="keyword" value="${searchKeyword}"></td>
					<td class="searchBtnColumn"><input type="submit" name="search" class="btn btn-2 btn-2c searchBtn" value="検索"></td>
				</form>
			</tr>
			<tr>
				<th>詳細検索</th>
				<form action="/HomeSystem/fc/PlanSearch" method="post">
					<td class="searchDetailColumn">
						<div class="detailData">
							<span class="detailTitle">ジャンル:</span><span class="detailInside">Java</span>,<span class="detailInside">php</span>,<span class="detailInside">Python</span>
						</div>
						<div class="detailData">
							<span class="detailTitle">ジャンル:</span><span class="detailInside">Java</span>,<span class="detailInside">php</span>,<span class="detailInside">Python</span>
						</div>
					</td>
					<td class="searchBtnColumn"><input type="submit" name="searchDetail" class="btn btn-2 btn-2c searchDetailBtn" value="詳細検索"></td>
				</form>
			</tr>


		</table>
		<hr>

		<div id="list">
			<div class="data">
				<div class="title">2泊3日湘南キャンプ</div>
				<hr>
				<div class="left">

					<div class="name">企画者：大河要祐</div>
					<div class="period">期間：2015年8月1日～2015年8月3日</div>
					<div class="genre">
						<span class="col">ジャンル：</span><a>キャンプ</a>,<a>海</a>,<a>キャンプ</a>,<a>海</a>,<a>キャンプ</a>,<a>海</a>,<a>キャンプ</a>,<a>海</a>,<a>キャンプ</a>,<a>海</a>,<a>キャンプ</a>,<a>海</a>,<a>キャンプ</a>,<a>海</a>,<a>キャンプ</a>,<a>海</a>,<a>キャンプ</a>,<a>海</a>,<a>キャンプ</a>,<a>海</a>,
					</div>
				</div>
				<div class="right">
					<div class="evaluation">
						<span class="like"><img src="../images/icon/like.png">いいね:100</span>/<span class="dislike"><img src="../images/icon/dislike.png">ダメだね：100</span>
					</div>
				</div>
			</div>
			<%
			    ArrayList<PlanBean> planList = (ArrayList<PlanBean>) request
					    .getAttribute("planList");
			    HashMap<Integer, PlanPointsBean> pointMap = (HashMap<Integer, PlanPointsBean>) request
					    .getAttribute("pointMap");
			    HashMap<Integer, List<GenreBean>> genreMap = (HashMap<Integer, List<GenreBean>>) request
					    .getAttribute("genreMap");
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");
			    for (PlanBean plan : planList) {
					out.println("<div class=\"data\">");
					out.println("<div class=\"title\"><a href=\"javascript:Post("
						+ plan.getPlanId() + ");\">" + plan.getPlanTitle()
						+ "</a></div><hr>");
					out.println("<div class=\"left\">");
					out.println("<div class=\"name\">企画者：" + plan.getPlannerName()
						+ "</div>");

					out.println("<div class=\"period\">期間："
						+ sdf.format(plan.getStartDate()) + "～"
						+ sdf.format(plan.getEndDate()) + "</div>");

					out.println("<div class=\"genre\">");
					out.println("<span class=\"col\">ジャンル：</span>");

					if (genreMap.containsKey(plan.getPlanId())) {
					    Iterator iterator = genreMap.get(plan.getPlanId())
						    .iterator();
					    while (iterator.hasNext()) {
						GenreBean genre = (GenreBean) iterator.next();
						out.print("<a href=\"/HomeSystem/fc/PlanList?search=検索&genre="
							+ genre.getGenreId()
							+ "\">"
							+ genre.getGenreName() + "</a>");
						if (iterator.hasNext()) {
						    out.print(",");
						}
					    }

					} else {
					    out.println("なし");
					}

					out.println("</div>");//genre

					out.println("</div>");//left
					out.println("<div class=\"right\">");

					out.println("<div class=\"evaluation\">");

					int like = 0;
					int dislike = 0;

					if (pointMap.containsKey(plan.getPlanId())) {
					    like = pointMap.get(plan.getPlanId()).getGoodCount();
					    dislike = pointMap.get(plan.getPlanId()).getBadCount();
					}

					out.println("<span class=\"like\"><img src=\"../images/icon/like.png\">いいね:"
						+ like + "</span>");
					out.println("<span class=\"dislike\"><img src=\"../images/icon/dislike.png\">ダメだね："
						+ dislike + "</span>");

					out.println("</div>");//evaluation

					out.println("</div>");//right

					out.println("</div>");//data
			    }
			%>
		</div>
		<form name="form1" method="post" action="/HomeSystem/fc/PlanDetail">
			<input type="hidden" name="detail">
			<input type="hidden" name="planId">
		</form>
		<hr>
	</div>
</body>
</html>