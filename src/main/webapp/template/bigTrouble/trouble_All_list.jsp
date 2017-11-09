<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/template/common/header.jsp" %>
<title>重大隐患</title>
  <script>
  function loc(){
	  if("${param.mineId}"==""||"${param.mineId}"==0){
	  	if("${param.tableName}"=="other"){
	  		window.location='../other/createOtherInit.xhtml';
	  	}else{
	  		window.location='../mine/createMineInit.xhtml';
	  	}
	  }else{
	  	if("${param.tableName}"=="other"){
	  		window.location='../other/loadOther.xhtml?mineReport.id=${param.mineId}';
	  	}else{
	  		window.location='../mine/loadMine.xhtml?mineReport.id=${param.mineId}';
	  	}
	  }
  }
  </script>
<body>	
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <tr>
    <td align="center" style="font-size:18px"><strong>
		重大隐患列表
		</strong>
    </td>
  </tr>
  <c:if test="${param.reportState==0||param.reportState==''}">
  </c:if>
</table>
<form method="post" name="searchForm" id="searchForm" action="loadAllBigTroubles.xhtml">
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_input">
  <tr>
	<th width="15%">填报单位</th>
	<td width="30%">
	<input type="text" name="bigTrouble.companyName" size="78" maxlength="50" value="${bigTrouble.companyName}">
	</td>
	<td width="30%"><input id="sub" type="button" value="搜  索" onClick="get('searchForm').submit();"/></td>
  </tr>
</table>
</form>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="table_list">
  <tr>
	<th width="6%">上报月份</th>
	<th width="6%">是否销号</th>
	<th width="18%">单位名称</th>
	<th width="35%">内容</th>
	<th width="10%">地址</th>
	<th width="7%">销号时间</th>
	<th width="7%">时间</th>
	<th width="7%">操作</th>
	</tr>
	<c:forEach var="list" items="${bigTroubles}" varStatus="status">
	<tr>$
	<td align="center">${list.reportMonth}&nbsp;ID:${list.id}</td>
	<td><#if list.state==0>否<#else>是</#if>&nbsp;</td>
	<td>${list.companyName}&nbsp;</td>
	<td>${list.content}&nbsp;</td>
	<td>${list.address}&nbsp;</td>
	<td>${list.stateTime}&nbsp;</td>
	<td>${list.reportMonth}&nbsp;</td>
	<td>
		<a href="javaScript:;" onClick="window.location='updateBigTroubleStateTime.xhtml?bigTrouble.id=${list.id}';">
		<#if list.state==0>销号时间<#else>销号时间</#if>
		</a>
	</td>
	
	</tr>
	</c:forEach>
	  <tr>
	 	 <td height="45" colspan="8" align="center" valign="bottom"><p:navigation pagination="${pagination}" /></td>
	  </tr>
	  <tr>
	 	 <td height="45" colspan="8" align="center" valign="bottom">
	 	 <c:choose>
			<c:when test="${param.reportState==123}">
				<input type="button" id="but_sub" value="返 回" onClick="javaScript:history.back();"/>
			</c:when>
			<c:otherwise>
				<input type="button" id="but_sub" value="返 回" onClick="loc()"/>
			</c:otherwise>
		</c:choose>
	 	 </td>
	  </tr>
</table>
</body>
</html>