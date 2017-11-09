<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/template/common/header.jsp" %>
<script type="text/javascript">
function checkForm() {
	getAll('reportMonth')[1].value=get('year').value+'-'+get('month').value;
	if(get("type").value=="3") {
		document.all.searchForm2.reportMonth.value=get('year').value+'-'+get('month').value;
		get('searchForm2').action = '../three/loadProvinceReport.xhtml';
		get('searchForm2').submit();
	}else if(get("type").value=="4") {
		document.all.searchForm2.reportMonth.value=get('year').value+'-'+get('month').value;
		get('searchForm2').action = '../zhifa/loadProvinceReport.xhtml';
		get('searchForm2').submit();
	} else {
		get('searchForm').submit();
	}
}
function sear_Form() {
	if(get("type").value=="3") {
		window.location="../three/loadProvinceReports.xhtml";
	} else if(get("type").value=="4") {
		window.location="../zhifa/loadProvinceReports.xhtml";
	} else {
		window.location="loadProvinceReports.xhtml?mineReport.year="+get('year').value+"&mineReport.month="+get('month').value+"&mineReport.type="+get('type').value+"";
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

function sendData(){
	window.location="sendDataOfMineByOMElement.xhtml?statistic.yearMonth="+get("year").value;
}
</script>
<body>
<form method="post" name="searchForm2" id="searchForm2" action="">
<input type="hidden" id="reportMonth" name="threeReport.reportMonth"/>
</form>
<form method="post" name="searchForm" id="searchForm" action="loadProvinceReport.xhtml">
<table  style="width:100%;" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <tr>
    <td align="center" style="font-size:18px" colspan="6"><strong>${mineReport.type==1?'矿山危化等行业和领域列表':'其他行业和领域列表'}
		</strong>
    </td>
  </tr>
	  <tr>
		<th width="12%">类型</th>
		<td width="26%"><select id="type" name="mineReport.type" style="width:160px;" onchange="changeSelect()">
		<!--<option value="1">矿山危化等行业和领域</option>
		<option value="2">其他行业和领域</option>
		option value="3">打击三非</option>
		<option value="4">安全生产执法行动</option>-->
		<option value="5">打非治违</option>
		<option value="6">打非治违处罚</option>
		</select></td>
	<th width="12%">填报年月</th>
	<td width="30%">
	<select id="year" style="width:85px;">
		  	<c:forEach var="year" items="${years}" varStatus="status"><option value="${year}">${year}</option></c:forEach>
		  </select>年<select id="month" style="width:55px;">
		  <option value="0">全部</option>
		  	<c:forEach var="month" items="${months}" varStatus="status"><option value="${month.month}">${month.month}</option></c:forEach>
		  </select>月&nbsp;&nbsp;&nbsp;&nbsp;
	</td>
	<td width="9%"><input id="sub_search" type="button" value="搜  索" onClick="sear_Form()"/></td>	
	<td width="30%">
	<input type="hidden" id="reportMonth" name="mineReport.reportMonth"/>
	<input id="sub" type="button" value="新增汇总统计" onClick="if(get('month').value!='0')checkForm(); else alert('请选择月份！');"/></td>
  </tr>
</table>
</form>
<table>
<tr><td height="10"></td>
</tr>
</table>
<table  style="width:100%;" border="0" cellpadding="0" cellspacing="0" class="table_list">
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
	 <td>
	 	<a href="#" onClick="window.location='../mine/loadProvinceReport2012.xhtml?mineReport.id=${mine.id}&mineReport.reportMonth=${mine.reportMonth}&mineReport.type=${mineReport.type}';">查看</a>&nbsp;
	 	<a href="#" onClick="window.location='loadProvinceReport.xhtml?mineReport.id=${mine.id}&mineReport.reportMonth=${mine.reportMonth}&mineReport.type=${mineReport.type}';">
	 		<c:choose>
	 			<c:when test="${mine.state>0}">旧版查看</c:when>
	 			<c:otherwise>修改</c:otherwise>
	 		</c:choose>
	 	</a>&nbsp;
	 	<c:if test="${mine.state==0}">
	 		<a href="#" onClick="if(window.confirm('  确定删除吗？'))window.location='deleteMine.xhtml?mineReport.id=${mine.id}&mineReport.report=true';">删除</a>&nbsp;
	 	</c:if>
	 	<c:choose>
	 		<c:when test="${!mine.allowReport}"></c:when>
	 		<c:otherwise>
	 			<a href="#" onClick="if(window.confirm('  确定上报吗？'))window.location='doneProvinceReport.xhtml?mineReport.id=${mine.id}&mineReport.report=true';">上报</a>&nbsp;
	 		</c:otherwise>
	 	</c:choose>
	 	<c:choose>
	 		<c:when test="${mine.allowRollback}">
	 			<a href="#" onClick="if(window.confirm('  确定退回吗？'))window.location='rollback.xhtml?mineReport.id=${mine.id}&mineReport.report=true';">退回</a>
	 		</c:when>
	 		<c:otherwise></c:otherwise>
	 	</c:choose>
	 </td>
	 <td><a href="#" onClick="window.location='loadProvinceReport2012.xhtml?mineReport.id=${mine.id}&mineReport.reportMonth=${mine.reportMonth}&mineReport.type=${mine.type}&mineReport.print=true';">打印</a></td>
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
<c:if test="${!empty(mineReport.type)}">
	get("type").value="${mineReport.type}";
</c:if>
<c:if test="${!empty(mineReport.month)}">
	get("month").value="${mineReport.month}";
</c:if>
<c:if test="${!empty(mineReport.type)}">
	get("type").value="${mineReport.type}";
</c:if>
</script>
</html>