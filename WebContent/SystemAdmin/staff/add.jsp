<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String[] values = (String[])request.getAttribute("values");
	String message = "全ての必須項目を入力してください。";
	if(request.getAttribute("message") != null) {
		message = (String)request.getAttribute("message");
	}
%>
<!DOCTYPE>
<html lang="ja">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="/HomeSystem/SystemAdmin/css/base.css">
	<link rel="stylesheet" type="text/css" href="/HomeSystem/SystemAdmin/css/wrapper.css">
	<link rel="stylesheet" type="text/css" href="/HomeSystem/SystemAdmin/css/staff/add.css">
	<title>社員ユーザ登録</title>
</head>

<body>
	<div id="wrapper">
		<header>
			<a href="/HomeSystem/fc/SystemAdmin/menu"><div class="menuLeftBox">メニュー</div></a>
			<a href="/HomeSystem/fc/SystemAdmin/staff/list"><div class="menuLeftBox">社員ユーザ一覧</div></a>
			
			<a href="/HomeSystem/fc/SystemAdmin/logout"><div class="menuRightBox">ログアウト</div></a>
		</header>
		
		<div id="contentBox">
			<form action="/HomeSystem/fc/SystemAdmin/staff/add" method="post">
				<table id="formTable">
					<tr>
						<td class="col1"><p class="msg1">姓</p><p class="msg2">必須</p></td><td class="col2"><input class="input1" type="text" name="lastName" maxlength="7" <% if(values != null) { out.print("value='" + values[0] + "'"); }%>><p>(全角７文字以内　例：山田)</p></td>
					</tr>
					<tr>
						<td class="col1"><p class="msg1">名</p><p class="msg2">必須</p></td><td class="col2"><input class="input1" type="text" name="firstName" maxlength="7" <% if(values != null) { out.print("value='" + values[1] + "'"); }%>><p>(全角７文字以内　例：太郎)</td>
					</tr>
					<tr>
						<td class="col1"><p class="msg1">姓のよみがな</p><p class="msg2">必須</p></td><td class="col2"><input class="input1" type="text" name="lastNameKana" maxlength="21" <% if(values != null) { out.print("value='" + values[2] + "'"); }%>><p>(ひらがな２１文字以内　例：やまだ)</td>
					</tr>
					<tr>
						<td class="col1"><p class="msg1">名のよみがな</p><p class="msg2">必須</p></td><td class="col2"><input class="input1" type="text" name="firstNameKana"  maxlength="21" <% if(values != null) { out.print("value='" + values[3] + "'"); }%>><p>(ひらがな２１文字以内　例：たろう)</td>
					</tr>
					<tr>
						<td class="col1"><p class="msg1">所属部署</p><p class="msg2">必須</p></td><td class="col2"><select name="department"><option value="-1">-所属部署を選択-</option><option value="1">販売部</option><option value="3">開発部</option><option value="4">研究部</option><option value="5">事業部</option><option value="2">人事部</option></select></td>
					</tr>
					<tr>
						<td class="col1"><p class="msg1">ユーザID</p><p class="msg2">必須</p></td><td class="col2"><input class="input1" type="text" name="id"  maxlength="20" <% if(values != null) { out.print("value='" + values[4] + "'"); }%>><p>(半角英数字８文字以上２０字以内)</td>
					</tr>
					<tr>
						<td class="col1"><p class="msg1">パスワード</p><p class="msg2">必須</p></td><td class="col2"><input class="input1" type="text" name="password"  maxlength="20" <% if(values != null) { out.print("value='" + values[5] + "'"); }%>><p>(半角英数字８文字以上２０文字以内)</td>
					</tr>
					<tr>
						<td class="col1"><p class="msg1">使用可否</p><p class="msg2">必須</p></td><td class="col2">有効:<input type="radio" name="useFlg" value="0">　　無効:<input type="radio" name="useFlg" value="1"></td>
					</tr>
					<tr>
						<td id="record1" colspan="2"><input id="submitBtn" type="submit" name="submitBtn" value="登録"><p id="message"><%= message %></p></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>