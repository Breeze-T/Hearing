
var prefix = "/edu/checkDetail"
$(function() {
	load();
});
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
						classId : $('.chosen-select').val(),
						studentName : $('#studentName').val(),
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
						field : 'map.className',
						title : '班级'
					},
					{
						field : 'map.adviserName',
						title : '班主任'
					},
					/*{
						title : '加分项',
						formatter : function(value, row, index) {
							var a = '<button  type="button" class="btn btn-success" onclick="showDetail('+row.id+',\'1\')">查看详情</button>';
							return a;
						}
					},
					{
						title : '减分项',
						formatter : function(value, row, index) {
							var a = '<button  type="button" class="btn btn-success" onclick="showDetail('+row.id+',\'2\')">查看详情</button>';
							return a;
						}
						
					},*/
					{	field : 'map.studentScore',
						title : '当前分数'
					},
					{
						title : '评级',
						formatter : function(value, row, index) {
							if(row.map.studentScore>=95){
								return "优秀";
							}else if(row.map.studentScore>=85&&row.map.studentScore<95){
								return "良好";
							}else if(row.map.studentScore>=60&&row.map.studentScore<85){
								return "及格";
							}else{
								return "不及格";
							}
						}	
					}/*,
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
					}*/ ]
			});
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
	var param={
		classId : $('.chosen-select').val(),
		studentName : $('#studentName').val()}
	window.location.href =prefix +"/toExcelStudentNew?Auditor="+ encodeURI(JSON.stringify(param));
}
