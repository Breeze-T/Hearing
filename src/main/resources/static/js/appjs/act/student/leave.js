$().ready(function () {
    validateRule();
    getLeaveType();
});
$.validator.setDefaults({
    submitHandler: function () {
        save();
    }
});
function save() {
    $.ajax({
        cache: true,
        type: "POST",
        url: "/student/leave/save",
        data: $('#signupForm').serialize(),// 你的formid
        async: false,
        error: function (request) {
            parent.layer.alert("Connection error");
        },
        success: function (data) {
            if (data.code == 0) {
                parent.layer.msg("操作成功");
                parent.reLoad();
                var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                parent.layer.close(index);

            } else {
                parent.layer.alert(data.msg)
            }

        }
    });
}
function getLeaveType(){
    $.ajax({
        cache: true,
        url: "/common/dict/list/leave_type",
        data: {type:'leave_type'},// 你的formid
        async: false,
        error: function (request) {
            parent.layer.alert("连接异常");
        },
        success: function (data) {
            $.each(data,function(index,value){
                if(index == 1){
                    $('#leaveType').append('<option data-id="'+value.id+'" selected label="'+value.name+'">'+value.name+'</option>');
                }else{
                    $('#leaveType').append('<option data-id="'+value.id+'" label="'+value.name+'">'+value.name+'</option>');
                }
            });
        }
    });
}
function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            startTime: {
                required: true
            },
            endTime: {
                required: true
            },
            reason: {
                required: true
            }
        },
        messages: {
            startTime: {
                required: icon + "必填项"
            },
            endTime: {
                required: icon + "必填项"
            },
            reason: {
                required: icon + "必填项"
            }
        }
    })
}