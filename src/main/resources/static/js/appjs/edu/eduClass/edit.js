$().ready(function() {
	validateRule();
	debugger
	var id=$('#id').val();
	$.ajax({
		type : "GET",
		url : "/common/dict/list/edu_position",
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.length> 0) {
				console.info(data);
				var str='';
				for(var i=0;i<data.length;i++){
					str+='<div class="form-group"><label class="col-sm-3 control-label">'+data[i].name+'：</label><div class="col-sm-8">';
					str+='<input id="'+data[i].id+'" name="'+data[i].name+'" title="'+data[i].value+'" class="hidden data">';
					str+='<input id="'+data[i].id+'Name" name="groupLeaderName" class="form-control" type="text" onclick="openUser(\''+data[i].id+'\',\''+data[i].id+'Name\')" readonly="readonly" placeholder="请选择">';
					str+='</div></div>';
				}
				$('#beizhu').before(str);
			} else {
				parent.layer.alert(data.msg)
			}

		}
	});
	$.ajax({
		type : "GET",
		url : "/edu/eduClass/getPositionByClassId?classId="+id,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.length> 0) {
				console.info(data);
				var str='';
				var id='';
				var userId='';
				var userName='';
				for(var i=0;i<data.length;i++){
					id=data[i].id;
					userId=data[i].userId;
					userName=data[i].userName;
					$('#'+id).val(userId);
					$('#'+id+'Name').val(userName);
					/*str+='<div class="form-group"><label class="col-sm-3 control-label">'+data[i].name+'：</label><div class="col-sm-8">';
					str+='<input id="'+data[i].id+'" name="'+data[i].name+'" title="'+data[i].value+'"  class="hidden data" value="'+toString(data[i].userId)+'" >';
					str+='<input id="'+data[i].id+'Name" name="groupLeaderName" value="'+toString(data[i].userName)+'" class="form-control" type="text" onclick="openUser(\''+data[i].id+'\',\''+data[i].id+'Name\')" readonly="readonly" placeholder="请选择">';
					str+='</div></div>';*/
				}
				//$('#beizhu').before(str);
			} else {
				//parent.layer.alert(data.msg)
			}

		}
	});
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	debugger
	var positionString=[];
	var positionData={};
	var bt=1;
	$.each($("input[class='hidden data']"),function(index,item){
		var positionId=item.id;
		var positionCode=item.title;
		var positionName=item.name;
		if(positionCode=='bzr'){
			if(item.value==''){
				parent.layer.msg("班主任不能为空");
				bt=0;
				return false;
			}else if(item.value.indexOf(",") != -1){
				parent.layer.msg("班主任只能选择一个");
				bt=2;
				return false;
			}
		}
		if(item.value!=''){
			positionData={positionId:positionId,positionCode:positionCode,positionUser:item.value,positionName:positionName};
			positionString.push(positionData)
		}

	});
	if(bt!=1){
		return false;
	}
	positionString=JSON.stringify(positionString)
	console.info(positionString);
	$.ajax({
		cache : true,
		type : "POST",
		url : "/edu/eduClass/update",
		data : $('#signupForm').serialize()+'&positionString='+positionString,// 你的formid
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
			className : {
				required : true
			},
			classAdviserName : {
				required : true,
				isMobile : true
			},
		},
		messages : {
			className : {
				required : icon + "请输入班级名称"
			},
			classAdviserName : {
				required : icon + "请选择班主任",
				isMobile :  "班主任只能选择一个"
			}
		}
	})
	jQuery.validator.addMethod("isMobile", function (value, element) {
		var phone = value.split(',') ;
		var aa=phone.length<=2;
		return (aa);
	}, "班主任只能选择一个");
}
var openUser = function(id,name){
	layer.open({
		type:2,
		title:"选择人员",
		area : [ '300px', '450px' ],
		content:"/sys/user/treeView?id="+id+"&name="+name
	})
}

function loadUser(userIds,userNames,id,name){
	$("#"+id).val(userIds);
	$("#"+name).val(userNames);
}
function toString(str){
	return str==null?"":str;
}