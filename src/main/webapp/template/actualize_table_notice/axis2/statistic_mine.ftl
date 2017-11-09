<@fkMacros.pageHeaderPrint />
<#escape x as (x)!>
<script language="javascript" type="text/javascript" src="${contextPath}/datePicker/WdatePicker.js"></script>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/jquery-1.3.2.js"></script>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/easydialog/easydialog.js"></script>
<script language="JavaScript" type="text/javascript" src="${resourcePath}/js/easydialog/easydialog.min.js"></script>
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
		<input name="print" type="button" value="<#if userDetail.secondArea==0>发送数据<#else>查看月统计</#if>" onclick="sendData();"/>
	</td>
	<td align="center">
		<input name="print" type="button"  value="旧版本"  onclick="checkOldData();"/>
	</td>
  </tr>
</table>
</center>
<div id='page1'>
<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr align="center">
	     <th colspan="2" height="50" style="font-size:20px;line-height:30px;"> <input type="text" id="year" onChange="winLocation();" value="${statistic.year}年" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
    煤矿、金属非金属矿山等工矿企业安全生产事故隐患排查治理情况月报表</th>
    </tr>
	<tr height="18" style="font-size:12px">
	  <td  width="70%">　　填报单位（章）:</td>
	  <td nowrap="nowrap">截止到　　　　年　　月　　日为止</td>
	</tr>
