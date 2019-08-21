
var prefix = "/edu/eduAssessReport"
$(function() {
	load();
	var start = {
		elem: '#start',
		format: 'YYYY-MM-DD hh:mm:ss',
		/*min: laydate.now(), //设定最小日期为当前日期*/
		min: '1999-01-01 00:00:00', //设定最小日期为当前日期
		max: '2099-06-16 23:59:59', //最大日期
		istime: true,
		istoday: true,
		choose: function(datas){
			end.min = datas; //开始日选好后，重置结束日的最小日期
			end.start = datas //将结束日的初始值设定为开始日
		}
	};
	var end = {
		elem: '#end',
		format: 'YYYY-MM-DD hh:mm:ss',
		min: '1999-01-01 00:00:00',
		max: '2099-06-16 23:59:59',
		istime: true,
		istoday: true,
		choose: function(datas){
			start.max = datas; //结束日选好后，重置开始日的最大日期
		}
	};
	laydate(start);
	laydate(end);
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/listSign", // 服务器数据的加载地址
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
								className:$('#className').val(),
								sName:$('#sName').val(),
								start:$('#start').val(),
								end:$('#end').val(),
								status:$('#status').val(),
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
								field : 'name',
								title : '姓名'
							},
																{
									field : 'username',
									title : '用户名'
								},
																{
									field : 'sign_time',
									title : '签到时间'
								},
							{
								field : 'sign_addr',
								title : '签到地址'
							},
							{
								field : 'longitude',
								title : '签到经纬度',
								formatter: function (value, row, index) {
									if(value==null){
										return '';
									}
									return value+'-'+row.latitude;
								}
							},

																{
									field : 'remark',
									title : '备注',
									width:'20%',
									formatter: function (value, row, index) {
										if(value!=null&&value.length>20){
											return "<span title="+value+">"+value.substring(0,20)+"......</span>"
										}
										return value;
									/*	return '<span class="label label-danger">未启用</span>';*/
									}
								},
									]
					});
}
function reLoad() {
	debugger
	$('#exampleTable').bootstrapTable('selectPage', 1);
}
