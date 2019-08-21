var prefix = "/edu/classAlbum"
var upload=[];
var upload1=[];
$(function() {
	selectLoad();
	//从班级管理列表点进去
	var classId=$("#classId").val();
	if(classId!=null&&classId!=''){
		$(".classChoose option[value='" + classId + "']").attr("selected", "selected");
	}
	load($(".classChoose").val())
});
function selectLoad() {
	var html = "";
	$.ajax({
		url : '/edu/classAlbum/classChoose?state=all',
		async : false,
		success : function(data) {
			//加载数据
			for (var i = 0; i < data.length; i++) {
				html += '<option value="' + data[i].resultValue + '">' + data[i].resultKey + '</option>'
			}
			$(".classChoose").append(html);
			//load($(".classChoose").val())
		}
	});
}
function load(classId) {
	$.ajax({
		type : 'get',
		data : {
			"classId" : classId
		},
		cache:false,
		url : prefix + '/list',
		success : function(data) {
			var html = '';
			var head = '';
			var end = '';
			if(data.length>0){
			$.each(data,function(i,v){
				var mid = '';
				head ='<div class="card-hd">'+
			  		'<h5>'+
			  			'<span>'+v.albumName+'</span>'+
			  			'<input type="text" placeholder="'+v.albumName+'"  name="" id="" value="'+v.albumName+'" class="hd-input"/>'+
			  		'</h5>'+
			  		'<div class="card-desc">'+
			  			'<span class="photo-desc-info">上传日期：'+v.createTime+'</span>'+
			  		'</div>'+
			  		'<div class="caozuo">'+
			  			'<a class="editor" onclick="edit(this)"><i class="ricon edit"></i>编辑</a>'+
			  			'<a class="confirm" onclick="confirm(this,'+v.id+')">确认</a>'+
			  		'</div>'+
			  	'</div>'+
			  	'<div class="card-bd bd-off">'+
			  		'<div class="photo-box clearfix">'
			  	
			  	end = '<div class="photo-item addpreviewimg" onclick="editAddpreviewimg(this,\''+v.id+'\')">'+
			  		  	'<a class="thumbnail">'+
			  		  		'<img src="/img/eduClassAlbum/add.png" alt="...">'+
			  		  	'</a>'+
			  		  '</div>'+
		  		  '</div>'+
	  		  '</div>'
	  		  
				$.each(v.albumDetailList,function(idx,val){
					mid = mid + '<div class="photo-item">'+
						  	  		'<a class="thumbnail pics">'+
						  	  			'<img src="'+val.photoUrl+'" alt="..." class="pic">'+
						  	  		'</a>'+
						  	  		'<div class="photo-mask">'+
						  	  			'<img src="/img/eduClassAlbum/delete.png" class="delete" onclick="editDeletephoto(this,'+val.id+')"/>'+
						  	  		'</div>'+
						        '</div>'
				});
				html = html+head+mid+end;
			});
			$(".card-box").children().remove();
			$(".card-box").append(html);
			}else{
				$(".card-box").children().remove();
				$(".card-box").append("<div>暂无相册</div>");
			}
		}
	});
}
function reload(msg){
	load($(msg).val());
}