</table>
<table width="100%" cellpadding="0" cellspacing="0" class="table_list">
        <tr height="25">
          <td rowspan="4" align="center" width="20%">行业和领域</td>
          <td height="10" colspan=3 width="15%">开展排查治理事故<br/>隐患的生产经营单位</td>
          <td colspan="3" width="15%"><center>一般事故隐患</center></td>
          <td colspan="8" width="40%"><center> 重大事故隐患</center></td>
          <td rowspan="3" width="10%" nowrap><center>累计落实隐<br/>患治理资金</center></td>
        </tr>
        <tr height="25" style="background:#F0F0F0;color:#333;">
          <td rowspan="2" nowrap><center>应排查治<br/>理事故隐<br/>患的生产<br/>经营单位</center></td>
          <td rowspan="2" nowrap><center>实际排查<br/>治理事故<br/>隐患的生<br/>产经营单<br/>位</center></td>
          <td rowspan="2" nowrap><center>覆<br/>盖<br/>率</center></td>
          <td rowspan="2" nowrap><center>排查一般<br/>事故隐患</center></td>
          <td rowspan="2" nowrap><center>其中：<br/>已整改</center></td>
          <td rowspan="2" nowrap><center>整<br/>改<br/>率</center></td>
          <td height="10" colspan="3" nowrap><center>排查治理重大隐患</center></td>
          <td colspan="2" nowrap><center>列入治理计划</center></td>
          <td colspan="3" nowrap><center>其中：挂牌督办</center></td>
        </tr>
        <tr height="25">
          <td nowrap><center>排查重<br/>大事故<br/>隐患</center></td>
          <td nowrap><center>其中：<br/>已整改<br/>销号</center></td>
          <td nowrap><center>整<br/>改<br/>率</center></td>
          <td nowrap><center>列入治<br/>理计划<br/>的重大<br/>事故隐<br/>患</center></td>
          <td nowrap><center>其中：<br/>达到“<br/>五到位<br/>”要求<br/>的</center></td>
          <td nowrap><center>挂牌督<br/>办&nbsp;&nbsp;的<br/>重大事<br/>故隐患</center></td>
          <td nowrap><center>其中：<br/>省级挂<br/>牌督办</center></td>
          <td nowrap><center>其中：<br/>地市级<br/>挂牌督<br/>办</center></td>
        </tr>
        <tr height="25" style="background:#F0F0F0;color:#333;">
          <td height="20"><center>(家)</center></td>
          <td><center>(家)</center></td>
          <td><center>(%)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(%)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(%)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(万元)</center></td>
        </tr>
        <tr id="tr_heji" height="25"><td style="text-align: center"><strong>合&nbsp;&nbsp;&nbsp;&nbsp;计</strong></td>
		  <td align="center" id="company">${map["s_total"].allCompanyNum }</td>
		  <td align="center" id="zg1_1">${map["s_total"].companyNum }</td>
		  <td align="center" id="generalDanger"><#if map["s_total"].allCompanyNum==0>/<#else>#{(map["s_total"].companyNum/map["s_total"].allCompanyNum*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_total"].troubNum }</td>
		  <td align="center" id="zg_1">${map["s_total"].troubCleanNum }</td>
		  <td align="center" id="bt"><#if map["s_total"].troubNum==0>/<#else>#{(map["s_total"].troubCleanNum/map["s_total"].troubNum*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_total"].bigTroubNum }</td>
		  <td align="center">${map["s_total"].bigTroubNum - map["s_total"].bigTroubCleanNum }</td>
		  <td align="center" id="btng"><#if map["s_total"].bigTroubNum==0>/<#else>#{((map["s_total"].bigTroubNum-map["s_total"].bigTroubCleanNum)/map["s_total"].bigTroubNum*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_total"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_total"].ddng5Num }</td>
		  <td align="center" id="pgp">${map["s_total"].proviceRcNum + map["s_total"].cityRcNum+ map["s_total"].qtRcNum }</td>
		  <td align="center" id="pgp">${map["s_total"].proviceRcNum }</td>
		  <td align="center" id="cgp">${map["s_total"].cityRcNum }</td>
		  <td align="center" id="planMoney">
		   <#assign total_governMoney = (map["s_total"].governMoney + map["s_total"].normalGovernMoney )>
		     ${total_governMoney?if_exists?string.number}
		  </td>
		</tr>
		<tr id="tr_1" height="25">
		  <td width="18%" class="td_left" id="a" nowrap align="center"><p>1.煤矿企业</p></td>
		  <td align="center" id="shouldTroubleshooting">&nbsp;</td>
		  <td align="center" id="company">&nbsp;</td>
		  <td align="center" id="zg1_1">&nbsp;</td>
		  <td align="center" id="generalDanger">&nbsp;</td>
		  <td align="center" id="generalDangerGovern">&nbsp;</td>
		  <td align="center" id="zg_1">&nbsp;</td>
		  <td align="center" id="bt">&nbsp;</td>
		  <td align="center" id="btg">&nbsp;</td>
		  <td align="center">&nbsp;</td>
		  <td align="center" id="btng">&nbsp;</td>
		  <td align="center" id="wdw">&nbsp;</td>
		  <td align="center" id="gpt">&nbsp;</td>
		  <td align="center" id="pgp">&nbsp;</td>
		  <td align="center" id="cgp">&nbsp;</td>
		  <td align="center" id="planMoney">&nbsp;</td>
		 </tr>
		 <tr id="tr_1" height="25">
		  <td align="center" id="shouldTroubleshooting"><p>2.金属非金属矿山企业</p></td>
		  <td align="center" id="company">${map["s_1"].allCompanyNum }</td>
		  <td align="center" id="zg1_1">${map["s_1"].companyNum }</td>
		  <td align="center" id="generalDanger"><#if map["s_1"].allCompanyNum==0>/<#else>#{(map["s_1"].companyNum/map["s_1"].allCompanyNum*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_1"].troubNum }</td>
		  <td align="center" id="zg_1">${map["s_1"].troubCleanNum }</td>
		  <td align="center" id="bt"><#if map["s_1"].troubNum==0>/<#else>#{(map["s_1"].troubCleanNum/map["s_1"].troubNum*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_1"].bigTroubNum }</td>
		  <td align="center">${map["s_1"].bigTroubNum - map["s_1"].bigTroubCleanNum }</td>
		  <td align="center" id="btng"><#if map["s_1"].bigTroubNum==0>/<#else>#{((map["s_1"].bigTroubNum-map["s_1"].bigTroubCleanNum)/map["s_1"].bigTroubNum*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_1"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_1"].ddng5Num }</td>
		  <td align="center" id="pgp">${map["s_1"].proviceRcNum + map["s_1"].cityRcNum+ map["s_1"].qtRcNum }</td>
		  <td align="center" id="pgp">${map["s_1"].proviceRcNum }</td>
		  <td align="center" id="cgp">${map["s_1"].cityRcNum }</td>
		  <td align="center" id="planMoney">
		  <#assign jsfjsks_governMoney = (map["s_1"].governMoney + map["s_1"].normalGovernMoney )>
		     ${jsfjsks_governMoney?if_exists?string.number}
		 </td>
		 </tr>
		 <tr id="tr_1" height="25">
		  <td width="18%" class="td_left" id="a" nowrap align="center"><p>3.石油天然气开采企业</p></td>
		  <td align="center" id="shouldTroubleshooting">&nbsp;</td>
		  <td align="center" id="company">&nbsp;</td>
		  <td align="center" id="zg1_1">&nbsp;</td>
		  <td align="center" id="generalDanger">&nbsp;</td>
		  <td align="center" id="generalDangerGovern">&nbsp;</td>
		  <td align="center" id="zg_1">&nbsp;</td>
		  <td align="center" id="bt">&nbsp;</td>
		  <td align="center" id="btg">&nbsp;</td>
		  <td align="center">&nbsp;</td>
		  <td align="center" id="btng">&nbsp;</td>
		  <td align="center" id="wdw">&nbsp;</td>
		  <td align="center" id="gpt">&nbsp;</td>
		  <td align="center" id="pgp">&nbsp;</td>
		  <td align="center" id="cgp">&nbsp;</td>
		  <td align="center" id="planMoney">&nbsp;</td>
		 </tr>
		 <tr id="tr_1" height="25">
		 <#assign all_num = (map["s_7"].allCompanyNum + map["s_8"].allCompanyNum + map["s_9"].allCompanyNum)>
		 <#assign num = (map["s_7"].companyNum + map["s_8"].companyNum + map["s_9"].companyNum)>
		  <td align="center" id="shouldTroubleshooting"><p>4.危险化学品企业</p></td>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_7"].troubNum + map["s_8"].troubNum + map["s_9"].troubNum}</td>
		  <td align="center" id="zg_1">${map["s_7"].troubCleanNum + map["s_8"].troubCleanNum + map["s_9"].troubCleanNum }</td>
		  <td align="center" id="bt">
		  	<#if (map["s_7"].troubNum + map["s_8"].troubNum + map["s_9"].troubNum)==0>/<#else>
		  	#{((map["s_7"].troubCleanNum + map["s_8"].troubCleanNum + map["s_9"].troubCleanNum)/(map["s_7"].troubNum + map["s_8"].troubNum + map["s_9"].troubNum)*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_7"].bigTroubNum + map["s_8"].bigTroubNum + map["s_9"].bigTroubNum}</td>
		  <td align="center">${map["s_7"].bigTroubNum - map["s_7"].bigTroubCleanNum + map["s_8"].bigTroubNum - map["s_8"].bigTroubCleanNum + map["s_9"].bigTroubNum - map["s_9"].bigTroubCleanNum}</td>
		  <td align="center" id="btng">
		  	<#if (map["s_7"].bigTroubNum + map["s_8"].bigTroubNum + map["s_9"].bigTroubNum)==0>/<#else>
		  	#{((map["s_7"].bigTroubNum - map["s_7"].bigTroubCleanNum + map["s_8"].bigTroubNum - map["s_8"].bigTroubCleanNum + map["s_9"].bigTroubNum - map["s_9"].bigTroubCleanNum)/(map["s_7"].bigTroubNum + map["s_8"].bigTroubNum + map["s_9"].bigTroubNum)*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_7"].bigTroubCleanNum + map["s_8"].bigTroubCleanNum + map["s_9"].bigTroubCleanNum}</td>
		  <td align="center" id="gpt">${map["s_7"].ddng5Num + map["s_8"].ddng5Num + map["s_9"].ddng5Num}</td>
		  <td align="center" id="pgp">${map["s_7"].proviceRcNum + map["s_7"].cityRcNum +map["s_7"].qtRcNum  + map["s_8"].proviceRcNum + map["s_8"].cityRcNum+ map["s_8"].qtRcNum  + map["s_9"].proviceRcNum  + map["s_9"].cityRcNum  + map["s_9"].qtRcNum}</td>
		  <td align="center" id="pgp">${map["s_7"].proviceRcNum + map["s_8"].proviceRcNum + map["s_9"].proviceRcNum}</td>
		  <td align="center" id="cgp">${map["s_7"].cityRcNum + map["s_8"].cityRcNum + map["s_9"].cityRcNum}</td>
		  <td align="center" id="planMoney">
		  <#assign wxhxpqy_governMoney = (map["s_7"].governMoney + map["s_8"].governMoney + map["s_9"].governMoney+map["s_7"].normalGovernMoney + map["s_8"].normalGovernMoney + map["s_9"].normalGovernMoney )>
		  ${wxhxpqy_governMoney?if_exists?string.number}
		  
		
		     
		  </td>
		 </tr>
		 <tr id="tr_1" height="25">
		<#assign all_num = (map["s_7"].allCompanyNum + map["s_9"].allCompanyNum )>
		 <#assign num = (map["s_7"].companyNum + map["s_9"].companyNum )>
		  <td align="center" id="shouldTroubleshooting"><p>&nbsp;&nbsp;(1)生产、储存和其他化工企业</p></td>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_7"].troubNum + map["s_9"].troubNum}</td>
		  <td align="center" id="zg_1">${map["s_7"].troubCleanNum  + map["s_9"].troubCleanNum}</td>
		  <td align="center" id="bt">
		  	<#if (map["s_7"].troubNum + map["s_9"].troubNum )==0>/<#else>
		  	#{((map["s_7"].troubCleanNum  + map["s_9"].troubCleanNum )/(map["s_7"].troubNum  + map["s_9"].troubNum)*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_7"].bigTroubNum + map["s_9"].bigTroubNum }</td>
		  <td align="center">${map["s_7"].bigTroubNum - map["s_7"].bigTroubCleanNum  + map["s_9"].bigTroubNum - map["s_9"].bigTroubCleanNum}</td>
		  <td align="center" id="btng">
		  	<#if (map["s_7"].bigTroubNum  + map["s_9"].bigTroubNum )==0>/<#else>
		  	#{((map["s_7"].bigTroubNum - map["s_7"].bigTroubCleanNum + map["s_9"].bigTroubNum - map["s_9"].bigTroubCleanNum)/(map["s_7"].bigTroubNum + map["s_9"].bigTroubNum )*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_7"].bigTroubCleanNum + map["s_9"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_7"].ddng5Num  + map["s_9"].ddng5Num}</td>
		  <td align="center" id="pgp">${map["s_7"].proviceRcNum + map["s_7"].cityRcNum + map["s_7"].qtRcNum +map["s_9"].proviceRcNum + map["s_9"].cityRcNum + map["s_9"].qtRcNum}</td>
		  <td align="center" id="pgp">${map["s_7"].proviceRcNum + map["s_9"].proviceRcNum}</td>
		  <td align="center" id="cgp">${map["s_7"].cityRcNum + map["s_9"].cityRcNum}</td>
		  <td align="center" id="planMoney">
		  <#assign sccchgovhgqy_governMoney = (map["s_7"].governMoney + map["s_9"].governMoney+map["s_7"].normalGovernMoney + map["s_9"].normalGovernMoney)>
		  ${sccchgovhgqy_governMoney?if_exists?string.number}
		  </td>
		 </tr>
		 <tr id="tr_1" height="25">
		      <#assign all_num = (map["s_8"].allCompanyNum )>
			  <#assign num = (map["s_8"].companyNum )>
			  <td width="18%" class="td_left" id="a" nowrap align="center"><p>&nbsp;&nbsp;(2)经营企业和单位</p></td>
			    <td align="center" id="company">${all_num }</td>
			  <td align="center" id="zg1_1">${num }</td>
			  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
			  <td align="center" id="generalDangerGovern">${map["s_8"].troubNum }</td>
			  <td align="center" id="zg_1">${map["s_8"].troubCleanNum  }</td>
			  <td align="center" id="bt">
			  	<#if (map["s_8"].troubNum )==0>/<#else>
			  	#{((map["s_8"].troubCleanNum )/(map["s_8"].troubNum )*100);M1}</#if></td>
			  <td align="center" id="btg">${map["s_8"].bigTroubNum}</td>
			  <td align="center">${map["s_8"].bigTroubNum - map["s_8"].bigTroubCleanNum}</td>
			  <td align="center" id="btng">
			  	<#if (map["s_8"].bigTroubNum )==0>/<#else>
			  	#{((map["s_8"].bigTroubNum - map["s_8"].bigTroubCleanNum )/(map["s_8"].bigTroubNum)*100);M1}</#if></td>
			  <td align="center" id="wdw">${map["s_8"].bigTroubCleanNum}</td>
			  <td align="center" id="gpt">${map["s_8"].ddng5Num}</td>
			  <td align="center" id="pgp">${map["s_8"].proviceRcNum + map["s_8"].cityRcNum + map["s_8"].qtRcNum}</td>
			  <td align="center" id="pgp">${map["s_8"].proviceRcNum}</td>
			  <td align="center" id="cgp">${map["s_8"].cityRcNum }</td>
			  <td align="center" id="planMoney">
			  <#assign sccchgovhgqy_governMoney = (map["s_8"].governMoney+map["s_8"].normalGovernMoney)>
			  ${sccchgovhgqy_governMoney?if_exists?string.number}
			  </td>
		 </tr>
		 <tr id="tr_1" height="25">
		 <#assign all_num = (map["s_14"].allCompanyNum + map["s_15"].allCompanyNum + map["s_16"].allCompanyNum)>
		 <#assign num = (map["s_14"].companyNum + map["s_15"].companyNum + map["s_16"].companyNum)>
		  <td align="center" id="shouldTroubleshooting"><p>5.烟花爆竹企业</p></td>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_14"].troubNum + map["s_15"].troubNum + map["s_16"].troubNum }</td>
		  <td align="center" id="zg_1">${map["s_14"].troubCleanNum + map["s_15"].troubCleanNum + map["s_16"].troubCleanNum }</td>
		  <td align="center" id="bt"><#if (map["s_14"].troubNum + map["s_15"].troubNum + map["s_16"].troubNum)==0>/<#else>#{((map["s_14"].troubCleanNum + map["s_15"].troubCleanNum + map["s_16"].troubCleanNum)/(map["s_14"].troubNum + map["s_15"].troubNum + map["s_16"].troubNum)*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_14"].bigTroubNum + map["s_15"].bigTroubNum + map["s_16"].bigTroubNum }</td>
		  <td align="center">${map["s_14"].bigTroubNum - map["s_14"].bigTroubCleanNum + map["s_15"].bigTroubNum - map["s_15"].bigTroubCleanNum + map["s_16"].bigTroubNum - map["s_16"].bigTroubCleanNum }</td>
		  <td align="center" id="btng"><#if (map["s_14"].bigTroubNum + map["s_15"].bigTroubNum + map["s_16"].bigTroubNum)==0>/<#else>#{((map["s_14"].bigTroubNum - map["s_14"].bigTroubCleanNum + map["s_15"].bigTroubNum - map["s_15"].bigTroubCleanNum + map["s_16"].bigTroubNum - map["s_16"].bigTroubCleanNum)/(map["s_14"].bigTroubNum + map["s_15"].bigTroubNum + map["s_16"].bigTroubNum)*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_14"].bigTroubCleanNum + map["s_15"].bigTroubCleanNum + map["s_16"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_14"].ddng5Num + map["s_15"].ddng5Num + map["s_16"].ddng5Num }</td>
		  <td align="center" id="pgp">${map["s_14"].proviceRcNum + map["s_14"].cityRcNum + map["s_14"].qtRcNum  + map["s_15"].proviceRcNum + map["s_15"].cityRcNum+ map["s_15"].qtRcNum + map["s_16"].proviceRcNum  + map["s_16"].cityRcNum+ map["s_16"].qtRcNum }</td>
		  <td align="center" id="pgp">${map["s_14"].proviceRcNum + map["s_15"].proviceRcNum + map["s_16"].proviceRcNum }</td>
		  <td align="center" id="cgp">${map["s_14"].cityRcNum + map["s_15"].cityRcNum + map["s_16"].cityRcNum }</td>
		  <td align="center" id="planMoney">
		  <#assign yhbzqy_governMoney = (map["s_14"].governMoney + map["s_15"].governMoney + map["s_16"].governMoney+map["s_14"].normalGovernMoney + map["s_15"].normalGovernMoney + map["s_16"].normalGovernMoney)>
		  ${yhbzqy_governMoney?if_exists?string.number}
		  </td>
		 </tr>
		 <tr id="tr_1" height="25">
		 <#assign all_num = (map["s_3"].allCompanyNum + map["s_4"].allCompanyNum)>
		 <#assign num = (map["s_3"].companyNum + map["s_4"].companyNum)>
		  <td align="center" id="shouldTroubleshooting"><p>6.冶金、有色企业</p></td>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_3"].troubNum + map["s_4"].troubNum }</td>
		  <td align="center" id="zg_1">${map["s_3"].troubCleanNum + map["s_4"].troubCleanNum }</td>
		  <td align="center" id="bt"><#if (map["s_3"].troubCleanNum + map["s_4"].troubCleanNum)==0>/
		  <#else>
		  #{((map["s_3"].troubCleanNum + map["s_4"].troubCleanNum)/(map["s_3"].troubNum + map["s_4"].troubNum)*100);M1}
		  </#if>
		  </td>
		  <td align="center" id="btg">${map["s_3"].bigTroubNum + map["s_4"].bigTroubNum }</td>
		  <td align="center">${map["s_3"].bigTroubNum - map["s_3"].bigTroubCleanNum + map["s_4"].bigTroubNum - map["s_4"].bigTroubCleanNum }</td>
		  <td align="center" id="btng"><#if (map["s_3"].bigTroubNum + map["s_4"].bigTroubNum)==0>/<#else>#{((map["s_3"].bigTroubNum - map["s_3"].bigTroubCleanNum + map["s_4"].bigTroubNum - map["s_4"].bigTroubCleanNum)/(map["s_3"].bigTroubNum + map["s_4"].bigTroubNum)*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_3"].bigTroubCleanNum + map["s_4"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_3"].ddng5Num + map["s_4"].ddng5Num }</td>
		  <td align="center" id="pgp">${map["s_3"].proviceRcNum + map["s_3"].cityRcNum+ map["s_3"].qtRcNum  + map["s_4"].proviceRcNum + map["s_4"].qtRcNum + map["s_4"].cityRcNum}</td>
		  <td align="center" id="pgp">${map["s_3"].proviceRcNum + map["s_4"].proviceRcNum }</td>
		  <td align="center" id="cgp">${map["s_3"].cityRcNum + map["s_4"].cityRcNum }</td>
		  <td align="center" id="planMoney">
		  <#assign jsfjsksqy_governMoney = (map["s_3"].governMoney + map["s_4"].governMoney+map["s_3"].normalGovernMoney + map["s_4"].normalGovernMoney)>
		  ${jsfjsksqy_governMoney?if_exists?string.number}
		  </td>
		 </tr>
				 
		 <tr id="tr_1" height="25">
		 <#assign all_num = (map["s_17"].allCompanyNum+map["s_6"].allCompanyNum)>
		 <#assign num = (map["s_17"].companyNum+map["s_6"].companyNum)>
		  <td align="center" id="shouldTroubleshooting"><p>7.其他企业</p></td>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_17"].troubNum+map["s_6"].troubNum}</td>
		  <td align="center" id="zg_1">${map["s_17"].troubCleanNum+map["s_6"].troubCleanNum}</td>
		  <td align="center" id="bt"><#if map["s_17"].troubNum==0>/<#else>#{((map["s_17"].troubCleanNum+map["s_6"].troubCleanNum)/(map["s_17"].troubNum+map["s_6"].troubNum)*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_17"].bigTroubNum+map["s_6"].bigTroubNum}</td>
		  <td align="center">${map["s_17"].bigTroubNum+map["s_6"].bigTroubNum - map["s_17"].bigTroubCleanNum-map["s_6"].bigTroubCleanNum}</td>
		  <td align="center" id="btng"><#if map["s_17"].bigTroubNum==0>/<#else>#{(((map["s_17"].bigTroubNum+map["s_6"].bigTroubNum)- (map["s_17"].bigTroubCleanNum+map["s_6"].bigTroubCleanNum))/(map["s_17"].bigTroubNum+map["s_6"].bigTroubNum)*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_17"].bigTroubCleanNum+map["s_6"].bigTroubCleanNum}</td>
		  <td align="center" id="gpt">${map["s_17"].ddng5Num+map["s_6"].ddng5Num}</td>
		  <td align="center" id="pgp">${map["s_17"].proviceRcNum + map["s_17"].cityRcNum+ map["s_17"].qtRcNum +map["s_6"].proviceRcNum + map["s_6"].cityRcNum+ map["s_6"].qtRcNum}</td>
		  <td align="center" id="pgp">${map["s_17"].proviceRcNum+map["s_6"].proviceRcNum}</td>
		  <td align="center" id="cgp">${map["s_17"].cityRcNum+map["s_6"].cityRcNum}</td>
		  <td align="center" id="planMoney">
		  <#assign qtqy_governMoney = (map["s_17"].governMoney+map["s_6"].governMoney+map["s_17"].normalGovernMoney+map["s_6"].normalGovernMoney)>
		  ${qtqy_governMoney?if_exists?string.number}
		  </td>
		 </tr>
    </table>
    <table width="97.5%" border="0" height="20" cellspacing="0" cellpadding="0">
	  <tr>
		<td align="center" style="font-size:12px;line-height:30px;">单位负责人（签字）：　　　　　　　　　填表人（签字）：　　　　　　　　　联系电话：　　　　　　　　　填报日期：　　　　年　　月　　日</td>
	  </tr>
	</table>
  </div>      
