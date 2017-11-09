<@fkMacros.pageHeader />
<#escape x as (x)!>
  <#if reportParams?exists && reportParams?size gt 0>
  	<#assign url='updateReportParam.xhtml'>
  <#else>
  	<#assign url='createReportParam.xhtml'>
  </#if>
<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
  		  var obj=get("reportParamForm");
  		  obj.action="${url}";
 		  obj.submit();
 	}
}
</script>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_save"><a href="javascript:submitCreate();" class="b1"><b>保存</b></a></li>
	<li id="img_refurbish"><a href="javascript:window.location.reload();" class="b4"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.back(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div>
<@fkMacros.formValidator 'reportParamForm' />
<form id="reportParamForm" name="reportParamForm" method="post" action="">
<table width="98%" cellpadding="0" cellspacing="0" class="table_list">
  <tr>
	<th width="3%" nowrap>序号</th>
    <th width="20%" nowrap>单位名称</th>
    <th width="20%" nowrap>单位负责人</th>
    <th width="20%" nowrap>填表人</th>
    <th width="20%" nowrap>联系电话</th>
  </tr>
  <#if reportParams?exists && reportParams?size gt 0>
	  <#list areas?if_exists as a>
		  <#list reportParams?if_exists as r>
			  <#if a.areaCode==r.areaCode>
				  <tr>
					<td>${a_index+1}</td>
					<td>${a.areaName}安全生产监督管理局<input type="hidden" name="reportParams[${a_index}].areaCode" value="${a.areaCode}" id="areaCode_${a_index}"/></td>
					<td ><div align="left"><input name="reportParams[${a_index}].chargeMan" id="chargeMan_${a_index}" type="text" value="${r.chargeMan}" size="16" maxlength="10">
					<span class="red_point">*</span><ui:v for="chargeMan_${a_index}" rule="require" empty="&nbsp;" pass="&nbsp;" /></div></td>
					<td><div align="left"><input name="reportParams[${a_index}].fillMan" id="fillMan_${a_index}" type="text" value="${r.fillMan}" size="16" maxlength="10">
					<span class="red_point">*</span><ui:v for="fillMan_${a_index}" rule="require" empty="&nbsp;" pass="&nbsp;" /></div></td>
					<td><div align="left"><input name="reportParams[${a_index}].tel" id="tel_${a_index}" type="text" value="${r.tel}" size="16" maxlength="13" style="ime-mode:disabled">
					<span class="red_point">*</span><ui:v for="tel_${a_index}" rule="phone_mobile" empty="&nbsp;" pass="&nbsp;"  warn="&nbsp;"/></div></td>
				  </tr>
			  </#if>
		  </#list>
	  </#list>
  <#else>
	  <#list areas?if_exists as a>
		  <tr>
			<td>${a_index+1}</td>
			<td>${a.areaName}安全生产监督管理局<input type="hidden" name="reportParams[${a_index}].areaCode" value="${a.areaCode}" id="areaCode_${a_index}"/></td>
			<td><div align="left"><input name="reportParams[${a_index}].chargeMan" id="chargeMan_${a_index}" type="text" size="16" maxlength="10">
			<span class="red_point">*</span><ui:v for="chargeMan_${a_index}" rule="require" empty="&nbsp;" pass="&nbsp;" /></div></td>
			<td><div align="left"><input name="reportParams[${a_index}].fillMan" id="fillMan_${a_index}" type="text" size="16" maxlength="10">
			<span class="red_point">*</span><ui:v for="fillMan_${a_index}" rule="require" empty="&nbsp;" pass="&nbsp;" /></div></td>
			<td><div align="left"><input name="reportParams[${a_index}].tel" id="tel_${a_index}" type="text" size="16" maxlength="13" style="ime-mode:disabled">
			<span class="red_point">*</span><ui:v for="tel_${a_index}" rule="phone_mobile" empty="&nbsp;" pass="&nbsp;"  warn="&nbsp;"/></div></td>
		  </tr>
	  </#list>
  </#if>
</table>
</form>
</#escape>
<@fkMacros.pageFooter />