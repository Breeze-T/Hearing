$().ready(function() {
	validateRule();
	debugger
	var id=$('#id').val();
	$.ajax({
		type : "GET",
		url : "/edu/eduClass/getSubjectByClassId?classId="+id,
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.length> 0) {
				console.info(data);
				var str='';
				var option='请选择学科';
				for(var i=0;i<data.length;i++){
					str+='<div class="form-group"><label class="col-sm-3 control-label">'+data[i].userName+'：</label><div class="col-sm-8">';
					str+='<input id="'+i+'" name="'+data[i].userId+'" class="hidden data" value="'+toString(data[i].sId)+'" >';
					str+='<input id="'+i+'Name" name="groupLeaderName" value="'+toString(data[i].sName)+'" class="form-control" type="text" onclick="openUser(\''+i+'\',\''+i+'Name\')" readonly="readonly" placeholder="请选择教官所带学科">';
					/*if(data[i].sName!=null&&data[i].sName!=''){
						option=data[i].sName;
					}
					str+='<select id="'+data[i].positionId+'" name="'+data[i].userId+'" value="'+data[i].subject_id+'" class="hi data"><option>'+option+'</option></select>';*/
						str+='</div></div>';
				}
				$('#beizhu').before(str);
			} else {
				parent.layer.alert("请先设置教官")
				$('#beizhu').hide();
			}

		}
	});

	/*$.ajax({
		type : "GET",
		url : "/common/dict/list/edu_subject",
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.length> 0) {
				console.info(data);
				var str='';
				for(var i=0;i<data.length;i++){
					str+='<option value="'+data[i].id+'">'+data[i].name+'</option>';
				}
				$(".data").append(str);
			} else {
				parent.layer.alert(data.msg)
			}

		}
	});*/
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	var a=$(".data");
	console.info(a);
	debugger
	var positionString=[];
	var positionData={};
	$.each($(".data"),function(index,item){
		var positionId=item.id;
		var userId=item.name;
		var subjectId=item.value;
		if(item.value!=''){
			positionData={positionId:positionId,positionUser:userId,subjectId:subjectId};
			positionString.push(positionData)
		}

	});
	positionString=JSON.stringify(positionString)
	console.info(positionString);
	$.ajax({
		cache : true,
		type : "POST",
		url : "/edu/eduClass/updateSubject",
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
			name : {
				required : true
			}
		},
		messages : {
			name : {
				required : icon + "请输入名字"
			}
		}
	})
}
var openUser = function(id,name){
	layer.open({
		type:2,
		title:"选择学科",
		area : [ '300px', '450px' ],
		content:"/edu/eduSubject/treeView?id="+id+"&name="+name
	})
}

function loadUser(userIds,userNames,id,name){
	debugger
	$("#"+id).val(userIds);
	$("#"+name).val(userNames);
}
function toString(str){
	return str==null?"":str;
}
