<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/template/common/header.jsp" %>
<title>重大隐患</title>
<body>	
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <tr>
    <td align="center" style="font-size:18px"><strong>
		未设置挂牌督办级别的重大隐患列表
		</strong>
    </td>
  </tr>
  <c:if test="${(param.reportState==0||param.reportState=='')&&param.tradeType!=0&&(param.tradeType<45||param.tradeType>99)}">
  <tr>
    <td>&nbsp;&nbsp;
		<a href="loadBigTrouble.xhtml?bigTrouble.tradeType=${param.tradeType}&bigTrouble.reportMonth=${param.reportMonth}&bigTrouble.tableType=${param.tableType}&bigTrouble.troubleType=${param.troubleType}&bigTrouble.reportState=${param.reportState}&mineId=${param.mineId=='' ? '0' : param.mineId}&tableName=${param.tableName}">添加重大隐患</a>
    </td>
  </tr>
  </c:if>
</table>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_list">
  <tr>
	<th width="4%">序号</th>
	<th width="4%">销号</th>
	<th width="15%">单位名称</th>
	<th width="15%">地址</th>
	<th width="38%">隐患内容</th>
	<th width="9%">时间</th>
	<th width="15%">操作</th>
	</tr>
	<c:forEach var="list" items="${bigTroubles}" varStatus="status">
	<tr>
	<td  align="center">${pagination.itemCount+status.count}&nbsp;</td>
	<c:choose><c:when test="${param.reportMonth==list.reportMonth||param.reportMonth!=list.stateTime}"><c:if test="${list.stateTime<=param.reportMonth||list.stateTime==null||list.stateTime==''}"><td>${list.state==0?'否':'是'}&nbsp;</td></c:if><c:if test="${list.stateTime>param.reportMonth&&list.stateTime!=null&&list.stateTime!=''}"><td>${list.state==0?'是':'否'}&nbsp;</td></c:if></c:when><c:otherwise><td>${list.state==0?'否':'是'}&nbsp;</td></c:otherwise></c:choose>
	<td><p>${list.companyName}&nbsp;</p></td>
	<td><p>${list.address}&nbsp;</p></td>
	<td><p>${list.content}&nbsp;</p></td>
	<td>${list.reportMonth}&nbsp;</td>
	<td><a href="javaScript:;" onclick="window.location='loadBigTrouble.xhtml?bigTrouble.id=${list.id}&bigTrouble.troubleType=${param.troubleType}&bigTrouble.reportState=1&mineId=${param.mineId=='' ? '0' : param.mineId}&tableName=${param.tableName}';">设置级别</a></td>
	</tr>
	</c:forEach>
	  <tr>
	 	 <td height="45" colspan="8" align="center" valign="bottom"><p:navigation pagination="${pagination}" /></td>
	  </tr>
</table>
</body>
</html>