
var prefix = "/edu/checkDetail"
$(function() {
	load();
});

function load() {
	$('#detailTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/detailList", // 服务器数据的加载地址
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
						studentId : $("#studentId").val(),
						checkType : $("#checkType").val()
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
						field : 'map.checkName',
						title : '评分项'
					},
					{
						field : 'remark',
						title : '备注'
					},
					{
						field : 'map.checkType',
						title : '类型',
						formatter : function(value, row, index) {
							if(row.map.checkType=='1'){
								return '加分项';
							}else if(row.map.checkType == '2'){
								return '减分项';
							}
						}
					},
					{
						field : 'map.checkScore',
						title : '分值'
					},
					{
						field : 'createTime',
						title : '日期',
						formatter : function(value, row, index) {
							if(value != null && value != ''){
								return value.split(' ')[0];
							}else{
								return value;
							}
						}	
					},
					{
						field : 'map.createUserName',
						title : '评分者'
					},
					{
						title : '操作',
						field : 'id',
						align : 'center',
						formatter : function(value, row, index) {
							var d = '<a class="btn btn-warning btn-sm" href="#" title="删除"  mce_href="#" onclick="remove(\''
								+ row.id
								+ '\')"><i class="fa fa-remove"></i></a> ';
							
							return d;
						}
					} ]
			});
}

function reLoad() {
	$('#detailTable').bootstrapTable('selectPage', 1);
}

function remove(id){
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix + "/remove",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					parent.reLoad();
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	})
}
