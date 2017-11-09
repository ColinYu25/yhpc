<@fkMacros.pageHeader />
<#escape x as (x)!>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>企业列表</th>
  </tr>
</table>
	
<div class="menu">
  	<ul>
	<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
  	<li id="img_return"><a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>	
	</ul>
</div>
<form action="company_list.xhtml" method="post" name="companiesForm" id="companiesForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
  	<th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th width="4%" nowrap>序号</th>
    <th width="25%" nowrap>企业名称</th>
    <th width="20%" nowrap>地址</th>
  </tr>
  <#if result?exists>
  	<#list result?if_exists as item>
	  <tr>
	  	<td><input name="ids" type="checkbox" value="${item.id}"/></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+item_index+1}<#else>${item_index+1}</#if></td>
	    <td><a href="company_update.xhtml?entity.id=${item.id}">${item.company.companyName}</a>&nbsp;</td>
	    <td>${item.company.regAddress} &nbsp;</td>	    	    	    
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

</script>
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