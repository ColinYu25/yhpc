<@fkMacros.pageHeader />
<#escape x as (x)!>
<link href="${resourcePath}/css/tabs.css" rel="stylesheet" type="text/css" />
<@fkMacros.initAreaXML />
<@fkMacros.winLIKE />
<div id="header">
	<ul id="primary">
		<li><span style="width:60px;">单位情况</span></li>
		<li><a href="javascript:go_pipeLines('${entity.pipeId}');">管道情况</a></li>
		<!--
		<li><a href="javascript:go('notSame_list.xhtml?entity.type=0', '${entity.pipeId}');" style="width:80px;">图纸不一致处</a></li>
		<li><a href="javascript:go('cyrymjcs_list.xhtml?entity.type=1', '${entity.pipeId}');" style="width:140px;">穿越人员密集场所情况</a></li>				
		<li><a href="javascript:go('fhqsg_list.xhtml?entity.type=2', '${entity.pipeId}');" style="width:100px;">防护区施工情况</a></li>						
		<li><a href="javascript:go('accident_list.xhtml?entity.type=3', '${entity.pipeId}');" style="width:90px;">安全事故情况</a></li>						
		<li><a href="javascript:go('wzzy.xhtml?entity.type=4', '${entity.pipeId}');" style="width:90px;">违章占压情况</a></li>						
		<li><a href="javascript:go('trouble_list.xhtml?', '${entity.pipeId}');" style="width:60px;">主要隐患</a></li>		
		<li><a href="javascript:go('zfxt_list.xhtml?entity.type=5', '${entity.pipeId}');" style="width:60px;">政府协调</a></li>						
		-->
	</ul>
</div>
<div id="main">
	<div id="contents">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
		  <tr>
			<th>油气管道基本情况调查表</th>
		  </tr>
		</table>
		<div class="menu">
		  	<ul>
			<li id="img_save"><a class="b1" href="javaScript:submitCreate();"><b>保存</b></a></li>
			<li id="img_refurbish"><a class="b4" href="javaScript:window.location.reload();"><b>刷新</b></a></li>
			<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
			</ul>
		</div> 
		<@fkMacros.formValidator 'companyForm' />
		<form id="companyForm" name="companyForm" method="post" action="${urlPrefix}_save.xhtml">
		<input type="hidden" name="entity.id" value="${entity.id}" id="entityId"/>
		<input type="hidden" name="entity.pipeId" value="${entity.pipeId}" id="pipeId"/>
		<input type="hidden" name="entity.company.id" value="${company.id}" />
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
			      <tr>
			        <th rowspan="5" style="text-align:center;width:10%;">单位<br />
			          情况</th>
			        <th colspan="2" style="width:13%;">产权单位</th>
			        <td colspan="3" style="width:77%;">
			        <input type="hidden" name="entity.cqCompany.id" value="${entity.cqCompany.id}" id="cqCompanyId"/>
			        <input name="" type="text" value="${entity.cqCompany.companyName}" class="input" id="cqCompanyName" value="" size="40" readonly="readonly" onclick="loadWindows('cqCompanyPicker.xhtml',750,400,20,20,'companies','单位列表',true);">&nbsp;
			        <span style=color:red>*</span></td>
			      </tr>
			      <tr>
			        <th colspan="2">使用单位</th>
			        <td colspan="3">${company.companyName}&nbsp;</td>
			        
			      </tr>
			      <tr>
			        <th colspan="2" style="width:15%">所在区域</th>
			        <td style="width:30%" ><@fkMacros.getSelectArea company.firstArea /><@fkMacros.getSelectArea company.secondArea /> <@fkMacros.getSelectArea company.thirdArea />&nbsp;
			        </td>
			        <th style="width:15%">联系地址</th>
			        <td style="width:35%">${company.regAddress}&nbsp;</td>
			  </tr>
			      <tr>
			        <th colspan="2">联系人</th>
			        <td>${company.fddelegate}</td>
			        <th>联系电话</th>
			        <td>${company.phoneCode}&nbsp;</td>
			      </tr>
			      <tr>
			        <th colspan="2">使用单位性质</th>
			        <td>
			        <select name="entity.useType">
			        <option value="1" <#if entity?? && entity.useType?? && entity.useType == 1> selected </#if>>央企</option>
			        <option value="2" <#if entity?? && entity.useType?? && entity.useType == 2> selected </#if>>直属企业</option>
			        <option value="3" <#if entity?? && entity.useType?? && entity.useType == 3> selected </#if>>其他</option>
			        <option value="4" <#if entity?? && entity.useType?? && entity.useType == 4> selected </#if>>独立法人</option>
			        <option value="5" <#if entity?? && entity.useType?? && entity.useType == 5> selected </#if>>非独立法人</option>                        
			        </select>
			        <th>上级主管单位</th>
			        <td><input name="entity.masterDept" type="text" class="input" id="masterDept" value="${entity.masterDept}" size="25" maxlength="100" >
			        <ui:v for="masterDept" rule="require" require="false" warn="电话正确格式如0574-87364008。" pass="&nbsp;"/></td>
			      </tr>   
			</table>
		</form>
	</div>
</div>

</body>
</html>
</#escape>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.js"></script>
<script>
jQuery.noConflict();
function submitCreate(){
	if(formValidator.validate()){
		if (jQuery("#cqCompanyId").val() == ""){
			alert("请选择产权单位");
			return;
		}
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
	//window.location.href = "yq_pipeline_list.xhtml?entity.daPipelineCompanyinfo.id=" + id;
	<#if pipeId?? && pipeId!=-1>
		window.location.href = "yq_pipeline_update.xhtml?entity.id=${pipeId}";
	<#else>
		<#if entity?? && entity.id!=-1>
			window.location.href = "yq_pipeline_create.xhtml?entity.daPipelineCompanyinfo.id=${entity.id}";
		<#else>
			alert("请先保存单位基本情况，再录入管道信息。");
			return;
		</#if>
	</#if>
}

function setCqCompanyValue(id, name){
	jQuery("#cqCompanyId").val(id);
	jQuery("#cqCompanyName").val(name);	
}

function go(url, pipeLineId){
	if (pipeLineId != "" && pipeLineId > 0){
		window.location.href = url +　"&entity.daPipelineInfo.id=" + pipeLineId;	
	}else{
		alert("请先保存管道信息");
	}
}

function go_Info(companyId){
	window.location.href = "company_update.xhtml?entity.id=" + companyId;	
}
</script>