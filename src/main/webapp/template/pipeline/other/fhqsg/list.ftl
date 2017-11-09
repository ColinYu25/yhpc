<@fkMacros.pageHeader />
<@fkMacros.initAreaXML />
<link href="${resourcePath}/css/tabs.css" rel="stylesheet" type="text/css" />
<#escape x as (x)!>
<div id="header">
	<ul id="primary">
		<li><a href="javascript:go_Info('${entity.daPipelineInfo.daPipelineCompanyinfo.id}', '${entity.daPipelineInfo.id}');" style="width:60px;">单位情况</a></li>
		<li><a href="javascript:go_pipeLines('${entity.daPipelineInfo.id}');">管道情况</a></li>
		<li><a href="javascript:go('notSame_list.xhtml?entity.type=0', '${entity.daPipelineInfo.id}');" style="width:80px;">图纸不一致处</a></li>
		<#if includeMenu?? && includeMenu=='rq-menu' ><#else>
		<li><a href="javascript:go('cyrymjcs_list.xhtml?entity.type=1', '${entity.daPipelineInfo.id}');" style="width:140px;">穿越人员密集场所情况</a></li>				
		</#if>
		<li><span style="width:100px;">防护区施工情况</span></li>						
		<li><a href="javascript:go('accident_list.xhtml?entity.type=3', '${entity.daPipelineInfo.id}');" style="width:90px;">安全事故情况</a></li>						
		<li><a href="javascript:go('wzzy.xhtml?entity.type=4', '${entity.daPipelineInfo.id}');" style="width:90px;">违章占压情况</a></li>						
		<li><a href="javascript:go('trouble_list.xhtml?', '${entity.daPipelineInfo.id}');" style="width:60px;">主要隐患</a></li>		
		<li><a href="javascript:go('zfxt_list.xhtml?entity.type=5', '${entity.daPipelineInfo.id}');" style="width:60px;">政府协调</a></li>						
	</ul>
</div>
<div id="main">
	<div id="contents">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
		  <tr>
			<th>防护区施工情况列表</th>
		  </tr>
		</table>
		<div class="menu">
		  	<ul>
			<li id="img_save"><a href="JavaScript:create();" class="b1"><b>添加</b></a></li>
			<li id="img_amend"><a class="b2" href="javaScript:loadNote('fhqsg_update.xhtml?entity.id');"><b>修改</b></a></li>
			<li id="img_del"><a class="b3" href="javaScript:del();"><b>删除</b></a></li>  	
			<li id="img_refurbish"><a class="b4" href="javaScript:window.location.reload();"><b>刷新</b></a></li>
			<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>			
			</ul>
		</div>
		<form action="notSame_list.xhtml" method="post" name="companiesForm" id="companiesForm">
		<input type="hidden" name="entity.daPipelineInfo.id" value="${entity.daPipelineInfo.id}" id="pipeLineId" />
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_list">
			  <tr>
			  	<th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
			    <th width="4%" nowrap>序号</th>
			    <th width="46%" nowrap>区域</th>            
			    <th width="46%" nowrap>名称</th>
			  </tr>
			  <#if result?exists>
			  	<#list result?if_exists as item>
				  <tr>
				  	<td><input name="ids" type="checkbox" value="${item.id}"/></td>
				    <td><#if pagination.itemCount?exists>${pagination.itemCount+item_index+1}<#else>${item_index+1}</#if></td>
				    <td>
				    <#if item.firstArea??>
				    <@fkMacros.getSelectArea item.firstArea/>	    
				    </#if>
					<#if item.secondArea??>
				    <@fkMacros.getSelectArea item.secondArea/>	    
				    </#if>
				    <#if item.thirdArea??>
				    <@fkMacros.getSelectArea item.thirdArea/>	    
				    </#if>
				    &nbsp;
				    </td>
				    <td>
				    ${item.title}
				    </td>
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
	</div>
</div>
</#escape>
<@fkMacros.pageFooter />
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.js"></script>
<script>
jQuery.noConflict();

	function create(){
		var pipeLineId = jQuery("#pipeLineId").val();
		if (pipeLineId != ""){
			window.location.href = "fhqsg_create.xhtml?entity.daPipelineInfo.id=" + pipeLineId;	
		}
	}
	
	function del(){
		var ids = document.getElementsByName("ids");
		if (ids == null || ids.length == 0){
			alert("没有找到要删除的数据");
			return;
		}
		
		if (!window.confirm("确实要删除吗？")){
			return;
		}
		
		var pipeLineId = jQuery("#pipeLineId").val();
		for(var i=0; i<ids.length; i++){
			if (ids[i].checked){
				window.location.href = "fhqsg_delete.xhtml?entity.type=3&entity.id=" + ids[i].value + "&entity.daPipelineInfo.id=" + pipeLineId;
				return;
			}
		}
		alert("请选择要删除的数据");
	}
	
	function go(url, pipeLineId){
		if (pipeLineId != "" && pipeLineId > 0){
			window.location.href = url +　"&entity.daPipelineInfo.id=" + pipeLineId;	
		}else{
			alert("请先保存管道信息");
		}
	}
	
	function go_pipeLines(pipeId){
		<#if includeMenu?? && includeMenu=='rq-menu'>
			window.location.href = "rq_pipeline_update.xhtml?entity.id=" + pipeId;
		<#else>
			window.location.href = "yq_pipeline_update.xhtml?entity.id=" + pipeId;	
		</#if>	
	}
	
	function go_Info(companyId, pipeId){
		<#if includeMenu?? && includeMenu=='rq-menu'>
			window.location.href = "rqCompany_update.xhtml?entity.pipeId="+pipeId+"&entity.id=" + companyId;	
		<#else>
			window.location.href = "company_update.xhtml?entity.pipeId="+pipeId+"&entity.id=" + companyId;		
		</#if>
	}
</script>