<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/employeeList.css">
<link rel="stylesheet" type="text/css"
	href="../js/colorbox/colorbox.css">
<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../js/colorbox/jquery.colorbox.js"></script>
<title>Insert title here</title>
<script type="text/javascript">
// 	function ImageUp(employeeId) {
// 		window.open("/HomeSystem/fc/Home?toUser="+employeeId,"window2","width=1000,height=500");
// 	}
	window.onload = function(){
		document.getElementById('bigGenreList1').style.display = 'none';
		document.getElementById('bigGenreList2').style.display = 'none';
	}
	$(function() {
		$(".iframe").colorbox({
			iframe : true,
			width : "500px",
			height : "90%",
			opacity : 0.7
		});
	});
	//ジャンルタブ切り替えメソッド
	function tabChange(tabName){
		var state='';
		if(document.getElementById(tabName).style.display == 'none'){
			state = 'block';
		}else{
			state = 'none';
		}
		document.getElementById(tabName).style.display = state;
	}
</script>
</head>
<body>
	<div class="contents">
		<div class="searchPart">
			<form action="/HomeSystem/fc/EmployeeList" method="POST">
				<div class="departmentSearch">
					<select name="departmentId" class="departmentSelect">
						<option value="0">部署で検索する</option>
						<c:forEach var="department" items="${departments}">
							<option value="${department.departmentId}">${department.departmentName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="genreList">
					<h4 class="genreTitle">ジャンルで検索する</h4>
					<div class="bigGenre1">
						<b><h5 class="bigGenreTitle"><a href="javascript:tabChange('bigGenreList1')">BigGenre 1</a></h5></b>
						<div id="bigGenreList1">
							<c:forEach var="genre" items="${genres}" varStatus="status">
								<c:if test="${genre.bigGenreId ==1}">
									<input type="checkbox" name="genre" value="${genre.genreId}">${genre.genreName}
									<br />
								</c:if>
							</c:forEach>
						</div>
					</div>
					<div class="bigGenre2">
						<b><h5 class="bigGenreTitle"><a href="javascript:tabChange('bigGenreList2')">BigGenre 2</a></h5></b>
						<div id="bigGenreList2">
							<c:forEach var="genre" items="${genres}" varStatus="status">
								<c:if test="${genre.bigGenreId ==2}">
									<input type="checkbox" name="genre" value="${genre.genreId}">${genre.genreName}
									<br />
								</c:if>
							</c:forEach>
						</div>
					</div>
					<input type="submit" name="submit" value="検索" class="genreButton">
				</div>
			</form>
		</div>
		<div class="employeeListPart">
			<h1 class="employeeListTitle">社員一覧</h1>
			<table class="employeeList">
				<tr>
					<c:forEach var="employee" items="${employees}" varStatus="status">
						<td class="employeeColumn">
							<div class="employeeImage">
								<a href="./EmployeePage?employeeId=${employee.employeeId}"><img
									src="../images/employees/${employee.employeeId}.jpg"></a>
							</div> <a href="./EmployeePage?employeeId=${employee.employeeId}">${employee.employeeName}</a>
							<br> ${employee.departmentName} <br> <c:if
								test="${sessionId != employee.employeeId}">
								<a class="iframe"
									href="/HomeSystem/fc/Home?toUser=${employee.employeeId}"><input
									type="button" value="この人を褒める"></a>
								<br />
							</c:if>
							<ul>
								<c:forEach var="employeeGenre" items="${employee.employeeGenre}">
									<li>${employeeGenre}</li>
								</c:forEach>
							</ul>
						</td>
						<c:if test="${status.count%5 ==0}">
				</tr>
				<tr>
					</c:if>
					</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>