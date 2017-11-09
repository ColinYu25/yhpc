<@fkMacros.pageHeader />
<#escape x as (x)!>
<link href="${resourcePath}/css/tabs.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="${base}/datePicker/WdatePicker.js"></script>
<@fkMacros.initAreaXML />
<div id="main">
	<div id="contents">
		<div class="menu">
			<div style="float:left;">
		  	<ul>
			<li id="img_left"></li>
			<li id="img_save"><a href="javaScript:;" class="b1" onClick="submitCreate();"><b>保存</b></a></li>
			<li id="img_refurbish"><a href="javaScript:;" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
			<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
			</ul>
			</div>
		</div> 
		<@fkMacros.formValidator 'companyForm' />
		<form id="companyForm" name="companyForm" method="post" action="attech_save.xhtml">
		<input type="hidden" name="entity.id" value="${entity.id}" />
		<input type="hidden" name="entity.type" value="${entity.type}" />
		<input type="hidden" name="entity.daPipelineInfo.id" value="${entity.daPipelineInfo.id}" id="pipeLineId" />
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
		      <tr>
		        <th>标题</th>
		        <td>
		        <input type="text" name="entity.title" id="title" value="${entity.title}"/>
		        <ui:v for="title"  rule="require" empty="名称不允许为空" pass="&nbsp;" warn="&nbsp;"/></td>
		      </tr>
		      <tr>
		        <th>区域</th>
		        <td>
		        <div id="div-area"></div>
		        </td>
		      </tr>       
		      <tr>
		        <th>内容</th>
		        <td>
				<textarea name="entity.content" id="content1">${entity.content}</textarea>
		        <ui:v for="content1"  rule="require" empty="内容不允许为空" pass="&nbsp;" warn="&nbsp;"/></td>
		        </td>
		      </tr>      
		
		    </table>
		</form>
	</div>
</div>
</body>
</html>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.js"></script>
<script>
	jQuery.noConflict();
	function submitCreate(){
		if(formValidator.validate()){
			get("companyForm").submit();
	  		return false;
	 	}
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
<@fkMacros.muilt_select_js />
<@fkMacros.selectAreas_fun "${entity?if_exists.firstArea?if_exists}" "${entity?if_exists.secondArea?if_exists}" "${entity?if_exists.thirdArea?if_exists}" "${entity?if_exists.fouthArea?if_exists}" "${entity?if_exists.fifthArea?if_exists}" "entity."/>
</#escape>