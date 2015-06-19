<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/HomeSystem/fc/EmployeeList" method="POST">

	<select name="departmentId">
			<option value="0">ジャンルで検索する</option>
		<c:forEach var="department" items="${departments}">
			<option value="${department.departmentId}">${department.departmentName}</option>
		</c:forEach>
	</select>
	<input type="submit" name="submit" value="検索">
	<br/>
		<c:forEach var="genre" items="${genres}">
			<input type="checkbox" name="genre" value="${genre.genreId}">${genre.genreName}
			<br/>
		</c:forEach>
	<br/>
	<input type="submit" name="submit" value="検索">
	</form>
	<c:forEach var="employee" items="${employees}">
		<a href="./EmployeePage?employeeId=${employee.employeeId}"><img src="../img/employees/${employee.employeeId}.jpg"></a><br>
		${employee.departmentName}
		<a href="./EmployeePage?employeeId=${employee.employeeId}">${employee.employeeName}</a>
		<br>
		<c:forEach var="employeeGenre" items="${employee.employeeGenre}">
			${employeeGenre}
		</c:forEach>
		<br/>
	</c:forEach>
</body>
</html>