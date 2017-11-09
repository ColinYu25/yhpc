<@fkMacros.pageHeader />
<#escape x as (x)!> 
<#if rollcallDefer?exists>
	<#if rollcallDefer.id!=-1>
  		<#assign url='updateRollcallDeferAgree.xhtml'>
  	<#else>
  		<#assign url='createRollcallDeferAgree.xhtml'>
  	</#if>
 <#else>
  	<#assign url='createRollcallDeferAgree.xhtml'>
</#if>
<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
  		  var obj=get("rollcallDeferForm");
  		  obj.action="${url}";
 		  obj.submit();
 	}
}
</script>
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
<@fkMacros.formValidator 'rollcallDeferForm' />
<form id="rollcallDeferForm" name="rollcallDeferForm" action="" method="post">
<table width="97.7%" border="0"  cellpadding="0" cellspacing="0"  style="background-image:url(${resourcePath}/images/bk.jpg);">
  <tr>
  <td align="center">
  <table width="570" height="350" border="0" cellpadding="0" cellspacing="0">
  <tr><td><br><strong style="font-size:23px;line-height:40px;">宁波市安全生产重大事故隐患治理<br>验收申请书</strong></td></tr>
  <tr><td height="50">&nbsp;</td></tr>
  <tr><td style="font-size:18px;line-height:50px;" align="left">
  　　<input type="text" name="rollcallDefer.govment" value="${rollcallDefer.govment}" id="govment" size="25" maxlength="25">：<br>
  　　根据（<span style="text-decoration:underline;"><#if rollcallCompany.wordOne?exists>${rollcallCompany.wordOne}<#else>　　　　　　　　</#if></span>字〔20<span style="text-decoration:underline;"><#if rollcallCompany.wordYear?exists>${rollcallCompany.wordYear}<#else>　</#if></span>〕第<span style="text-decoration:underline;"><#if rollcallCompany.wordTwo?exists>${rollcallCompany.wordTwo}<#else>　　</#if></span>号）通知书要求，我单位存在的<span style="text-decoration:underline;">${danger.description}。</span><br>
  	 　　重大事故隐患现已治理完毕，特申请验收。<br><br><br><br><br>
　　　　　　　　　　　　　　　　　　　　治理责任单位（盖章）<br>
　　　　　　　　　　　　　　　　　　　　　　<input type="text" id="companyApplyTime" value="${rollcallDefer.companyApplyTime?string('yyyy年MM月dd日')}" onfocus="WdatePicker({dateFmt:'yyyy年MM月dd日',vel:'companyApplyTime_2',minDate:'2000-01-01'})" class="Wdate" size="14" maxlength="10"><input type="hidden"  id="companyApplyTime_2"  name="rollcallDefer.companyApplyTime"  value="${rollcallDefer.companyApplyTime?date}"/>
  	 
</td></tr>
  </table>
  </td>
  </tr>
</table>
<table width="97.7%" border="0" height="70" cellspacing="0" cellpadding="0" style="background-image:url(${resourcePath}/images/bk.jpg);" >
  <tr>
	<td align="center">
	<input name="yuran" type="button"  value="保　 存"  onclick="submitCreate()"/>　　　
	<#if rollcallDefer?exists><#if rollcallDefer.id!=-1><input name="print" type="button"  value="打   印"  onclick="javascript:window.location='loadRollcallDeferAgreeCha.xhtml?rollcallDefer.id=${rollcallDefer.id}'"/>　　　</#if></#if>
	<input name="print" type="button"  value="返   回"  onclick="javascript:history.back(-1);"/></td>
  </tr>
</table>
<input type="hidden" name="rollcallDefer.daRollcallCompany.id" value="${rollcallCompany.id}">
<input type="hidden" name="rollcallDefer.id" value="${rollcallDefer.id}">
<input type="hidden" name="rollcallDefer.type" value="${rollcallDefer.type}">
<input type="hidden" name="rollcallDefer.done" value="true">
<input type="hidden" name="rollcallDefer.isAgree" value="${rollcallDefer.isAgree}">
</form>
</#escape> 
<@fkMacros.pageFooter />