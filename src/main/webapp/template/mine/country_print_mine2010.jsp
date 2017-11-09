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
		     <th colspan="2" class="head" style="padding-top: 15px;padding-bottom:5px;font-size:18pt;" ><br/><br/>煤矿、金属非金属矿山等工矿企业安全生产事故隐患排查治理情况月报表<br/></th>
        </tr>
        <tr>
		     <td colspan="2" height="30"  style="vertical-align:top;font-size:14pt;text-align:center" >${fn:substring(mineReport.reportMonth,0,4)}年1月-<span id="month"></span>月</th>
        </tr>
		<tr style="font-size:12px">
		  <td height="18" width="80%">填报单位:${fillUnit}&nbsp;&nbsp;&nbsp;&nbsp;</td>
		  <!--td height="18" width="20%" align="right" nowrap="nowrap" valign="bottom">	
		 截止到${fn:substring(mineReport.reportMonth,0,4)}年${fn:substring(mineReport.reportMonth,5,7)}月<span id="day"></span>日为止
		</td-->
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
  <td nowrap="true" width="18%"><p><strong>1.煤矿企业</strong></p></td>
  <td align="center" nowrap="true" id="company">&nbsp;</td>
	  <td align="center" nowrap="true" id="generalDanger">&nbsp;</td>
	  <td align="center" nowrap="true" id="generalDangerGovern">&nbsp;</td>
	  <td align="center" nowrap="true" id="zg_0">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt">&nbsp;</td>
	  <td align="center" nowrap="true" id="btg">&nbsp;</td>
	  <td align="center" nowrap="true">&nbsp;</td>
	  <td align="center" nowrap="true" id="btng">&nbsp;</td>
	  <td align="center" nowrap="true" id="wdw">&nbsp;</td>
	  <td align="center" nowrap="true" id="gpt">&nbsp;</td>
	  <td align="center" nowrap="true" id="pgp">&nbsp;</td>
	  <td align="center" nowrap="true" id="planMoney">&nbsp;</td>
	  </tr>
        
        
		<tr>
	  <td nowrap="true" width="18%" class="td_left"><p><strong>2.金属非金属矿山企业</strong></p></td>
	  <td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[0].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[0].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[0].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_1">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[0].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[0].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[0].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[0].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[0].bigTrouble==0||statistics[0].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[0].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[0].bigTroubleGovern/statistics[0].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[0].bigTroubleGovern/statistics[0].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btng"><c:choose><c:when test="${statistics[0].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[0].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[0].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[0].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[0].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[0].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[0].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[0].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[0].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[0].planMoneyString,(fn:length((mineReportDetails[0].planMoneyString))-2),fn:length((mineReportDetails[0].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[0].planMoney}"/></c:when><c:otherwise>${mineReportDetails[0].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>3.尾矿库</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[1].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[1].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[1].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_2">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[1].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[1].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[1].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[1].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[1].bigTrouble==0||statistics[1].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[1].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[1].bigTroubleGovern/statistics[1].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[1].bigTroubleGovern/statistics[1].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btng"><c:choose><c:when test="${statistics[1].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[1].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[1].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[1].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[1].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[1].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[1].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[1].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[1].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[1].planMoneyString,(fn:length((mineReportDetails[1].planMoneyString))-2),fn:length((mineReportDetails[1].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[1].planMoney}"/></c:when><c:otherwise>${mineReportDetails[1].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>
	  
	  
	            <tr>
  <td nowrap="true" width="18%"><p><strong>4.石油天然气开采企业</strong></p></td>
  <td align="center" nowrap="true" id="company">&nbsp;</td>
	  <td align="center" nowrap="true" id="generalDanger">&nbsp;</td>
	  <td align="center" nowrap="true" id="generalDangerGovern">&nbsp;</td>
	  <td align="center" nowrap="true" id="zg_3">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt">&nbsp;</td>
	  <td align="center" nowrap="true" id="btg">&nbsp;</td>
	  <td align="center" nowrap="true">&nbsp;</td>
	  <td align="center" nowrap="true" id="btng">&nbsp;</td>
	  <td align="center" nowrap="true" id="wdw">&nbsp;</td>
	  <td align="center" nowrap="true" id="gpt">&nbsp;</td>
	  <td align="center" nowrap="true" id="pgp">&nbsp;</td>
	  <td align="center" nowrap="true" id="planMoney">&nbsp;</td>
	  </tr>
	 
	  <tr>
  <td nowrap="true" width="18%"><p><strong>5.危险化学品企业</strong></p></td>
  	  <td align="center" nowrap="true" width="4%" id="company_6"></td>
  	  <td align="center" nowrap="true" width="4%" id="generalDanger_6"></td>
	  <td align="center" nowrap="true" id="generalDangerGovern_6"></td>
	  <td align="center" nowrap="true" id="zg_n_6">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt_6">0</td>
	  <td align="center" nowrap="true" id="btg_6">0</td>
	  <td align="center" nowrap="true" id="bt_r_6">&nbsp;</td>
	  <td align="center" nowrap="true" id="btng_6">0</td>
	  <td align="center" nowrap="true" id="wdw_6">0</td>
	  <td align="center" nowrap="true" id="gpt_6">0</td>
	  <td align="center" nowrap="true" id="pgp_6">0</td>
	  <td align="center" nowrap="true" id="planMoney_6"></td></tr>
	<td nowrap="true" width="18%"><p>（1）生产、储存和其他化工企业</p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[6].company+mineReportDetails[8].company+mineReportDetails[9].company+mineReportDetails[10].company+mineReportDetails[11].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].company+mineReportDetails[8].company+mineReportDetails[9].company+mineReportDetails[10].company+mineReportDetails[11].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[6].generalDanger+mineReportDetails[8].generalDanger+mineReportDetails[9].generalDanger+mineReportDetails[10].generalDanger+mineReportDetails[11].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].generalDanger+mineReportDetails[8].generalDanger+mineReportDetails[9].generalDanger+mineReportDetails[10].generalDanger+mineReportDetails[11].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[6].generalDangerGovern+mineReportDetails[8].generalDangerGovern+mineReportDetails[9].generalDangerGovern+mineReportDetails[10].generalDangerGovern+mineReportDetails[11].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].generalDangerGovern+mineReportDetails[8].generalDangerGovern+mineReportDetails[9].generalDangerGovern+mineReportDetails[10].generalDangerGovern+mineReportDetails[11].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_4">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[6].bigTrouble+statistics[8].bigTrouble+statistics[9].bigTrouble+statistics[10].bigTrouble+statistics[11].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[6].bigTrouble+statistics[8].bigTrouble+statistics[9].bigTrouble+statistics[10].bigTrouble+statistics[11].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[6].bigTroubleGovern+statistics[8].bigTroubleGovern+statistics[9].bigTroubleGovern+statistics[10].bigTroubleGovern+statistics[11].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[6].bigTroubleGovern+statistics[8].bigTroubleGovern+statistics[9].bigTroubleGovern+statistics[10].bigTroubleGovern+statistics[11].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="bt_r_4">
	  <c:choose><c:when test="${statistics[6].bigTrouble+statistics[8].bigTrouble+statistics[9].bigTrouble+statistics[10].bigTrouble+statistics[11].bigTrouble==0||statistics[6].bigTroubleGovern+statistics[8].bigTroubleGovern+statistics[9].bigTroubleGovern+statistics[10].bigTroubleGovern+statistics[11].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${((statistics[6].bigTroubleGovern+statistics[8].bigTroubleGovern+statistics[9].bigTroubleGovern+statistics[10].bigTroubleGovern+statistics[11].bigTroubleGovern)/(statistics[6].bigTrouble+statistics[8].bigTrouble+statistics[9].bigTrouble+statistics[10].bigTrouble+statistics[11].bigTrouble))*100}"/></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btng"><c:choose><c:when test="${statistics[6].bigTroubleNotGovern+statistics[8].bigTroubleNotGovern+statistics[9].bigTroubleNotGovern+statistics[10].bigTroubleNotGovern+statistics[11].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[6].bigTroubleNotGovern+statistics[8].bigTroubleNotGovern+statistics[9].bigTroubleNotGovern+statistics[10].bigTroubleNotGovern+statistics[11].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[6].wdw+statistics[8].wdw+statistics[9].wdw+statistics[10].wdw+statistics[11].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[6].wdw+statistics[8].wdw+statistics[9].wdw+statistics[10].wdw+statistics[11].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[6].guapaiTotal+statistics[8].guapaiTotal+statistics[9].guapaiTotal+statistics[10].guapaiTotal+statistics[11].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[6].guapaiTotal+statistics[8].guapaiTotal+statistics[9].guapaiTotal+statistics[10].guapaiTotal+statistics[11].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[6].provinceGuapai+statistics[8].provinceGuapai+statistics[9].provinceGuapai+statistics[10].provinceGuapai+statistics[11].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[6].provinceGuapai+statistics[8].provinceGuapai+statistics[9].provinceGuapai+statistics[10].provinceGuapai+statistics[11].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney">
	  <c:choose>
	  <c:when test="${mineReportDetails[6].planMoney+mineReportDetails[8].planMoney+mineReportDetails[9].planMoney+mineReportDetails[10].planMoney+mineReportDetails[11].planMoney==0.0}">&nbsp;
	  </c:when>
	  <c:otherwise>
	  	<fmt:formatNumber type="number" value="${mineReportDetails[6].planMoney+mineReportDetails[8].planMoney+mineReportDetails[9].planMoney+mineReportDetails[10].planMoney+mineReportDetails[11].planMoney}" maxFractionDigits="1" pattern="0.0"/>
	  </c:otherwise>
	  </c:choose></td></tr><tr>
  <td nowrap="true" width="18%"><p>（2）经营企业和单位</p></td>
  <td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[7].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[7].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[7].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_5">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[7].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[7].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[7].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[7].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[7].bigTrouble==0||statistics[7].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[7].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[7].bigTroubleGovern/statistics[7].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[7].bigTroubleGovern/statistics[7].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btng"><c:choose><c:when test="${statistics[7].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[7].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[7].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[7].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[7].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[7].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[7].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[7].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[7].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[7].planMoneyString,(fn:length((mineReportDetails[7].planMoneyString))-2),fn:length((mineReportDetails[7].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[7].planMoney}"/></c:when><c:otherwise>${mineReportDetails[7].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>

	
	  <tr>
		  <td nowrap="true" width="18%"><p>（3）道路运输企业和单位</p></td>
		  <td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[12].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[12].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[12].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_6">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[12].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[12].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[12].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[12].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[12].bigTrouble==0||statistics[12].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[12].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[12].bigTroubleGovern/statistics[12].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[12].bigTroubleGovern/statistics[12].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btng"><c:choose><c:when test="${statistics[12].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[12].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[12].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[12].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[12].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[12].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[12].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[12].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[12].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[12].planMoneyString,(fn:length((mineReportDetails[12].planMoneyString))-2),fn:length((mineReportDetails[12].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[12].planMoney}"/></c:when><c:otherwise>${mineReportDetails[12].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>
	  <tr>
		<td nowrap="true" width="18%"><p><strong>6.烟花爆竹企业</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[13].company+mineReportDetails[14].company+mineReportDetails[15].company==0||(mineReportDetails[13].company==null&&mineReportDetails[14].company==null&&mineReportDetails[15].company==null)}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].company+mineReportDetails[14].company+mineReportDetails[15].company}</c:otherwise></c:choose></td>
	<td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[13].generalDanger+mineReportDetails[14].generalDanger+mineReportDetails[15].generalDanger==0||(mineReportDetails[13].generalDanger==null&&mineReportDetails[14].generalDanger==null&&mineReportDetails[15].generalDanger==null)}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].generalDanger+mineReportDetails[14].generalDanger+mineReportDetails[15].generalDanger}</c:otherwise></c:choose></td>
	<td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[13].generalDangerGovern+mineReportDetails[14].generalDangerGovern+mineReportDetails[15].generalDangerGovern==0||(mineReportDetails[13].generalDangerGovern==null&&mineReportDetails[14].generalDangerGovern&&mineReportDetails[15].generalDangerGovern)}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].generalDangerGovern+mineReportDetails[14].generalDangerGovern+mineReportDetails[15].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_7">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[13].bigTrouble+statistics[14].bigTrouble+statistics[15].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[13].bigTrouble+statistics[14].bigTrouble+statistics[15].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[13].bigTroubleGovern+statistics[14].bigTroubleGovern+statistics[15].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[13].bigTroubleGovern+statistics[14].bigTroubleGovern+statistics[15].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="bt_r_7">
	  <c:choose><c:when test="${statistics[13].bigTrouble+statistics[14].bigTrouble+statistics[15].bigTrouble==0||statistics[13].bigTroubleGovern+statistics[14].bigTroubleGovern+statistics[15].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${((statistics[13].bigTroubleGovern+statistics[14].bigTroubleGovern+statistics[15].bigTroubleGovern)/(statistics[13].bigTrouble+statistics[14].bigTrouble+statistics[15].bigTrouble))*100}"/></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btng"><c:choose><c:when test="${statistics[13].bigTroubleNotGovern+statistics[14].bigTroubleNotGovern+statistics[15].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[13].bigTroubleNotGovern+statistics[14].bigTroubleNotGovern+statistics[15].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[13].wdw+statistics[14].wdw+statistics[15].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[13].wdw+statistics[14].wdw+statistics[15].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[13].guapaiTotal+statistics[14].guapaiTotal+statistics[15].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[13].guapaiTotal+statistics[14].guapaiTotal+statistics[15].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[13].provinceGuapai+statistics[14].provinceGuapai+statistics[15].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[13].provinceGuapai+statistics[14].provinceGuapai+statistics[15].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[13].planMoney+mineReportDetails[14].planMoney+mineReportDetails[15].planMoney==0.0||(mineReportDetails[13].planMoney==null&&mineReportDetails[14].planMoney==null&&mineReportDetails[15].planMoney==null)}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].planMoney+mineReportDetails[14].planMoney+mineReportDetails[15].planMoney}</c:otherwise></c:choose></td></tr>
	  
	
          
           <tr>
	<td nowrap="true" width="18%"><p><strong>7.冶金、有色企业</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[2].company+mineReportDetails[3].company==0||(mineReportDetails[2].company==null&&mineReportDetails[3].company==null)}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].company+mineReportDetails[3].company}</c:otherwise></c:choose></td>
	<td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[2].generalDanger+mineReportDetails[3].generalDanger==0||(mineReportDetails[2].generalDanger==null&&mineReportDetails[3].generalDanger)}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].generalDanger+mineReportDetails[3].generalDanger}</c:otherwise></c:choose></td>
	<td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[2].generalDangerGovern+mineReportDetails[3].generalDangerGovern==0||(mineReportDetails[2].generalDangerGovern==null&&mineReportDetails[3].generalDangerGovern)}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].generalDangerGovern+mineReportDetails[3].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_8">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[2].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[2].bigTrouble+statistics[3].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[2].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[2].bigTroubleGovern+statistics[3].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="bt_r_8">
	  <c:choose><c:when test="${statistics[2].bigTrouble+statistics[3].bigTrouble==0||statistics[2].bigTroubleGovern+statistics[3].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${((statistics[2].bigTroubleGovern+statistics[3].bigTroubleGovern)/(statistics[2].bigTrouble+statistics[3].bigTrouble))*100}"/></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btng"><c:choose><c:when test="${statistics[2].bigTroubleNotGovern+statistics[3].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[2].bigTroubleNotGovern+statistics[3].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="wdw"><c:choose><c:when test="${statistics[2].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[2].wdw}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gpt"><c:choose><c:when test="${statistics[2].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[2].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="pgp"><c:choose><c:when test="${statistics[2].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[2].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[2].planMoney+mineReportDetails[3].planMoney==0.0||(mineReportDetails[2].planMoney==null&&mineReportDetails[3].planMoney==null)}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].planMoney+mineReportDetails[3].planMoney}</c:otherwise></c:choose></td></tr>
	  
          
          
          
		<tr>
			<td nowrap="true" width="18%"><p><strong>8.其他企业</strong></p></td>
			 <td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[16].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[16].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[16].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_9">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[16].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[16].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[16].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[16].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[16].bigTrouble==0||statistics[16].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[16].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[16].bigTroubleGovern/statistics[16].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[16].bigTroubleGovern/statistics[16].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btng"><c:choose><c:when test="${statistics[16].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[16].bigTroubleNotGovern}</c:otherwise></c:choose></td>
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
	  </tr>
  </table>
   	<tr>
	 <td colspan="15">
	  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	    <tr style="font-size:12px">
		  <td width="20%"><br/>审核人： ${mineReport.chargeMan}</td>
		  <td align="center" width="30%"><br/>填表人：${mineReport.fillMan}</td>
		  <td  align="center" width="30%"><br/>联系电话：${mineReport.tel}</td>
		  <td width="40%"><br/>填报日期：<s:date name='mineReport.fillDate' format='yyyy年 MM月 dd日' /></td>       
	   </tr>
	  </table>
	 </td>
	</tr> 
   </td>
  </tr>
  <tr><td width="100%" style="font-size:12px;padding-left:200px;"><br/><br/><br/>有关要求：1.各地区要认真填写本表，并于每月6日前以网上填报、传真（或电子邮件）等方式报送至国务院安委会办公室。
