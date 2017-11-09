<@fkMacros.pageHeader />
<#escape x as (x)!>
<#if statisticUpAndDownDepts.size()!=0>
	<#assign type = statisticUpAndDownDepts[0].type>
	<#if type=='0'>
		<#assign letter='已抄告隐患列表'>
		<#assign url='loadTroubleDown.xhtml'>
	<#elseif  type=='1'>
		<#assign letter='已抄告未回复隐患列表'>
		<#assign url='loadTroubleDownNotBack.xhtml'>
	<#elseif  type=='2'>
		<#assign letter='已抄告已回复隐患列表'>
		<#assign url='loadTroubleDownBack.xhtml'>
	<#elseif  type=='3'>
		<#assign letter='已抄告未反馈隐患列表'>
		<#assign url='loadTroubleDownNotResult.xhtml'>
	<#elseif  type=='4'>
		<#assign letter='已抄告已反馈隐患列表'>
		<#assign url='loadTroubleDownResult.xhtml'>
	</#if>
</#if>
	
<script type="text/javascript">
var enumObj=new Enum("${enumXmlUrl}");
var areaObj = new Area("${AreaXmlUrl}");
</script>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>${letter}</th>
  </tr>
</table>
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
  	<th  id="" width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th  id="th_id" width="4%"  nowrap>序号</th>
    <th  width="23%" nowrap>隐患单位名称</th>
    <th  width="18%" nowrap>发现隐患部门</th>
    <th  width="15%" nowrap>主送单位</th>
    <th  width="12%" nowrap>抄告编号</th>
    <th  width="10%" nowrap>抄告时间</th>
    <th  width="7%" nowrap>是否回复</th>
    <th  width="7%" nowrap>是否反馈</th>
  </tr>
  <#if statisticUpAndDownDepts?exists>
  	<#list statisticUpAndDownDepts as d>
	  <tr>
	  	<td><input id="ids${d.id}" name="ids" type="checkbox" value="${d.id}"/></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+d_index+1}<#else>${d_index+1}</#if></td>
	    <td><div align="left"><a href="loadTroubleByDeptPrint.xhtml?dept.id=${d.id}">${d.daTrouble.troubleCompanyName}</a></div></td>
	    <td><div align="left">${d.findDeptName}</div></td>
	    <td>
	    <#if d.mostlyCompanyArea?if_exists!=0><script type="text/javascript">document.write(areaObj.getArea("${d.mostlyCompanyArea}")[0]);</script><#else>宁波市</#if><#if d.mostlyCompany?exists><script type="text/javascript">document.write(enumObj.getSelect("${d.mostlyCompany}"));</script></#if>&nbsp;</td>	    
	    <td>${d.daTrouble.troubleNo}&nbsp;</td>
	    <td>${d.submitTime?date}&nbsp;</td>
	    <td><#if d.back?exists && d.back><a href="loadTroubleByBackPrint.xhtml?dept.id=${d.id}">是</a><#else>否</#if></td>
	    <td><#if d.result?exists && d.result><a href="loadTroubleByResultPrint.xhtml?dept.id=${d.id}">是</a><#else>否</#if></td>
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
<script type="text/javascript">
function submitForm(formName){
	var formObj=get(formName);
	formObj.action="${url}";
	formObj.submit();
}
</script>
<@fkMacros.muilt_select_js />
<#if trouble?has_content>
<@fkMacros.selectAreas_fun "${trouble?if_exists.firstArea?if_exists}" "${trouble?if_exists.secondArea?if_exists}" "${trouble?if_exists.thirdArea?if_exists}" "${trouble?if_exists.fouthArea?if_exists}" "${trouble?if_exists.fifthArea?if_exists}" "trouble."/>
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "trouble."/>
</#if>
</#escape>
<@fkMacros.pageFooter />