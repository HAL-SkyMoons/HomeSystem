<%@page import="jp.ac.hal.skymoons.beans.CustomerTestBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	@SuppressWarnings("unchecked") List<CustomerTestBean> list = (List<CustomerTestBean>)request.getAttribute("list");
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
		<form>
			企業名<input type="text">
			姓<input type="text">
			名<input type="text">
			<input type="submit" name="submmit" value="検索">
		</form>
<%
	if(list != null) {
		out.println("<table border='1'>"
				+	"<tr><th>企業名</th>"
				+	"<th>姓</th>"
				+	"<th>名</th>"
				+	"<th>操作</th>");
		for(int i = 0; i < list.size(); i++) {
			out.println("<tr>"
					+	"<td>" + list.get(i).getCustomer_company() + "</td>"
					+	"<td>" + list.get(i).getLast_name() + "</td>"
					+	"<td>" + list.get(i).getFirst_name() + "</td>"
					+	"<td><form action='/HomeSystem/fc/customer/detail' method='POST'><input type='hidden' name='id' value='" + list.get(i).getCustomer_id() + "'><input type='submit' name='submit' value='詳細'></form></td>"
					+	"</tr>");
		}
		out.println("</table>");
	} else {
		out.println("<p>データが見つかりません。");
	}
%>
	</body>
</html>