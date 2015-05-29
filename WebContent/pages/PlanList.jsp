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
		ArrayList<PlanBean> planList = (ArrayList<PlanBean>)request.getAttribute("planList");
		for(PlanBean plan : planList){
			out.println("<tr>");
				out.println("<td>"+plan.getPlanTitle()+"</td>");
				out.println("<td>"+plan.getPlanner()+"</td>");
				out.println("<td>"+plan.getPlanDatetime()+"</td>");
				out.println("<td><form action=\"/HomeSystem/fc/PlanDetail\" method=\"post\"><input type=\"submit\" name=\"detail\" value=\"詳細\"><input type=\"hidden\" name=\"planId\" value=\""+ plan.getPlanId() +"\"></form></td>");
			out.println("</tr>");
		}
	%>
</table>
</body>
</html>