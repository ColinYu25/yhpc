<@fkMacros.pageHeaderPrint />
<#escape x as (x)!>
<table width="97.7%" border="0" height="70" cellspacing="0" cellpadding="0" style="background-image:url(${resourcePath}/images/bk.jpg);" >
  <tr>
	<td align="center">
	<input name="yuran" type="button"  value="打印预览"  onclick="javascript:doPrint('printPreview');"/>　　　
	<input name="print" type="button"  value="打   印"  onclick="javascript:if(confirm('   确定要打印吗?')){doPrint('print');}"/>　　　
	<input name="print" type="button"  value="返   回"  onclick="javascript:history.back(-1);"/></td>
  </tr>
</table>
<div id='page1'>
<table width="97.7%" border="0"  cellpadding="0" cellspacing="0"  style="background-image:url(${resourcePath}/images/bk.jpg);">
	<tr>
	<td align="center">
<table width="700" border="0" cellpadding="0" cellspacing="0">
	<tr><td  align="center"><br><strong style="font-size:23px;line-height:40px;">宁波市安全生产重大事故隐患治理<br>延期申请书</strong></td></tr>
	<tr height="5"><td>&nbsp;</td></tr>
</table>
<table width="700" border="0" cellpadding="0" cellspacing="0">
	<tr>
	<td align="right" style="font-size:18px;line-height:25px;"><#if rollcallDefer?if_exists.companyApplyTime?exists>${rollcallDefer.companyApplyTime?string('yyyy年MM月dd日')}<#else>年　　月　　日</#if></td>
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
	  <td colspan="3" align="left" id="area_value">&nbsp;</td>
	  </tr>
	  <tr>
	<tr height="40" style="font-size:18px;line-height:25px;vertical-align:middle;">
	  <th>联 系 人</th>
	  <td width="33%">&nbsp;${danger.linkMan}</td> 
	  <th style="width:12%;">联系电话</th>
	  <td width="33%">&nbsp;${danger.linkTel}</td>
	</tr>
	<tr height="160" style="font-size:18px;line-height:25px;">
	  <th>存在隐患<br>及治理情<br>况简述</th><td colspan="3" align="left">　　${danger.description}&nbsp;</td>
	</tr>
	<tr height="160" style="font-size:18px;line-height:25px;">
	  <th>申请延期<br>理由及防<br>控措施</th><td colspan="3" align="left">　　${rollcallDefer.deferReason}&nbsp;</td>
	</tr>
	<tr height="70" style="font-size:18px;line-height:25px;">
	  <th>申请延期<br>日期至</th>
	  <td colspan="3">&nbsp;<#if rollcallDefer?if_exists.deferTime?exists>${rollcallDefer.deferTime?string('yyyy年MM月dd日')}<#else><div align="center">年　　月　　日</div></#if>
	  </td>
	</tr>
</table>
<table width="700" border="0" cellpadding="0" cellspacing="0">
	<tr height="5"><td>&nbsp;</td></tr>
	<tr>
	<td align="left" style="font-size:18px;line-height:25px;">　主要负责人（签字）：${rollcallDefer.presideMan}</td>
	</tr>
</table>
</div>
<script>
var areaObj = new Area("${AreaXmlUrl}");
<#if company.secondArea?exists><#if company.secondArea!=0>get("area_value").innerHTML="&nbsp;"+areaObj.getArea("${company.secondArea}")[0];</#if></#if>
<#if company.thirdArea?exists><#if company.thirdArea!=0>get("area_value").innerHTML+=areaObj.getArea("${company.thirdArea}")[0];</#if></#if>
printParam(10,0,0,0,1);
</script>
</#escape>
<@fkMacros.pageFooter />