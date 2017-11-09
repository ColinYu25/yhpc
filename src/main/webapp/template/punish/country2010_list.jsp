<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/template/common/header.jsp" %>
<link href="${contextPath}/datePicker/skin/WdatePicker.css"
	rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript"
	src="${contextPath}/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
function changeSelect() {
	if(get("type").value=="5") {
		get('searchForm2').action = '../dfzw/loadProvinceReports.xhtml';
		get('searchForm2').submit();
	}else if(get("type").value=="6") {
		get('searchForm2').action = '../punish/loadProvinceReports.xhtml';
		get('searchForm2').submit();
	}
}
function changeReportDateBegin(date) {
	if (date == "") {
		get("reportDateEnd").value = "";
		return;
	}
	var d = new Date(date.substring(0,4),date.substring(5,7)-1,date.substring(8,10));
	d.setDate(d.getDate()+6);
	var d_str = d.getYear() + "-" + (d.getMonth()+1) + "-" + d.getDate();
	get("reportDateEnd").value = d_str;
	get("reportDateReport_hidden").value = get("reportDateBegin").value;
	$('ajaxForm').set("send",{onComplete:function(response){
		get("addStatistic").disabled = response=="true"?false:true;
		get("div_remind").innerHTML = response=="true"?"":"对应您选择的周报日期已存在数据，请在原有数据中修改，防止重复数据。";
	}});
	get("ajaxForm").send();
}
function addStatistics() {
	if (get("reportDateBegin").value=="") {
		alert("请选择周报时间");
		return;
	}
	window.location = '../punish/loadProvinceReport.xhtml?entity.reportDateBegin='+get('reportDateBegin').value+'&entity.reportDateEnd='+get('reportDateEnd').value;
}
</script>
<body>
<form id="ajaxForm" name="ajaxForm" method="post" action="checkAllowCreate.xhtml">
<input type="hidden" name="entity.reportDateBegin" id="reportDateReport_hidden" />
</form>
<form method="post" name="searchForm" id="searchForm" action="loadCountry.xhtml">
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <tr>
    <td align="center" style="font-size:18px" colspan="3"><strong>统计报表
		</strong>
    </td>
  </tr>
  <tr>
	<th width="7%">周报时间</th>
	<td width="30%">
	<input type="text" id="reportDateBegin"
			name="entity.reportDateBegin" maxLength=15 size=15
			value="<fmt:formatDate value='${entity.reportDateBegin}' pattern='yyyy-MM-dd'/>" class="Wdate"
			onfocus="WdatePicker({minDate:'1900-01-01',dateFmt:'yyyy-MM-dd',maxDate:'#F{\'%y-%M-%d\'}',disabledDays:[0,1,2,4,5,6],errDealMode:1})"
			readOnly="true" onchange="changeReportDateBegin(this.value);" /> 至<input type="text" id="reportDateEnd"
			name="entity.reportDateEnd" maxLength=15 size=15
			value="<fmt:formatDate value='${entity.reportDateEnd}' pattern='yyyy-MM-dd'/>" class="Wdate"
			readOnly="true" />
	</td>
	<td width="55%">
	<!--input type="hidden" id="reportMonth" name="entity.reportMonth"/-->
	<input id="addStatistic" type="button" value="新增统计" onClick="addStatistics();"/></td>
  </tr>
</table>
</form>
<table>
<tr><td height="10"><div id="div_remind" style="font-size:12px;color:red;text-align:left"></div></td>
</tr>
</table>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_list">
  <tr>
	<th width="4%">序号</th>
	<th width="20%">填报单位</th>
	<th width="15%">周报日期</th>
	<th width="7%">负责人</th>
	<th width="7%">填表人</th>
	<th width="12%">联系电话</th>
	<th width="13%">操作</th>
	<th width="5%">打印</th>
  </tr>
  <c:forEach var="item" items="${entities}" varStatus="status">
	<tr>
	 <td>${pagination.itemCount+status.count}&nbsp;</td>
	 <td>${item.user.fkUserInfo.userCompany}&nbsp;</td>
	 <td>${item.reportDateBegin}&nbsp;到&nbsp;${item.reportDateEnd}</td>
	 <td>${item.chargeMan}&nbsp;</td>
	 <td>${item.fillMan}&nbsp;</td>
	 <td>${item.tel}&nbsp;</td>
	 <td><a href="#" onClick="window.location='loadProvinceReport.xhtml?entity.id=${item.id}';"><c:choose><c:when test="${item.state>0}">查看</c:when><c:otherwise>修改</c:otherwise></c:choose></a>&nbsp;<c:if test="${item.state==0}"><a href="#" onClick="if(window.confirm('  确定删除吗？'))window.location='delete.xhtml?entity.id=${item.id}&entity.report=true';">删除</a>&nbsp;</c:if><c:choose><c:when test="${!item.allowReport}"></c:when><c:otherwise><a href="#" onClick="if(window.confirm('  确定上报吗？'))window.location='doneCityReport.xhtml?entity.id=${item.id}&entity.report=true';">上报</a>&nbsp;</c:otherwise></c:choose>
	 <c:choose><c:when test="${item.allowRollback}"><a href="#" onClick="if(window.confirm('  确定退回吗？'))window.location='rollback.xhtml?mineReport.id=${mine.id}&mineReport.report=true';">退回</a></c:when><c:otherwise></c:otherwise></c:choose></td>
	 <td><a href="#" onClick="window.location='loadCountry.xhtml?entity.id=${item.id}&entity.print=true';">打印</a></td>
	</tr>
  </c:forEach>
  <tr>
 	 <td height="45" colspan="10" align="center" valign="bottom"><p:navigation pagination="${pagination}" /></td>
  </tr>
</table>
</body>
<script type="text/javascript">
</script>
</html>