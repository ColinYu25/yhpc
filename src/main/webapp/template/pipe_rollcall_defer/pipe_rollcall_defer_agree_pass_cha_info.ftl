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
  <tr><td align="center"><br><strong style="font-size:23px;line-height:40px;">宁波市安全生产重大事故隐患挂牌督办<br>验收意见书</strong></td></tr>
  <tr><td height="50">&nbsp;</td></tr>
  <tr><td style="font-size:18px;line-height:50px;" align="left">
  <span style="text-decoration:underline;"><#if rollcallCompany.wordOne?exists>${rollcallCompany.wordOne}<#else>　　　　　　　　</#if></span>字〔20<span style="text-decoration:underline;"><#if rollcallCompany.wordYear?exists>${rollcallCompany.wordYear}<#else>　</#if></span>〕第<span style="text-decoration:underline;"><#if rollcallCompany.wordTwo?exists>${rollcallCompany.wordTwo}<#else>　　</#if></span>号　　　　　　签发人：<span style="text-decoration:underline;">${rollcallDefer.signatory}</span></td></tr>
  <tr><td height="10">&nbsp;</td></tr>
  <tr><td style="font-size:18px;line-height:50px;" align="left">
  <span style="text-decoration:underline;">${danger.pipeLine.daPipelineCompanyinfo.company.companyName}：</span><br>
  　　根据你单位<span style="text-decoration:underline;">${rollcallDefer.createTime?string('yyyy年MM月dd日')}</span>提出的《宁波市安全生产重大事故隐患治理验收申请书》，经对你单位存在的<span style="text-decoration:underline;">${danger.description}。</span><br>
  	 　　重大事故隐患治理情况进行验收，意见如下（见打√栏）：<br>
  	 <#if rollcallDefer?if_exists.isAgree?if_exists==1>
		<input type="checkbox" checked>验收合格。</td></tr>
		<tr><td height="6">&nbsp;</td></tr>
  		<tr><td style="font-size:18px;line-height:50px;" align="left">
		<input type="checkbox">验收不合格。<span style="text-decoration:underline;">　　　　　　　　　　　　　　　　　&nbsp;&nbsp;</span><br><br>
	<#elseif rollcallDefer?if_exists.isAgree?if_exists==0>
		<input type="checkbox">验收合格。</td></tr>
		<tr><td height="6">&nbsp;</td></tr>
  		<tr><td style="font-size:18px;line-height:50px;" align="left">
		<input type="checkbox" checked>验收不合格。<span style="text-decoration:underline;">${rollcallDefer.remark}。</span><br><br>
	</#if>
　　　　　　　　　　　　　　　　　　　　　挂牌督办单位（盖章）<br>
　　　　　　　　　　　　　　　　　　　　　　<#if rollcallDefer.rollcallUnitTime?exists>${rollcallDefer.rollcallUnitTime?string('yyyy年MM月dd日')}<#else>年　　月　　日</#if><br>
送达人（签字）：${rollcallDefer.sendoffMan}<br>       
治理责任单位主要负责人（签收）：${rollcallDefer.signinMan}<br>
　　　　　　　　　　　　　　　　　　　　　　<#if rollcallDefer.signinTime?exists>${rollcallDefer.signinTime?string('yyyy年MM月dd日')}<#else>年　　月　　日</#if><br>
</td></tr>
<tr><td style="font-size:14px;line-height:20px;" align="left">
注：本通知书一式三份，治理责任单位、督办单位、同级政府安委办、各执一份。
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