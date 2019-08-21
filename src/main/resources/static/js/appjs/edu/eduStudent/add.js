document.write("<script language=javascript src='/js/appjs/edu/eduStudent/card.js'></script>");
$(function() {
	var html = "";
	$.ajax({
		url : '/edu/classAlbum/classChoose',
		success : function(data) {
			//加载数据
			for (var i = 0; i < data.length; i++) {
				html += '<option value="' + data[i].resultValue + '">' + data[i].resultKey + '</option>'
			}
			$("#classId").append(html);
		}
	});
});
//手机号码验证
jQuery.validator.addMethod("isPhone", function(value, element) {
          var length = value.length;
          var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
          return this.optional(element) || (length == 11 && mobile.test(value));
         }, "请填写正确的手机号码");
//身份证验证
jQuery.validator.addMethod("isCardNum", function(value, element) {
	return this.optional(element) || idCardNoUtil.checkIdCardNo(value);
	}, "请正确输入您的身份证号码");

$().ready(function() {
	validateRule();
});


$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	upload();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/edu/student/save",
		data : $('#addForm').serialize(), // 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("网络超时");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg);
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#addForm").validate({
		onkeyup:false,
		rules : {
			studentName : {
				required : true
			},
			sex : {
				required : true
			},
			cardNum : {
			/*	required : true,*/
				isCardNum : true
			},
			phoneNum : {
				required : true,
				isPhone : true
			},
			email :{
				//required : true,
				email:true	
			},
			unit : {
				required : true
			},
			duties : {
				required : true
			},
			alarmNum : {
				required : true,
				remote : {
					url : "/sys/user/exit", // 后台处理程序
					type : "post", // 数据发送方式
					dataType : "json", // 接受数据格式
					data : { // 要传递的数据
						username : function() {
							return $("#alarmNum").val();
						}
					}
				}
			},
			classId :{
				required : true
			}
		},
		messages : {
			name : {
				required : icon + "请填写姓名"
			},
			sex : {
				required : icon + "请选择性别"
			},
			cardNum : {
				/*required : icon + "请填写身份证号",*/
				isCardNum : icon + "请填写正确格式的身份证号"
			},
			phoneNum : {
				required : icon + "请填写手机号",
				isPhone : icon + "请填写正确格式的手机号码"
			},
			email : {
				//required : icon + "请填写邮箱",
				email : icon + "请填写正确格式的邮箱账号"
			},
			unit : {
				required : icon + "请填写所在单位"
			},
			duties : {
				required : icon + "请填写职务"
			},
			alarmNum : {
				required : icon + "请填写警号",
				remote : icon + "警号已经存在"
			},
			classId :{
				required : icon + "请选择班级"
			}
		}
	})
}