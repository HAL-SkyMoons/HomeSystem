<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE>
<html lang="ja">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="/HomeSystem/SystemAdmin/css/base.css">
	<link rel="stylesheet" type="text/css" href="/HomeSystem/SystemAdmin/css/wrapper.css">
	<link rel="stylesheet" type="text/css" href="/HomeSystem/SystemAdmin/css/menu.css">
	<title>メニュー</title>
</head>

<body>
	<div id="wrapper">
		<header>
			<a href="/HomeSystem/fc/SystemAdmin/menu"><div class="menuLeftBox">メニュー</div></a>
			
			<a href="/HomeSystem/fc/SystemAdmin/logout"><div class="menuRightBox">ログアウト</div></a>
		</header>
	
		<div id="contentBox">
			<div id="box1A">
				<h2>ユーザ管理</h2>
				<a href="/HomeSystem/fc/SystemAdmin/staff/list"><div class="btn1">社員</div></a>
				<a href="/HomeSystem/fc/SystemAdmin/staff/list"><div class="btn2">顧客</div></a>
				<a href="/HomeSystem/fc/SystemAdmin/staff/list"><div class="btn2">管理者</div></a>
			</div>
			<div id="box1B">
				<h2>コンテンツ管理</h2>
				<div class="btn1">ホメコンテンツ</div>
				<div class="btn2">企画</div>
			</div>
			<div id="box1C">
				<h2>マスター管理</h2>
				<a href="/HomeSystem/fc/genre"><div class="btn1">ジャンル</div></a>
				<a href="/HomeSystem/fc/batchList"><div class="btn2">バッジ</div></a>
				<a href="/HomeSystem/fc/trophy"><div class="btn2">トロフィー</div></a>
				<a href="/HomeSystem/fc/companyCapacity"><div class="btn2">社内資格</div></a>
			</div>
		</div>
	</div>
</body>
</html>