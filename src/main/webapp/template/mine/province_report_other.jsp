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
		  </select>年<select id="month" name="mineReport.month" style="width:45px;" onChange="window.location='loadProvinceReport.xhtml?mineReport.type=${mineReport.type}&mineReport.reportMonth='+get('year').value+'-'+this.value;">
		  	<c:forEach var="month" items="${months}" varStatus="status"><c:choose><c:when test="${month.choose}"><option value="${month.month}">${month.month}</option></c:when><c:otherwise><optgroup label="${month.month}"></optgroup></c:otherwise></c:choose></c:forEach>
		  </select>月<span id="day"></span>日为止&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
    </tr><tr>
		<td width="18%" class="td_left"><p><strong>1.道路运输企业</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[0].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[0].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_0">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[0].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[0].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_0">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[0].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(18,1);">${statistics[0].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[0].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(18,2);">${statistics[0].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[0].bigTrouble==0||statistics[0].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[0].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[0].bigTroubleGovern/statistics[0].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[0].bigTroubleGovern/statistics[0].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[0].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(18,3);">${statistics[0].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[0].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(18,4);">${statistics[0].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[0].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(18,5);">${statistics[0].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[0].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(18,6);">${statistics[0].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[0].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(18,7);">${statistics[0].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[0].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[0].planMoneyString,(fn:length((mineReportDetails[0].planMoneyString))-2),fn:length((mineReportDetails[0].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[0].planMoney}"/></c:when><c:otherwise>${mineReportDetails[0].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>2.公路养护施工企业</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[1].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[1].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_1">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[1].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[1].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_1">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[1].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(19,1);">${statistics[1].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[1].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(19,2);">${statistics[1].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[1].bigTrouble==0||statistics[1].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[1].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[1].bigTroubleGovern/statistics[1].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[1].bigTroubleGovern/statistics[1].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[1].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(19,3);">${statistics[1].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[1].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(19,4);">${statistics[1].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[1].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(19,5);">${statistics[1].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[1].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(19,6);">${statistics[1].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[1].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(19,7);">${statistics[1].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[1].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[1].planMoneyString,(fn:length((mineReportDetails[1].planMoneyString))-2),fn:length((mineReportDetails[1].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[1].planMoney}"/></c:when><c:otherwise>${mineReportDetails[1].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>3.水上运输企业</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[2].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[2].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_2">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[2].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[2].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_2">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[2].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(20,1);">${statistics[2].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[2].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(20,2);">${statistics[2].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[2].bigTrouble==0||statistics[2].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[2].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[2].bigTroubleGovern/statistics[2].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[2].bigTroubleGovern/statistics[2].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[2].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(20,3);">${statistics[2].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[2].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(20,4);">${statistics[2].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[2].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(20,5);">${statistics[2].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[2].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(20,6);">${statistics[2].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[2].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(20,7);">${statistics[2].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[2].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[2].planMoneyString,(fn:length((mineReportDetails[2].planMoneyString))-2),fn:length((mineReportDetails[2].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[2].planMoney}"/></c:when><c:otherwise>${mineReportDetails[2].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>4.铁路运输企业</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[3].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[3].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[3].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[3].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_3">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[3].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[3].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[3].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[3].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_3">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[3].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(21,1);">${statistics[3].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[3].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(21,2);">${statistics[3].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[3].bigTrouble==0||statistics[3].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[3].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[3].bigTroubleGovern/statistics[3].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[3].bigTroubleGovern/statistics[3].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[3].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(21,3);">${statistics[3].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[3].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(21,4);">${statistics[3].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[3].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(21,5);">${statistics[3].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[3].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(21,6);">${statistics[3].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[3].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(21,7);">${statistics[3].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[3].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[3].planMoneyString,(fn:length((mineReportDetails[3].planMoneyString))-2),fn:length((mineReportDetails[3].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[3].planMoney}"/></c:when><c:otherwise>${mineReportDetails[3].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>5.航空公司</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[4].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[4].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[4].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[4].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_4">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[4].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[4].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[4].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[4].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_4">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[4].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(22,1);">${statistics[4].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[4].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(22,2);">${statistics[4].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[4].bigTrouble==0||statistics[4].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[4].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[4].bigTroubleGovern/statistics[4].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[4].bigTroubleGovern/statistics[4].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[4].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(22,3);">${statistics[4].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[4].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(22,4);">${statistics[4].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[4].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(22,5);">${statistics[4].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[4].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(22,6);">${statistics[4].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[4].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(22,7);">${statistics[4].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[4].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[4].planMoneyString,(fn:length((mineReportDetails[4].planMoneyString))-2),fn:length((mineReportDetails[4].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[4].planMoney}"/></c:when><c:otherwise>${mineReportDetails[4].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%" class="td_left"><p><strong>6.机场和油料企业</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[5].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[5].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[5].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[5].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_5">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[5].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[5].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[5].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[5].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_5">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[5].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(23,1);">${statistics[5].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[5].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(23,2);">${statistics[5].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[5].bigTrouble==0||statistics[5].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[5].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[5].bigTroubleGovern/statistics[5].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[5].bigTroubleGovern/statistics[5].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[5].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(23,3);">${statistics[5].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[5].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(23,4);">${statistics[5].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[5].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(23,5);">${statistics[5].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[5].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(23,6);">${statistics[5].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[5].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(23,7);">${statistics[5].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[5].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[5].planMoneyString,(fn:length((mineReportDetails[5].planMoneyString))-2),fn:length((mineReportDetails[5].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[5].planMoney}"/></c:when><c:otherwise>${mineReportDetails[5].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>7.渔业企业</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[6].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[6].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_6">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[6].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[6].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_6">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[6].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(24,1);">${statistics[6].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[6].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(24,2);">${statistics[6].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[6].bigTrouble==0||statistics[6].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[6].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[6].bigTroubleGovern/statistics[6].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[6].bigTroubleGovern/statistics[6].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[6].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(24,3);">${statistics[6].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[6].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(24,4);">${statistics[6].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[6].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(24,5);">${statistics[6].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[6].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(24,6);">${statistics[6].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[6].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(24,7);">${statistics[6].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[6].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[6].planMoneyString,(fn:length((mineReportDetails[6].planMoneyString))-2),fn:length((mineReportDetails[6].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[6].planMoney}"/></c:when><c:otherwise>${mineReportDetails[6].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>8.农机行业</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[7].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[7].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_7">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[7].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[7].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_7">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[7].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(25,1);">${statistics[7].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[7].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(25,2);">${statistics[7].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[7].bigTrouble==0||statistics[7].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[7].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[7].bigTroubleGovern/statistics[7].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[7].bigTroubleGovern/statistics[7].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[7].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(25,3);">${statistics[7].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[7].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(25,4);">${statistics[7].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[7].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(25,5);">${statistics[7].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[7].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(25,6);">${statistics[7].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[7].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(25,7);">${statistics[7].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[7].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[7].planMoneyString,(fn:length((mineReportDetails[7].planMoneyString))-2),fn:length((mineReportDetails[7].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[7].planMoney}"/></c:when><c:otherwise>${mineReportDetails[7].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>9.水利工程</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[8].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[8].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[8].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[8].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_8">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[8].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[8].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[8].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[8].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_8">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[8].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(26,1);">${statistics[8].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[8].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(26,2);">${statistics[8].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[8].bigTrouble==0||statistics[8].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[8].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[8].bigTroubleGovern/statistics[8].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[8].bigTroubleGovern/statistics[8].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[8].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(26,3);">${statistics[8].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[8].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(26,4);">${statistics[8].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[8].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(26,5);">${statistics[8].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[8].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(26,6);">${statistics[8].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[8].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(26,7);">${statistics[8].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[8].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[8].planMoneyString,(fn:length((mineReportDetails[8].planMoneyString))-2),fn:length((mineReportDetails[8].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[8].planMoney}"/></c:when><c:otherwise>${mineReportDetails[8].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%" class="td_left"><p><strong>10.学校</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[10].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[10].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[10].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[10].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_10">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[10].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[10].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[10].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[10].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_10">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[10].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(28,1);">${statistics[10].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[10].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(28,2);">${statistics[10].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[10].bigTrouble==0||statistics[10].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[10].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[10].bigTroubleGovern/statistics[10].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[10].bigTroubleGovern/statistics[10].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[10].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(28,3);">${statistics[10].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[10].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(28,4);">${statistics[10].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[10].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(28,5);">${statistics[10].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[10].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(28,6);">${statistics[10].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[10].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(28,7);">${statistics[10].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[10].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[10].planMoneyString,(fn:length((mineReportDetails[10].planMoneyString))-2),fn:length((mineReportDetails[10].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[10].planMoney}"/></c:when><c:otherwise>${mineReportDetails[10].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>11.商场、市场等人员密集场所</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[11].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[11].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[11].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[11].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_11">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[11].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[11].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[11].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[11].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_11">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[11].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(29,1);">${statistics[11].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[11].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(29,2);">${statistics[11].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[11].bigTrouble==0||statistics[11].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[11].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[11].bigTroubleGovern/statistics[11].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[11].bigTroubleGovern/statistics[11].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[11].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(29,3);">${statistics[11].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[11].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(29,4);">${statistics[11].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[11].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(29,5);">${statistics[11].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[11].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(29,6);">${statistics[11].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[11].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(29,7);">${statistics[11].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[11].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[11].planMoneyString,(fn:length((mineReportDetails[11].planMoneyString))-2),fn:length((mineReportDetails[11].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[11].planMoney}"/></c:when><c:otherwise>${mineReportDetails[11].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>12.建筑施工企业</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[12].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[12].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_12">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[12].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[12].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_12">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[12].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(30,1);">${statistics[12].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[12].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(30,2);">${statistics[12].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[12].bigTrouble==0||statistics[12].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[12].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[12].bigTroubleGovern/statistics[12].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[12].bigTroubleGovern/statistics[12].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[12].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(30,3);">${statistics[12].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[12].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(30,4);">${statistics[12].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[12].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(30,5);">${statistics[12].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[12].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(30,6);">${statistics[12].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[12].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(30,7);">${statistics[12].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[12].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[12].planMoneyString,(fn:length((mineReportDetails[12].planMoneyString))-2),fn:length((mineReportDetails[12].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[12].planMoney}"/></c:when><c:otherwise>${mineReportDetails[12].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>13.民爆器材生产企业</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[13].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[13].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_13">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[13].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[13].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_13">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[13].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(31,1);">${statistics[13].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[13].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(31,2);">${statistics[13].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[13].bigTrouble==0||statistics[13].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[13].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[13].bigTroubleGovern/statistics[13].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[13].bigTroubleGovern/statistics[13].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[13].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(31,3);">${statistics[13].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[13].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(31,4);">${statistics[13].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[13].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(31,5);">${statistics[13].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[13].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(31,6);">${statistics[13].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[13].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(31,7);">${statistics[13].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[13].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[13].planMoneyString,(fn:length((mineReportDetails[13].planMoneyString))-2),fn:length((mineReportDetails[13].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[13].planMoney}"/></c:when><c:otherwise>${mineReportDetails[13].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>14.电力企业</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[14].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[14].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[14].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[14].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_14">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[14].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[14].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[14].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[14].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_14">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[14].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(32,1);">${statistics[14].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[14].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(32,2);">${statistics[14].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[14].bigTrouble==0||statistics[14].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[14].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[14].bigTroubleGovern/statistics[14].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[14].bigTroubleGovern/statistics[14].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[14].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(32,3);">${statistics[14].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[14].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(32,4);">${statistics[14].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[14].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(32,5);">${statistics[14].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[14].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(32,6);">${statistics[14].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[14].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(32,7);">${statistics[14].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[14].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[14].planMoneyString,(fn:length((mineReportDetails[14].planMoneyString))-2),fn:length((mineReportDetails[14].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[14].planMoney}"/></c:when><c:otherwise>${mineReportDetails[14].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%" class="td_left"><p><strong>15.机械制造企业</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[15].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[15].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[15].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[15].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_15">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[15].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[15].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[15].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[15].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_15">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[15].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(33,1);">${statistics[15].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[15].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(33,2);">${statistics[15].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[15].bigTrouble==0||statistics[15].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[15].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[15].bigTroubleGovern/statistics[15].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[15].bigTroubleGovern/statistics[15].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[15].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(33,3);">${statistics[15].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[15].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(33,4);">${statistics[15].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[15].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(33,5);">${statistics[15].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[15].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(33,6);">${statistics[15].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[15].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(33,7);">${statistics[15].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[15].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[15].planMoneyString,(fn:length((mineReportDetails[15].planMoneyString))-2),fn:length((mineReportDetails[15].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[15].planMoney}"/></c:when><c:otherwise>${mineReportDetails[15].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>16.地铁施工（按项目部统计）</strong></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[9].shouldTroubleshooting==0||mineReportDetails[9].shouldTroubleshooting==null}">&nbsp;</c:when><c:otherwise>${mineReportDetails[9].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[9].company==0||mineReportDetails[9].company==null}">&nbsp;</c:when><c:otherwise>${mineReportDetails[9].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_9">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[9].generalDanger==0||mineReportDetails[9].generalDanger==null}">&nbsp;</c:when><c:otherwise>${mineReportDetails[9].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[9].generalDangerGovern==0||mineReportDetails[9].generalDangerGovern==null}">&nbsp;</c:when><c:otherwise>${mineReportDetails[9].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_9">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[9].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(27,1);">${statistics[9].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[9].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(27,2);">${statistics[9].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[9].bigTrouble==0||statistics[9].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[9].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[9].bigTroubleGovern/statistics[9].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[9].bigTroubleGovern/statistics[9].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[9].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(27,3);">${statistics[9].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[9].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(27,4);">${statistics[9].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[9].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(27,5);">${statistics[9].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[9].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(27,6);">${statistics[9].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[9].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(27,7);">${statistics[9].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[9].planMoney==0.0||mineReportDetails[9].planMoney==null}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[9].planMoneyString,(fn:length((mineReportDetails[9].planMoneyString))-2),fn:length((mineReportDetails[9].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[9].planMoney}"/></c:when><c:otherwise>${mineReportDetails[9].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>17.道路交通事故多发点段</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[17].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[17].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[17].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[17].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_17">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[17].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[17].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[17].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[17].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_17">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[17].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(35,1);">${statistics[17].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[17].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(35,2);">${statistics[17].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[17].bigTrouble==0||statistics[17].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[17].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[17].bigTroubleGovern/statistics[17].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[17].bigTroubleGovern/statistics[17].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[17].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(35,3);">${statistics[17].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[17].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(35,4);">${statistics[17].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[17].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(35,5);">${statistics[17].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[17].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(35,6);">${statistics[17].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[17].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(35,7);">${statistics[17].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[17].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[17].planMoneyString,(fn:length((mineReportDetails[17].planMoneyString))-2),fn:length((mineReportDetails[17].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[17].planMoney}"/></c:when><c:otherwise>${mineReportDetails[17].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>18.道路交通安全设施</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[18].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[18].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[18].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[18].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_18">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[18].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[18].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[18].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[18].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_18">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[18].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(36,1);">${statistics[18].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[18].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(36,2);">${statistics[18].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[18].bigTrouble==0||statistics[18].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[18].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[18].bigTroubleGovern/statistics[18].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[18].bigTroubleGovern/statistics[18].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[18].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(36,3);">${statistics[18].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[18].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(36,4);">${statistics[18].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[18].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(36,5);">${statistics[18].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[18].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(36,6);">${statistics[18].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[18].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(36,7);">${statistics[18].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[18].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[18].planMoneyString,(fn:length((mineReportDetails[18].planMoneyString))-2),fn:length((mineReportDetails[18].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[18].planMoney}"/></c:when><c:otherwise>${mineReportDetails[18].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>19.临水临崖危险路段</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[19].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[19].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[19].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[19].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_19">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[19].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[19].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[19].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[19].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_19">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[19].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(37,1);">${statistics[19].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[19].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(37,2);">${statistics[19].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[19].bigTrouble==0||statistics[19].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[19].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[19].bigTroubleGovern/statistics[19].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[19].bigTroubleGovern/statistics[19].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[19].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(37,3);">${statistics[19].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[19].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(37,4);">${statistics[19].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[19].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(37,5);">${statistics[19].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[19].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(37,6);">${statistics[19].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[19].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(37,7);">${statistics[19].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[19].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[19].planMoneyString,(fn:length((mineReportDetails[19].planMoneyString))-2),fn:length((mineReportDetails[19].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[19].planMoney}"/></c:when><c:otherwise>${mineReportDetails[19].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%" class="td_left"><p><strong>20.城市公共交通</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[20].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[20].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[20].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[20].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_20">&nbsp;</td>
		 <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[20].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[20].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[20].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[20].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_20">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[20].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(38,1);">${statistics[20].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[20].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(38,2);">${statistics[20].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[20].bigTrouble==0||statistics[20].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[20].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[20].bigTroubleGovern/statistics[20].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[20].bigTroubleGovern/statistics[20].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[20].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(38,3);">${statistics[20].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[20].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(38,4);">${statistics[20].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[20].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(38,5);">${statistics[20].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[20].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(38,6);">${statistics[20].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[20].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(38,7);">${statistics[20].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[20].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[20].planMoneyString,(fn:length((mineReportDetails[20].planMoneyString))-2),fn:length((mineReportDetails[20].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[20].planMoney}"/></c:when><c:otherwise>${mineReportDetails[20].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>21.燃气</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[21].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[21].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[21].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[21].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_21">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[21].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[21].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[21].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[21].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_21">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[21].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(39,1);">${statistics[21].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[21].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(39,2);">${statistics[21].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[21].bigTrouble==0||statistics[21].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[21].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[21].bigTroubleGovern/statistics[21].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[21].bigTroubleGovern/statistics[21].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[21].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(39,3);">${statistics[21].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[21].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(39,4);">${statistics[21].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[21].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(39,5);">${statistics[21].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[21].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(39,6);">${statistics[21].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[21].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(39,7);">${statistics[21].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[21].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[21].planMoneyString,(fn:length((mineReportDetails[21].planMoneyString))-2),fn:length((mineReportDetails[21].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[21].planMoney}"/></c:when><c:otherwise>${mineReportDetails[21].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>22.旅游行业</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[22].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[22].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[22].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[22].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_22">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[22].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[22].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[22].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[22].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_22">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[22].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(40,1);">${statistics[22].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[22].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(40,2);">${statistics[22].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[22].bigTrouble==0||statistics[22].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[22].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[22].bigTroubleGovern/statistics[22].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[22].bigTroubleGovern/statistics[22].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[22].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(40,3);">${statistics[22].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[22].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(40,4);">${statistics[22].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[22].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(40,5);">${statistics[22].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[22].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(40,6);">${statistics[22].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[22].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(40,7);">${statistics[22].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[22].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[22].planMoneyString,(fn:length((mineReportDetails[22].planMoneyString))-2),fn:length((mineReportDetails[22].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[22].planMoney}"/></c:when><c:otherwise>${mineReportDetails[22].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>23.铁路</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[23].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[23].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[23].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[23].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_23">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[23].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[23].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[23].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[23].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_23">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[23].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(41,1);">${statistics[23].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[23].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(41,2);">${statistics[23].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[23].bigTrouble==0||statistics[23].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[23].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[23].bigTroubleGovern/statistics[23].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[23].bigTroubleGovern/statistics[23].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[23].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(41,3);">${statistics[23].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[23].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(41,4);">${statistics[23].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[23].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(41,5);">${statistics[23].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[23].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(41,6);">${statistics[23].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[23].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(41,7);">${statistics[23].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[23].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[23].planMoneyString,(fn:length((mineReportDetails[23].planMoneyString))-2),fn:length((mineReportDetails[23].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[23].planMoney}"/></c:when><c:otherwise>${mineReportDetails[23].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>24.医院</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[23].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[23].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[23].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[23].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_24">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[23].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[23].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[23].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[23].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_24">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[24].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(42,1);">${statistics[24].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[24].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(42,2);">${statistics[24].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[24].bigTrouble==0||statistics[24].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[24].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[24].bigTroubleGovern/statistics[24].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[24].bigTroubleGovern/statistics[24].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[24].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(42,3);">${statistics[24].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[24].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(42,4);">${statistics[24].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[24].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(42,5);">${statistics[24].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[24].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(42,6);">${statistics[24].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[24].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(42,7);">${statistics[24].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[24].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[24].planMoneyString,(fn:length((mineReportDetails[24].planMoneyString))-2),fn:length((mineReportDetails[24].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[24].planMoney}"/></c:when><c:otherwise>${mineReportDetails[24].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%" class="td_left"><p><strong>25.“三合一”场所</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[25].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[25].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[25].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[25].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_25">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[25].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[25].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[25].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[25].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_25">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[25].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(43,1);">${statistics[25].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[25].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(43,2);">${statistics[25].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[25].bigTrouble==0||statistics[25].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[25].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[25].bigTroubleGovern/statistics[25].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[25].bigTroubleGovern/statistics[25].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[25].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(43,3);">${statistics[25].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[25].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(43,4);">${statistics[25].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[25].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(43,5);">${statistics[25].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[25].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(43,6);">${statistics[25].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[25].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(43,7);">${statistics[25].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[25].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[25].planMoneyString,(fn:length((mineReportDetails[25].planMoneyString))-2),fn:length((mineReportDetails[25].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[25].planMoney}"/></c:when><c:otherwise>${mineReportDetails[25].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%" class="td_left"><p><strong>26.出租房</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[26].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[26].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[26].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[26].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_26">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[26].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[26].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[26].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[26].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_26">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[26].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(40,1);">${statistics[26].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[26].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(40,2);">${statistics[26].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[26].bigTrouble==0||statistics[26].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[26].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[26].bigTroubleGovern/statistics[26].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[26].bigTroubleGovern/statistics[26].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[26].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(40,3);">${statistics[26].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[26].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(44,4);">${statistics[26].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[26].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(44,5);">${statistics[26].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[26].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(44,6);">${statistics[26].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[26].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(44,7);">${statistics[26].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[26].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[26].planMoneyString,(fn:length((mineReportDetails[26].planMoneyString))-2),fn:length((mineReportDetails[26].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[26].planMoney}"/></c:when><c:otherwise>${mineReportDetails[26].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>27.其他单位</strong></p></td>
		<td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[16].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].shouldTroubleshooting}</c:otherwise></c:choose></td>
		<td align="center" id="company"><c:choose><c:when test="${mineReportDetails[16].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].company}</c:otherwise></c:choose></td>
		<td align="center" id="zg1_16">&nbsp;</td>
		<td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[16].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].generalDanger}</c:otherwise></c:choose></td>
		<td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[16].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].generalDangerGovern}</c:otherwise></c:choose></td>
		<td align="center" id="zg_16">&nbsp;</td>
		<td align="center"><c:choose><c:when test="${statistics[16].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(34,1);">${statistics[16].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[16].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(34,2);">${statistics[16].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[16].bigTrouble==0||statistics[16].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[16].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[16].bigTroubleGovern/statistics[16].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[16].bigTroubleGovern/statistics[16].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[16].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(34,3);">${statistics[16].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[16].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(34,4);">${statistics[16].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[16].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(34,5);">${statistics[16].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[16].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(34,6);">${statistics[16].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[16].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(34,7);">${statistics[16].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[16].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[16].planMoneyString,(fn:length((mineReportDetails[16].planMoneyString))-2),fn:length((mineReportDetails[16].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[16].planMoney}"/></c:when><c:otherwise>${mineReportDetails[16].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
	</tr><tr>
		<td width="18%"><p><strong>合计</strong></p></td>
		<td align="center" id="shouldTroubleshooting_t"></td>
		<td align="center" id="company_t"></td>
		<td align="center" id="zg1_t">&nbsp;</td>
		<td align="center" id="generalDanger_t"></td>
		<td align="center" id="generalDangerGovern_t"></td>
		<td align="center" id="zg_t">&nbsp;</td>
		<td align="center" width="4%"><c:choose><c:when test="${statistics[27].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(0,1);">${statistics[27].bigTrouble}</a></c:otherwise></c:choose></td>
		<td align="center" width="4%"><c:choose><c:when test="${statistics[27].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(0,2);">${statistics[27].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		<td align="center" width="4%"><c:choose><c:when test="${statistics[27].bigTrouble==0||statistics[27].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[27].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[27].bigTroubleGovern/statistics[27].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[27].bigTroubleGovern/statistics[27].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		<td align="center" width="4%"><c:choose><c:when test="${statistics[27].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(0,3);">${statistics[27].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[27].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(0,4);">${statistics[27].wdw}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[27].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(0,5);">${statistics[27].guapaiTotal}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[27].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(0,6);">${statistics[27].provinceGuapai}</a></c:otherwise></c:choose></td>
		<td align="center"><c:choose><c:when test="${statistics[27].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(0,7);">${statistics[27].cityGuapai}</a></c:otherwise></c:choose></td>
		<td align="center" id="planMoney_t"></td>
	</tr><tr>
       <td colspan="16">
	       <table width="100%" border="0"><tr>
			   <td width="15%" nowrap="nowrap">单位负责人：</td>
			   <td width="8%"><input type="text" id="chargeMan" name="mineReport.chargeMan" class="input" maxlength="10" size="10" value="${mineReport.chargeMan}"/></td>
			   <td width="13%" align="center">填表人：</td>
			   <td width="8%"><input type="text" id="fillMan" name="mineReport.fillMan" class="input" maxlength="10" size="10" value="${mineReport.fillMan}"/></td>
			   <td width="13%" align="center">联系电话：</td>
			   <td width="15%"><input type="text" id="tel" name="mineReport.tel" class="input" maxlength="13" size="13" style="ime-mode:disabled;" value="${mineReport.tel}"/><ui:v for="tel" rule="phone" require="false" tips="格式为0574-87364008" warn="您输入的电话号码不存在或格式不正确"/></td>
			   <td width="16%" align="center">填报日期：</td>
			   <td width="12%"><input type="text" id="fillDate" name="fillDate" class="input" maxlength="10" size="10" onKeyUp="AutoFillDate(this,10);" style="ime-mode:disabled;" value="<s:date name='mineReport.fillDate' format='yyyy-MM-dd' />" onMouseDown="checkDateOnMouse(this)"/><a href="javascript:void()" onFocus="this.blur();"><img id="cal" src="${resourcePath}/img/cal.gif" width="20" height="17" onClick="calendar(get('fillDate'));" border="0" title="日历控件"/></a>
			   <ui:v for="fillDate" rule="date" require="false" tips="格式为2000-01-01" warn="您输入的日期不存在或格式不正确"/></td>
		   </tr></table>
	   </td>
    </tr>	
  </table></td></tr></table>
   <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>		 
     <td width="28%" align="center">
     <input id="sub" type="button" class="but_2" value="保  存"  onclick="submitCreate();"/>
     <input type="button" class="but_2" onClick="window.location='loadProvinceReport.xhtml?mineReport.type=${mineReport.type}&mineReport.reportMonth=${mineReport.reportMonth}&mineReport.print=true&mineReport.id=${mineReport.id}';" value="打  印" />
     &nbsp;&nbsp;<input type="button" class="but_2" value="返  回"  onclick="history.back(-1);"/> 
	 <input type="button" name="back" class="but_2" onClick="tableToExcel();" value="导入到EXCEL" />
    </td>
    </tr>
</table>
 	<input type="hidden" id="type" name="mineReport.type" value="2"/>
	 <input type="hidden" id="isProvince" name="mineReport.province" value="true"/>
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
		//显示填报月份
		<c:if test="${!empty(mineReport.reportMonth)}">
			var year = "${mineReport.reportMonth}".split("-")[0];
			var month = "${mineReport.reportMonth}".split("-")[1];
			get("year").value=year;
			get("month").value=month;
		</c:if>	
		<c:if test="${mineReport.state>0}">
			for(var i=0;i<document.getElementsByTagName("input").length;i++) {
				if (document.getElementsByTagName("input")[i].type=="text") {
					document.getElementsByTagName("input")[i].disabled = true;
				}
			}
			get("sub").style.display = "none";
			get("year").disabled = true;
			get("month").disabled = true;
			get("cal").style.display = "none";
		</c:if>
	</c:otherwise>
</c:choose>
<c:if test="${!empty(reportMonth)}">
	var year = "${reportMonth}".split("-")[0];
	var month = "${reportMonth}".split("-")[1];
	get("year").value=year;
	get("month").value=month;
</c:if>	
if(get("month").value == ""){
	get("month").value=0;
}
//合计
sumSons(get('shouldTroubleshooting_t'),getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);
sumSons(get('company_t'),getAll('company'),get('company_t'),true);
sumSons(get('generalDanger_t'),getAll('generalDanger'),get('generalDanger_t'),true);
sumSons(get('generalDangerGovern_t'),getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);
sumSons(get('planMoney_t'),getAll('planMoney'),get('planMoney_t'),false);

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