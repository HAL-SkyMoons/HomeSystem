<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeeBatchBean"%>
<%@page import="jp.ac.hal.skymoons.beans.EmployeePageBean"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="jp.ac.hal.skymoons.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ユーザ情報編集</title>
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="../css/employeePlofileEditPage.css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/style.css">
<script src="../js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="../js/uploadThumbnail.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/autosize/autosize.js"></script>
<script type="text/javascript">
	$(function() {
		$('.comment').bind('keyup', function() {
			var thisValueLength = $(this).val().length;
			$('.count').html(thisValueLength);
		});
	});
</script>
<%
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("S");
	String milliSec = sdf.format(date);
	ArrayList<EmployeePageBean> headerEmployeeDatas = (ArrayList<EmployeePageBean>)request.getAttribute("headerEmployeeData");
	EmployeePageBean headerEmployeeData = headerEmployeeDatas.get(0);
	int headerEmployeeLevel = headerEmployeeData.getLevel();
	if(headerEmployeeLevel>12){
		headerEmployeeLevel=12;
	}
%>
</head>
<body>
<!--******************************** こっからへっだー ***********************************-->
	<div id="allwrap">
		<div id="headerline">line</div>
		<header>
			<div id="logo">
				<a href="/HomeSystem/fc/Index"><img src="/HomeSystem/images/logo.png" /></a>
			</div>

			<div id="headerright">
				<c:forEach var="headerEmployeeData" items="${headerEmployeeData}">
					<div id="headerstr">
						<label id="headername">${headerEmployeeData.employeeName}さん</label><br> <label id="headerlevel">レベル${headerEmployeeData.level}</label>
					</div>
					<div id="headerimage">
						<img src="/HomeSystem/images/employees/${headerEmployeeData.employeeId}.jpg?<%=milliSec%>">
						<div id="headerflame">
							<img src="/HomeSystem/images/flame/<%=headerEmployeeLevel%>.png?<%=milliSec%>">
						</div>
					</div>
				</c:forEach>
				<div id="headerbutton">
					<div id="mypage">
						<form action="/HomeSystem/fc/EmployeeMyPage">
							<input type="submit" class="btn btn-2 btn-2c submit" value="マイページ">
						</form>
					</div>
					<div id="logout">
						<form action="/HomeSystem/fc/logout/user">
							<input type="submit" class="btn btn-2 btn-2c submit" value="ログアウト">
						</form>
					</div>
				</div>

			</div>

			<ul id="headermenu">
				<li class="menu1"><a href="/HomeSystem/fc/EmployeeList">社員一覧</a></li>
				<li class="menu2"><a href="#">企画</a>
					<ul>
						<li><a href="/HomeSystem/fc/PlanList">企画一覧</a></li>
						<li><a href="/HomeSystem/fc/PlanRegister">企画登録</a></li>
						<li><a href="/HomeSystem/fc/PlanCalendar">企画カレンダー</a></li>
					</ul></li>
				<li class="menu3"><a href="#">ホメホメコンテンツ</a>
					<ul>
						<li><a href="/HomeSystem/fc/contents/list">コンテンツ一覧</a></li>
						<li><a href="/HomeSystem/fc/contents/regist">コンテンツ登録</a></li>
					</ul></li>
				<li class="menu4"><a href="/HomeSystem/fc/ranking/topnum">ランキング</a></li>
			</ul>

		</header>
		<div id="allcontents">
			<!--*********************************ここまでへっだー ***********************************-->
	<div class="contents">
		<form action="/HomeSystem/fc/EmployeeProfileEdit" method="POST" enctype="multipart/form-data">
			<table class="editTable">
				<tr>
					<td>【 画像ファイル 】<br> <input type="file" name="employeeImage" id="file" onchange="$('#fake_input_file').text($(this)[0].files[0].name)" /> <input type="button" class="btn btn-2 btn-2c uploadBtn" value="ファイル選択" onclick="$('#file').click();" /></td>
					<td class="help">推奨設定<br>サイズ：150*150以上<br>縦横比：1:1</td>
				</tr>
				<tr>
					<td><br>【 一言コメント 】<br> <textarea name="comment" class="comment" maxlength="500">${comment}</textarea> <%-- 					<input type="text" name="comment" value="${comment}" /></td> --%>
					<td class="help">最大500文字<br>※改行込<br>現在文字数：<span class="count">${comment.length()}</count></td>
				</tr>
				<tr>
					<td><br>【 パスワード変更 】<br> <input type="password" name="password" class="comment" maxlength="20"> <%-- 					<input type="text" name="comment" value="${comment}" /></td> --%>
					<td class="help">最大20文字<br></td>
				</tr>
			</table>
			<input type="submit" name="button" value="更新" class="btn btn-2 btn-2c submitBtn">
		</form>
	</div>
			<!--*********************************ここからふったー ***********************************-->
		</div>
		<footer>
			<div id="footertop">
				<a href="#top">▲ページTOPへ</a>
			</div>

			<div id="footermenu">
				<div id="footermenuin">
					<div id="footermenu1">
						<h4>社員</h4>
						<p>
							<a href="/HomeSystem/fc/EmployeeList">社員一覧</a>
						</p>
						<p>
							<a href="/HomeSystem/fc/EmployeeMyPage">マイページ</a>
						</p>
					</div>
					<div id="footermenu2">
						<h4>企画</h4>
						<p>
							<a href="/HomeSystem/fc/PlanList">企画一覧</a>
						</p>
						<p>
							<a href="/HomeSystem/fc/PlanRegister">企画登校</a>
						</p>
						<p>
							<a href="/HomeSystem/fc/PlanCalendar">企画カレンダー</a>
						</p>
					</div>
					<div id="footermenu3">
						<h4>ホメホメコンテンツ</h4>
						<p>
							<a href="/HomeSystem/fc/contents/list">コンテンツ一覧</a>
						</p>
						<p>
							<a href="/HomeSystem/fc/contents/regist">コンテンツ登録</a>
						</p>
					</div>
					<div id="footermenu4">
						<h4>ランキング</h4>
						<p>
							<a href="/HomeSystem/fc/ranking/topnum">ランキング</a>
						</p>
					</div>
				</div>
			</div>
			<div id="footerline">Copyright &copy; 2015-2016 SkyMoons All Rights Reserved.</div>
		</footer>
	</div>
	<!--*********************************ここまでふったー ***********************************-->
</body>
<script>
	autosize(document.querySelectorAll('textarea'));
</script>
</html>