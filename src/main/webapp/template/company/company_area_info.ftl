<@fkMacros.pageHeader />
<#escape x as (x)!> 

<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
  		  var obj=get("companyAcountForm");
  		  obj.action="createCompanyAcount.xhtml";
 		  obj.submit();
 	}
}
</script>
<table border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th>异地经营</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_save">		<a href="#" class="b1" onClick="submitCreate();"><b>保存</b></a></li>
	<li id="img_refurbish">	<a href="#" class="b4" onClick="window.location.reload();"><b>刷新</b></a></li>
	<li id="img_return">	<a href="javascript:history.go(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>
</div> 
<@fkMacros.formValidator 'companyAcountForm' />
<form id="companyAcountForm" name="companyAcountForm" method="post" action="">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_input">
      <tr>
        <th>单位名称：</th>
        <td colspan="3">${company.companyName}</td>
      </tr>
      <tr>
        <th>是否宁波：</th> 
		<td colspan="3">
		<input type="radio" name="companyAcount.isningbo" value="true" id="isningbo1" checked="checked"/>是　　　
		<input type="radio" name="companyAcount.isningbo" value="false" id="isningbo2"/>否</td>
	  </tr>
      <tr>
        <th>实际所在区域：</th>
        <td colspan="3"><div id="div-area"></div></td>
      </tr>
      <tr>
        <th>联系人：</th>
        <td><input type="text" name="companyAcount.fddelegate" value="${company.fddelegate}" id="fddelegate"/>
        <ui:v for="fddelegate"  rule="require" empty="联系人不允许为空" pass="&nbsp;" tips="&nbsp;"/>
        </td>
        <th>联系电话：</th>
        <td><input type="text" name="companyAcount.tel" value="${company.phoneCode}" id="tel"/>
        <ui:v for="tel"  rule="require" empty="联系电话不允许为空" pass="&nbsp;" tips="&nbsp;"/>
        </td>
      </tr>
      <tr>
      	<th>实际经营地址：</th>
      	<td colspan="3">
      	<input type="text" name="companyAcount.address" id="address" size="50"/>
      	<ui:v for="address"  rule="require" empty="实际经营地址不允许为空" pass="&nbsp;" tips="&nbsp;"/>
      	</td>
      </tr>
    </table></td>
  </tr>
</table>
<input type="hidden" name="companyAcount.oldfirstArea" id="id" value="${company.firstArea}"/>
<input type="hidden" name="companyAcount.oldsecondArea" id="id" value="${company.secondArea}"/>
<input type="hidden" name="companyAcount.oldthirdArea" id="id" value="${company.thirdArea}"/>
<input type="hidden" name="companyAcount.daCompany.id" id="id" value="${company.id}"/>
</form>
<@fkMacros.muilt_select_js />
<#if company?has_content>
<@fkMacros.selectAreas_fun "${companyAcount?if_exists.firstArea?if_exists}" "${companyAcount?if_exists.secondArea?if_exists}" "${company?if_exists.thirdArea?if_exists}" "${companyAcount?if_exists.fouthArea?if_exists}" "${companyAcount?if_exists.fifthArea?if_exists}" "companyAcount."/>
<#else>
<@fkMacros.selectAreas_fun "${userDetail.firstArea?if_exists}" "${userDetail.secondArea?if_exists}" "${userDetail.thirdArea?if_exists}" "${userDetail.fouthArea?if_exists}" "${userDetail.fifthArea?if_exists}" "companyAcount."/>
</#if>
<script type="text/javascript">
var areaObj = new Area("${AreaXmlUrl}");
	var firstArea="";
	var secondArea="";
	var thirdArea="";
  <#if company?exists>
 	<#if company.firstArea != 0>
		firstArea=areaObj.getArea("${company.firstArea}")[0];
	</#if>
	<#if company.secondArea != 0>
		secondArea=areaObj.getArea("${company.secondArea}")[0];
	</#if>
	<#if company.thirdArea != 0>
		thirdArea=areaObj.getArea("${company.thirdArea}")[0];
	</#if>
  </#if>
	get("area").innerHTML=firstArea+" "+secondArea+" "+thirdArea;
temp_img_save();
</script>
</#escape>
<@fkMacros.pageFooter />