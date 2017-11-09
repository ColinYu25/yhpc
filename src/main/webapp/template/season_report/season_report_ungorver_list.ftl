<@fkMacros.pageHeader />
<#escape x as (x)!> 
<body>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22" background="">一般隐患未治理列表</th>
  </tr>
</table>
<form action="loadSeasonReportsUnGorver.xhtml" method="post" name="companyForm" id="companyForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
  <tr>
    <th>单位名称</th>
    <td width="33%"><input type="text" name="company.companyName" id="companyName" value="${company.companyName}" size="35"></td>
	<th>单位区域</th>
    <td width="38%"><div id="div-area"></div></td>
  </tr>
  <tr>
    <th>行业部门</th>
	    <td id="td_trade" style="width:300px;" nowrap><select name="company.tradeTypes" id="tradeTypes1" onchange="changeTrade(this.value,1);"><option value="">--请选择--</option><#list tradeTypes?if_exists as t><#if t.type==1><option value="${t.id}" <#if userDetail.userIndustry!='anwei'>selected</#if>><#if userDetail.secondArea!=0 && t.name="贸易局" >商务局 <#else>${t.name} </#if></option></#if></#list></select>
	    &nbsp;<select name="company.tradeTypes" id="tradeTypes2" onchange="changeTrade(this.value,2);"><option value="">--请选择--</option></select>
	    &nbsp;<select name="company.tradeTypes" id="tradeTypes3" onchange=""><option value="">--请选择--</option></select></td>
    <th>年　　份</th>
    <td>
  	<input type="text" id="year" value="${daNomalDanger.nowYear}年" onfocus="WdatePicker({dateFmt:'yyyy年',vel:'year_2',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
    <input type="hidden" name="daNomalDanger.nowYear" id="year_2" value="${daNomalDanger.nowYear}"/>
    </td>
   </tr>
   <tr>
    <th>落实资金(万元)</th>
    <td width="33%" colspan=3><input type="text" name="daNomalDanger.governMoney1" id="governMoney1" value="<#if daNomalDanger.governMoney1!=0>${daNomalDanger.governMoney1}</#if>" size="15"> -- <input type="text" name="daNomalDanger.governMoney2" id="governMoney2" value="<#if daNomalDanger.governMoney2!=0>${daNomalDanger.governMoney2}</#if>" size="15"></td>
  </tr>
</table>
</form>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_amend"><a href="javascript:loadNote('loadNomalDangersUnGorver.xhtml?daNomalDanger.nowYear=${daNomalDanger.nowYear}&company.id');" class="b2"><b>修改</b></a></li>
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
    <th width="6%" nowrap>未治理（个）</th>
    <th width="7%" nowrap>联系人</th>
    <th width="11%" nowrap>联系电话</th>
  </tr>
  <#if companies?exists>
	  <#list companies?if_exists as s>
		  <tr>
		    <td><input type="checkbox" name="ids" id="ids${s.id}" value="${s.id}"></td>
		    <td nowrap><#if pagination.itemCount?exists>${pagination.itemCount+s_index+1}<#else>${s_index+1}</#if></td>
		    <td nowrap id="roleName" name="roleName"><div align="left"><a href="loadNomalDangersUnGorver.xhtml?company.id=${s.id}&daNomalDanger.nowYear=${daNomalDanger.nowYear}">${s.companyName}</a>&nbsp;</div></td>
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
</#escape> 
<@fkMacros.pageFooter />