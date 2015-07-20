jQuery(document).ready(function($) {
	var i = 0;
	$('#btn_add').click(function() {
		i++;
		$("#form_0").clone(true).attr('id',i).appendTo("#forms");
		$("#"+i).find('#btn_del').attr('value',i).css('visibility','visible');
		$("#"+i).find('#count_0').attr('id',"count_"+i).attr('name',"count_"+i);
		$("#"+i).find('#batchId_0').attr('id',"batchId_"+i).attr('name',"batchId_"+i);
	});

	$('#btn_del').click(function() {
		var value = $(this).val();
		$("#"+value).remove();
	});
});