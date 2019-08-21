var prefix = "/personal/prsData"
$(document).ready(function () {
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method : 'get', // 服务器数据的请求方式 get or post
                url : prefix + "/list", // 服务器数据的加载地址
                iconSize : 'outline',
                toolbar : '#exampleToolbar',
                striped : true, // 设置为true会有隔行变色效果
                dataType : "json", // 服务器返回的数据类型
                pagination : true, // 设置为true会在底部显示分页条
                singleSelect : false, // 设置为true将禁止多选
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
                columns : [
                    {
                        field : 'id',
                        title : '序号' ,
                        formatter: function (value, row, index) {
                            return index+1;
                        }

                    },
                    {
                        field : 'type',
                        title : '测试类别'
                    },
                    {
                        field : 'ctime',
                        title : '测试时间'
                    },
                    {
                        title: '操作',
                        field: 'id',
                        align: 'center',
                        valign: 'center',
                        formatter: function (item, index) {
                            var e = '<a class="btn btn-primary btn-sm '
                                + '" href="#" mce_href="#" title="查看" onclick="look(\''
                                + item.id
                                + '\')"><i class="fa fa-edit"></i></a> ';
                            return e;
                        }
                    } ]
            });
}
function reLoad() {
    $('#exampleTable').bootstrapTable('selectPage', 1);
}

function look(id) {
    layer.open({
        type : 2,
        title : '查看',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : prefix + '/look/' + id // iframe的url
    });
}

function toExcel(id,name) {
    debugger
    var param={className:$('#searchTime').val()}
    window.location.href =prefix +"/toExcel?Auditor="+ encodeURI(JSON.stringify(param));
}