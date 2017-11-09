<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>管道所属单位选择</th>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	<form action="" method="post" name="companyForm" id="companyForm">
	  <tr>
	    <th>单位名称</th>
	    <td width="35%"><input type="text" name="company.companyName" id="companyName" value="${company.companyName}" size="39" maxlength="50"></td>
	    <th>地　　址</th>
	    <td width="38%"><input type="text" name="company.regAddress" id="address" value="${company.regAddress}" size="35" maxlength="50"></td>
	  </tr>
	  <tr>
	    <th colspan="4"><div align="center"><input type="button" id="sub" value="搜　索" class="confirm_but" style="height:25px; width:80px" onClick="document.getElementById('companyForm').submit();" /></div></th>
	  </tr>
	</form>
</table>
<div class="menu">
  	<ul>
	<li id="img_save"><a href="javascript:selectCompany();" class="b1"><b>选择</b></a></li>  	
	<li id="img_refurbish"><a href="javaScript:;" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	</ul>
</div>
<form action="" method="post" name="companiesForm" id="companiesForm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
  	<th  id="" width="4%"><input type="radio" ></th>
    <th  id="th_id" width="4%" style="cursor:hand;" onClick="orderProperty('id');" nowrap>序号</th>
    <th  id="th_companyName" width="25%" style="cursor:hand;" onClick="orderProperty('companyName');" nowrap>企业名称</th>
    <th  id="th_regAddress" width="20%" style="cursor:hand;" onClick="orderProperty('regAddress');" nowrap>地址</th>

  </tr>
  <#if result?exists>
  	<#list result?if_exists as item>
	  <tr>
	  	<td><input name="ids" type="radio" value="${item.id}" companyName="${item.companyName}"/></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+item_index+1}<#else>${item_index+1}</#if></td>
	    <td>${item.companyName}&nbsp;</td>
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
</script>
<@fkMacros.pageFooter />
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.4.2.js"></script>
<script>
function selectCompany(){
	var id = jQuery("input[type='radio']:checked");
	window.parent.setCqCompanyValue(id.val());
	closeWindows("companies");
}
</script>
</#escape>