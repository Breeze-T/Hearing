option1 = {
        title : {
            text: '学员男女比例',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : 'left',
            data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
        },
        calculable : true,
        series : [
            {
                name:'访问来源',
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    /*{value:335, name:'直接访问'},
                    {value:310, name:'邮件营销'},
                    {value:234, name:'联盟广告'},
                    {value:135, name:'视频广告'},
                    {value:1548, name:'搜索引擎'}*/
                ]
            }
        ]
    };

$.getJSON('/edu/student/getStudentBySex',function(data){
	var nameArr = [];
	$.each(data,function(index,value){
		nameArr.push(value.name);
		option1.series[0].data.push({value:value.num,name:value.name});
	});
	option1.legend.data=nameArr;
	var studentBysex=echarts.init(document.getElementById("studentBysex"));
	studentBysex.setOption(option1);
    $(window).resize(studentBysex.resize);
})



option2={
        title : {
            text: '学员年龄分布'
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['学员数量']
        },
        grid:{
            x:30,
            x2:40,
            y2:24
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'学员数量',
                type:'bar',
                data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
            }
        ]
    };

$.getJSON('/edu/student/getStudentByAge',function(data){
	var rangeArr = [];
	var numArr = [];
	$.each(data,function(index,value){
		rangeArr.push(value.ageRange);
		numArr.push(value.num);
	});
	option2.xAxis[0].data=rangeArr;
	option2.series[0].data=numArr;
	var studentByAge=echarts.init(document.getElementById("studentByAge"));
	studentByAge.setOption(option2);
    $(window).resize(studentByAge.resize);
})

