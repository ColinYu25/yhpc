<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22">重大隐患详情列表</th>
  </tr>
</table>
<form action="deleteDangers.xhtml" method="post" name="dangersFormForDelete" id="dangersFormForDelete">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="3%" nowrap><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th width="3%" nowrap>序号</th>
    <th width="11%" nowrap>隐患编号</th>
    <th width="17%" nowrap>管道名称</th>
    <#if company?? && company.id??><th width="8%" nowrap>传输介质</th><#else><th width="21%" nowrap>所属企业</th></#if> 
    <th width="18%" nowrap>隐患地址</th>
    <th width="6%" nowrap>是否挂牌</th>
    <th width="7%" nowrap>联系人</th>
    <th width="11%" nowrap>联系电话</th>
  </tr>
  <#if result?exists>
  <#list result?if_exists as s>
  <tr>
  	<td><input type="checkbox" name="ids" id="ids${s.id}" value="${s.id}"></td>
    <td nowrap><#if pagination.itemCount?exists>${pagination.itemCount+s_index+1}<#else>${s_index+1}</#if></td>
    <td nowrap><a href="loadDanger.xhtml?danger.id=${s.id}">${s.dangerNo}</a>&nbsp;</td>
    <td nowrap><div align="center"><a href="../pipeline/yq_pipeline_update.xhtml?entity.id=${s.pipeLine.id}">${s.pipeLine.name}</a>&nbsp;</div></td>
    <td style="text-align:center"><#if company?? && company.id??>${s.medium}<#else>${s.pipeLine.daPipelineCompanyinfo.company.companyName}</#if>&nbsp;</td>
    <td nowrap><div align="center">${s.dangerAdd}&nbsp;</div></td>
    <td nowrap><#if (s.daPipeRollcallCompanies?size>0)>是<#else>否</#if>&nbsp;</td>
    <td nowrap>${s.linkMan}&nbsp;</td>
    <td nowrap>${s.linkTel}&nbsp;</td>
  </tr>
  </#list>
  </#if>
</table>
</form>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
		<#if (result?if_exists?size>0)>
			<@p.navigation pagination=pagination/>
		<#else>
			此处暂时没有记录！
		</#if>
		</td>
	</tr>
</table>
<script>
function submitForm(formName){
	var formObj=get(formName);
	get("year_2").value=get("year_2").value.substring(0,4);
	formObj.submit();
}
</script>
</#escape>
<@fkMacros.pageFooter />