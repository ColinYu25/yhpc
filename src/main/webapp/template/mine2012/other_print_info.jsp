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
	<link href="${resourcePath}/css/css1.css" rel="stylesheet" type="text/css" />
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
        <tr style="font-size:18pt">
		     <th colspan="2" height="70" class="head"><br/><br/>交通运输等重点行业领域企业和单位安全生产事故隐患排查治理情况月报表<br/><br/></th>
        </tr>
		<tr style="font-size:14px">
		  <td height="18" width="50%">填报单位:<c:choose><c:when test="${!empty(mineReport.userId)}">${mineReport.userId.fkUserInfo.userCompany}</c:when>
		  <c:otherwise>${userDetail.userCompany}</c:otherwise></c:choose>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		  <td height="18" width="50%" align="left" nowrap="nowrap" valign="bottom">	
		${fn:substring(mineReport.reportMonth,0,4)}年1月至${fn:substring(mineReport.reportMonth,5,7)}月
		</td>
		</tr>
      </table>
	  </td>
    </tr>
    <tr>
      <td><table width="100%" cellpadding="0" cellspacing="0" class="table_list">
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
        </tr>
	<tbody id="tf">
    <tr id="tr_heji"><td style="text-align: center"><strong>合&nbsp;&nbsp;&nbsp;&nbsp;计</strong></td>
	  <td align="center">&nbsp;</td>
	  <td align="center">&nbsp;</td>
	  <td align="center" id="zg1_t">&nbsp;</td>
	  <td align="center">&nbsp;</td>
	  <td align="center">&nbsp;</td>
	  <td align="center" id="zg_t">&nbsp;</td>
	  <td align="center">&nbsp;</td>
	  <td align="center">&nbsp;</td>
	  <td align="center" id="zg2_t">&nbsp;</td>
	  <td align="center">&nbsp;</td>
	  <td align="center">&nbsp;</td>
	  <td align="center">&nbsp;</td>
	  <td align="center">&nbsp;</td>
	  <td align="center">&nbsp;</td>
	  <td align="center">&nbsp;</td>
	</tr>
	<c:forEach var="item" items="${otherType}" varStatus="status">
		<tr id="tr_${item.key}">
		  <td width="18%" class="td_left" id="a" nowrap><p>${item.value}</p></td>
		  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[status.count-1].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[status.count-1].shouldTroubleshooting}</c:otherwise></c:choose></td>
		  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[status.count-1].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[status.count-1].company}</c:otherwise></c:choose></td>
		  <td align="center" id="zg1_${status.count-1}"><c:choose><c:when test="${mineReportDetails[status.count-1].shouldTroubleshooting==0||mineReportDetails[status.count-1].company==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${mineReportDetails[status.count-1].companyIsDividend}"><fmt:formatNumber pattern="0" value="${mineReportDetails[status.count-1].company/mineReportDetails[status.count-1].shouldTroubleshooting*100}" /></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${mineReportDetails[status.count-1].company/mineReportDetails[status.count-1].shouldTroubleshooting*100}" /></c:otherwise></c:choose></c:otherwise></c:choose></td>
		  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[status.count-1].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[status.count-1].generalDanger}</c:otherwise></c:choose></td>
		  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[status.count-1].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[status.count-1].generalDangerGovern}</c:otherwise></c:choose></td>
		  <td align="center" id="zg_${status.count-1}"><c:choose><c:when test="${mineReportDetails[status.count-1].generalDanger==0||mineReportDetails[status.count-1].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${mineReportDetails[status.count-1].generalDangerIsDividend}"><fmt:formatNumber pattern="0" value="${mineReportDetails[status.count-1].generalDangerGovern/mineReportDetails[status.count-1].generalDanger*100}" /></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${mineReportDetails[status.count-1].generalDangerGovern/mineReportDetails[status.count-1].generalDanger*100}" /></c:otherwise></c:choose></c:otherwise></c:choose></td>
		  <td align="center" id="bt"><c:choose><c:when test="${statistics[status.count-1].bigTrouble==0}">&nbsp;</c:when><c:otherwise>${statistics[status.count-1].bigTrouble}</c:otherwise></c:choose></td>
		  <td align="center" id="btg"><c:choose><c:when test="${statistics[status.count-1].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[status.count-1].bigTroubleGovern}</c:otherwise></c:choose></td>
		  <td align="center">
		  <c:choose><c:when test="${statistics[status.count-1].bigTrouble==0||statistics[status.count-1].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[status.count-1].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[status.count-1].bigTroubleGovern/statistics[status.count-1].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[status.count-1].bigTroubleGovern/statistics[status.count-1].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		  <td align="center" id="btng"><c:choose><c:when test="${statistics[status.count-1].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise>${statistics[status.count-1].bigTroubleNotGovern}</c:otherwise></c:choose></td>
		  <td align="center" id="wdw"><c:choose><c:when test="${statistics[status.count-1].wdw==0}">&nbsp;</c:when><c:otherwise>${statistics[status.count-1].wdw}</c:otherwise></c:choose></td>
		  <td align="center" id="gpt"><c:choose><c:when test="${statistics[status.count-1].guapaiTotal==0}">&nbsp;</c:when><c:otherwise>${statistics[status.count-1].guapaiTotal}</c:otherwise></c:choose></td>
		  <td align="center" id="pgp"><c:choose><c:when test="${statistics[status.count-1].provinceGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[status.count-1].provinceGuapai}</c:otherwise></c:choose></td>
		  <td align="center" id="cgp"><c:choose><c:when test="${statistics[status.count-1].cityGuapai==0}">&nbsp;</c:when><c:otherwise>${statistics[status.count-1].cityGuapai}</c:otherwise></c:choose></td>
		  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[status.count-1].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[status.count-1].planMoneyString,(fn:length((mineReportDetails[status.count-1].planMoneyString))-2),fn:length((mineReportDetails[status.count-1].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[status.count-1].planMoney}"/></c:when><c:otherwise>${mineReportDetails[status.count-1].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
		</tr>
	</c:forEach>
	</tbody>
	</table>
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	   <tr style="font-size:12px">
		  <td width="20%"><br/>审核人： ${mineReport.chargeMan}</td>
		  <td align="center" width="30%"><br/>填表人：${mineReport.fillMan}</td>
		  <td  align="center" width="30%"><br/>联系电话：${mineReport.tel}</td>
		  <td width="40%"><br/>填报日期:<s:date name='mineReport.fillDate' format='yyyy-MM-dd' /></td>       
	   </tr>
	   <tr style="font-size:12px">
		  <td colspan=4 style="padding-top:5px;line-height:1.5">有关要求：1.各地区要认真填写本表，并于每月6日前以网上填报、传真（或电子邮件）等方式报送至国务院安委会办公室。
		  <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.统计数据位${fn:substring(mineReport.reportMonth,0,4)}年1月以来的累计数据。</br></td>       
	   </tr>
	</table></td></tr></table>
</div>
 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
 <tr>
	 <td width="100%" style="font-size:12px;color:red;">注：如已按提示设置IE且仍未能打印者，请点击菜单“下载打印控件”！</td>
	</tr>
   <tr>		 
      <td align="center"><input name="yulan" type="button" class="but_2" value="打印预览"  onclick="javascript:doPrint('printPreview');"/>&nbsp;&nbsp;
        <input name="print" type="button" class="but_2" value="打   印"  onclick="javascript:if(confirm('   确定要打印吗?')){doPrint('print');}"/>&nbsp;&nbsp;
        <input type="button" class="but_2" value="返  回"  onclick="history.back(-1);"/>
        <input type="button" class="but_2" value="旧版打印入口"  onclick="loadOldTable();"/>
	    <input type="hidden" id="type" name="mineReport.type" value="1"/>
	    <input type="hidden" id="isReport" name="mineReport.report" value="true"/>
	    <input type="hidden" id="id" name="mineReport.id" value="${mineReport.id}" disabled/>
	 </td>
  </tr>
</table>
</body>
<script language="javascript" type="text/javascript" src="${resourcePath}/js/jquery-1.3.2.js"></script>
<script type="text/javascript">
function getAction() {
	var addr = window.location.href;
	var pos = addr.indexOf(".xhtml");
	if (pos < 0) {
		return "";
	}
	var pos2 = addr.lastIndexOf("/");
	return addr.substring(pos2 + 1, pos);
}
function loadOldTable() {
	var action = getAction();
	if (action.indexOf("loadOther") != -1) {
		window.location='printOther.xhtml?mineReport.id=${mineReport.id}'
	} else if (action.indexOf("loadCountry") > -1) {
		var addr = window.location.href;
		window.location=addr.replaceAll("2012","").replaceAll("loadCountry","loadProvinceReport");
	} else {
		var addr = window.location.href;
		window.location=addr.replaceAll("2012","");
	}
}
var j = jQuery.noConflict();
for(var i = 1; i < 16; i++){
	if(i!=3&&i!=6&&i!=9){//排除比率列
		var spanTo = 0;
		var rows = j("#tf>tr");
		for (var z = 1; z < rows.length; z++){
			var val = j("#" + rows[z].id + " td:eq(" +(i)+")").html();
			if (rows[z].children[i]!=undefined
			     	&&rows[z].children[i].children!=undefined
					&&rows[z].children[i].children.length!=undefined
					&&rows[z].children[i].children.length >0
					&&rows[z].children[i].children[0]!=undefined) {//如果存在子项（链接）
				val = rows[z].children[i].children[0].innerHTML.replaceAll("&nbsp;","");
			} else {
				val = val.replaceAll("&nbsp;","");
			}
			spanTo += parseFloat(val?val:0);
		}
		if (!isNaN(spanTo)){
			j("#tf tr:eq(0) td:eq("+(i)+")").html(spanTo==0?"&nbsp;":spanTo);
		}
	}
}
//计算合计的百分率
var index = 1;
var val = "&nbsp;";
if (j("#tr_heji td:eq("+(index)+")").html()!="0"&&j("#tr_heji td:eq("+(index)+")").html()!="&nbsp;"
	&&j("#tr_heji td:eq("+(index+1)+")").html()!="0"&&j("#tr_heji td:eq("+(index+1)+")").html()!="&nbsp;") {
	val = parseFloat(j("#tr_heji td:eq("+(index+1)+")").html()/j("#tr_heji td:eq("+(index)+")").html()*100).Fixed(1);
}
j("#tr_heji td:eq("+(index+2)+")").html(val);
index = index + 3;
val = "&nbsp;";
if (j("#tr_heji td:eq("+(index)+")").html()!="0"&&j("#tr_heji td:eq("+(index)+")").html()!="&nbsp;"
	&&j("#tr_heji td:eq("+(index+1)+")").html()!="0"&&j("#tr_heji td:eq("+(index+1)+")").html()!="&nbsp;") {
	val = parseFloat(j("#tr_heji td:eq("+(index+1)+")").html()/j("#tr_heji td:eq("+(index)+")").html()*100).Fixed(1);
}
j("#tr_heji td:eq("+(index+2)+")").html(val);
index = index + 3;
val = "&nbsp;";
if (j("#tr_heji td:eq("+(index)+")").html()!="0"&&j("#tr_heji td:eq("+(index)+")").html()!="&nbsp;"
	&&j("#tr_heji td:eq("+(index+1)+")").html()!="0"&&j("#tr_heji td:eq("+(index+1)+")").html()!="&nbsp;") {
	val = parseFloat(j("#tr_heji td:eq("+(index+1)+")").html()/j("#tr_heji td:eq("+(index)+")").html()*100).Fixed(1);
}
j("#tr_heji td:eq("+(index+2)+")").html(val);

printParam(5,0,0,0,2);
</script>
</html>