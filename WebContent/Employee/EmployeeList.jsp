<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/homesystem/fc/EmployeeList" method="POST">
		<select name="departmentId">
			<option value="0">ジャンルで検索する</option>
			<option value="1">部署1</option>
			<option value="2">部署2</option>
			<option value="3">部署3</option>
			<option value="4">部署4</option>
		</select>
		<input type="checkbox" name="genre" value="1">ジャンル1
		<input type="checkbox" name="genre" value="2">ジャンル2<br/>
		<input type="checkbox" name="genre" value="3">ジャンル3
		<input type="checkbox" name="genre" value="4">ジャンル4<br/>
		<input type="checkbox" name="genre" value="5">ジャンル5
		<input type="checkbox" name="genre" value="6">ジャンル6<br/>
		<input type="checkbox" name="genre" value="7">ジャンル7
		<input type="checkbox" name="genre" value="8">ジャンル8<br/>
		<input type="checkbox" name="genre" value="9">ジャンル9
		<input type="checkbox" name="genre" value="10">ジャンル10<br/>
		<input type="submit" name="submit" value="検索">
	</form>



</body>
</html>