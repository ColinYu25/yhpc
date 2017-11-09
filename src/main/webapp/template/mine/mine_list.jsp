<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/template/common/header.jsp" %>
<body>
<form method="post" name="searchForm" id="searchForm" action="../mine/loadMines.xhtml">
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="width:100%;" class="table_input">
  <tr>
    <td align="center" style="font-size:18px" colspan="3"><strong>
		<c:if test="${mineOrOther=='mine'}">矿山危化等行业和领域列表</c:if>
    	<c:if test="${mineOrOther=='other'}">其他行业和领域列表</c:if>
		</strong>
    </td>
  </tr>
  <tr>
	<th width="15%">填报年月</th> 
	<td width="30%">
	<select id="year" name="mineReport.year" style="width:90px;">
		  	<c:forEach var="year" items="${years}" varStatus="status"><option value="${year}">${year}</option></c:forEach>
		  </select>年<select id="month" name="mineReport.month" style="width:90px;">
		  <option value="0">--请选择--</option>
		  	<c:forEach var="month" items="${months}" varStatus="status"><option value="${month.month}">${month.month}</option></c:forEach>
		  </select>月&nbsp;&nbsp;&nbsp;&nbsp;
	</td>
	<td width="55%"><input id="sub" type="button" value="搜  索" onClick="get('searchForm').submit();"/></td>
  </tr>
  <!--tr>
    <td colspan="3">&nbsp;&nbsp;
		<c:if test="${mineOrOther=='mine'}"><a href="createMineInit.xhtml">添加矿山</a></c:if>
    	<c:if test="${mineOrOther=='other'}"><a href="createOtherInit.xhtml">添加其他</a></c:if>
    </td>
  </tr-->
</table>
</form>
<table>
<tr><td height="10"></td>
</tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_list">
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
	 <td><c:if test="${mineOrOther=='mine'}">
	 <c:if test="${mine.state>=1}"><a href="javaScript:;" onClick="<c:choose><c:when test='${mine.state==2}'>window.location='../mine/loadCityReportInit2012.xhtml?mineReport.id=${mine.id}&mineReport.type=${mineReport.type}';</c:when><c:otherwise>window.location='loadMine2012.xhtml?mineReport.id=${mine.id}';</c:otherwise></c:choose>">查看</a>&nbsp;</c:if>
	 <a href="javaScript:;" onClick="<c:choose><c:when test='${mine.state==2}'>window.location='../mine/loadCityReportInit.xhtml?mineReport.id=${mine.id}&mineReport.type=${mineReport.type}';</c:when><c:otherwise>window.location='loadMine.xhtml?mineReport.id=${mine.id}';</c:otherwise></c:choose>"><c:choose><c:when test="${mine.state>0}">旧版查看</c:when><c:otherwise>修改</c:otherwise></c:choose></a>&nbsp;<c:if test="${mine.state==0}"><a href="javaScript:;" onClick="if(window.confirm('  确定删除吗？'))window.location='deleteMine.xhtml?mineReport.id=${mine.id}';">删除</a></c:if></c:if>
	 <c:if test="${mineOrOther=='other'}">
	 <c:if test="${mine.state>=1}"><a href="javaScript:;" onClick="<c:choose><c:when test='${mine.state==2}'>window.location='../mine/loadCityReportInit2012.xhtml?mineReport.id=${mine.id}&mineReport.type=${mineReport.type}';</c:when><c:otherwise>window.location='loadOther2012.xhtml?mineReport.id=${mine.id}';</c:otherwise></c:choose>">查看</a>&nbsp;</c:if>
	 <a href="javaScript:;" onClick="<c:choose><c:when test='${mine.state==2}'>window.location='../mine/loadCityReportInit.xhtml?mineReport.id=${mine.id}&mineReport.type=${mineReport.type}';</c:when><c:otherwise>window.location='loadOther.xhtml?mineReport.id=${mine.id}';</c:otherwise></c:choose>"><c:choose><c:when test="${mine.state>0}">旧版查看</c:when><c:otherwise>修改</c:otherwise></c:choose></a>&nbsp;<c:if test="${mine.state=='0'}"><a href="javaScript:;" onClick="if(window.confirm('  确定删除吗？'))window.location='deleteOther.xhtml?mineReport.id=${mine.id}';">删除</a>&nbsp;</c:if></c:if><c:choose><c:when test="${!mine.allowReport}"></c:when><c:otherwise><a href="javaScript:;" onClick="if(window.confirm('  确定上报吗？'))window.location='doneReport.xhtml?mineReport.id=${mine.id}';">上报</a>&nbsp;</c:otherwise></c:choose>
	 <c:choose><c:when test="${mine.allowRollback}"><a href="javaScript:;" onClick="if(window.confirm('  确定退回吗？'))window.location='../mine/rollback.xhtml?mineReport.id=${mine.id}&mineReport.report=true';">退回</a></c:when><c:otherwise></c:otherwise></c:choose></td>
	 <td><c:choose><c:when test="${mine.state!=2}">
	 <c:if test="${mineOrOther=='mine'}"><a href="javaScript:;" onClick="window.location='loadMine2012.xhtml?mineReport.id=${mine.id}&mineReport.print=true'">打印</a></c:if>
	 <c:if test="${mineOrOther=='other'}"><a href="javaScript:;" onClick="window.location='loadOther2012.xhtml?mineReport.id=${mine.id}&mineReport.print=true'">打印</a></c:if>
	 </c:when><c:otherwise><a href="javaScript:;" onClick="window.location='../mine/loadCityReportInit2012.xhtml?mineReport.id=${mine.id}&mineReport.type=${mineReport.type}&mineReport.print=true';">打印</a></c:otherwise></c:choose>
	 </td>
	</tr>
  </c:forEach>
  <tr>
 	 <td height="45" colspan="10" align="center" valign="bottom"><p:navigation pagination="${pagination}" /></td>
  </tr>
</table>
</body>
<script type="text/javascript">
<c:if test="${!empty(mineReport.year)&&!empty(mineReport.month)}">
	var year = "${mineReport.year}";
	var month = "${mineReport.month}";
	get("year").value=year;
	get("month").value=month;
</c:if>
<c:if test="${mineOrOther=='other'}">
	get("searchForm").action="../other/loadOthers.xhtml";
</c:if>
</script>
</html>