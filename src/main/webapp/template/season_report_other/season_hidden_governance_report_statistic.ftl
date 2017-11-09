<@fkMacros.pageHeaderPrint />
<#escape x as (x)!>
<script language="javascript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
<link href="${contextPath}/datePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.table_list{
	border-left-width: 1px;
	border-left-style: solid;
	border-left-color: #000;
	border-top-width: 1px;
	border-top-style: solid;
	border-top-color: #000;
	margin-top:5px;
	width:97%;
	background-color:#f7fcff;
}

.table_list th{FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#FFFFFF, EndColorStr=#b4dff8);}

.table_list td {
	font-size:12px;
	color:#000;
	height:12px;
	border-right-width: 1px;
	border-left-width: 1px;
	border-top-color: #FFF;
	border-right-color: #000;
	border-bottom-color: #000;
	border-left-color: #FFF;
	line-height:17px;
	padding-left:5px;
	padding-right:3px;
}

.table_list td p{font-size:12px;text-align:left; padding-left:5px; line-height:14px;}
-->
</style>
<table width="97.5%" border="0" height="30" cellspacing="0" cellpadding="0">
  <tr><td height="20">&nbsp;</td></tr>
  <#if seasonReportOther?if_exists.beginDate?if_exists!='1'>
  <tr>
	<td align="center">
	<input name="yuran" type="button"  value="打印预览"  onclick="javascript:doPrint('printPreview');"/>　　　　　　　　　　　　　　
	<input name="print" type="button"  value="打   印"  onclick="javascript:if(confirm('   确定要打印吗?')){doPrint('print');}"/>　　　　　　　　　　　　　　
	<input name="print" type="button"  value="发送数据"  onclick="javascript:window.location='loadHiddenGovernanceReportStatistic.xhtml?seasonReportOther.beginDate=1'"/>
	</td>
  </tr>
  <#else>
  <tr>
	<td align="center">
	<input name="print" type="button"  value="确定发送数据"  onclick="javascript:if(confirm('   确定要发送数据吗?')){}"/>　　　　　　　　　　　　　　
	</td>
  </tr>
  </#if>
</table>
<div id='page1'>
<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr align="center">
	     <th colspan="2" height="50" style="font-size:20px;line-height:30px;"> <input type="text" id="year" onChange="winLocation();" value="${seasonReportOther.nowYear}年" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
    矿山、危化、烟花爆竹、冶金等行业和领域安全生产隐患排查治理情况报表</th>
    </tr>
	<tr height="18" style="font-size:12px">
	  <td  width="70%">　　填报单位（章）:</td>
	  <td nowrap="nowrap">截止到　　　　年　　月　　日为止</td>
	</tr>
</table>
<table width="97.5%" cellspacing="0" cellpadding="0" border="0"  class="table_list">
        <tr height="20" align="center" style="background:#F0F0F0;color:#333;">
          <td width="18%" rowspan="5" align="center">行业和领域</td>
          <td>&nbsp;</td>
          <td colspan="3"><center>一般隐患</center></td>
          <td colspan="10"><center>重大隐患</center></td>
        </tr>
        <tr height="20" align="center" style="background:#F0F0F0;color:#333;">
          <td width="4%" rowspan="3" ><center>排查<br>单位<br>数</center></td>
          <td width="3.5%" rowspan="3"><center>隐患<br>数</center></td>
          <td width="3.5%" rowspan="3"><center>已经<br>整改<br>数</center></td>
          <td width="3.5%" rowspan="3"><center>整改<br>率</center></td>
          <td height="20" colspan="3"><center>排查治理重大隐患</center></td>
          <td colspan="7"><center>列入治理计划的重大隐患</center></td>
        </tr>
        <tr height="20" align="center" style="background:#F0F0F0;color:#333;">
          <td width="3.5%" rowspan="2"><center>重大<br>隐患<br>数</center></td>
          <td width="3.5%" rowspan="2"><center>已整<br>改销<br>号数</center></td>
          <td width="3.5%" rowspan="2"><center>整改<br>率</center></td>
          <td width="3.5%" rowspan="2"><center>重大<br>隐患<br>数</center></td>
          <td height="20" colspan="5"><center>其中</center></td>
          <td width="6%" rowspan="2"><center>累计落实<br>治理资金</center></td>
        </tr>
        <tr height="19" align="center" style="background:#F0F0F0;color:#333;">
          <td width="4%"><center>落实治理<br>目标任务</center></td>
          <td width="4%"><center>落实治理<br>经费物资</center></td>
          <td width="4%"><center>落实治理<br>机构人员</center></td>
          <td width="4%"><center>落实治理<br>时间要求</center></td>
          <td width="6%"><center>落实安全措施应急预案</center></td>
        </tr>        
        <tr height="20" align="center" style="background:#F0F0F0;color:#333;">
          <td height="20"><center>(家)</center></td>
          <td><center>(项)</center></td>
          <td><center>项)</center></td>
          <td><center>(%)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(%)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td nowrap="true"><center>(万元)</center></td>
        </tr>
        <#assign companyNum1=0>
        <#assign troubNum1=0>
        <#assign troubCleanNum1=0>
        <#assign bigTroubNum1=0>
        <#assign bigTroubCleanNum1=0>
        <#assign target1=0>
        <#assign goods1=0>
        <#assign resource1=0>
        <#assign finishDate1=0>
        <#assign safetyMethod1=0>
        <#assign governMoney1=0>
        <#assign companyNum2=0>
        <#assign troubNum2=0>
        <#assign troubCleanNum2=0>
        <#assign bigTroubNum2=0>
        <#assign bigTroubCleanNum2=0>
        <#assign target2=0>
        <#assign goods2=0>
        <#assign resource2=0>
        <#assign finishDate2=0>
        <#assign safetyMethod2=0>
        <#assign governMoney2=0>
        
        <#assign companyNumAll=0>
        <#assign troubNumAll=0>
        <#assign troubCleanNumAll=0>
        <#assign bigTroubNumAll=0>
        <#assign bigTroubCleanNumAll=0>
        <#assign targetAll=0>
        <#assign goodsAll=0>
        <#assign resourceAll=0>
        <#assign finishDateAll=0>
        <#assign safetyMethodAll=0>
        <#assign governMoneyAll=0>
        
		<#if seasonReportOthers?exists>
		<#list seasonReportOthers as s>
		<#if s_index=1>
			<tr height="20"  align="center">
            <td height="20"><div align="left">&nbsp;尾矿库</div></td>
            <td height="20">0</td>
            <td height="20">0</td>
            <td height="20">0</td>
            <td height="20">/</td>
            <td height="20">0</td>
            <td height="20">0</td>
            <td height="20">/</td>
            <td height="20">0</td>
            <td height="20">0</td>
            <td height="20">0</td>
            <td height="20">0</td>
            <td height="20">0</td>
            <td height="20">0</td>
            <td height="20">0</td>
		</tr>
		</#if>
		<#if s_index=3>
			<tr height="20"  align="center">
            <td height="20"><div align="left">&nbsp;危险化学品企业</div></td>
            <td height="20" id="companyNum1">0</td>
            <td height="20" id="troubNum1">0</td>
            <td height="20" id="troubCleanNum1">0</td>
            <td height="20" id="wrate1">/</td>
            <td height="20" id="bigTroubNum1">0</td>
            <td height="20" id="bigTroubCleanNum1">0</td>
            <td height="20" id="wrate2">/</td>
            <td height="20" id="hiddenNum1">0</td>
            <td height="20" id="target1">0</td>
            <td height="20" id="goods1">0</td>
            <td height="20" id="resource1">0</td>
            <td height="20" id="finishDate1">0</td>
            <td height="20" id="safetyMethod1">0</td>
            <td height="20" id="governMoney1">0</td>
		</tr>
		</#if>
		<#if s_index=6>
			<tr height="20"  align="center">
            <td height="20"><div align="left">&nbsp;&nbsp;&nbsp;使用危化品的生产单位</div></td>
            <td height="20" >0</td>
            <td height="20" >0</td>
            <td height="20" >0</td>
            <td height="20">/</td>
            <td height="20" >0</td>
            <td height="20" >0</td>
            <td height="20">/</td>
            <td height="20">0</td>
            <td height="20">0</td>
            <td height="20" >0</td>
            <td height="20">0</td>
            <td height="20">0</td>
            <td height="20" >0</td>
            <td height="20" >0</td>
			</tr>
			<tr height="20"  align="center">
            <td height="20" nowrap="true"><div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用危化品的化工企业</div></td>
            <td height="20" >0</td>
            <td height="20" >0</td>
            <td height="20" >0</td>
            <td height="20">/</td>
            <td height="20" >0</td>
            <td height="20" >0</td>
            <td height="20">/</td>
            <td height="20">0</td>
            <td height="20">0</td>
            <td height="20" >0</td>
            <td height="20">0</td>
            <td height="20">0</td>
            <td height="20" >0</td>
            <td height="20" >0</td>
			</tr>
			<tr height="20"  align="center">
            <td height="20" nowrap="true"><div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用危化品的医药生产企业</div></td>
            <td height="20" >0</td>
            <td height="20" >0</td>
            <td height="20" >0</td>
            <td height="20">/</td>
            <td height="20" >0</td>
            <td height="20" >0</td>
            <td height="20">/</td>
            <td height="20">0</td>
            <td height="20">0</td>
            <td height="20" >0</td>
            <td height="20">0</td>
            <td height="20">0</td>
            <td height="20" >0</td>
            <td height="20" >0</td>
			</tr>
			<tr height="20"  align="center">
            <td height="20" nowrap="true"><div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用危化品的其他企业或单位</div></td>
            <td height="20" >0</td>
            <td height="20" >0</td>
            <td height="20" >0</td>
            <td height="20">/</td>
            <td height="20" >0</td>
            <td height="20" >0</td>
            <td height="20">/</td>
            <td height="20">0</td>
            <td height="20">0</td>
            <td height="20" >0</td>
            <td height="20">0</td>
            <td height="20">0</td>
            <td height="20" >0</td>
            <td height="20" >0</td>
			</tr>
		</#if>
		<#if s_index=7>
		<tr height="20"  align="center">
            <td height="20"><div align="left">&nbsp;烟花爆竹企业</div></td>
            <td height="20" id="companyNum2">0</td>
            <td height="20" id="troubNum2">0</td>
            <td height="20" id="troubCleanNum2">0</td>
            <td height="20" id="yrate1">/</td>
            <td height="20" id="bigTroubNum2">0</td>
            <td height="20" id="bigTroubCleanNum2">0</td>
            <td height="20" id="yrate2">/</td>
            <td height="20" id="hiddenNum2">0</td>
            <td height="20" id="target2">0</td>
            <td height="20" id="goods2">0</td>
            <td height="20" id="resource2">0</td>
            <td height="20" id="finishDate2">0</td>
            <td height="20" id="safetyMethod2">0</td>
            <td height="20" id="governMoney2">0</td>
		</tr>
		</#if>
       <tr height="20"  align="center">
            <td height="20"  nowrap="true"><div align="left">&nbsp;<#if s_index gt 2 && s_index lt 10>&nbsp;&nbsp;</#if><#if s_index=seasonReportOthers?size-1>其他单位<#else>${s.enumName}</#if></div></td>
            <td height="20">${s.companyNum}&nbsp;</td>
            <td height="20">${s.troubNum}&nbsp;</td>
            <td height="20">${s.troubCleanNum}&nbsp;</td>
            <td height="20"><#if s.troubNum==0>/<#else>#{(s.troubCleanNum/s.troubNum*100);M1}%</#if></td>
            <td height="20">${s.bigTroubNum}</td>
            <td height="20">${s.bigTroubNum-s.bigTroubCleanNum}</td>
            <td height="20"><#if s.bigTroubNum==0>/<#else>#{((s.bigTroubNum-s.bigTroubCleanNum)/s.bigTroubNum*100);M1}%</#if></td>
            <td height="20">${s.bigTroubCleanNum}</td>
            <td height="20">${s.target}</td>
            <td height="20">${s.goods}</td>
            <td height="20">${s.resource}</td>
            <td height="20">${s.finishDate}</td>
            <td height="20">${s.safetyMethod}</td>
            <td height="20">${s.governMoney}</td>
		</tr>
		<#if s_index gt 2 && s_index lt 7>
		<#assign companyNum1=companyNum1+s.companyNum>
		<#assign troubNum1=troubNum1+s.troubNum>
		<#assign troubCleanNum1=troubCleanNum1+s.troubCleanNum>
		<#assign bigTroubNum1=bigTroubNum1+s.bigTroubNum>
		<#assign bigTroubCleanNum1=bigTroubCleanNum1+s.bigTroubCleanNum>
		<#assign target1=target1+s.target>
		<#assign goods1=goods1+s.goods>
		<#assign resource1=resource1+s.resource>
		<#assign finishDate1=finishDate1+s.finishDate>
		<#assign safetyMethod1=safetyMethod1+s.safetyMethod>
		<#assign governMoney1=governMoney1+s.governMoney>
		</#if>
		<#if s_index gt 6 && s_index lt 10>
		<#assign companyNum2=companyNum2+s.companyNum>
		<#assign troubNum2=troubNum2+s.troubNum>
		<#assign troubCleanNum2=troubCleanNum2+s.troubCleanNum>
		<#assign bigTroubNum2=bigTroubNum2+s.bigTroubNum>
		<#assign bigTroubCleanNum2=bigTroubCleanNum2+s.bigTroubCleanNum>
		<#assign target2=target2+s.target>
		<#assign goods2=goods2+s.goods>
		<#assign resource2=resource2+s.resource>
		<#assign finishDate2=finishDate2+s.finishDate>
		<#assign safetyMethod2=safetyMethod2+s.safetyMethod>
		<#assign governMoney2=governMoney2+s.governMoney>
		</#if>
		<#assign companyNumAll=companyNumAll+s.companyNum>
		<#assign troubNumAll=troubNumAll+s.troubNum>
		<#assign troubCleanNumAll=troubCleanNumAll+s.troubCleanNum>
		<#assign bigTroubNumAll=bigTroubNumAll+s.bigTroubNum>
		<#assign bigTroubCleanNumAll=bigTroubCleanNumAll+s.bigTroubCleanNum>
		<#assign targetAll=targetAll+s.target>
		<#assign goodsAll=goodsAll+s.goods>
		<#assign resourceAll=resourceAll+s.resource>
		<#assign finishDateAll=finishDateAll+s.finishDate>
		<#assign safetyMethodAll=safetyMethodAll+s.safetyMethod>
		<#assign governMoneyAll=governMoneyAll+s.governMoney>
        </#list>
        </#if>
        <tr height="20"  align="center">
            <td height="20">&nbsp;合　　　计</td>
            <td height="20" id="companyNumAll">0</td>
            <td height="20" id="troubNumAll">0</td>
            <td height="20" id="troubCleanNumAll">0</td>
            <td height="20" id="rateAll1">/</td>
            <td height="20" id="bigTroubNumAll">0</td>
            <td height="20" id="bigTroubCleanNumAll">0</td>
            <td height="20" id="rateAll2">/</td>
            <td height="20" id="hiddenNumAll">0</td>
            <td height="20" id="targetAll">0</td>
            <td height="20" id="goodsAll">0</td>
            <td height="20" id="resourceAll">0</td>
            <td height="20" id="finishDateAll">0</td>
            <td height="20" id="safetyMethodAll">0</td>
            <td height="20" id="governMoneyAll">0</td>
		</tr>
    </table>
    <table width="97.5%" border="0" height="20" cellspacing="0" cellpadding="0">
	  <tr>
		<td align="center" style="font-size:12px;line-height:30px;">单位负责人（签字）：　　　　　　　　　填表人（签字）：　　　　　　　　　联系电话：　　　　　　　　　填报日期：　　　　年　　月　　日</td>
	  </tr>
	</table>
  </div>      
