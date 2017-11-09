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
		<#if !viewRole?? || !viewRole>
		<input name="print" type="button" value="<#if userDetail.secondArea==0>发送数据<#else>查看月统计</#if>"  onclick="sendData();"/>
		</#if>
	</td>
	<td align="center">
		<#if !viewRole?? || !viewRole>
		<input name="print" type="button"  value="旧版本"  onclick="checkOldData();"/>
		</#if>
	</td>
  </tr>
    <tr>
  	<td align="center" colspan=4>
	<#if refreshDate??><strong> <font  color=red > &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 最近更新时间： </font>	</strong>${refreshDate?datetime("yyyy-MM-dd HH:mm:ss")}</#if></strong>	
		&nbsp;&nbsp;
	<#if refresh==1 >
		<a href="loadStatisticOtherByParam.xhtml?statistic.year=${statistic.year}&statistic.month=${statistic.month}&statistic.reFresh=true"><strong><font  color=red >刷新</font></strong></a>
	</#if>
	</td>
  </tr>
</table>
</center>
<div id='page1'>
<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr align="center">
	     <th colspan="2" height="50" style="font-size:20px;line-height:30px;"><input type="text" id="year" onChange="winLocation();" value="${statistic.year}年" onfocus="WdatePicker({dateFmt:'yyyy年',minDate:'2009',maxDate:'%y-%M-%d %H-%m'})" class="Wdate" size="8" maxlength="6">
    交通运输等重点行业领域企业和单位安全生产事故隐患排查治理情况月报表</th>
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
        <#assign all_num = (map["s_total"].allCompanyNum)>
		 <#assign num = (map["s_total"].companyNum)>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_total"].troubNum }</td>
		  <td align="center" id="zg_1">${map["s_total"].troubCleanNum }</td>
		  <td align="center" id="bt"><#if map["s_total"].troubNum==0>/<#else>#{(map["s_total"].troubCleanNum/map["s_total"].troubNum*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_total"].bigTroubNum }</td>
		  <td align="center">${map["s_total"].bigTroubNum - map["s_total"].bigTroubCleanNum }</td>
		  <td align="center" id="btng"><#if map["s_total"].bigTroubNum==0>/<#else>#{((map["s_total"].bigTroubNum-map["s_total"].bigTroubCleanNum)/map["s_total"].bigTroubNum*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_total"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_total"].ddng5Num }</td>
		  <td align="center" id="pgp">${map["s_total"].proviceRcNum + map["s_total"].cityRcNum + map["s_total"].qtRcNum }</td>
		  <td align="center" id="pgp">${map["s_total"].proviceRcNum }</td>
		  <td align="center" id="cgp">${map["s_total"].cityRcNum }</td>
		  <td align="center" id="planMoney">
		   <#assign totle_governMoney = (map["s_total"].governMoney + map["s_total"].normalGovernMoney)>
		  ${totle_governMoney?if_exists?string.number}
		  </td>
		</tr>
		 <tr id="tr_1" height="25">
		 <#assign all_num = (map["s_18"].allCompanyNum+map["s_13"].allCompanyNum)>
		 <#assign num = (map["s_18"].companyNum+map["s_13"].companyNum)>
		  <td align="center" id="shouldTroubleshooting"><p>1.道路运输企业</p></td>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_18"].troubNum+map["s_13"].troubNum  }</td>
		  <td align="center" id="zg_1">${map["s_18"].troubCleanNum+map["s_13"].troubCleanNum  }</td>
		  <td align="center" id="bt"><#if (map["s_18"].troubNum+map["s_13"].troubNum)==0>/<#else>#{((map["s_18"].troubCleanNum+map["s_13"].troubCleanNum)/(map["s_18"].troubNum+map["s_13"].troubNum)*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_18"].bigTroubNum+map["s_13"].bigTroubNum }</td>
		  <td align="center">${(map["s_18"].bigTroubNum+map["s_13"].bigTroubNum) - (map["s_18"].bigTroubCleanNum+map["s_13"].bigTroubCleanNum) }</td>
		  <td align="center" id="btng"><#if (map["s_18"].bigTroubNum+map["s_13"].bigTroubNum)==0>/<#else>#{((map["s_18"].bigTroubNum+map["s_13"].bigTroubNum-map["s_18"].bigTroubCleanNum-map["s_13"].bigTroubCleanNum)/(map["s_18"].bigTroubNum+map["s_13"].bigTroubNum)*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_18"].bigTroubCleanNum+map["s_13"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_18"].ddng5Num +map["s_13"].ddng5Num}</td>
		  <td align="center" id="pgp">${map["s_18"].proviceRcNum + map["s_18"].cityRcNum + map["s_18"].qtRcNum +map["s_13"].proviceRcNum + map["s_13"].cityRcNum + map["s_13"].qtRcNum }</td>
		  <td align="center" id="pgp">${map["s_18"].proviceRcNum+map["s_13"].proviceRcNum  }</td>
		  <td align="center" id="cgp">${map["s_18"].cityRcNum +map["s_13"].cityRcNum}</td>
		  <td align="center" id="planMoney">
		  <#assign dlysqy_governMoney = (map["s_18"].governMoney + map["s_13"].governMoney+map["s_18"].normalGovernMoney + map["s_13"].normalGovernMoney)>
		  ${dlysqy_governMoney?if_exists?string.number}
		  </td>
		 </tr>
		 <tr id="tr_1" height="25">
		 <#assign all_num = (map["s_19"].allCompanyNum)>
		 <#assign num = (map["s_19"].companyNum)>
		  <td align="center" id="shouldTroubleshooting"><p>2.公路养护施工企业</p></td>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_19"].troubNum }</td>
		  <td align="center" id="zg_1">${map["s_19"].troubCleanNum }</td>
		  <td align="center" id="bt"><#if map["s_19"].troubNum==0>/<#else>#{(map["s_19"].troubCleanNum/map["s_19"].troubNum*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_19"].bigTroubNum }</td>
		  <td align="center">${map["s_19"].bigTroubNum - map["s_19"].bigTroubCleanNum }</td>
		  <td align="center" id="btng"><#if map["s_19"].bigTroubNum==0>/<#else>#{((map["s_19"].bigTroubNum-map["s_19"].bigTroubCleanNum)/map["s_19"].bigTroubNum*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_19"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_19"].ddng5Num }</td>
		  <td align="center" id="pgp">${map["s_19"].proviceRcNum + map["s_19"].cityRcNum + map["s_19"].qtRcNum }</td>
		  <td align="center" id="pgp">${map["s_19"].proviceRcNum }</td>
		  <td align="center" id="cgp">${map["s_19"].cityRcNum }</td>
		  <td align="center" id="planMoney">
		    <#assign glyhsgqy_governMoney = (map["s_19"].governMoney + map["s_19"].normalGovernMoney)>
		  ${glyhsgqy_governMoney?if_exists?string.number}
		  </td>
		 </tr>
		 <tr id="tr_1" height="25">
		 <#assign all_num = (map["s_20"].allCompanyNum)>
		 <#assign num = (map["s_20"].companyNum)>
		  <td align="center" id="shouldTroubleshooting"><p>3.水上运输企业</p></td>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_20"].troubNum }</td>
		  <td align="center" id="zg_1">${map["s_20"].troubCleanNum }</td>
		  <td align="center" id="bt"><#if map["s_20"].troubNum==0>/<#else>#{(map["s_20"].troubCleanNum/map["s_20"].troubNum*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_20"].bigTroubNum }</td>
		  <td align="center">${map["s_20"].bigTroubNum - map["s_20"].bigTroubCleanNum }</td>
		  <td align="center" id="btng"><#if map["s_20"].bigTroubNum==0>/<#else>#{((map["s_20"].bigTroubNum-map["s_20"].bigTroubCleanNum)/map["s_20"].bigTroubNum*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_20"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_20"].ddng5Num }</td>
		  <td align="center" id="pgp">${map["s_20"].proviceRcNum + map["s_20"].cityRcNum + map["s_20"].qtRcNum }</td>
		  <td align="center" id="pgp">${map["s_20"].proviceRcNum }</td>
		  <td align="center" id="cgp">${map["s_20"].cityRcNum }</td>
		  <td align="center" id="planMoney">
		    <#assign ssyyqy_governMoney = (map["s_20"].governMoney + map["s_20"].normalGovernMoney)>
		  ${ssyyqy_governMoney?if_exists?string.number}
		  </td>
		 </tr>
		 <tr id="tr_1" height="25">
		 <#assign all_num = (map["s_21"].allCompanyNum)>
		 <#assign num = (map["s_21"].companyNum)>
		  <td align="center" id="shouldTroubleshooting"><p>4.铁路运输企业</p></td>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_21"].troubNum }</td>
		  <td align="center" id="zg_1">${map["s_21"].troubCleanNum }</td>
		  <td align="center" id="bt"><#if map["s_21"].troubNum==0>/<#else>#{(map["s_21"].troubCleanNum/map["s_21"].troubNum*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_21"].bigTroubNum }</td>
		  <td align="center">${map["s_21"].bigTroubNum - map["s_21"].bigTroubCleanNum }</td>
		  <td align="center" id="btng"><#if map["s_21"].bigTroubNum==0>/<#else>#{((map["s_21"].bigTroubNum-map["s_21"].bigTroubCleanNum)/map["s_21"].bigTroubNum*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_21"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_21"].ddng5Num }</td>
		  <td align="center" id="pgp">${map["s_21"].proviceRcNum + map["s_21"].cityRcNum + map["s_21"].qtRcNum }</td>
		  <td align="center" id="pgp">${map["s_21"].proviceRcNum }</td>
		  <td align="center" id="cgp">${map["s_21"].cityRcNum }</td>
		  <td align="center" id="planMoney">
		   <#assign tlysqy_governMoney = (map["s_21"].governMoney + map["s_21"].normalGovernMoney)>
		  ${tlysqy_governMoney?if_exists?string.number}
		  </td>
		 </tr>
		 <tr id="tr_1" height="25">
		 <#assign all_num = (map["s_22"].allCompanyNum)>
		 <#assign num = (map["s_22"].companyNum)>
		  <td align="center" id="shouldTroubleshooting"><p>5.航空公司</p></td>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_22"].troubNum }</td>
		  <td align="center" id="zg_1">${map["s_22"].troubCleanNum }</td>
		  <td align="center" id="bt"><#if map["s_22"].troubNum==0>/<#else>#{(map["s_22"].troubCleanNum/map["s_22"].troubNum*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_22"].bigTroubNum }</td>
		  <td align="center">${map["s_22"].bigTroubNum - map["s_22"].bigTroubCleanNum }</td>
		  <td align="center" id="btng"><#if map["s_22"].bigTroubNum==0>/<#else>#{((map["s_22"].bigTroubNum-map["s_22"].bigTroubCleanNum)/map["s_22"].bigTroubNum*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_22"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_22"].ddng5Num }</td>
		  <td align="center" id="pgp">${map["s_22"].proviceRcNum + map["s_22"].cityRcNum + map["s_22"].qtRcNum }</td>
		  <td align="center" id="pgp">${map["s_22"].proviceRcNum }</td>
		  <td align="center" id="cgp">${map["s_22"].cityRcNum }</td>
		  <td align="center" id="planMoney">
		  
		   <#assign hkgs_governMoney = (map["s_22"].governMoney + map["s_22"].normalGovernMoney)>
		   ${hkgs_governMoney?if_exists?string.number}
		  </td>
		 </tr>
		 <tr id="tr_1" height="25">
		 <#assign all_num = (map["s_23"].allCompanyNum)>
		 <#assign num = (map["s_23"].companyNum)>
		  <td align="center" id="shouldTroubleshooting"><p>6.机场和油料企业</p></td>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_23"].troubNum }</td>
		  <td align="center" id="zg_1">${map["s_23"].troubCleanNum }</td>
		  <td align="center" id="bt"><#if map["s_23"].troubNum==0>/<#else>#{(map["s_23"].troubCleanNum/map["s_23"].troubNum*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_23"].bigTroubNum }</td>
		  <td align="center">${map["s_23"].bigTroubNum - map["s_23"].bigTroubCleanNum }</td>
		  <td align="center" id="btng"><#if map["s_23"].bigTroubNum==0>/<#else>#{((map["s_23"].bigTroubNum-map["s_23"].bigTroubCleanNum)/map["s_23"].bigTroubNum*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_23"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_23"].ddng5Num }</td>
		  <td align="center" id="pgp">${map["s_23"].proviceRcNum + map["s_23"].cityRcNum + map["s_23"].qtRcNum }</td>
		  <td align="center" id="pgp">${map["s_23"].proviceRcNum }</td>
		  <td align="center" id="cgp">${map["s_23"].cityRcNum }</td>
		  <td align="center" id="planMoney">
		  
		    <#assign jchylqy_governMoney = (map["s_23"].governMoney + map["s_23"].normalGovernMoney)>
		   ${jchylqy_governMoney?if_exists?string.number}
		  </td>
		 </tr>
		 <tr id="tr_1" height="25">
		 <#assign all_num = (map["s_30"].allCompanyNum)>
		 <#assign num = (map["s_30"].companyNum)>
		  <td align="center" id="shouldTroubleshooting"><p>7.建筑施工企业</p></td>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_30"].troubNum }</td>
		  <td align="center" id="zg_1">${map["s_30"].troubCleanNum }</td>
		  <td align="center" id="bt"><#if map["s_30"].troubNum==0>/<#else>#{(map["s_30"].troubCleanNum/map["s_30"].troubNum*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_30"].bigTroubNum }</td>
		  <td align="center">${map["s_30"].bigTroubNum - map["s_30"].bigTroubCleanNum }</td>
		  <td align="center" id="btng"><#if map["s_30"].bigTroubNum==0>/<#else>#{((map["s_30"].bigTroubNum-map["s_30"].bigTroubCleanNum)/map["s_30"].bigTroubNum*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_30"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_30"].ddng5Num }</td>
		  <td align="center" id="pgp">${map["s_30"].proviceRcNum + map["s_30"].cityRcNum + map["s_30"].qtRcNum }</td>
		  <td align="center" id="pgp">${map["s_30"].proviceRcNum }</td>
		  <td align="center" id="cgp">${map["s_30"].cityRcNum }</td>
		  <td align="center" id="planMoney">
		  
		    <#assign jzsgqy_governMoney = (map["s_30"].governMoney + map["s_30"].normalGovernMoney)>
		   ${jzsgqy_governMoney?if_exists?string.number}
		  </td>
		 </tr>
		 <tr id="tr_1" height="25">
		 <#assign all_num = (map["s_28"].allCompanyNum)>
		 <#assign num = (map["s_28"].companyNum)>
		  <td align="center" id="shouldTroubleshooting"><p>8.学校</p></td>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_28"].troubNum }</td>
		  <td align="center" id="zg_1">${map["s_28"].troubCleanNum }</td>
		  <td align="center" id="bt"><#if map["s_28"].troubNum==0>/<#else>#{(map["s_28"].troubCleanNum/map["s_28"].troubNum*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_28"].bigTroubNum }</td>
		  <td align="center">${map["s_28"].bigTroubNum - map["s_28"].bigTroubCleanNum }</td>
		  <td align="center" id="btng"><#if map["s_28"].bigTroubNum==0>/<#else>#{((map["s_28"].bigTroubNum-map["s_28"].bigTroubCleanNum)/map["s_28"].bigTroubNum*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_28"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_28"].ddng5Num }</td>
		  <td align="center" id="pgp">${map["s_28"].proviceRcNum + map["s_28"].cityRcNum + map["s_28"].qtRcNum }</td>
		  <td align="center" id="pgp">${map["s_28"].proviceRcNum }</td>
		  <td align="center" id="cgp">${map["s_28"].cityRcNum }</td>
		  <td align="center" id="planMoney">
		     <#assign xx_governMoney = (map["s_28"].governMoney + map["s_28"].normalGovernMoney)>
		   ${xx_governMoney?if_exists?string.number}
		  </td>
		 </tr>
		 <tr id="tr_1" height="25">
		 <#assign all_num = (map["s_29"].allCompanyNum)>
		 <#assign num = (map["s_29"].companyNum)>
		  <td align="center" id="shouldTroubleshooting"><p>9.商场、市场等人员密集场所</p></td>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_29"].troubNum }</td>
		  <td align="center" id="zg_1">${map["s_29"].troubCleanNum }</td>
		  <td align="center" id="bt"><#if map["s_29"].troubNum==0>/<#else>#{(map["s_29"].troubCleanNum/map["s_29"].troubNum*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_29"].bigTroubNum }</td>
		  <td align="center">${map["s_29"].bigTroubNum - map["s_29"].bigTroubCleanNum }</td>
		  <td align="center" id="btng"><#if map["s_29"].bigTroubNum==0>/<#else>#{((map["s_29"].bigTroubNum-map["s_29"].bigTroubCleanNum)/map["s_29"].bigTroubNum*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_29"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_29"].ddng5Num }</td>
		  <td align="center" id="pgp">${map["s_29"].proviceRcNum + map["s_29"].cityRcNum + map["s_29"].qtRcNum }</td>
		  <td align="center" id="pgp">${map["s_29"].proviceRcNum }</td>
		  <td align="center" id="cgp">${map["s_29"].cityRcNum }</td>
		  <td align="center" id="planMoney">
		     <#assign scscdrymjcs_governMoney = (map["s_29"].governMoney + map["s_29"].normalGovernMoney)>
		   ${scscdrymjcs_governMoney?if_exists?string.number}
		  </td>
		 </tr>
		 <tr id="tr_1" height="25">
		 <#assign all_num = (map["s_26"].allCompanyNum)>
		 <#assign num = (map["s_26"].companyNum)>
		  <td align="center" id="shouldTroubleshooting"><p>10.水库</p></td>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_26"].troubNum }</td>
		  <td align="center" id="zg_1">${map["s_26"].troubCleanNum }</td>
		  <td align="center" id="bt"><#if map["s_26"].troubNum==0>/<#else>#{(map["s_26"].troubCleanNum/map["s_26"].troubNum*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_26"].bigTroubNum }</td>
		  <td align="center">${map["s_26"].bigTroubNum - map["s_26"].bigTroubCleanNum }</td>
		  <td align="center" id="btng"><#if map["s_26"].bigTroubNum==0>/<#else>#{((map["s_26"].bigTroubNum-map["s_26"].bigTroubCleanNum)/map["s_26"].bigTroubNum*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_26"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_26"].ddng5Num }</td>
		  <td align="center" id="pgp">${map["s_26"].proviceRcNum + map["s_26"].cityRcNum + map["s_26"].qtRcNum}</td>
		  <td align="center" id="pgp">${map["s_26"].proviceRcNum }</td>
		  <td align="center" id="cgp">${map["s_26"].cityRcNum }</td>
		  <td align="center" id="planMoney">
		    <#assign sk_governMoney = (map["s_26"].governMoney + map["s_26"].normalGovernMoney)>
		   ${sk_governMoney?if_exists?string.number}
		  </td>
		 </tr>
		 <tr id="tr_1" height="25">
		 <#assign all_num = (map["s_32"].allCompanyNum)>
		 <#assign num = (map["s_32"].companyNum)>
		  <td align="center" id="shouldTroubleshooting"><p>11.电力企业</p></td>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_32"].troubNum }</td>
		  <td align="center" id="zg_1">${map["s_32"].troubCleanNum }</td>
		  <td align="center" id="bt"><#if map["s_32"].troubNum==0>/<#else>#{(map["s_32"].troubCleanNum/map["s_32"].troubNum*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_32"].bigTroubNum }</td>
		  <td align="center">${map["s_32"].bigTroubNum - map["s_32"].bigTroubCleanNum }</td>
		  <td align="center" id="btng"><#if map["s_32"].bigTroubNum==0>/<#else>#{((map["s_32"].bigTroubNum-map["s_32"].bigTroubCleanNum)/map["s_32"].bigTroubNum*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_32"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_32"].ddng5Num }</td>
		  <td align="center" id="pgp">${map["s_32"].proviceRcNum + map["s_32"].cityRcNum + map["s_32"].qtRcNum }</td>
		  <td align="center" id="pgp">${map["s_32"].proviceRcNum }</td>
		  <td align="center" id="cgp">${map["s_32"].cityRcNum }</td>
		  <td align="center" id="planMoney">
		    <#assign dlqy_governMoney = (map["s_32"].governMoney + map["s_32"].normalGovernMoney)>
		   ${dlqy_governMoney?if_exists?string.number}
		  </td>
		 </tr>
		 <tr id="tr_1" height="25">
		 <#assign all_num = (map["s_25"].allCompanyNum)>
		 <#assign num = (map["s_25"].companyNum)>
		  <td align="center" id="shouldTroubleshooting"><p>12.农机行业</p></td>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_25"].troubNum }</td>
		  <td align="center" id="zg_1">${map["s_25"].troubCleanNum }</td>
		  <td align="center" id="bt"><#if map["s_25"].troubNum==0>/<#else>#{(map["s_25"].troubCleanNum/map["s_25"].troubNum*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_25"].bigTroubNum }</td>
		  <td align="center">${map["s_25"].bigTroubNum - map["s_25"].bigTroubCleanNum }</td>
		  <td align="center" id="btng"><#if map["s_25"].bigTroubNum==0>/<#else>#{((map["s_25"].bigTroubNum-map["s_25"].bigTroubCleanNum)/map["s_25"].bigTroubNum*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_25"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_25"].ddng5Num }</td>
		  <td align="center" id="pgp">${map["s_25"].proviceRcNum + map["s_25"].cityRcNum + map["s_25"].qtRcNum }</td>
		  <td align="center" id="pgp">${map["s_25"].proviceRcNum }</td>
		  <td align="center" id="cgp">${map["s_25"].cityRcNum }</td>
		  <td align="center" id="planMoney">
		  
		    <#assign njhy_governMoney = (map["s_25"].governMoney + map["s_25"].normalGovernMoney)>
		   ${njhy_governMoney?if_exists?string.number}
		  </td>
		 </tr>
		 <tr id="tr_1" height="25">
		 <#assign all_num = (map["s_24"].allCompanyNum)>
		 <#assign num = (map["s_24"].companyNum)>
		  <td align="center" id="shouldTroubleshooting"><p>13.渔业企业</p></td>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_24"].troubNum }</td>
		  <td align="center" id="zg_1">${map["s_24"].troubCleanNum }</td>
		  <td align="center" id="bt"><#if map["s_24"].troubNum==0>/<#else>#{(map["s_24"].troubCleanNum/map["s_24"].troubNum*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_24"].bigTroubNum }</td>
		  <td align="center">${map["s_24"].bigTroubNum - map["s_24"].bigTroubCleanNum }</td>
		  <td align="center" id="btng"><#if map["s_24"].bigTroubNum==0>/<#else>#{((map["s_24"].bigTroubNum-map["s_24"].bigTroubCleanNum)/map["s_24"].bigTroubNum*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_24"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_24"].ddng5Num }</td>
		  <td align="center" id="pgp">${map["s_24"].proviceRcNum + map["s_24"].cityRcNum + map["s_24"].qtRcNum }</td>
		  <td align="center" id="pgp">${map["s_24"].proviceRcNum }</td>
		  <td align="center" id="cgp">${map["s_24"].cityRcNum }</td>
		  <td align="center" id="planMoney">
		  <#assign yyqy_governMoney = (map["s_24"].governMoney + map["s_24"].normalGovernMoney)>
		   ${yyqy_governMoney?if_exists?string.number}
		  
		  </td>
		 </tr>
		 <tr id="tr_1" height="25">
		 <#assign all_num = (map["s_31"].allCompanyNum)>
		 <#assign num = (map["s_31"].companyNum)>
		  <td align="center" id="shouldTroubleshooting"><p>14.民爆器材生产企业</p></td>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_31"].troubNum }</td>
		  <td align="center" id="zg_1">${map["s_31"].troubCleanNum }</td>
		  <td align="center" id="bt"><#if map["s_31"].troubNum==0>/<#else>#{(map["s_31"].troubCleanNum/map["s_31"].troubNum*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_31"].bigTroubNum }</td>
		  <td align="center">${map["s_31"].bigTroubNum - map["s_31"].bigTroubCleanNum }</td>
		  <td align="center" id="btng"><#if map["s_31"].bigTroubNum==0>/<#else>#{((map["s_31"].bigTroubNum-map["s_31"].bigTroubCleanNum)/map["s_31"].bigTroubNum*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_31"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_31"].ddng5Num }</td>
		  <td align="center" id="pgp">${map["s_31"].proviceRcNum + map["s_31"].cityRcNum + map["s_31"].qtRcNum }</td>
		  <td align="center" id="pgp">${map["s_31"].proviceRcNum }</td>
		  <td align="center" id="cgp">${map["s_31"].cityRcNum }</td>
		  <td align="center" id="planMoney">
		  
		   <#assign mbqcscqy_governMoney = (map["s_31"].governMoney + map["s_31"].normalGovernMoney)>
		   ${mbqcscqy_governMoney?if_exists?string.number}
		  </td>
		 </tr>
		 <tr id="tr_1" height="25">
		 <#assign all_num = (map["s_15"].allCompanyNum)>
		 <#assign num = (map["s_15"].companyNum)>
		  <td align="center" id="shouldTroubleshooting"><p>15.其他企业单位</p></td>
		  <td align="center" id="company">${all_num }</td>
		  <td align="center" id="zg1_1">${num }</td>
		  <td align="center" id="generalDanger"><#if all_num==0>/<#else>#{(num/all_num*100);M1}</#if></td>
		  <td align="center" id="generalDangerGovern">${map["s_15"].troubNum }</td>
		  <td align="center" id="zg_1">${map["s_15"].troubCleanNum }</td>
		  <td align="center" id="bt"><#if map["s_15"].troubNum==0>/<#else>#{(map["s_15"].troubCleanNum/map["s_15"].troubNum*100);M1}</#if></td>
		  <td align="center" id="btg">${map["s_15"].bigTroubNum }</td>
		  <td align="center">${map["s_15"].bigTroubNum - map["s_15"].bigTroubCleanNum }</td>
		  <td align="center" id="btng"><#if map["s_15"].bigTroubNum==0>/<#else>#{((map["s_15"].bigTroubNum - map["s_15"].bigTroubCleanNum)/map["s_15"].bigTroubNum*100);M1}</#if></td>
		  <td align="center" id="wdw">${map["s_15"].bigTroubCleanNum }</td>
		  <td align="center" id="gpt">${map["s_15"].ddng5Num }</td>
		  <td align="center" id="pgp">${map["s_15"].proviceRcNum + map["s_15"].cityRcNum + map["s_15"].qtRcNum }</td>
		  <td align="center" id="pgp">${map["s_15"].proviceRcNum }</td>
		  <td align="center" id="cgp">${map["s_15"].cityRcNum }</td>
		  <td align="center" id="planMoney">
		    <#assign qtqydw_governMoney = (map["s_15"].governMoney + map["s_15"].normalGovernMoney)>
		   ${qtqydw_governMoney?if_exists?string.number}
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
<script language="javascript">
	function winLocation(){
		window.location="?statistic.month=${statistic.month}&statistic.year="+get("year").value.substring(0,4);
		progressText("正在加载统计数据，请稍等...");
	}
	
	function checkOldData(){
		window.location="loadStatisticOtherByParam2.xhtml?statistic.month=${statistic.month}&statistic.year="+get("year").value.substring(0,4);
		progressText("正在加载统计数据，请稍等...");
	}
	
	function sendData(){
		window.location="loadStatisticForAxis2OtherByParam.xhtml?statistic.yearMonth=${statistic.year}-${statistic.month}";
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
	printParam(5,8,0,0,2);
</script>
 </#escape>
<@fkMacros.pageFooter />