<table width="97%" border="0" cellpadding="0" cellspacing="0" style="border: #CCCCCC solid 1px; border-collapse: collapse; background: #f6f6f6; font-size: 12px;line-height:150%;">
	<tr>
		<th width="15%" style="border: #CCCCCC solid 1px; color: #5494af; line-height: 30px; text-align: center;">
			说
			<br />
			明
		</th>
		<td width="85%" style="border: #CCCCCC solid 1px; padding-left: 10px; color: #878383; " style="text-align:left">
			<p>
				1.新增统计列“应排查治理事故隐患的生产经营单位”及其“覆盖率”；
			</p>
			<p>
				2.新增：列入治理计划中的“达到'五到位'要求的”，为旧统计表中 a、落实治理目标任务 b、落实治理经费物资 
			</p>
			<p>
				c、落实治理机构人员d、落实治理时间要求 e、落实安全措施应急预案，五项全部符合的重大隐患；
			</p>
			<p>
				3.新增：重大隐患增加了挂牌督办情况的统计项。
			</p>
			<p>
				4.新统计表显示的行业都是旧统计表中的行业或者一些行业的小计，并且总合计数新旧统计表相同的列的数据是相同的。
			</p>
			<p>
				5.新统计表各个行业的统计数据跟旧统计表一样，只是新统计表新增的挂牌统计情况旧统计表是没有的，原先的各项统计数据都是一样的。
			</p>
		</td>
	</tr>
