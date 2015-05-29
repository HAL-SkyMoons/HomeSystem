<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企画登録</title>
</head>
<body>
	<form action="/HomeSystem/fc/PlanRegister" method="post">
		<table>
			<tr>
				<th>企画者：</th>
				<td>大河　要祐<input type="hidden" name="planner" value="E0001"></td>
			</tr>
			<tr>
				<th>企画名：</th>
				<td><input type="text" name="planTitle"></td>
			</tr>
			<tr>
				<th>企画内容：</th>
				<td><textarea col="30" rows="30" name="planComment"></textarea></td>
			</tr>
		</table>
		<input type="submit" name="submit" value="登録">
	</form>
</body>
</html>