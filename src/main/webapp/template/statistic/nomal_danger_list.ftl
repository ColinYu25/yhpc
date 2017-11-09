<@fkMacros.pageHeader />
<#escape x as (x)!> 
<body>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22" background="">存在一般隐患的企事业单位列表</th>
  </tr>
</table>
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="3%" nowrap>序号</th>
    <th width="29%" nowrap>单位名称</th>
    <th width="6%" nowrap>隐患数（个）</th>
    <th width="7%" nowrap>联系人</th>
    <th width="11%" nowrap>联系电话</th>
  </tr>
  <#if companies?exists>
	  <#list companies?if_exists as s>
		  <tr>
		    <td nowrap><#if pagination.itemCount?exists>${pagination.itemCount+s_index+1}<#else>${s_index+1}</#if></td>
		    <td nowrap id="roleName" name="roleName"><div align="left"><a href="loadNomalDangersOfStatistic.xhtml?statistic.companyId=${s.id}&statistic.isGorver=${statistic.isGorver}&statistic.year=${statistic.year}">${s.companyName}</a>&nbsp;</div></td>
		    <td nowrap><a href="loadNomalDangersOfStatistic.xhtml?statistic.companyId=${s.id}&statistic.isGorver=${statistic.isGorver}&statistic.year=${statistic.year}">${s.notRepairCount}&nbsp;</a></td>
		    <td nowrap>${s.fddelegate}&nbsp;</td>
		    <td nowrap>${s.phoneCode}&nbsp;</td>
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