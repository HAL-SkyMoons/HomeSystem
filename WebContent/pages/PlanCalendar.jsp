<%@page import="java.util.ArrayList"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企画カレンダー</title>
<%
	ArrayList<PlanBean> planList = (ArrayList<PlanBean>)request.getAttribute("planList");
%>
<!-- CSS -->
<link href="../js/fullcalendar/css/fullcalendar.min.css" rel="stylesheet" type="text/css">
<!-- JavaScript -->
<script type="text/javascript" src="../js/fullcalendar/js/moment.min.js"></script>
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../js/fullcalendar/js/jquery-ui.custom.min.js"></script>
<script type="text/javascript" src="../js/fullcalendar/js/fullcalendar.min.js"></script>
<script type="text/javascript" src="../js/fullcalendar/js/ja.js"></script>
<script>

	$(document).ready(function() {

		$('#calendar').fullCalendar({
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month'
			},
			editable: false,
			eventLimit: true, // allow "more" link when too many events
			events: [
			         <%
			         	for(PlanBean plan : planList){
						out.println("{");
			         	out.println("title: '" + plan.getPlanTitle() + "',");
			         	out.println("url: 'http://localhost:8080/HomeSystem/fc/PlanDetail?planId="+ plan.getPlanId() +"&detail=true',");
			         	out.println("start: '"+ plan.getImplementationDate() +"'");

			         	out.println("},");
			         	}
			         %>
			]
		});

	});

</script>
<style>

	body {
		margin: 40px 10px;
		padding: 0;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		font-size: 14px;
	}

	#calendar {
		max-width: 900px;
		margin: 0 auto;
	}

</style>

</head>
<body>
<div id="calendar"></div>
</body>
</html>