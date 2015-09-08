jQuery(document).ready(function($) {
	var i = 0;
	$('#btn_add').click(function() {
		i++;
		$("#form_0").clone(true).attr('id',"form_"+i).appendTo("#forms");
		$("#form_"+i).find('.btn_del').attr('value',i).css('visibility','visible');
		$("#form_"+i).find('#count_0').attr('id',"count_"+i).attr('name',"count_"+i).attr('value',1);
		$("#form_"+i).find('#batchId_0').attr('id',"batchId_"+i).attr('name',"batchId_"+i);
	});

	$('.btn_del').click(function() {
		var value = $(this).val();
		$("#form_"+value).remove();
	});
});