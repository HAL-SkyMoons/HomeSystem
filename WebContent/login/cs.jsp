<%@page import="jp.ac.hal.skymoons.security.session.SessionController"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="content-style-type" content="text/css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/reset.css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/style.css">
<link rel="stylesheet" type="text/css" href="/HomeSystem/css/logincs.css">
<title>顧客・社員用ログインページ</title>
</head>
<body>
	<div id="allwrap">
		<div id="headerline">line</div>
		<header>
			<div id="logo">
				<a href="/HomeSystem/login/cs.jsp"><img src="/HomeSystem/images/logo.png"></a>
			</div>

		</header>
		<div id="allcontents">
			<div id="boxout">
				<div id="boxcomment">
					<label>ホメ</label>た数だけ<br> <label> 笑顔</label>がある
				</div>
				<div id="box">
					<h1>顧客・社員用ログイン</h1>
					<hr color="orange">

					<form action="/HomeSystem/fc/login/user" method="POST">
						<p>
							ログインID：<input type="text" name="id" size="30" id="text">
						</p>
						<p>
							パスワード：<input type="password" name="pass" size="30" id="text">
						</p>
						<h5>
							<%
								if (request.getAttribute("message") != null) {
									out.println(request.getAttribute("message"));
								}
							%>
						</h5>
						<p>
							<input type="submit" name="submit" value="ログイン" class="btn btn-2 btn-2c">
						</p>
					</form>
				</div>
			</div>
		</div>
		<footer>
			<div id="footerline">Copyright &copy; 2015-2016 SkyMoons All Rights Reserved.</div>
		</footer>
	</div>

</body>
</html>