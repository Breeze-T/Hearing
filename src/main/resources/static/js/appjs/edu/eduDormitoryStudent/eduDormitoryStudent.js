var classId=$("#classId").val();//班级
var oldId='';
var oldName='';
var oldNumber='';
var oldRoomId='';
var oldRoomType='';
$(function() {
	getData("/edu/eduDormitoryStudent/getDormitoryStudentList",{classId:classId});
	$(".confirm").click(function(){
		debugger
		//var val=$('input[name="info"]:checked').next().text();
		var selectContent = $('#exampleTable').bootstrapTable('getSelections')[0];
		var val=selectContent.studentName;//选中的学生名称
		var id=selectContent.id;//选中的学生id
		id="a"+id;
		var newNumber=$("#"+id).attr("name");
		var newRoomId=$("#"+id).parent().parent().attr("id");
		if(newRoomId==null){
			parent.layer.alert("找不到该学员的宿舍信息");
			return false;
		}
		//老的成新的
		$("#"+oldId).text(val);
		$("#"+id).text(oldName);
		$("#"+id).addClass('choiceNew')
		$("#"+oldId).attr("id",id);
		$('.choiceNew').attr("id",oldId);
		//被选中的的也替换掉
		/*$('.choice em').text(val);
		 $('.choice1 i').text(val);
		 $('.choice11 i').text(val);
		 $('.choice111 i').text(val);*/
		$(".layui-layer").css("display","none");
		$(".layui-layer-shade").css("display","none");
		/*$(".replace").removeClass("choice");
		 $(".replace1").removeClass("choice1");
		 $(".list-title").removeClass("choice11");*/
		layer.closeAll();
		var newId=id.substring(1,id.length);
		oldId=oldId.substring(1,oldId.length);
		newRoomId=newRoomId.substring(1,newRoomId.length);
		oldRoomId=oldRoomId.substring(1,oldRoomId.length);
		var data ={newId:newId,newRoomId:newRoomId,newNumber:newNumber,oldId:oldId,oldName:oldName,oldNumber:oldNumber,oldRoomId:oldRoomId};
			save(data,1);
	});
	/*$(".battalion").on("click",function(){
		oldId=$(this).children('i').attr("id");
		oldName=$(this).children('i').text();
		layer.open({
			title:"请选择警员和大队长："+oldName+"进行调换",
			type:1,
			area: ['850px', '600px'],
			fix: false, //不固定
			maxmin: false,
			content:$('#popIn')
		});
		$(".choice").removeClass('choice');//先把这个样式的清空
		$(".choiceNew").removeClass('choiceNew');//先把这个样式的清空
		$(this).children('i').addClass("choice");
		//大队长
	});*/
	$(".cancel").click(function(){
		layer.closeAll();
	})
	load();
})
function save(data,flag) {
	//保存课表
	$.ajax({
		cache : true,
		type : "POST",
		url : "/edu/eduDormitoryStudent/changeStudentDormitory?flag="+flag,
		data : data,// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			debugger
			if (data.code == 0) {
				flag= 0;
				parent.layer.msg("操作成功", {icon: 1,time: 500});

			} else {
				flag= 1;
				parent.layer.alert(data.msg)
			}

		}
	});
	return flag;
}
function load() {
	$('#exampleTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url :"/edu/student/list", // 服务器数据的加载地址
				//	showRefresh : true,
				//	showToggle : true,
				//	showColumns : true,
				iconSize : 'outline',
				toolbar : '#toolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
				pagination : true, // 设置为true会在底部显示分页条
				// queryParamsType : "limit",
				// //设置为limit则会发送符合RESTFull格式的参数
				singleSelect : true, // 设置为true将禁止多选
				// contentType : "application/x-www-form-urlencoded",
				// //发送到服务器的数据编码类型
				pageSize : 10, // 如果设置了分页，每页数据条数
				pageNumber : 1, // 如果设置了分布，首页页码
				//search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
				queryParams : function(params) {
					return {
						//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset,
						classId:classId,
						studentName:$('#searchName').val(),
						sex:oldRoomType,
					};
				},
				columns : [
					{
						checkbox : true
					},
					{
						field : 'id',
						title : '序号',
						formatter : function(value, row, index) {
							return index+1;
						}
					},
					{
						field : 'studentName',
						title : '姓名'
					},
					{
						field : 'sex',
						title : '性别',
						width : '100px',
						formatter : function(value, row, index) {
							if(row.sex=='M'){
								return '男';
							}else{
								return '女';
							}
						}
					},
					{
						field : 'phoneNum',
						title : '手机'
					},

					{
						field : 'alarmNum',
						title : '警号'
					},
					{
						field : 'map.className',
						title : '班级'
					} ]
			});
}

