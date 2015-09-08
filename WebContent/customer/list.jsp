<%@page import="jp.ac.hal.skymoons.beans.customer.CustomerUsersBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	@SuppressWarnings("unchecked") List<CustomerUsersBean> list = (List<CustomerUsersBean>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>顧客リスト</title>
	</head>
	<body>
		<h1>顧客リスト</h1>
		<p><a href="/HomeSystem/fc/customer/add">登録</a></p>
		<form action="/HomeSystem/fc/customer/list" method="POST">
		キーワード:<input type="text" name="keyword" maxlength="100"
<%
	if(request.getParameter("keyword") != null) {
		if(request.getParameter("keyword").equals("") == false) {
			out.print(" value='" + request.getParameter("keyword") + "'");
		}
	}
%>
			>
			<input type="submit" name="submmit" value="検索">
		</form>
		
		
		
<%
	// 顧客リストの生成
	if(list != null) {
		if(list.size() != 0) {
			out.println("<table border='1'>" + "<tr><th>企業名</th>"
					+ "<th>姓</th>" + "<th>名</th>" + "<th>失効</th>"
					+ "<th>操作</th>");
			for (CustomerUsersBean record : list) {
				out.println("<tr>" + "<td>"
						+ record.getCustomer_company() + "</td>"
						+ "<td>" + record.getLast_name() + "</td>"
						+ "<td>" + record.getFirst_name() + "</td>");
				if (record.getLapse_flag() == 0) {
					out.println("<td>No</td>");
				} else {
					out.println("<td>Yes</td>");
				}
				out.println("<td><form action='/HomeSystem/fc/customer/detail' method='POST'><input type='hidden' name='id' value='"
						+ record.getUser_id()
						+ "'><input type='submit' name='submit' value='詳細'></form></td>"
						+ "</tr>");
			}
			out.println("</table>");
		} else {
			out.println("<p>データが見つかりません。");
		}
	} else {
		out.println("<p>データが見つかりません。");
	}
%>
	</body>
</html>
