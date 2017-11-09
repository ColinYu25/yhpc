<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/template/common/header.jsp" %>
<link href="${contextPath}/datePicker/skin/WdatePicker.css"
	rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript"
	src="${contextPath}/datePicker/WdatePicker.js"></script>
<body>
<form method="post" name="searchForm" id="searchForm" action="../dfzw/loadCountyReport.xhtml">
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <tr>
    <td align="center" style="font-size:18px"  colspan="5"><strong>
    	打非治违县级列表
		</strong>
    </td>
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
	<th width="10%">地级市</th>
	<td width="15%">
	<select id="secondArea" name="entity.secondArea" style="width:90px;">
	<c:if test="${fn:length(areas)>1}">
	<option value="-1">全部</option>
	</c:if>
	<c:forEach var="area" items="${areas}" varStatus="status"><option value="${area.areaCode}">${area.areaName}</option></c:forEach>
	</select>
	</td>
	<td width="55%"><input id="sub" type="button" value="搜  索" onClick="get('searchForm').submit();"/></td>
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
	<th width="15%">周报日期</th>
	<th width="7%">负责人</th>
	<th width="7%">填表人</th>
	<th width="12%">联系电话</th>
	<th width="10%">上报时间</th>
	<th width="8%">上报状态</th>
	<th width="6%">操作</th>
	<th width="5%">打印</th>
  </tr>
  <c:forEach var="item" items="${entities}" varStatus="status">
	<tr>
	 <td>${pagination.itemCount+status.count}&nbsp;</td>
	 <td <c:if test="${item.state==0}">style="color:red;"</c:if>>${item.user.fkUserInfo.userCompany}&nbsp;</td>
	 <td>${item.reportDateBegin}&nbsp;到&nbsp;${item.reportDateEnd}</td>
	 <td>${item.chargeMan}&nbsp;</td>
	 <td>${item.fillMan}&nbsp;</td>
	 <td>${item.tel}&nbsp;</td>
	 <td><c:if test="${item.state!=0}">${fn:substring(item.modifyTime,0,10)}</c:if>&nbsp;</td>
	 <td <c:if test="${item.state==0}">style="color:red;"</c:if>>${item.stateChinese}&nbsp;</td>
	 <td>&nbsp;<c:if test="${item.state!=0}"><a href="#" onClick="window.location='view.xhtml?entity.id=${item.id}';">查看</a></c:if></td>
	 <td><a href="#" onClick="window.location='print.xhtml?entity.id=${item.id}'">打印</a>
	 </td>
	</tr>
  </c:forEach>
  <tr>
 	 <td height="45" colspan="10" align="center" valign="bottom"><p:navigation pagination="${pagination}" /></td>
  </tr>
</table>
<script type="text/javascript">
<c:if test="${!empty(entity.secondArea)}">
get("secondArea").value = "${entity.secondArea}";
</c:if>
</script>
</body>
</html>
