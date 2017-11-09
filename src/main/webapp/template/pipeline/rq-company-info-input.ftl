<@fkMacros.pageHeader />
<#escape x as (x)!>
<link href="${resourcePath}/css/tabs.css" rel="stylesheet" type="text/css" />
<@fkMacros.initAreaXML />
<div id="header">
	<ul id="primary">
		<li><span style="width:60px;">单位情况</span></li>
		<li><a href="javascript:go_pipeLines('${entity.pipeId}');">管道情况</a></li>
	</ul>
</div>
<div id="main">
	<div id="contents">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
		  <tr>
			<th>燃气管道基本情况调查表</th>
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
		      </tr>
		      <tr>
		        <th colspan="2" style="width:15%">使用单位</th>
		        <td colspan="3">${company.companyName}&nbsp;</td>
		      </tr>
		      <tr>
		        <th colspan="2" style="width:15%">所在区域</th>
		        <td style="width:20%" ><@fkMacros.getSelectArea company.secondArea /> <@fkMacros.getSelectArea company.thirdArea />&nbsp;
		        </td>
		        <th style="width:15%">联系地址</th>
		        <td style="width:25%">${company.regAddress}&nbsp;</td>
		     </tr>
		      <tr>
		        <th colspan="2">联系人</th>
		        <td>${company.fddelegate}</td>
		        <th>联系电话</th>
		        <td>${company.phoneCode}&nbsp;</td>
		      </tr>
		      <tr>
		        <th colspan="2">管道所在区域</th>
		        <td>
		        <select name="entity.areaCode">
		        <#if areas??>
		        <#list areas as item>
		        <option value="${item.areaCode}" <#if entity?? && entity.areaCode?? && entity.areaCode == item.areaCode>selected</#if>>${item.areaName}</option>
		        </#list>
		        </#if>
		        </select>
		         &nbsp;</td>
		        <th>&nbsp;</th>
		        <td>&nbsp;</td>
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
		//window.location.href = "rq_pipeline_list.xhtml?entity.daPipelineCompanyinfo.id=" + id;
		<#if pipeId?? && pipeId!=-1>
			window.location.href = "rq_pipeline_update.xhtml?entity.id=${pipeId}";
		<#else>
			<#if entity?? && entity.id!=-1>
				window.location.href = "rq_pipeline_create.xhtml?entity.daPipelineCompanyinfo.id=${entity.id}";
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
