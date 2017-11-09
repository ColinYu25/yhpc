<@fkMacros.pageHeader />
<#escape x as (x)!>
<script type="text/javascript">
	function createTrade(url) {
		var checkboxs = getTag('input');
		var checkSize = 0;
		for(var i=0;i<checkboxs.length; i++) {
			if(checkboxs[i].type=='checkbox'){
				if(checkboxs[i].checked) {
					checkSize ++;				
				}
			}
		}
		if(checkSize <= 1){
			if ("${admin.toString()}"=="false"&&checkSize==0) {
				alert("请选择一个父行业。");
			} else {
				get("tradeTypesForm").action = url;
				get("tradeTypesForm").submit();
			}
		} else {
			alert("请将父行业选择为一个。");
		}
	}
</script>
<body>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22"><#if type>督办方案、治理方案<#else>会议记录、纪要</#if>列表</th>
  </tr>
</table>
<form action="loadFileList.xhtml" method="post" name="tradeTypeForm" id="tradeTypeForm">
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	  <tr>
	    <th width="15%">标题：</th>
	    <td width="25%"><input type="text" name="file.fileName" id="title" size="35" value="${file.fileName}" maxlength="25"></td>
	  	<td width="10%"><input type="submit" id="sub" value="查  询" onClick="this.form.submit();" style="input_submit"/></td>
  	  </tr>
	</table>
	<input type="hidden" name="rollcallCompany.id" value="${rollcallCompany.id}">
	<input type="hidden" name="type" value="${type?string}">
</form>

<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_save">		<a href="#" class="b1" onClick="createTrade('createFileInit.xhtml?rollcallCompany.id=${rollcallCompany.id}&type=${type?string}');"><b>添加</b></a></li>
	<li id="img_amend">		<a href="#" class="b2" onClick="loadNote('createFileInit.xhtml?rollcallCompany.id=${rollcallCompany.id}&type=${type?string}&file.id');"><b>修改</b></a></li>
	<li id="img_del">		<a href="#" class="b3" onClick="loadNote('deleteFile.xhtml?rollcallCompany.id=${rollcallCompany.id}&type=${type?string}&file.id');"><b>删除</b></a></li>
	<li id="img_refurbish">	<a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return">	<a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div>
<form action="deleteActualizeProject.xhtml" method="post" name="tradeTypesForm" id="tradeTypesForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,get('ids'));"></th>
    <th width="4%">序号</th>
    <th width="40%">文件名</th>
    <th width="40%">上传时间</th>
  </tr>
  <#if rollcallFiles?exists>
  	<#list rollcallFiles as rollcallFile>
  		<#if rollcallFile.daFile?exists>
  		<tr>
  			<td><input id="id" name="rollcallFile.daFile.id" type="checkbox" value="${rollcallFile.daFile.id}"/></td>
	    	<td><#if pagination.itemCount?exists>${pagination.itemCount+rollcallFile_index+1}<#else>${rollcallFile_index+1}</#if></td>
  			<td align="left"><a href="${contextPath}${rollcallFile.daFile.fileRealPath}">${rollcallFile.daFile.fileName}</a></td>
  			<td>${rollcallFile.daFile.modifyTime}&nbsp;</td>
  		</tr>
  		</#if>
  	</#list>
  </#if>
</table>
</form>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
			<@p.navigation pagination=pagination/>
		</td>
	</tr>
</table>
</body>
</#escape>
<@fkMacros.pageFooter />