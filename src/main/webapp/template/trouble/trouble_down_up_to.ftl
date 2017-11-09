<@fkMacros.pageHeader />
<#escape x as (x)!>
  	<#assign url='createTroubleByDownOrUpOrBackOrResult.xhtml'>
<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
  		var obj=get("troubleForm");
  		obj.action="${url}";
 		obj.submit();
 	}
}
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="100%" height="22">安全生产隐患<#if userDetail.secondArea!=0>上报<#else>下达</#if>表</th>
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
<@fkMacros.formValidator 'troubleForm' />
<form id="troubleForm" name="troubleForm" method="post" action="">
<table cellspacing="0" cellpadding="0" class="table_input">
  <tr>
    <td width="7%" rowSpan="3"  style="background:#F0F0F0;color:#333;" align="center"><strong>隐患<br>发现<br>部门</strong></td>
    <td width="9%" style="background:#F0F0F0;color:#333;" align="center"><strong>部门名称</strong>
    <td colSpan="5"><input id="findDeptName" maxLength="50" size="50" name="dept.findDeptName" value="${dept.findDeptName}"> 
    <span class="red_point">*</span><ui:v for="findDeptName" rule="require" empty="&nbsp;" warn="&nbsp;" pass="&nbsp;"/></td>
  </tr>
  <tr>
    <td width="11%" style="background:#F0F0F0;color:#333;" align="center"><strong>经 办 人</strong></td>
    <td width="20%"><input id="linkMan" maxLength="10" size="12" name="dept.linkMan" value="${dept.linkMan}"> 
    <span class="red_point">*</span><ui:v for="linkMan" rule="require" empty="&nbsp;" warn="&nbsp;" pass="&nbsp;"/></td>
    <td width="9%" style="background:#F0F0F0;color:#333;" align="center"><strong>联系电话</strong></td>
    <td width="20%"><input maxLength="13" size="11" name="dept.linkTel" id="linkTel" value="${dept.linkTel}" style="ime-mode:disabled"> 
    <span class="red_point">*</span><ui:v for="linkTel" rule="phone_mobile" empty="&nbsp;" pass="&nbsp;" warn="&nbsp;"/></td>
    <td width="9%" style="background:#F0F0F0;color:#333;" align="center"><strong>批 准 人</strong></td>
    <td width="23%"><input maxLength="10" size="12" name="dept.passMan" id="passMan" value="${dept.passMan}">
    <span class="red_point">*</span><ui:v for="passMan" rule="require" empty="&nbsp;" warn="&nbsp;" pass="&nbsp;"/></td>
  </tr>
  <tr>
    <td style="background:#F0F0F0;color:#333;" align="center"><strong>处理意见</strong></td>
    <td colSpan="5"><textarea id="description" name="dept.description" rows="3" cols="70">${dept.description}</textarea>
    <span class="red_point">*</span><ui:v for="description" rule="require" empty="&nbsp;" warn="&nbsp;" pass="&nbsp;"/></td>
  </tr>
  <tr>
    <td  style="background:#F0F0F0;color:#333;" rowSpan="7" align="center"><strong>隐<br><br>患<br><br>情<br><br>况</strong></td>
    <td  style="background:#F0F0F0;color:#333;" align="center"><strong>隐患单位名称</strong></td>
    <td colSpan="5">
    ${trouble.troubleCompanyName}</td>
  </tr>
  <tr>
    <td style="background:#F0F0F0;color:#333;" align="center"><strong>所在区域</strong></td>
    <td colSpan="5">${trouble.firstAreaName}${trouble.secondAreaName}${trouble.thirdAreaName}</td>
  </tr>
  <tr>
    <td style="background:#F0F0F0;color:#333;" align="center"><strong>隐患地址</strong></td>
    <td colSpan="5">${trouble.dangerAdd}</td>
  </tr>
  <tr>
    <td style="background:#F0F0F0;color:#333;" align="center"><strong>法定代表人</strong></td>
    <td>${trouble.fddelegate}</td>
    <td style="background:#F0F0F0;color:#333;" align="center"><strong>主要负责人</strong></td>
    <td colSpan="3">${trouble.principal}</td>
  </tr>
  <tr>
    <td style="background:#F0F0F0;color:#333;" align="center"><strong>联系电话</strong></td>
    <td>${trouble.linkTel}</td>
    <td style="background:#F0F0F0;color:#333;" align="center"><strong>手　　机</strong></td>
    <td colSpan="3">${trouble.linkMobile}</td>
  </tr>
  <tr>
    <td style="background:#F0F0F0;color:#333;" align="center"><strong>隐患描述</strong></td>
    <td colSpan="5">${trouble.description}</td>
  </tr>
  <tr>
    <td style="background:#F0F0F0;color:#333;" align="center"><strong>相关附件</strong></td>
    <td colSpan="5">
    <#if troubleFiles?exists>
	  	<#list troubleFiles?if_exists as f>
	  	<a href="${contextPath}${f.filePath}">${f.fileName}</a><#if f_index+1 != troubleFiles?size><br></#if>
	  	</#list>
  	</#if>
    &nbsp;</td>
  </tr>
  <tr>
    <td colSpan="2" style="background:#F0F0F0;color:#333;" align="center"><strong><#if userDetail.secondArea!=0>上报<#else>下达</#if>部门（单位）</strong></td>
    <td colSpan="5">
    <#if userDetail.secondArea!=0>宁波市<#else>
    <select name="dept.mostlyCompanyArea" id="mostlyCompanyArea" style="width:128px;">
		<option value="">--请选择--</option>
		<#if areas?exists>
		  	<#list areas?if_exists as fa>
				<option value="${fa.areaCode}">${fa.areaName}</option>
			</#list>
	  	</#if>
	</select>
	<ui:v for="mostlyCompanyArea" rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;"/>
    </#if>
    ${fkEnum.enumName}&nbsp;
    <input type="hidden" name="dept.mostlyCompany" value="${fkEnum.enumCode}" id="enumCode"/>
	</td>
  </tr>
</table>
<input type="hidden" name="dept.id" value="${dept.id}" id="id"/>
<input type="hidden" name="dept.daDept.id" value="${faDept.id}" id="faDeptId"/>
<input type="hidden" name="trouble.id" value="${trouble.id}" id="troubleId"/>
<#if userDetail.secondArea!=0>
<input type="hidden" name="dept.troubleCopyType" value="3" id="troubleCopyType"/>
<#else>
<input type="hidden" name="dept.troubleCopyType" value="2" id="troubleCopyType"/>
</#if>
</form>
</#escape>
<@fkMacros.pageFooter />