$("#upload").on("change",function(event){
	$(".newpreviewimg").siblings().remove();
	var that=this;
	upload=[];
	Array.prototype.push.apply(upload, this.files);
	upload = $("#upload")[0].files;
	for(var i=0;i<upload.length;i++){
		suffixName = upload[i].name.substr(upload[i].name.lastIndexOf(".")+1).toLowerCase();
		if(suffixName != "jpg" && suffixName != "png"&& suffixName != "gif"&& suffixName != "bmp"){
			layer.msg('目前只支持jpg、png、gif、bmp格式的图片！');
			return false;
		}
	}
	$(".previewimg").css("display","block");
	for(var i=0;i<event.target.files.length;i++){
		let index=i;
		var read=new FileReader(); // 创建FileReader对像;
		read.readAsDataURL(this.files[i]);  // 调用readAsDataURL方法读取文件;
		read.onload=function(e){
        var url=e.target.result;  // 拿到读取结果;
		var str='<div class="photo-item">'
		    +'<a href="#" class="thumbnail">'
		      +'<img src="'+url+'" class="pic"/>'
		    +'</a>'
		    +'<div class="photo-mask">'
				+'<img src="/img/eduClassAlbum/delete.png" class="delete" onclick="deletephoto(this,'+index+',\'1\')"/>'    	
		    +'</div>'
		  	+'</div>';
			$(".newpreviewimg").before(str);
  				}
	}
	
});
function edit(msg){
	$(msg).css("display","none");
	$(msg).next().css("display","block");
	$(msg).parents(".card-box").find(".card-bd").removeClass("bd-off");
	$(msg).parents(".card-hd").find("h5 input").css("display","block");
	$(msg).parents(".card-hd").find("h5 span").css("display","none");
};
function confirm(msg,id){
	var oldName = $(msg).parents(".card-hd").find("h5 span").text();
	var albumName = $(msg).parents(".card-hd").find("h5 input").val();
	if(albumName == oldName){
		$(msg).css("display","none");
		$(msg).prev().css("display","block");
		$(msg).parents(".card-box").find(".card-bd").addClass("bd-off");
		$(msg).parents(".card-hd").find("h5 input").css("display","none");
		$(msg).parents(".card-hd").find("h5 span").css("display","block");
		$(msg).parents(".card-hd").find("h5 span").text($(msg).parents(".card-hd").find("h5 input").val());
	}else{
		$.ajax({
			url : prefix+'/confirm',
			type : 'get',
			data : {
				'id':id,
				'albumName':albumName
			},
			success : function(r) {
				if(r.code == 0){
					$(msg).css("display","none");
					$(msg).prev().css("display","block");
					$(msg).parents(".card-box").find(".card-bd").addClass("bd-off");
					$(msg).parents(".card-hd").find("h5 input").css("display","none");
					$(msg).parents(".card-hd").find("h5 span").css("display","block");
					$(msg).parents(".card-hd").find("h5 span").text($(msg).parents(".card-hd").find("h5 input").val());
				}else{
					layer.msg(r.msg);
				}
			}
		});
	}
};
function addpreviewimg(msg){
	$("#upload1").click().off().change(function(){
		//Array.prototype.push.apply(upload1, $("#upload1")[0].files);
		var upLength = upload1.length;
		for(var i=0;i<$("#upload1")[0].files.length;i++){
			upload1.push($("#upload1")[0].files[i]);
			let index=i+upLength;
			var read=new FileReader(); // 创建FileReader对像;
				read.onload=function(e){
                    var url=e.target.result;  // 拿到读取结果;
				var str='<div class="photo-item">'
			    +'<a href="#" class="thumbnail">'
			      +'<img src="'+url+'" class="pic"/>'
			    +'</a>'
			    +'<div class="photo-mask">'
					+'<img src="/img/eduClassAlbum/delete.png" class="delete" onclick="deletephoto(this,'+index+',\'2\')"/>'    	
			    +'</div>'
			  	+'</div>';
					$(msg).before(str);
  				}
				read.readAsDataURL(this.files[i]);  // 调用readAsDataURL方法读取文件;
		}
	});
}

