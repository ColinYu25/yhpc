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
	height:11px;
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
<body>
    <div id="page1">
  <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" id="tb">
     <tr>
      <td>
	  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
		     <th colspan="2" height="30" class="head">其他重点行业领域安全生产隐患排查治理情况统计表</th>
        </tr>
		<tr style="font-size:12px">
		  <td height="18" width="80%">填报单位（章）:${fillUnit}</td>
		  <td height="18" width="20%" align="right" nowrap="nowrap" valign="bottom">
			  截止到${fn:substring(mineReport.reportMonth,0,4)}年${fn:substring(mineReport.reportMonth,5,7)}月<span id="day"></span>日为止</td>
		</tr>
      </table>
	  </td>
    </tr>
    <tr>
       <td>
      <table width="100%" cellpadding="0" cellspacing="0" class="table_list">
        <tr height="20">
          <td rowspan="4" align="center" width="20%">行业和领域</td>
          <td height="10" colspan=3 width="15%">开展排查治理事故<br/>隐患的生产经营单位</td>
          <td colspan="3" width="15%"><center>一般事故隐患</center></td>
          <td colspan="8" width="40%"><center> 重大事故隐患</center></td>
          <td rowspan="3" width="10%" nowrap><center>累计落实隐<br/>患治理资金</center></td>
        </tr>
        <tr height="20">
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
        <tr height="20">
          <td nowrap><center>排查重<br/>大事故<br/>隐患</center></td>
          <td nowrap><center>其中：<br/>已整改<br/>销号</center></td>
          <td nowrap><center>整<br/>改<br/>率</center></td>
          <td nowrap><center>列入治<br/>理计划<br/>的重大<br/>事故隐<br/>患</center></td>
          <td nowrap><center>其中：<br/>达到“<br/>五到位<br/>”要求<br/>的</center></td>
          <td nowrap><center>挂牌督<br/>办&nbsp;&nbsp;的<br/>重大事<br/>故隐患</center></td>
          <td nowrap><center>其中：<br/>省级挂<br/>牌督办</center></td>
          <td nowrap><center>其中：<br/>地市级<br/>挂牌督<br/>办</center></td>
        </tr>
        <tr height="20">
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
    </tr><tr>
	  <td width="18%" class="td_left"><p><strong>1.道路运输企业</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[0].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[0].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_0">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[0].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[0].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_0">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[0].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[0].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[0].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[0].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[0].bigTrouble==0||statistics[0].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[0].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[0].bigTroubleGovern/statistics[0].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[0].bigTroubleGovern/statistics[0].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[0].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[0].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[0].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[0].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[0].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[0].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[0].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[0].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[0].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[0].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[0].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[0].planMoneyString,(fn:length((mineReportDetails[0].planMoneyString))-2),fn:length((mineReportDetails[0].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[0].planMoney}"/></c:when><c:otherwise>${mineReportDetails[0].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%"><p><strong>2.公路养护施工企业</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[1].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[1].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_1">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[1].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[1].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_1">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[1].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[1].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[1].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[1].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[1].bigTrouble==0||statistics[1].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[1].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[1].bigTroubleGovern/statistics[1].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[1].bigTroubleGovern/statistics[1].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[1].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[1].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[1].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[1].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[1].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[1].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[1].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[1].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[1].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[1].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[1].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[1].planMoneyString,(fn:length((mineReportDetails[1].planMoneyString))-2),fn:length((mineReportDetails[1].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[1].planMoney}"/></c:when><c:otherwise>${mineReportDetails[1].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%"><p><strong>3.水上运输企业</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[2].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[2].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_2">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[2].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[2].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_2">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[2].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[2].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[2].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[2].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[2].bigTrouble==0||statistics[2].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[2].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[2].bigTroubleGovern/statistics[2].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[2].bigTroubleGovern/statistics[2].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[2].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[2].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[2].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[2].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[2].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[2].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[2].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[2].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[2].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[2].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[2].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[2].planMoneyString,(fn:length((mineReportDetails[2].planMoneyString))-2),fn:length((mineReportDetails[2].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[2].planMoney}"/></c:when><c:otherwise>${mineReportDetails[2].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%"><p><strong>4.铁路运输企业</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[3].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[3].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[3].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[3].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_3">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[3].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[3].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[3].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[3].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_3">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[3].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[3].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[3].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[3].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[3].bigTrouble==0||statistics[3].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[3].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[3].bigTroubleGovern/statistics[3].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[3].bigTroubleGovern/statistics[3].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[3].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[3].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[3].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[3].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[3].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[3].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[3].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[3].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[3].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[3].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[3].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[3].planMoneyString,(fn:length((mineReportDetails[3].planMoneyString))-2),fn:length((mineReportDetails[3].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[3].planMoney}"/></c:when><c:otherwise>${mineReportDetails[3].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
  	  <td width="18%"><p><strong>5.航空公司</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[4].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[4].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[4].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[4].company}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg1_4">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[4].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[4].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[4].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[4].generalDangerGovern}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg_4">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[4].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[4].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[4].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[4].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[4].bigTrouble==0||statistics[4].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[4].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[4].bigTroubleGovern/statistics[4].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[4].bigTroubleGovern/statistics[4].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[4].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[4].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[4].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[4].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[4].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[4].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[4].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[4].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[4].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[4].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[4].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[4].planMoneyString,(fn:length((mineReportDetails[4].planMoneyString))-2),fn:length((mineReportDetails[4].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[4].planMoney}"/></c:when><c:otherwise>${mineReportDetails[4].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%" class="td_left"><p><strong>6.机场和油料企业</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[5].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[5].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[5].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[5].company}</c:otherwise></c:choose></td>
      <td align="center" id="zg1_5">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[5].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[5].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[5].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[5].generalDangerGovern}</c:otherwise></c:choose></td>
      <td align="center" id="zg_5">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[5].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[5].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[5].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[5].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[5].bigTrouble==0||statistics[5].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[5].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[5].bigTroubleGovern/statistics[5].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[5].bigTroubleGovern/statistics[5].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[5].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[5].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[5].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[5].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[5].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[5].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[5].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[5].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[5].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[5].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[5].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[5].planMoneyString,(fn:length((mineReportDetails[5].planMoneyString))-2),fn:length((mineReportDetails[5].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[5].planMoney}"/></c:when><c:otherwise>${mineReportDetails[5].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%"><p><strong>7.渔业企业</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[6].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[6].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].company}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg1_6">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[6].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[6].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].generalDangerGovern}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg_6">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[6].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[6].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[6].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[6].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[6].bigTrouble==0||statistics[6].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[6].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[6].bigTroubleGovern/statistics[6].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[6].bigTroubleGovern/statistics[6].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[6].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[6].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[6].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[6].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[6].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[6].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[6].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[6].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[6].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[6].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[6].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[6].planMoneyString,(fn:length((mineReportDetails[6].planMoneyString))-2),fn:length((mineReportDetails[6].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[6].planMoney}"/></c:when><c:otherwise>${mineReportDetails[6].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%"><p><strong>8.农机行业</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[7].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[7].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].company}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg1_7">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[7].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[7].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].generalDangerGovern}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg_7">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[7].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[7].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[7].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[7].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[7].bigTrouble==0||statistics[7].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[7].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[7].bigTroubleGovern/statistics[7].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[7].bigTroubleGovern/statistics[7].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[7].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[7].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[7].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[7].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[7].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[7].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[7].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[7].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[7].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[7].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[7].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[7].planMoneyString,(fn:length((mineReportDetails[7].planMoneyString))-2),fn:length((mineReportDetails[7].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[7].planMoney}"/></c:when><c:otherwise>${mineReportDetails[7].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%"><p><strong>9.水利工程</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[8].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[8].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[8].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[8].company}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg1_8">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[8].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[8].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[8].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[8].generalDangerGovern}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg_8">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[8].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[8].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[8].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[8].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[8].bigTrouble==0||statistics[8].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[8].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[8].bigTroubleGovern/statistics[8].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[8].bigTroubleGovern/statistics[8].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[8].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[8].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[8].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[8].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[8].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[8].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[8].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[8].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[8].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[8].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[8].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[8].planMoneyString,(fn:length((mineReportDetails[8].planMoneyString))-2),fn:length((mineReportDetails[8].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[8].planMoney}"/></c:when><c:otherwise>${mineReportDetails[8].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%" class="td_left"><p><strong>10.学校</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[10].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[10].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[10].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[10].company}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg1_10">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[10].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[10].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[10].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[10].generalDangerGovern}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg_10">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[10].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[10].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[10].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[10].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[10].bigTrouble==0||statistics[10].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[10].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[10].bigTroubleGovern/statistics[10].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[10].bigTroubleGovern/statistics[10].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[10].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[10].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[10].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[10].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[10].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[10].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[10].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[10].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[10].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[10].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[10].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[10].planMoneyString,(fn:length((mineReportDetails[10].planMoneyString))-2),fn:length((mineReportDetails[10].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[10].planMoney}"/></c:when><c:otherwise>${mineReportDetails[10].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%"><p><strong>11.商场、市场等人员密集场所</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[11].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[11].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[11].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[11].company}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg1_11">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[11].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[11].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[11].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[11].generalDangerGovern}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg_11">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[11].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[11].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[11].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[11].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[11].bigTrouble==0||statistics[11].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[11].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[11].bigTroubleGovern/statistics[11].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[11].bigTroubleGovern/statistics[11].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[11].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[11].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[11].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[11].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[11].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[11].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[11].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[11].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[11].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[11].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[11].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[11].planMoneyString,(fn:length((mineReportDetails[11].planMoneyString))-2),fn:length((mineReportDetails[11].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[11].planMoney}"/></c:when><c:otherwise>${mineReportDetails[11].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%"><p><strong>12.建筑施工企业</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[12].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[12].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].company}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg1_12">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[12].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[12].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].generalDangerGovern}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg_12">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[12].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[12].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[12].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[12].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[12].bigTrouble==0||statistics[12].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[12].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[12].bigTroubleGovern/statistics[12].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[12].bigTroubleGovern/statistics[12].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[12].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[12].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[12].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[12].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[12].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[12].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[12].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[12].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[12].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[12].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[12].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[12].planMoneyString,(fn:length((mineReportDetails[12].planMoneyString))-2),fn:length((mineReportDetails[12].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[12].planMoney}"/></c:when><c:otherwise>${mineReportDetails[12].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%"><p><strong>13.民爆器材生产企业</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[13].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[13].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].company}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg1_13">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[13].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[13].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].generalDangerGovern}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg_13">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[13].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[13].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[13].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[13].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[13].bigTrouble==0||statistics[13].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[13].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[13].bigTroubleGovern/statistics[13].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[13].bigTroubleGovern/statistics[13].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[13].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[13].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[13].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[13].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[13].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[13].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[13].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[13].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[13].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[13].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[13].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[13].planMoneyString,(fn:length((mineReportDetails[13].planMoneyString))-2),fn:length((mineReportDetails[13].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[13].planMoney}"/></c:when><c:otherwise>${mineReportDetails[13].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
  	  <td width="18%"><p><strong>14.电力企业</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[14].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[14].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[14].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[14].company}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg1_14">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[14].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[14].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[14].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[14].generalDangerGovern}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg_14">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[14].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[14].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[14].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[14].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[14].bigTrouble==0||statistics[14].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[14].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[14].bigTroubleGovern/statistics[14].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[14].bigTroubleGovern/statistics[14].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[14].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[14].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[14].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[14].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[14].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[14].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[14].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[14].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[14].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[14].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[14].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[14].planMoneyString,(fn:length((mineReportDetails[14].planMoneyString))-2),fn:length((mineReportDetails[14].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[14].planMoney}"/></c:when><c:otherwise>${mineReportDetails[14].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%" class="td_left"><p><strong>15.机械制造企业</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[15].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[15].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[15].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[15].company}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg1_15">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[15].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[15].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[15].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[15].generalDangerGovern}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg_15">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[15].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[15].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[15].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[15].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[15].bigTrouble==0||statistics[15].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[15].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[15].bigTroubleGovern/statistics[15].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[15].bigTroubleGovern/statistics[15].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[15].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[15].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[15].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[15].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[15].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[15].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[15].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[15].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[15].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[15].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[15].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[15].planMoneyString,(fn:length((mineReportDetails[15].planMoneyString))-2),fn:length((mineReportDetails[15].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[15].planMoney}"/></c:when><c:otherwise>${mineReportDetails[15].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
  	  <td width="18%"><p><strong>16.地铁施工（按项目部统计）</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[9].shouldTroubleshooting==0||mineReportDetails[9].shouldTroubleshooting==null}">&nbsp;</c:when><c:otherwise>${mineReportDetails[9].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[9].company==0||mineReportDetails[9].company==null}">&nbsp;</c:when><c:otherwise>${mineReportDetails[9].company}</c:otherwise></c:choose></td>
      <td align="center" id="zg1_9">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[9].generalDanger==0||mineReportDetails[9].generalDanger==null}">&nbsp;</c:when><c:otherwise>${mineReportDetails[9].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[9].generalDangerGovern==0||mineReportDetails[9].generalDangerGovern==null}">&nbsp;</c:when><c:otherwise>${mineReportDetails[9].generalDangerGovern}</c:otherwise></c:choose></td>
      <td align="center" id="zg_9">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[9].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[9].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[9].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[9].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[9].bigTrouble==0||statistics[9].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[9].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[9].bigTroubleGovern/statistics[9].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[9].bigTroubleGovern/statistics[9].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[9].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[9].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[9].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[9].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[9].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[9].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[9].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[9].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[9].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[9].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[9].planMoney==0.0||mineReportDetails[9].planMoney==null}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[9].planMoneyString,(fn:length((mineReportDetails[9].planMoneyString))-2),fn:length((mineReportDetails[9].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[9].planMoney}"/></c:when><c:otherwise>${mineReportDetails[9].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%"><p><strong>17.道路交通事故多发点段</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[17].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[17].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[17].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[17].company}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg1_17">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[17].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[17].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[17].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[17].generalDangerGovern}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg_17">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[17].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[17].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[17].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[17].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[17].bigTrouble==0||statistics[17].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[17].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[17].bigTroubleGovern/statistics[17].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[17].bigTroubleGovern/statistics[17].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[17].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[17].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[17].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[17].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[17].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[17].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[17].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[17].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[17].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[17].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[17].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[17].planMoneyString,(fn:length((mineReportDetails[17].planMoneyString))-2),fn:length((mineReportDetails[17].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[17].planMoney}"/></c:when><c:otherwise>${mineReportDetails[17].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%"><p><strong>18.道路交通安全设施</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[18].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[18].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[18].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[18].company}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg1_18">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[18].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[18].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[18].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[18].generalDangerGovern}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg_18">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[18].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[18].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[18].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[18].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[18].bigTrouble==0||statistics[18].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[18].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[18].bigTroubleGovern/statistics[18].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[18].bigTroubleGovern/statistics[18].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[18].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[18].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[18].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[18].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[18].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[18].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[18].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[18].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[18].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[18].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[18].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[18].planMoneyString,(fn:length((mineReportDetails[18].planMoneyString))-2),fn:length((mineReportDetails[18].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[18].planMoney}"/></c:when><c:otherwise>${mineReportDetails[18].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
  	  <td width="18%"><p><strong>19.临水临崖危险路段</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[19].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[19].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[19].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[19].company}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg1_19">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[19].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[19].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[19].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[19].generalDangerGovern}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg_19">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[19].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[19].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[19].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[19].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[19].bigTrouble==0||statistics[19].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[19].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[19].bigTroubleGovern/statistics[19].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[19].bigTroubleGovern/statistics[19].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[19].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[19].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[19].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[19].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[19].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[19].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[19].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[19].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[19].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[19].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[19].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[19].planMoneyString,(fn:length((mineReportDetails[19].planMoneyString))-2),fn:length((mineReportDetails[19].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[19].planMoney}"/></c:when><c:otherwise>${mineReportDetails[19].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%" class="td_left"><p><strong>20.城市公共交通</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[20].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[20].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[20].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[20].company}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg1_20">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[20].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[20].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[20].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[20].generalDangerGovern}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg_20">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[20].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[20].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[20].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[20].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[20].bigTrouble==0||statistics[20].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[20].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[20].bigTroubleGovern/statistics[20].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[20].bigTroubleGovern/statistics[20].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[20].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[20].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[20].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[20].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[20].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[20].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[20].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[20].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[20].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[20].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[20].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[20].planMoneyString,(fn:length((mineReportDetails[20].planMoneyString))-2),fn:length((mineReportDetails[20].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[20].planMoney}"/></c:when><c:otherwise>${mineReportDetails[20].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%"><p><strong>21.燃气</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[21].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[21].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[21].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[21].company}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg1_21">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[21].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[21].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[21].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[21].generalDangerGovern}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg_21">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[21].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[21].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[21].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[21].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[21].bigTrouble==0||statistics[21].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[21].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[21].bigTroubleGovern/statistics[21].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[21].bigTroubleGovern/statistics[21].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[21].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[21].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[21].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[21].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[21].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[21].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[21].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[21].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[21].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[21].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[21].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[21].planMoneyString,(fn:length((mineReportDetails[21].planMoneyString))-2),fn:length((mineReportDetails[21].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[21].planMoney}"/></c:when><c:otherwise>${mineReportDetails[21].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%"><p><strong>22.旅游行业</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[22].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[22].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[22].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[22].company}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg1_22">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[22].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[22].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[22].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[22].generalDangerGovern}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg_22">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[22].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[22].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[22].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[22].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[22].bigTrouble==0||statistics[22].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[22].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[22].bigTroubleGovern/statistics[22].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[22].bigTroubleGovern/statistics[22].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[22].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[22].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[22].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[22].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[22].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[22].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[22].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[22].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[22].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[22].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[22].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[22].planMoneyString,(fn:length((mineReportDetails[22].planMoneyString))-2),fn:length((mineReportDetails[22].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[22].planMoney}"/></c:when><c:otherwise>${mineReportDetails[22].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%"><p><strong>23.铁路</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[23].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[23].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[23].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[23].company}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg1_23">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[23].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[23].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[23].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[23].generalDangerGovern}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg_23">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[23].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[23].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[23].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[23].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[23].bigTrouble==0||statistics[23].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[23].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[23].bigTroubleGovern/statistics[23].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[23].bigTroubleGovern/statistics[23].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[23].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[23].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[23].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[23].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[23].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[23].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[23].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[23].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[23].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[23].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[23].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[23].planMoneyString,(fn:length((mineReportDetails[23].planMoneyString))-2),fn:length((mineReportDetails[23].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[23].planMoney}"/></c:when><c:otherwise>${mineReportDetails[23].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
  	  <td width="18%"><p><strong>24.医院</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[24].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[24].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[24].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[24].company}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg1_24">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[24].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[24].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[24].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[24].generalDangerGovern}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg_24">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[24].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[24].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[24].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[24].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[24].bigTrouble==0||statistics[24].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[24].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[24].bigTroubleGovern/statistics[24].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[24].bigTroubleGovern/statistics[24].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[24].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[24].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[24].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[24].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[24].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[24].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[24].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[24].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[24].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[24].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[24].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[24].planMoneyString,(fn:length((mineReportDetails[24].planMoneyString))-2),fn:length((mineReportDetails[24].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[24].planMoney}"/></c:when><c:otherwise>${mineReportDetails[24].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%" class="td_left"><p><strong>25.“三合一”场所</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[25].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[25].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[25].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[25].company}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg1_25">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[25].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[25].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[25].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[25].generalDangerGovern}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg_25">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[25].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[25].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[25].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[25].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[25].bigTrouble==0||statistics[25].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[25].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[25].bigTroubleGovern/statistics[25].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[25].bigTroubleGovern/statistics[25].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[25].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[25].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[25].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[25].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[25].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[25].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[25].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[25].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[25].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[25].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[25].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[25].planMoneyString,(fn:length((mineReportDetails[25].planMoneyString))-2),fn:length((mineReportDetails[25].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[25].planMoney}"/></c:when><c:otherwise>${mineReportDetails[25].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%" class="td_left"><p><strong>26.出租房</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[26].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[26].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[26].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[26].company}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg1_26">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[26].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[26].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[26].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[26].generalDangerGovern}</c:otherwise></c:choose></td>
  	  <td align="center" id="zg_26">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[26].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[26].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[26].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[26].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[26].bigTrouble==0||statistics[26].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[26].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[26].bigTroubleGovern/statistics[26].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[26].bigTroubleGovern/statistics[26].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[26].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[26].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[26].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[26].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[26].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[26].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[26].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[26].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[26].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[26].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[26].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[26].planMoneyString,(fn:length((mineReportDetails[26].planMoneyString))-2),fn:length((mineReportDetails[26].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[26].planMoney}"/></c:when><c:otherwise>${mineReportDetails[26].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%"><p><strong>27.其他单位</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[16].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[16].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].company}</c:otherwise></c:choose></td>
 	  <td align="center" id="zg1_16">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[16].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[16].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].generalDangerGovern}</c:otherwise></c:choose></td>
 	  <td align="center" id="zg_16">&nbsp;</td>
	  <td align="center"><c:choose><c:when test="${statistics[16].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[16].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[16].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[16].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[16].bigTrouble==0||statistics[16].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[16].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[16].bigTroubleGovern/statistics[16].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[16].bigTroubleGovern/statistics[16].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[16].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[16].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[16].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[16].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[16].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[16].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[16].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[16].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[16].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[16].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[16].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[16].planMoneyString,(fn:length((mineReportDetails[16].planMoneyString))-2),fn:length((mineReportDetails[16].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[16].planMoney}"/></c:when><c:otherwise>${mineReportDetails[16].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
	  <td width="18%"><p><strong>合计</strong></p></td>
	  <td align="center" id="shouldTroubleshooting_t"></td>
	  <td align="center" id="company_t"></td>
	  <td align="center" id="zg1_t">&nbsp;</td>
	  <td align="center" id="generalDanger_t"></td>
	  <td align="center" id="generalDangerGovern_t"></td>
	  <td align="center" id="zg_t">&nbsp;</td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[27].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[27].bigTrouble}</c:otherwise></c:choose></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[27].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[27].bigTroubleGovern}</c:otherwise></c:choose></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[27].bigTrouble==0||statistics[27].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[27].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[27].bigTroubleGovern/statistics[27].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[27].bigTroubleGovern/statistics[27].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[27].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[27].bigTroubleNotGovern}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[27].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[27].wdw}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[27].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[27].guapaiTotal}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[27].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[27].provinceGuapai}</c:otherwise></c:choose></td>
	  <td align="center"><c:choose><c:when test="${statistics[27].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[27].cityGuapai}</c:otherwise></c:choose></td>
	  <td align="center" id="planMoney_t"></td>
	</tr>
  </table>
       </td>
    </tr><tr>
	 <td colspan="15">
	  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	    <tr style="font-size:12px">
		  <td width="20%"><br/>单位负责人： ${mineReport.chargeMan}</td>
		  <td align="center" width="30%"><br/>填表人：${mineReport.fillMan} </td>
		  <td  align="center" width="30%"><br/>联系电话：${mineReport.tel}</td>
		  <td width="40%" align="center"><br/>填报日期：<s:date name='mineReport.fillDate' format='yyyy-MM-dd' /></td>       
	   </tr>
	  </table>
	 </td>
	</tr>
