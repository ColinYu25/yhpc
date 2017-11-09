<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>企业列表</th>
  </tr>
</table>
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	<form action="loadCompanies.xhtml" method="post" name="companyForm" id="companyForm">
	  <tr>
	    <th style="width:80px;">单位名称</th>
	    <td style="width:300px;"><input type="text" name="company.companyName" id="companyName" value="${company.companyName}" size="40" maxlength="50"></td>
	    <th style="width:80px;">地　　址</th>
	    <td style="width:300px;"><input type="text" name="company.regAddress" id="address" value="${company.regAddress}" size="50" maxlength="50"></td>
	  </tr>
	  <tr>
	    <th style="width:80px;">行业部门</th>
	    <td id="td_trade" style="width:300px;" nowrap><select name="company.tradeTypes" id="tradeTypes1" onchange="changeTrade(this.value,1);"><option value="">--请选择--</option><#list tradeTypes?if_exists as t><#if t.type==1><option value="${t.id}" <#if userDetail.userIndustry!='anwei'>selected</#if>>${t.name}</option></#if></#list></select>
	    &nbsp;<select name="company.tradeTypes" id="tradeTypes2" onchange="changeTrade(this.value,2);"><option value="">--请选择--</option></select>
	    &nbsp;<select name="company.tradeTypes" id="tradeTypes3" onchange=""><option value="">--请选择--</option></select></td>
	    <th style="width:80px;">区　　域</th>
	    <td><div id="div-area"></div></td>
	  </tr>
	  	<input type="hidden" id="orderProperty" name="company.orderProperty" value="id"/>
		<input type="hidden" id="orderType" name="company.orderType"/>
		</form>
	  <tr>
<td colspan="4"><div align="center"><input type="submit" id="sub" value="搜　索" onClick="submitForm('companyForm');"/></div></td></tr>
	</table>

<div class="menu">
  	<ul>
  	<li id="img_refurbish"><a href="#" class="b7" onClick="submitForm('companyForm');"><b>查询</b></a></li>
  	<li id="img_zgtzs"><a href="#" class="img_zgtzs" onClick="loadNote('loadRollcallCompaniesByCompany.xhtml?company.id')"><b>查看督办记录</b></a></li>
	<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
</div>
<form action="deleteCompanies.xhtml" method="post" name="companiesForm" id="companiesForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
  	<th  id="" width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('compIds'));"></th>
    <th  id="th_id" width="4%" style="cursor:hand;" onClick="orderProperty('id');" nowrap>序号</th>
    <th  id="th_companyName" width="30%" style="cursor:hand;" onClick="orderProperty('companyName');" nowrap>单位名称</th>
    <th  id="th_regAddress" width="30%" style="cursor:hand;" onClick="orderProperty('regAddress');" nowrap>地　　址</th>
    <th  id="th_fddelegate" width="20%" style="cursor:hand;" onClick="orderProperty('fddelegate');" nowrap>法人代表</th>
    <th  id="th_phoneCode" style="cursor:hand" onClick="orderProperty('phoneCode')" nowrap>联系电话</th>
  </tr>
  <#if companies?exists>
  	<#list companies?if_exists as c>
	  <tr>
	  	<td><input id="compIds" name="company.id" type="checkbox" value="${c.id}"/></td>
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
<@fkMacros.selectAreas_fun "${company?if_exists.firstArea?if_exists}" "${company?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${company?if_exists.fouthArea?if_exists}" "${company?if_exists.fifthArea?if_exists}" "company."/>
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "company."/>
</#if>
</#escape>
<@fkMacros.pageFooter />