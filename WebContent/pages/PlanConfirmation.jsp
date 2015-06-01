<%@page import="jp.ac.hal.skymoons.util.Utility"%>
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
<title>企画登録</title>
</head>
<%
	Utility util = new Utility();
	PlanBean plan = (PlanBean) request.getAttribute("plan");

	String planner = plan.getPlanner();
	String planTitle = plan.getPlanTitle();
	String planComment = plan.getPlanComment();
%>
<body>
	<form action="/HomeSystem/fc/PlanConfirmation" method="post">
		<table>
			<tr>
				<th>企画者：</th>
				<td>
					<%
						out.print(planner);
					%>
				</td>
			</tr>
			<tr>
				<th>企画名：</th>
				<td>
					<%
						out.print(planTitle);
					%>
				</td>
			</tr>
			<tr>
				<th>企画内容：</th>
				<td>
					<%
						out.print(util.nlToBR(planComment));
					%>
				</td>
			</tr>
		</table>
		<%
			out.println("<input type=\"hidden\" name=\"planner\" value=\""
					+ planner + "\">");
			out.println("<input type=\"hidden\" name=\"planTitle\" value=\""
					+ planTitle + "\">");
			out.println("<input type=\"hidden\" name=\"planComment\" value=\""
					+ planComment + "\">");
		%>

		<h3>登録ジャンル</h3>
		<%
			ArrayList<Integer> genreIds = (ArrayList<Integer>) request
					.getAttribute("genreIds");
			ArrayList<String> genreNames = (ArrayList<String>) request
					.getAttribute("genreNames");

			for (int i = 0; i < genreIds.size(); i++) {
				out.println(genreNames.get(i));
				out.println("<input type=\"hidden\" name=\"id\" value=\"" + genreIds.get(i) + "\">");
			}
		%>

		<input type="submit" name="submit" value="登録">
	</form>
</body>
</html>