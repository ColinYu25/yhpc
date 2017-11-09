<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/template/common/header.jsp" %>
<script type="text/javascript">
function checkForm() {
	if(get("type").value=="3") {
		window.location="../three/doneCityReportInit.xhtml";
	} else {
		get('searchForm').submit();
	}
}
function change(opv){
	if(opv==3){
		window.location="../three/doneCityReportInit.xhtml";
	}else if (opv == 4){
		window.location="../zhifa/doneCityReportInit.xhtml";
	}else if (opv == 5){
		window.location="../dfzw/doneCityReportInit.xhtml";
	}else if (opv == 6){
		window.location="../punish/doneCityReportInit.xhtml";
	}else{
		get('searchForm').submit();
	}
}
</script>
<body>
<form method="post" name="searchForm" id="searchForm" action="doneCityReportInit.xhtml">
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <tr>
    <td align="center" style="font-size:18px" colspan="6"><strong>统计报表
		</strong>
    </td>
  </tr>
  <tr>
	<th width="7%">类型</th>
	<td width="26%"><select id="type" name="mineReport.type" style="width:160px;" onChange="change(this.options[this.options.selectedIndex].value)">
	<!--option value="1">矿山危化等行业和领域</option>
	<option value="2">其他行业和领域</option>
	<option value="3">打击三非</option>
	<option value="4" selected>安全生产执法行动</option-->
	<option value="5">打非治违</option>
	<option value="6">打非治违处罚</option>
	</select></td>
	<th width="7%">填报年月</th>
	<td width="30%">
	<select id="year" name="mineReport.year" style="width:85px;">
		  	<c:forEach var="year" items="${years}" varStatus="status"><option value="${year}">${year}</option></c:forEach>
	    </select>年<select id="month" name="mineReport.month" style="width:85px;">
		  <option value="0">--请选择--</option>
		  	<c:forEach var="month" items="${months}" varStatus="status"><option value="${month.month}">${month.month}</option></c:forEach>
		  </select>月&nbsp;&nbsp;&nbsp;&nbsp;
	 <input type="hidden" id="isReport" name="mineReport.report" value="true"/>
	</td>
	<td width="9%"><input id="sub" type="button" value="搜  索" onClick="checkForm();"/></td>
	<td width="21%"><input id="sub" type="button" value="新增汇总统计" onClick="javaScript:window.location='loadCityReportInit.xhtml?mineReport.type=${mineReport.type}&mineReport.year='+get('year').value+'&mineReport.month='+get('month').value;"/>
    </td>
  </tr>
  
</table>
</form>
<table>
<tr><td height="10"></td>
</tr>
</table>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_list">
  <tr>
	<th width="4%">序号</th>
	<th width="20%">填报单位</th>
	<th width="8%">填报月份</th>
	<th width="7%">负责人</th>
	<th width="7%">填表人</th>
	<th width="12%">联系电话</th>
	<th width="10%">上报时间</th>
	<th width="10%">上报状态</th>
	<th width="13%">操作</th>
	<th width="5%">打印</th>
  </tr>
  <c:forEach var="mine" items="${mineReports}" varStatus="status">
	<tr>
	 <td>${pagination.itemCount+status.count}&nbsp;</td>
	 <td>${mine.userId.fkUserInfo.userCompany}&nbsp;</td>
	 <td>${mine.reportMonth}&nbsp;</td>
	 <td>${mine.chargeMan}&nbsp;</td>
	 <td>${mine.fillMan}&nbsp;</td>
	 <td>${mine.tel}&nbsp;</td>
	 <td><c:if test="${mine.state!='0'}">${fn:substring(mine.modifyTime,0,10)}</c:if>&nbsp;</td>
	 <td>${mine.stateChinese}&nbsp;</td>
	 <td><a href="javaScript:;" onClick="window.location='../mine/loadCityReportInit2012.xhtml?mineReport.id=${mine.id}&mineReport.reportMonth=${mine.reportMonth}&mineReport.type=${mineReport.type}';">查看</a>&nbsp;<a href="javaScript:;" onClick="window.location='loadCityReportInit.xhtml?mineReport.id=${mine.id}&mineReport.type=${mineReport.type}';"><c:choose><c:when test="${mine.state>0}">旧版查看</c:when><c:otherwise>修改</c:otherwise></c:choose></a>&nbsp;<c:if test="${mine.state==0}"><a href="javaScript:;" onClick="if(window.confirm('  确定删除吗？'))window.location='deleteMine.xhtml?mineReport.id=${mine.id}&mineReport.report=true';">删除</a>&nbsp;</c:if><c:choose><c:when test="${!mine.allowReport}"></c:when><c:otherwise><a href="javaScript:;" onClick="if(window.confirm('  确定上报吗？'))window.location='doneCityReport.xhtml?mineReport.id=${mine.id}&mineReport.report=true';">上报</a>&nbsp;</c:otherwise></c:choose>
	 <c:choose><c:when test="${mine.allowRollback}"><a href="javaScript:;" onClick="if(window.confirm('  确定退回吗？'))window.location='rollback.xhtml?mineReport.id=${mine.id}&mineReport.report=true';">退回</a></c:when><c:otherwise></c:otherwise></c:choose></td>
	 <td><a href="javaScript:;" onClick="window.location='loadCityReportInit2012.xhtml?mineReport.id=${mine.id}&mineReport.type=${mine.type}&mineReport.print=true';">打印</a></td>
	</tr>
  </c:forEach>
  <tr>
 	 <td height="45" colspan="10" align="center" valign="bottom"><p:navigation pagination="${pagination}" /></td>
  </tr>
</table>
</body>
<script type="text/javascript">
<c:if test="${!empty(mineReport.reportMonth)}">
	var year = "${mineReport.reportMonth}".split("-")[0];
	var month = "${mineReport.reportMonth}".split("-")[1];
	get("year").value=year;
	get("month").value=month;
</c:if>
<c:if test="${!empty(mineReport.year)}">
	get("year").value="${mineReport.year}";
</c:if>
<c:if test="${!empty(mineReport.month)}">
	get("month").value="${mineReport.month}";
</c:if>
<c:if test="${!empty(mineReport.type)}">
	get("type").value="${mineReport.type}";
</c:if>
</script>
</html>