<script>
function winLocation(){
	window.location="?seasonReportOther.nowYear="+get("year").value.substring(0,4);
}
get("companyNum1").innerHTML="${companyNum1}";
get("troubNum1").innerHTML="${troubNum1}";
get("troubCleanNum1").innerHTML="${troubCleanNum1}";
if("${troubNum1}"=="0"){
	get("wrate1").innerHTML="/";
}else{
	var value=parseInt("${troubCleanNum1}")/parseInt("${troubNum1}")*100;
	get("wrate1").innerHTML=Math.round(value*Math.pow(10,1))/Math.pow(10,1)+"%";
}
get("bigTroubNum1").innerHTML="${bigTroubNum1}";
get("bigTroubCleanNum1").innerHTML="${bigTroubNum1-bigTroubCleanNum1}";
if("${bigTroubNum1}"=="0"){
	get("wrate2").innerHTML="/";
}else{
	var value=parseInt("${bigTroubNum1-bigTroubCleanNum1}")/parseInt("${bigTroubNum1}")*100;
	get("wrate2").innerHTML=Math.round(value*Math.pow(10,1))/Math.pow(10,1)+"%";
}
get("hiddenNum1").innerHTML=${bigTroubCleanNum1};
get("target1").innerHTML="${target1}";
get("goods1").innerHTML="${goods1}";
get("resource1").innerHTML="${resource1}";
get("finishDate1").innerHTML="${finishDate1}";
get("safetyMethod1").innerHTML="${safetyMethod1}";
get("governMoney1").innerHTML="${governMoney1}";