</table>
   </div>    
<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
	<tr>
	 <td width="100%" style="font-size:12px;color:red;">注：如已按提示设置IE且仍未能打印者，请点击菜单“下载打印控件”！</td>
	</tr>
   <tr>
	 <td align="center">
	 	 <input type="button" name="back" class="but_2" onClick="doPrint('printPreview');" value="打印预览" />&nbsp;&nbsp;
		 <input type="button" name="back" class="but_2" onClick="if(window.confirm('确定打印吗？'))doPrint('print');" value="打  印" />&nbsp;&nbsp;
		 <input name="back" type="button" class="but_2" value="返　回"  onClick="javaScript:history.back();"/>&nbsp;&nbsp;
		 </td>
	</tr>
</table>

</body>
<script type="text/javascript">
var especial = "9";
		//计算整改率
		for (var i=0;i<getAll('generalDanger').length;i++) {
			if(i<9) {
				getDivisor(getAll('generalDangerGovern')[i],getAll('generalDanger')[i],'zg_'+i,true);
				getDivisor(getAll('company')[i],getAll('shouldTroubleshooting')[i],'zg1_'+i,true);
			}else{
				if(i==9){
					getDivisor(getAll('generalDangerGovern')[15],getAll('generalDanger')[15],'zg_9',true);
					getDivisor(getAll('company')[15],getAll('shouldTroubleshooting')[15],'zg1_9',true);
				}else if(i==26){
					getDivisor(getAll('generalDangerGovern')[26],getAll('generalDanger')[26],'zg_16',true);
					getDivisor(getAll('company')[26],getAll('shouldTroubleshooting')[26],'zg1_16',true);
				}
				if(i<26){
					getDivisor(getAll('generalDangerGovern')[i],getAll('generalDanger')[i],'zg_'+(i+1),true);
					getDivisor(getAll('company')[i],getAll('shouldTroubleshooting')[i],'zg1_'+(i+1),true);
				}
			}
		}	
	
//合计
sumSons(get('shouldTroubleshooting_t'),getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);
sumSons(get('company_t'),getAll('company'),get('company_t'),true);
sumSons(get('generalDanger_t'),getAll('generalDanger'),get('generalDanger_t'),true);
sumSons(get('generalDangerGovern_t'),getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);
sumSons(get('planMoney_t'),getAll('planMoney'),get('planMoney_t'),false);	

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
printParam(5,0,0,0,2);
</script>
</html>