<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.统计数据为${fn:substring(mineReport.reportMonth,0,4)}年1月以来的累计数据。
</td>
	</tr>
  </table>
  </div>
 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
 <tr>
	 <td width="100%" style="font-size:12px;color:red;"><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如已按提示设置IE且仍未能打印者，请点击菜单“下载打印控件”！</td>
	</tr>
 <tr>
   <tr>		 
      <td align="center"><input name="yulan" type="button" class="but_2" value="打印预览"  onclick="javascript:doPrint('printPreview');"/>&nbsp;&nbsp;
        <input name="print" type="button" class="but_2" value="打   印"  onclick="javascript:if(confirm('   确定要打印吗?')){doPrint('print');}"/>&nbsp;&nbsp;
        <input type="button" class="but_2" value="返  回"  onclick="history.back(-1);"/>
	    <input type="hidden" id="type" name="mineReport.type" value="${mineReport.type}"/>
	    <input type="hidden" id="isReport" name="mineReport.report" value="true"/>
	    <input type="hidden" id="id" name="mineReport.id" value="${mineReport.id}" disabled="true"/>
	 </td>
  </tr>
</table>
</body>
<script type="text/javascript">
setIsPrint(true);
var especial = "5";
//6.危险化学品企业		
sumSon(getAll('company')[4],getAll('company')[5],getAll('company')[6],get('company_6'),true);
sumSon(getAll('generalDanger')[4],getAll('generalDanger')[5],getAll('generalDanger')[6],get('generalDanger_6'),true);
sumSon(getAll('generalDangerGovern')[4],getAll('generalDangerGovern')[5],getAll('generalDangerGovern')[6],get('generalDangerGovern_6'),true);
sumSon(getAll('planMoney')[4],getAll('planMoney')[5],getAll('planMoney')[6],get('planMoney_6'),false);
//计算整改率
for (var i=0;i<getAll('generalDanger').length;i++) {
	if(i==4){
		getDivisor(get('generalDangerGovern_6'),get('generalDanger_6'),'zg_n_6',true);
	}
	getDivisor(getAll('generalDangerGovern')[i],getAll('generalDanger')[i],'zg_'+i,true);
}