get("companyNum2").innerHTML="${companyNum2}";
get("troubNum2").innerHTML="${troubNum2}";
get("troubCleanNum2").innerHTML="${troubCleanNum2}";
if("${troubNum2}"=="0"){
	get("yrate1").innerHTML="/";
}else{
	var value=parseInt("${troubCleanNum2}")/parseInt("${troubNum2}")*100;
	get("yrate1").innerHTML=Math.round(value*Math.pow(10,1))/Math.pow(10,1)+"%";
}
get("bigTroubNum2").innerHTML="${bigTroubNum2}";
get("bigTroubCleanNum2").innerHTML="${bigTroubNum2-bigTroubCleanNum2}";
if("${bigTroubNum2}"=="0"){
	get("yrate2").innerHTML="/";
}else{
	var value=parseInt("${bigTroubNum2-bigTroubCleanNum2}")/parseInt("${bigTroubNum2}")*100;
	get("yrate2").innerHTML=Math.round(value*Math.pow(10,1))/Math.pow(10,1)+"%";
}
get("hiddenNum2").innerHTML=${bigTroubCleanNum2};
get("target2").innerHTML="${target2}";
get("goods2").innerHTML="${goods2}";
get("resource2").innerHTML="${resource2}";
get("finishDate2").innerHTML="${finishDate2}";
get("safetyMethod2").innerHTML="${safetyMethod2}";
get("governMoney2").innerHTML="${governMoney2}";

