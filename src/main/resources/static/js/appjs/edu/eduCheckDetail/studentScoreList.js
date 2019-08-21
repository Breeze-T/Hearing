
var prefix = "/edu/checkDetail"
var dataTemp;
var column=[{
	field :'id',
	title :'序号',
	formatter: function (value, row, index) {
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
		field : 'className',
		title : '班级'
	}];
$(function() {
	$.ajax({
		type : "POST",
		url : "/edu/eduSubject/getSubjectScoreList?class_id="+67,
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {

			if (data.length> 0) {
				var str='';
				for(var i=0;i<data.length;i++){
					dataTemp=data[i];
					column.push(
						{field: data[i].id,
						title : data[i].subject_name+'('+data[i].rate+')',
						formatter : function(value, row, index) {
							var aa=dataTemp.id;
							aa=row.id;
							if(value==-1){
								var a = '<input  name="'+row.id+'" type="number"oninput="value=checkInput(value)"/>';
								return a;
							}
							var a = '<input name="'+row.id+'" type="number"  oninput="value=checkInput(value)" value="'+value+'"/>';
							return a;
						}
							}
					);
				}
				column.push({
					field : 'totalScore',
					title : '本次总成绩'
				});

			} else {
				parent.layer.alert("请先设置课程")
			}

		}
	});
	load();
	/*	$("#num").change(function() {

	 selectChange(); });*/
});
function checkInput(value) {
	debugger
	if(value>100||value<0){
		return '';
	}
	if(value.indexOf('.')!=-1 && value.length-value.indexOf('.')>3){
		value=value.substr(0,value.length-1);
		return value;
	}
	return value;

}
function selectChange() {
	var num=$("#num").val();

	reLoad()
}
function selectLoad() {
	var html = "";
	$.ajax({
		url : '/edu/classAlbum/classChoose',
		success : function(data) {
			//加载数据
			for (var i = 0; i < data.length; i++) {
				html += '<option value="' + data[i].resultValue + '">' + data[i].resultKey + '</option>'
			}
			$(".chosen-select").append(html);
		}
	});
}
function load() {
	selectLoad();
	$('#studentTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/studentScoreList", // 服务器数据的加载地址
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
				singleSelect : false, // 设置为true将禁止多选
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
						classId : $('.chosen-select').val(),
						studentName : $('#studentName').val(),
						num : $('#num').val(),
						// name:$('#searchName').val(),
					};
				},
				// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
				// queryParamsType = 'limit' ,返回参数必须包含
				// limit, offset, search, sort, order 否则, 需要包含:
				// pageSize, pageNumber, searchText, sortName,
				// sortOrder.
				// 返回false将会终止请求
				columns : getcil()
				/*columns : [

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
						field : 'className',
						title : '班级'
					},
					{
						title : '加分项',
						formatter : function(value, row, index) {

							columns.push(
								{field: "studentName",
								check: true}
							)
							var text = "Object ";
							var data="";
							jQuery.each(row, function(i, val) {

								text = text + "Key:" + i + ", Value:" + val;
								data+='<a>'+i+'</a><input value="'+val+'"/>';

							});
							console.log(text)
							var a = '<button  type="button" class="btn btn-success" onclick="showDetail('+row.id+',\'1\')">查看详情</button>';
							return data;
						}
					},
					{
						title : '减分项',
						formatter : function(value, row, index) {
							var a = '<button  type="button" class="btn btn-success" onclick="showDetail('+row.id+',\'2\')">查看详情</button>';
							return a;
						}

					},

					{
						title : '操作',
						field : 'id',
						align : 'center',
						formatter : function(value, row, index) {
							var e = '<a class="btn btn-primary btn-sm ' + s_addPoint_h + '" href="#" mce_href="#" title="加分" onclick="addPoint(\''
								+ row.id
								+ '\')"><i class="glyphicon glyphicon-plus"></i></a> ';
							var d = '<a class="btn btn-warning btn-sm ' + s_removePoint_h + '" href="#" title="减分"  mce_href="#" onclick="removePoint(\''
								+ row.id
								+ '\')"><i class="glyphicon glyphicon-minus"></i></a> ';
							return e + d;
						}
					} ]*/
			});
	function getcil() {

		return column;
	}

}
function reLoad() {
	/*var opt = {
		query : {
			classId : $('.chosen-select').val(),
			studentName : $('#studentName').val(),
		}
	}*/
	$('#studentTable').bootstrapTable('selectPage', 1);
}
$('.studentTable').on("change","input",function () {
	if(!$(this).hasClass("flag")){
		$(this).addClass("flag")
	}
});
function save() {

	//var row=$('#studentTable').bootstrapTable('getData', 1);
	var arr=[];
	var studentIds=[];//哪些学生修改了
	var sIdTemp='';
	$('#studentTable tbody').find("tr").each(function (){
		var len=$(this).find("input.flag").length;
		var arr1=[];
		if(len>0){
			len=$(this).find("input").length;
		for(var i=0;i<len;i++){
			//var obj={};
			var val=$(this).find("input").eq(i).val();
			var sId=$(this).find("input").eq(i).attr('name');
			var classFlag=$(this).find("input").eq(i).attr('class');
			var index=$(this).find("input").eq(i).parent().index();
			var key=$('#studentTable thead th').eq(index).attr("data-field");
			var obj={'studentId':sId,'subjectId':key,'scores':val};
			if(sIdTemp!=sId){
				sIdTemp=sId;
				studentIds.push(sIdTemp);
			}

			/*obj['subjectid']=key;
			obj['scores']=val;
			obj['studentId']=sId;*/
			if(val!=''){
				arr.push(obj);
			}

		}
		}
		/*if(arr1.length>0){
			arr.push(arr1);
		}*/
	});
	var num=$("#num").val();//考试第几次
	console.log(arr);
	if(studentIds.length==0){
		return false;
	}
var data={num:num,scoreList:arr,studentList:studentIds};
	$.ajax({
		type : "POST",
		url :  prefix + "/saveStudentScore",
		data: JSON.stringify(data),
		dataType:'json',
		contentType: 'application/json',
		error : function(request) {
			layer.alert("Connection error");
		},
		success : function(r) {
			if (r.code==0) {
				layer.msg(r.msg);
				reLoad();
			}else{
				layer.msg(r.msg);
			}
		}
	});
/*
	$("#studentTable input[type='text']").each(function(){
		var aa=$(this).val();
	});*/
}
function addPoint(studentId) {
	layer.open({
		type : 2,
		title : '加分',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/addPoint/' + studentId  // iframe的url
	});
}

function removePoint(studentId) {
	layer.open({
		type : 2,
		title : '减分',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/removePoint/' + studentId  // iframe的url
	});
}

function showDetail(studentId,type){
	layer.open({
		type : 2,
		title : '详情',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/showDetail/' + studentId +'/' + type  // iframe的url
	});
}
