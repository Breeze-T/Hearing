var classId=$("#classId").val();//班级
var date='';//哪一年
var section='';//第几节课
var dictSection;
//几点时间段
function customerChannelDict(list,data) {
	if(list==null||data=="" || list.length<=0){
		return "";
	}
	var len = list.length;
	for(var i=0;i<len;i++){
		if(data==list[i].value){
			return '('+list[i].description+')';
		}
	}
	return "";
}
$(function() {
	debugger
	/*$.ajax({
		type : "GET",
		url : "/common/dict/list/section",
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.length> 0) {
				dictSection=data;
			}

		}
	});*/
	addDate();
	$.ajax({
		type : "POST",
		url : "/edu/eduSubject/getSubjectTeacherList?class_id="+classId,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.length> 0) {
				var str='';
				for(var i=0;i<data.length;i++){
					str+='<li><input name="info" type="radio"/><label id="'+data[i].subject_id+'" name="'+data[i].position_user+'">'+data[i].subject_name+'-'+data[i].name+'</label></li>';
				}
				$('#subjectList').append(str);
			} else {
				parent.layer.alert("请先设置教官课程")
			}

		}
	});
	$.ajax({
		type : "POST",
		url : "/edu/eduRoom/getAllRoomList?class_id="+classId,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.length> 0) {
				var str='';
				for(var i=0;i<data.length;i++){
					str+='<li><input name="content" type="radio"/><label id="'+data[i].id+'" >'+data[i].roomName+'</label></li>';
				}
				$('#roomList').append(str);
			} else {
				parent.layer.alert("请先设置教室")
			}

		}
	});





	$(".pop em").each(function(){
		if($(this).text()){//当前元素内容不为空的操作
			console.log($(this).text())
		}else{//当前元素内容为空时的操作
			$(this).parent().addClass("one")
		}
	});
	$(".address em").each(function(){
		if($(this).text()){
			console.log($(this).text())
		}else{
			$(this).parent().addClass("one")
		}
	});
	$("table").on("click",".pop",function () {
		debugger
		var tt1=$(this).children('em').attr('id');//2018-12-01_1_s
		tt1=tt1.split('_');
		date=tt1[0];//哪一年
		section=tt1[1];//第几节课
		/*var tt=$(this).prev().text();
		var aa=$(this).prev().prev().text();*/
		$(".choice").removeClass('choice');//先把这个样式的清空
		$(this).addClass('choice');
		layer.open({
			title:"授课内容选择",
			type:1,
			area: ['350px', '450px'],
			fix: false, //不固定
			maxmin: false,
			content:$('#popIn')
		});
	});
	//点击确定课程
	$(".confirm").click(function(){
		debugger
		var val=$('input[name="info"]:checked').next().text();
		if(''==val){
			parent.layer.alert("授课内容不能为空!")
			return false;
		}
		var subjectId=$('input[name="info"]:checked').next().attr("id");
		var positionUser=$('input[name="info"]:checked').next().attr("name");
		$('input[name="info"]:checked').attr("checked",false);

		var data='classId='+classId+"&date="+date+"&section="+section+"&subjectId="+subjectId+"&positionUser="+positionUser;
		//保存课表
		var re=save(data,1);
		if(re==0){
			$('.choice em').text(val);
			$('.choice em').parent().removeClass("one");
			$(".layui-layer").css("display","none");
			$(".layui-layer-shade").css("display","none");
		}
		$(".pop").removeClass("choice");
		layer.closeAll();
	});

	$("table").on("click",".address",function () {
		debugger
		var tt1=$(this).children('em').attr('id');
		tt1=tt1.split('_');
		date=tt1[0];//哪一年
		section=tt1[1];//第几节课
		$(".choice11").removeClass('choice11');//先把这个样式的清空
		$(this).addClass('choice11');
		layer.open({
			title:"授课地点的选择",
			type:1,
			area: ['350px', '450px'],
			fix: false, //不固定
			maxmin: false,
			content:$('#address')
		});
	});
	$(".confirm1").click(function(){
		debugger
		var val=$('input[name="content"]:checked').next().text();
		if(''==val){
			parent.layer.alert("教学地点不能为空!")
			return false;
		}
		var roomId=$('input[name="content"]:checked').next().attr("id");
		var data='classId='+classId+"&date="+date+"&section="+section+"&roomId="+roomId;
		$('input[name="content"]:checked').attr("checked",false);
		//保存课表
		var re=save(data,2);
		if(re==0){
			$('.choice11 em').text(val);
			$('.choice11 em').parent().removeClass("one");
			$(".layui-layer").css("display","none");
			$(".layui-layer-shade").css("display","none");
		}

		$(".address").removeClass("choice11");
		layer.closeAll();
	});
	$(".cancel").click(function(){
		layer.closeAll();
	});
	var start = {
		elem: '#start',
		format: 'YYYY-MM-DD',
		min: laydate.now(), //设定最小日期为当前日期
		max: '2099-06-16 23:59:59', //最大日期
		istime: false,
		istoday: false,
		choose: function(datas){
			end.min = datas; //开始日选好后，重置结束日的最小日期
			end.start = datas //将结束日的初始值设定为开始日
		}
	};
	var end = {
		elem: '#end',
		format: 'YYYY-MM-DD',
		min: laydate.now(),
		max: '2099-06-16 23:59:59',
		istime: false,
		istoday: false,
		choose: function(datas){
			start.max = datas; //结束日选好后，重置开始日的最大日期
		}
	};
	laydate(start);
	laydate(end);
});
function addClassDate(){
    layer.confirm('确定要重新设置课程开始和结束时间吗？', {
        btn : [ '确定', '取消' ]
    }, function() {
        debugger
        var  start=$("#start").val();
        var  end=$("#end").val();
        if(start==''||end==''){
            layer.alert("课程时间不能为空!")
            return false;
        }
		if(start==end){
			layer.alert("课程开始和结束时间不能相同!")
			return false;
		}
		addDate();
        //保存课程开始和结束时间
        $.ajax({
            cache : true,
            type : "POST",
            url : "/edu/eduClass/updateScheduleDate",
            data : "id="+classId+'&scheduleStart='+start+'&scheduleEnd='+end,// 你的formid
            async : false,
            error : function(request) {
                layer.alert("Connection error");
            },
            success : function(data) {
                if (data.code == 0) {
                    layer.msg("操作成功", {icon: 1,time: 500});
                } else {
                    layer.alert(data.msg)
                }

            }
        });
    })

}
function addDate() {
	debugger
	var  start=$("#start").val();
	var  end=$("#end").val();
	if(start==''||end==''){
		start='2018-12-12';
		end='2018-12-30';
		return false;
	}
	$("#data").html('<thead><tr bgcolor="#1ab394" style="color:#FFF;"><th colspan="2">时间</th><th>内容(授课人)</th><th>授课地点</th></tr></thead>');
	var dateList=getBetweenDateStr(start,end);
	var str="";
	for(var i=0;i<dateList.length;i++){
		console.log(dateList[i]);
	var date=dateList[i].slice(0,10);
		debugger
		str+='<tbody><tr><td rowspan="4" width="25%">'+dateList[i]+'</td>' +
			'<td>早晨'+customerChannelDict(dictSection,1)+'<a onclick="deleteSchedule(\''+date+'\',1)" class="delete-button">删除课程</a>'+'</td><td class="pop one"><em class="in" id="'+date+'_1_s"></em><img src="/img/add.png"/></td>' +
'<td class="address one"><em class="in" id="'+date+'_1_r"></em><img src="/img/add.png"/></td></tr>';
		str+='<tr><td>上午'+customerChannelDict(dictSection,2)+'<a onclick="deleteSchedule(\''+date+'\',2)" class="delete-button">删除课程</a>'+'</td><td class="pop one"><em class="in"  id="'+date+'_2_s"></em><img src="/img/add.png"/></td><td class="address one"><em id="'+date+'_2_r" class="in"></em><img src="/img/add.png"/></td></tr>';
		str+='<tr><td>下午'+customerChannelDict(dictSection,3)+'<a onclick="deleteSchedule(\''+date+'\',3)" class="delete-button">删除课程</a>'+'</td><td class="pop one"><em class="in"  id="'+date+'_3_s"></em><img src="/img/add.png"/></td><td class="address one"><em id="'+date+'_3_r" class="in"></em><img src="/img/add.png"/></td></tr>';
		str+='<tr><td>晚上'+customerChannelDict(dictSection,4)+'<a onclick="deleteSchedule(\''+date+'\',4)" class="delete-button">删除课程</a>'+'</td><td class="pop one"><em class="in"  id="'+date+'_4_s"></em><img src="/img/add.png"/></td><td class="address one"><em id="'+date+'_4_r" class="in"></em><img src="/img/add.png"/></td></tr></tbody>';
	}
	$("#data").append(str);
	showData();
}
function deleteSchedule(date,num) {
	debugger
	layer.confirm('确定要删除该节课?', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			type: "POST",
			url: "/edu/eduSchedule/deleteSchedule",
			data: {classId: classId, date: date, section: num},
			error: function (request) {
				parent.layer.alert("Connection error");
			},
			success: function (data) {
				if (data.code == 0) {
					$("#" + date + "_" + num + "_" + "s").text("");
					$("#" + date + "_" + num + "_" + "r").text("");
					$("#" + date + "_" + num + "_" + "s").parent().addClass("one");
					$("#" + date + "_" + num + "_" + "r").parent().addClass("one");
					layer.msg("操作成功");
				} else {
					layer.alert(data.msg)
				}

			}
		});
});
}
function getBetweenDateStr(start,end){
	var show_day=new Array('星期日','星期一','星期二','星期三','星期四','星期五','星期六');
	var result = [];
	var beginDay = start.split("-");
	var endDay = end.split("-");
	var diffDay = new Date();
	var dateList = new Array;
	var i = 0;
	diffDay.setDate(beginDay[2]);
	diffDay.setMonth(beginDay[1]-1);
	diffDay.setFullYear(beginDay[0]);
	result.push(start+" "+show_day[diffDay.getDay()]);
	while(i == 0){
		var countDay = diffDay.getTime() + 24 * 60 * 60 * 1000;
		diffDay.setTime(countDay);
		dateList[2] = diffDay.getDate();
		dateList[1] = diffDay.getMonth() + 1;
		dateList[0] = diffDay.getFullYear();
		if(String(dateList[1]).length == 1){dateList[1] = "0"+dateList[1]};
		if(String(dateList[2]).length == 1){dateList[2] = "0"+dateList[2]};
		result.push(dateList[0]+"-"+dateList[1]+"-"+dateList[2]+" "+show_day[diffDay.getDay()]);
		if(dateList[0] == endDay[0] && dateList[1] == endDay[1] && dateList[2] == endDay[2]){ i = 1;
		}
	};
	//console.log(result);
	return result;
};
function save(data,flag) {
	//保存课表
	$.ajax({
		cache : true,
		type : "POST",
		url : "/edu/eduSchedule/save?flag="+flag,
		data : data,// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				flag= 0;
				parent.layer.msg("操作成功", {icon: 1,time: 500});

			} else {
				flag= 1;
				parent.layer.alert(data.msg)
			}

		}
	});
	return flag;
}
function showData() {
	debugger
	//保存课表
	$.ajax({
		type : "POST",
		url : "/edu/eduSchedule/list",
		data: {classId:classId},
		dataType:'json',
		error : function(request) {
			layer.alert("Connection error");
		},
		success : function(data) {
			var ids='';
			var idr='';
			if (data.length> 0) {
				for(var i=0;i<data.length;i++){
					ids=data[i].date+"_"+data[i].section+"_s";
					idr=data[i].date+"_"+data[i].section+"_r";
					var room_name=data[i].room_name;
					if(data[i].room_name!=null){
						$("#"+idr).text(data[i].room_name);
						$("#"+idr).parent().removeClass("one");
					}
					if(data[i].subject_name!=null){
						$("#"+ids).text(data[i].subject_name+"-"+data[i].position_name);
						$("#"+ids).parent().removeClass("one");
					}
/*
					$("#"+ids).text(data[i].subject_name+"-"+data[i].position_name);
					$("#"+idr).text(data[i].room_name);
					$("#"+ids).parent().removeClass("one");
					$("#"+idr).parent().removeClass("one");*/
				}
			}

		}
	});
}