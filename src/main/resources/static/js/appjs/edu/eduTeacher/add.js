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
		url : "/edu/eduTeacher/save",
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
	$("#signupForm").validate({
		onkeyup:false,
		rules : {
			policeNumber : {
				required : true,
				minlength : 2,
				remote : {
					url : "/sys/user/exit", // 后台处理程序
					type : "post", // 数据发送方式
					dataType : "json", // 接受数据格式
					data : { // 要传递的数据
						username : function() {
							return $("#policeNumber").val();
						}
					}
				}
			},
			name : {
				required : true
			},
			deptName : {
				required : true
			},
		/*	teacherType : {
				required : true
			},*/
			phone : {
				required : true
			},
		},
		messages : {
			policeNumber : {
				required : icon + "请输入您的用户名",
				minlength : icon + "警号必须两个字符以上",
				remote : icon + "用户名已经存在"
			},
			name : {
				required : icon + "请输入姓名"
			},
			deptName : {
				required : icon + "请选择部门"
			},
			/*teacherType : {
				required :  icon + "请选择教官类型"
			},*/
			phone : {
				required :  icon + "请输入手机号"
			},
		}
	})
}
var openDept = function(){
	layer.open({
		type:2,
		title:"选择部门",
		area : [ '300px', '450px' ],
		content:"/system/sysDept/treeView"
	})
}
function loadDept( deptId,deptName){
	$("#deptId").val(deptId);
	$("#deptName").val(deptName);
}