function editAddpreviewimg(msg,id){
	$("#upload2").click().off().change(function(){
		var upload2 = $("#upload2")[0].files;
		if(upload2.length==0){
			return false;
		}
		for(var i=0;i<upload2.length;i++){
			suffixName = upload2[i].name.substr(upload2[i].name.lastIndexOf(".")+1).toLowerCase();
			if(suffixName != "jpg" && suffixName != "png"&& suffixName != "gif"&& suffixName != "bmp"){
				layer.msg('目前只支持jpg、png、gif、bmp格式的图片！');
				return false;
			}
		}
		var formData = new FormData();
		for(var i=0;i<upload2.length;i++){
			formData.append("file"+i,upload2[i]);
		}
		formData.append("id",id);
		$.ajax({
			url : prefix+'/editAdd',
			type : 'POST',
			data : formData,
			// 告诉jQuery不要去处理发送的数据
			processData : false,
			// 告诉jQuery不要去设置Content-Type请求头
			contentType : false,
			cache : false,
			success : function(data) {
				if(data.length==0){
					layer.msg('添加失败！');
					return false;
				}
				for(var i=0;i<upload2.length;i++){
					let index=i;
					var read=new FileReader(); // 创建FileReader对像;
					read.onload=function(e){
	                    var url=e.target.result;  // 拿到读取结果;
					var str='<div class="photo-item">'
				    +'<a href="#" class="thumbnail">'
				      +'<img src="'+url+'" class="pic"/>'
				    +'</a>'
				    +'<div class="photo-mask">'
						+'<img src="/img/eduClassAlbum/delete.png" class="delete" onclick="editDeletephoto(this,'+data[index].id+')"/>'    	
				    +'</div>'
				  	+'</div>';
						$(msg).before(str);
	  				}
					read.readAsDataURL(upload2[i]);
				}
			}
		});
	});
	
}

function deletephoto(msg,index,type){
	if(type == '1'){
		upload[index]=null;
	}else{
		upload1[index]=null;
	}
	$(msg).parents(".photo-item").remove();
	
}

function editDeletephoto(msg,detailId){
	$.ajax({
		url : prefix + "/delete",
		type : "post",
		data : {
			'detailId' : detailId
		},
		success : function(r) {
			if (r.code == 0) {
				$(msg).parents(".photo-item").remove();
			} 
		}
	});
}

function send(){
	var albumName = $('#albumName').val();
	var classId = $(".classChoose").val();
	var uploadFileArr = [];
	var suffixName="";
	if(albumName == null||$.trim(albumName)==''){
		layer.msg('请填写相册名！');
		return false;
	}
	if(albumName.length>40){
		layer.msg('相册名太长！');
		return false;
	}
	for(var i=0;i<upload.length;i++){
		if(upload[i]!=null){
			suffixName = upload[i].name.substr(upload[i].name.lastIndexOf(".")+1).toLowerCase();
			if(suffixName != "jpg" && suffixName != "png"&& suffixName != "gif"&& suffixName != "bmp"){
				layer.msg('目前只支持jpg、png、gif、bmp格式的图片！');
				return false;
			}
			uploadFileArr.push(upload[i]);
		}
	}
	for(var i=0;i<upload1.length;i++){
		if(upload1[i]!=null){
			suffixName = upload1[i].name.substr(upload1[i].name.lastIndexOf(".")+1).toLowerCase();
			if(suffixName != "jpg" && suffixName != "png"&& suffixName != "gif"&& suffixName != "bmp"){
				layer.msg('目前只支持jpg、png、gif、bmp格式的图片！格式的图片！');
				return false;
			}
			uploadFileArr.push(upload1[i]);
		}
	}
	if(uploadFileArr.length == 0){
		layer.msg('请选择图片上传！');
		return false;
	}
	var formData = new FormData();
	for(var i=0;i<uploadFileArr.length;i++){
		formData.append("file"+i,uploadFileArr[i])
	}
	formData.append("albumName",albumName);
	formData.append("classId",classId);
	$.ajax({
		url : prefix+'/add',
		type : 'POST',
		data : formData,
		// 告诉jQuery不要去处理发送的数据
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		cache : false,
		success : function(r) {
			if (r.code == 0) {
				layer.msg(r.msg);
				$(".newpreviewimg").siblings().remove();
				$('#albumName').val("");
				$(".previewimg").css("display","none");
				upload1=[];//每发送一次  将此数组清零
				load(classId);
			} else {
				layer.msg(r.msg);
			}
		}
	});
	
}