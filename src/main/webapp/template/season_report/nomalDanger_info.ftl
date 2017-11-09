<@fkMacros.pageHeader />
<#escape x as (x)!> 
<#if daNomalDanger?exists>
	<#if daNomalDanger.id!=-1>
  		<#assign url='updateNomalDanger.xhtml'>
  	<#else>
  		<#assign url='createNomalDanger.xhtml'>
  	</#if>
 <#else>
  	<#assign url='createNomalDanger.xhtml'>
</#if>
<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
  		  var obj=get("seasonReportForm");
  		  obj.action="${url}";
 		  obj.submit();
 	}
}

function opened(){
	get("dangerType_span").style.display='';
	get("description_span").style.display='';
	get("dangerType").disabled=false;
	get("description").disabled=false;
	get("completedDate").disabled=false;
	get("isDangerVal").value=true;
}

function closed(){
	get("dangerType_span").style.display='none';
	get("description_span").style.display='none';
	get("dangerType").disabled=true;
	get("description").disabled=true;
	get("completedDate").disabled=true;
	get("dangerType").value="";
	get("description").value="";
	get("completedDate").value="";
	get("isDangerVal").value=false;
}
function isCheck(){
	if(get("isDanger").checked){
		closed();
	}else{
		opened();
	}
}
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="100%" height="22">一般隐患录入</th>
  </tr>
</table>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<li id="img_save"><a href="#" class="b1" onClick="submitCreate();"><b>保存</b></a></li>
	<li id="img_refurbish"><a href="javascript:window.location.reload()" class="b4"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.back(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>	
</div>
<@fkMacros.formValidator 'seasonReportForm' />
<form id="seasonReportForm" name="seasonReportForm" action="" method="post">
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_input">
<tr>
  <th width="14%">单位名称</th>
  <td colspan="6"><#if company??>${company.companyName}</#if><#if bag??>${bag.name}</#if></td>
  </tr>
  <tr>
  <th>单位地址</th>
  <td colspan="6"><#if company??>${company.regAddress}</#if><#if bag??>${bag.regAddress}</#if></td>
  </tr>
  <tr>
<tr>
	  <th>联 系 人</th>
	  <td width="23%"><input id="linkMan" name="daNomalDanger.linkMan" type="text" value="${daNomalDanger?if_exists.linkMan}" size="16" maxlength="50" /> 
	  <span class="red_point">*</span><ui:v for="linkMan" rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
	  <th style="width:12%;">联系电话</th>
	  <td width="22%">
	  <input id="linkTel" name="daNomalDanger.linkTel" type="text" size="14" value="${daNomalDanger?if_exists.linkTel}" maxlength="13" style="ime-mode:disabled"/>
	  <span class="red_point">*</span><ui:v for="linkTel" rule="phone_mobile" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/></td>
	  <th style="width:12%;">手　　机</th>
	  <td width="24%" colspan=2>
	  <input id="linkMobile" name="daNomalDanger.linkMobile" type="text" value="${daNomalDanger?if_exists.linkMobile}"  size="13" maxlength="13" style="ime-mode:disabled"/>
	  <ui:v for="linkMobile" rule="mobile" require="false" warn="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
	</tr>
   	<#if company??>
	<input type="hidden" name="company.id" value="${company.id}" />
	</#if>
	<#if bag??>
		<input type="hidden" name="bag.id" value="${bag.id}" />
	</#if>
	<#if daNomalDanger??><input type="hidden" name="daNomalDanger.id" value="${daNomalDanger.id}" /></#if>
	<tr>
	  <th>有无隐患</th>
	  <td>
      <input type="checkbox" onClick="isCheck();" id="isDanger" <#if daNomalDanger?if_exists.danger?exists&&!daNomalDanger.danger&&daNomalDanger?if_exists.id!=-1>checked</#if>>无隐患 <span class="red_point">*</span></td>
	  <th style="width:12%;">隐患类别</th>
	  <td colspan="4">
	  <select id="dangerType" name="daNomalDanger.type">
	  	<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--　请选择　--&nbsp;&nbsp;&nbsp;&nbsp;</option>
	  	<#list tradeTypes?if_exists as t>
		<optgroup label="&nbsp;${t.name}">		
		<#list t.daIndustryParameters?if_exists as ts>
			<#if (ts.daIndustryParameters?size>0)> <optgroup label="&nbsp;&nbsp;&nbsp;└${ts.name}"><#else>
			<option value="${ts.id}" <#if daNomalDanger?if_exists.type?exists><#if daNomalDanger.type==ts.id>selected</#if></#if>>&nbsp;&nbsp;&nbsp;└${ts.name}</option></#if>
			<#list ts.daIndustryParameters?if_exists as tss>
				<#if (tss.daIndustryParameters?size>0)> <optgroup label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└${tss.name}"><#else>
				<option value="${tss.id}" <#if daNomalDanger?if_exists.type?exists><#if daNomalDanger.type==tss.id>selected</#if></#if>>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└${tss.name}</option></#if>
				<#list tss.daIndustryParameters?if_exists as tsss>
					<option value="${tsss.id}" <#if daNomalDanger?if_exists.type?exists><#if daNomalDanger.type==tsss.id>selected</#if></#if>>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└${tsss.name}</option>
				</#list>
			</#list>
		</#list>  
	  	</#list> 
	  </select> <span id="dangerType_span" class="red_point">*</span><ui:v for="dangerType" rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
	 </tr>
	<tr>
	  <th>隐患描述</th><td colspan="6"><textarea id="description" name="daNomalDanger.description" rows="8" cols="75">${daNomalDanger.description}</textarea> 
	  <span id="description_span" class="red_point">*</span><ui:v for="description" rule="require" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;"/></td>
	</tr>
	<tr>
	  <th>完成整改日期</th>
	  <td>
	  <input id="completedDate" name="daNomalDanger.completedDate" type="text" size="14" value="${daNomalDanger.completedDate?date}" style="ime-mode:disabled"  maxlength="10" onfocus="WdatePicker();" class="Wdate" />
	  <ui:v for="completedDate" rule="date" require="false" warn="&nbsp;" pass="&nbsp;" tips="&nbsp;"/>
	  </td>
	  <th colspan=5 ><div align="left">　　　　<input type="button" value="确　认" class="confirm_but" style="height:25px; width:80px" onClick="submitCreate();"/></div></th>
	</tr>
</table>
<input type="hidden" name="daNomalDanger.danger" id="isDangerVal"/>
</form>
<script>
<#if !daNomalDanger?if_exists.danger?exists||daNomalDanger.danger||daNomalDanger?if_exists.id==-1>
opened();
<#else>
closed();
</#if>
</script>
</#escape> 
<@fkMacros.pageFooter />