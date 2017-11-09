<@fkMacros.pageHeader />
<#escape x as (x)!> 
<body>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22" background="">重大隐患列表</th>
  </tr>
</table>
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="5%">序号</th>
		  	<th width="35%">单位名称</th>
		  	<th width="15%">隐患编号</th>
		  	<th width="35%">隐患地址</th>
		  	<th width="15%">联 系 人</th>
		  </tr>
		  <#if dangers?exists>
		  	<#list dangers?if_exists as c>
			  <tr height="25" align="center" >
			    <td><#if pagination.itemCount?exists>${pagination.itemCount+c_index+1}<#else>${c_index+1}</#if></td>
			    <td><div align="left">${c.daCompanyPass.daCompany.companyName}&nbsp;</div></td>
			    <td><a href="loadDangerOfStatistic.xhtml?statistic.companyId=${c.id}"><#if c.dangerNo?exists>${c.dangerNo}<#else>&nbsp;</#if></a></td>
			    <td><div align="left"><a href="loadDangerOfStatistic.xhtml?statistic.companyId=${c.id}"><#if c.dangerAdd?exists>${c.dangerAdd}<#else>&nbsp;</#if></a></div></td>
			    <td><#if c.linkMan?exists>${c.linkMan}<#else>&nbsp;</#if></td>
			  </tr>
			 </#list>
		  </#if>
</table>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
			<@p.navigation pagination=pagination/>
		</td>
	</tr>
</table>
</#escape> 
<@fkMacros.pageFooter />