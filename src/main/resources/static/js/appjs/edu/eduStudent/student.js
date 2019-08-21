
var prefix = "/edu/student"
$(function() {
	
	//	var config = {
	//		'.chosen-select' : {},
	//		'.chosen-select-deselect' : {
	//			allow_single_deselect : true
	//		},
	//		'.chosen-select-no-single' : {
	//			disable_search_threshold : 10
	//		},
	//		'.chosen-select-no-results' : {
	//			no_results_text : '没有数据'
	//		},
	//		'.chosen-select-width' : {
	//			width : "95%"
	//		}
	//	}
	//	for (var selector in config) {
	//		$(selector).chosen(config[selector]);
	//	}
	load();
	getClass();
});
function selectLoad() {
	var html = "";
	$.ajax({
		url : '/edu/student/type',
		success : function(data) {
			//加载数据
			for (var i = 0; i < data.length; i++) {
				html += '<option value="' + data[i].value + '">' + data[i].name + '</option>'
			}
			$("#status").append(html);
			$("#status").chosen({
				maxHeight : 200
			});
			//点击事件
			$('#status').on('change', function(e, params) {
				console.log(params.selected);
				var opt = {
					query : {
						type : params.selected,
					}
				}
				$('#exampleTable').bootstrapTable('refresh', opt);
			});
		}
	});
}
function load() {
	selectLoad();
	$('#studentTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/list", // 服务器数据的加载地址
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
						status : $('#status').val(),
						studentName : $('#studentName').val(),
						classId : $('#classId').val(),
						// name:$('#searchName').val(),
					};
				},
				// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
				// queryParamsType = 'limit' ,返回参数必须包含
				// limit, offset, search, sort, order 否则, 需要包含:
				// pageSize, pageNumber, searchText, sortName,
				// sortOrder.
				// 返回false将会终止请求
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
						field : 'alarmNum',
						title : '学号'
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
						field : 'cardNum',
						title : '身份证号'
					},
					{
						field : 'phoneNum',
						title : '手机'
					},
					{
						field : 'email',
						title : '邮箱'
					},
					{
						field : 'unit',
						title : '学院'
					},
					{
						field : 'duties',
						title : '专业'
					},
					{
						field : 'map.className',
						title : '班级'
					},
					{
						field : 'type',
						title : '班级职务',
						formatter : function(value, row, index) {
							if(row.type=='1'){
								return '班长';
							}else if(row.type == '2'){
								return '委员';
							}else if(row.type == '3'){
								return '组长';
							}else if(row.type == '4'){
								return '普通';
							}else{
								return '';
							}
						}
					},
					{
						field : 'status',
						title : '状态',
						formatter : function(value, row, index) {
							if(row.status=='0'){
								return '待审核';
							}else if(row.status == '1'){
								return '正常';
							}else{
								return '未通过';
							}
						}
					},
					{
						visible : false,
						field : 'classId',
						title : '班级id'
					},
					{
						visible : false,
						field : 'photo',
						title : '照片'
					},
					{
						visible : false,
						field : 'userId',
						title : '用户id'
					},
					{
						title : '操作',
						field : 'id',
						align : 'center',
						formatter : function(value, row, index) {
							var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
								+ row.id 
								+ '\')"><i class="fa fa-edit"></i></a> ';
							var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
								+ row.id + '\',\''+row.userId + '\',\''+row.status
								+ '\')"><i class="fa fa-remove"></i></a> ';
							var f ='';
							if(row.status=='0'){
								f='<a class="btn btn-success btn-sm ' + s_pass_h + '" href="#" title="通过"  mce_href="#" onclick="pass(\''
									+ row.id
									+ '\')"><i class="glyphicon glyphicon-ok-circle"></i></a> '+'<a class="btn btn-danger btn-sm ' + s_refuse_h + '" href="#" title="驳回"  mce_href="#" onclick="refuse(\''
									+ row.id
									+ '\')"><i class="glyphicon glyphicon-remove-circle"></i></a> ';
							}
							return e + d + f;
						}
					} ]
			});
}
function reLoad() {
	/*var opt = {
		query : {
			status : $('.chosen-select').val(),
			studentName : $('#studentName').val(),
			selectPage : 1
		}
	}*/
	$('#studentTable').bootstrapTable('selectPage', 1);
}
function add() {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add' // iframe的url
	});
}
function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}
function remove(studentId,userId,status) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix + "/remove",
			type : "post",
			data : {
				'studentId' : studentId,
				'userId' : userId,
				'status' : status
			},
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	})
}


