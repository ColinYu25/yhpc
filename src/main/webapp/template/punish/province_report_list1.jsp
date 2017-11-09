<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/template/common/header.jsp"%>
<link href="${contextPath}/datePicker/skin/WdatePicker.css"
	rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript"
	src="${contextPath}/datePicker/WdatePicker.js"></script>
<body>
<form method="post" name="searchForm" id="searchForm"
	action="../punish/listForCity.xhtml">
<table width="98%" border="0" cellpadding="0" cellspacing="0"
	class="table_input">
	<tr>
		<td align="center" style="font-size: 18px" colspan="3"><strong>
		打非治违处罚列表 </strong></td>
	</tr>
	<tr>
		<th width="15%">周报日期</th>
		<td width="30%"><input type="text" id="reportDateBegin"
			name="entity.reportDateBegin" maxLength=15 size=15
			value="<fmt:formatDate value='${entity.reportDateBegin}' pattern='yyyy-MM-dd'/>" class="Wdate"
			onfocus="WdatePicker({minDate:'1900-01-01',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'reportDateEnd\')||\'%y-%M-%d\'}',errDealMode:1})"
			readOnly="true" /> 至<input type="text" id="reportDateEnd"
			name="entity.reportDateEnd" maxLength=15 size=15
			value="<fmt:formatDate value='${entity.reportDateEnd}' pattern='yyyy-MM-dd'/>" class="Wdate"
			onfocus="WdatePicker({minDate:'#F{$dp.$D(\'reportDateBegin\')||\'1900-01-01\'}',dateFmt:'yyyy-MM-dd',errDealMode:1})"
			readOnly="true" /></td>
		<td width="55%"><input id="sub" type="button" value="搜  索"
			onClick="get('searchForm').submit();" /></td>
	</tr>
</table>
</form>
<table>
	<tr>
		<td height="10"></td>
	</tr>
</table>
<table width="98%" border="0" cellpadding="0" cellspacing="0"
	class="table_list">
	<tr>
		<th width="4%">序号</th>
		<th width="20%">填报单位</th>
		<th width="15%">日期</th>
		<th width="5%">负责人</th>
		<th width="5%">填表人</th>
		<th width="12%">联系电话</th>
		<th width="9%">上报时间</th>
		<th width="7%">上报状态</th>
		<th width="11%">操作</th>
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
			<td><c:if test="${item.state!='0'}">${fn:substring(item.modifyTime,0,10)}</c:if>&nbsp;</td>
			<td>${item.stateChinese}&nbsp;</td>
			<td><c:choose>
				<c:when test="${item.state=='0'}">
					<a href="#"
						onClick="window.location='loadForCity.xhtml?entity.id=${item.id}';">修改</a>
				</c:when>
				<c:otherwise>
					<a href="#"
						onClick="
	 	<c:choose>
	 	<c:when test='${item.state==2}'>
	 		window.location='../punish/view.xhtml?entity.id=${item.id}';
	 	</c:when>
	 	<c:otherwise>
			window.location='view.xhtml?entity.id=${item.id}';
		</c:otherwise>
	 </c:choose>"><c:choose>
						<c:when test="${item.state>0}">查看</c:when>
						<c:otherwise>修改</c:otherwise>
					</c:choose></a>
				</c:otherwise>
			</c:choose> <c:if test="${item.state=='0'}">&nbsp;<a href="#"
					onClick="if(window.confirm('  确定删除吗？'))window.location='deleteForCity.xhtml?entity.id=${item.id}';">删除</a>
			</c:if> &nbsp;<c:choose>
				<c:when test="${!item.allowReport}"></c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose> <c:choose>
				<c:when test="${ !item.notAllowedroolBack && item.allowRollback && roleDepic == item.roleDepic }">
					<a href="#"
						onClick="if(window.confirm('  确定退回吗？'))window.location='../punish/rollback.xhtml?entity.id=${item.id}&entity.report=true';">退回</a>
				</c:when>
				<c:otherwise></c:otherwise>
			</c:choose></td>
			</td>
			<td><c:choose>
				<c:when test="${item.state!=2}">
					<a href="#"
						onClick="window.location='print.xhtml?entity.id=${item.id}'">打印</a>
				</c:when>
				<c:otherwise>
					<a href="#"
						onClick="window.location='print.xhtml?entity.id=${item.id}&entity.print=true';">打印</a>
				</c:otherwise>
			</c:choose></td>
		</tr>
	</c:forEach>
	<tr>
		<td height="45" colspan="10" align="center" valign="bottom"><p:navigation
			pagination="${pagination}" /></td>
	</tr>
</table>
</body>
</html>
