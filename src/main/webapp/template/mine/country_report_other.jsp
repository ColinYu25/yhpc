<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/header.jsp" %>
<script type="text/javascript">
function submitCreate(){
if (get("year").value=="0" || get("year").value=="") {
			alert("请选择年份！");
			get("year").focus();
			return false;
		} else if(get("month").value=="0" || get("month").value=="") {
			alert("请选择月份！");
			get("month").focus();
			return false;
		}
 		 var obj=get("mineForm");
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

</script>
<body>
<form id="bigTroubleForm" name="bigTroubleForm" method="post" action="../bigTrouble/loadBigTroubles.xhtml">
<input type="hidden" name="mineId" id="mineId" value="${mineReport.id}"/>
<input type="hidden" name="reportState" id="reportState" value="123"/>
<input type="hidden" name="tableType" id="tableType" value="2"/>
<input type="hidden" name="troubleType" id="troubleType"/>
<input type="hidden" name="tradeType" id="tradeType"/>
<input type="hidden" name="reportMonth" id="reportMonth"/>
<input type="hidden" name="tableName" value="other"/>
</form>
<form name="mineForm" id="mineForm" method="post">
  <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" id="tb">
    <tr>
      <td>
	  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
		     <th colspan="2" height="50" class="head">其他重点行业领域安全生产隐患排查治理情况统计表</th>
        </tr>
		<tr style="font-size:12px">
		  <td height="18" width="80%">填报单位（章）:${fillUnit}</span></td>
		  <td height="18" width="20%" align="right" nowrap="nowrap" valign="bottom">
		  截止到<select id="year" name="mineReport.year" style="width:55px;">
		  	<c:forEach var="year" items="${years}" varStatus="status"><option value="${year}">${year}</option></c:forEach>
		  </select>年<select id="month" name="mineReport.month" style="width:45px;" onChange="window.location='loadCountry.xhtml?mineReport.type=3&mineReport.reportMonth='+get('year').value+'-'+this.value;">
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
          <td height="10" widtd="72">&nbsp;</td>
          <td colspan="3" widtd="216"><center>一般隐患</center></td>
          <td colspan="10" widtd="720"><center> 重大隐患</center></td>
        </tr>
        <tr height="20">
          <td rowspan="3"><center>排查单位数</center></td>
          <td rowspan="3"><center>隐患数</center></td>
          <td rowspan="3"><center>已经整改数</center></td>
          <td rowspan="3"><center>整改率</center></td>
          <td height="20" colspan="3"><center>排查治理重大隐患</center></td>
          <td colspan="7"><center>列入治理计划的重大隐患</center></td>
        </tr>
        <tr height="20">
          <td rowspan="2"><center>重大隐患数</center></td>
          <td rowspan="2"><center>已整改销号数</center></td>
          <td rowspan="2"><center>整改率</center></td>
          <td rowspan="2"><center>重大隐患数</center></td>
          <td height="20" colspan="5"><center>其中</center></td>
          <td rowspan="2"><center>累计落实治理资金</center></td>
        </tr>
        <tr height="19">
          <td><center>落实治理目标任务</center></td>
          <td><center>落实治理经费物资</center></td>
          <td><center>落实治理机构人员</center></td>
          <td><center>落实治理时间要求</center></td>
          <td><center>落实安全措施应急预案</center></td>
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
          <td><center>(项)</center></td>
          <td><center>(项)</center></td>
          <td><center>(万元)</center></td>
        </tr>
		<tr>
	  <td nowrap="true" width="18%" class="td_left"><p><strong>1.道路运输企业</strong></p></td>
	  <td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[0].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[0].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[0].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_0">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[0].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(18,1);">${statistics[0].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[0].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(18,2);">${statistics[0].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[0].bigTrouble==0||statistics[0].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[0].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[0].bigTroubleGovern/statistics[0].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[0].bigTroubleGovern/statistics[0].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[0].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(18,3);">${statistics[0].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="tt"><c:choose><c:when test="${statistics[0].targetTask==0}">&nbsp;</c:when><c:otherwise>${statistics[0].targetTask}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="g"><c:choose><c:when test="${statistics[0].goods==0}">&nbsp;</c:when><c:otherwise>${statistics[0].goods}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="w"><c:choose><c:when test="${statistics[0].worker==0}">&nbsp;</c:when><c:otherwise>${statistics[0].worker}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gd"><c:choose><c:when test="${statistics[0].governDate==0}">&nbsp;</c:when><c:otherwise>${statistics[0].governDate}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="sm"><c:choose><c:when test="${statistics[0].safetyMethod==0}">&nbsp;</c:when><c:otherwise>${statistics[0].safetyMethod}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[0].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[0].planMoneyString,(fn:length((mineReportDetails[0].planMoneyString))-2),fn:length((mineReportDetails[0].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[0].planMoney}"/></c:when><c:otherwise>${mineReportDetails[0].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>2.公路养护施工企业</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[1].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[1].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[1].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_1">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[1].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(19,1);">${statistics[1].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[1].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(19,2);">${statistics[1].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[1].bigTrouble==0||statistics[1].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[1].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[1].bigTroubleGovern/statistics[1].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[1].bigTroubleGovern/statistics[1].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[1].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(19,3);">${statistics[1].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="tt"><c:choose><c:when test="${statistics[1].targetTask==0}">&nbsp;</c:when><c:otherwise>${statistics[1].targetTask}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="g"><c:choose><c:when test="${statistics[1].goods==0}">&nbsp;</c:when><c:otherwise>${statistics[1].goods}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="w"><c:choose><c:when test="${statistics[1].worker==0}">&nbsp;</c:when><c:otherwise>${statistics[1].worker}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gd"><c:choose><c:when test="${statistics[1].governDate==0}">&nbsp;</c:when><c:otherwise>${statistics[1].governDate}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="sm"><c:choose><c:when test="${statistics[1].safetyMethod==0}">&nbsp;</c:when><c:otherwise>${statistics[1].safetyMethod}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[1].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[1].planMoneyString,(fn:length((mineReportDetails[1].planMoneyString))-2),fn:length((mineReportDetails[1].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[1].planMoney}"/></c:when><c:otherwise>${mineReportDetails[1].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>3.水上运输企业</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[2].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[2].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[2].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_2">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[2].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(20,1);">${statistics[2].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[2].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(20,2);">${statistics[2].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[2].bigTrouble==0||statistics[2].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[2].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[2].bigTroubleGovern/statistics[2].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[2].bigTroubleGovern/statistics[2].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[2].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(20,3);">${statistics[2].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="tt"><c:choose><c:when test="${statistics[2].targetTask==0}">&nbsp;</c:when><c:otherwise>${statistics[2].targetTask}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="g"><c:choose><c:when test="${statistics[2].goods==0}">&nbsp;</c:when><c:otherwise>${statistics[2].goods}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="w"><c:choose><c:when test="${statistics[2].worker==0}">&nbsp;</c:when><c:otherwise>${statistics[2].worker}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gd"><c:choose><c:when test="${statistics[2].governDate==0}">&nbsp;</c:when><c:otherwise>${statistics[2].governDate}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="sm"><c:choose><c:when test="${statistics[2].safetyMethod==0}">&nbsp;</c:when><c:otherwise>${statistics[2].safetyMethod}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[2].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[2].planMoneyString,(fn:length((mineReportDetails[2].planMoneyString))-2),fn:length((mineReportDetails[2].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[2].planMoney}"/></c:when><c:otherwise>${mineReportDetails[2].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>4.铁路运输企业</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[3].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[3].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[3].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[3].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[3].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[3].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_3">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[3].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(21,1);">${statistics[3].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[3].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(21,2);">${statistics[3].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[3].bigTrouble==0||statistics[3].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[3].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[3].bigTroubleGovern/statistics[3].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[3].bigTroubleGovern/statistics[3].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[3].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(21,3);">${statistics[3].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="tt"><c:choose><c:when test="${statistics[3].targetTask==0}">&nbsp;</c:when><c:otherwise>${statistics[3].targetTask}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="g"><c:choose><c:when test="${statistics[3].goods==0}">&nbsp;</c:when><c:otherwise>${statistics[3].goods}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="w"><c:choose><c:when test="${statistics[3].worker==0}">&nbsp;</c:when><c:otherwise>${statistics[3].worker}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gd"><c:choose><c:when test="${statistics[3].governDate==0}">&nbsp;</c:when><c:otherwise>${statistics[3].governDate}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="sm"><c:choose><c:when test="${statistics[3].safetyMethod==0}">&nbsp;</c:when><c:otherwise>${statistics[3].safetyMethod}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[3].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[3].planMoneyString,(fn:length((mineReportDetails[3].planMoneyString))-2),fn:length((mineReportDetails[3].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[3].planMoney}"/></c:when><c:otherwise>${mineReportDetails[3].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>
	  <tr>
  <td nowrap="true" width="18%"><p><strong>5.航空公司</strong></p></td>
 	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[4].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[4].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[4].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[4].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[4].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[4].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_4">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[4].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(22,1);">${statistics[4].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[4].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(22,2);">${statistics[4].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[4].bigTrouble==0||statistics[4].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[4].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[4].bigTroubleGovern/statistics[4].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[4].bigTroubleGovern/statistics[4].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[4].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(22,3);">${statistics[4].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="tt"><c:choose><c:when test="${statistics[4].targetTask==0}">&nbsp;</c:when><c:otherwise>${statistics[4].targetTask}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="g"><c:choose><c:when test="${statistics[4].goods==0}">&nbsp;</c:when><c:otherwise>${statistics[4].goods}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="w"><c:choose><c:when test="${statistics[4].worker==0}">&nbsp;</c:when><c:otherwise>${statistics[4].worker}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gd"><c:choose><c:when test="${statistics[4].governDate==0}">&nbsp;</c:when><c:otherwise>${statistics[4].governDate}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="sm"><c:choose><c:when test="${statistics[4].safetyMethod==0}">&nbsp;</c:when><c:otherwise>${statistics[4].safetyMethod}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[4].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[4].planMoneyString,(fn:length((mineReportDetails[4].planMoneyString))-2),fn:length((mineReportDetails[4].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[4].planMoney}"/></c:when><c:otherwise>${mineReportDetails[4].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>
	  
	  <tr>
	  <td nowrap="true" width="18%" class="td_left"><p><strong>6.机场和油料企业</strong></p></td>
	  <td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[5].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[5].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[5].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[5].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[5].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[5].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_5">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[5].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(23,1);">${statistics[5].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[5].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(23,2);">${statistics[5].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[5].bigTrouble==0||statistics[5].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[5].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[5].bigTroubleGovern/statistics[5].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[5].bigTroubleGovern/statistics[5].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[5].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(23,3);">${statistics[5].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="tt"><c:choose><c:when test="${statistics[5].targetTask==0}">&nbsp;</c:when><c:otherwise>${statistics[5].targetTask}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="g"><c:choose><c:when test="${statistics[5].goods==0}">&nbsp;</c:when><c:otherwise>${statistics[5].goods}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="w"><c:choose><c:when test="${statistics[5].worker==0}">&nbsp;</c:when><c:otherwise>${statistics[5].worker}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gd"><c:choose><c:when test="${statistics[5].governDate==0}">&nbsp;</c:when><c:otherwise>${statistics[5].governDate}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="sm"><c:choose><c:when test="${statistics[5].safetyMethod==0}">&nbsp;</c:when><c:otherwise>${statistics[5].safetyMethod}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[5].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[5].planMoneyString,(fn:length((mineReportDetails[5].planMoneyString))-2),fn:length((mineReportDetails[5].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[5].planMoney}"/></c:when><c:otherwise>${mineReportDetails[5].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>7.渔业企业</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[6].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[6].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[6].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_6">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[6].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(24,1);">${statistics[6].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[6].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(24,2);">${statistics[6].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[6].bigTrouble==0||statistics[6].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[6].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[6].bigTroubleGovern/statistics[6].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[6].bigTroubleGovern/statistics[6].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[6].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(24,3);">${statistics[6].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="tt"><c:choose><c:when test="${statistics[6].targetTask==0}">&nbsp;</c:when><c:otherwise>${statistics[6].targetTask}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="g"><c:choose><c:when test="${statistics[6].goods==0}">&nbsp;</c:when><c:otherwise>${statistics[6].goods}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="w"><c:choose><c:when test="${statistics[6].worker==0}">&nbsp;</c:when><c:otherwise>${statistics[6].worker}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gd"><c:choose><c:when test="${statistics[6].governDate==0}">&nbsp;</c:when><c:otherwise>${statistics[6].governDate}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="sm"><c:choose><c:when test="${statistics[6].safetyMethod==0}">&nbsp;</c:when><c:otherwise>${statistics[6].safetyMethod}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[6].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[6].planMoneyString,(fn:length((mineReportDetails[6].planMoneyString))-2),fn:length((mineReportDetails[6].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[6].planMoney}"/></c:when><c:otherwise>${mineReportDetails[6].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>8.农机行业</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[7].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[7].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[7].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_7">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[7].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(25,1);">${statistics[7].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[7].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(25,2);">${statistics[7].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[7].bigTrouble==0||statistics[7].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[7].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[7].bigTroubleGovern/statistics[7].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[7].bigTroubleGovern/statistics[7].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[7].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(25,3);">${statistics[7].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="tt"><c:choose><c:when test="${statistics[7].targetTask==0}">&nbsp;</c:when><c:otherwise>${statistics[7].targetTask}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="g"><c:choose><c:when test="${statistics[7].goods==0}">&nbsp;</c:when><c:otherwise>${statistics[7].goods}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="w"><c:choose><c:when test="${statistics[7].worker==0}">&nbsp;</c:when><c:otherwise>${statistics[7].worker}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gd"><c:choose><c:when test="${statistics[7].governDate==0}">&nbsp;</c:when><c:otherwise>${statistics[7].governDate}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="sm"><c:choose><c:when test="${statistics[7].safetyMethod==0}">&nbsp;</c:when><c:otherwise>${statistics[7].safetyMethod}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[7].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[7].planMoneyString,(fn:length((mineReportDetails[7].planMoneyString))-2),fn:length((mineReportDetails[7].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[7].planMoney}"/></c:when><c:otherwise>${mineReportDetails[7].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>9.水利工程</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[8].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[8].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[8].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[8].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[8].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[8].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_8">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[8].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(26,1);">${statistics[8].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[8].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(26,2);">${statistics[8].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[8].bigTrouble==0||statistics[8].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[8].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[8].bigTroubleGovern/statistics[8].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[8].bigTroubleGovern/statistics[8].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[8].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(26,3);">${statistics[8].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="tt"><c:choose><c:when test="${statistics[8].targetTask==0}">&nbsp;</c:when><c:otherwise>${statistics[8].targetTask}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="g"><c:choose><c:when test="${statistics[8].goods==0}">&nbsp;</c:when><c:otherwise>${statistics[8].goods}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="w"><c:choose><c:when test="${statistics[8].worker==0}">&nbsp;</c:when><c:otherwise>${statistics[8].worker}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gd"><c:choose><c:when test="${statistics[8].governDate==0}">&nbsp;</c:when><c:otherwise>${statistics[8].governDate}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="sm"><c:choose><c:when test="${statistics[8].safetyMethod==0}">&nbsp;</c:when><c:otherwise>${statistics[8].safetyMethod}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[8].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[8].planMoneyString,(fn:length((mineReportDetails[8].planMoneyString))-2),fn:length((mineReportDetails[8].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[8].planMoney}"/></c:when><c:otherwise>${mineReportDetails[8].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>

	  <td nowrap="true" width="18%" class="td_left"><p><strong>10.学校</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[10].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[10].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[10].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[10].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[10].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[10].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_10">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[10].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(28,1);">${statistics[10].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[10].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(28,2);">${statistics[10].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[10].bigTrouble==0||statistics[10].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[10].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[10].bigTroubleGovern/statistics[10].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[10].bigTroubleGovern/statistics[10].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[10].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(28,3);">${statistics[10].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="tt"><c:choose><c:when test="${statistics[10].targetTask==0}">&nbsp;</c:when><c:otherwise>${statistics[10].targetTask}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="g"><c:choose><c:when test="${statistics[10].goods==0}">&nbsp;</c:when><c:otherwise>${statistics[10].goods}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="w"><c:choose><c:when test="${statistics[10].worker==0}">&nbsp;</c:when><c:otherwise>${statistics[10].worker}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gd"><c:choose><c:when test="${statistics[10].governDate==0}">&nbsp;</c:when><c:otherwise>${statistics[10].governDate}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="sm"><c:choose><c:when test="${statistics[10].safetyMethod==0}">&nbsp;</c:when><c:otherwise>${statistics[10].safetyMethod}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[10].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[10].planMoneyString,(fn:length((mineReportDetails[10].planMoneyString))-2),fn:length((mineReportDetails[10].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[10].planMoney}"/></c:when><c:otherwise>${mineReportDetails[10].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>11.商场、市场等人员密集场所</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[11].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[11].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[11].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[11].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[11].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[11].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_11">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[11].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(29,1);">${statistics[11].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[11].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(29,2);">${statistics[11].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[11].bigTrouble==0||statistics[11].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[11].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[11].bigTroubleGovern/statistics[11].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[11].bigTroubleGovern/statistics[11].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[11].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(29,3);">${statistics[11].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="tt"><c:choose><c:when test="${statistics[11].targetTask==0}">&nbsp;</c:when><c:otherwise>${statistics[11].targetTask}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="g"><c:choose><c:when test="${statistics[11].goods==0}">&nbsp;</c:when><c:otherwise>${statistics[11].goods}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="w"><c:choose><c:when test="${statistics[11].worker==0}">&nbsp;</c:when><c:otherwise>${statistics[11].worker}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gd"><c:choose><c:when test="${statistics[11].governDate==0}">&nbsp;</c:when><c:otherwise>${statistics[11].governDate}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="sm"><c:choose><c:when test="${statistics[11].safetyMethod==0}">&nbsp;</c:when><c:otherwise>${statistics[11].safetyMethod}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[11].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[11].planMoneyString,(fn:length((mineReportDetails[11].planMoneyString))-2),fn:length((mineReportDetails[11].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[11].planMoney}"/></c:when><c:otherwise>${mineReportDetails[11].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>12.建筑施工企业</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[12].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[12].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[12].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_12">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[12].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(30,1);">${statistics[12].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[12].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(30,2);">${statistics[12].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[12].bigTrouble==0||statistics[12].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[12].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[12].bigTroubleGovern/statistics[12].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[12].bigTroubleGovern/statistics[12].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[12].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(30,3);">${statistics[12].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="tt"><c:choose><c:when test="${statistics[12].targetTask==0}">&nbsp;</c:when><c:otherwise>${statistics[12].targetTask}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="g"><c:choose><c:when test="${statistics[12].goods==0}">&nbsp;</c:when><c:otherwise>${statistics[12].goods}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="w"><c:choose><c:when test="${statistics[12].worker==0}">&nbsp;</c:when><c:otherwise>${statistics[12].worker}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gd"><c:choose><c:when test="${statistics[12].governDate==0}">&nbsp;</c:when><c:otherwise>${statistics[12].governDate}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="sm"><c:choose><c:when test="${statistics[12].safetyMethod==0}">&nbsp;</c:when><c:otherwise>${statistics[12].safetyMethod}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[12].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[12].planMoneyString,(fn:length((mineReportDetails[12].planMoneyString))-2),fn:length((mineReportDetails[12].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[12].planMoney}"/></c:when><c:otherwise>${mineReportDetails[12].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>13.民爆器材生产企业</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[13].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[13].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[13].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_13">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[13].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(31,1);">${statistics[13].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[13].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(31,2);">${statistics[13].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[13].bigTrouble==0||statistics[13].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[13].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[13].bigTroubleGovern/statistics[13].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[13].bigTroubleGovern/statistics[13].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[13].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(31,3);">${statistics[13].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="tt"><c:choose><c:when test="${statistics[13].targetTask==0}">&nbsp;</c:when><c:otherwise>${statistics[13].targetTask}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="g"><c:choose><c:when test="${statistics[13].goods==0}">&nbsp;</c:when><c:otherwise>${statistics[13].goods}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="w"><c:choose><c:when test="${statistics[13].worker==0}">&nbsp;</c:when><c:otherwise>${statistics[13].worker}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gd"><c:choose><c:when test="${statistics[13].governDate==0}">&nbsp;</c:when><c:otherwise>${statistics[13].governDate}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="sm"><c:choose><c:when test="${statistics[13].safetyMethod==0}">&nbsp;</c:when><c:otherwise>${statistics[13].safetyMethod}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[13].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[13].planMoneyString,(fn:length((mineReportDetails[13].planMoneyString))-2),fn:length((mineReportDetails[13].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[13].planMoney}"/></c:when><c:otherwise>${mineReportDetails[13].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
  <td nowrap="true" width="18%"><p><strong>14.电力企业</strong></p></td>
  <td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[14].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[14].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[14].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[14].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[14].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[14].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_14">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[14].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(32,1);">${statistics[14].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[14].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(32,2);">${statistics[14].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[14].bigTrouble==0||statistics[14].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[14].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[14].bigTroubleGovern/statistics[14].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[14].bigTroubleGovern/statistics[14].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[14].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(32,3);">${statistics[14].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="tt"><c:choose><c:when test="${statistics[14].targetTask==0}">&nbsp;</c:when><c:otherwise>${statistics[14].targetTask}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="g"><c:choose><c:when test="${statistics[14].goods==0}">&nbsp;</c:when><c:otherwise>${statistics[14].goods}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="w"><c:choose><c:when test="${statistics[14].worker==0}">&nbsp;</c:when><c:otherwise>${statistics[14].worker}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gd"><c:choose><c:when test="${statistics[14].governDate==0}">&nbsp;</c:when><c:otherwise>${statistics[14].governDate}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="sm"><c:choose><c:when test="${statistics[14].safetyMethod==0}">&nbsp;</c:when><c:otherwise>${statistics[14].safetyMethod}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[14].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[14].planMoneyString,(fn:length((mineReportDetails[14].planMoneyString))-2),fn:length((mineReportDetails[14].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[14].planMoney}"/></c:when><c:otherwise>${mineReportDetails[14].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>
	  
		<tr>
	  <td nowrap="true" width="18%" class="td_left"><p><strong>15.机械制造企业</strong></p></td>
	  <td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[15].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[15].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[15].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[15].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[15].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[15].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_15">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[15].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(33,1);">${statistics[15].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[15].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(33,2);">${statistics[15].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[15].bigTrouble==0||statistics[15].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[15].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[15].bigTroubleGovern/statistics[15].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[15].bigTroubleGovern/statistics[15].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[15].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(33,3);">${statistics[15].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="tt"><c:choose><c:when test="${statistics[15].targetTask==0}">&nbsp;</c:when><c:otherwise>${statistics[15].targetTask}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="g"><c:choose><c:when test="${statistics[15].goods==0}">&nbsp;</c:when><c:otherwise>${statistics[15].goods}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="w"><c:choose><c:when test="${statistics[15].worker==0}">&nbsp;</c:when><c:otherwise>${statistics[15].worker}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gd"><c:choose><c:when test="${statistics[15].governDate==0}">&nbsp;</c:when><c:otherwise>${statistics[15].governDate}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="sm"><c:choose><c:when test="${statistics[15].safetyMethod==0}">&nbsp;</c:when><c:otherwise>${statistics[15].safetyMethod}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="planMoney"><c:choose><c:when test="${mineReportDetails[15].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[15].planMoneyString,(fn:length((mineReportDetails[15].planMoneyString))-2),fn:length((mineReportDetails[15].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[15].planMoney}"/></c:when><c:otherwise>${mineReportDetails[15].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td nowrap="true" width="18%"><p><strong>16.其他单位</strong></p></td>
	<td align="center" nowrap="true" id="company"><c:choose><c:when test="${mineReportDetails[16].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].company}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDanger"><c:choose><c:when test="${mineReportDetails[16].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[16].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="zg_16">&nbsp;</td>
	  <td align="center" nowrap="true" id="bt"><c:choose><c:when test="${statistics[16].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(34,1);">${statistics[16].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="btg"><c:choose><c:when test="${statistics[16].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(34,2);">${statistics[16].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true">
	  <c:choose><c:when test="${statistics[16].bigTrouble==0||statistics[16].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[16].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[16].bigTroubleGovern/statistics[16].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[16].bigTroubleGovern/statistics[16].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose>
	  </td>
	  <td align="center" nowrap="true"><c:choose><c:when test="${statistics[16].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(34,3);">${statistics[16].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="tt"><c:choose><c:when test="${statistics[16].targetTask==0}">&nbsp;</c:when><c:otherwise>${statistics[16].targetTask}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="g"><c:choose><c:when test="${statistics[16].goods==0}">&nbsp;</c:when><c:otherwise>${statistics[16].goods}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="w"><c:choose><c:when test="${statistics[16].worker==0}">&nbsp;</c:when><c:otherwise>${statistics[16].worker}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="gd"><c:choose><c:when test="${statistics[16].governDate==0}">&nbsp;</c:when><c:otherwise>${statistics[16].governDate}</c:otherwise></c:choose></td>
	  <td align="center" nowrap="true" id="sm"><c:choose><c:when test="${statistics[16].safetyMethod==0}">&nbsp;</c:when><c:otherwise>${statistics[16].safetyMethod}</c:otherwise></c:choose></td>
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
	  <td align="center" nowrap="true" id="tt_t">&nbsp;</td>
	  <td align="center" nowrap="true" id="g_t">&nbsp;</td>
	  <td align="center" nowrap="true" id="w_t">&nbsp;</td>
	  <td align="center" nowrap="true" id="gd_t">&nbsp;</td>
	  <td align="center" nowrap="true" id="sm_t">&nbsp;</td>
	  <td align="center" nowrap="true" id="planMoney_t">&nbsp;</td></tr>
	  <tr>
       <td colspan="15"><table width="100%" border="0"><tr>
	   <td width="15%" nowrap="nowrap">单位负责人：	     </td>
	   <td width="8%"><input type="text" id="chargeMan" name="mineReport.chargeMan" class="input" maxlength="10" size="10" value="${mineReport.chargeMan}"/></td>
	   <td width="13%" align="center">填表人：	     </td>
	   <td width="8%"><input type="text" id="fillMan" name="mineReport.fillMan" class="input" maxlength="10" size="10" value="${mineReport.fillMan}"/></td>
	   <td width="13%" align="center">联系电话：	     </td>
	   <td width="15%"><input type="text" id="tel" name="mineReport.tel" class="input" maxlength="13" size="13" style="ime-mode:disabled;" value="${mineReport.tel}"/><ui:v for="tel" rule="phone" require="false" tips="格式为0574-87364008" warn="您输入的电话号码不存在或格式不正确"/></td>
	   <td width="16%" align="center">填报日期：	     </td>
	   <td width="12%"><input type="text" id="fillDate" name="fillDate" class="input" maxlength="10" size="10" onKeyUp="AutoFillDate(this,10);" style="ime-mode:disabled;" value="<s:date name='mineReport.fillDate' format='yyyy-MM-dd' />" onMouseDown="checkDateOnMouse(this)"/><a href="javaScript:;" onFocus="this.blur();"><img id="cal" src="${resourcePath}/img/cal.gif" width="20" height="17" onClick="calendar(get('fillDate'));" border="0" title="日历控件"/></a>
	   <ui:v for="fillDate" rule="date" require="false" tips="格式为2000-01-01" warn="您输入的日期不存在或格式不正确"/></td>
	   </tr></table></td>
    </tr>	
  </table>
	   </td>
    </tr>	
  </table>
   <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>		 
     <td width="28%" align="center">
     <input id="sub" type="button" class="but_2" value="保  存"  onclick="submitCreate();"/>
     <input type="button" class="but_2" value="返  回"  onclick="history.back(-1);"/> 
	 <input type="button" class="but_2" onClick="window.location='printCountry.xhtml?mineReport.type=3&mineReport.reportMonth=${mineReport.reportMonth}&mineReport.print=true&mineReport.id=${mineReport.id}';" value="打  印" />
	 <input type="button" name="back" class="but_2" onClick="tableToExcel();" value="导入到EXCEL" />
	 </td>
    </tr>
</table>
	 <input type="hidden" id="type" name="mineReport.type" value="3"/>
	 <input type="hidden" id="isCountry" name="mineReport.country" value="true"/>
	 <input type="hidden" id="id" name="mineReport.id" value="${mineReport.id}" disabled="true"/>
</form>
</body>
<script type="text/javascript">
<c:choose>
	<c:when test="${empty(mineReport)}">
		var input = document.getElementsByTagName("input");
		for(var i = 0 ; i < input.length-8; i++) {
			if(input[i].type == "text") {
				input[i].value = "0";
			}
		}
	</c:when>
	<c:otherwise>
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
		sumSons(get('company_t'),getAll('company'),get('company_t'),true);
		sumSons(get('generalDanger_t'),getAll('generalDanger'),get('generalDanger_t'),true);
		sumSons(get('generalDangerGovern_t'),getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);
		sumSons(get('planMoney_t'),getAll('planMoney'),get('planMoney_t'),false);		
		//显示填报月份
		<c:if test="${!empty(mineReport.reportMonth)}">
			var year = "${mineReport.reportMonth}".split("-")[0];
			var month = "${mineReport.reportMonth}".split("-")[1];
			get("year").value=year;
			get("month").value=month;
		</c:if>	
	</c:otherwise>
</c:choose>
sumSonInnerHTML("0","1","2","3","4","5","6","7","8","10","11","12","13","14","15","9","t");


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
</script>
</html>