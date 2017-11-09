<@fkMacros.pageHeaderPrint/>
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
  <table width="570" height="350" border="0" cellpadding="0" cellspacing="0">
  <tr><td align="center"><br><strong style="font-size:23px;line-height:40px;">宁波市安全生产重大事故隐患治理<br>验收申请书</strong></td></tr>
  <tr><td height="50">&nbsp;</td></tr>
  <tr><td style="font-size:18px;line-height:50px;" align="left">
  　　<span style="text-decoration:underline;">${rollcallDefer.govment}：</span><br>
  　　根据（<span style="text-decoration:underline;"><#if rollcallCompany.wordOne?exists>${rollcallCompany.wordOne}<#else>　　　　　　　　</#if></span>字〔20<span style="text-decoration:underline;"><#if rollcallCompany.wordYear?exists>${rollcallCompany.wordYear}<#else>　</#if></span>〕第<span style="text-decoration:underline;"><#if rollcallCompany.wordTwo?exists>${rollcallCompany.wordTwo}<#else>　　</#if></span>号）通知书要求，我单位存在的<span style="text-decoration:underline;">${danger.description}。</span><br>
  	 　　重大事故隐患现已治理完毕，特申请验收。<br><br><br><br><br>
　　　　　　　　　　　　　　　　　　　　治理责任单位（盖章）<br>
　　　　　　　　　　　　　　　　　　　　　　<#if rollcallDefer.companyApplyTime?exists>${rollcallDefer.companyApplyTime?string('yyyy年MM月dd日')}<#else>年　　月　　日</#if>
  	 
</td></tr>
  </table>
  </td>
  </tr>
</table>
</div>
<script language="javascript">
printParam(10,0,0,0,1);
</script>
</#escape>
<@fkMacros.pageFooter />