function batchRemove() {
	var rows = $('#studentTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		var userIds = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
			if(row['userId'] != null && row['userId'] != ''){
				userIds.push(row['userId']);
			}
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids,
				'userIds' : userIds
			},
			url : prefix + '/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {});
}


function batchPass() {
	var rows = $('#studentTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	var flag = 'ok';
	$.each(rows, function(i, row) {
		if(row['status'] != '0'){
			flag = 'no';
		}
	});
	if(flag == 'no'){
		layer.msg("请选择待审核的记录进行操作");
		return;
	}
	if(rows.length == 0){
		layer.msg("请选择要通过的记录");
		return;
	}
	layer.confirm("确认要通过选中的'" + rows.length + "'条记录吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchPass',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {});
}


function pass(id){
	layer.confirm('确定要通过选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix + "/pass",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	})
}


function refuse(id){
	layer.confirm('确定要驳回选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix + "/refuse",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	})
}


function batchRefuse() {
	var rows = $('#studentTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	var flag = 'ok';
	$.each(rows, function(i, row) {
		if(row['status'] != '0'){
			flag = 'no';
		}
	});
	if(flag == 'no'){
		layer.msg("请选择待审核的记录进行操作");
		return;
	}
	if (rows.length == 0) {
		layer.msg("请选择要通过的记录");
		return;
	}
	layer.confirm("确认要通过选中的'" + rows.length + "'条记录吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchPass',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {});
}


//导出模板
function exportStuModel(){
	window.open(prefix + "/exportStuModel");
}

function batchImport(){
	var form = new FormData(document.getElementById("upload_submit"));
	var fileName = document.getElementById("upload_excel").value;
	if(fileName != ""){
		if(fileName.split('.')[1]=="xlsx" || fileName.split('.')[1]=="xls"){
			$.ajax({
	              cache : false,
	              type : "POST",
	              url : prefix + '/batchImport',
	              data : form,
	              processData : false,
	              contentType : false,
	              async : true,
	              error : function(request) {
	            	  layer.msg("导入失败");
	              },
	              success : function(r) {
	            	  if (r.code == 0) {
	  					layer.msg(r.msg);
	  					setTimeout(function () { window.location.reload(); }, 1000);
	  					
	  				} else {
	  					layer.msg(r.msg);
	  					setTimeout(function () { window.location.reload(); }, 1000);
	  				}
	              }
	     });
		}else{
			$.dialog.tips('只允许上传excel文件！',2);
			return false;
		}
	}else{
		$.dialog.tips('文件名不为空！',2);
		return false;
	}
}
function toExcel(id,name) {
	/*layer.open({
	 type : 2,
	 title : name+'的测评分数',
	 maxmin : true,
	 shadeClose : false, // 点击遮罩关闭层
	 area : [ '100%', '100%' ],
	 content : prefix + '/ToExcel/' + id // iframe的url
	 });*/
	debugger
	var param={status : $('#status').val(),
		studentName : $('#studentName').val(),classId : $('#classId').val()}
	window.location.href =prefix +"/toExcel?Auditor="+ encodeURI(JSON.stringify(param));
}
function getClass() {
	var html = "";
	$.ajax({
		url : '/edu/classAlbum/classChoose?state=1',
		success : function(data) {
			//加载数据
			for (var i = 0; i < data.length; i++) {
				html += '<option value="' + data[i].resultValue + '">' + data[i].resultKey + '</option>'
			}
			//$("#classId").html('');
			$("#classId").append(html);
		}
	});
}