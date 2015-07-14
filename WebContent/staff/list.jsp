<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>社員リスト</title>
	</head>
	
	<body>
		<h1>社員リスト</h1>
		<p>登録</p>
		<form action="/HomeSystem/fc/staff/list" method="GET">
			<input type="text" name="keyword">
			<input type="submit" name="submit" value="検索">
		</form>
		<p>1-2-3-4-5</p>
		<table>
			<tr>
				<th>姓</th>
				<th>名</th>
			</tr>
		</table>
		<p>1-2-3-4-5</p>
	</body>
</html>