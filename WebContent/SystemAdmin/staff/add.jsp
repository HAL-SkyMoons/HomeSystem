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
	<title>社員ユーザ登録</title>
</head>

<body>
	<h1>社員ユーザ登録</h1>
	
	<p><a href="/HomeSystem/fc/SystemAdmin/menu">メニュー</a><p>
	<p><a href="/HomeSystem/fc/SystemAdmin/staff/list">社員ユーザ一覧</a></p>
	<p><a href="/HomeSystem/fc/SystemAdmin/logout">ログアウト</a></p>

	<form action="/HomeSystem/fc/SystemAdmin/staff/add" method="post">
		<p>姓　必須　(全角７文字以内　例：山田)</p>
		<p><input type="text" name="lastName" maxlength="7" <% if(values != null) { out.print("value='" + values[0] + "'"); }%>></p>
		<p>名　必須　(全角７文字以内　例：太郎)</p>
		<p><input type="text" name="firstName" maxlength="7" <% if(values != null) { out.print("value='" + values[1] + "'"); }%>></p>
		<p>姓(よみがな)　必須　(ひらがな２１文字以内　例：やまだ)</p>
		<p><input type="text" name="lastNameKana" maxlength="21" <% if(values != null) { out.print("value='" + values[2] + "'"); }%>></p>
		<p>名のよみがな</p><p class="msg2">必須</p></td><td class="col2"><input class="input1" type="text" name="firstNameKana"  maxlength="21" <% if(values != null) { out.print("value='" + values[3] + "'"); }%>><p>(ひらがな２１文字以内　例：たろう)</td>
		<p>所属部署</p><p class="msg2">必須</p></td><td class="col2"><select name="department"><option value="-1">-所属部署を選択-</option><option value="1">販売部</option><option value="3">開発部</option><option value="4">研究部</option><option value="5">事業部</option><option value="2">人事部</option></select></td>
		<p>ユーザID</p><p class="msg2">必須</p></td><td class="col2"><input class="input1" type="text" name="id"  maxlength="20" <% if(values != null) { out.print("value='" + values[4] + "'"); }%>><p>(半角英数字８文字以上２０字以内)</td>
		<p>パスワード</p><p class="msg2">必須</p></td><td class="col2"><input class="input1" type="text" name="password"  maxlength="20" <% if(values != null) { out.print("value='" + values[5] + "'"); }%>><p>(半角英数字８文字以上２０文字以内)</td>
		<p>使用可否</p><p class="msg2">必須</p></td><td class="col2">有効:<input type="radio" name="useFlg" value="0">　　無効:<input type="radio" name="useFlg" value="1"></td>
		<p><input id="submitBtn" type="submit" name="submitBtn" value="登録"><p id="message"><%= message %></p></td>
	</form>

</body>
</html>