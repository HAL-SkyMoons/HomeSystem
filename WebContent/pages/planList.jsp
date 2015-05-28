<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanListBean"%>
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
<%
	ArrayList<PlanListBean> planList = (ArrayList<PlanListBean>)request.getAttribute("planList");
	for(PlanListBean plan : planList){
		out.println(plan.getPlanTitle());
	}
%>
</body>
</html>