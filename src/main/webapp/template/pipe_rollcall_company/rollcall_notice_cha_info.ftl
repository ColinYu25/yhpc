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
  <table width="570" border="0" cellpadding="0" cellspacing="0">
  <tr><td align="center"><br><strong style="font-size:23px;line-height:40px;">宁波市安全生产重大事故隐患挂牌督办<br>通　　知　　书</strong><br></td></tr>
  <tr height="50"><td>&nbsp;</td></tr>
  <tr><td style="font-size:18px;line-height:40px;" align="left">
  <span style="text-decoration:underline;"><#if rollcallCompany.wordOne?exists>${rollcallCompany.wordOne}<#else>　　　　　　　　</#if></span>字〔20<span style="text-decoration:underline;"><#if rollcallCompany.wordYear?exists>${rollcallCompany.wordYear}<#else>　</#if></span>〕第<span style="text-decoration:underline;"><#if rollcallCompany.wordTwo?exists>${rollcallCompany.wordTwo}<#else>　　</#if></span>号　　　　　　签发人：<span style="text-decoration:underline;">${rollcallCompany.signatory}</span></td></tr>
  <tr><td height="10">&nbsp;</td></tr>
  <tr><td style="font-size:18px;line-height:50px;" align="left">
  <span style="text-decoration:underline;">${danger.pipeLine.daPipelineCompanyinfo.company.companyName}：</span><br>
  　　经检查，你单位存在以下重大事故隐患：<span style="text-decoration:underline;">${rollcallCompany.daPipeDanger.description}。</span><br>
  　　依据相关法律法规和《宁波市重大事故隐患治理挂牌督办暂行办法》，决定对你单位上述重大事故隐患治理工作实施挂牌督办，责令你单位于<#if rollcallCompany.finishTime?exists><span style="text-decoration:underline;">${rollcallCompany.finishTime?string('yyyy年MM月dd日')}</span><#else><span style="text-decoration:underline;">　　</span>年<span style="text-decoration:underline;">　</span>月<span style="text-decoration:underline;">　</span>日</#if>前，将该事故隐患治理完毕。<br>
　　治理期间应做好以下工作：<br>
　　1．单位主要负责人应组织制定重大事故隐患治理方案并组织实施，并在<#if rollcallCompany.planTime?exists><span style="text-decoration:underline;">${rollcallCompany.planTime?string('yyyy年MM月dd日')}</span><#else><span style="text-decoration:underline;">　　</span>年<span style="text-decoration:underline;">　</span>月<span style="text-decoration:underline;">　</span>日</#if>前，将治理方案报我单位备案；<br>
　　2．自收到通知书之日起，每季度至少一次向我单位报告重大事故隐患治理进展情况；<br>
　　3．在治理期限届满前向我单位提交《宁波市安全生产重大事故隐患治理验收申请书》，申请验收。确不能在限期内治理完毕的，应在治理期限届满前15个工作日内向我单位提交《宁波市安全生产重大事故隐患治理延期申请表》，申请延期；<br>
</td></tr>
 </table>
  </td>
  </tr>
</table>
</div>
<div id='page2'>
<table width="97.7%" border="0"  cellpadding="0" cellspacing="0"  style="background-image:url(${resourcePath}/images/bk.jpg);">
  <tr>
  <td align="center">
  <table width="570" border="0" cellpadding="0" cellspacing="0">
  <tr><td style="font-size:18px;line-height:50px;" align="left"><br>
　　4．治理期间，落实安全防控措施，切实防止事故发生。<br><br><br>
　　　　　　　　　　　　　　　　　　　　　挂牌督办单位（章）<br>
　　　　　　　　　　　　　　　　　　　　　　<#if rollcallCompany.rollcallUnitTime?exists>${rollcallCompany.rollcallUnitTime?string('yyyy年MM月dd日')}<#else>年　　月　　日</#if><br>
　送达人（签字）：${rollcallCompany.sendoffMan}<br>
　治理责任单位主要负责人（签收）：${rollcallCompany.signinMan}<br>
　　　　　　　　　　　　　　　　　　　　　　<#if rollcallCompany.signinTime?exists>${rollcallCompany.signinTime?string('yyyy年MM月dd日')}<#else>年　　月　　日</#if><br>
</td></tr>
  <tr><td style="font-size:14px;line-height:20px;" align="left">
　注：本通知书一式二份，督办单位、治理责任单位各执一份。<br>
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