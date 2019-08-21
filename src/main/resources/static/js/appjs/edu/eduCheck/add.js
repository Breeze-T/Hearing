$(function (){
	if($('#pId').val() != 0){
		$('#checkType').val($('#cType').val());
		$('#checkType').attr("disabled", "disabled");
	}
})

$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	var pId = $("#pId").val();
	var checkName = $.trim($("#checkName").val());
	var checkType = $.trim($("#checkType").val());
	var checkArr = document.getElementsByName("isLeaf");
    var isLeaf;
    var score = $("#score").val();
    var regu = /^([1-9][0-9]*){1,3}$/;
    for(k in checkArr){
        if(checkArr[k].checked)
        	isLeaf=(checkArr[k].value);
    }
    if(isLeaf==''||isLeaf==null){
    	layer.msg("请选择是否为叶子节点！"); return false;
    }
    if(isLeaf == '1'){
    	if(score ==''||score==null){
    		layer.msg("请填写分值！"); return false;
    	}else{
    		if(!regu.test(score)||score>100){
    			layer.msg("分值为1-100的整数！"); return false;	
    		}
    	}
    }
	$.ajax({
		cache : true,
		type : "POST",
		url : "/edu/check/save",
		data : {'pId' : pId,
				'checkName' : checkName,
				'checkType' : checkType,
				'isLeaf' : isLeaf,
				'score' : score},
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
	$("#addForm").validate({
		rules : {
				checkName : {
				required : true
			}
		},
		messages : {
				checkName : {
				required : icon + "请填写项目名称！"
			}
		}
	})
}


//控制是否为叶子节点的checkbox
function selectCheckOne(obj){
    var checks = document.getElementsByName("isLeaf");
    if(obj.checked){
        for( var i=0;i<checks.length;i++){
        checks[i].checked=false;
        }
        obj.checked=true;
        if($(obj).val() == '1'){
        	$("#scoreDiv").show();
        }else{
        	$("#scoreDiv").hide();
        }
    }else{
        for( var i=0;i<checks.length;i++){
        checks[i].checked=false;
        }
        $("#scoreDiv").hide();
    }
}