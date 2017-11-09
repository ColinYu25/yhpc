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
	//var reportMonth = get("year").value+"-"+get("month").value;
	var reportMonth = "${mineReport.reportMonth}";
	get("troubleType").value=troubleType;
	get("tradeType").value=tradeType;
	get("reportMonth").value=reportMonth;
	get("bigTroubleForm").submit();
}
</script>
<body>
<form id="bigTroubleForm" name="bigTroubleForm" method="post" action="../bigTrouble/loadBigTroubles.xhtml">
<input type="hidden" name="mineId" id="mineId" value="${mineReport.id}"/>
<input type="hidden" name="reportState" id="reportState"/>
<input type="hidden" name="tableType" id="tableType" value="2"/>
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
		     <th colspan="2" height="50" class="head">交通运输等重点行业领域企业和单位安全生产事故隐患排查治理情况月报表</th>
        </tr>
		<tr style="font-size:12px">
		  <td height="18" width="80%">填报单位:<c:choose><c:when test="${!empty(mineReport.userId)}">${mineReport.userId.fkUserInfo.userCompany}</c:when>
		  <c:otherwise>${userDetail.userCompany}</c:otherwise></c:choose>&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red; font-size:12px;">（如填报单位名称不对，请到个人信息修改！）</span></td>
		  <td height="18" width="20%" align="right" nowrap="nowrap" valign="bottom">
		  截止到${fn:substring(mineReport.reportMonth,0,4)}年${fn:substring(mineReport.reportMonth,6,8)}月<span id="day"></span><!--日-->为止&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
		  <td align="center" id="bt"><c:choose><c:when test="${statistics[status.count-1].bigTrouble==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(${item.key},1);">${statistics[status.count-1].bigTrouble}</a></c:otherwise></c:choose></td>
		  <td align="center" id="btg"><c:choose><c:when test="${statistics[status.count-1].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(${item.key},2);">${statistics[status.count-1].bigTroubleGovern}</a></c:otherwise></c:choose></td>
		  <td align="center">
		  <c:choose><c:when test="${statistics[status.count-1].bigTrouble==0||statistics[status.count-1].bigTroubleGovern==0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${statistics[status.count-1].bigTroubleIsDividend}"><fmt:formatNumber pattern="0" value="${(statistics[status.count-1].bigTroubleGovern/statistics[status.count-1].bigTrouble)*100}"/></c:when><c:otherwise><fmt:formatNumber pattern="0.0" value="${(statistics[status.count-1].bigTroubleGovern/statistics[status.count-1].bigTrouble)*100}"/></c:otherwise></c:choose></c:otherwise></c:choose></td>
		  <td align="center" id="btng"><c:choose><c:when test="${statistics[status.count-1].bigTroubleNotGovern==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(${item.key},3);">${statistics[status.count-1].bigTroubleNotGovern}</a></c:otherwise></c:choose></td>
		  <td align="center" id="wdw"><c:choose><c:when test="${statistics[status.count-1].wdw==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(${item.key},4);">${statistics[status.count-1].wdw}</a></c:otherwise></c:choose></td>
		  <td align="center" id="gpt"><c:choose><c:when test="${statistics[status.count-1].guapaiTotal==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(${item.key},5);">${statistics[status.count-1].guapaiTotal}</a></c:otherwise></c:choose></td>
		  <td align="center" id="pgp"><c:choose><c:when test="${statistics[status.count-1].provinceGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(${item.key},6);">${statistics[status.count-1].provinceGuapai}</a></c:otherwise></c:choose></td>
		  <td align="center" id="cgp"><c:choose><c:when test="${statistics[status.count-1].cityGuapai==0}">&nbsp;</c:when><c:otherwise><a href="javaScript:;" onClick="openWindow(${item.key},7);">${statistics[status.count-1].cityGuapai}</a></c:otherwise></c:choose></td>
		  <td align="center" id="planMoney"><c:choose><c:when test="${mineReportDetails[status.count-1].planMoney==0.0}">&nbsp;</c:when><c:otherwise><c:choose><c:when test="${fn:substring(mineReportDetails[status.count-1].planMoneyString,(fn:length((mineReportDetails[status.count-1].planMoneyString))-2),fn:length((mineReportDetails[status.count-1].planMoneyString)))=='.0'}"><fmt:formatNumber pattern="0" value="${mineReportDetails[status.count-1].planMoney}"/></c:when><c:otherwise>${mineReportDetails[status.count-1].planMoney}</c:otherwise></c:choose></c:otherwise></c:choose></td>
		</tr>
	</c:forEach>
	</tbody>
  	<tr>
       <td colspan="16"><table width="100%" border="0"><tr>
	   <td width="15%" nowrap="nowrap">单位负责人：	     </td>
	   <td width="8%">${mineReport.chargeMan}</td>
	   <td width="13%" align="center">填表人：	     </td>
	   <td width="8%">${mineReport.fillMan}</td>
	   <td width="13%" align="center">联系电话：	     </td>
	   <td width="15%">${mineReport.tel}</td>
	   <td width="16%" align="center">填报日期：	     </td>
	   <td width="12%"><s:date name='mineReport.fillDate' format='yyyy-MM-dd' /></td>
	   </tr></table></td>
    </tr>	
  </table>
  
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>		 
     <td width="28%" align="center">
     <input type="button" class="but_2" onClick="window.location='../other/loadOther2012.xhtml?mineReport.id=${mineReport.id}&mineReport.print=true';" value="打  印" />
     &nbsp;&nbsp;<input type="button" class="but_2" value="返  回"  onclick="history.back(-1);"/>
	 <input type="button" name="back" class="but_2" onClick="tableToExcel();" value="导入到EXCEL" />
        </td>
    </tr>
</table>
</form>
</body>
<script language="javascript" type="text/javascript" src="${resourcePath}/js/jquery-1.3.2.js"></script>
<script type="text/javascript">
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
//将重大隐患的，td中的文字，转换成链接
var tr_heji = get("tr_heji");
index = 1;
for (var i=7; i<tr_heji.cells.length; i++) {
	if (i!=9 && i<15) {//排除百分率、资金
		if (tr_heji.cells[i].innerHTML!="&nbsp;") {
			tr_heji.cells[i].innerHTML = "<a href='#' onClick='openWindow(111,"+index+");'>"+tr_heji.cells[i].innerHTML+"</a>";
		}
		index ++;
	}
}

</script>
</html>