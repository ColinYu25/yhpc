<@fkMacros.pageHeader />
<link href="${resourcePath}/css/tabs.css" rel="stylesheet" type="text/css" />
<#escape x as (x)!>
	
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>燃气管道信息列表</th>
  </tr>
</table>


<div class="menu">
  	<ul>
	<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>	
	</ul>
</div>
<form action="company_list.xhtml" method="post" name="companiesForm" id="companiesForm">
<input type="hidden" name="entity.daPipelineCompanyinfo.id" value="${entity.daPipelineCompanyinfo.id}" id="companyId" />
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
  	<th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th width="4%" nowrap>序号</th>
    <th nowrap>使用单位</th>        
    <th nowrap>管道长度</th>        
    <th nowrap>铺设方式</th>
    <th nowrap>传输介质</th>        
    <th nowrap>管道材质</th>  
    <th nowrap>规划许可</th>                  
  </tr>
  <#if result?exists>
  	<#list result?if_exists as item>
	  <tr>
	  	<td><input name="ids" type="checkbox" value="${item.id}"/></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+item_index+1}<#else>${item_index+1}</#if></td>
	    <td>
	    <a href="rq_pipeline_update.xhtml?entity.id=${item.id}">${item.daPipelineCompanyinfo.company.companyName}</a>
	    </td>
	    <td>
	    <a href="rq_pipeline_update.xhtml?entity.id=${item.id}">${item.length?string("####.####")} Km</a>
	    </td>
	    <td>
	    <#if item.buildType?? && item.buildType ==1>
	    	架空	    
	    <#elseif item.buildType?? && item.buildType ==2>
	    	埋地
	    <#else>
	    	地面
	    </#if>&nbsp;
	    </td>
	    <td>
	    ${item.medium}&nbsp;
	    </td>
	    <td>
	    ${item.material}&nbsp;
	    </td>
	    <td>
	    <#if item?? && item.ghxklx?? && item.ghxklx == 2>
	    	全部办理
	    <#elseif item?? && item?? && item.ghxklx?? && item.ghxklx == 1>
	    	部分办理
	    <#else>
	    	没有办理
	    </#if>&nbsp;
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

</#escape>
<@fkMacros.pageFooter />
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.js"></script>
<script>
jQuery.noConflict();
</script>