<@fkMacros.pageHeader />
<link href="${resourcePath}/css/tabs.css" rel="stylesheet" type="text/css" />
<#escape x as (x)!>
<@fkMacros.winLIKE />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>
	<#if company?? && company.id??>
 		高压管道列表
 	<#else>
 		<#if entity?? && entity.type??>
		<#if entity.type==1>长输管道</#if><#if entity.type==2>城镇燃气管道</#if><#if entity.type==3>工业管道</#if><#if entity.type==4>港区内油气管道</#if></#if>列表
 	</#if>
 	</th>
  </tr>
</table>
<form action="" method="post" name="pipeLineForm" id="pipeLineForm">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	  <tr>
	  	<th>管道名称</th>
	    <td width="35%"><input type="text" name="entity.name" id="name" value="${entity.name}" size="45" maxlength="50"></td>
	    <#if company?? && company.id??>
	    <th>管道传输介质</th>
	    <td width="35%">
	    	<input type="text" name="entity.medium" value="${entity.medium}" id="medium" maxlength="50"/>
	    </td>
	  	<#else>
	  	<th>所属企业</th>
	    <td width="35%"><input type="text" name="entity.daPipelineCompanyinfo.company.companyName" id="companyName" value="${entity.daPipelineCompanyinfo.company.companyName}" size="45" maxlength="50"></td>
	    </#if>
	  </tr>
	  <#if company?? && company.id??>
	  <tr>
	  	<th>管道种类</th>
	    <td width="35%">
	    	<select name="entity.type">
	    		<option value="-1">-请选择-</option>
	    		<option value="1" <#if entity?? && entity.type?? && entity.type == 1>selected</#if>>长输管道</option> 
	    		<option value="2" <#if entity?? && entity.type?? && entity.type == 2>selected</#if>>城镇燃气管道</option> 
	    		<option value="3" <#if entity?? && entity.type?? && entity.type == 3>selected</#if>>工业管道</option> 
	    		<option value="4" <#if entity?? && entity.type?? && entity.type == 4>selected</#if>>港区内油气管道</option> 
	    	</select>
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
	  </#if>
	  <tr>
	  	<th>管道起点</th>
	    <td width="35%">
	    	<input type="text" name="entity.startPoint" id="name" value="${entity.startPoint}" size="45" maxlength="50">
	    </td>
	    <th>管道止点</th>
	    <td width="35%">
	    	<input type="text" name="entity.endPoint" id="name" value="${entity.endPoint}" size="45" maxlength="50">
	    </td>
	  </tr>
	  <!--<th colspan="4"><div align="center"><input type="submit" value="搜　索" class="confirm_but" onClick="submitForm('pipeLineForm');"/></div></th></tr>-->
	</table>
</form>
<div class="menu">
  	<ul>
  	
	<li id="img_save"><a class="b_save" href="javascript:<#if company?? && company.id??>setCqCompanyValue(${company.id});<#else>loadWindows('checkCompanyPicker.xhtml',750,400,150,60,'companies','单位列表',true);</#if>"><b>添加</b></a></li>
	<li id="img_amend"><a class="b_amend" href="javaScript:loadNote('yq_pipeline_update.xhtml?entity.id');"><b>修改</b></a></li>
	<li id="img_del"><a class="b_del" href="javaScript:deleteNote(get('deletePipeLineForm'), 'yq_pipeline_deleteAll.xhtml?entity.type=${entity.type }')"><b>删除</b></a></li>
	<li id="img_xcjcjl"><a href="export.xhtml?entity.type=${entity.type }" class="img_xcjcjl"><b>导出EXCEL</b></a></li>
	<li id="img_refurbish"><a href="javascript:window.location.reload()" class="b4"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.back(-1);" class="b6"><b>返回</b></a></li>
	<li id="img_lookup"><a class="b13" href="javaScript:document.getElementById('pipeLineForm').submit();"><b>查询</b></a></li>
	</ul>
</div>
<form action="" method="post" name="deletePipeLineForm" id="deletePipeLineForm">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_list">
	  <tr>
	  	<th width="2%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
	    <th width="3%" nowrap>序号</th>
	    <th width="17%" nowrap>管道名称</th>
	    <#if !(company?? && company.id??)><th width="21%" nowrap>所属企业</th></#if>
	    <th width="10%" nowrap>传输介质</th>
	    <th width="4%" nowrap>铺设方式</th>
	    <th width="12%" nowrap>管道起点</th>    
	    <th width="13%" nowrap>管道止点</th>  
	    <th width="8%" nowrap>竣工时间</th>                 
	  </tr>
	  <#if result?exists>
	  	<#list result?if_exists as item>
		  <tr>
		  	<td><input name="ids" type="checkbox" value="${item.id}"/></td>
		    <td><#if pagination.itemCount?exists>${pagination.itemCount+item_index+1}<#else>${item_index+1}</#if></td>
		    <td><a href="yq_pipeline_update.xhtml?entity.id=${item.id}">${item.name}</a>&nbsp;</td>
		    <#if !(company?? && company.id??)><td>${item.daPipelineCompanyinfo.company.companyName}&nbsp;</td></#if>
		    <td>${item.medium}&nbsp;</td>
		    <td>
		    <#if item.buildType?? && item.buildType ==1>
		    	架空	    
		    <#elseif item.buildType?? && item.buildType ==2>
		    	埋地
		    <#else>
		    	地面
		    </#if>&nbsp;
		    </td>
		    <td>${item.startPoint}&nbsp;</td>
		    <td>${item.endPoint}&nbsp;</td>
		    <td>${item.buildEndDate?string('yyyy-MM-dd')}&nbsp;</td>	    	    	    
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
	window.location.href = "yq_pipeline_list.xhtml?entity.daPipelineCompanyinfo.id=" + id;
}

function setCqCompanyValue(id){
	window.location.href = "company_create.xhtml?company.id=" + id;
}
</script>
<@fkMacros.pageFooter />