<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>已上报企业列表</th>
  </tr>
</table>

<div class="menu">
  	<ul>
	<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	</ul>
</div>
<form action="company_list.xhtml" method="post" name="companiesForm" id="companiesForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
  	<th  id="" width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th  id="th_id" width="4%" nowrap>序号</th>
    <th  id="th_companyName" width="25%"  nowrap>企业名称</th>
    <th id="th_regAddress" width="20%" nowrap>地址</th>
    <th width="10%" nowrap>操作</th>    

  </tr>
  <#if result?exists>
  	<#list result?if_exists as item>
	  <tr>
	  	<td><input name="ids" type="checkbox" value="${item.id}"/></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+item_index+1}<#else>${item_index+1}</#if></td>
	    <td style="text-align:left"><a href="company_view.xhtml?entity.id=${item.id}" >${item.company.companyName}</a>&nbsp;</td>
	    <td style="text-align:left">${item.company.regAddress} &nbsp;</td>	    	    	    
		<td ><a href="javascript:go_back(${item.id});">退回</a> &nbsp;</td>
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
<script>
function go_back(id){
	if (window.confirm("确定要退回？")){
		window.location.href = "company_back.xhtml?areaCode=${areaCode}&year=${year}&entity.id=" + id;
	}
}
</script>