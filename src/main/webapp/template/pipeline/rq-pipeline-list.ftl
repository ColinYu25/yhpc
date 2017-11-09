<@fkMacros.pageHeader />
<@fkMacros.initAreaXML />
<link href="${resourcePath}/css/tabs.css" rel="stylesheet" type="text/css" />
<#escape x as (x)!>
<div id="header">
	<ul id="primary">
		<li><a href="javascript:go_Info();">单位情况</a></li>
		<li><span>管道情况</span></li>
	</ul>
</div>
<div id="main">
	<div id="contents">
	
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>燃气管道信息列表</th>
  </tr>
</table>


<div class="menu">
  	<ul>
	<li id="img_save"><a href="JavaScript:create();" class="b1"><b>添加</b></a></li>
	<li id="img_amend"><a class="b2" href="JavaScript:loadNote('rq_pipeline_update.xhtml?entity.id');"><b>修改</b></a></li>
	<li id="img_del"><a class="b3" href="JavaScript:del();"><b>删除</b></a></li>  	
	<li id="img_refurbish"><a class="b4" href="JavaScript:window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>	
	</ul>
</div>
<form action="company_list.xhtml" method="post" name="companiesForm" id="companiesForm">
<input type="hidden" name="entity.daPipelineCompanyinfo.id" value="${entity.daPipelineCompanyinfo.id}" id="companyId" />
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
  	<th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th width="4%" nowrap>序号</th>
    <th width="8%" nowrap>所在区域</th>    
    <th width="25%" nowrap>铺设方式</th>
    <th width="12%" nowrap>管道长度</th>        
    <th width="12%" nowrap>传输介质</th>        
    <th width="12%" nowrap>管道材质</th>  
    <th width="12%" nowrap>规划许可</th>                  
  </tr>
  <#if result?exists>
  	<#list result?if_exists as item>
	  <tr>
	  	<td><input name="ids" type="checkbox" value="${item.id}"/></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+item_index+1}<#else>${item_index+1}</#if></td>
	    <td>
	    <#if item.areaCode??>
	    <a href="rq_pipeline_update.xhtml?entity.id=${item.id}"><@fkMacros.getSelectArea item.areaCode/></a>
	    </#if>
	    &nbsp;
	    </td>
	    <td><a href="rq_pipeline_update.xhtml?entity.id=${item.id}">
	    <#if item.buildType?? && item.buildType ==1>
	    	架空	    
	    <#elseif item.buildType?? && item.buildType ==2>
	    	埋地
	    <#else>
	    	地面
	    </#if>&nbsp;
	    </a>
	    </td>
	    <td><a href="rq_pipeline_update.xhtml?entity.id=${item.id}">${item.length?string("####.####")} Km</a></td>
	    
	    <td>
	    ${item.medium}
	    </td>
	    <td>
	    ${item.material}
	    </td>
	    <td>
	    <#if item?? && item.ghxklx?? && item.ghxklx == 2>
	    	全部办理
	    <#elseif item?? && item?? && item.ghxklx?? && item.ghxklx == 1>
	    	部分办理
	    <#else>
	    	没有办理
	    </#if>
	    </td>	    	    	    
	  </tr>
	 </#list>
  </#if>
</table>
</form>


</div>
</div>
</#escape>
<@fkMacros.pageFooter />
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.js"></script>
<script>
jQuery.noConflict();
function go_Info(){
	var companyId = jQuery("#companyId").val();
	if (companyId != ""){
		window.location.href = "rqCompany_update.xhtml?entity.id=" + companyId;	
	}
}

function create(){
	var companyId = jQuery("#companyId").val();
	if (companyId != ""){
		window.location.href = "rq_pipeline_create.xhtml?entity.daPipelineCompanyinfo.id=" + companyId;	
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
	var companyId = jQuery("#companyId").val();
	for(var i=0; i<ids.length; i++){
		if (ids[i].checked){
			window.location.href = "rq_pipeline_delete.xhtml?entity.id=" + ids[i].value + "&entity.daPipelineCompanyinfo.id=" + companyId;
			return;
		}
	}
	alert("请选择要删除的数据");
}
</script>