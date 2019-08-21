function exportProfile(){
	var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
	parent.layer.close(index);
	/*var studentId= $("#studentId").val();
	window.open("/edu/student/exportProfile?studentId="+studentId);*/
	$("#searchForm").attr("action","/edu/student/exportProfile");
	$("#searchForm").submit();
}
$().ready(function() {
	//validateRule();
	var id=$('#id').val();
	$.ajax({
		type : "POST",
		url : "/edu/checkDetail/getTotalScoreList?studentId="+$("#studentId").val(),
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
					totalAvg+=data[i].totalScore;
					str+='<div class="form-group"><label class="col-sm-2 control-label">第'+data[i].num+'次考试成绩：</label><div class="col-sm-10">';
					str+='<em>'+(data[i].totalScore).toFixed(2)+'</em>' +
						'<a hidden onclick="lookDetail(\''+data[i].id+'\',\''+data[i].num+'\')">查看测评明细</a></div></div>';
				}
				str+='<div class="form-group"><label class="col-sm-2 control-label">总平均分：</label><div class="col-sm-10">';
				str+='<em>'+(totalAvg/length).toFixed(2)+'</em></div></div>';
				$('#signupForm').after(str);
			}else {
				//parent.layer.alert("暂无该老师的测评分数");
			}

		}
	});
});