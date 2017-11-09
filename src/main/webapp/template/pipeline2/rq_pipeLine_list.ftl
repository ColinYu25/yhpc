<@fkMacros.pageHeader />
<link href="${resourcePath}/css/tabs.css" rel="stylesheet" type="text/css" />
<#escape x as (x)!>
<@fkMacros.winLIKE />
<@enum.initAreaXML/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>中低压燃气管道列表</th>
  </tr>
</table>
<form action="" method="post" name="pipeLineForm" id="pipeLineForm">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	  <tr>
  		<#if company?? && company.id??>
  			<th>所在区域</th>
  			<td width="35%">
	     	 <select name="entity.areaCode2">
		        <option value="0">--请选择--</option>        
		        <#if areas??>
		        <#list areas as item>
		        <option value="${item.areaCode}" <#if entity?? && entity.areaCode2?? && entity.areaCode2 == item.areaCode>selected</#if>>${item.areaName}</option>
		        </#list>
		        </#if>
	        </select>
	       </td>
	  	<#else>
	  		<th>管道所属企业</th>
    		<td width="35%"><input type="text" name="entity.daPipelineCompanyinfo.company.companyName" id="companyName" value="${entity.daPipelineCompanyinfo.company.companyName}" size="45" maxlength="50"></td>
	    </#if>
	    <th>管道材质</th>
	    <td width="35%">
	    	<input type="text" name="entity.material" value="${entity.material}" id="medium" maxlength="50"/>
	    </td>
	  </tr>
	  <tr>
	  	<th>管道传输介质</th>
	    <td width="35%">
	    	<input type="text" name="entity.medium" value="${entity.medium}" id="medium" maxlength="50"/>
	    </td>
	    <th>管道铺设方式</th>
	    <td width="35%">
	    	<select name="entity.buildType">
	    		<option value="">-请选择-</option>
	    		<option value="1" <#if entity?? && entity.buildType?? && entity.buildType == 1>selected</#if>>架空</option> 
	    		<option value="2" <#if entity?? && entity.buildType?? && entity.buildType == 2>selected</#if>>埋地</option> 
	    		<option value="3" <#if entity?? && entity.buildType?? && entity.buildType == 3>selected</#if>>地面</option>
	    	</select>
	    </td>
	  </tr>
	   <!--<th colspan="4"><div align="center"><input type="submit" value="搜　索" class="confirm_but" onClick="submitForm('pipeLineForm');"/></div></th></tr>-->
	</table>
</form>
<div class="menu">
  	<ul>
	<li id="img_save"><a class="b_save" href="javaScript:<#if company?? && company.id??>setCqCompanyValue(${company.id});<#else>loadWindows('checkCompanyPicker.xhtml',750,400,150,60,'companies','单位列表',true);</#if>"><b>添加</b></a></li>
	<li id="img_amend"><a class="b_amend" href="javaScript:loadNote('rq_pipeline_update.xhtml?entity.id');"><b>修改</b></a></li>
	<li id="img_del"><a class="b_del" href="javaScript:deleteNote(get('deletePipeLineForm'), 'rq_pipeline_deleteAll.xhtml')"><b>删除</b></a></li>
	<li id="img_xcjcjl"><a href="export.xhtml?entity.type=${entity.type }" class="img_xcjcjl"><b>导出EXCEL</b></a></li>
	<li id="img_refurbish"><a href="javascript:window.location.reload()" class="b4"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.back(-1);" class="b6"><b>返回</b></a></li>
  	<li id="img_lookup"><a class="b13" href="javaScript:document.getElementById('pipeLineForm').submit();"><b>查询</b></a></li>
	</ul>
</div>
<form id="deletePipeLineForm" name="deletePipeLineForm" action="" method="post">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_list">
	  <tr>
	  	<th width="2%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
	    <th width="3%" nowrap>序号</th>
	    <#if !(company?? && company.id??)><th width="27%" nowrap>使用单位</th></#if>
	    <th width="8%" nowrap>所在区域</th>
	    <th width="10%" nowrap>传输介质</th>
	    <th width="4%" nowrap>铺设方式</th>
	    <th width="12%" nowrap>管道长度（KM）</th>    
	    <th width="13%" nowrap>管道材质</th>  
	  </tr>
	  <#if result?exists>
	  	<#list result?if_exists as item>
		  <tr>
		  	<td><input name="ids" type="checkbox" value="${item.id}"/></td>
		    <td><#if pagination.itemCount?exists>${pagination.itemCount+item_index+1}<#else>${item_index+1}</#if></td>
		    <#if !(company?? && company.id??)>
		    <td>
		    	<a href="rq_pipeline_update.xhtml?entity.id=${item.id}">${item.daPipelineCompanyinfo.company.companyName}</a>&nbsp;
		    </td>
		    </#if>
		    <td><#if item.areaCode??><@enum.getSelectArea item.areaCode/></#if>&nbsp;</td>
		    <td>${item.medium}</td>
		    <td>
		    <#if item.buildType?? && item.buildType ==1>
		    	架空	    
		    <#elseif item.buildType?? && item.buildType ==2>
		    	埋地
		    <#else>
		    	地面
		    </#if>&nbsp;
		    </td>
		    <td>${item.length?string('####.###')}&nbsp;</td>
		    <td>${item.material}&nbsp;</td>
		  </tr>
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
</#escape>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.js"></script>
<script>
jQuery.noConflict();
function submitCreate(){
	if(formValidator.validate()){
		get("companyForm").submit();
  		return false;
 	}
 	return false;
}
function go_pipeLines(){
	var id = jQuery("#entityId").val();
	if (id == ""){
		alert("请先保存单位基本情况，再录入管道信息。");
		return;
	}
	window.location.href = "rq_pipeline_list.xhtml?entity.daPipelineCompanyinfo.id=" + id;
}

function setCqCompanyValue(id){
	window.location.href = "rqCompany_create.xhtml?company.id=" + id;
}
</script>
<@fkMacros.pageFooter />