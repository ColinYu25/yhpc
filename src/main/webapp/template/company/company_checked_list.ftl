<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>已确认企业列表</th>
  </tr>
</table>
<form action="loadCheckedCompanies.xhtml" method="post" name="companyForm" id="companyForm">
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	  <tr>
	    <th style="width:75px;">单位名称：</th>
	    <td style="width:300px;"><input type="text" name="company.companyName" id="companyName" size="40" maxlength="50"></td>
	    <th style="width:75px;">地址：</th>
	    <td style="width:300px;"><input type="text" name="company.address" id="address" size="50" maxlength="50"></td>
	  </tr>
	  <tr>
	    <th style="width:75px;">区域：</th>
	    <td><div id="div-area"></div></td>
	    <td colspan="2"><input type="submit" id="sub" value="查  询" onClick="get('companyForm').submit();" style="input_submit"/></td>
	  </tr>
	</table>
</form>
<div class="menu">
  	<ul>
	<li id="img_amend">		<a href="#" class="b2" onClick="loadNote('loadCompany.xhtml?company.id');"><b>修改</b></a></li>
	<li id="img_xcjcjl">		<a href="#" class="b_xcjcjl" onClick="delCompanyAcount()"><b>取消确认</b></a></li>
	<li id="img_refurbish">	<a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return">	<a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
</div>
<form action="deleteCompanies.xhtml" method="post" name="companiesForm" id="companiesForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th id="th_id" width="4%" style="cursor:hand;" onClick="orderProperty('id');" nowrap>序号</th>
    <th id="th_companyName" width="28%" style="cursor:hand;" onClick="orderProperty('companyName');" nowrap>单位名称</th>
    <th id="th_address" width="25%" style="cursor:hand;" onClick="orderProperty('address');" nowrap>地址</th>
    <th id="th_fdDelegate" width="8%" style="cursor:hand;" onClick="orderProperty('fdDelegate');" nowrap>法人代表</th>
    <th id="th_phone" width="12%" style="cursor:hand;" onClick="orderProperty('phone');" nowrap>联系电话</th>
    <!--<th width="18%">所属行业</th>-->
  </tr>
  <#if companies?exists>
  	<#list companies?if_exists as c>
	  <tr>
	    <td><input type="checkbox" name="ids" id="ids${c.id}" value="${c.id}"></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+c_index+1}<#else>${c_index+1}</#if></td>
	    <td><div align="left"><a href="loadCompany.xhtml?company.id=${c.id}">${c.companyName}</a>&nbsp;&nbsp;<#if c.daCompanyAcounts?size!=0><a href="loadCompanyAcounts.xhtml?company.id=${c.id}"><font color='red'>异地</font></a></#if></div></td>
	    <td><div align="left">${c.regAddress}&nbsp;</div></td>
	    <td>${c.fddelegate}&nbsp;</td>
	    <td>${c.phoneCode}&nbsp;</td>
	    <!--<td><#list c.hzTradeTypes?if_exists as ct><#list tradeTypes?if_exists as t><#if t.id==ct.id>${ct.tradeName} </#if></#list></#list>&nbsp;</td>-->
	    <!--<td><#list c.hzTradeTypes?if_exists as ct>${ct.tradeName} </#list>&nbsp;</td>-->
	  <!--  <td><#list tradeTypes?if_exists as t><#list c.hzTradeTypes?if_exists as ct><#if t==ct><!-- ${ct.tradeName}--><!--</#if></#list><#list t.hzTradeTypes?if_exists as st><#list c.hzTradeTypes?if_exists as ct><#if st==ct> ${ct.tradeName}</#if></#list><#list st.hzTradeTypes?if_exists as sst><#list c.hzTradeTypes?if_exists as ct><#if sst==ct> ${ct.tradeName}</#if></#list></#list></#list><#list c.hzTradeTypes?if_exists as ct><#if (t==ct&&c.hzTradeTypes?if_exists?size>1)>;</#if></#list></#list>&nbsp;</td> -->
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
<form action="../company/deleteCompanyPass.xhtml" method="post" name="companyPassForm" id="companyPassForm">
	<input type="hidden" id="companyIds" name="companyIds" value=""/>
</form>
<script type="text/javascript">
	function delCompanyAcount(){
	if(chooseCheckBox()){
		var checkboxs = getTag('input');
		var companyIds="";
		var num=0;
		for(var i=0;i<checkboxs.length; i++) {
			if(checkboxs[i].type=='checkbox'){
				if(checkboxs[i].checked) {
					companyIds=companyIds+checkboxs[i].value+",";
					num=num+1;
				}
			}
		}
		if(num==0){
			alert("请选择复选框！");
		}
		get("companyIds").value=companyIds.substring(0,companyIds.length-1);
		get("companyPassForm").submit();
		}
	}
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
	<#list tradeTypes?if_exists as t>
		<#list t.hzTradeTypes?if_exists as st>
			if (type == 1 && tradeId == "${t.id}") {
				<#if st_index==0>cleanSelect(get("tradeTypes2"));</#if>
				var opt = new Option("${st.tradeName}", "${st.id}");
				get("tradeTypes2").options["${st_index+1}"] = opt;
				opt = null;
			}
			<#list st.hzTradeTypes?if_exists as sst>
				if (type == 2 && tradeId == "${st.id}") {
					<#if sst_index==0>cleanSelect(get("tradeTypes3"));</#if>
					var opt = new Option("${sst.tradeName}", "${sst.id}");
					get("tradeTypes3").options["${sst_index+1}"] = opt;
					opt = null;
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
<@fkMacros.selectAreas_fun "${company?if_exists.firstArea?if_exists}" "${company?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${company?if_exists.fouthArea?if_exists}" "${company?if_exists.fifthArea?if_exists}" "company."/>
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "company."/>
</#if>
</#escape>
<@fkMacros.pageFooter />