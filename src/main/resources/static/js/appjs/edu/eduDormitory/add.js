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
		url : "/edu/eduDormitory/save",
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
		var aint=parseInt(value);
		return aint>0&&aint<7&& (aint+"")==value;
	}, "");
	$("#signupForm").validate({
		rules : {
			roomName : {
				required : true
			},
			roomType : {
				required : true
			},

			liveAmount:{ positiveinteger:true}
		},
		messages : {
			roomName : {
				required : icon + "请输入宿舍名称"
			},
			roomType : {
				required : icon + "请选择宿舍类型"
			},
		liveAmount:{ positiveinteger:icon + "请输入小于7的正整数"}
		}
	})
}