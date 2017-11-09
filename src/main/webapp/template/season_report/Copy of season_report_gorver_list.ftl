<@fkMacros.pageHeader />
<#escape x as (x)!> 
<body>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22" background="">一般隐患已治理列表</th>
  </tr>
</table>
<form action="loadSeasonReportsGorver.xhtml" method="post" name="companyForm" id="companyForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
  <tr>
    <th>单位名称</th>
    <td width="33%"><input type="text" name="company.companyName" id="companyName" value="${company.companyName}" size="35"></td>
	<th>单位区域</th>
    <td width="38%"><div id="div-area"></div></td>
  </tr>
  <tr>
    <th>年　　份</th>
    <td colspan="3">
  	<input type="text" id="year" value="${daNomalDanger.nowYear}年" onfocus="WdatePicker({dateFmt:'yyyy年',vel:'year_2',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
    <input type="hidden" name="daNomalDanger.nowYear" id="year_2" value="${daNomalDanger.nowYear}"/>
    </td>
   </tr>
</table>
</form>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_amend"><a href="javascript:loadNote('loadNomalDangers.xhtml?repair=1&company.id');" class="b2"><b>修改</b></a></li>
	<li id="img_refurbish"><a href="javascript:window.location.reload();" class="b4"><b>刷新</b></a></li>	
	<li id="img_lookup"><a href="javascript:submitForm('companyForm');" class="b13"><b>查询</b></a></li>
	</ul>
	</div>
	
</div> 
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="3%" nowrap><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th width="3%" nowrap>序号</th>
    <th width="29%" nowrap>单位名称</th>
    <th width="6%" nowrap>已治理（个）</th>
    <th width="7%" nowrap>联系人</th>
    <th width="11%" nowrap>联系电话</th>
  </tr>
  <#if companies?exists>
	  <#list companies?if_exists as s>
	  <tr>
	    <td><input type="checkbox" name="ids" id="ids${s.id}" value="${s.id}"></td>
	    <td nowrap><#if pagination.itemCount?exists>${pagination.itemCount+s_index+1}<#else>${s_index+1}</#if></td>
	    <td nowrap id="roleName" name="roleName"><div align="left"><a href="loadNomalDangersGorver.xhtml?company.id=${s.id}&daNomalDanger.nowYear=${daNomalDanger.nowYear}">${s.companyName}</a>&nbsp;</div></td>
	    <td nowrap>${s.notRepairCount}&nbsp;</td>
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
<script>
function submitForm(formName){
	var formObj=get(formName);
	get("year_2").value=get("year_2").value.substring(0,4);
	formObj.submit();
}
</script>
<@fkMacros.muilt_select_js />
<#if company?has_content>
<@fkMacros.selectAreas_fun "${company?if_exists.firstArea?if_exists}" "${company?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${company?if_exists.fouthArea?if_exists}" "${company?if_exists.fifthArea?if_exists}" "company." />
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "company."/>
</#if>
</#escape> 
<@fkMacros.pageFooter />