<@fkMacros.pageHeader />
<#escape x as (x)!> 
  <#if pointType?if_exists.id?if_exists!=-1>
  	<#assign url='updatePointType.xhtml'>
  <#else>
  	<#assign url='createPointType.xhtml'>
  </#if>
<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
  		  var obj=get("pointTypeForm");
  		  obj.action="${url}";
 		  obj.submit();
 	}
}
</script>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22"><strong><#if pointType?if_exists.id?if_exists!=-1>修改<#else>添加</#if>核定信息</strong></th>
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
<@fkMacros.formValidator 'pointTypeForm' />
<form id="pointTypeForm" name="pointTypeForm" method="post" action="">
<table width="98%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_input">
      <tr>
        <th width="15%">名　称：</th>
        <td><input name="pointType.tradeName" type="text" class="input" id="tradeName" value="${pointType.name}" size="35" maxlength="25"><ui:v for="tradeName"  rule="require" empty="行业名称不允许为空" pass="&nbsp;" tips="&nbsp;"/></td>
      </tr>
      <tr>
        <th>分　数：</th>
        <td><input name="pointType.point" type="text" class="input" id="point" value="${pointType.point}" size="35" maxlength="25"></td>
      </tr>
      <tr>
        <th>序　号：</th>
        <td><input name="pointType.sortNum" type="text" class="input" id="sortNum" value="${pointType.sortNum}" size="35" maxlength="25"></td>
      </tr>
</table>
<input type="hidden" name="pointType.id" id="id" value="${pointType.id}"/>
<input type="hidden" name="pointType.daIndustryParameter.id" id="typeId" value="${pointType.daIndustryParameter.id}"/>
</form>
</#escape>
<@fkMacros.pageFooter />