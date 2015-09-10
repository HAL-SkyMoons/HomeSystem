<%@page import="jp.ac.hal.skymoons.systemadmin.beans.StaffBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	request.setCharacterEncoding("utf-8");
	@SuppressWarnings("unchecked") List<StaffBean> staffList = (List<StaffBean>)request.getAttribute("staffList");
	System.out.println("HIT件数:" + staffList.size());
%>
<!DOCTYPE>
<html lang="ja">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="/HomeSystem/SystemAdmin/css/base.css">
	<link rel="stylesheet" type="text/css" href="/HomeSystem/SystemAdmin/css/wrapper.css">
	<link rel="stylesheet" type="text/css" href="/HomeSystem/SystemAdmin/css/staff/list.css">
	<title>社員ユーザ一覧</title>
</head>

<body>
	<div id="wrapper">
		<header>
			<a href="/HomeSystem/fc/SystemAdmin/menu"><div class="menuLeftBox">メニュー</div></a>
			<a href="/HomeSystem/fc/SystemAdmin/staff/add"><div class="menuLeftBox">登録</div></a>
			<div id="menuSearchBox">
				<form action="list" method="post">
					<input id="searchInput1" type="text" name="keyword" maxlength="50" placeholder="キーワード検索" <% if(request.getAttribute("where") != null){ out.print("value='" + request.getAttribute("where") + "'"); } %> ><input id="searchInput2" type="submit" name="searchBtn" value="検索">
				</form>
			</div>
			
			<a href="/HomeSystem/fc/SystemAdmin/logout"><div class="menuRightBox">ログアウト</div></a>
		</header>
		
		<div id="contentBox">
			<div id="listBox">
					<%
						if(staffList.size() != 0) {
							out.println("<table>");
							out.println("<caption>社員ユーザ一覧</caption>");
							out.println("<tr>");
							out.println("<th>姓</th><th>名</th><th>所属部署</th><th>使用可否</th><th>詳細</th>");
							out.println("</tr>");
							int flg = 0;
							for(int i = 0; i < staffList.size(); i++) {
								if(flg == 0) {
									out.print("<tr class='record1'>");
								} else {
									out.print("<tr class='record2'>");
								}
								out.print("<td>" + staffList.get(i).getLast_name() + "（" + staffList.get(i).getLast_name_kana() + "）" + "</td>");
								out.print("<td>" + staffList.get(i).getFirst_name() + "（" + staffList.get(i).getFirst_name_kana() + "）" + "</td>");
								out.print("<td>" + staffList.get(i).getDepartment_name() + "</td>");
								if(staffList.get(i).getLapse_flag() == 0) {
									out.print("<td>有効</td>");
								} else {
									out.print("<td>失効</td>");
								}
								out.print("<td><form action='detail' method='post'><input class='detailBtn' type='submit' name='detailBtn' value='詳細'><input type='hidden' name='id' value='" + staffList.get(i).getUser_id() + "'></form></td>");
								out.println("</tr>");
								if(flg == 0) {
									flg = 1;
								} else {
									flg = 0;
								}
							}
							out.println("</table>");
						} else {
							out.println("<div>データが見つかりません。</div>");
						}
					%>
			</div>
		</div>
	</div>
</body>
</html>