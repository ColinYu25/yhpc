<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/header.jsp" %>
<script type="text/javascript">
var formValidator=Validator.setup({   
	form : 'mineForm',
	configs : 'attribute,tag',
	warns : 'color,alert',
	color : {warn :'black', pass:'black'}
});  
function submitCreate(){
	  if(formValidator.validate()){
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
		  if(!chkNull(obj.chargeMan,'请填写单位负责人')){
		  	 return false;
		  }else if(!chkNull(obj.fillMan,'请填写填表人')){
		  	 return false;
		  }else if(!chkNull(obj.tel,'请填写联系电话')){
		  	 return false;
		  }else  if(!chkNull(obj.fillDate,'请填写填报日期')){
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
</form>
<form name="mineForm" id="mineForm" method="post">
  <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" id="tb">
    <tr>
      <td>
	  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
		     <th colspan="2" height="50" class="head">矿山、危化、烟花爆竹、冶金等行业和领域安全生产隐患排查治理情况报表</th>
        </tr>
		<tr style="font-size:12px">
		  <td height="18" width="80%">填报单位（章）:${fillUnit}&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red; font-size:12px;">（如填报单位名称不对，请到个人信息修改！）</span></td>
		  <td height="18" width="20%" align="right" nowrap="nowrap" valign="bottom">
		  截止到<select id="year" name="mineReport.year" style="width:55px;">
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
		<tr>
	  <td width="18%" class="td_left"><p><strong>1.金属非金属矿山企业</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[0].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[0].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_0">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[0].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[0].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[0].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_0">&nbsp;</td>
	  <td align="center" id="bt"><c:choose><c:when test="${statistics[0].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(1,1);">${statistics[0].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" id="btg"><c:choose><c:when test="${statistics[0].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(1,2);">${statistics[0].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center">
	  <c:choose><c:when test="${statistics[0].bigTrouble==0||statistics[0].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[0].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[0].bigTroubleGovern/statistics[0].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[0].bigTroubleGovern/statistics[0].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" id="btng"><c:choose><c:when test="${statistics[0].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(1,3);">${statistics[0].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" id="wdw"><c:choose><c:when test="${statistics[0].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(1,4);">${statistics[0].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" id="gpt"><c:choose><c:when test="${statistics[0].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(1,5);">${statistics[0].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" id="pgp"><c:choose><c:when test="${statistics[0].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(1,6);">${statistics[0].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="cgp"><c:choose><c:when test="${statistics[0].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(1,7);">${statistics[0].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[0].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[0].planMoneyString,(fn:length((mineReportDetails[0].planMoneyString))-2),fn:length((mineReportDetails[0].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[0].planMoney}"/></c:when><c:otherwise>${mineReportDetails[0].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td width="18%"><p><strong>2.尾矿库</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[1].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[1].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_1">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[1].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[1].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[1].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_1">&nbsp;</td>
	  <td align="center" id="bt"><c:choose><c:when test="${statistics[1].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(2,1);">${statistics[1].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" id="btg"><c:choose><c:when test="${statistics[1].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(2,2);">${statistics[1].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center">
	  <c:choose><c:when test="${statistics[1].bigTrouble==0||statistics[1].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[1].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[1].bigTroubleGovern/statistics[1].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[1].bigTroubleGovern/statistics[1].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" id="btng"><c:choose><c:when test="${statistics[1].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(2,3);">${statistics[1].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" id="wdw"><c:choose><c:when test="${statistics[1].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(2,4);">${statistics[1].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" id="gpt"><c:choose><c:when test="${statistics[1].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(2,5);">${statistics[1].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" id="pgp"><c:choose><c:when test="${statistics[1].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(2,6);">${statistics[1].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="cgp"><c:choose><c:when test="${statistics[1].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(2,7);">${statistics[1].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[1].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[1].planMoneyString,(fn:length((mineReportDetails[1].planMoneyString))-2),fn:length((mineReportDetails[1].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[1].planMoney}"/></c:when><c:otherwise>${mineReportDetails[1].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td width="18%"><p><strong>3.冶金企业</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[2].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[2].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_2">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[2].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[2].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[2].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_2">&nbsp;</td>
	  <td align="center" id="bt"><c:choose><c:when test="${statistics[2].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(3,1);">${statistics[2].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" id="btg"><c:choose><c:when test="${statistics[2].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(3,2);">${statistics[2].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center">
	  <c:choose><c:when test="${statistics[2].bigTrouble==0||statistics[2].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[2].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[2].bigTroubleGovern/statistics[2].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[2].bigTroubleGovern/statistics[2].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" id="btng"><c:choose><c:when test="${statistics[2].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(3,3);">${statistics[2].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" id="wdw"><c:choose><c:when test="${statistics[2].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(3,4);">${statistics[2].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" id="gpt"><c:choose><c:when test="${statistics[2].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(3,5);">${statistics[2].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" id="pgp"><c:choose><c:when test="${statistics[2].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(3,6);">${statistics[2].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="cgp"><c:choose><c:when test="${statistics[2].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(3,7);">${statistics[2].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[2].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[2].planMoneyString,(fn:length((mineReportDetails[2].planMoneyString))-2),fn:length((mineReportDetails[2].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[2].planMoney}"/></c:when><c:otherwise>${mineReportDetails[2].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td width="18%"><p><strong>4.有色企业</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[3].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[3].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[3].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[3].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_3">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[3].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[3].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[3].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[3].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_3">&nbsp;</td>
	  <td align="center" id="bt"><c:choose><c:when test="${statistics[3].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(4,1);">${statistics[3].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" id="btg"><c:choose><c:when test="${statistics[3].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(4,2);">${statistics[3].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center">
	  <c:choose><c:when test="${statistics[3].bigTrouble==0||statistics[3].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[3].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[3].bigTroubleGovern/statistics[3].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[3].bigTroubleGovern/statistics[3].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" id="btng"><c:choose><c:when test="${statistics[3].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(4,3);">${statistics[3].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" id="wdw"><c:choose><c:when test="${statistics[3].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(4,4);">${statistics[3].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" id="gpt"><c:choose><c:when test="${statistics[3].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(4,5);">${statistics[3].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" id="pgp"><c:choose><c:when test="${statistics[3].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(4,6);">${statistics[3].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="cgp"><c:choose><c:when test="${statistics[3].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(4,7);">${statistics[3].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[3].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[3].planMoneyString,(fn:length((mineReportDetails[3].planMoneyString))-2),fn:length((mineReportDetails[3].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[3].planMoney}"/></c:when><c:otherwise>${mineReportDetails[3].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
  <td width="18%"><p><strong>5.重大基础设施</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[4].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[4].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[4].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[4].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_4">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[4].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[4].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[4].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[4].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_4">&nbsp;</td>
	  <td align="center" id="bt"><c:choose><c:when test="${statistics[4].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(5,1);">${statistics[4].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" id="btg"><c:choose><c:when test="${statistics[4].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(5,2);">${statistics[4].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center">
	  <c:choose><c:when test="${statistics[4].bigTrouble==0||statistics[4].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[4].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[4].bigTroubleGovern/statistics[4].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[4].bigTroubleGovern/statistics[4].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" id="btng"><c:choose><c:when test="${statistics[4].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(5,3);">${statistics[4].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" id="wdw"><c:choose><c:when test="${statistics[4].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(5,4);">${statistics[4].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" id="gpt"><c:choose><c:when test="${statistics[4].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(5,5);">${statistics[4].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" id="pgp"><c:choose><c:when test="${statistics[4].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(5,6);">${statistics[4].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="cgp"><c:choose><c:when test="${statistics[4].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(5,7);">${statistics[4].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[4].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[4].planMoneyString,(fn:length((mineReportDetails[4].planMoneyString))-2),fn:length((mineReportDetails[4].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[4].planMoney}"/></c:when><c:otherwise>${mineReportDetails[4].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>
	  
	  
	  <tr>
  <td width="18%"><p><strong>6.危险化学品企业</strong></p></td>
  	  <td align="center" width="4%" id="shouldTroubleshooting_6"></td>
	  <td align="center" id="company_6"></td>
	  <td align="center" id="zg1_n_6">&nbsp;</td>
	  <td align="center" width="4%" id="generalDanger_6"></td>
	  <td align="center" id="generalDangerGovern_6"></td>
	  <td align="center" id="zg_n_6">&nbsp;</td>
	  <td align="center" id="bt_6">0</td>
	  <td align="center" id="btg_6">0</td>
	  <td align="center" id="bt_r_6">&nbsp;</td>
	  <td align="center" id="btng_6">0</td>
	  <td align="center" id="wdw_6">0</td>
	  <td align="center" id="gpt_6">0</td>
	  <td align="center" id="pgp_6">0</td>
	  <td align="center" id="cgp_6">0</td>
	  <td align="center" id="planMoney_6"></td></tr><tr>
	<td width="18%"><p>(1)生产企业</p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[6].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[6].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_6">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[6].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[6].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[6].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_6">&nbsp;</td>
	  <td align="center" id="bt"><c:choose><c:when test="${statistics[6].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(7,1);">${statistics[6].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" id="btg"><c:choose><c:when test="${statistics[6].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(7,2);">${statistics[6].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center">
	  <c:choose><c:when test="${statistics[6].bigTrouble==0||statistics[6].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[6].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[6].bigTroubleGovern/statistics[6].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[6].bigTroubleGovern/statistics[6].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" id="btng"><c:choose><c:when test="${statistics[6].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(7,3);">${statistics[6].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" id="wdw"><c:choose><c:when test="${statistics[6].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(7,4);">${statistics[6].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" id="gpt"><c:choose><c:when test="${statistics[6].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(7,5);">${statistics[6].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" id="pgp"><c:choose><c:when test="${statistics[6].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(7,6);">${statistics[6].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="cgp"><c:choose><c:when test="${statistics[6].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(7,7);">${statistics[6].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[6].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[6].planMoneyString,(fn:length((mineReportDetails[6].planMoneyString))-2),fn:length((mineReportDetails[6].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[6].planMoney}"/></c:when><c:otherwise>${mineReportDetails[6].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
  <td width="18%"><p>(2)经营企业和单位</p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[7].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[7].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_7">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[7].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[7].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[7].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_7">&nbsp;</td>
	  <td align="center" id="bt"><c:choose><c:when test="${statistics[7].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(8,1);">${statistics[7].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" id="btg"><c:choose><c:when test="${statistics[7].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(8,2);">${statistics[7].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center">
	  <c:choose><c:when test="${statistics[7].bigTrouble==0||statistics[7].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[7].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[7].bigTroubleGovern/statistics[7].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[7].bigTroubleGovern/statistics[7].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" id="btng"><c:choose><c:when test="${statistics[7].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(8,3);">${statistics[7].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" id="wdw"><c:choose><c:when test="${statistics[7].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(8,4);">${statistics[7].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" id="gpt"><c:choose><c:when test="${statistics[7].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(8,5);">${statistics[7].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" id="pgp"><c:choose><c:when test="${statistics[7].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(8,6);">${statistics[7].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="cgp"><c:choose><c:when test="${statistics[7].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(8,7);">${statistics[7].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[7].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[7].planMoneyString,(fn:length((mineReportDetails[7].planMoneyString))-2),fn:length((mineReportDetails[7].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[7].planMoney}"/></c:when><c:otherwise>${mineReportDetails[7].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	<td width="18%"><p>(3)存储企业和单位(仓储业)</p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[8].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[8].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[8].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[8].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_8">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[8].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[8].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[8].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[8].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_8">&nbsp;</td>
	  <td align="center" id="bt"><c:choose><c:when test="${statistics[8].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(9,1);">${statistics[8].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" id="btg"><c:choose><c:when test="${statistics[8].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(9,2);">${statistics[8].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center">
	  <c:choose><c:when test="${statistics[8].bigTrouble==0||statistics[8].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[8].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[8].bigTroubleGovern/statistics[8].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[8].bigTroubleGovern/statistics[8].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" id="btng"><c:choose><c:when test="${statistics[8].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(9,3);">${statistics[8].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" id="wdw"><c:choose><c:when test="${statistics[8].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(9,4);">${statistics[8].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" id="gpt"><c:choose><c:when test="${statistics[8].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(9,5);">${statistics[8].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" id="pgp"><c:choose><c:when test="${statistics[8].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(9,6);">${statistics[8].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="cgp"><c:choose><c:when test="${statistics[8].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(9,7);">${statistics[8].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[8].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[8].planMoneyString,(fn:length((mineReportDetails[8].planMoneyString))-2),fn:length((mineReportDetails[8].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[8].planMoney}"/></c:when><c:otherwise>${mineReportDetails[8].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	  <td width="18%"><p>(4)使用危化品的生产单位</p></td>
	  <td align="center" id="shouldTroubleshooting_6_4"></td>
	  <td align="center" id="company_6_4"></td>
	  <td align="center" id="zg1_n_6_4">&nbsp;</td>
	  <td align="center" id="generalDanger_6_4"></td>
	  <td align="center" id="generalDangerGovern_6_4"></td>
	  <td align="center" id="zg_n_6_4">&nbsp;</td>
	  <td align="center" id="bt_6_4">0</td>
	  <td align="center" id="btg_6_4">0</td>
	  <td align="center" id="bt_r_6_4">&nbsp;</td>
	  <td align="center" id="btng_6_4">0</td>
	  <td align="center" id="wdw_6_4">0</td>
	  <td align="center" id="gpt_6_4">0</td>
	  <td align="center" id="pgp_6_4">0</td>
	  <td align="center" id="cgp_6_4">0</td>
	  <td align="center" id="planMoney_6_4"></td></tr><tr>
	<td width="18%"><p>   使用危化品的化工企业</p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[9].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[9].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[9].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[9].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_9">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[9].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[9].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[9].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[9].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_9">&nbsp;</td>
	  <td align="center" id="bt"><c:choose><c:when test="${statistics[9].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(10,1);">${statistics[9].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" id="btg"><c:choose><c:when test="${statistics[9].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(10,2);">${statistics[9].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center">
	  <c:choose><c:when test="${statistics[9].bigTrouble==0||statistics[9].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[9].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[9].bigTroubleGovern/statistics[9].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[9].bigTroubleGovern/statistics[9].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" id="btng"><c:choose><c:when test="${statistics[9].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(10,3);">${statistics[9].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" id="wdw"><c:choose><c:when test="${statistics[9].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(10,4);">${statistics[9].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" id="gpt"><c:choose><c:when test="${statistics[9].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(10,5);">${statistics[9].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" id="pgp"><c:choose><c:when test="${statistics[9].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(10,6);">${statistics[9].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="cgp"><c:choose><c:when test="${statistics[9].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(10,7);">${statistics[9].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[9].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[9].planMoneyString,(fn:length((mineReportDetails[9].planMoneyString))-2),fn:length((mineReportDetails[9].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[9].planMoney}"/></c:when><c:otherwise>${mineReportDetails[9].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
	  <td width="18%"><p>   使用危化品的医药生产企业</p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[10].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[10].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[10].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[10].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_10">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[10].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[10].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[10].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[10].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_10">&nbsp;</td>
	  <td align="center" id="bt"><c:choose><c:when test="${statistics[10].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(11,1);">${statistics[10].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" id="btg"><c:choose><c:when test="${statistics[10].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(11,2);">${statistics[10].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center">
	  <c:choose><c:when test="${statistics[10].bigTrouble==0||statistics[10].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[10].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[10].bigTroubleGovern/statistics[10].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[10].bigTroubleGovern/statistics[10].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" id="btng"><c:choose><c:when test="${statistics[10].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(11,3);">${statistics[10].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" id="wdw"><c:choose><c:when test="${statistics[10].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(11,4);">${statistics[10].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" id="gpt"><c:choose><c:when test="${statistics[10].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(11,5);">${statistics[10].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" id="pgp"><c:choose><c:when test="${statistics[10].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(11,6);">${statistics[10].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="cgp"><c:choose><c:when test="${statistics[10].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(11,7);">${statistics[10].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[10].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[10].planMoneyString,(fn:length((mineReportDetails[10].planMoneyString))-2),fn:length((mineReportDetails[10].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[10].planMoney}"/></c:when><c:otherwise>${mineReportDetails[10].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
		<td width="18%"><p>   使用危化品的其他企业或单位</p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[11].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[11].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[11].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[11].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_11">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[11].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[11].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[11].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[11].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_11">&nbsp;</td>
	  <td align="center" id="bt"><c:choose><c:when test="${statistics[11].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(12,1);">${statistics[11].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" id="btg"><c:choose><c:when test="${statistics[11].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(12,2);">${statistics[11].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center">
	  <c:choose><c:when test="${statistics[11].bigTrouble==0||statistics[11].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[11].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[11].bigTroubleGovern/statistics[11].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[11].bigTroubleGovern/statistics[11].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" id="btng"><c:choose><c:when test="${statistics[11].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(12,3);">${statistics[11].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" id="wdw"><c:choose><c:when test="${statistics[11].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(12,4);">${statistics[11].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" id="gpt"><c:choose><c:when test="${statistics[11].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(12,5);">${statistics[11].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" id="pgp"><c:choose><c:when test="${statistics[11].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(12,6);">${statistics[11].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="cgp"><c:choose><c:when test="${statistics[11].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(12,7);">${statistics[11].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[11].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[11].planMoneyString,(fn:length((mineReportDetails[11].planMoneyString))-2),fn:length((mineReportDetails[11].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[11].planMoney}"/></c:when><c:otherwise>${mineReportDetails[11].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr><tr>
		  <td width="18%"><p>(5)道路运输企业和单位</p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[12].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[12].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_12">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[12].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[12].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[12].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_12">&nbsp;</td>
	  <td align="center" id="bt"><c:choose><c:when test="${statistics[12].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(13,1);">${statistics[12].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" id="btg"><c:choose><c:when test="${statistics[12].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(13,2);">${statistics[12].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center">
	  <c:choose><c:when test="${statistics[12].bigTrouble==0||statistics[12].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[12].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[12].bigTroubleGovern/statistics[12].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[12].bigTroubleGovern/statistics[12].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" id="btng"><c:choose><c:when test="${statistics[12].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(13,3);">${statistics[12].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" id="wdw"><c:choose><c:when test="${statistics[12].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(13,4);">${statistics[12].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" id="gpt"><c:choose><c:when test="${statistics[12].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(13,5);">${statistics[12].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" id="pgp"><c:choose><c:when test="${statistics[12].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(13,6);">${statistics[12].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="cgp"><c:choose><c:when test="${statistics[12].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(13,7);">${statistics[12].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[12].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[12].planMoneyString,(fn:length((mineReportDetails[12].planMoneyString))-2),fn:length((mineReportDetails[12].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[12].planMoney}"/></c:when><c:otherwise>${mineReportDetails[12].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>
	  <tr>
		<td width="18%"><p><strong>7.烟花爆竹企业</strong></p></td>
	  <td align="center" id="shouldTroubleshooting_7"></td>
	  <td align="center" id="company_7"></td>
	  <td align="center" id="zg1_n_7">&nbsp;</td>
	  <td align="center" id="generalDanger_7"></td>
	  <td align="center" id="generalDangerGovern_7"></td>
	  <td align="center" id="zg_n_7">&nbsp;</td>
	  <td align="center" id="bt_7">0</td>
	  <td align="center" id="btg_7">0</td>
	  <td align="center" id="bt_r_7">&nbsp;</td>
	  <td align="center" id="btng_7">0</td>
	  <td align="center" id="wdw_7">0</td>
	  <td align="center" id="gpt_7">0</td>
	  <td align="center" id="pgp_7">0</td>
	  <td align="center" id="cgp_7">0</td>
	  <td align="center" id="planMoney_7"></td></tr>
	<tr>
	<td width="18%"><p>(1)生产企业</p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[13].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[13].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_13">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[13].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[13].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[13].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_13">&nbsp;</td>
	  <td align="center" id="bt"><c:choose><c:when test="${statistics[13].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(14,1);">${statistics[13].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" id="btg"><c:choose><c:when test="${statistics[13].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(14,2);">${statistics[13].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center">
	  <c:choose><c:when test="${statistics[13].bigTrouble==0||statistics[13].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[13].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[13].bigTroubleGovern/statistics[13].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[13].bigTroubleGovern/statistics[13].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" id="btng"><c:choose><c:when test="${statistics[13].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(14,3);">${statistics[13].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" id="wdw"><c:choose><c:when test="${statistics[13].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(14,4);">${statistics[13].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" id="gpt"><c:choose><c:when test="${statistics[13].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(14,5);">${statistics[13].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" id="pgp"><c:choose><c:when test="${statistics[13].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(14,6);">${statistics[13].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="cgp"><c:choose><c:when test="${statistics[13].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(14,7);">${statistics[13].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[13].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[13].planMoneyString,(fn:length((mineReportDetails[13].planMoneyString))-2),fn:length((mineReportDetails[13].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[13].planMoney}"/></c:when><c:otherwise>${mineReportDetails[13].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>
	<tr>
	  <td><p>(2)经营(批发)企业</p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[14].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[14].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[14].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[14].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_14">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[14].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[14].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[14].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[14].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_14">&nbsp;</td>
	  <td align="center" id="bt"><c:choose><c:when test="${statistics[14].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(15,1);">${statistics[14].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" id="btg"><c:choose><c:when test="${statistics[14].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(15,2);">${statistics[14].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center">
	  <c:choose><c:when test="${statistics[14].bigTrouble==0||statistics[14].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[14].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[14].bigTroubleGovern/statistics[14].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[14].bigTroubleGovern/statistics[14].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" id="btng"><c:choose><c:when test="${statistics[14].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(15,3);">${statistics[14].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" id="wdw"><c:choose><c:when test="${statistics[14].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(15,4);">${statistics[14].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" id="gpt"><c:choose><c:when test="${statistics[14].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(15,5);">${statistics[14].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" id="pgp"><c:choose><c:when test="${statistics[14].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(15,6);">${statistics[14].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="cgp"><c:choose><c:when test="${statistics[14].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(15,7);">${statistics[14].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[14].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[14].planMoneyString,(fn:length((mineReportDetails[14].planMoneyString))-2),fn:length((mineReportDetails[14].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[14].planMoney}"/></c:when><c:otherwise>${mineReportDetails[14].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
          </tr>
		<tr>
		  <td><p>(3)经营(零售)企业</p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[15].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[15].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[15].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[15].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_15">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[15].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[15].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[15].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[15].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_15">&nbsp;</td>
	  <td align="center" id="bt"><c:choose><c:when test="${statistics[15].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(16,1);">${statistics[15].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" id="btg"><c:choose><c:when test="${statistics[15].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(16,2);">${statistics[15].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center">
	  <c:choose><c:when test="${statistics[15].bigTrouble==0||statistics[15].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[15].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[15].bigTroubleGovern/statistics[15].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[15].bigTroubleGovern/statistics[15].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" id="btng"><c:choose><c:when test="${statistics[15].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(16,3);">${statistics[15].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" id="wdw"><c:choose><c:when test="${statistics[15].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(16,4);">${statistics[15].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" id="gpt"><c:choose><c:when test="${statistics[15].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(16,5);">${statistics[15].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" id="pgp"><c:choose><c:when test="${statistics[15].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(16,6);">${statistics[15].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="cgp"><c:choose><c:when test="${statistics[15].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(16,7);">${statistics[15].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[15].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[15].planMoneyString,(fn:length((mineReportDetails[15].planMoneyString))-2),fn:length((mineReportDetails[15].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[15].planMoney}"/></c:when><c:otherwise>${mineReportDetails[15].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
          </tr>
          
          
           <tr>
	<td width="18%"><p><strong>8.船舶修造企业</strong></p></td>
	  <td align="center" id="shouldTroubleshooting_5"><c:choose><c:when test="${mineReportDetails[5].shouldTroubleshooting==0||mineReportDetails[5].shouldTroubleshooting==null}">&nbsp;</c:when><c:otherwise>${mineReportDetails[5].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company_5"><c:choose><c:when test="${mineReportDetails[5].company==0||mineReportDetails[5].company==null}">&nbsp;</c:when><c:otherwise>${mineReportDetails[5].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_5">&nbsp;</td>
	  <td align="center" id="generalDanger_5"><c:choose><c:when test="${mineReportDetails[5].generalDanger==0||mineReportDetails[5].generalDanger==null}">&nbsp;</c:when><c:otherwise>${mineReportDetails[5].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern_5"><c:choose><c:when test="${mineReportDetails[5].generalDangerGovern==0||mineReportDetails[5].generalDangerGovern==null}">&nbsp;</c:when><c:otherwise>${mineReportDetails[5].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_5">&nbsp;</td>
	  <td align="center" id="bt"><c:choose><c:when test="${statistics[5].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(6,1);">${statistics[5].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" id="btg"><c:choose><c:when test="${statistics[5].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(5,2);">${statistics[5].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center">
	  <c:choose><c:when test="${statistics[5].bigTrouble==0||statistics[5].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[5].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[5].bigTroubleGovern/statistics[5].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[5].bigTroubleGovern/statistics[5].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" id="btng"><c:choose><c:when test="${statistics[5].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(6,3);">${statistics[5].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" id="wdw"><c:choose><c:when test="${statistics[5].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(6,4);">${statistics[5].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" id="gpt"><c:choose><c:when test="${statistics[5].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(6,5);">${statistics[5].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" id="pgp"><c:choose><c:when test="${statistics[5].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(6,6);">${statistics[5].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="cgp"><c:choose><c:when test="${statistics[5].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(6,7);">${statistics[5].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="planMoney_5"><c:choose><c:when test="${mineReportDetails[5].planMoney==0.0||mineReportDetails[5].planMoney==null}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[5].planMoneyString,(fn:length((mineReportDetails[5].planMoneyString))-2),fn:length((mineReportDetails[5].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[5].planMoney}"/></c:when><c:otherwise>${mineReportDetails[5].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>
	 
          
          
		<tr>
			<td width="18%"><p><strong>9.其他企业</strong></p></td>
	  <td align="center" id="shouldTroubleshooting"><c:choose><c:when test="${mineReportDetails[16].shouldTroubleshooting==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].shouldTroubleshooting}</c:otherwise></c:choose></td>
	  <td align="center" id="company"><c:choose><c:when test="${mineReportDetails[16].company==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].company}</c:otherwise></c:choose></td>
	  <td align="center" id="zg1_16">&nbsp;</td>
	  <td align="center" id="generalDanger"><c:choose><c:when test="${mineReportDetails[16].generalDanger==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].generalDanger}</c:otherwise></c:choose></td>
	  <td align="center" id="generalDangerGovern"><c:choose><c:when test="${mineReportDetails[16].generalDangerGovern==0}">&nbsp;</c:when><c:otherwise>${mineReportDetails[16].generalDangerGovern}</c:otherwise></c:choose></td>
	  <td align="center" id="zg_16">&nbsp;</td>
	  <td align="center" id="bt"><c:choose><c:when test="${statistics[16].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(17,1);">${statistics[16].bigTrouble}</a></c:otherwise></c:choose></td>
	  <td align="center" id="btg"><c:choose><c:when test="${statistics[16].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(17,2);">${statistics[16].bigTroubleGovern}</a></c:otherwise></c:choose></td>
	  <td align="center">
	  <c:choose><c:when test="${statistics[16].bigTrouble==0||statistics[16].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[16].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[16].bigTroubleGovern/statistics[16].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[16].bigTroubleGovern/statistics[16].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" id="btng"><c:choose><c:when test="${statistics[16].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(17,3);">${statistics[16].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" id="wdw"><c:choose><c:when test="${statistics[16].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(17,4);">${statistics[16].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" id="gpt"><c:choose><c:when test="${statistics[16].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(17,5);">${statistics[16].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" id="pgp"><c:choose><c:when test="${statistics[16].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(17,6);">${statistics[16].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="cgp"><c:choose><c:when test="${statistics[16].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(17,7);">${statistics[16].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[16].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[16].planMoneyString,(fn:length((mineReportDetails[16].planMoneyString))-2),fn:length((mineReportDetails[16].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[16].planMoney}"/></c:when><c:otherwise>${mineReportDetails[16].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td></tr>
		<tr><td><p><strong>合计</strong></p></td>
	  <td align="center" id="shouldTroubleshooting_t"></td>
	  <td align="center" id="company_t"></td>
	  <td align="center" id="zg1_t">&nbsp;</td>
	  <td align="center" id="generalDanger_t"></td>
	  <td align="center" id="generalDangerGovern_t"></td>
	  <td align="center" id="zg_t">&nbsp;</td>
	  <td align="center"><a href="javaScript:;" onClick="openWindow(0,1);">${statistics[17].bigTrouble}</a></td>
	  <td align="center"><a href="javaScript:;" onClick="openWindow(0,2);">${statistics[17].bigTroubleGovern}</a></td>
	  <td align="center"><c:choose><c:when test="${statistics[17].bigTrouble==0||statistics[17].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[17].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[17].bigTroubleGovern/statistics[17].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[17].bigTroubleGovern/statistics[17].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
	  <td align="center" width="4%"><c:choose><c:when test="${statistics[17].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(0,3);">${statistics[17].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
	  <td align="center" id="wdw"><c:choose><c:when test="${statistics[17].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(0,4);">${statistics[17].wdw}</a></c:otherwise></c:choose></td>
	  <td align="center" id="gpt"><c:choose><c:when test="${statistics[17].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(0,5);">${statistics[17].guapaiTotal}</a></c:otherwise></c:choose></td>
	  <td align="center" id="pgp"><c:choose><c:when test="${statistics[17].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(0,6);">${statistics[17].provinceGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="cgp"><c:choose><c:when test="${statistics[17].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(0,7);">${statistics[17].cityGuapai}</a></c:otherwise></c:choose></td>
	  <td align="center" id="planMoney_t"></td>
	  </tr>
  	<tr>
       <td colspan="16"><table width="100%" border="0"><tr>
	   <td width="15%" nowrap="nowrap">单位负责人：	     </td>
	   <td width="8%"><input type="text" id="chargeMan" name="mineReport.chargeMan" class="input" maxlength="10" size="10" value="${mineReport.chargeMan}"/></td>
	   <td width="13%" align="center">填表人：	     </td>
	   <td width="8%"><input type="text" id="fillMan" name="mineReport.fillMan" class="input" maxlength="10" size="10" value="${mineReport.fillMan}"/></td>
	   <td width="13%" align="center">联系电话：	     </td>
	   <td width="15%"><input type="text" id="tel" name="mineReport.tel" class="input" maxlength="13" size="13" style="ime-mode:disabled;" value="${mineReport.tel}"/><ui:v for="tel" rule="phone" require="false" tips="格式为0574-87364008" warn="您输入的电话号码不存在或格式不正确"/></td>
	   <td width="16%" align="center">填报日期：	     </td>
	   <td width="12%"><input type="text" id="fillDate" name="fillDate" class="input" maxlength="10" size="10" onKeyUp="AutoFillDate(this,10);" style="ime-mode:disabled;" value="<s:date name='mineReport.fillDate' format='yyyy-MM-dd' />" onMouseDown="checkDateOnMouse(this)"/><a href="javascript:void(0);" onFocus="this.blur();"><img id="cal" src="${resourcePath}/img/cal.gif" onclick="calendar(get('fillDate'))" width="20" height="17" border="0" title="日历控件"/></a>
	   <ui:v for="fillDate" rule="date" require="false" tips="格式为2000-01-01" warn="您输入的日期不存在或格式不正确"/></td>
	   </tr></table></td>
    </tr>	
  </table>
  
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>		 
     <td width="28%" align="center">
     <input id="sub" type="button" class="but_2" value="保  存"  onclick="submitCreate();"/>
     <input type="button" class="but_2" onClick="window.location='loadProvinceReport.xhtml?mineReport.type=${mineReport.type}&mineReport.reportMonth=${mineReport.reportMonth}&mineReport.print=true&mineReport.id=${mineReport.id}';" value="打  印" />
     &nbsp;&nbsp;<input type="button" class="but_2" value="返  回"  onclick="history.back(-1);"/>
	 <input type="button" name="back" class="but_2" onClick="tableToExcel();" value="导入到EXCEL" />
	 <input type="hidden" id="type" name="mineReport.type" value="1"/>
	 <input type="hidden" id="id" name="mineReport.id" value="${mineReport.id}" disabled="true"/>
        </td>
    </tr>
</table>
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
//6.危险化学品企业		
sumSon(getAll('shouldTroubleshooting')[11],getAll('shouldTroubleshooting')[5],getAll('shouldTroubleshooting')[6],getAll('shouldTroubleshooting')[7],getAll('shouldTroubleshooting')[8],getAll('shouldTroubleshooting')[9],getAll('shouldTroubleshooting')[10],get('shouldTroubleshooting_6'),true);
sumSon(getAll('company')[11],getAll('company')[5],getAll('company')[6],getAll('company')[7],getAll('company')[8],getAll('company')[9],getAll('company')[10],get('company_6'),true);
sumSon(getAll('generalDanger')[11],getAll('generalDanger')[5],getAll('generalDanger')[6],getAll('generalDanger')[7],getAll('generalDanger')[8],getAll('generalDanger')[9],getAll('generalDanger')[10],get('generalDanger_6'),true);
sumSon(getAll('generalDangerGovern')[11],getAll('generalDangerGovern')[5],getAll('generalDangerGovern')[6],getAll('generalDangerGovern')[7],getAll('generalDangerGovern')[8],getAll('generalDangerGovern')[9],getAll('generalDangerGovern')[10],get('generalDangerGovern_6'),true);
sumSon(getAll('planMoney')[11],getAll('planMoney')[5],getAll('planMoney')[6],getAll('planMoney')[7],getAll('planMoney')[8],getAll('planMoney')[9],getAll('planMoney')[10],get('planMoney_6'),false);
//(4)使用危化品的生产单位
sumSon(getAll('shouldTroubleshooting')[10],getAll('shouldTroubleshooting')[8],getAll('shouldTroubleshooting')[9],get('shouldTroubleshooting_6_4'),true);
sumSon(getAll('company')[10],getAll('company')[8],getAll('company')[9],get('company_6_4'),true);
sumSon(getAll('generalDanger')[10],getAll('generalDanger')[8],getAll('generalDanger')[9],get('generalDanger_6_4'),true);
sumSon(getAll('generalDangerGovern')[10],getAll('generalDangerGovern')[8],getAll('generalDangerGovern')[9],get('generalDangerGovern_6_4'),true);
sumSon(getAll('planMoney')[10],getAll('planMoney')[8],getAll('planMoney')[9],get('planMoney_6_4'),false);
//7.烟花爆竹企业
sumSon(getAll('shouldTroubleshooting')[12],getAll('shouldTroubleshooting')[13],getAll('shouldTroubleshooting')[14],get('shouldTroubleshooting_7'),true)
sumSon(getAll('company')[12],getAll('company')[13],getAll('company')[14],get('company_7'),true)
sumSon(getAll('generalDanger')[12],getAll('generalDanger')[13],getAll('generalDanger')[14],get('generalDanger_7'),true)
sumSon(getAll('generalDangerGovern')[12],getAll('generalDangerGovern')[13],getAll('generalDangerGovern')[14],get('generalDangerGovern_7'),true)
sumSon(getAll('planMoney')[12],getAll('planMoney')[13],getAll('planMoney')[14],get('planMoney_7'),false)		
//计算整改率
for (var i=0;i<getAll('generalDanger').length;i++) {
	if(i<5) {
		getDivisor(getAll('generalDangerGovern')[i],getAll('generalDanger')[i],'zg_'+i,true);
		if(getAll('shouldTroubleshooting')[i].innerHTML!='0')getDivisor(getAll('company')[i],getAll('shouldTroubleshooting')[i],'zg1_'+i,true);
	}else {
		getDivisor(getAll('generalDangerGovern')[i],getAll('generalDanger')[i],'zg_'+(i+1),true);
		if(getAll('shouldTroubleshooting')[i].innerHTML!='0')getDivisor(getAll('company')[i],getAll('shouldTroubleshooting')[i],'zg1_'+(i+1),true);
	}
	if(i==5){
		getDivisor(get('generalDangerGovern_'+i),get('generalDanger_'+i),'zg_'+i,true);
		if(get('shouldTroubleshooting_'+i).innerHTML!='0')getDivisor(get('company_'+i),get('shouldTroubleshooting_'+i),'zg1_'+i,true);
	} else if(i==6||i==7) {
		getDivisor(get('generalDangerGovern_'+i),get('generalDanger_'+i),'zg_n_'+i,true);
		if(get('shouldTroubleshooting_'+i).innerHTML!='0')getDivisor(get('company_'+i),get('shouldTroubleshooting_'+i),'zg1_n_'+i,true);
	} else if(i==getAll('generalDanger').length-1) {
		getDivisor(get('generalDangerGovern_6_4'),get('generalDanger_6_4'),'zg_n_6_4',true);
		if(get('shouldTroubleshooting_6_4').innerHTML!='0')getDivisor(get('company_6_4'),get('shouldTroubleshooting_6_4'),'zg1_n_6_4',true);
	}
}
//合计
sumSons(get('shouldTroubleshooting_t'),getAll('shouldTroubleshooting'),get('shouldTroubleshooting_t'),true);
sumSons(get('company_t'),getAll('company'),get('company_t'),true);
sumSons(get('generalDanger_t'),getAll('generalDanger'),get('generalDanger_t'),true);
sumSons(get('generalDangerGovern_t'),getAll('generalDangerGovern'),get('generalDangerGovern_t'),true);
sumSons(get('planMoney_t'),getAll('planMoney'),get('planMoney_t'),false);	
//设置TD值
sumSonInnerHTML("5","6","7","8","9","10","11","6");//sumSonInnerHTML("6","7","8","9","10","11","12","6");
sumSonInnerHTML("8","9","10","6_4");//sumSonInnerHTML("9","10","11","6_4");
sumSonInnerHTML("12","13","14","7");//sumSonInnerHTML("13","14","15","7");
<c:if test="${!empty(reportMonth)&&reportMonth!=''}">
	var year = "${reportMonth}".split("-")[0];
	var month = "${reportMonth}".split("-")[1];
	get("year").value=year;
	get("month").value=month;
</c:if>

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