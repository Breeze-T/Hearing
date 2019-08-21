
var prefix = "/edu/eduTeacher"
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
								score:"1",
					           name:$('#searchName').val(),
								policeNumber:$('#policeNumber').val(),
								teacherType:$('#teacherType').val(),
								sort:'score',
								order:'desc',
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
									/*							{
									field : 'userId', 
									title : '用户id' 
								},*/
																{
									field : 'name', 
									title : '姓名' 
								},
							{
								field : 'policeNumber',
								title : '警号'
							},
																{
									field : 'phone', 
									title : '手机号' 
								},

																{
									field : 'sex', 
									title : '性别' ,
									formatter: function (value, row, index) {
										if("M"==value){
											return "男";
										}else if("F"==value) {
											return "女";
										}
									return "未知";
									}
								},
																{
									field : 'idCard', 
									title : '身份证' 
								},
																{
									field : 'postGrade', 
									title : '职级' 
								},
																{
									field : 'post', 
									title : '职务' 
								},
																{
									field : 'company', 
									title : '单位' 
								},
																{
									field : 'teacherType', 
									title : '教官类型' 
								},
							{
								field : 'score',
								title : '测评总平均分'
							},
																						{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {
										var f = '<a class="btn btn-success btn-sm '+s_getTeacherAssessPage_h+'" href="#" title="查看分数"  mce_href="#" onclick="getTeacherAssessPage(\''
												+ row.userId+'\',\''+ row.name
												+ '\')"><i class="fa fa-building-o">查看明细</i></a> ';
										return f;
									}
								} ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('selectPage', 1);
}

function getTeacherAssessPage(id,name) {
	layer.open({
		type : 2,
		title : name+'的测评分数',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '100%', '100%' ],
		content : prefix + '/getTeacherAssessPage/' + id // iframe的url
	});
}