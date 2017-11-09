<@fkMacros.pageHeader />
<#escape x as (x)!>
  <#if trouble?exists>
  	<#assign url='updateTrouble.xhtml'>
  <#else>
  	<#assign url='createTrouble.xhtml'>
  </#if>
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
    <td colSpan="3"><input id="findDept" maxLength="50" size="50" name="dept.findDept" value="${dept.findDept}"> 
    <span class="red_point">*</span><ui:v for="findDept" rule="require" empty="&nbsp;" warn="&nbsp;" pass="&nbsp;"/></td>
    <td width="9%" style="background:#F0F0F0;color:#333;" align="center"><strong>抄告编号</strong>
    <td><input id="troubleNo" maxLength="10" size="12" name="trouble.troubleNo" value="${trouble.troubleNo}"> 
    <span class="red_point">*</span><ui:v for="troubleNo" rule="require" empty="&nbsp;" warn="&nbsp;" pass="&nbsp;"/></td>
  </tr>
  <tr>
    <td width="11%" style="background:#F0F0F0;color:#333;" align="center"><strong>经 办 人</strong></td>
    <td width="20%"><input id="linkMan" maxLength="50" size="12" name="dept.linkMan" value="${dept.linkMan}"> 
    <span class="red_point">*</span><ui:v for="linkMan" rule="require" empty="&nbsp;" warn="&nbsp;" pass="&nbsp;"/></td>
    <td width="9%" style="background:#F0F0F0;color:#333;" align="center"><strong>联系电话</strong></td>
    <td width="20%"><input maxLength="13" size="11" name="dept.linkTel" id="linkTel" value="${dept.linkTel}" style="ime-mode:disabled"> 
    <span class="red_point">*</span><ui:v for="linkTel" rule="phone_mobile" empty="&nbsp;" pass="&nbsp;" warn="&nbsp;"/></td>
    <td width="9%" style="background:#F0F0F0;color:#333;" align="center"><strong>批 准 人</strong></td>
    <td width="23%"><input maxLength="12" size="12" name="dept.passMan" id="passMan" value="${dept.passMan}" style="ime-mode:disabled">
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
    <td colSpan="5">${company.companyName}&nbsp;</td>
  </tr>
  <tr>
    <td style="background:#F0F0F0;color:#333;" align="center"><strong>所在区域</strong></td>
    <td colSpan="5"><div id="div-area"></div></td>
  </tr>
  <tr>
    <td style="background:#F0F0F0;color:#333;" align="center"><strong>隐患地址</strong></td>
    <td colSpan="5"><input id="dangerAdd" maxLength="50" size="50" name="trouble.dangerAdd" value="${trouble.dangerAdd}"> 
    <span class="red_point">*</span><ui:v for="dangerAdd" rule="require" empty="&nbsp;" warn="&nbsp;" pass="&nbsp;"/></td>
  </tr>
  <tr>
    <td style="background:#F0F0F0;color:#333;" align="center"><strong>法定代表人</strong></td>
    <td>${company.fddelegate}&nbsp;</td>
    <td style="background:#F0F0F0;color:#333;" align="center"><strong>主要负责人</strong></td>
    <td colSpan="3">&nbsp;</td>
  </tr>
  <tr>
    <td style="background:#F0F0F0;color:#333;" align="center"><strong>联系电话</strong></td>
    <td colSpan="5">${company.phoneCode}&nbsp;</td>
  </tr>
  <tr>
    <td style="background:#F0F0F0;color:#333;" align="center"><strong>隐患描述</strong></td>
    <td colSpan="5"><textarea id="troubledescription" name="trouble.description" rows="3" cols="70">${trouble.description}</textarea>
    <span class="red_point">*</span><ui:v for="troubledescription" rule="require" empty="&nbsp;" warn="&nbsp;" pass="&nbsp;"/></td>
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
    ${fkEnum.enumName}&nbsp;
    <input type="hidden" name="dept.mostlyCompany" value="${fkEnum.enumCode}" id="enumCode"/>
	</td>
  </tr>
</table>
<input type="hidden" name="dept.id" value="${dept.id}" id="id"/>
<input type="hidden" name="trouble.id" value="${trouble.id}" id="troubleId"/>
<input type="hidden" name="company.id" value="${company.id}" id="companyId"/>
</form>
<@fkMacros.muilt_select_js />
<#if trouble?has_content>
	<@fkMacros.selectAreas_fun "${trouble?if_exists.firstArea?if_exists}" "${trouble?if_exists.secondArea?if_exists}" "${trouble?if_exists.thirdArea?if_exists}" "${trouble?if_exists.fouthArea?if_exists}" "${trouble?if_exists.fifthArea?if_exists}" "trouble."/>
<#else>
	<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "trouble."/>
</#if>
</#escape>
<@fkMacros.pageFooter />