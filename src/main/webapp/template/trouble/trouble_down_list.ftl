<@fkMacros.pageHeader />
<#escape x as (x)!>
<#if dept?exists>
	<#if dept.type?if_exists=='0'>
		<#assign letter='已抄告隐患列表'>
		<#assign url='loadTroubleDown.xhtml'>
	<#elseif  dept.type?if_exists=='1'>
		<#assign letter='已抄告未回复隐患列表'>
		<#assign url='loadTroubleDownNotBack.xhtml'>
	<#elseif  dept.type?if_exists=='2'>
		<#assign letter='已抄告已回复隐患列表'>
		<#assign url='loadTroubleDownBack.xhtml'>
	<#elseif  dept.type?if_exists=='3'>
		<#assign letter='已抄告未反馈隐患列表'>
		<#assign url='loadTroubleDownNotResult.xhtml'>
	<#elseif  dept.type?if_exists=='4'>
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
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	<form action="" method="post" name="companyForm" id="companyForm">
	  <tr>
	    <td width="11%" style="background:#F0F0F0;color:#333;" align="center"><strong>单位名称</strong></td>
	    <td width="35%"><input type="text" name="trouble.troubleCompanyName" id="troubleCompanyName" value="${trouble.troubleCompanyName}" size="39" maxlength="50"></td>
	    <td width="11%" style="background:#F0F0F0;color:#333;" align="center"><strong>区　　域</strong></td>
	    <td><div id="div-area"></div></td>
	  </tr>
	</form>
	  <tr>
<th colspan="4"><div align="center"><input type="button" id="sub" value="搜　索"  class="confirm_but" style="height:25px; width:80px"  onClick="submitForm('companyForm');"/></div></th></tr>
	</table>

<div class="menu">
  	<ul>
  	<li id="img_lookup"><a href="#" class="b13" onClick="submitForm('companyForm');"><b>查询</b></a></li>
  	<#if dept.type?if_exists=='2'>
  	<li id="img_xcjcjl"><a href="#" class="b_xcjcjl" onClick="loadNote('loadTroubleByBackPrint.xhtml?dept.id')"><b>查看回复</b></a></li>
  	</#if>
  	<#if dept.type?if_exists=='4'>
  	<li id="img_xcjcjl"><a href="#" class="b_xcjcjl" onClick="loadNote('loadTroubleByResultPrint.xhtml?dept.id')"><b>查看反馈</b></a></li>
  	</#if>
	<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
</div>
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
  <#if deptes?exists>
  	<#list deptes as d>
	  <tr>
	  	<td><input id="ids${d.id}" name="ids" type="checkbox" value="${d.id}"/></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+d_index+1}<#else>${d_index+1}</#if></td>
	    <td><div align="left"><a href="loadTroubleByDeptPrint.xhtml?dept.id=${d.id}">${d.daTrouble.troubleCompanyName}</a></div></td>
	    <td><div align="left">${d.findDeptName}</div></td>
	    <td>
	    	<#if enums?exists>
	  	<#list enums?if_exists as i>
	  		<#assign checked=''>
  					<#if i.enumCode==d.mostlyCompany>
  						<#if d.mostlyCompanyArea==0>宁波市<#else>${d.mostlyCompanyAreaName}</#if>${i.enumName}
  					</#if>
	  	</#list>
  	</#if>&nbsp;</td>	    
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
<@fkMacros.searchselectAreas_fun "${trouble?if_exists.firstArea?if_exists}" "${trouble?if_exists.secondArea?if_exists}" "${trouble?if_exists.thirdArea?if_exists}" "${trouble?if_exists.fouthArea?if_exists}" "${trouble?if_exists.fifthArea?if_exists}" "trouble."/>
<#else>
<@fkMacros.searchselectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "trouble."/>
</#if>
</#escape>
<@fkMacros.pageFooter />