sumSons(get('company_t'),getAll('company'),get('company_t'),true);
sumSons(get('generalDanger_t'),getAll('generalDanger'),get('generalDanger_t'),true);
sumSons(get('generalDangerGovern_t'),getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);
sumSons(get('planMoney_t'),getAll('planMoney'),get('planMoney_t'),false);
//设置TD值
sumSonInnerHTML("4","5","6","6");//sumSonInnerHTML("6","7","8","9","10","11","12","6");
sumSonInnerHTML("0","1","2","3","4","5","6","7","8","9","t");
delZero(get("bt_r_7"),getAll("planMoney")[7],get("bt_r_8"),getAll("planMoney")[8]);
<c:if test="${!empty(reportMonth)&&reportMonth!=''}">
	var year = "${reportMonth}".split("-")[0];
	var month = "${reportMonth}".split("-")[1];
	get("year").value=year;
	get("month").value=month;
</c:if>
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
function delZero() {
	if (arguments) {
		for (var i=0; i<arguments.length; i++) {
			if(arguments[i] && arguments[i].innerHTML.indexOf(".0") > -1){
				arguments[i].innerHTML = arguments[i].innerHTML.substring(0,arguments[i].innerHTML.length-2);
			}
		}
	}
}
if(get("month"))get("month").innerHTML = parseFloat("${fn:substring(mineReport.reportMonth,5,fn:length(mineReport.reportMonth))}");
print
printParam(5,0,0,0,2);
</script>
</html>