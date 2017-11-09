<@fkMacros.pageHeader />
<#escape x as (x)!>
<#if danger?if_exists.isGorver?if_exists=='0'>
	<#assign url='loadDangersUnGorver.xhtml'>
	<#assign letter='重大隐患未治理隐患列表'>
<#elseif  danger?if_exists.isGorver?if_exists=='1'>
	<#assign url='loadDangersGorver.xhtml'>
	<#assign letter='重大隐患已治理隐患列表'>
<#elseif  danger?if_exists.isGorver?if_exists=='2'>
	<#assign url='loadDangersTimeOut.xhtml'>
	<#assign letter='重大隐患到期提醒隐患列表'>
<#else>
	<#assign url='loadDangersRollcall.xhtml'>
	<#assign letter='重大隐患挂牌管理列表'>
</#if>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22">${letter}</th>
  </tr>
</table>
<form action="${url}" method="post" name="dangersForm" id="dangersForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
  <tr>
  <th>单位名称</th>
  <td><input type="text" name="company.companyName" size="35" value="${company.companyName}" onBlur="SpacesAll(this);"></td>
  <th>年　　份</th>
    <td><input type="text" id="year" value="${danger.nowYear}年" onfocus="WdatePicker({dateFmt:'yyyy年',vel:'year_2',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
    <input type="hidden" name="danger.nowYear" id="year_2" value="${danger.nowYear}"/></td>
  </tr>
  <tr>
    <th>隐患地址</th>
    <td width="33%"><input type="text" name="danger.dangerAdd" size="35" value="${danger.dangerAdd}" onBlur="SpacesAll(this);"></td>
	<th>单位区域</th>
    <td width="38%"><div id="div-area"></div></td>
  </tr>
  <tr>
  <#if  danger?if_exists.isGorver?if_exists=='2'>
    <th>时间范围</th>
    <td>
    <input type="text" name="danger.startTime" id="startTime" onfocus="WdatePicker();" class="Wdate" value="${danger.startTime?date}" size="12" maxlength="10" onKeyUp="AutoFillDate(this,10);" style="ime-mode:disabled;">　至　
    <input type="text" name="danger.endTime" id="endTime" onfocus="WdatePicker();" class="Wdate" value="${danger.endTime?date}" size="12" maxlength="10" onKeyUp="AutoFillDate(this,10);" style="ime-mode:disabled;"></td>
   <#else>
   	<th>行业部门</th>
	    <td id="td_trade" style="width:300px;" nowrap><select name="company.tradeTypes" id="tradeTypes1" onchange="changeTrade(this.value,1);"><option value="">--请选择--</option><#list tradeTypes?if_exists as t><#if t.type==1><option value="${t.id}" <#if userDetail.userIndustry!='anwei'>selected</#if>><#if userDetail.secondArea!=0 && t.name="贸易局" >商务局 <#else>${t.name} </#if></option></#if></#list></select>
	    &nbsp;<select name="company.tradeTypes" id="tradeTypes2" onchange="changeTrade(this.value,2);"><option value="">--请选择--</option></select>
	    &nbsp;<select name="company.tradeTypes" id="tradeTypes3" onchange=""><option value="">--请选择--</option></select></td>
   </#if>
    <th>是否挂牌</th>
    <td><input type="checkbox" name="danger.isRollcall" onClick="checkedOne(this,'danger.isRollcall');" value="1" <#if danger?if_exists.isRollcall?exists><#if danger.isRollcall=='1'>checked</#if></#if>> 是　　　　
    <input type="checkbox" name="danger.isRollcall" onClick="checkedOne(this,'danger.isRollcall');" value="0" <#if danger?if_exists.isRollcall?exists><#if danger.isRollcall=='0'>checked</#if></#if>> 否　　　　　
    </td>
  </tr>
  <#if letter='重大隐患已治理隐患列表'>
  <tr>
    <th>落实资金(万元)</th>
    <td width="33%" colspan=3><input type="text" name="danger.governMoney1" id="governMoney1" value="<#if danger?if_exists.governMoney1?exists  &&  danger.governMoney1!=0>${danger.governMoney1}</#if>" size="15"> -- <input type="text" name="danger.governMoney2" id="governMoney2" value="<#if danger?if_exists.governMoney2?exists  && danger.governMoney2!=0>${danger.governMoney2}</#if>" size="15"></td>
  </tr>
  </#if >
  
  
  <tr><th colspan="4"><div align="center"><input type="button" id="sub" value="搜　索" class="confirm_but" style="height:25px; width:80px" onClick="javascript:submitForm('dangersForm');"/></div></th></tr>
