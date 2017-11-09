<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/template/common/header.jsp" %>
<body>
<form method="post" name="searchForm" id="searchForm" action="loadCountry.xhtml">
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <tr>
    <td align="center" style="font-size:18px" colspan="3"><strong>统计报表
		</strong>
    </td>
  </tr>
  <tr>
	<th width="15%">填报年月</th>
	<td width="30%">
	<select id="year" style="width:85px;">
		  	<c:forEach var="year" items="${years}" varStatus="status"><option value="${year}">${year}</option></c:forEach>
		  </select>年<select id="month" style="width:55px;">
		  	<c:forEach var="month" items="${months}" varStatus="status"><option value="${month.month}">${month.month}</option></c:forEach>
		  </select>月&nbsp;&nbsp;&nbsp;&nbsp;
	</td>
	<td width="55%">
	<input type="hidden" id="reportMonth" name="mineReport.reportMonth"/>
	<input type="hidden" id="type" name="mineReport.type" value="3"/>
	<input id="sub" type="button" value="新增统计" onClick="get('reportMonth').value=get('year').value+'-'+get('month').value;get('searchForm').submit();"/></td>
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
	<th width="40%">填报单位</th>
	<th width="8%">填报月份</th>
	<th width="7%">负责人</th>
	<th width="7%">填表人</th>
	<th width="12%">联系电话</th>
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
	 <td><a href="javaScript:;" onClick="window.location='loadCountry.xhtml?mineReport.id=${mine.id}&mineReport.reportMonth=${mine.reportMonth}&mineReport.type=${mineReport.type}';"><c:choose><c:when test="${mine.state>0}">查看</c:when><c:otherwise>修改</c:otherwise></c:choose></a>&nbsp;<c:if test="${mine.state==0}"><a href="javaScript:;" onClick="if(window.confirm('  确定删除吗？'))window.location='deleteMine.xhtml?mineReport.id=${mine.id}&mineReport.report=true';">删除</a>&nbsp;</c:if><c:choose><c:when test="${!mine.allowReport}"></c:when><c:otherwise><a href="javaScript:;" onClick="if(window.confirm('  确定上报吗？'))window.location='doneCityReport.xhtml?mineReport.id=${mine.id}&mineReport.report=true';">上报</a>&nbsp;</c:otherwise></c:choose>
	 <c:choose><c:when test="${mine.allowRollback}"><a href="javaScript:;" onClick="if(window.confirm('  确定退回吗？'))window.location='rollback.xhtml?mineReport.id=${mine.id}&mineReport.report=true';">退回</a></c:when><c:otherwise></c:otherwise></c:choose></td>
	 <td><a href="javaScript:;" onClick="window.location='loadCountry.xhtml?mineReport.id=${mine.id}&mineReport.reportMonth=${mine.reportMonth}&mineReport.type=${mine.type}&mineReport.print=true';">打印</a></td>
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
</script>
</html>