<@fkMacros.pageHeader />
<#escape x as (x)!>
<#if rollcallCompany?if_exists.level?if_exists=='province_city'>
	<#assign letter='省级重大隐患挂牌管理列表'>
<#elseif  rollcallCompany?if_exists.level?if_exists=='city_city'>
	<#assign letter='市级重大隐患挂牌管理列表'>
<#elseif  rollcallCompany?if_exists.level?if_exists=='county_county'>
	<#assign letter='县级重大隐患挂牌管理列表'>
<#elseif  rollcallCompany?if_exists.level?if_exists=='town_town'>
	<#assign letter='乡镇重大隐患挂牌管理列表'>
<#else>
	<#assign letter='重大隐患挂牌管理列表'>
</#if>
<script type="text/javascript">
var enumObj=new Enum("${enumXmlUrl}");
var areaObj = new Area("${AreaXmlUrl}");
</script>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22">${letter}</th>
  </tr>
</table>
<form action="loadRollcallCompanies.xhtml" method="post" name="rollcallCompanysForm" id="rollcallCompanysForm">
<input type="hidden" name="rollcallCompany.level" value="${rollcallCompany.level}" id="level"/>
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
  <tr>
  <th>单位名称</th>
  <td><input type="text" name="company.companyName" size="35" value="${company.companyName}" onBlur="SpacesAll(this);"></td>
  <th>年　　份</th>
    <td><input type="text" id="year" value="${rollcallCompany.nowYear}年" onfocus="WdatePicker({dateFmt:'yyyy年',vel:'year_2',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
    <input type="hidden" name="rollcallCompany.nowYear" id="year_2" value="${rollcallCompany.nowYear}"/></td>
  </tr>
  <tr>
    <th>隐患地址</th>
    <td width="35%"><input type="text" name="rollcallCompany.daDanger.dangerAdd" size="35" value="${rollcallCompany.daDanger.dangerAdd}" onBlur="SpacesAll(this);"></td>
	<th>单位区域</th>
    <td width="38%"><div id="div-area"></div></td>
  </tr>
  <tr><th colspan="4"><div align="center"><input type="button" id="sub" value="搜　索" class="confirm_but" style="height:25px; width:80px" onClick="javascript:submitForm('rollcallCompanysForm');"/></div></th></tr>
</table>
</form>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<#if rollcallCompany?exists>
		<#if !viewRole?? || !viewRole>
			<li id="img_amend"><a href="javascript:loadNote('loadRollcallCompany.xhtml?rollcallCompany.id');" class="b2"><b>修改</b></a></li>
		</#if>
		<li id="img_zgtzs"><a href="javascript:loadNote('createRollcallCompanyForNoticeInit.xhtml?rollcallCompany.id');" class="img_zgtzs"><b>发送通知书</b></a></li>
		<li id="img_xcjcjl"><a href="javascript:loadNote('../file/loadFileList.xhtml?type=1&rollcallCompany.id');" class="b_xcjcjl"><b>督办方案</b></a></li>
		<li id="img_xcjcjl"><a href="javascript:loadNote('../file/loadFileList.xhtml?type=2&rollcallCompany.id');" class="b_xcjcjl"><b>治理方案</b></a></li>
		<li id="img_xcjcjl"><a href="javascript:loadNote('../file/loadFileList.xhtml?type=3&rollcallCompany.id');" class="b_xcjcjl"><b>督办记录</b></a></li>
		<li id="img_zgtzs"><a href="javascript:loadNote('../rollcallDefer/createRollcallDeferInit.xhtml?rollcallCompany.id');" class="img_zgtzs"><b>企业延期申请</b></a></li>
		<li id="img_zgtzs"><a href="javascript:loadNote('../rollcallDefer/loadRollcallDeferForPass.xhtml?rollcallCompany.id');" class="img_zgtzs"><b>延期治理审批</b></a></li>
		<li id="img_xcjcjl"><a href="javascript:loadNote('../rollcallDefer/loadRollcallDeferAgrees.xhtml?rollcallCompany.id');" class="b_xcjcjl"><b>验收申请</b></a></li>
		<li id="img_xcjcjl"><a href="javascript:loadNote('../rollcallDefer/loadRollcallDeferAgreePasses.xhtml?rollcallCompany.id');" class="b_xcjcjl"><b>验收审批</b></a></li>
		<!--<li id="img_xcjcjl"><a href="javascript:deleteNote(get('rollcallCompanyFormForDelete'));" class="b_xcjcjl" ><b>取消挂牌</b></a></li>-->
	<#else>
		<li id="img_zgtzs"><a href="javascript:loadNote('loadRollcallCompanyForNotice.xhtml?rollcallCompany.id');" class="img_zgtzs"><b>查看通知书</b></a></li>
		<li id="img_xcjcjl"><a href="javascript:loadNote('../file/loadFileList.xhtml?type=1&rollcallCompany.id');" class="b_xcjcjl"><b>督办方案</b></a></li>
		<li id="img_xcjcjl"><a href="javascript:loadNote('../file/loadFileList.xhtml?type=2&rollcallCompany.id');" class="b_xcjcjl"><b>治理方案</b></a></li>
		<li id="img_xcjcjl"><a href="javascript:loadNote('../file/loadFileList.xhtml?type=3&rollcallCompany.id');" class="b_xcjcjl"><b>督办记录</b></a></li>
		<li id="img_zgtzs"><a href="javascript:loadNote('../rollcallDefer/createRollcallDeferCha.xhtml?rollcallCompany.id');" class="img_zgtzs"><b>企业延期申请</b></a></li>
		<li id="img_zgtzs"><a href="javascript:loadNote('../rollcallDefer/loadRollcallDeferForPassCha.xhtml?rollcallCompany.id');" class="img_zgtzs"><b>延期治理审批</b></a></li>
		<li id="img_xcjcjl"><a href="javascript:loadNote('../rollcallDefer/loadRollcallDeferAgreesCha.xhtml?rollcallCompany.id');" class="b_xcjcjl"><b>验收申请</b></a></li>
		<li id="img_xcjcjl"><a href="javascript:loadNote('../rollcallDefer/loadRollcallDeferAgreePassesCha.xhtml?rollcallCompany.id');" class="b_xcjcjl"><b>验收审批</b></a></li>
	</#if>
	<!--
	<li id="img_refurbish"><a href="javascript:window.location.reload();" class="b4"><b>刷新</b></a></li>	
	<li id="img_lookup"><a href="javascript:get('rollcallCompanysForm').submit();" class="b13"><b>查询</b></a></li>
	-->
	</ul>
	</div>
