<@fkMacros.pageHeader />
<#escape x as (x)!> 
  <#if tradeType?exists>
  	<#assign url='updateTradeType.xhtml'>
  <#else>
  	<#assign url='createTradeType.xhtml'>
  </#if>
<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
  		  var obj=get("tradeTypeForm");
  		  obj.action="${url}";
 		  obj.submit();
 	}
}
</script>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_4_blue">
  <tr>
	<th width="98%" height="22"><strong><#if tradeType?exists>修改<#else>添加</#if>行业信息</strong></th>
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
<@fkMacros.formValidator 'tradeTypeForm' />
<form id="tradeTypeForm" name="tradeTypeForm" method="post" action="">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_input">
      <#if (!ids?exists&&(tradeType?exists&&!tradeType.hzTradeType?exists)||(!ids?exists&&!tradeType?exists))>
      </#if>
	      <tr>
	        <th>类型：</th>
	        <td><select name="tradeType.type" id="type">
	        	  <option value="1">企业行业</option>
	        	  <option value="2">工程项目重大隐患类型</option>
	        	  <option value="3">企业重大隐患类型</option>
	        	  <option value="4">打非类型</option>
	        	  <option value="5">统计上报行业</option>
	        	  <option value="6">企业季报行业</option>
	        	  <option value="7">行业部门季报行业</option>
	        	  <option value="8">企业一般隐患类型</option>
	        	  <option value="9">企业分级分类类型</option>
	        	  <option value="10">安全生产执法行业类型</option>
	        	</select></td>
	      </tr>
      <tr>
        <th width="15%">行业名称：</th>
        <td><input name="tradeType.name" type="text" class="input" id="name" value="${tradeType.name}" size="35" maxlength="25">
        <ui:v for="name"  rule="require" empty="行业名称不允许为空" pass="&nbsp;" tips="&nbsp;"/></td>
      </tr>
      <tr>
        <th width="15%">行业编码：</th>
        <td><input name="tradeType.code" type="text" class="input" id="code" value="${tradeType.code}" size="35" maxlength="25"></td>
      </tr>
      <tr>
        <th width="15%">排序：</th>
        <td><input name="tradeType.sort" type="text" class="input" id="sort" value="${tradeType.sort}" size="35" maxlength="25">
        <ui:v for="sort" rule="integer" empty="&nbsp;" pass="&nbsp;" tips="&nbsp;" warn="&nbsp;"/>
        </td>
      </tr>
      <tr>
        <th>说明：</th>
        <td><input name="tradeType.depiction" type="text" class="input" id="depiction" value="${tradeType.depiction}" size="35" maxlength="25"></td>
      </tr>
</table>
<input type="hidden" name="tradeType.id" id="id" value="${tradeType.id}"/>
<input type="hidden" name="ids" id="ids" value="${ids}"/>
</form>
<script type="text/javascript">
<#if !admin>
	<#if roleDepic?if_exists!="">
		<#if (!ids?exists&&(tradeType?exists&&!tradeType.hzTradeType?exists)||(!ids?exists&&!tradeType?exists))>
			get("depiction").value = "${roleDepic}";
			get("depiction").readOnly = true;
		</#if>
	<#else>
		
	</#if>
</#if>
<#if tradeType?exists&&tradeType.type?exists>
	get("type").value = "${tradeType.type}";
</#if>
temp_img_save();
</script>
</#escape>
<@fkMacros.pageFooter />