get("companyNumAll").innerHTML="${companyNumAll}";
get("troubNumAll").innerHTML="${troubNumAll}";
get("troubCleanNumAll").innerHTML="${troubCleanNumAll}";
if("${troubNumAll}"=="0"){
	get("rateAll1").innerHTML="/";
}else{
	var value=parseInt("${troubCleanNumAll}")/parseInt("${troubNumAll}")*100;
	get("rateAll1").innerHTML=Math.round(value*Math.pow(10,1))/Math.pow(10,1)+"%";
}
get("bigTroubNumAll").innerHTML="${bigTroubNumAll}";
get("bigTroubCleanNumAll").innerHTML="${bigTroubNumAll-bigTroubCleanNumAll}";
if("${bigTroubNumAll}"=="0"){
	get("rateAll2").innerHTML="/";
}else{
	var value=parseInt("${bigTroubNumAll-bigTroubCleanNumAll}")/parseInt("${bigTroubNumAll}")*100;
	get("rateAll2").innerHTML=Math.round(value*Math.pow(10,1))/Math.pow(10,1)+"%";
}
get("hiddenNumAll").innerHTML=${bigTroubCleanNumAll};
get("targetAll").innerHTML="${targetAll}";
get("goodsAll").innerHTML="${goodsAll}";
get("resourceAll").innerHTML="${resourceAll}";
get("finishDateAll").innerHTML="${finishDateAll}";
get("safetyMethodAll").innerHTML="${safetyMethodAll}";
get("governMoneyAll").innerHTML="${governMoneyAll}";

printParam(20,8,0,0,2);
</script>
 </#escape>
<@fkMacros.pageFooter />
