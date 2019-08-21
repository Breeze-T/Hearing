$(function (){
	$("#checkType").val($("#cType").val());
	var isLeafHidden = $("#isLeafHidden").val();
	var checks = document.getElementsByName("isLeaf");
	for( var i=0;i<checks.length;i++){
		if($(checks[i]).val() == isLeafHidden){
			checks[i].checked=true;
		}
	} 
	if(isLeafHidden == '1'){
		$("#scoreDiv").show();
	}else{
	    $("#scoreDiv").hide(); 
	}
})


$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	var id = $("#id").val();
	var checkName = $.trim($("#checkName").val());
	var isLeaf = $("#isLeafHidden").val();
	var score = $.trim($("#score").val());
    var regu = /^([1-9][0-9]*){1,3}$/;
    if(isLeaf == '1'){
    	if(score == "" || score == null){
    		layer.msg("请填写分数！"); return false;	
    	}else{
    		if(!regu.test(score)||score>100){
    			layer.msg("分值为1-100的整数！"); return false;	
    		}
    	}
    }
	$.ajax({
		cache : true,
		type : "POST",
		url : "/edu/check/update",
		data : {'id':id,'checkName':checkName,'isLeaf':isLeaf,'score':score},// 你的formid
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
		rules : {
			checkName : {
				required : true
			}
		},
		messages : {
			checkName : {
				required : icon + "请输入项目名称！"
			}
		}
	})
}