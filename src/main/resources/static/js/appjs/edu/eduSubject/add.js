$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/edu/eduSubject/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	jQuery.validator.addMethod("positiveinteger", function(value, element) {
		debugger
		if($("#examFlag").val()!=1){
			return true;
		}
		var aint=value;
		return aint>0&&aint<1;
	}, "");
	$("#signupForm").validate({
		rules : {
			subjectName : {
				required : true
			},
			examFlag : {
				required : true
			},
			rate:{ positiveinteger:true}
		},
		messages : {
			subjectName : {
				required : icon + "请输入科目名称"
			},
			examFlag : {
				required : icon + "请选择是否需要考试"
			},
			rate:{ positiveinteger:icon + "请输入大于0并且小于1的小数"}
		}
	})
}
function selectChange() {
	if($("#examFlag").val()==1){
		$("#rateDiv").show();
	}else {
		$("#rateDiv").hide();
		$("#rate").val("0");
	}
}