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
<title>企画一覧</title>
</head>
<body>
<table>
	<%
	PlanBean plan = (PlanBean)request.getAttribute("planDetail");
	%>
	<tr>
		<th>企画ID</th>
		<td><% out.println(plan.getPlanId()); %></td>
	</tr>
	<tr>
		<th>企画名</th>
		<td><% out.println(plan.getPlanTitle()); %></td>
	</tr>
	<tr>
		<th>企画者</th>
		<td><% out.println(plan.getPlanner()); %></td>
	</tr>
	<tr>
		<th>企画日</th>
		<td><% out.println(plan.getPlanDatetime()); %></td>
	</tr>
	<tr>
		<th>企画内容</th>
		<td><% out.println(plan.getPlanComment()); %></td>
	</tr>
</table>
</body>
</html>