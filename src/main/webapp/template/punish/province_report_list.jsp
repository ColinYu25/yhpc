<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/template/common/header.jsp" %>
<link href="${contextPath}/datePicker/skin/WdatePicker.css"
	rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript"
	src="${contextPath}/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
function sear_Form() {
	if(get("type").value=="3") {
		window.location="../three/loadProvinceReports.xhtml?threeReport.year="+get('year').value+"&threeReport.month="+get('month').value;
	} else if(get("type").value=="4") {
		window.location="loadProvinceReports.xhtml?zhifaReport.year="+get('year').value+"&zhifaReport.month="+get('month').value;
	} else if(get("type").value=="5"||get("type").value=="6") {
		window.location="loadProvinceReports.xhtml?entity.reportDateBegin="+get("reportDateBegin").value+"&entity.reportDateEnd="+get("reportDateEnd").value;
	} else {
		window.location="../mine/loadProvinceReports.xhtml?threeReport.year="+get('year').value+"&threeReport.month="+get('month').value+"&entity.type="+get('type').value+"";
	}
}
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
	var roleDepic = '${roleDepic}';
	get("reportDateEnd_hidden").value = get("reportDateEnd").value;
	get("reportType_hidden").value = 1;
	if(roleDepic && roleDepic=="anwei"){
		get("anweiCheck").value = true;
	}else{
		get("anweiCheck").value = false;
	}
	$('ajaxForm').set("send",{onComplete:function(response){
		get("addStatistic").disabled = response=="true"?false:true;
		get("div_remind").innerHTML = response=="true"?"":"对应您选择的周报日期已存在数据，请在原有数据中修改，防止重复数据。";
	}});
	get("ajaxForm").send();
}

function changeReportDateEnd(date){
	if (date == "") {
		get("reportDateBegin").value = "";
		return;
	}
	var d = new Date(date.substring(0,4),date.substring(5,7),0)
	var d2 = new Date(date.substring(0,4),date.substring(5,7)-1,date.substring(8,10));
	if((d-d2)!=0){
		alert("请选择最后月的最后一天!");
		return false;
	}
	get("reportDateBegin").value = date.substring(0,4)+"-01-01";
	get("reportDateReport_hidden").value = get("reportDateBegin").value;
	get("reportDateEnd_hidden").value = get("reportDateEnd").value;
	get("reportType_hidden").value = 2;
	var roleDepic = '${roleDepic}';
	if(roleDepic && roleDepic=="anwei"){
		get("anweiCheck").value = true;
	}else{
		get("anweiCheck").value = false;
	}
	$('ajaxForm').set("send",{onComplete:function(response){
		get("addStatistic").disabled = response=="true"?false:true;
		get("div_remind").innerHTML = response=="true"?"":"对应您选择的日期已存在数据，请在原有数据中修改，防止重复数据。";
	}});
	get("ajaxForm").send();
	
}
function addStatistics() {
	if (get("reportDateBegin").value=="") {
		alert("请选择时间");
		return;
	}
	var roleDepic = '${roleDepic}';
	if(roleDepic && roleDepic=="anwei"){
		window.location = '../punish/loadProvinceReportForCity.xhtml?entity.province=true&entity.anweiCheck=true&entity.reportDateBegin='+get('reportDateBegin').value+'&entity.reportDateEnd='+get('reportDateEnd').value+'&entity.reportType='+get('reportType_hidden').value;
	}else{
		window.location = '../punish/loadProvinceReportForCity.xhtml?entity.reportDateBegin='+get('reportDateBegin').value+'&entity.reportDateEnd='+get('reportDateEnd').value+'&entity.reportType='+get('reportType_hidden').value;
	}
}
</script>
<body>
<form id="ajaxForm" name="ajaxForm" method="post" action="checkAllowCreate1.xhtml">
	<input type="hidden" name="entity.reportDateBegin" id="reportDateReport_hidden" />
	<input type="hidden" name="entity.anweiCheck" id="anweiCheck" />
	<input type="hidden" name="entity.reportType" id="reportType_hidden" />
	<input type="hidden" name="entity.reportDateEnd" id="reportDateEnd_hidden" />
