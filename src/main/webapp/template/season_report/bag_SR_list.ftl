<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>企业列表</th>
  </tr>
</table>
<form action="loadCompanies.xhtml?companyOrBag=2" method="post" name="companyForm" id="companyForm">
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	  <tr>
	    <th style="width:75px;">单位名称：</th>
	    <td style="width:300px;"><input type="text" name="bag.name" id="name" value="${bag.name}" size="40" maxlength="50"></td>
	    <th style="width:75px;">地址：</th>
	    <td style="width:300px;"><input type="text" name="bag.regAddress" id="address" value="${bag.regAddress}" size="50" maxlength="50"></td>
	  </tr>
	  <tr>	   
	    <th style="width:75px;">行业：</th>
	    <td id="td_trade" style="width:300px;" nowrap><select name="company.tradeTypes" id="tradeTypes1" onchange="changeTrade(this.value,1);"><option value="">--请选择--</option><#list tradeTypes?if_exists as t><#if t.type==1><option value="${t.id}">${t.name}</option></#if></#list></select>
	    &nbsp;<select name="company.tradeTypes" id="tradeTypes2" onchange="changeTrade(this.value,2);"><option value="">--请选择--</option></select>
	    &nbsp;<select name="company.tradeTypes" id="tradeTypes3" onchange=""><option value="">--请选择--</option></select></td>
	    <th style="width:75px;">区域：</th>
	    <td><div id="div-area" colspan=3></div></td>
	  </tr>
	  	<input type="hidden" id="orderProperty" name="bag.orderProperty" value="id"/>
	  	<input type="hidden" id="orderType" name="bag.orderType"/>
	</table>
</form>
<div class="menu">
  	<ul>
  	<li id="img_refurbish"> <a href="#" class="b7" onClick="submitForm('companyForm');"><b>搜索</b></a></li>
  	<li id="img_yinhuan">   <a href="#" class="b_yinhuan" onClick="loadNote('loadNomalDangers.xhtml?bag.id')"><b>添加隐患</b></a></li>
	<li id="img_refurbish">	<a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return">	<a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
</div>
<form action="deleteCompanies.xhtml" method="post" name="companiesForm" id="companiesForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
  	<th  id="" width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('compIds'));"></th>
    <th  id="th_id" width="4%" style="cursor:hand;" onClick="orderProperty('id');" nowrap>序号</th>
    <th  id="th_companyName" width="30%" style="cursor:hand;" onClick="orderProperty('companyName');" nowrap>单位名称</th>
    <th  id="th_regAddress" width="30%" style="cursor:hand;" onClick="orderProperty('regAddress');" nowrap>地址</th>
    <th  id="th_fddelegate" width="20%" style="cursor:hand;" onClick="orderProperty('fddelegate');" nowrap>法人代表</th>
    <th  id="th_phoneCode" style="cursor:hand" onClick="orderProperty('phoneCode')" nowrap>联系电话</th>
  </tr>
  <#if bags?exists>
  	<#list bags?if_exists as c>
	  <tr>
	  	<td><input id="compIds" name="company.id" type="checkbox" value="${c.id}"/></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+c_index+1}<#else>${c_index+1}</#if></td>
	    <td><div align="left"><a href="../company/loadCompany.xhtml?company.id=${c.id}">${c.name}</a></div></td>
	    <td><div align="left">${c.regAddress}&nbsp;</div></td>
	    <td>${c.fddelegate}&nbsp;</td>	    
	    <td>${c.phoneCode}&nbsp;</td>
	   <!-- <td><div align="left"><#if c.daCompanyPass?if_exists.daDangers?if_exists?size!=0><a href="../danger/loadDangers.xhtml?danger.daCompanyPass.id=${c.id}">${c.daCompanyPass.daDangers?size}</a></#if> <a href="../danger/createDangerInit.xhtml?danger.daCompanyPass.id=${c.id}">添加</a></div></td>-->
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
	function orderProperty(orderProperty) {
		var orderType = true;
		<#if bag?if_exists.orderType?exists>
			orderType = "${bag.orderType?string}"=="true"?false:true;
		</#if>
		get("orderType").value = orderType;
		get("orderProperty").value=orderProperty;		
		submitSerach();
	}
	function submitSerach(){
			get('companyForm').submit();
	}
	<#if bag?exists>
		<#if bag.orderProperty?exists>
			get("th_${bag.orderProperty}").innerHTML = "<div align='left'><img src='${contextPath}/resources/default/img/"+(("${bag.orderType?string}"=="true")?2:1)+".gif' border='0'/></div>"+get("th_${bag.orderProperty}").innerHTML;
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
</script>
<@fkMacros.muilt_select_js />
<#if bag?has_content>
<@fkMacros.selectAreas_fun "${bag?if_exists.firstArea?if_exists}" "${bag?if_exists.secondArea?if_exists}" "${bag?if_exists.thirdArea?if_exists}" "${bag?if_exists.fouthArea?if_exists}" "${bag?if_exists.fifthArea?if_exists}" "bag."/>
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "bag."/>
</#if>
</#escape>
<@fkMacros.pageFooter />