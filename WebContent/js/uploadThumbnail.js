	$(function() {
		$('input[type=file]').before('<div class="uploadThumbnail" style="display:none;"></div>');

		// アップロードするファイルを選択
		$('input[type=file]').change(function() {
			var file = $(this).prop('files')[0];

			// 画像以外は処理を停止
			if (!file.type.match('image.*')) {
				$('.uploadThumbnail').html('');
				return;
			}

			// 画像表示
			var reader = new FileReader();
			reader.onload = function() {
				var img_src = $('<img>').attr('src', reader.result);
				$('.uploadThumbnail').html(img_src);
				$('.uploadThumbnail').css("display","block");
			}
			reader.readAsDataURL(file);
		});
	});