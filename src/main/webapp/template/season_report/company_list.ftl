<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>企业列表</th>
  </tr>
</table>
<form action="loadCompanies.xhtml" method="post" name="companyForm" id="companyForm">
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	
	  <tr>
	    <th>单位名称</th>
	    <td width="35%"><input type="text" name="company.companyName" id="companyName" value="${company.companyName}" size="39" maxlength="50"></td>
	    <th>地　　址</th>
	    <td width="38%"><input type="text" name="company.regAddress" id="address" value="${company.regAddress}" size="35" maxlength="50"></td>
	  </tr>
	  <tr>
	    <th>行业部门</th>
	    <td id="td_trade" style="width:300px;" nowrap><select name="company.tradeTypes" id="tradeTypes1" onchange="changeTrade(this.value,1);"><option value="">--请选择--</option><#list tradeTypes?if_exists as t><#if t.type==1><option value="${t.id}" <#if userDetail.userIndustry!='anwei'>selected</#if>><#if userDetail.secondArea!=0 && t.name="贸易局" >商务局 <#else>${t.name} </#if></option></#if></#list></select>
	    &nbsp;<select name="company.tradeTypes" id="tradeTypes2" onchange="changeTrade(this.value,2);"><option value="">--请选择--</option></select>
	    &nbsp;<select name="company.tradeTypes" id="tradeTypes3" onchange=""><option value="">--请选择--</option></select></td>
	    <th>区　　域</th>
	    <td><div id="div-area"></div></td>
	  </tr>
	  	<input type="hidden" id="orderProperty" name="company.orderProperty" value="id"/>
	  	<input type="hidden" id="companyOrBag" name="companyOrBag"value="1"/>
	  	<input type="hidden" id="seasonNumber" name="seasonNumber"value="${seasonNumber}"/>
		<input type="hidden" id="orderType" name="company.orderType"/>
		
	  <tr><!--<th style="width:80px;">规模以上</th>
    <td colspan="3">
    <input type="checkbox" name="company.daCompanyPass.enterprise" onClick="checkedOne(this,'company.daCompanyPass.enterprise');" value="true" <#if company?if_exists.daCompanyPass?if_exists.enterprise?exists><#if company.daCompanyPass.enterprise>checked</#if></#if>> 是　　　　
    <input type="checkbox" name="company.daCompanyPass.enterprise" onClick="checkedOne(this,'company.daCompanyPass.enterprise');" value="false" <#if company?if_exists.daCompanyPass?if_exists.enterprise?exists><#if !company.daCompanyPass.enterprise>checked</#if></#if>> 否-->
	<th colspan="4"><div align="center"><input type="submit" value="搜　索" class="confirm_but" onClick="submitForm('companyForm');"/></div></th></tr>
	</table>
</form>
<div class="menu">
  	<ul>
  	<li id="img_lookup"><a href="#" class="b13" onClick="submitForm('companyForm');"><b>查询</b></a></li>
  	<#if seasonNumber==0>
  	<li id="img_zgtzs"><a href="#" class="img_zgtzs" onClick="loadNote('loadNomalDangers.xhtml?company.id')"><b>添加一般隐患</b></a></li>
  	<#elseif seasonNumber==1>
  	<li id="img_zgtzs"><a href="#" class="img_zgtzs" onClick="loadNote('../danger/loadDangers.xhtml?company.id')"><b>添加重大隐患</b></a></li>
  	<#else>
  	<li id="img_zgtzs"><a href="#" class="img_zgtzs" onClick="loadNote('../company/loadCompanyForLevel.xhtml?company.id')"><b>企业分级分类</b></a></li>
  	</#if>
	<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
</div>
<form action="deleteCompanies.xhtml" method="post" name="companiesForm" id="companiesForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
  	<th  id="" width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th  id="th_id" width="4%" style="cursor:hand;" onClick="orderProperty('id');" nowrap>序号</th>
    <th  id="th_companyName" width="30%" style="cursor:hand;" onClick="orderProperty('companyName');" nowrap>单位名称</th>
    <th  id="th_regAddress" width="30%" style="cursor:hand;" onClick="orderProperty('regAddress');" nowrap>地　　址</th>
    <th  id="th_fddelegate" width="20%" style="cursor:hand;" onClick="orderProperty('fddelegate');" nowrap>法人代表</th>
    <th  id="th_phoneCode" style="cursor:hand" onClick="orderProperty('phoneCode')" nowrap>联系电话</th>
  </tr>
  <#if companies?exists>
  	<#list companies?if_exists as c>
	  <tr>
	  	<td><input id="ids${r.id}" name="ids" type="checkbox" value="${c.id}"/></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+c_index+1}<#else>${c_index+1}</#if></td>
	    <td><div align="left"><a href="../company/loadCompany.xhtml?company.id=${c.id}">${c.companyName}</a></div></td>
	    <td><div align="left">${c.regAddress}&nbsp;</div></td>
	    <td>${c.fddelegate}&nbsp;</td>	    
	    <td>${c.phoneCode}&nbsp;</td>
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
<script type="text/javascript">
<#if userDetail.userIndustry!='anwei'>
	changeTrade(get("tradeTypes1").value,1);
</#if>
	function orderProperty(orderProperty) {
		var orderType = true;
		<#if company?if_exists.orderType?exists>
			orderType = "${company.orderType?string}"=="true"?false:true;
		</#if>
		get("orderType").value = orderType;
		get("orderProperty").value=orderProperty;		
		submitSerach();
	}
	function submitSerach(){
			get('companyForm').submit();
	}
	<#if company?exists>
		<#if company.orderProperty?exists>
			get("th_${company.orderProperty}").innerHTML = "<div align='left'><img src='${contextPath}/resources/default/img/"+(("${company.orderType?string}"=="true")?2:1)+".gif' border='0'/></div>"+get("th_${company.orderProperty}").innerHTML;
		</#if>
	</#if>
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

function submitForm(formName){
	var formObj=get(formName);
	formObj.submit();
}
function createSeasonReport(url){
	var compForm= get("companiesForm");
	compForm.action=url;
	compForm.submit();
	
}
</script>
<@fkMacros.muilt_select_js />
<#if company?has_content>
<@fkMacros.searchselectAreas_fun "${company?if_exists.firstArea?if_exists}" "${company?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${company?if_exists.fouthArea?if_exists}" "${company?if_exists.fifthArea?if_exists}" "company."/>
<#else>
<@fkMacros.searchselectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "company."/>
</#if>
</#escape>
<@fkMacros.pageFooter />
