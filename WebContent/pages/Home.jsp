<%@page import="jp.ac.hal.skymoons.beans.GenreBean"%>
<%@page import="jp.ac.hal.skymoons.beans.PlanBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企画登録</title>
</head>
<body>
	<form action="/HomeSystem/fc/Home" method="post">
		<table>
			<tr>
				<th>From：</th>
				<%
					out.println("<td>木村拓也</td>");
					out.println("<input type=\"hidden\" name=\"fromId\" value=\"E0001\">");
				%>


			</tr>
			<tr>
				<th>To：</th>
				<td></td>
			</tr>
			<tr>
				<th>バッチ：</th>
				<td>
				<input type="radio" name="batch" value="1">リーダーバッチ
				<input type="radio" name="batch" value="2">プレゼンバッチ
				<input type="radio" name="batch" value="3">スピードバッチ
				<input type="radio" name="batch" value="4">熱血バッチ
				<input type="radio" name="batch" value="5">発想バッチ
				<input type="radio" name="batch" value="6">グッジョブバッチ


				</td>
			</tr>
			<tr>
				<th>ポイント：</th>
				<td></td>
			</tr>
			<tr>
				<th>コメント：</th>
				<td></td>
			</tr>
			<tr>
				<th>コンテンツID：</th>
				<td></td>
			</tr>

		</table>
	</form>
</body>
</html>