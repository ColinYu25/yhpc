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
</head>
<body>
<center>
<table width="30%" border="0" height="30" cellspacing="0" cellpadding="0">
  <tr><td height="20">&nbsp;</td></tr>
  <tr>
	<td align="center">
		<input name="yuran" type="button"  value="打印预览"  onclick="javascript:doPrint('printPreview');"/>　　　　　　　　　　　　　　
	</td>
	<td align="center">
		<input name="print" type="button"  value="打   印"  onclick="javascript:if(confirm('确定要打印吗?')){doPrint('print');}"/>　　　　　　　　　　　　　　
	</td>
	<td align="center">
		<input name="print" type="button" value="返   回" onclick="history.back(-1);">
	</td>
  </tr>
</table>
</center>
<div id='page1'>
<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr align="center">
	     <th colspan="2" height="50" style="font-size:20px;line-height:30px;"><input type="text" id="year" onChange="winLocation();" value="${statistic.year}年" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
    其他重点行业领域安全生产隐患排查治理情况统计表</th>
    </tr>
	<tr height="18" style="font-size:12px">
	  <td  width="70%">　　填报单位（章）:</td>
	  <td nowrap="nowrap">截止到　　　　年　　月　　日为止</td>
	</tr>
</table>
<table width="97%" cellspacing="0" cellpadding="0" border="0"  class="table_list">
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
		<#if statistics?exists>
		<#list statistics as s>
       <tr align="center">
            <td nowrap="true"><div align="left">&nbsp;${s.enumName}</div></td>
            <td>${s.companyNum}&nbsp;</td>
            <td>${s.troubNum}&nbsp;</td>
            <td>${s.troubCleanNum}&nbsp;</td>
            <td><#if s.troubNum==0>/<#else>#{(s.troubCleanNum/s.troubNum*100);M1}%</#if></td>
            <td>${s.bigTroubNum}</td>
            <td>${s.bigTroubNum-s.bigTroubCleanNum}</td>
            <td><#if s.bigTroubNum==0>/<#else>#{((s.bigTroubNum-s.bigTroubCleanNum)/s.bigTroubNum*100);M1}%</#if></td>
            <td>${s.bigTroubCleanNum}</td>
            <td>${s.target}</td>
            <td>${s.goods}</td>
            <td>${s.resource}</td>
            <td>${s.finishDate}</td>
            <td>${s.safetyMethod}</td>
            <td>#{s.governMoney;M1}</td>
		</tr>
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
		<tr  align="center">
            <td>&nbsp;合　　　计</td>
            <td id="companyNumAll">0</td>
            <td id="troubNumAll">0</td>
            <td id="troubCleanNumAll">0</td>
            <td id="rateAll1">/</td>
            <td id="bigTroubNumAll">0</td>
            <td id="bigTroubCleanNumAll">0</td>
            <td id="rateAll2">/</td>
            <td id="hiddenNumAll">0</td>
            <td id="targetAll">0</td>
            <td id="goodsAll">0</td>
            <td id="resourceAll">0</td>
            <td id="finishDateAll">0</td>
            <td id="safetyMethodAll">0</td>
            <td id="governMoneyAll">0</td>
		</tr>
    </table>
    <table width="97.5%" border="0" height="20" cellspacing="0" cellpadding="0">
	  <tr>
		<td align="center" style="font-size:12px;line-height:30px;">单位负责人（签字）：　　　　　　　　　填表人（签字）：　　　　　　　　　联系电话：　　　　　　　　　填报日期：　　　　年　　月　　日</td>
	  </tr>
	</table>
  </div>
<script language="javascript">
function winLocation(){
	window.location="?statistic.year="+get("year").value.substring(0,4);
}
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
get("governMoneyAll").innerHTML="#{governMoneyAll;M1}";
printParam(5,8,0,0,2);
</script>
 </#escape>
<@fkMacros.pageFooter />
