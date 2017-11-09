<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>企业列表</th>
  </tr>
</table>
<div class="menu">
  	<ul>
	<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
  	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>	
	</ul>
</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
  	<th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th width="4%" nowrap>序号</th>
    <th width="25%" nowrap>企业名称</th>
    <th width="20%" nowrap>地址</th>
  </tr>
  <#if result?exists>
  	<#list result?if_exists as item>
	  <tr>
	  	<td><input name="ids" type="checkbox" value="${item.id}"/></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+item_index+1}<#else>${item_index+1}</#if></td>
	    <td><a href="../company/loadCompany.xhtml?company.id=${item.id}">${item.companyName}</a>&nbsp;</td>
	    <td>${item.regAddress} &nbsp;</td>	    	    	    
	  </tr>
	 </#list>
  </#if>
</table>
<table width="100%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
			<@p.navigation pagination=pagination/>
		</td>
	</tr>
</table>
</#escape>