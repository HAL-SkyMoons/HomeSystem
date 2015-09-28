<%@page import="jp.ac.hal.skymoons.systemadmin.beans.StaffBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	request.setCharacterEncoding("utf-8");
	@SuppressWarnings("unchecked") List<StaffBean> staffList = (List<StaffBean>)request.getAttribute("staffList");
%>
<!DOCTYPE>
<html lang="ja">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>社員ユーザ一覧</title>
</head>

<body>
	<h1>社員ユーザ一覧</h1>
	
	<p><a href="/HomeSystem/fc/SystemAdmin/menu">メニュー</a><p>
	<p><a href="/HomeSystem/fc/SystemAdmin/staff/add">登録</a></p>
	<p><a href="/HomeSystem/fc/SystemAdmin/logout">ログアウト</a></p>
	
	<form action="list" method="post">
		<input type="text" name="keyword" maxlength="50" placeholder="キーワード検索"<% if(request.getAttribute("where") != null){ out.print(" value='" + request.getAttribute("where") + "'"); } %>><input type="submit" name="searchBtn" value="検索">
	</form>

<%
	if(staffList.size() != 0) {
		out.println("<table>");
		out.println("<caption>社員ユーザ一覧</caption>");
		out.print("<tr>");
		out.print("<th>姓</th><th>名</th><th>所属部署</th><th>詳細</th>");
		out.println("</tr>");

		for(int i = 0; i < staffList.size(); i++) {
			out.print("<tr>");
			out.print("<td>" + staffList.get(i).getLast_name() + "（" + staffList.get(i).getLast_name_kana() + "）" + "</td>");
			out.print("<td>" + staffList.get(i).getFirst_name() + "（" + staffList.get(i).getFirst_name_kana() + "）" + "</td>");
			out.print("<td>" + staffList.get(i).getDepartment_name() + "</td>");
			out.print("<td><form action='detail' method='post'><input class='detailBtn' type='submit' name='detailBtn' value='詳細'><input type='hidden' name='id' value='" + staffList.get(i).getUser_id() + "'></form></td>");
			out.println("</tr>");
		}
		out.println("</table>");
	} else {
		out.println("<p>データが見つかりません。</p>");
	}
%>

</body>
</html>