<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css">
<title>Insert title here</title>
<script type="text/javascript">
	function ImageUp(employeeId) {
		window.open("/HomeSystem/fc/Home?toUser="+employeeId,"window2","width=1000,height=500");
	}
</script>
</head>
<body>
	<form action="/HomeSystem/fc/EmployeeList" method="POST">

	<select name="departmentId">
			<option value="0">部署で検索する</option>
		<c:forEach var="department" items="${departments}">
			<option value="${department.departmentId}">${department.departmentName}</option>
		</c:forEach>
	</select>
	<input type="submit" name="submit" value="検索">
	<br/>
	<div class="genreList">
	<h4>ジャンルで検索する</h4>
		<div class="BigGenre 1">
			<h5>BigGenre 1</h5>
			<c:forEach var="genre" items="${genres}" varStatus="status">
				<c:if test="${genre.bigGenreId ==1}">
				<input type="checkbox" name="genre" value="${genre.genreId}">${genre.genreName}
				<br/>
				</c:if>
			</c:forEach>
		</div>
		<div class="BigGenre 2">
			<h5>BigGenre 2</h5>
			<c:forEach var="genre" items="${genres}" varStatus="status">
				<c:if test="${genre.bigGenreId ==2}">
				<input type="checkbox" name="genre" value="${genre.genreId}">${genre.genreName}
				<br/>
				</c:if>
			</c:forEach>
		</div>
	</div>
	<input type="submit" name="submit" value="検索">
	</form>
	<table>
	<tr>
	<c:forEach var="employee" items="${employees}" varStatus="status">
		<td>
		<a href="./EmployeePage?employeeId=${employee.employeeId}"><img src="../images/employees/${employee.employeeId}.jpg"></a><br>
		${employee.departmentName}
		<a href="./EmployeePage?employeeId=${employee.employeeId}">${employee.employeeName}</a>
		<br>
		${sessionId} 、${employee.employeeId} <br/>
		<c:if test="${sessionId != employee.employeeId}">
			<a href="javascript:ImageUp('${employee.employeeId}');" ><input type="button" value="この人を褒める"></a>
		<br/>
		</c:if>
		<c:forEach var="employeeGenre" items="${employee.employeeGenre}">
			${employeeGenre}
		</c:forEach>
		</td>
		<c:if test="${status.count%5 ==0}">
			</tr>
		</c:if>
		<c:if test="${status.count%5 ==0}">
			<tr>
		</c:if>
	</c:forEach>
	</table>
</body>
</html>