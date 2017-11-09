<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/template/common/header.jsp" %>
<body style="overflow-x:visible;overflow-y:visible;overflow:visible">	
<table width="737" border="0" cellpadding="0" cellspacing="0" class="table_list">
  <tr>
	<th width="170">序号</th>
	<th width="157">姓名</th>
	<th width="157">部门</th>
	<th width="157">意见</th>
	<th width="157">时间</th>
	</tr>
	<c:forEach var="list" items="${ideas}" varStatus="status">
	<tr>
	<td width="170" align="center">${status.count}</td>
	<td width="157">${list.fkUser.fkUserInfo.factName}</td>
	<td width="157">${list.fkUser.fkUserInfo.userCompany}</td>
	<td width="157">${list.content}</td>
	<td width="157">${list.createTime}</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>