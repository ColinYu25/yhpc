<@fkMacros.pageHeader />
<#escape x as (x)!>
<#if file?exists>
  		<#assign url='updateFile.xhtml?type=${type?string}'>
<#else>
  		<#assign url='createFile.xhtml?type=${type?string}'>
</#if>
<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
  		  var obj=get("fileForm");
  		  obj.action="${url}";
 		  obj.submit();
 	}
}
</script>
<body>
<table border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th><#if file?exists>修改<#else>添加</#if><#if type==1>督办方案<#elseif type==2>治理方案<#elseif type==3>督办记录</#if></th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_save"><a href="#" class="b_xcjcjl" onClick="submitCreate();"><b>保存</b></a></li>
	<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div> 
<@fkMacros.formValidator 'fileForm' />
<form id="fileForm" name="fileForm" method="post" action="" enctype="multipart/form-data">
<table width="98%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_input">
      <tr>
        <th colspan="2">文件名</th>
        <td><input name="file.fileName" type="text" class="input" id="fileName" value="${file.fileName}" size="40" maxlength="50">&nbsp;<span style=color:red>*</span></td>
      </tr>
      <tr>
      <th colspan="2">附件</th>
        <td><input name="file.file" type="file" class="input" id="filePath" value="${file.file}" contentEditable="false" size="40">&nbsp;<span style=color:red>*<a href="${contextPath}${file.fileRealPath}">${file.fileFileName}</a></span></td>
      </tr>
      </table></tr>
      <input type="hidden" name="file.id" value="${file.id}">
      <input type="hidden" name="file.createTime" value="${file.createTime}">
      <input type="hidden" name="rollcallCompany.id" value="${rollcallCompany.id}">
</table>
</form>
</#escape>
<@fkMacros.pageFooter />