
var prefix = "/edu/eduDormitory"
$(function() {
	debugger
	getClass();
});
function getClass() {
	var html = "";
	$.ajax({
		url : '/edu/classAlbum/classChoose?state=1',
		success : function(data) {
			//加载数据
			for (var i = 0; i < data.length; i++) {
				html += '<option value="' + data[i].resultValue + '">' + data[i].resultKey + '</option>'
			}
			//$("#classId").html('');
			$("#classId").append(html);
		}
	});
}

