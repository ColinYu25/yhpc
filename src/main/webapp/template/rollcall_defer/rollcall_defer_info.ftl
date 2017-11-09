<@fkMacros.pageHeader />
<#escape x as (x)!> 
<#if rollcallDefer?exists>
	<#if rollcallDefer.id!=-1>
  		<#assign url='updateRollcallDefer.xhtml'>
  	<#else>
  		<#assign url='createRollcallDefer.xhtml'>
  	</#if>
 <#else>
  	<#assign url='createRollcallDefer.xhtml'>
</#if>
<script type="text/javascript">
function submitCreate(){
 	if(formValidator.validate()){
  		  var obj=get("rollcallDeferForm");
  		  obj.action="${url}";
 		  obj.submit();
 	}
}

var areaObj = new Area("${AreaXmlUrl}");
</script>
<div class="menu">
	<div style="float:left;">
  	<ul>
	<li id="img_left"></li>
	<#if !viewRole?? || !viewRole>
	<li id="img_save"><a href="#" class="b1" onClick="submitCreate();"><b>保存</b></a></li>
	</#if>
	<li id="img_refurbish"><a href="javascript:window.location.reload()" class="b4"><b>刷新</b></a></li>
	<li id="img_return"><a href="javascript:history.back(-1);" class="b6"><b>返回</b></a></li>
	</ul>
	</div>	
</div>
<@fkMacros.formValidator 'rollcallDeferForm' />
<form id="rollcallDeferForm" name="rollcallDeferForm" action="" method="post">
<table width="98%" border="0" cellpadding="0" cellspacing="0">
<!--
<#if rollcallDefer?exists>
<#if rollcallDefer.id!=-1>
	<tr>
	  <th>审批情况</th>
	  <td colspan="3"><#if rollcallDefer.isAgree==0>不同意隐患治理延期<#elseif rollcallDefer.isAgree==1>同意隐患治理延期<#else>暂未审批</#if>
	  </td>
	</tr>
</#if></#if>
-->
</table>
<table width="97.7%" border="0"  cellpadding="0" cellspacing="0"  style="background-image:url(${resourcePath}/images/bk.jpg);">
	<tr>
	<td align="center">
	<table width="700" border="0" cellpadding="0" cellspacing="0">
	<tr><td><br><strong style="font-size:23px;line-height:40px;">宁波市安全生产重大事故隐患治理<br>延期申请书</strong></td></tr>
	<tr height="5"><td>&nbsp;</td></tr>
</table>
<table width="700" border="0" cellpadding="0" cellspacing="0">
	<tr>
	<td align="right" style="font-size:18px;line-height:25px;"><input type="text" id="companyApplyTime" value="${rollcallDefer.companyApplyTime?string('yyyy年MM月dd日')}" onfocus="WdatePicker({dateFmt:'yyyy年MM月dd日',vel:'companyApplyTime_2',minDate:'2000-01-01'})" class="Wdate" size="14" maxlength="10"><input type="hidden"  id="companyApplyTime_2"  name="rollcallDefer.companyApplyTime"  value="${rollcallDefer.companyApplyTime?date}"/></td>
	<td width="8%">&nbsp;</td></tr>
</table>
<table width="700" border="1" cellpadding="0" cellspacing="0">
	<tr height="80" style="font-size:18px;line-height:25px;">
	  <th width="14%">治理责<br>任单位</th>
	  <td colspan="3" align="left" style="vertical-align:bottom;">&nbsp;${company.companyName}<p>
	  　　　　　　　　　　　　　　　　　　　　　　　　　（盖章）</td>
	  </tr>
	  <tr height="40" style="font-size:18px;line-height:25px;vertical-align:middle;">
	  <th>所在区域</th>
	  <td colspan="3" align="left">&nbsp;<#if company.secondArea?exists><#if company.secondArea!=0><script type="text/javascript">document.write(areaObj.getArea("${company.secondArea}")[0]);</script></#if></#if>
	  <#if company.thirdArea?exists><#if company.thirdArea!=0><script type="text/javascript">document.write(areaObj.getArea("${company.thirdArea}")[0]);</script></#if></#if>&nbsp;</td>
	  </tr>
	  <tr>
	<tr height="40" style="font-size:18px;line-height:25px;vertical-align:middle;">
	  <th>联 系 人</th>
	  <td width="33%">&nbsp;${danger.linkMan}</td> 
	  <th style="width:12%;">联系电话</th>
	  <td width="33%">&nbsp;${danger.linkTel}</td>
	</tr>
	<tr height="160" style="font-size:18px;line-height:25px;">
	  <th>存在隐患<br>及治理情<br>况简述</th><td colspan="3" align="left">　　${danger.description}</td>
	</tr>
	<tr height="160" style="font-size:18px;line-height:25px;">
	  <th>申请延期<br>理由及防<br>控措施</th><td colspan="3" align="left"><textarea id="deferReason" name="rollcallDefer.deferReason" rows="10" cols="71">${rollcallDefer.deferReason}</textarea></td>
	</tr>
	<tr height="70" style="font-size:18px;line-height:25px;">
	  <th>申请延期<br>日期至</th>
	  <td colspan="3" align="left"><input type="text" id="deferTime" value="${rollcallDefer.deferTime?string('yyyy年MM月dd日')}" onfocus="WdatePicker({dateFmt:'yyyy年MM月dd日',vel:'deferTime_2',minDate:'2000-01-01'})" class="Wdate" size="14" maxlength="10">
    <input type="hidden" name="rollcallDefer.deferTime" id="deferTime_2" value="${rollcallDefer.deferTime?date}"/>
	  </td>
	</tr>
</table>
<table width="700" border="0" cellpadding="0" cellspacing="0">
	<tr height="5"><td>&nbsp;</td></tr>
	<tr>
	<td align="left" style="font-size:18px;line-height:25px;">　主要负责人（签字）：<input type="text"  id="presideMan"  name="rollcallDefer.presideMan" value="${rollcallDefer.presideMan}"  size="10" maxlength="10"></td>
	</tr>
</table>
<table width="97.7%" border="0" height="70" cellspacing="0" cellpadding="0" style="background-image:url(${resourcePath}/images/bk.jpg);" >
  <tr>
	<td align="center">
	<#if !viewRole?? || !viewRole>
	<input name="yuran" type="button"  value="保　 存"  onclick="submitCreate()"/>　
	</#if>　　
	<input name="print" type="button"  value="打   印"  onclick="javascript:window.location='createRollcallDeferCha.xhtml?rollcallCompany.id=${rollcallCompany.id}'"/>　　　
	<input name="print" type="button"  value="返   回"  onclick="javascript:history.back(-1);"/></td>
  </tr>
</table>
<input type="hidden" name="rollcallDefer.daRollcallCompany.id" value="${rollcallCompany.id}">
<input type="hidden" name="rollcallDefer.id" value="${rollcallDefer.id}">
<input type="hidden" name="rollcallDefer.type" value="${rollcallDefer.type}">
<input type="hidden" name="rollcallDefer.done" value="true">
<input type="hidden" name="rollcallDefer.isAgree" value="${rollcallDefer.isAgree}">
<input type="hidden" name="rollcallDefer.rollcallUnitTime" value="${rollcallDefer.rollcallUnitTime?date}">
<input type="hidden" name="rollcallDefer.sendoffMan" value="${rollcallDefer.sendoffMan}">
<input type="hidden" name="rollcallDefer.signinMan" value="${rollcallDefer.signinMan}">
<input type="hidden" name="rollcallDefer.signinTime" value="${rollcallDefer.signinTime?date}">
</form>
</#escape> 
<@fkMacros.pageFooter />