</table>
</form>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<#if danger?if_exists.isGorver?exists>
	<li id="img_amend"><a href="javascript:loadNote('loadDanger.xhtml?danger.id');" class="b2"><b>修改</b></a></li>
	<#if danger.isGorver=='0'>
	<li id="img_del"><a href="javascript:deleteNote(get('dangersFormForDelete'));" class="b3"><b>删除</b></a></li>
	</#if>
	<#if danger.isGorver=='1'>
	<li id="img_zgtzs"><a href="javascript:loadNote('../dangerGorver/createDangerGorverInit.xhtml?dangerGorver.daDanger.id');" class="b_zgtzs"><b>查看整改信息</b></a></li>
    <#else>
    	<#if !viewRole?? || !viewRole>
		<li id="img_report"><a href="javascript:loadNote('../dangerGorver/createDangerGorverInit.xhtml?dangerGorver.daDanger.id');" class="b7"><b>整改</b></a></li>
		</#if>
	</#if>
	
	<#if danger.hc?? &&danger.hc==1>
	<li id="img_004"><a href="javascript:loadNote('../dangerGorver/checkDangerGorverInit.xhtml?dangerGorver.daDanger.id');" class="b_004"><b>隐患核查</b></a></li>	
	</#if>
	
	
	</#if>
	<#if !danger?if_exists.isGorver?exists>
		<#if !viewRole?? || !viewRole>
			<li id="img_amend"><a href="javascript:loadNote('../rollcallCompany/createRollcallCompanyInit.xhtml?rollcallCompany.daDanger.id');" class="b2"><b>挂牌</b></a></li>
		</#if>
	</#if>
	<li id="img_refurbish"><a href="javascript:window.location.reload();" class="b4"><b>刷新</b></a></li>	
	<li id="img_lookup"><a href="javascript:submitForm('dangersForm');" class="b13"><b>查询</b></a></li>
	</ul>
	</div>
</div>
<form action="deleteDangers.xhtml" method="post" name="dangersFormForDelete" id="dangersFormForDelete">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="3%" nowrap><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th width="3%" nowrap>序号</th>
    <th width="29%" nowrap>单位名称</th>
    <th width="11%" nowrap>隐患编号</th>
    <th width="18%" nowrap>隐患地址</th>
    <th width="6%" nowrap>是否挂牌</th>
    <th width="7%" nowrap>联系人</th>
    <th width="11%" nowrap>联系电话</th>
  </tr>
  <#if dangers?exists>
  <#list dangers?if_exists as s>
  <tr>
  	<td><input type="checkbox" name="ids" id="ids${s.id}" value="${s.id}"></td>
    <td nowrap><#if pagination.itemCount?exists>${pagination.itemCount+s_index+1}<#else>${s_index+1}</#if></td>
    <td nowrap id="roleName"><div align="left"><a href="../company/loadCompany.xhtml?company.id=${s.daCompanyPass.id}">${s.daCompanyPass.daCompany.companyName}</a>&nbsp;</div></td>
    <td nowrap><a href="loadDanger.xhtml?danger.id=${s.id}">${s.dangerNo}</a>&nbsp;</td>
    <td nowrap><div align="left">${s.dangerAdd}&nbsp;</div></td>
    <td nowrap><#if (s.daRollcallCompanies?size>0)>是<#else>否</#if></td>
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
		<#if (dangers?if_exists?size>0)>
			<@p.navigation pagination=pagination/>
		<#else>
			此处暂时没有记录！
		</#if>
		</td>
	</tr>
</table>
<script>
<#if userDetail.userIndustry!='anwei'>
	changeTrade(get("tradeTypes1").value,1);
</#if>
function submitForm(formName){
	var formObj=get(formName);
	get("year_2").value=get("year_2").value.substring(0,4);
	formObj.submit();
}
function changeTrade(tradeId, type) {
	var length = 1;
	if(type == 1) {
		cleanSelect(get("tradeTypes2"));
		cleanSelect(get("tradeTypes3"));
	} else if (type == 2){
		cleanSelect(get("tradeTypes3"));
	}
	<#list tradeTypes?if_exists as t>
		<#list t.daIndustryParameters?if_exists as st>
			if (type == 1 && tradeId == "${t.id}" && "${st.type}" == 1) {
				var opt = new Option("${st.name}", "${st.id}");
				get("tradeTypes2").options[length] = opt;
				opt = null;
				length ++;
			}
			<#list st.daIndustryParameters?if_exists as sst>
				if (type == 2 && tradeId == "${st.id}" && "${sst.type}" == 1) {
					var opt = new Option("${sst.name}", "${sst.id}");
					get("tradeTypes3").options[length] = opt;
					opt = null;
					length ++;
				}
			</#list>
		</#list>
	</#list>
}
<#if company?exists>
	<#if company.tradeTypes?exists>
		var trades = "${company.tradeTypes}";
		getName("company.tradeTypes")[getName("company.tradeTypes").length-1].value = trades;
		for (var i=0;i<trades.split(",").length;i++) {
			if (trim(trades.split(",")[i]) != "") {
				get("tradeTypes"+(i+1)).value = trim(trades.split(",")[i]);
				changeTrade(trim(trades.split(",")[i]), (i+1));
			}
		}
	</#if>
</#if>
</script>
<@fkMacros.muilt_select_js />
<#if company?has_content>
<@fkMacros.searchselectAreas_fun "${company?if_exists.firstArea?if_exists}" "${company?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${company?if_exists.fouthArea?if_exists}" "${company?if_exists.fifthArea?if_exists}" "company."/>
<#else>
<@fkMacros.searchselectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "company."/>
</#if>
<script>
 roleName("roleName");
</script>
</#escape>
<@fkMacros.pageFooter />