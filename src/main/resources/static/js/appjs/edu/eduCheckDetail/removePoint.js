
var prefix = "/edu/checkDetail"
$(function() {
	load();
});

function load() {
	$('#checkTable')
		.bootstrapTreeTable(
			{
				id : 'id',
				code : 'id',
                parentCode : 'pid',
				type : "GET", // 请求数据的ajax类型
				url : '/edu/check/list', // 请求数据的ajax的url
				ajaxParams : {checkType:'2'}, // 请求数据的ajax的data属性
				expandColumn : '0', // 在哪一列上面显示展开按钮
				striped : true, // 是否各行渐变色
				bordered : true, // 是否显示边框
				expandAll : false, // 是否全部展开
				// toolbar : '#exampleToolbar',
				columns : [
					{
						field : 'checkName',
						title : '项',
                        valign : 'center',
						witth :20
					},
					{
						field : 'checkType',
						title : '类型',
                        align : 'center',
                        valign : 'center',
                        formatter : function(item, index) {
							if (item.checkType == '1') {
								return '加分项';
							} else if (item.checkType == '2') {
								return '减分项';
							}
						}
					},
					{
						field : 'score',
						title : '分值',
						align : 'center',
                        valign : 'center',
                        formatter : function(item, index) {
							if (item.score != null && item.score != '') {
								return item.score;
							} else{
								return '-';
							}
						}
					},
					{	title : '备注',
						align : 'center',
                        valign : 'center',
                        formatter : function(item, index) {
                        	var a = '<input name="remark" id="'+item.id+'" type="text"/>'
							if (item.isLeaf == '1') {
								return a;
							}
						}
					},
					{
                        formatter : function(item, index) {
                        	var a = '<input name="check" type="checkbox" value="'+item.id+'"/>'
							if (item.isLeaf == '1') {
								return a;
							}
						}
					}
					]
			});
}
function confirm(){
	var studentId = $("#studentId").val();
	var checkIds = new Array();
	var remarkArr = new Array();
	var checkArr = document.getElementsByName("check");
	for(k in checkArr){
	    if(checkArr[k].checked){
	    	checkIds.push(checkArr[k].value);
	    }
	}
	if(checkIds.length == 0){
		layer.msg("请选择评分项！"); return false;
	}
	for(var i = 0;i<checkIds.length;i++){
		remarkArr.push($("#"+checkIds[i]).val());
	}
	if(studentId == ''||studentId == null){
		layer.msg("阿欧，出错了！"); return false;
	}
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/confirm",
		data : {'studentId' : studentId,
				'checkIds' : checkIds,
				'remarkArr' : remarkArr
				},
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
			} else {
				parent.layer.alert(data.msg);
			}

		}
	});
}

