
var prefix = "/edu/eduClass"
$(function() {
	load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/list", // 服务器数据的加载地址
					//	showRefresh : true,
					//	showToggle : true,
					//	showColumns : true,
						iconSize : 'outline',
						toolbar : '#exampleToolbar',
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
								limit: params.limit,
								offset:params.offset,
								className:$('#searchName').val()
					           // username:$('#searchName').val()
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
								field : 'id',
								title : '序号' ,
								formatter: function (value, row, index) {
									return index+1;
								}

							},
																{
									field : 'className', 
									title : '班级名称' 
								},/*
																{
									field : 'grade', 
									title : '年级，如2018'
								},*/
																{
									field : 'classAdviser', 
									title : '班主任'
								},
								/*								{
									field : 'highCaptain', 
									title : '大队长id' 
								},
																{
									field : 'mediumCaptain', 
									title : '中队长id' 
								},
																{
									field : 'groupLeader', 
									title : '组长id' 
								},*/
																{
									field : 'createUser', 
									title : '创建人' 
								},
																{
									field : 'createTime', 
									title : '创建时间' 
								},
																{
									field : 'remark', 
									title : '备注' 
								},
							{
								field : 'delFlag',
								title : '启用状态',
								formatter: function (value, row, index) {
									if (value == '1') {
										return '<span class="label label-primary">启用</span>';
									} else if (value == '0'){
										return '<span class="label label-danger">未启用</span>';
									} else if (value == '2'){
										return '<span class="label label-danger">结业</span>';
									}
								}
							},
																{
									title : '操作',
									field : 'id',
									align : 'left',
									formatter : function(value, row, index) {
										var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
												+ row.id
												+ '\')"><i class="fa fa-edit"></i>编辑</a> ';
										var d = '';
										var h = '';
										var j = '';
										if("1"==row.delFlag){
											d = '<a class="btn btn-success btn-sm" href="#" title="停用"  mce_href="#" onclick="resetState(\''
												+ row.id+'\',\''+2
												+ '\')"><i class="fa fa-unlock-alt">结业</i></a> ';
											h = '<a class="btn btn-success btn-sm '+s_edit_h+'"href="#" title="班级分组"  mce_href="#" onclick="getStudentGroupPage(\''
												+ row.id+'\',\''+row.className
												+ '\')"><i class="fa fa-cog"></i>班级分组</a> ';
											j = '<a class="btn btn-success btn-sm '+s_edit_h+'"href="#" title="分配宿舍"  mce_href="#" onclick="getStudentRoomPage(\''
												+ row.id+'\',\''+row.className
												+ '\')"><i class="fa fa-cog"></i>分配宿舍</a> ';
										}else if("0"==row.delFlag){
											d = '<a class="btn btn-success btn-sm" href="#" title="启用"  mce_href="#" onclick="resetState(\''
												+ row.id+'\',\''+1
												+ '\')"><i class="fa fa-unlock">启用</i></a> ';
										}
										var f = '<a class="btn btn-success btn-sm '+s_edit_h+'" href="#" title="设置1学科"  mce_href="#" onclick="resetPwd(\''
												+ row.id
												+ '\')"><i class="fa fa-cog"></i>设置学科</a> ';
										var g = '<a class="btn btn-success btn-sm '+s_edit_h+'"href="#" title="班级相册"  mce_href="#" onclick="classImg(\''
											+ row.id+'\',\''+row.className
											+ '\')"><i class="fa fa-cog"></i>班级相册</a> ';
										return e + d +f+g+h+j;
									}
								} ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('selectPage', 1);
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
function resetState(id,state) {
	var msg='确定要变更班级状态吗?';
	if(state==1){
		msg='确定要启用该班级吗?';
	}else if(state==2){
		msg='确定要对该班级进行结业吗?结业之后学员的宿舍将会被清空';
	}
	layer.confirm(msg, {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/updateScheduleDate",
			type : "post",
			data : {id:id,delFlag:state},// 你的formid
			success : function(r) {
				if (r.code==0) {
					layer.msg(r.msg);
					reLoad();
				}else{
					layer.msg(r.msg);
				}
			}
		});
	})
}

function resetPwd(id) {
	layer.open({
		type : 2,
		title : '请选择教官所带学科',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '600px' ],
		content : prefix + '/editSubject/' + id // iframe的url
	});
}
function classImg(id,className) {
	layer.open({
		type : 2,
		title : '班级相册',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '80%', '98%' ],
		content :'/edu/classAlbum?classId='+id+'&className='+className// iframe的url
	});
}
/*function classImg(id,className) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '80%', '90%' ],
		content :'/common/sysFile?classId='+id+'&className='+className// iframe的url
	});
}*/
function getStudentGroupPage(id,className) {
	layer.open({
		type : 2,
		title : '学员分组',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '100%', '100%' ],
		content :'/edu/eduDormitoryStudent/getStudentGroupPage?classId='+id+'&className='+className// iframe的url
	});
}
function getStudentRoomPage(id,className) {
	layer.open({
		type : 2,
		title : '分配学员宿舍',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '100%', '100%' ],
		content :'/edu/eduDormitoryStudent/getStudentDormitoryPage?classId='+id+'&className='+className// iframe的url
	});
}
function batchRemove() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
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
	}, function() {

	});
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
	var param={className:$('#searchName').val()}
	window.location.href =prefix +"/toExcel?Auditor="+ encodeURI(JSON.stringify(param));
}