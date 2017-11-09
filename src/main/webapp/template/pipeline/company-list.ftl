<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>企业已审核列表</th>
  </tr>
</table>
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	<form action="" method="post" name="companyForm" id="companyForm">
	  <tr>
	    <th>单位名称</th>
	    <td width="35%"><input type="text" name="company.companyName" id="companyName" value="${company.companyName}" size="39" maxlength="50"></td>
	    <th>地　　址</th>
	    <td width="38%"><input type="text" name="company.regAddress" id="address" value="${company.regAddress}" size="35" maxlength="50"></td>
	  </tr>
	  <tr>
	    <th>行　　业</th>
	    <td id="td_trade" nowrap><select name="company.tradeTypes" id="tradeTypes1" onchange="changeTrade(this.value,1);"><option value="">--请选择--</option>
	    <#list tradeTypes?if_exists as t><#if t.type==1><option value="${t.id}" >${t.name}</option></#if></#list></select>
	    &nbsp;<select name="company.tradeTypes" id="tradeTypes2" onchange="changeTrade(this.value,2);"><option value="">--请选择--</option></select>
	    &nbsp;<select name="company.tradeTypes" id="tradeTypes3" onchange=""><option value="">--请选择--</option></select></td>
	    <th>区　　域</th>
	    <td><div id="div-area"></div></td>
	    </tr>
	  <tr>
	    <th colspan="4"><div align="center"><input type="button" id="sub" value="搜　索" class="confirm_but" style="height:25px; width:80px" onClick="document.getElementById('companyForm').submit();" /></div></th>
	  </tr>
	    <input type="hidden" id="orderProperty" name="company.orderProperty" value="id"/>
		<input type="hidden" id="orderType" name="company.orderType"/>	  
	  </form>
	</table>
	
<div class="menu">
  	<ul>
	<li id="img_save"><a href="javascript:create();" class="b1"><b>添加</b></a></li>  	
	<li id="img_refurbish"><a href="javaScript:;" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	</ul>
</div>
<form action="company_list.xhtml" method="post" name="companiesForm" id="companiesForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
  	<th  id="" width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th  id="th_id" width="4%" nowrap>序号</th>
    <th  id="th_companyName" width="25%" nowrap>企业名称</th>
    <th  id="th_regAddress" width="20%" nowrap>地址</th>
  </tr>
  <#if result?exists>
  	<#list result?if_exists as item>
	  <tr>
	  	<td><input name="ids" type="checkbox" value="${item.id}"/></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+item_index+1}<#else>${item_index+1}</#if></td>
	    <td><a href="${urlPrefix}_create.xhtml?company.id=${item.id}">${item.companyName}</a>&nbsp;</td>
	    <td>${item.regAddress} &nbsp;</td>	    	    	    
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
		<#if company?if_exists.orderType?exists>
			orderType = "${company.orderType?string}"=="true"?false:true;
		</#if>
		get("orderType").value = orderType;
		get("orderProperty").value=orderProperty;		
		submitSerach();
	}
function submitForm(formName){
	var formObj=get(formName);
	formObj.submit();
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
</script>
<@fkMacros.muilt_select_js />
<#if company?has_content>
<@fkMacros.selectAreas_fun "${company?if_exists.firstArea?if_exists}" "${company?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${company?if_exists.fouthArea?if_exists}" "${company?if_exists.fifthArea?if_exists}" "company."/>
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "company."/>
</#if>
<@fkMacros.pageFooter />

<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.js"></script>
<script>
function create(){
	var ids = jQuery("input[type='checkbox']:checked");
	if (ids.length > 1){
		alert("只能选择一家企业进行添加。");
		return;
	}
	if (ids.length == 0){
		alert("请选择一家企业");
		return;
	}
	window.location.href = "${urlPrefix}_create.xhtml?company.id=" + ids.val();
}
</script>
</#escape>