</table>  
<br>
<#--进度条-->
<div id="progress" style="display:none;border:1px solid #c0c0c0;width:350px;position:absolute;">
	<table width="100%" border="0" align="center" cellpadding="1" cellspacing="0" bgcolor="#EEEEEE">
		<tr>
			<td colspan="2" style="height:20px;" bgcolor="#A6CFFD" align="center"><span style="color:#F8F2C7;font-weight:bold;font-size:12px">【系统提示】</span></td>
		</tr>
		<tr>
        	<td align="center" height="40" id="progressText" ></td> 
       </tr>
	</table>
</div>     
<script>
	function winLocation(){
		window.location="?statistic.month=${statistic.month}&statistic.year="+get("year").value.substring(0,4);
		progressText("正在加载统计数据，请稍等...");
	}
	
	function checkOldData(){
		window.location="loadStatisticMineByParam2.xhtml?statistic.month=${statistic.month}&statistic.year="+get("year").value.substring(0,4);
		progressText("正在加载统计数据，请稍等...");
	}
	
	function sendData(){
		window.location="loadStatisticForAxis2MineByParam.xhtml?statistic.yearMonth=${statistic.year}-${statistic.month}";
		progressText("正在加载统计数据，请稍等...");
	}
	
	//进度条弹出层
	function progressText(txt){
		$("#progressText").html(txt);
		easyDialog.open({
			container : 'progress',
			drag : false
		});
	};
	
	printParam(20,8,0,0,2);
</script>
 </#escape>
<@fkMacros.pageFooter />
