<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/template/common/header.jsp" %>
<body>
<form method="post" name="searchForm" id="searchForm" action="../mine/loadCountyReport.xhtml">
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <tr>
    <td align="center" style="font-size:18px" colspan="5"><strong>
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
		  <option value="0">全部</option>
		  	<c:forEach var="month" items="${months}" varStatus="status"><option value="${month.month}">${month.month}</option></c:forEach>
		  </select>月&nbsp;&nbsp;&nbsp;&nbsp;
		  <input type="hidden" name="mineReport.type" id="type" value="${mineOrOther=='mine'?1:2}"/>
	</td>
	<th width="10%">地级市</th>
	<td width="15%">
	<select id="secondArea" name="mineReport.secondArea" style="width:90px;">
	<c:if test="${fn:length(areas)>1}"><option value="-1">全部</option></c:if>
		  	<c:forEach var="area" items="${areas}" varStatus="status"><option value="${area.areaCode}">${area.areaName}</option></c:forEach>
		  </select>
	</td>
	<td width="40%"><input id="sub" type="button" value="搜  索" onClick="get('searchForm').submit();"/></td>
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
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_list">
  <tr>
	<th width="4%">序号</th>
	<th width="25%">填报单位</th>
	<th width="8%">填报月份</th>
	<th width="7%">负责人</th>
	<th width="7%">填表人</th>
	<th width="12%">联系电话</th>
	<th width="10%">上报时间</th>
	<th width="10%">上报状态</th>
	<th width="12%" nowrap>操作</th>
	<th width="5%">打印</th>
  </tr>
  <c:forEach var="mine" items="${mineReports}" varStatus="status">
	<tr>
	 <td>${pagination.itemCount+status.count}&nbsp;</td>
	 <td <c:if test="${mine.state==0}">style="color:red;"</c:if>>${mine.userId.fkUserInfo.userCompany}&nbsp;</td>
	 <td>${mine.reportMonth}&nbsp;</td>
	 <td>${mine.chargeMan}&nbsp;</td>
	 <td>${mine.fillMan}&nbsp;</td>
	 <td>${mine.tel}&nbsp;</td>
	 <td><c:if test="${mine.state!=0}">${fn:substring(mine.modifyTime,0,10)}</c:if>&nbsp;</td>
	 <td <c:if test="${mine.state==0}">style="color:red;"</c:if>>${mine.stateChinese}&nbsp;</td>
	 <td><c:if test="${mine.state!=0}">
	 <c:choose><c:when test="${mineOrOther=='mine'}"><a href="javaScript:;" onClick="window.location='../mine/loadMine2012.xhtml?mineReport.id=${mine.id}';">查看</a>&nbsp;&nbsp;<a href="javaScript:;" onClick="window.location='loadMine.xhtml?mineReport.id=${mine.id}';">旧版查看</a></c:when>
	 <c:otherwise><a href="javaScript:;" onClick="window.location='../other/loadOther2012.xhtml?mineReport.id=${mine.id}';">查看</a>&nbsp;&nbsp;<a href="javaScript:;" onClick="window.location='../other/loadOther.xhtml?mineReport.id=${mine.id}';">旧版查看</a></c:otherwise></c:choose>
	 </c:if>&nbsp;</td>
	 <td><c:choose><c:when test="${mine.state!=2}">
	 <c:if test="${mineOrOther=='mine'}"><a href="javaScript:;" onClick="window.location='../mine/loadMine2012.xhtml?mineReport.id=${mine.id}&mineReport.print=true'">打印</a></c:if>
	 <c:if test="${mineOrOther=='other'}"><a href="javaScript:;" onClick="window.location='../other/loadOther2012.xhtml?mineReport.id=${mine.id}&mineReport.print=true'">打印</a></c:if>
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
<c:if test="${!empty(mineReport.secondArea)}">
	get("secondArea").value="${mineReport.secondArea}";
</c:if>
</script>
</html>