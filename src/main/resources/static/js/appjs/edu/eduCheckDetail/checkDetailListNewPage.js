
var prefix = "/edu/checkDetail"
$(function() {
	load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/getCheckDetailListNew", // 服务器数据的加载地址
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
								create_user:$('#create_user').val(),
								score:$('#score').val(),
								student_name:$('#student_name').val(),
								type:$('#type').val(),
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
									field : 'student_name',
									title : '被评价人'
								},
																{
									field : 'check_type',
									title : '评价类型',
									formatter: function (value, row, index) {
										debugger
										if("1"==value){
											return "加分项";
										}else if("2"==value) {
											return "减分项";
										}
										return "未知";
									}
								},
							{
								field : 'check_name',
								title : '评价指标'
							},
							{
								field : 'checkScore',
								title : '分数'
							},
							/*{
								field : 'remark',
								title : '评价内容',
							},*/
																{
									field : 'name',
									title : '评价人'
								},


																{
									field : 'remark',
									title : '意见和建议',
									width:'20%',
									formatter: function (value, row, index) {
										if(value!=null&&value.length>20){
											return "<span title="+value+">"+value.substring(0,20)+"......</span>"
										}
										return value;
									/*	return '<span class="label label-danger">未启用</span>';*/
									}
								},
																{
									field : 'create_time',
									title : '评价时间' 
								},
										/*						{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {
										var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
												+ row.id
												+ '\')"><i class="fa fa-edit"></i></a> ';
										var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
												+ row.id
												+ '\')"><i class="fa fa-remove"></i></a> ';
										var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
												+ row.id
												+ '\')"><i class="fa fa-key"></i></a> ';
										return e + d ;
									}
								} */]
					});
}
function reLoad() {
	debugger
	$('#exampleTable').bootstrapTable('selectPage', 1);
}