function reLoad() {
	$('#exampleTable').bootstrapTable('selectPage', 1);
}
function setTeamGroup(){
	layer.confirm('确定要重置班级学员宿舍信息吗,之前的分配的宿舍将被清空？', {
		btn : [ '确定', '取消' ]
	}, function() {
		debugger
		$("#data").html('');
		getData("/edu/eduDormitoryStudent/setDormitoryStudentList",{classId:classId},1);
	})

}

function getData(url,data,flag){
	$.ajax({
		type : "POST",
		url : url,
		data :data,// 你的formid
		dataType:'json',
		async : false,
		error : function(request) {
			parent.layer.alert("操作失败，请核实下该班级学员数量是否满足分组数");
			$("#big").html('<span>操作失败，请核实下该班级学员数量是否满足分组数:</span>');
		},
		success : function(data) {
			if (data.code != 0) {
				layer.alert(data.msg);
				return false;
			}
			debugger
			var teamList=data.data;
			if(teamList.length==0){
				$("#big").html('<span>请先对班级学员进行分组:</span>');
				layer.alert("请先对班级学员进行分组");
				return false;
			}
			if(flag==1){
				layer.alert("操作成功");
			}

			var str="";
			var roomType="";
			var roomTypeName="";
			var typeClor="";
			var roomMap="";
			var studentList="";
			$.each(teamList,function(index,value){
				roomMap=value.roomMap;
				roomType=roomMap.roomType;
				if('M'==roomType){
					roomTypeName="男生";
					typeClor="";
				}else if ('F'==roomType){
					roomTypeName="女生";
					typeClor="typeClor";
				}
				str+='<div class="list"><h5 class="list-title '+typeClor+'"><span>'+roomTypeName+'宿舍名:</span><i>'+roomMap.roomName+'</i></h5> <div class="list-info"><ul>';
					str+='<li><div class="members"><ul id="b'+roomMap.id+'" name="'+roomMap.roomType+'">';
				studentList=value.studentList;
					$.each(studentList,function(index,value){
						str+='<li class="replace"><i id="a'+value.studentId+'" name="'+value.number+'">'+value.studentName+'</i><img src="/img/add.png"></li>';
					});
					str+=' </ul></div></li>';

				str+='</ul></div></div>';
			});
			$("#data").append(str);
		}
	});

	$(".list").on("click",".replace",function(){
		debugger
		oldId=$(this).children('i').attr("id");
		oldName=$(this).children('i').text();
		oldNumber=$(this).children('i').attr("name");
		oldRoomId=$(this).parent().attr("id");
		oldRoomType=$(this).parent().attr("name");
		layer.open({
			title:"请选择学员和："+oldName+"进行调换宿舍",
			type:1,
			area: ['65%', '70%'],
			fix: false, //不固定
			maxmin: false,
			content:$('#popIn')
		});
		$(".choice").removeClass('choice');//先把这个样式的清空
		$(".choiceNew").removeClass('choiceNew');//先把这个样式的清空
		$(this).children('i').addClass("choice");
		reLoad();
		//学员
	});
}
/*function getData1(url,data,flag){
	$.ajax({
		type : "POST",
		url : url,
		data :data,// 你的formid
		dataType:'json',
		async : false,
		error : function(request) {
			parent.layer.alert("操作失败，请核实下该班级学生数量是否满足分组数");
			$("#big").html('<span>操作失败，请核实下该班级学生数量是否满足分组数:</span>');
		},
		success : function(data) {
			if (data.code != 0) {
				layer.msg(data.msg);
				return false;
			}
			debugger
			var teamList=data.data;
			if(teamList.length==0){
				$("#big").html('<span>请先对班级学员进行分组:</span>');
				layer.alert("请先对班级学员进行分组");
				return false;
			}
			if(flag==1){
				layer.alert("操作成功");
			}

			var str="";
			var roomType="";
			var roomTypeName="";
			var typeClor="";
			$.each(teamList,function(index,value){
				roomType=value.roomType;
				if('M'==roomType){
					roomTypeName="男生";
					typeClor="";
				}else if ('F'==roomType){
					roomTypeName="女生";
					typeClor="typeClor";
				}
				str+='<div class="list"><h5 class="list-title '+typeClor+'"><span>'+roomTypeName+'宿舍名:</span><i id="a'+value.roomId+'">'+value.roomName+'</i></h5> <div class="list-info"><ul>';
				var groupList=value.studentId;
				groupList=groupList.split(',');
				var groupName=value.studentName;
				groupName=groupName.split(',');
				var number=value.number;
				number=number.split(',');
				str+='<li><div class="members"><ul id="'+value.roomId+'" name="'+value.roomType+'">';
				$.each(groupList,function(index,value){
					str+='<li class="replace"><i id="a'+value+'" name="'+number[index]+'">'+groupName[index]+'</i><img src="/img/add.png"></li>';
				});
				str+=' </ul></div></li>';

				str+='</ul></div></div>';
			});
			$("#data").append(str);
		}
	});

	$(".list").on("click",".replace",function(){
		debugger
		oldId=$(this).children('i').attr("id");
		oldName=$(this).children('i').text();
		oldNumber=$(this).children('i').attr("name");
		oldRoomId=$(this).parent().attr("id");
		oldRoomType=$(this).parent().attr("name");
		layer.open({
			title:"请选择学员和："+oldName+"进行调换宿舍",
			type:1,
			area: ['850px', '600px'],
			fix: false, //不固定
			maxmin: false,
			content:$('#popIn')
		});
		$(".choice").removeClass('choice');//先把这个样式的清空
		$(".choiceNew").removeClass('choiceNew');//先把这个样式的清空
		$(this).children('i').addClass("choice");
		reLoad();
		//学员
	});
	/!*	$(".list").on("click",".replace1",function(){
	 oldId=$(this).children('i').attr("id");
	 oldName=$(this).children('i').text();
	 layer.open({
	 title:"请选择警员和组长："+oldName+"进行调换",
	 type:1,
	 area: ['850px', '600px'],
	 fix: false, //不固定
	 maxmin: false,
	 content:$('#popIn')
	 });
	 $(".choice").removeClass('choice');//先把这个样式的清空
	 $(".choiceNew").removeClass('choiceNew');//先把这个样式的清空
	 $(this).children('i').addClass("choice");
	 //组长
	 });
	 $(".list").on("click",".list-title",function(){
	 oldId=$(this).children('i').attr("id");
	 oldName=$(this).children('i').text();
	 layer.open({
	 title:"请选择警员和中队长："+oldName+"进行调换",
	 type:1,
	 area: ['850px', '600px'],
	 fix: false, //不固定
	 maxmin: false,
	 content:$('#popIn')
	 });
	 $(".choice").removeClass('choice');//先把这个样式的清空
	 $(".choiceNew").removeClass('choiceNew');//先把这个样式的清空
	 $(this).children('i').addClass("choice");
	 //中队长
	 });*!/


}*/
