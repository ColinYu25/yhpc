<@fkMacros.pageHeader />
<#escape x as (x)!>
<script>
	function modifyPass(userId){
			window.location="updateCompanyPasswordInit.xhtml?fkUser.id="+userId;
	}
</script>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>企业已审核列表</th>
  </tr>
</table>
  <form action="loadCompaniesForAdmin.xhtml" method="post" name="companyForm" id="companyForm">
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_input">
	  <tr>
	    <th width="5%" nowrap="nowrap">单位名称</th>
	    <td width="20%"><input type="text" name="company.companyName" id="companyName" value="${company.companyName}" size="20" maxlength="50"></td>
	    
	    <th nowrap="nowrap">
		      <input name="company.type"   value="1"  type="radio"  checked="checked" />  统一社会信用代码（工商注册号） 
		      <input type="radio" name="company.type"   value="2"   <#if company?exists && company.type?? && company.type==2 >   checked="checked"  </#if> />  组织机构代码
		 </th> 
		 <td >    
		      <input type="text" id="regNum" name="company.regNum"  value=${company.regNum} >
	     </td>
	    
	  </tr>
	  <tr>
	    <th>UUID</th>
	    <td><input type="text" name="company.uuid" id="uuid" value="${company.uuid}"  size="20" maxlength="50"></td>
	    <th>区　　域</th>
	    <td><div id="div-area"></div></td>
	 </tr>
	    <input type="hidden" id="orderProperty" name="company.orderProperty" value="createTime"/>
		<input type="hidden" id="orderType" name="company.orderType"/>
	
	
	  <tr>
	    <th colspan="4"><div align="center">
	    <input type="button" id="sub" value="搜　索" class="confirm_but" style="height:25px; width:80px" onClick="submitForm('companyForm');" /></div></th>
	  </tr>
	</table>
	</form>
<div class="menu">
  	<ul>
  	<li id="img_amend"><a href="#" class="b2" onClick="loadNote('loadCompanyForBinding.xhtml?company.id');"><b>重绑</b></a></li>
	<li id="img_refurbish"><a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:get('companyForm').submit();" class="b6"><b>查询</b></a></li>
	<li id="img_return"><a href="javascript:reset();" class="b6"><b>重置</b></a></li>
	</ul>
</div>
<form action="" method="post" name="companiesForm" id="companiesForm">
<table width="98%" border="0" cellspacing="0" cellpadding="0" class="table_list">
  <tr>
    <th width="4%"><input type="checkbox" onClick="selectAllOrNo(this,getName('ids'));"></th>
    <th id="th_id" width="4%"  nowrap>序号</th>
    <th id="th_companyName" width="25%" style="cursor:pointer;" onClick="orderProperty('companyName');" nowrap><b>单位名称 &nbsp;<img src='${contextPath}/resources/default/img/arrow_for_two.gif' border='0'  width='6' height='18'/></b></th>
    <th id="th_fddelegate" width="8%"   nowrap>UUID</th>
    <th id="th_fddelegate" width="8%"   nowrap>统一社会信用代码（工商注册号）</th>
    <th id="th_phoneCode" width="8%"  nowrap>组织机构编码</th>
    <th id="th_phoneCode" width="8%"  nowrap>法定代表人</th>
    <th id="th_createTime" width="8%" style="cursor:pointer;" onClick="orderProperty('createTime');" nowrap><b>创建时间 &nbsp;<img src='${contextPath}/resources/default/img/arrow_for_two.gif' border='0'  width='6' height='18'/></b></th>
    <th id="th_phoneCode" width="8%"  nowrap>修改时间</th>
    <th width="12%" nowrap>企业用户名</th>

  </tr>
  <#if companies?exists>
  	<#list companies?if_exists as c>
	  <tr>
	    <td><input type="checkbox" name="ids" id="ids${c.id}" value="${c.id}"></td>
	    <td><#if pagination.itemCount?exists>${pagination.itemCount+c_index+1}<#else>${c_index+1}</#if></td>
	    <td><div align="left"><a href="loadCompany.xhtml?company.id=${c.id}">${c.companyName}</a></td>
	    <td><div align="left">${c.uuid}</td>
	    <td>${c.regNum}</td>
	    <td>${c.setupNumber}</td>
	    <td>${c.fddelegate}</td>
	    <td>${c.createTime?date}</td>
	    <td>${c.modifyTime?date}</td>
	  	<td><#if c.userName?if_exists!=''>${c.userName}<#else>&nbsp;</#if></td>

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

function reset(){
    document.getElementById("companyName").value="";
    document.getElementById("regNum").value="";
    document.getElementById("uuid").value="";
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
<@fkMacros.muilt_select_js />
<#if company?has_content>
<@fkMacros.searchselectAreas_fun "${company?if_exists.firstArea?if_exists}" "${company?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${company?if_exists.fouthArea?if_exists}" "${company?if_exists.fifthArea?if_exists}" "company."/>
<#else>
<@fkMacros.searchselectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "company."/>
</#if>
</#escape>
<@fkMacros.pageFooter />