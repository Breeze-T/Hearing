//手机号码验证
jQuery.validator.addMethod("isPhone", function(value, element) {
          var length = value.length;
          var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
          return this.optional(element) || (length == 11 && mobile.test(value));
         }, "请填写正确的手机号码");

$().ready(function() {
	validateRule();
});


$(function() {
	var sex = $("#sexHidden").val();
	var classId = $("#classIdHidden").val();
	var html = "";
	$.ajax({
		url : '/edu/classAlbum/classChoose',
		success : function(data) {
			//加载数据
			for (var i = 0; i < data.length; i++) {
				if(data[i].resultValue==classId){
					html += '<option selected = "selected" value="' + data[i].resultValue + '">' + data[i].resultKey + '</option>'
				}else{
					html += '<option value="' + data[i].resultValue + '">' + data[i].resultKey + '</option>'
				}
			}
			$("#classId").append(html);
		}
	});
	$("#sex").val(sex);

	var photo=$("#photo").val();
	if(photo!=null&&photo!=''){
		var arr=photo.split(',');
		var str = "<div class='pics'>";
		for(var i=0;i<arr.length;i++){
			str += '<em id=em'+i+'>'
				+ '<img style="width: 45px;" alt="" src="'+arr[i]+'">'
				+ '<img style="width: 15px;" alt="" src="/img/eduClassAlbum/close1.png" onclick="removeUrl('+"'"+arr[i]+"',"+i+');"></em>';
		}
		str+='</div>';
		$("#file_div").find("em").remove();
		///$("#file_div").find("br").remove();
		$("#file_div").append(str);
	}
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	upload();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/edu/student/update",
		data : $('#editForm').serialize(),// 你的formid
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
	$("#editForm").validate({
		rules : {
			studentName : {
				required : true
			},
			sex : {
				required : true
			},
			/*cardNum : {
				required : true
			},*/
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
				required : true
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
			/*cardNum : {
				required : icon + "请填写身份证号"
			},*/
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
				required : icon + "请填写警号"
			},
			classId :{
				required : icon + "请选择班级"
			}
		}
	})
}