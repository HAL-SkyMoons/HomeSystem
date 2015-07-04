<%@page import="java.text.SimpleDateFormat"%>
<%@page import="jp.ac.hal.skymoons.beans.UserBean"%>
<%@page import="jp.ac.hal.skymoons.util.Utility"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企画登録確認</title>
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="../css/PlanConfirmation.css">
</head>
<%
    Utility util = new Utility();
    PlanBean plan = (PlanBean) request.getAttribute("plan");
    UserBean user = (UserBean) request.getAttribute("user");

    String planner = plan.getPlanner();
    String planTitle = plan.getPlanTitle();
    String planComment = plan.getPlanComment();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分");

    String startDate = sdf.format(plan.getStartDate());
    String endDate = sdf.format(plan.getEndDate());
%>
<body>
	<div id="wrapper">
		<form action="/HomeSystem/fc/PlanConfirmation" method="post">
			<div id="plan">
				<div id="planHeader">
					<div id="planDate">
						<jsp:useBean id="date" class="java.util.Date" />
						企画日：
						<fmt:formatDate value="${date}" pattern="yyyy年MM月dd日" />
					</div>
				</div>
				<div id="title">
					<div id="titleValue">${plan.planTitle}</div>
				</div>
				<div id="planDetail">
					<div id="startEnd">
						<div>企画実施日</div>
						<fmt:formatDate value="${plan.startDate}" pattern="yyyy年MM月dd日 KK時mm分" />
						&nbsp;&sim;&nbsp;
						<fmt:formatDate value="${plan.endDate}" pattern="yyyy年MM月dd日 KK時mm分" />
					</div>
					<div id="planner">
						企画者
						<div id="img">
							<img src="../images/employees/${user.userId}.jpg">
						</div>
						${user.lastName}${user.firstName}
						<input type="hidden" name="planner" value="${user.userId}">
					</div>
				</div>
				<div id="planComment">
					<div id="planCommentValue">${Utility.nlToBR(plan.planComment)}</div>
				</div>
				<br>

			</div>
			<div id="genre">
				登録ジャンル<br>
				<c:forEach var="genre" items="${genreIds}" varStatus="status">
					${genreNames.get(status.index)}
					<input type="hidden" name="id" value="${genre}">
				</c:forEach>

			</div>
			<input type="hidden" name="planner" value="${plan.planner}">
			<input type="hidden" name="planTitle" value="${plan.planTitle}">
			<input type="hidden" name="planComment" value="${plan.planComment}">
			<fmt:formatDate value="${plan.startDate}" pattern="yyyy-MM-dd KK:mm" var="startDate" />
			<input type="hidden" name="startDate" value="${startDate}">
			<fmt:formatDate value="${plan.endDate}" pattern="yyyy-MM-dd KK:mm" var="endDate" />
			<input type="hidden" name="endDate" value="${endDate}">
			<div class="btnZone">
				<input type="submit" name="submit" class="btn btn-2 btn-2c submit" id="submit" value="登録">
			</div>
		</form>
	</div>
	<form action="/HomeSystem/fc/PlanConfirmation" method="post">
		<%
		    out.println("<input type=\"hidden\" name=\"planner\" value=\""
				    + planner + "\">");
		    out.println("<input type=\"hidden\" name=\"planTitle\" value=\""
				    + planTitle + "\">");
		    out.println("<input type=\"hidden\" name=\"planComment\" value=\""
				    + planComment + "\">");
		    out.println("<input type=\"hidden\" name=\"startDate\" value=\""
				    + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(plan
					    .getStartDate()) + "\">");
		    out.println("<input type=\"hidden\" name=\"endDate\" value=\""
				    + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(plan
					    .getEndDate()) + "\">");
		%>

		<h3>登録ジャンル</h3>
		<%
		    // 		    ArrayList<Integer> genreIds = (ArrayList<Integer>) request
		    // 				    .getAttribute("genreIds");
		    // 		    ArrayList<String> genreNames = (ArrayList<String>) request
		    // 				    .getAttribute("genreNames");

		    // 		    for (int i = 0; i < genreIds.size(); i++) {
		    // 				out.println(genreNames.get(i));
		    // 				out.println("<input type=\"hidden\" name=\"id\" value=\""
		    // 					+ genreIds.get(i) + "\">");
		    // 		    }
		%>

		<input type="submit" name="submit" value="登録">
	</form>
</body>
</html>