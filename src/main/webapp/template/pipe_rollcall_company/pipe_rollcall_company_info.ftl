<@fkMacros.pageHeader />
<#escape x as (x)!>
  <#if rollcallCompany?if_exists.id?if_exists!=-1>
  	<#assign url='updateRollcallCompany.xhtml'>
  <#else>
  	<#assign url='createRollcallCompany.xhtml'>
  </#if>
<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
  		  var obj=get("rollcallCompanyForm");
  		  obj.action="${url}";
 		  obj.submit();
 	}
}
var enumObj=new Enum("${enumXmlUrl}");
var areaObj = new Area("${AreaXmlUrl}");
</script>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22" ><#if rollcallCompany?if_exists.id?if_exists!=-1>修改<#else>添加</#if>重大隐患挂牌信息</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_save"><a href="javascript:submitCreate();" class="b1"><b>保存</b></a></li>
	<li id="img_refurbish"><a href="javascript:window.location.reload();" class="b4"><b>刷新</b></a></li>	
	<li id="img_return"><a href="javascript:history.back(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div>
<@fkMacros.formValidator 'rollcallCompanyForm' />
<form id="rollcallCompanyForm" name="rollcallCompanyForm" method="post" action="">
	<table width="100%" border="0"  cellpadding="0" cellspacing="0" class="table_input">
	<#if rollcallCompany?if_exists.id?if_exists!=-1>
	<tr>
		<th>级　　别</th>
		<td>
		<#if rollcallCompany.level?exists><script type="text/javascript">document.write(enumObj.getSelect("${rollcallCompany.level}"));</script></#if>挂牌
		</td>
	</tr>
	<tr>
		<th>督办单位</th>
		<td>
			<#if rollcallCompany.govment?exists><script type="text/javascript">document.write(areaObj.getArea("${rollcallCompany.govment}")[0]);</script></#if>
		</td>
	</tr>
	<tr>
		<th>督办部门</th>
		<td>
		<#if rollcallCompany.department?exists>${rollcallCompany.department}<#else>&nbsp;</#if>
		</td>
	</tr>
	<tr>
		<th>督办完成时间</th>
		<td>${rollcallCompany.completeTime?date}</td>
	</tr>
  	<tr>
		<th colspan=2><div align="center"><#if rollcallCompany?if_exists.id?if_exists!=-1>修改<#else>添加</#if>重大隐患挂牌信息</div></th>
	</tr>
	</#if>
  	<tr>
		<th>级　　别</th>
		<td>
		<select name="rollcallCompany.level" id="level" style="width:128px;">
			<option value="">--请选择--</option>
			<#if fkEnums?exists>
			  	<#list fkEnums?if_exists as fe>
					<option value="${fe.enumCode}">${fe.enumName}</option>
				</#list>
		  	</#if>
		</select>
		<ui:v for="level" rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;"/>
		</td>
	</tr>
	<tr>
		<th>督办单位</th>
		<td>
			<select name="rollcallCompany.govment" id="govment" style="width:128px;">
				<option value="">--请选择--</option>
				<#if areas?exists && areas?size!=0>
				  	<#list areas?if_exists as fa>
						<option value="${fa.areaCode}">${fa.areaName}</option>
					</#list>
				<#else>
					<option value="${area.areaCode}">${area.areaName}</option>
			  	</#if>
			</select>
			<ui:v for="govment" rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;"/>
		</td>
	</tr>
	<tr>
		<th>督办部门</th>
		<td>
		<input type="text" name="rollcallCompany.department"  id="department" size="16" maxlength="25">
		<ui:v for="department" rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;"/>
		</td>
	</tr>
	<tr>
		<th>督办完成时间</th>
		<td><input type="text" name="rollcallCompany.completeTime" id="completeTime" onClick="WdatePicker();" class="Wdate" style="width:128px;" maxlength="10">
		<ui:v for="completeTime" rule="date" require="false" warn="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
	</tr>
	</table>
	<input type="hidden" name="rollcallCompany.daPipeDanger.id" value="${rollcallCompany.daPipeDanger.id}" id="dangerId"/>
	<input type="hidden" name="rollcallCompany.id" value="${rollcallCompany.id}" id="id"/>
</form>
</#escape>
<@fkMacros.pageFooter />