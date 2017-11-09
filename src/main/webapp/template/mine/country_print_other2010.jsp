<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld"%> 
<%@ taglib uri="http://www.safetys.cn/tag/pagination" prefix="p" %>
<%@ taglib uri="http://java.sun.com/jstl/function" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="${contextPath}/validator/css/validator.css" rel="stylesheet" type="text/css" />
	<link href="${resourcePath}/css/css.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${resourcePath}/js/common.js"></script>
	<script language="javascript" src="${resourcePath}/js/print.js"></script>
</head>
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
	width:100%;
	background-color:#f7fcff;
}
.table_list th{FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#FFFFFF, EndColorStr=#b4dff8);}

.table_list td {
	font-size:12px;
	color:#000;
	height:20px;
	border-top-width: 1px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #FFF;
	border-right-color: #000;
	border-bottom-color: #000;
	border-left-color: #FFF;
	line-height:20px;
	padding-left:5px;
	padding-right:5px;
}
.table_list td {text-align:center;}
.table_list td p{text-align:left; padding-left:5px; line-height:15px;}
-->
</style>
<body>
<div id="page1">
  <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" id="tb">
    <tr>
      <td>
	  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
		     <th colspan="2" class="head" style="padding-top: 15px;padding-bottom:5px;font-size:18pt;"><br/><br/>交通运输等重点行业（领域）企业单位安全生产隐患排查治理情况统计表<br/></th>
        </tr>
        <tr>
		     <td colspan="2" height="30"  style="vertical-align:top;font-size:14pt;text-align:center" >（${fn:substring(mineReport.reportMonth,0,4)}年1月-<span id="month"></span>月）</th>
        </tr>
		<tr style="font-size:12px">
		  <td height="18" width="80%">填报单位（章）:${fillUnit}</span></td>
		  <!--td height="18" width="20%" align="right" nowrap="nowrap" valign="bottom"> 
		  截止到${fn:substring(mineReport.reportMonth,0,4)}年${fn:substring(mineReport.reportMonth,5,7)}月<span id="day"></span>日为止</td-->
		</tr>
      </table>
	  </td>
    </tr>
    <tr>
      <td><table width="100%" cellpadding="0" cellspacing="0" class="table_list">
        <col widtd="72" span="15" />
        <tr height="20">
          <td rowspan="5" align="center" widtd="72">行业和领域</td>
          <td rowspan="3" nowrap><center>排查治<br/>理事故<br/>隐患的<br/>生产经<br/>营单位</center></td>
          <td colspan="3" widtd="216"><center>一般事故隐患</center></td>
          <td colspan="7" widtd="720"><center> 重大事故隐患</center></td>
          <td rowspan="3"><center>累计落实治理资金</center></td>
        </tr>
        <tr height="20">
          <td rowspan="2"><center>排查一般事故隐患</center></td>
          <td rowspan="2"><center>其中：<br/>已整改</center></td>
          <td rowspan="2" nowrap><center>整改率</center></td>
          <td height="20" colspan="3"><center>排查治理重大隐患</center></td>
          <td colspan="2"><center>其中：例如治理计划</center></td>
          <td colspan="2"><center>其中：挂牌督办</center></td>
        </tr>
        <tr height="20">
          <td nowrap><center>排查重<br/>大事故<br/>隐&nbsp;&nbsp;患</center></td>
          <td nowrap><center>其中：<br/>已整改<br/>销号</center></td>
          <td nowrap><center>整改率</center></td>
          <td nowrap><center>列入治理&nbsp;&nbsp;<br/>计划的重大<br/>事故隐患&nbsp;&nbsp;</center></td>
          <td nowrap><center>其中：达到<br/>“五到位”<br/>要求的&nbsp;&nbsp;</center></td>
          <td nowrap><center>挂牌督办<br/>的重大&nbsp;&nbsp;<br/>事故隐患</center></td>
          <td nowrap><center>其中：&nbsp;&nbsp;<br/>省级&nbsp;&nbsp;&nbsp;&nbsp;<br/>挂牌督办</center></td>
        </tr>
        <tr height="20">
          <td height="20"><center>(家)</center></td>
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
          <td><center>(万元)</center></td>
        </tr>
        <tr>
          <td height="20"><center>1</center></td>
          <td><center>2</center></td>
          <td><center>3</center></td>
          <td><center>4</center></td>
          <td><center>5</center></td>
          <td><center>6</center></td>
          <td><center>7</center></td>
          <td><center>8</center></td>
          <td><center>9</center></td>
          <td><center>10</center></td>
          <td><center>11</center></td>
          <td><center>12</center></td>
        </tr>
		<tr>
	  <td nowrap="true" width="18%" class="td_left"><p><strong>1.道路运输企业</strong></p></td>
	  <td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[0].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[0].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[0].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_0">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[0].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[0].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[0].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[0].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[0].bigTrouble==0||statistics[0].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[0].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[0].bigTroubleGovern/statistics[0].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[0].bigTroubleGovern/statistics[0].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[0].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[0].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[0].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[0].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[0].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[0].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[0].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[0].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[0].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[0].planMoneyString,(fn:length((mineReportDetails[0].planMoneyString))-2),fn:length((mineReportDetails[0].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[0].planMoney}"/></c:when><c:otherwise>${mineReportDetails[0].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>2.公路养护施工企业</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[1].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[1].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[1].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_1">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[1].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[1].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[1].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[1].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[1].bigTrouble==0||statistics[1].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[1].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[1].bigTroubleGovern/statistics[1].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[1].bigTroubleGovern/statistics[1].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[1].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[1].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[1].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[1].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[1].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[1].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[1].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[1].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[1].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[1].planMoneyString,(fn:length((mineReportDetails[1].planMoneyString))-2),fn:length((mineReportDetails[1].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[1].planMoney}"/></c:when><c:otherwise>${mineReportDetails[1].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>3.水上运输企业</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[2].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[2].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[2].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_2">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[2].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[2].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[2].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[2].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[2].bigTrouble==0||statistics[2].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[2].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[2].bigTroubleGovern/statistics[2].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[2].bigTroubleGovern/statistics[2].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[2].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[2].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[2].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[2].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[2].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[2].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[2].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[2].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[2].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[2].planMoneyString,(fn:length((mineReportDetails[2].planMoneyString))-2),fn:length((mineReportDetails[2].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[2].planMoney}"/></c:when><c:otherwise>${mineReportDetails[2].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>4.铁路运输企业</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[3].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[3].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[3].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[3].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[3].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[3].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_3">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[3].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[3].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[3].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[3].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[3].bigTrouble==0||statistics[3].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[3].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[3].bigTroubleGovern/statistics[3].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[3].bigTroubleGovern/statistics[3].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[3].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[3].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[3].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[3].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[3].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[3].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[3].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[3].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[3].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[3].planMoneyString,(fn:length((mineReportDetails[3].planMoneyString))-2),fn:length((mineReportDetails[3].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[3].planMoney}"/></c:when><c:otherwise>${mineReportDetails[3].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>
	  <tr>
  <td nowrap="true" width="18%"><p><strong>5.航空公司</strong></p></td>
 	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[4].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[4].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[4].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[4].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[4].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[4].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_4">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[4].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[4].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[4].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[4].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[4].bigTrouble==0||statistics[4].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[4].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[4].bigTroubleGovern/statistics[4].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[4].bigTroubleGovern/statistics[4].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[4].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[4].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[4].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[4].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[4].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[4].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[4].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[4].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[4].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[4].planMoneyString,(fn:length((mineReportDetails[4].planMoneyString))-2),fn:length((mineReportDetails[4].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[4].planMoney}"/></c:when><c:otherwise>${mineReportDetails[4].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>
	  
	  <tr>
	  <td nowrap="true" width="18%" class="td_left"><p><strong>6.机场和油料企业</strong></p></td>
	  <td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[5].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[5].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[5].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[5].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[5].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[5].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_5">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[5].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[5].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[5].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[5].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[5].bigTrouble==0||statistics[5].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[5].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[5].bigTroubleGovern/statistics[5].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[5].bigTroubleGovern/statistics[5].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[5].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[5].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[5].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[5].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[5].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[5].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[5].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[5].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[5].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[5].planMoneyString,(fn:length((mineReportDetails[5].planMoneyString))-2),fn:length((mineReportDetails[5].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[5].planMoney}"/></c:when><c:otherwise>${mineReportDetails[5].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>7.渔业企业</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[6].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[6].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[6].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_6">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[6].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[6].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[6].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[6].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[6].bigTrouble==0||statistics[6].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[6].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[6].bigTroubleGovern/statistics[6].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[6].bigTroubleGovern/statistics[6].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[6].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[6].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[6].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[6].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[6].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[6].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[6].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[6].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[6].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[6].planMoneyString,(fn:length((mineReportDetails[6].planMoneyString))-2),fn:length((mineReportDetails[6].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[6].planMoney}"/></c:when><c:otherwise>${mineReportDetails[6].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>8.农机行业</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[7].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[7].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[7].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_7">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[7].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[7].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[7].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[7].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[7].bigTrouble==0||statistics[7].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[7].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[7].bigTroubleGovern/statistics[7].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[7].bigTroubleGovern/statistics[7].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[7].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[7].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[7].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[7].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[7].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[7].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[7].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[7].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[7].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[7].planMoneyString,(fn:length((mineReportDetails[7].planMoneyString))-2),fn:length((mineReportDetails[7].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[7].planMoney}"/></c:when><c:otherwise>${mineReportDetails[7].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>9.水利工程</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[8].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[8].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[8].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[8].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[8].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[8].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_8">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[8].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[8].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[8].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[8].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[8].bigTrouble==0||statistics[8].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[8].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[8].bigTroubleGovern/statistics[8].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[8].bigTroubleGovern/statistics[8].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[8].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[8].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[8].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[8].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[8].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[8].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[8].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[8].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[8].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[8].planMoneyString,(fn:length((mineReportDetails[8].planMoneyString))-2),fn:length((mineReportDetails[8].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[8].planMoney}"/></c:when><c:otherwise>${mineReportDetails[8].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>
	 <!--tr>
  <td nowrap="true" width="18%"><p>其中：水库</p></td>
  <td align="center" nowrap="true" id="company_9"><c:choose><c:when test="${mineReportDetails[9].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[9].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger_9"><c:choose><c:when test="${mineReportDetails[9].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[9].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern_9"><c:choose><c:when test="${mineReportDetails[9].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[9].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_9">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[9].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[9].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[9].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[9].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[9].bigTrouble==0||statistics[9].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[9].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[9].bigTroubleGovern/statistics[9].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[9].bigTroubleGovern/statistics[9].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[9].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[9].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[9].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[9].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="g"><c:choose><c:when test="${statistics[9].goods==0}">&nbsp;</c:when><c:otherwise>${statistics[9].goods}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="w"><c:choose><c:when test="${statistics[9].worker==0}">&nbsp;</c:when><c:otherwise>${statistics[9].worker}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[9].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[9].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[9].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[9].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney_9"><c:choose><c:when test="${mineReportDetails[9].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[9].planMoneyString,(fn:length((mineReportDetails[9].planMoneyString))-2),fn:length((mineReportDetails[9].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[9].planMoney}"/></c:when><c:otherwise>${mineReportDetails[9].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr-->
		<tr>
	  <td nowrap="true" width="18%" class="td_left"><p><strong>10.学校</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[10].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[10].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[10].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[10].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[10].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[10].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_10">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[10].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[10].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[10].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[10].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[10].bigTrouble==0||statistics[10].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[10].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[10].bigTroubleGovern/statistics[10].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[10].bigTroubleGovern/statistics[10].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[10].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[10].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[10].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[10].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[10].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[10].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[10].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[10].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[10].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[10].planMoneyString,(fn:length((mineReportDetails[10].planMoneyString))-2),fn:length((mineReportDetails[10].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[10].planMoney}"/></c:when><c:otherwise>${mineReportDetails[10].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>11.商场、市场等人员密集场所</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[11].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[11].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[11].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[11].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[11].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[11].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_11">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[11].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[11].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[11].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[11].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[11].bigTrouble==0||statistics[11].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[11].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[11].bigTroubleGovern/statistics[11].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[11].bigTroubleGovern/statistics[11].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[11].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[11].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[11].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[11].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[11].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[11].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[11].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[11].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[11].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[11].planMoneyString,(fn:length((mineReportDetails[11].planMoneyString))-2),fn:length((mineReportDetails[11].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[11].planMoney}"/></c:when><c:otherwise>${mineReportDetails[11].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>12.建筑施工企业</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[12].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[12].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[12].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_12">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[12].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[12].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[12].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[12].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[12].bigTrouble==0||statistics[12].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[12].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[12].bigTroubleGovern/statistics[12].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[12].bigTroubleGovern/statistics[12].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[12].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[12].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[12].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[12].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[12].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[12].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[12].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[12].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[12].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[12].planMoneyString,(fn:length((mineReportDetails[12].planMoneyString))-2),fn:length((mineReportDetails[12].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[12].planMoney}"/></c:when><c:otherwise>${mineReportDetails[12].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>13.民爆器材生产企业</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[13].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[13].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[13].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_13">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[13].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[13].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[13].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[13].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[13].bigTrouble==0||statistics[13].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[13].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[13].bigTroubleGovern/statistics[13].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[13].bigTroubleGovern/statistics[13].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[13].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[13].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[13].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[13].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[13].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[13].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[13].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[13].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[13].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[13].planMoneyString,(fn:length((mineReportDetails[13].planMoneyString))-2),fn:length((mineReportDetails[13].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[13].planMoney}"/></c:when><c:otherwise>${mineReportDetails[13].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
  <td nowrap="true" width="18%"><p><strong>14.电力企业</strong></p></td>
  <td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[14].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[14].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[14].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[14].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[14].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[14].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_14">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[14].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[14].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[14].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[14].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[14].bigTrouble==0||statistics[14].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[14].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[14].bigTroubleGovern/statistics[14].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[14].bigTroubleGovern/statistics[14].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[14].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[14].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[14].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[14].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[14].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[14].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[14].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[14].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[14].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[14].planMoneyString,(fn:length((mineReportDetails[14].planMoneyString))-2),fn:length((mineReportDetails[14].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[14].planMoney}"/></c:when><c:otherwise>${mineReportDetails[14].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>
	  
		<tr>
	  <td nowrap="true" width="18%" class="td_left"><p><strong>15.机械制造企业</strong></p></td>
	  <td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[15].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[15].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[15].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[15].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[15].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[15].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_15">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[15].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[15].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[15].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[15].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[15].bigTrouble==0||statistics[15].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[15].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[15].bigTroubleGovern/statistics[15].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[15].bigTroubleGovern/statistics[15].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[15].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[15].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[15].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[15].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[15].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[15].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[15].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[15].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[15].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[15].planMoneyString,(fn:length((mineReportDetails[15].planMoneyString))-2),fn:length((mineReportDetails[15].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[15].planMoney}"/></c:when><c:otherwise>${mineReportDetails[15].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>16.其他企业和单位</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[16].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[16].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[16].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_16">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[16].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[16].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[16].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[16].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[16].bigTrouble==0||statistics[16].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[16].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[16].bigTroubleGovern/statistics[16].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[16].bigTroubleGovern/statistics[16].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[16].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[16].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[16].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[16].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[16].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[16].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[16].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[16].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[16].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[16].planMoneyString,(fn:length((mineReportDetails[16].planMoneyString))-2),fn:length((mineReportDetails[16].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[16].planMoney}"/></c:when><c:otherwise>${mineReportDetails[16].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>
	  
		<tr><td nowrap="true" width="18%"><p><strong>合计</strong></p></td>
		<td align="center" nowrap="true" id="company_t">&nbsp;</td>
		<td align="center" nowrap="true" id="generalDanger_t">&nbsp;</td>
		<td align="center" nowrap="true" id="generalDangerGovern_t">&nbsp;</td>
	  <td align="center" nowrap="true" id="zg_t">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt_t">&nbsp;</td>
	  <td align="center" nowrap="true" id="btg_t">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt_r_t">&nbsp;</td>
	  <td align="center" nowrap="true" id="btng_t">&nbsp;</td>
	  <td align="center" nowrap="true" id="wdw_t">&nbsp;</td>
	  <td align="center" nowrap="true" id="gpt_t">&nbsp;</td>
	  <td align="center" nowrap="true" id="pgp_t">&nbsp;</td>
	  <td align="center" nowrap="true" id="planMoney_t">&nbsp;</td></tr>
	  
 	 </table>
 		
		<tr>
	 <td colspan="15">
	  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	    <tr style="font-size:12px">
		  <td width="20%"><br/>单位负责人： ${mineReport.chargeMan}</td>
		  <td align="center" width="25%"><br/>填表人：${mineReport.fillMan}</td>
		  <td  align="center" width="25%"><br/>联系电话：${mineReport.tel}</td>
		  <td width="30%" align="center"><br/>填报日期:<s:date name='mineReport.fillDate' format='yyyy-MM-dd' /></td>       
	   </tr>
	  </table>
	 </td>
	</tr> 
	   </td>
    </tr>	
  </table>
  </div>
   <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
   <tr><td width="100%" style="font-size:12px;"><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：1.表中数据关系：（8）小于或等于（5）-（6）；（9）、（10）、（11）、（12）、（13）分别小于或等于（8）。 
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.统计数据为本年度1月份以来的累计数据。	
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.列入治理计划的重大隐患(8)应包括往年未完成治理情况,另注说明。
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.表中关系说明：（1）代表“排查治理隐患单位数”，（2）代表“隐患数”，依次（14）代表“累计落实隐患治理资金”	。
</td>
	</tr>
 <tr>
	 <td width="100%" style="font-size:12px;color:red;"><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如已按提示设置IE且仍未能打印者，请点击菜单“下载打印控件”！</td>
	</tr>
 <tr>
	<tr>		 
     <td width="28%" align="center">
     <input type="button" name="back" class="but_2" onClick="doPrint('printPreview');" value="打印预览" />&nbsp;&nbsp;
		 <input type="button" name="back" class="but_2" onClick="if(window.confirm('确定打印吗？'))doPrint('print');" value="打  印" />&nbsp;&nbsp;
		 <input type="button" class="but_2" value="返  回"  onclick="history.back(-1);"/> 
	 </td>
    </tr>
</table>
</form>
</body>
<script type="text/javascript">
var especial = "";
//计算整改率
for (var i=0;i<getAll('generalDanger').length;i++) {
	if(i<9) {
		getDivisor(getAll('generalDangerGovern')[i],getAll('generalDanger')[i],'zg_'+i,true);
	}else{
		getDivisor(getAll('generalDangerGovern')[i],getAll('generalDanger')[i],'zg_'+(i+1),true);
	}
	if(i==9){
		getDivisor(get('generalDangerGovern_'+i),get('generalDanger_'+i),'zg_'+i,true);
	}
}
//合计
setIsPrint(true);
sumSons(get('company_t'),getAll('company'),get('company_t'),true);
sumSons(get('generalDanger_t'),getAll('generalDanger'),get('generalDanger_t'),true);
sumSons(get('generalDangerGovern_t'),getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);
sumSons(get('planMoney_t'),getAll('planMoney'),get('planMoney_t'),false);
sumSonInnerHTML("0","1","2","3","4","5","6","7","8","10","11","12","13","14","15","9","t");
var t1 = parseFloat(get("planMoney_t").innerHTML);
var t2 = parseFloat(get("planMoney_t").innerHTML).Fixed(1);
if (t1 = t2)
	get("planMoney_t").innerHTML = t1;
else
	get("planMoney_t").innerHTML = isNaN(t2)?"&nbsp;":t2;
if(get("day"))
if("${mineReport.reportMonth}".substring(5,7) == "04" || "${mineReport.reportMonth}".substring(5,7) == "06" || "${mineReport.reportMonth}".substring(5,7) == "09" || "${mineReport.reportMonth}".substring(5,7) == "11") {
	get("day").innerHTML="30";
} else {
	if ("${mineReport.reportMonth}".substring(5,7) == "02") {
		if(("${mineReport.reportMonth}".substring(0,4) % 4 == 0 && "${mineReport.reportMonth}".substring(0,4) % 100 != 0) || "${mineReport.reportMonth}".substring(0,4) % 400 == 0)
			get("day").innerHTML="29";
		else
			get("day").innerHTML="28";
	} else {
		get("day").innerHTML="31";
	}
}
if(get("month"))get("month").innerHTML = parseFloat("${fn:substring(mineReport.reportMonth,5,fn:length(mineReport.reportMonth))}");
printParam(5,0,0,0,2);
</script>
</html>