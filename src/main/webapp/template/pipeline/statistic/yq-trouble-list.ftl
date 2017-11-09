<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>隐患列表</th>
  </tr>
</table>
<div class="menu">
  	<ul>
	<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
  	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>	
	</ul>
</div>
<form action="company_list.xhtml" method="post" name="companiesForm" id="companiesForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
  	<th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th width="4%" style="cursor:hand;" onClick="orderProperty('id');" nowrap>序号</th>
    <th nowrap>企业名称</th>
    <th nowrap>治理状态</th>    
    <th nowrap>隐患内容</th>
  </tr>
  <#if result?exists>
  	<#list result?if_exists as item>
	  <tr>
	  	<td><input name="ids" type="checkbox" value="${item.id}"/></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+item_index+1}<#else>${item_index+1}</#if></td>
	    <td>${item.daPipelineInfo.daPipelineCompanyinfo.company.companyName}&nbsp;</td>
	    <td>
	    <#if item.status?? && item.status == 1>
		    已治理 
	    <#else>
		    未治理
	    </#if>
	    &nbsp;</td>	    
	    <td>${item.content} &nbsp;</td>	    	    	    		    
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