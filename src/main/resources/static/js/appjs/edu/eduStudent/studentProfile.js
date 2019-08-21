
var prefix = "/edu/checkDetail"
$(function() {
	load();
	var reportTime = {
			elem: '#reportTime',
			format: 'YYYY-MM-DD',
			/*min: laydate.now(), //设定最小日期为当前日期*/
			min: '1999-01-01 00:00:00', //设定最小日期为当前日期
			max: '2099-06-16 23:59:59', //最大日期
			istime: false,
			istoday: true,
			choose: function(datas){
				end.min = datas; //开始日选好后，重置结束日的最小日期
				end.start = datas //将结束日的初始值设定为开始日
			}
		};
	laydate(reportTime);
});

function selectLoad() {
	var html = "";
	$.ajax({
		url : '/edu/classAlbum/classChoose?state=all',
		success : function(data) {
			//加载数据
			for (var i = 0; i < data.length; i++) {
				html += '<option value="' + data[i].resultValue + '">' + data[i].resultKey + '</option>'
			}
			$(".classSelect").append(html);
		}
	});
}
function load() {
	selectLoad();
	$('#studentTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : "/edu/student/profileList", // 服务器数据的加载地址
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
						keyWords : $('#keyWords').val(),
						sex : $('#sex').val(),
						reportTime :$('#reportTime').val(),
						status:'1'
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
						field : 'alarmNum',
						title : '警号'
					},
					{
						field : 'duties',
						title : '职务'
					},
					{
						field : 'unit',
						title : '单位'
					},
					{
						field : 'map.age',
						title : '年龄'
					},
					{
						field : 'map.reportTime',
						title : '入学报到时间'
					},
					{
						field : 'map.className',
						title : '班级'
					},
					{
						field : 'map.adviserName',
						title : '班主任'
					},
					{
						title : '操作',
						field : 'id',
						align : 'center',
						formatter : function(value, row, index) {
							var a = '<button  type="button" class="btn btn-success" onclick="showDetail('+row.id+')">查看详情</button>';
							return a;
						}
					} ]
			});
}
function reLoad() {
	
	$('#studentTable').bootstrapTable('selectPage', 1);
}

function showDetail(studentId,type){
	layer.open({
		type : 2,
		title : '详情',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '1000px', '600px' ],
		content :'/edu/student/profileDetail/' + studentId // iframe的url
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
	var param={classId : $('.chosen-select').val(),
		keyWords : $('#keyWords').val(),
		sex : $('#sex').val(),
		reportTime :$('#reportTime').val(),
		status:'1'}
	window.location.href ="/edu/student/toExcel?Auditor="+ encodeURI(JSON.stringify(param))+"&flag=1";
}
