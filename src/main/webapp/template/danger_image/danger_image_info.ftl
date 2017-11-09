<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宁波市安全生产事故隐患排查治理信息系统</title>
<link rel="stylesheet" type="text/css" href="${resourcePath}/webuploader/webuploader.css">
<link rel="stylesheet" type="text/css" href="${resourcePath}/loadmask/jquery.loadmask.css">
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${resourcePath}/webuploader/webuploader.min.js"></script>
<script type="text/javascript" src="${resourcePath}/loadmask/jquery.loadmask.min.js"></script>
<script>jQuery.noConflict();</script>
<style type="text/css">
body {
	font-size:14px;
	margin:0;
	padding:0;
}
img{ border:none;}
.box {
	margin:0 auto;
	width:580px;
	background:#ffffff
}
.file_box_img {
	float:left;
	margin:10px 7px;
	border:solid 1px #efefef;
	position:relative;
	overflow:hidden;
}
.file_box_img img {
	width:127px;
	height:121px;
}
.file_box_img p {
	margin:0;
	padding:0;
	height:30px;
	line-height:30px;
}
.file_box_img a.close {
	position:absolute;
	top:5px;
	right:5px;
}
.file_box_img a.close img {
	border:none;
	width:20px;
	height:20px;
}
input {
	vertical-align:middle;
	margin:0;
	padding:0
}
.file_box {
	float:left;
	width:127px;
	margin-top:10px;
	position:relative;
}
</style>
</head>
<#escape x as (x)!>
<script>
jQuery(function(){
	var uploader = WebUploader.create({
	    swf:'${resourcePath}/webuploader/Uploader.swf',
	    server: '${base}/danger/image_upload.xhtml',
	    auto: true,
	    pick : {id : '#picker',multiple: false},
	    resize: true,
	    fileSingleSizeLimit:1024*1024*5,
	    formData:{"nomalDanger.id": '${nomalDanger.id}'},
	    accept: {
	        title: 'Images',
	        extensions: 'gif,jpg,jpeg,bmp,png'
	        //mimeTypes: 'image/*'
	    }
	});
	
	uploader.on( 'uploadError', uploader_uploadError);
	uploader.on('error',uploader_error);
	uploader.on('uploadSuccess', function(file, msg) {
		document.location = '${base}/danger/image_showImageWin.xhtml?nomalDanger.id=${nomalDanger.id}';
	}); 
	uploader.on( 'fileQueued', function() {
		jQuery("body").mask("正在上传...请稍等");
	});
	uploader.on( 'uploadComplete', function() {
		$(element).unmask();
	});
});

function removeTr(o) {
	jQuery(o).parent().remove();
}

function deleteFile(obj, id, name) {
	if(confirm("确定要删除图片" + name + "吗？")) {
		jQuery.ajax({
			type : "post",
			url : "${base}/danger/image_delete.xhtml",
			data : {"entity.id" : id, ran : Math.random()},
			success : function(data) {
				if(data == "true") {
					jQuery(obj).parent().remove();
				}
				alert("删除成功!");
			},
			error : function() {
				alert("删除失败!");
			}
		});
	}
}

function uploader_error(t){
	if (t=="F_EXCEED_SIZE") {
		alert("最大上传5M文件！");
	} else if (t=="F_DUPLICATE") {
		alert("无法进行重复上传！");
	} else if (t=="Q_TYPE_DENIED") {
		alert("上传失败，只允许上传jpg,png,gif,jpeg格式的图片");
	} else {
		alert(t);
	}
}
function uploader_uploadError(file){
	alert("文件上传失败！");
}

</script>
<div class="box">
	<#if daDangerImageList?? && daDangerImageList?has_content>
		<#list daDangerImageList as file>
			<div class="file_box_img">
				<a href="${base}${file.path}" target="_blank"><img src="${base}${file.path}"></a>
			   	<a class="close" href="javascript:void(0);" onclick="deleteFile(this, ${file.id}, '${file.name}');">
			    	<img src="${resourcePath}/images/x.png" width="19" height="20">
			    </a>
			</div>
		</#list>
	</#if>
	<div class="file_box">
		<div id="picker" style="padding: 0px 10px 3px;">选择图片</div>
	</div>
</div>
</#escape>
<@fkMacros.pageFooter />