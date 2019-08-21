$().ready(function() {
	//validateRule();
	var id=$('#id').val();
	$.ajax({
		type : "POST",
		url : "/edu/eduTeacher/getTeacherAssess?userId="+id,
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			debugger
			if (data.code != 0) {
				layer.msg(data.msg);
				return false;
			}
			data=data.data;
			if (data.length> 0) {
				var totalAvg=0;
				var length=data.length;
				var str='';
				for(var i=0;i<data.length;i++){
					totalAvg+=data[i].avg;
					str+='<div class="form-group"><label class="col-sm-3 control-label">'+data[i].className+'平均分：</label><div class="col-sm-8">';
					str+='<input readonly="readonly"  value="'+(data[i].avg).toFixed(2)+'"  class="form-control" type="text"></div>' +
						'<a onclick="lookDetail(\''+data[i].classId+'\',\''+id+'\')">查看测评明细</a></div>';
				}
				str+='<div class="form-group"><label class="col-sm-3 control-label">总平均分：</label><div class="col-sm-8">';
				str+='<input readonly="readonly"  value="'+(totalAvg/length).toFixed(2)+'"  class="form-control" type="text"></div></div>';
				$('#signupForm').append(str);
			}else {
				$('#signupForm').append('<span style="font-size: 17px;color: tomato;">暂无该老师的测评分数!</span>');
				//parent.layer.alert("暂无该老师的测评分数");
			}

		}
	});
});
function lookDetail(classId,teacherUserId) {
	debugger
	layer.open({
		type : 2,
		title : '评价明细',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '80%', '90%' ],
		content :'/edu/eduAssessReport?classId='+classId+'&teacherUserId='+teacherUserId// iframe的url
	});
}