</div>
<form action="deleteRollcallCompany.xhtml" method="post" name="rollcallCompanyFormForDelete" id="rollcallCompanyFormForDelete">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="3%" nowrap><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th width="3%" nowrap>序号</th>
    <th width="24%" nowrap>单位名称</th>
    <th width="9%" nowrap>隐患编号</th>
    <th width="18%" nowrap>隐患地址</th>
    <th width="6%" nowrap>是否整改</th>
    <th width="7%" nowrap>挂牌级别</th>
    <th width="9%" nowrap>督办单位</th>
    <th width="9%" nowrap>督办部门</th>
    <th width="9%" nowrap>督办完成时间</th>
  </tr>
  <#if rollcallCompanies?exists>
  <#list rollcallCompanies?if_exists as r>
  <tr>
  	<td><input type="checkbox" name="ids" id="ids${r.id}" value="${r.id}"></td>
    <td nowrap><#if pagination.itemCount?exists>${pagination.itemCount+r_index+1}<#else>${r_index+1}</#if></td>
    <td id="roleName" name="roleName"><div align="left"><a href="../company/loadCompany.xhtml?company.id=${r.daDanger.daCompanyPass.id}">${r.daDanger.daCompanyPass.daCompany.companyName}</a>&nbsp;</div></td>
    <td nowrap><a href="../danger/loadDanger.xhtml?danger.id=${r.daDanger.id}">${r.daDanger.dangerNo}</a>&nbsp;</td>
    <td><div align="left">${r.daDanger.dangerAdd}&nbsp;</div></td>
    <td nowrap><#if (r.daDanger?if_exists.daDangerGorvers?size>0)>是<#else>否</#if></td>
    <td><#if r.level?exists><script type="text/javascript">document.write(enumObj.getSelect("${r.level}"));</script></#if>挂牌</td>
    <td><#if r.govment?exists><script type="text/javascript">document.write(areaObj.getArea("${r.govment}")[0]);</script></#if>&nbsp;</td>
    <td><div align="left"><#if r.department?exists>${r.department}</#if>&nbsp;</div></td>
    <td>${r.completeTime?date}&nbsp;</td>
  </tr>
  </#list>
  </#if>
</table>
</form>
<table width="98%" cellpadding="0" cellspacing="1" >
	<tr>
		<td align="center">
		<#if (rollcallCompanies?size>0)>
			<@p.navigation pagination=pagination/>
		<#else>
			此处暂时没有记录！
		</#if>
		</td>
	</tr>
</table>
<@fkMacros.muilt_select_js />
<#if rollcallCompany?has_content>
<@fkMacros.searchselectAreas_fun "${company?if_exists.firstArea?if_exists}" "${company?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${company?if_exists.fouthArea?if_exists}" "${company?if_exists.fifthArea?if_exists}" "company."/>
<#else>
<@fkMacros.searchselectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "company."/>
</#if>
<script>
function submitForm(formName){
	var formObj=get(formName);
	get("year_2").value=get("year_2").value.substring(0,4);
	formObj.submit();
}

 roleName("roleName");
 function alertErr(){
 	alert(arguments[0]);
 }
</script>
</#escape>
<@fkMacros.pageFooter />