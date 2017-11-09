<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/header.jsp" %>
<script type="text/javascript">
function submitCreate(){
 var obj=get("mineForm");
if (get("year").value=="0" || get("year").value=="") {
			alert("请选择年份！");
			get("year").focus();
			return false;
		} else if(get("month").value=="0" || get("month").value=="") {
			alert("请选择月份！");
			get("month").focus();
			return false;
		}
		if(!chkNull(obj.fillDate,'请填写填报日期')){
		  	 return false;
		  }
		  if(!confirm("您选择的日期是"+get("year").value+"年"+get("month").value+"月,确定保存吗?")){
			 return false;
		  }
	  <c:choose>
		  <c:when test="${!empty(mineReport)&&mineReport.id!=-1}">
			obj.action="updateMine.xhtml";
			get("id").disabled = false;
		  </c:when>
		  <c:otherwise>
	 		 obj.action="createMine.xhtml";
		  </c:otherwise>
	  </c:choose>
	  obj.submit();
	  get("sub").disabled = true;
}
function openWindow(tradeType,troubleType) {
	var reportMonth = get("year").value+"-"+get("month").value;
	if (get("year").value=="0" || get("year").value=="") {
		if ("${mineReport.reportMonth}"!="") {
			reportMonth = "${mineReport.reportMonth}";
		} else if("${reportMonth}"!="") {
			reportMonth = "${reportMonth}";
		}else {
			alert("请选择年份！");
			get("year").focus();
			return false;
		}
	} else if(get("month").value=="0" || get("month").value=="") {
		if ("${mineReport.reportMonth}"!="") {
			reportMonth = "${mineReport.reportMonth}";
		} else if("${reportMonth}"!="") {
			reportMonth = "${reportMonth}";
		}else {
			alert("请选择月份！");
			get("month").focus();
			return false;
		}
	}
	get("troubleType").value=troubleType;
	get("tradeType").value=tradeType;
	get("reportMonth").value=reportMonth;
	get("bigTroubleForm").submit();
}
var especial = "5";
</script>
<body>
<form id="bigTroubleForm" name="bigTroubleForm" method="post" action="../bigTrouble/loadBigTroubles.xhtml">
<input type="hidden" name="mineId" id="mineId" value="${mineReport.id}"/>
<input type="hidden" name="reportState" id="reportState" value="123"/>
<input type="hidden" name="tableType" id="tableType" value="1"/>
<input type="hidden" name="troubleType" id="troubleType"/>
<input type="hidden" name="tradeType" id="tradeType"/>
<input type="hidden" name="reportMonth" id="reportMonth"/>
<input type="hidden" name="tableName" value="mine"/>
<input type="hidden" name="mineType" value="${mineReport.type}"/>
</form>
<form name="mineForm" id="mineForm" method="post">
  <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" id="tb">
    <tr>
      <td>
	  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
		     <th colspan="2" height="50" class="head">煤矿、金属非金属矿山等工矿企业安全生产隐患排查治理情况统计表</th>
        </tr>
		<tr style="font-size:12px">
		  <td height="18" width="80%">填报单位（章）:${fillUnit}</span></td>
		  <td height="18" width="20%" align="right" nowrap="nowrap" valign="bottom">
		  截止到<select id="year" style="width:55px;" name="mineReport.year">
		  	<c:forEach var="year" items="${years}" varStatus="status"><option value="${year}">${year}</option></c:forEach>
		  </select>年<select id="month" name="mineReport.month" style="width:45px;" onChange="window.location='loadProvinceReport.xhtml?mineReport.type=${mineReport.type}&mineReport.reportMonth='+get('year').value+'-'+this.value;">
		  	<c:forEach var="month" items="${months}" varStatus="status"><c:choose><c:when test="${month.choose||fn:split(mineReport.reportMonth,'-')[1]==month.month}"><option value="${month.month}">${month.month}</option></c:when><c:otherwise><optgroup label="${month.month}"></optgroup></c:otherwise></c:choose></c:forEach>
		  </select>月<span id="day"></span>日为止&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
  <td nowrap width="18%"><p><strong>1.煤矿企业</strong></p></td>
  <td align="center" nowrap id="company">&nbsp;</td>
	  <td align="center" nowrap id="generalDanger">&nbsp;</td>
	  <td align="center" nowrap id="generalDangerGovern">&nbsp;</td>
	  <td align="center" nowrap id="zg_0">&nbsp;</td>
	  <td align="center" nowrap id="bt">&nbsp;</td>
	  <td align="center" nowrap id="btg">&nbsp;</td>
	  <td align="center" nowrap>&nbsp;</td>
	  <td align="center" nowrap id="btng">&nbsp;</td>
	  <td align="center" nowrap id="wdw">&nbsp;</td>
	  <td align="center" nowrap id="gpt">&nbsp;</td>
	  <td align="center" nowrap id="pgp">&nbsp;</td>
	  <td align="center" nowrap id="planMoney">&nbsp;</td>
	  </tr>
        
        
		<tr>
	  <td nowrap width="18%" class="td_left"><p><strong>2.金属非金属矿山企业</strong></p></td>
	  <td align="center" nowrap id="company"><c:choose><c:when test="${mineReportDetails[0].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap id="generalDanger"><c:choose><c:when test="${mineReportDetails[0].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[0].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap id="zg_1">&nbsp;</td>
	  <td align="center" nowrap id="bt"><c:choose><c:when test="${statistics[0].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(1,1);">${statistics[0].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="btg"><c:choose><c:when test="${statistics[0].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(1,2);">${statistics[0].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap>
	  <c:choose><c:when test="${statistics[0].bigTrouble==0||statistics[0].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[0].bigTroubleIsDividend}"><fmt:formatNumber pattern="0.0" value="${(statistics[0].bigTroubleGovern/statistics[0].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[0].bigTroubleGovern/statistics[0].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="btng"><c:choose><c:when test="${statistics[0].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(1,3);">${statistics[0].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="wdw"><c:choose><c:when test="${statistics[0].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(1,4);">${statistics[0].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="gpt"><c:choose><c:when test="${statistics[0].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(1,5);">${statistics[0].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="pgp"><c:choose><c:when test="${statistics[0].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(1,6);">${statistics[0].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="planMoney"><c:choose><c:when test="${mineReportDetails[0].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[0].planMoneyString,(fn:length((mineReportDetails[0].planMoneyString))-2),fn:length((mineReportDetails[0].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0.0" value="${mineReportDetails[0].planMoney}"/></c:when><c:otherwise>${mineReportDetails[0].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap width="18%"><p><strong>3.尾矿库</strong></p></td>
	<td align="center" nowrap id="company"><c:choose><c:when test="${mineReportDetails[1].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap id="generalDanger"><c:choose><c:when test="${mineReportDetails[1].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[1].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap id="zg_2">&nbsp;</td>
	  <td align="center" nowrap id="bt"><c:choose><c:when test="${statistics[1].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(2,1);">${statistics[1].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="btg"><c:choose><c:when test="${statistics[1].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(2,2);">${statistics[1].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap>
	  <c:choose><c:when test="${statistics[1].bigTrouble==0||statistics[1].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[1].bigTroubleIsDividend}"><fmt:formatNumber pattern="0.0" value="${(statistics[1].bigTroubleGovern/statistics[1].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[1].bigTroubleGovern/statistics[1].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="btng"><c:choose><c:when test="${statistics[1].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(2,3);">${statistics[1].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="wdw"><c:choose><c:when test="${statistics[1].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(2,4);">${statistics[1].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="gpt"><c:choose><c:when test="${statistics[1].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(2,5);">${statistics[1].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="pgp"><c:choose><c:when test="${statistics[1].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(2,6);">${statistics[1].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="planMoney"><c:choose><c:when test="${mineReportDetails[1].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[1].planMoneyString,(fn:length((mineReportDetails[1].planMoneyString))-2),fn:length((mineReportDetails[1].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0.0" value="${mineReportDetails[1].planMoney}"/></c:when><c:otherwise>${mineReportDetails[1].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>
	  
	  
	            <tr>
  <td nowrap width="18%"><p><strong>4.石油天然气开采企业</strong></p></td>
  <td align="center" nowrap id="company">&nbsp;</td>
	  <td align="center" nowrap id="generalDanger">&nbsp;</td>
	  <td align="center" nowrap id="generalDangerGovern">&nbsp;</td>
	  <td align="center" nowrap id="zg_3">&nbsp;</td>
	  <td align="center" nowrap id="bt">&nbsp;</td>
	  <td align="center" nowrap id="btg">&nbsp;</td>
	  <td align="center" nowrap>&nbsp;</td>
	  <td align="center" nowrap id="btng">&nbsp;</td>
	  <td align="center" nowrap id="wdw">&nbsp;</td>
	  <td align="center" nowrap id="gpt">&nbsp;</td>
	  <td align="center" nowrap id="pgp">&nbsp;</td>
	  <td align="center" nowrap id="planMoney">&nbsp;</td>
	  </tr>
	 
	  <tr>
  <td nowrap width="18%"><p><strong>5.危险化学品企业</strong></p></td>
  	  <td align="center" nowrap width="4%" id="company_6"></td>
  	  <td align="center" nowrap width="4%" id="generalDanger_6"></td>
	  <td align="center" nowrap id="generalDangerGovern_6"></td>
	  <td align="center" nowrap id="zg_n_6">&nbsp;</td>
	  <td align="center" nowrap id="bt_6">0</td>
	  <td align="center" nowrap id="btg_6">0</td>
	  <td align="center" nowrap id="bt_r_6">&nbsp;</td>
	  <td align="center" nowrap id="btng_6">0</td>
	  <td align="center" nowrap id="wdw_6">0</td>
	  <td align="center" nowrap id="gpt_6">0</td>
	  <td align="center" nowrap id="pgp_6">0</td>
	  <td align="center" nowrap id="planMoney_6">0</td></tr>
	<td nowrap width="18%"><p>（1）危险化学品生产、储存企业和其他化工企业</p></td>
	<td align="center" nowrap id="company"><c:choose><c:when test="${mineReportDetails[6].company+mineReportDetails[8].company+mineReportDetails[9].company+mineReportDetails[10].company+mineReportDetails[11].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].company+mineReportDetails[8].company+mineReportDetails[9].company+mineReportDetails[10].company+mineReportDetails[11].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap id="generalDanger"><c:choose><c:when test="${mineReportDetails[6].generalDanger+mineReportDetails[8].generalDanger+mineReportDetails[9].generalDanger+mineReportDetails[10].generalDanger+mineReportDetails[11].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].generalDanger+mineReportDetails[8].generalDanger+mineReportDetails[9].generalDanger+mineReportDetails[10].generalDanger+mineReportDetails[11].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[6].generalDangerGovern+mineReportDetails[8].generalDangerGovern+mineReportDetails[9].generalDangerGovern+mineReportDetails[10].generalDangerGovern+mineReportDetails[11].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].generalDangerGovern+mineReportDetails[8].generalDangerGovern+mineReportDetails[9].generalDangerGovern+mineReportDetails[10].generalDangerGovern+mineReportDetails[11].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap id="zg_4">&nbsp;</td>
	  <td align="center" nowrap id="bt"><c:choose><c:when test="${statistics[6].bigTrouble+statistics[8].bigTrouble+statistics[9].bigTrouble+statistics[10].bigTrouble+statistics[11].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(49,1);">${statistics[6].bigTrouble+statistics[8].bigTrouble+statistics[9].bigTrouble+statistics[10].bigTrouble+statistics[11].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="btg"><c:choose><c:when test="${statistics[6].bigTroubleGovern+statistics[8].bigTroubleGovern+statistics[9].bigTroubleGovern+statistics[10].bigTroubleGovern+statistics[11].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(49,2);">${statistics[6].bigTroubleGovern+statistics[8].bigTroubleGovern+statistics[9].bigTroubleGovern+statistics[10].bigTroubleGovern+statistics[11].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="bt_r_4">
	  <c:choose><c:when test="${statistics[6].bigTrouble+statistics[8].bigTrouble+statistics[9].bigTrouble+statistics[10].bigTrouble+statistics[11].bigTrouble==0||statistics[6].bigTroubleGovern+statistics[8].bigTroubleGovern+statistics[9].bigTroubleGovern+statistics[10].bigTroubleGovern+statistics[11].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${((statistics[6].bigTroubleGovern+statistics[8].bigTroubleGovern+statistics[9].bigTroubleGovern+statistics[10].bigTroubleGovern+statistics[11].bigTroubleGovern)/(statistics[6].bigTrouble+statistics[8].bigTrouble+statistics[9].bigTrouble+statistics[10].bigTrouble+statistics[11].bigTrouble))*100}"/></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="btng"><c:choose><c:when test="${statistics[6].bigTroubleNotGovern+statistics[8].bigTroubleNotGovern+statistics[9].bigTroubleNotGovern+statistics[10].bigTroubleNotGovern+statistics[11].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(49,3);">${statistics[6].bigTroubleNotGovern+statistics[8].bigTroubleNotGovern+statistics[9].bigTroubleNotGovern+statistics[10].bigTroubleNotGovern+statistics[11].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center"id="wdw" nowrap ><c:choose><c:when test="${statistics[6].wdw+statistics[8].wdw+statistics[9].wdw+statistics[10].wdw+statistics[11].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(49,4);">${statistics[6].wdw+statistics[8].wdw+statistics[9].wdw+statistics[10].wdw+statistics[11].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="gpt"><c:choose><c:when test="${statistics[6].guapaiTotal+statistics[8].guapaiTotal+statistics[9].guapaiTotal+statistics[10].guapaiTotal+statistics[11].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(49,5);">${statistics[6].guapaiTotal+statistics[8].guapaiTotal+statistics[9].guapaiTotal+statistics[10].guapaiTotal+statistics[11].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="pgp"><c:choose><c:when test="${statistics[6].provinceGuapai+statistics[8].provinceGuapai+statistics[9].provinceGuapai+statistics[10].provinceGuapai+statistics[11].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(49,6);">${statistics[6].provinceGuapai+statistics[8].provinceGuapai+statistics[9].provinceGuapai+statistics[10].provinceGuapai+statistics[11].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="planMoney">
	  	<c:choose>
	  	<c:when test="${mineReportDetails[6].planMoney+mineReportDetails[8].planMoney+mineReportDetails[9].planMoney+mineReportDetails[10].planMoney+mineReportDetails[11].planMoney==0.0}">&nbsp;
	  	</c:when>
	  	<c:otherwise>
	  		<fmt:formatNumber type="number" value="${mineReportDetails[6].planMoney+mineReportDetails[8].planMoney+mineReportDetails[9].planMoney+mineReportDetails[10].planMoney+mineReportDetails[11].planMoney}" maxFractionDigits="1" pattern="0.0"/> 
	  	</c:otherwise>
	  	</c:choose>
	  </td></tr><tr>
  <td nowrap width="18%"><p>（2）危险化学品经营企业和单位</p></td>
  <td align="center" nowrap id="company"><c:choose><c:when test="${mineReportDetails[7].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap id="generalDanger"><c:choose><c:when test="${mineReportDetails[7].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[7].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap id="zg_5">&nbsp;</td>
	  <td align="center" nowrap id="bt"><c:choose><c:when test="${statistics[7].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(8,1);">${statistics[7].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="btg"><c:choose><c:when test="${statistics[7].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(8,2);">${statistics[7].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap>
	  <c:choose><c:when test="${statistics[7].bigTrouble==0||statistics[7].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[7].bigTroubleIsDividend}"><fmt:formatNumber pattern="0.0" value="${(statistics[7].bigTroubleGovern/statistics[7].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[7].bigTroubleGovern/statistics[7].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="btng"><c:choose><c:when test="${statistics[7].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(8,3);">${statistics[7].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="wdw"><c:choose><c:when test="${statistics[7].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(8,4);">${statistics[7].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="gpt"><c:choose><c:when test="${statistics[7].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(8,5);">${statistics[7].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="pgp"><c:choose><c:when test="${statistics[7].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(8,6);">${statistics[7].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="planMoney"><c:choose><c:when test="${mineReportDetails[7].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[7].planMoneyString,(fn:length((mineReportDetails[7].planMoneyString))-2),fn:length((mineReportDetails[7].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0.0" value="${mineReportDetails[7].planMoney}"/></c:when><c:otherwise>${mineReportDetails[7].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>

	
	  <tr>
		  <td nowrap width="18%"><p>（3）危险化学品道路运输企业和单位</p></td>
		  <td align="center" nowrap id="company"><c:choose><c:when test="${mineReportDetails[12].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap id="generalDanger"><c:choose><c:when test="${mineReportDetails[12].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[12].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap id="zg_6">&nbsp;</td>
	  <td align="center" nowrap id="bt"><c:choose><c:when test="${statistics[12].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(13,1);">${statistics[12].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="btg"><c:choose><c:when test="${statistics[12].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(13,2);">${statistics[12].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap>
	  <c:choose><c:when test="${statistics[12].bigTrouble==0||statistics[12].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[12].bigTroubleIsDividend}"><fmt:formatNumber pattern="0.0" value="${(statistics[12].bigTroubleGovern/statistics[12].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[12].bigTroubleGovern/statistics[12].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="btng"><c:choose><c:when test="${statistics[12].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(13,3);">${statistics[12].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="wdw"><c:choose><c:when test="${statistics[12].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(13,4);">${statistics[12].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="gpt"><c:choose><c:when test="${statistics[12].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(13,5);">${statistics[12].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="pgp"><c:choose><c:when test="${statistics[12].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(13,6);">${statistics[12].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="planMoney"><c:choose><c:when test="${mineReportDetails[12].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[12].planMoneyString,(fn:length((mineReportDetails[12].planMoneyString))-2),fn:length((mineReportDetails[12].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0.0" value="${mineReportDetails[12].planMoney}"/></c:when><c:otherwise>${mineReportDetails[12].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>
	  <tr>
		<td nowrap width="18%"><p><strong>6.烟花爆竹企业</strong></p></td>
	<td align="center" nowrap id="company"><c:choose><c:when test="${mineReportDetails[13].company+mineReportDetails[14].company+mineReportDetails[15].company==0||(mineReportDetails[13].company==null&&mineReportDetails[14].company==null&&mineReportDetails[15].company==null)}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].company+mineReportDetails[14].company+mineReportDetails[15].company}</c:otherwise></c:choose></td>
	<td align="center" nowrap id="generalDanger"><c:choose><c:when test="${mineReportDetails[13].generalDanger+mineReportDetails[14].generalDanger+mineReportDetails[15].generalDanger==0||(mineReportDetails[13].generalDanger==null&&mineReportDetails[14].generalDanger==null&&mineReportDetails[15].generalDanger==null)}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].generalDanger+mineReportDetails[14].generalDanger+mineReportDetails[15].generalDanger}</c:otherwise></c:choose></td>
	<td align="center" nowrap id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[13].generalDangerGovern+mineReportDetails[14].generalDangerGovern+mineReportDetails[15].generalDangerGovern==0||(mineReportDetails[13].generalDangerGovern==null&&mineReportDetails[14].generalDangerGovern&&mineReportDetails[15].generalDangerGovern)}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].generalDangerGovern+mineReportDetails[14].generalDangerGovern+mineReportDetails[15].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap id="zg_7">&nbsp;</td>
	  <td align="center" nowrap id="bt"><c:choose><c:when test="${statistics[13].bigTrouble+statistics[14].bigTrouble+statistics[15].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(47,1);">${statistics[13].bigTrouble+statistics[14].bigTrouble+statistics[15].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="btg"><c:choose><c:when test="${statistics[13].bigTroubleGovern+statistics[14].bigTroubleGovern+statistics[15].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(47,2);">${statistics[13].bigTroubleGovern+statistics[14].bigTroubleGovern+statistics[15].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="bt_r_7">
	  <c:choose><c:when test="${statistics[13].bigTrouble+statistics[14].bigTrouble+statistics[15].bigTrouble==0||statistics[13].bigTroubleGovern+statistics[14].bigTroubleGovern+statistics[15].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${((statistics[13].bigTroubleGovern+statistics[14].bigTroubleGovern+statistics[15].bigTroubleGovern)/(statistics[13].bigTrouble+statistics[14].bigTrouble+statistics[15].bigTrouble))*100}"/></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="btng"><c:choose><c:when test="${statistics[13].bigTroubleNotGovern+statistics[14].bigTroubleNotGovern+statistics[15].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(47,3);">${statistics[13].bigTroubleNotGovern+statistics[14].bigTroubleNotGovern+statistics[15].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="wdw"><c:choose><c:when test="${statistics[13].wdw+statistics[14].wdw+statistics[15].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(47,4);">${statistics[13].wdw+statistics[14].wdw+statistics[15].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="gpt"><c:choose><c:when test="${statistics[13].guapaiTotal+statistics[14].guapaiTotal+statistics[15].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(47,5);">${statistics[13].guapaiTotal+statistics[14].guapaiTotal+statistics[15].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="pgp"><c:choose><c:when test="${statistics[13].provinceGuapai+statistics[14].provinceGuapai+statistics[15].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(47,6);">${statistics[13].provinceGuapai+statistics[14].provinceGuapai+statistics[15].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="planMoney"><c:choose><c:when test="${mineReportDetails[13].planMoney+mineReportDetails[14].planMoney+mineReportDetails[15].planMoney==0.0||(mineReportDetails[13].planMoney==null&&mineReportDetails[14].planMoney==null&&mineReportDetails[15].planMoney==null)}">&nbsp;</c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${mineReportDetails[13].planMoney+mineReportDetails[14].planMoney+mineReportDetails[15].planMoney}"/></c:otherwise></c:choose></td></tr>
	  
	
          
           <tr>
	<td nowrap width="18%"><p><strong>7.冶金、有色企业</strong></p></td>
	<td align="center" nowrap id="company"><c:choose><c:when test="${mineReportDetails[2].company+mineReportDetails[3].company==0||(mineReportDetails[2].company==null&&mineReportDetails[3].company==null)}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].company+mineReportDetails[3].company}</c:otherwise></c:choose></td>
	<td align="center" nowrap id="generalDanger"><c:choose><c:when test="${mineReportDetails[2].generalDanger+mineReportDetails[3].generalDanger==0||(mineReportDetails[2].generalDanger==null&&mineReportDetails[3].generalDanger)}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].generalDanger+mineReportDetails[3].generalDanger}</c:otherwise></c:choose></td>
	<td align="center" nowrap id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[2].generalDangerGovern+mineReportDetails[3].generalDangerGovern==0||(mineReportDetails[2].generalDangerGovern==null&&mineReportDetails[3].generalDangerGovern)}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].generalDangerGovern+mineReportDetails[3].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap id="zg_8">&nbsp;</td>
	  <td align="center" nowrap id="bt"><c:choose><c:when test="${statistics[2].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(4,1);">${statistics[2].bigTrouble+statistics[3].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="btg"><c:choose><c:when test="${statistics[2].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(4,2);">${statistics[2].bigTroubleGovern+statistics[3].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="bt_r_8">
	  <c:choose><c:when test="${statistics[2].bigTrouble+statistics[3].bigTrouble==0||statistics[2].bigTroubleGovern+statistics[3].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${((statistics[2].bigTroubleGovern+statistics[3].bigTroubleGovern)/(statistics[2].bigTrouble+statistics[3].bigTrouble))*100}"/></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="btng"><c:choose><c:when test="${statistics[2].bigTroubleNotGovern+statistics[3].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(4,3);">${statistics[2].bigTroubleNotGovern+statistics[3].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="wdw"><c:choose><c:when test="${statistics[2].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(4,4);">${statistics[2].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="gpt"><c:choose><c:when test="${statistics[2].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(4,5);">${statistics[2].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="pgp"><c:choose><c:when test="${statistics[2].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(4,6);">${statistics[2].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="planMoney"><c:choose><c:when test="${mineReportDetails[2].planMoney+mineReportDetails[3].planMoney==0.0||(mineReportDetails[2].planMoney==null&&mineReportDetails[3].planMoney==null)}">&nbsp;</c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${mineReportDetails[2].planMoney+mineReportDetails[3].planMoney}"/></c:otherwise></c:choose></td></tr>
	  
          
          
          
		<tr>
			<td nowrap width="18%"><p><strong>8.其他企业</strong></p></td>
			 <td align="center" nowrap id="company"><c:choose><c:when test="${mineReportDetails[16].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap id="generalDanger"><c:choose><c:when test="${mineReportDetails[16].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[16].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap id="zg_9">&nbsp;</td>
	  <td align="center" nowrap id="bt"><c:choose><c:when test="${statistics[16].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(17,1);">${statistics[16].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="btg"><c:choose><c:when test="${statistics[16].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(17,2);">${statistics[16].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap>
	  <c:choose><c:when test="${statistics[16].bigTrouble==0||statistics[16].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[16].bigTroubleIsDividend}"><fmt:formatNumber pattern="0.0" value="${(statistics[16].bigTroubleGovern/statistics[16].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[16].bigTroubleGovern/statistics[16].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="btng"><c:choose><c:when test="${statistics[16].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(17,3);">${statistics[16].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="wdw"><c:choose><c:when test="${statistics[16].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(17,4);">${statistics[16].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="gpt"><c:choose><c:when test="${statistics[16].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(17,5);">${statistics[16].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="pgp"><c:choose><c:when test="${statistics[16].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(17,6);">${statistics[16].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap id="planMoney"><c:choose><c:when test="${mineReportDetails[16].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[16].planMoneyString,(fn:length((mineReportDetails[16].planMoneyString))-2),fn:length((mineReportDetails[16].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0.0" value="${mineReportDetails[16].planMoney}"/></c:when><c:otherwise>${mineReportDetails[16].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>
	
		<tr><td nowrap width="18%"><p><strong>合计</strong></p></td>
		<td align="center" nowrap id="company_t">&nbsp;</td>
		<td align="center" nowrap id="generalDanger_t">&nbsp;</td>
		<td align="center" nowrap id="generalDangerGovern_t">&nbsp;</td>
	  <td align="center" nowrap id="zg_t">&nbsp;</td>
	  <td align="center" nowrap id="bt_t">&nbsp;</td>
	  <td align="center" nowrap id="btg_t">&nbsp;</td>
	  <td align="center" nowrap id="bt_r_t">&nbsp;</td>
	  <td align="center" nowrap id="btng_t">&nbsp;</td>
	  <td align="center" nowrap id="wdw_t">&nbsp;</td>
	  <td align="center" nowrap id="gpt_t">&nbsp;</td>
	  <td align="center" nowrap id="pgp_t">&nbsp;</td>
	  <td align="center" nowrap id="planMoney_t">&nbsp;</td></tr>
	  </tr>
	  <tr>
       <td colspan="15"><table width="100%" border="0"><tr>
	   <td width="15%" nowrap="nowrap">审核人：	     </td>
	   <td width="8%"><input type="text" id="chargeMan" name="mineReport.chargeMan" class="input" maxlength="10" size="10" value="${mineReport.chargeMan}"/></td>
	   <td width="13%" align="center">填表人：	     </td>
	   <td width="8%"><input type="text" id="fillMan" name="mineReport.fillMan" class="input" maxlength="10" size="10" value="${mineReport.fillMan}"/></td>
	   <td width="13%" align="center">联系电话：	     </td>
	   <td width="15%"><input type="text" id="tel" name="mineReport.tel" class="input" maxlength="13" size="13" style="ime-mode:disabled;" value="${mineReport.tel}"/><ui:v for="tel" rule="phone" require="false" tips="格式为0574-87364008" warn="您输入的电话号码不存在或格式不正确"/></td>
	   <td width="16%" align="center">填报日期：	     </td>
	   <td width="12%"><input type="text" id="fillDate" name="fillDate" class="input" maxlength="10" size="10" onKeyUp="AutoFillDate(this,10);" style="ime-mode:disabled;" value="<s:date name='mineReport.fillDate' format='yyyy-MM-dd' />" onMouseDown="checkDateOnMouse(this)"/><a href="javascript:void(0);" onFocus="this.blur();"><img id="cal" src="${resourcePath}/img/cal.gif" width="20" height="17" onClick="calendar(get('fillDate'));" border="0" title="日历控件"/></a>
	   <ui:v for="fillDate" rule="date" require="false" tips="格式为2000-01-01" warn="您输入的日期不存在或格式不正确"/></td>
	   </tr></table></td>
    </tr>	
  </table>
   <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>		 
     <td width="28%" align="center">
     <input id="sub" type="button" class="but_2" value="保  存"  onclick="submitCreate();"/>
     <input type="button" class="but_2" onClick="window.location='loadCountry2010.xhtml?mineReport.type=${mineReport.type}&mineReport.reportMonth=${mineReport.reportMonth}&mineReport.print=true&mineReport.id=${mineReport.id}';" value="打  印" />
     &nbsp;&nbsp;<input type="button" class="but_2" value="返  回"  onclick="history.back(-1);"/>
	 <input type="button" name="back" class="but_2" onClick="tableToExcel();" value="导入到EXCEL" />
        </td>
    </tr>
</table>
	 <input type="hidden" id="type" name="mineReport.type" value="${mineReport.type}"/>
	 <input type="hidden" id="isProvince" name="mineReport.province" value="true"/>
	 <input type="hidden" id="id" name="mineReport.id" value="${mineReport.id}" disabled/>
</form>
</body>
<script type="text/javascript">
<c:choose>
	<c:when test="${empty(mineReport)}">
	
	</c:when>
	<c:otherwise>
		//显示填报月份
		<c:if test="${!empty(mineReport.reportMonth)}">
			var year = "${mineReport.reportMonth}".split("-")[0];
			var month = "${mineReport.reportMonth}".split("-")[1];
			get("year").value=year;
			get("month").value=month;
		</c:if>
		<c:if test="${mineReport.state>0}">
			for(var i=0;i<document.getElementsByTagName("input").length;i++) {
				document.getElementsByTagName("input")[i].disabled = true;				
			}
			get("sub").style.display = "none";
			get("year").disabled = true;
			get("month").disabled = true;
			get("cal").style.display = "none";
		</c:if>
	</c:otherwise>
</c:choose>
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
//合计
sumSons(get('company_t'),getAll('company'),get('company_t'),true);
sumSons(get('generalDanger_t'),getAll('generalDanger'),get('generalDanger_t'),true);
sumSons(get('generalDangerGovern_t'),getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);
sumSons(get('planMoney_t'),getAll('planMoney'),get('planMoney_t'),false);	
//设置TD值
sumSonInnerHTML("4","5","6","6");//sumSonInnerHTML("6","7","8","9","10","11","12","6");
delZero(get("bt_r_4"),getAll("planMoney")[4],get("bt_r_7"),getAll("planMoney")[7],get("bt_r_8"),getAll("planMoney")[8]);
<c:if test="${!empty(reportMonth)}">
	var year = "${reportMonth}".split("-")[0];
	var month = "${reportMonth}".split("-")[1];
	get("year").value=year;
	get("month").value=month;
</c:if>
setTradeType(51);
sumSonInnerHTML("0","1","2","3","4","5","6","7","8","9","t");

if(get("month").value == "04" || get("month").value == "06" || get("month").value == "09" || get("month").value == "11") {
	get("day").innerHTML="30";
} else {
	if (get("month").value == "02") {
		if((get("year").value % 4 == 0 && get("year").value % 100 != 0) || get("year").value % 400 == 0)
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
</script>
</html>