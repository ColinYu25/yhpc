<@fkMacros.company_pageheader />
<#escape x as (x)!> 
<@enum.initAreaXML/>
<script type="text/javascript">
var checkExt = function(obj) {
	if (!(/(jpg|png|gif)$/i.test(obj.value))) {
		alert("只允许上传jpg、png、gif的文件");
		obj.select();// 选择后清空文件框
		document.selection.clear();
	}
};

function checkForm() {
	var imagefile = document.getElementById("daImageFile").value;
	if (!imagefile) {
		alert("请选择上传的文件！")
		return false;
	}
	return true;
}
</script>
<style type="text/css">
.noborder {
	border: 0;
}

.border {
	border: solid 1px #7f9db9
}

.onborder {
	background-color: #D8DBFE;
	border-top: solid 1px #D8E3EB;
	border-left: solid 1px #D8E3EB;
	border-bottom: solid 1px #0080C0;
	border-right: solid 1px #0080C0;
}

.input {
	height: 24px;
	BORDER-BOTTOM: #DBDBDB 1px solid;
	BORDER-LEFT: #DBDBDB 1px solid;
	PADDING-BOTTOM: 0px;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 0px;
	BACKGROUND: #f6f6f6;
	BORDER-TOP: #DBDBDB 1px solid;
	BORDER-RIGHT: #DBDBDB 1px solid;
	PADDING-TOP: 0px;
	-moz-border-radius: 0px;
	-webkit-border-radius: 0px
}

/*弹出上传*/
.main_up {
	margin-top: 10px;
	background: #F6F8FC
}

.dbsy_1 {
	border: solid 2px #C9D7F1;
}
</style>
<body style="background-color:#fff; overflow:scroll;overflow-y:hidden;overflow-x:hidden;"> 
 <table width="100%" border="0" cellpadding="0" cellspacing="0">
 	<tr>
    	<td width="280" style="height:20px" align="center" valign="bottom">厂区大门</td>
        <td width="280" style="height:20px" align="center" valign="bottom">主要作业场所
  </tr>
 </table>
 <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="280" style="height:150" align="center" valign="bottom">
    	<#if imageOne??>
    	<a href="..${imageOne.path}" class="thickbox"><img src="..${imageOne.path}" width="260" height="150"/></a><br/>
    		 <a href="#" onclick="javascript:showImageUpload(2,${imageOne.id},1)">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="if(confirm('确定删除吗？'))window.location.href='../imageFile/delete.xhtml?daImageFile.id=${imageOne.id}&daImageFile.daCompany.id=${company.id}&daImageFile.urlPath=${daImageFile.urlPath}'">删除</a>
    	<#else>
    	   <#if company.id == 0>
	    		<img src="${resourcePath}/img/nopicture.jpg" width="260" height="150">
	    		<div id="oneOperate" style="font-size:13px;text-align:center;">
	    		</div>
    		<#else>
    		    <a href="#" onclick="javascript:showImageUpload(1,-1,1)"><img src="${resourcePath}/img/nopicture.jpg" width="260" height="150"></a>
	    		<div id="oneOperate" style="font-size:13px;text-align:center;">
	    		 <a href="#" onclick="javascript:showImageUpload(1,-1,1)">上传</a>
	    		</div>
    		</#if>
    	</#if>
    </td>
    <td width="280" height="150" align="center" valign="bottom">
	    <#if imageTwo??>
	    	<a href="..${imageTwo.path}" class="thickbox"><img src="..${imageTwo.path}" width="260" height="150"/></a><br/>
    		 <a href="#" onclick="javascript:showImageUpload(2,${imageTwo.id},2)">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="if(confirm('确定删除吗？'))window.location.href='../imageFile/delete.xhtml?daImageFile.id=${imageTwo.id}&daImageFile.daCompany.id=${company.id}&daImageFile.urlPath=${daImageFile.urlPath}'">删除</a>
		<#else>
			<#if company.id == 0>
				<img src="${resourcePath}/img/nopicture.jpg" width="260" height="150">
				<div id="twoOperate" style="font-size:13px;text-align:center;">
	    		</div>
			<#else>
				<a href="#" onclick="javascript:showImageUpload(1,-1,2)"><img src="${resourcePath}/img/nopicture.jpg" width="260" height="150"></a>
				<div id="twoOperate" style="font-size:13px;text-align:center;">
	    		 <a href="#" onclick="javascript:showImageUpload(1,-1,2)">上传</a>
	    		</div>
			</#if>
			
		</#if>
  	</td>
  </tr>
</table>
<div id="uploadImageDiv" style="position:absolute; left:50px; display:none; left: 100px; top: 30px; font-size:13px;z-index: 99999">
	<form method="post" id="imageUploadForm" action="#" enctype="multipart/form-data" onsubmit="return checkForm();">
	<input type="hidden" name="daImageFile.daCompany.id" value="${company.id}" />
	<input type="hidden" name="daImageFile.urlPath" value="${daImageFile.urlPath}" />
	  <table width="280" border="0" cellpadding="0" cellspacing="0" align="center" class="main_up">
		  <tr>
		    <td align="center"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="dbsy_1" height="96">
		      <tr>
		        <td valign="top" align="center" style="padding:1px 0px"><table width="94%" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		            <td height="30" align="left" valign="bottom">图片上传：</td>
		          </tr>
		          <tr>
		            <td height="30" align="left" valign="middle">
		            <input type="file" id="daImageFile" name="daImageFile.file" size="34" contentEditable="false" onchange="checkExt(this)" />
		            </td>
		          </tr>
		          <tr>
		            <td height="30" align="center" valign="middle">
		            	<input value="上传" type="submit" name="btnSubmit" style="background:url('${resourcePath}/img/map/bg1.gif');width:45px;height:20px;padding:0px;margin:0px;border:0px;" />&nbsp;
		      			<input value="取消" type="button" name="btnCancel" style="background:url('${resourcePath}/img/map/bg1.gif');width:45px;height:20px;padding:0px;margin:0px;border:0px;" onclick="javascript:hideImageUpload()" />	
					</td>
		          </tr>
		        </table></td>
		      </tr>
		    </table></td>
		  </tr>
		</table>
	 </form>
</div>
<script type="text/javascript">
	//tag:1 上传 2：编辑
	function showImageUpload(tag, id,ds){
		$("#uploadImageDiv").show();
		if(tag == 1){
			$("#imageUploadForm").attr("action", "../imageFile/createFile.xhtml?daImageFile.fileDescription="+ds);;
		}else{
			$("#imageUploadForm").attr("action", "../imageFile/updateFile.xhtml?daImageFile.id="+id+"&daImageFile.fileDescription="+ds);
		}
	}
	function hideImageUpload(){
		$("#uploadImageDiv").hide();
	}
</script>
</#escape> 
<@fkMacros.pageFooter />