</form>
<form method="post" name="searchForm2" id="searchForm2" action="">
<input type="hidden" id="reportMonth" name="zhifaReport.reportMonth"/>
</form>
<form method="post" name="searchForm" id="searchForm" action="../punish/loadProvinceReport.xhtml">
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <tr>
    <td align="center" style="font-size:18px" colspan="6"><strong>打非治违处罚列表
		</strong>
    </td>
  </tr>
	  <tr>
		<th width="12%">类型</th>
		<td width="26%"><select id="type" name="entity.type" style="width:160px;" onchange="changeSelect()">
		<!--option value="1">矿山危化等行业和领域</option>
		<option value="2">其他行业和领域</option>
		<option value="3">打击三非</option>
		<option value="4">安全生产执法行动</option-->
		<option value="5">打非治违</option>
		<option value="6" selected>打非治违处罚</option>
		</select></td>
	<th width="7%">时间</th>
	<td width="30%">
	<input type="text" id="reportDateBegin"
			name="entity.reportDateBegin" maxLength=15 size=15
			value="<fmt:formatDate value='${entity.reportDateBegin}' pattern='yyyy-MM-dd'/>" class="Wdate"
			onfocus="WdatePicker({minDate:'1900-01-01',dateFmt:'yyyy-MM-dd',maxDate:'#F{\'%y-%M-%d\'}',disabledDays:[0,1,2,4,5,6],errDealMode:1})"
			readOnly="true" onchange="changeReportDateBegin(this.value);" /> 至<input type="text" id="reportDateEnd"
			name="entity.reportDateEnd" maxLength=15 size=15 onchange="changeReportDateEnd(this.value);" onchange="changeReportDateEnd(this.value);"
			onfocus="WdatePicker({minDate:'1900-01-01',dateFmt:'yyyy-MM-dd',maxDate:'#F{\'%y-%M-%d\'}',errDealMode:1})"
			value="<fmt:formatDate value='${entity.reportDateEnd}' pattern='yyyy-MM-dd'/>" class="Wdate"
			readOnly="true" />
	</td>
	<td width="9%"><input id="sub_search" type="button" value="搜  索" onClick="sear_Form()"/></td>	
	<td width="30%">
	<input type="hidden" id="reportMonth" name="entity.reportMonth"/>
    <input id=addStatistic type="button" value="新增汇总统计" onClick="addStatistics()" <c:if test="${hasAjjLookRole=='1'}">disabled="disabled"</c:if>/></td>
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
	<th width="15%">日期</th>
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
	 <td>
	 	<c:choose>
	 		<c:when test="${item.state>0}"><a href="#" onClick="window.location='view.xhtml?entity.id=${item.id}&entity.province=true';">查看</a></c:when>
	 	<c:otherwise></c:otherwise>
	 	</c:choose>&nbsp;
	 <c:if test="${item.state==0}"><a href="#" onClick="if(window.confirm('  确定删除吗？'))window.location='delete.xhtml?entity.province=true&entity.id=${item.id}&entity.report=true';">删除</a>&nbsp;</c:if>
	 <c:if test="${hasAjjLookRole=='0'}">
	    <c:choose><c:when test="${item.isReport}"></c:when><c:otherwise><a href="#" onClick="if(window.confirm('  确定上报吗？'))window.location='doneCityReport.xhtml?entity.id=${item.id}&entity.report=true';"><c:if test="${item.state==0}">上报</c:if></a>&nbsp;</c:otherwise></c:choose>
	 </c:if>
	 <c:choose><c:when test="${item.allowRollback}"><a href="#" onClick="if(window.confirm('  确定退回吗？'))window.location='rollback.xhtml?entity.id=${item.id}&entity.report=true';">退回</a></c:when><c:otherwise></c:otherwise></c:choose></td>
	 <td><a href="#" onClick="window.location='loadDetailsByDfzw.xhtml?entity.id=${item.id}&entity.print=true';">打印</a></td>
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