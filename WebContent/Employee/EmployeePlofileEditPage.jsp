<%@page import="jp.ac.hal.skymoons.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ユーザ情報編集</title>
<link rel="stylesheet" type="text/css" href="../css/reset.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="../css/employeePlofileEditPage.css">
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
</head>
<body>
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
</body>
<script>
	autosize(document.querySelectorAll('textarea'));
</script>
</html>