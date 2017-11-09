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
  function toExcel(){
    var pageSize=document.getElementById("paPageSize").value;
  	window.location='loadBigTroubles_excel.xhtml?tableType=${bigTrouble.tableType}&tradeType=${bigTrouble.tradeType==48 ? '0' : bigTrouble.tradeType}&reportMonth=${bigTrouble.reportMonth}&state=${bigTrouble.state}&mineId=${mineId}&pagination.pageSize='+pageSize+'';
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
  <c:if test="${(param.reportState==0||param.reportState=='')&&param.tradeType!=0&&(param.tradeType<45||param.tradeType>99)}">
  <!--tr>
    <td>&nbsp;&nbsp;
		<a href="loadBigTrouble.xhtml?bigTrouble.tradeType=${param.tradeType}&bigTrouble.reportMonth=${param.reportMonth}&bigTrouble.tableType=${param.tableType}&bigTrouble.troubleType=${param.troubleType}&bigTrouble.reportState=${param.reportState}&mineId=${param.mineId=='' ? '0' : param.mineId}&tableName=${param.tableName}">添加重大隐患</a>
    </td>
  </tr-->
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
	<%--当前月未销号，下个月再销号。然后回到当前月再销号，再反销号.这样会出现问题。--%>
	<!--td>${list.state==0?'否':'是'}&nbsp;</td-->
	<c:choose><c:when test="${param.reportMonth==list.reportMonth||param.reportMonth!=list.stateTime}"><c:if test="${list.stateTime<=param.reportMonth||list.stateTime==null||list.stateTime==''}"><td>${list.state==0?'否':'是'}&nbsp;</td></c:if><c:if test="${list.stateTime>param.reportMonth&&list.stateTime!=null&&list.stateTime!=''}"><td>${list.state==0?'是':'否'}&nbsp;</td></c:if></c:when><c:otherwise><td>${list.state==0?'否':'是'}&nbsp;</td></c:otherwise></c:choose>
	<td><p>${list.companyName}&nbsp;</p></td>
	<td><p>${list.address}&nbsp;</p></td>
	<td><p>${list.content}&nbsp;</p></td>
	<td>${list.reportMonth}&nbsp;</td>
	<c:choose>
		<c:when test="${bigTrouble.isCounty=='county'}"><!--县市区用户-->
				<c:choose>
					<c:when test="${param.reportMonth==list.reportMonth}"><!--当前月-->
						<td>
							<c:choose>
								<c:when test="${param.reportState==0||param.reportState==null}"><!--未上报-->
									<a href="javaScript:;" onClick="window.location='loadBigTrouble.xhtml?bigTrouble.id=${list.id}&bigTrouble.troubleType=${param.troubleType}&bigTrouble.reportState=${param.reportState=='' ? '0' : param.reportState}&mineId=${param.mineId=='' ? '0' : param.mineId}&tableName=${param.tableName}';"><c:if test="${param.reportState==0||param.reportState==''}">修改</c:if><c:if test="${param.reportState>0}">查看</c:if></a> &nbsp;<c:if test="${param.reportState==0||param.reportState==''}"><a href="javaScript:;" onClick="if(window.confirm('  确定删除吗？'))window.location='deleteBigTrouble.xhtml?bigTrouble.id=${list.id}&bigTrouble.tradeType=${param.tradeType}&bigTrouble.reportMonth=${param.reportMonth}&bigTrouble.tableType=${param.tableType}&bigTrouble.troubleType=${param.troubleType}&bigTrouble.reportState=${param.reportState=='' ? '0' : param.reportState}&mineId=${param.mineId=='' ? '0' : param.mineId}&tableName=${param.tableName}';">删除</a></c:if>
									<c:choose>
										<c:when test="${list.state==0||list.stateTime>param.reportMonth}"><a href="javaScript:;" onClick="if(window.confirm('  确定执行吗？'))window.location='updateBigTroubleOut.xhtml?bigTrouble.id=${list.id}&bigTrouble.tradeType=${param.tradeType}&bigTrouble.reportMonth=${param.reportMonth}&bigTrouble.tableType=${param.tableType}&bigTrouble.troubleType=${param.troubleType}&bigTrouble.reportState=${param.reportState=='' ? '0' : param.reportState}&mineId=${param.mineId=='' ? '0' : param.mineId}&tableName=${param.tableName}&state=0';">销号</a></c:when>
										<c:when test="${list.state==1||list.stateTime<=param.reportMonth}"><a href="javaScript:;" onClick="if(window.confirm('  确定执行吗？'))window.location='updateBigTroubleOut.xhtml?bigTrouble.id=${list.id}&bigTrouble.tradeType=${param.tradeType}&bigTrouble.reportMonth=${param.reportMonth}&bigTrouble.tableType=${param.tableType}&bigTrouble.troubleType=${param.troubleType}&bigTrouble.reportState=${param.reportState=='' ? '0' : param.reportState}&mineId=${param.mineId=='' ? '0' : param.mineId}&tableName=${param.tableName}&state=1';">反销号</a></c:when>
									</c:choose>
								</c:when>
								<c:otherwise><!--已上报-->
									<a href="javaScript:;" onClick="window.location='loadBigTrouble.xhtml?bigTrouble.id=${list.id}&bigTrouble.troubleType=${param.troubleType}&bigTrouble.reportState=1&mineId=${param.mineId=='' ? '0' : param.mineId}&tableName=${param.tableName}';">查看</a> 
								</c:otherwise>
							 </c:choose>
						</td>
					</c:when>
					<c:otherwise><!--不是当前月-->
						<c:choose>
							<c:when test="${list.state==1&&param.reportMonth>=list.stateTime}"><!--已销号-->
								<td><a href="javaScript:;" onClick="window.location='loadBigTrouble.xhtml?bigTrouble.id=${list.id}&bigTrouble.troubleType=${param.troubleType}&bigTrouble.reportState=1&mineId=${param.mineId=='' ? '0' : param.mineId}&tableName=${param.tableName}';">查看</a></td>
							</c:when>
							<c:otherwise><!--未销号-->
								<td>
								<c:choose>
									<c:when test="${param.reportState==0||param.reportState==''}"><!--当前查看的报表未上报-->
										<c:choose>
											<c:when test="${list.chargeMan==0||list.chargeMan==null||list.chargeMan==''}"><!--未上报-->
												<a href="javaScript:;" onClick="window.location='loadBigTrouble.xhtml?bigTrouble.id=${list.id}&bigTrouble.troubleType=${param.troubleType}&bigTrouble.reportState=${param.reportState}&bigTrouble.reportState2=30&bigTrouble.reportMonth2=${param.reportMonth}&mineId=${param.mineId=='' ? '0' : param.mineId}&tableName=${param.tableName}';">修改</a> &nbsp;<a href="javaScript:;" onClick="if(window.confirm('  确定删除吗？'))window.location='deleteBigTrouble.xhtml?bigTrouble.id=${list.id}&bigTrouble.tradeType=${param.tradeType}&bigTrouble.reportMonth=${param.reportMonth}&bigTrouble.tableType=${param.tableType}&bigTrouble.troubleType=${param.troubleType}&bigTrouble.reportState=${param.reportState=='' ? '0' : param.reportState}&mineId=${param.mineId=='' ? '0' : param.mineId}&tableName=${param.tableName}';">删除</a>
												<c:choose>
													<c:when test="${list.state==0||list.stateTime>param.reportMonth}">
														<a href="javaScript:;" onClick="if(window.confirm('  确定执行吗？'))window.location='updateBigTroubleOut.xhtml?bigTrouble.id=${list.id}&bigTrouble.tradeType=${param.tradeType}&bigTrouble.reportMonth=${param.reportMonth}&bigTrouble.tableType=${param.tableType}&bigTrouble.troubleType=${param.troubleType}&bigTrouble.reportState=${param.reportState=='' ? '0' : param.reportState}&mineId=${param.mineId=='' ? '0' : param.mineId}&tableName=${param.tableName}&state=0';">销号</a>
													</c:when>
													<c:when test="${list.state==1&&list.stateTime<=param.reportMonth}">
														<a href="javaScript:;" onClick="if(window.confirm('  确定执行吗？'))window.location='updateBigTroubleOut.xhtml?bigTrouble.id=${list.id}&bigTrouble.tradeType=${param.tradeType}&bigTrouble.reportMonth=${param.reportMonth}&bigTrouble.tableType=${param.tableType}&bigTrouble.troubleType=${param.troubleType}&bigTrouble.reportState=${param.reportState=='' ? '0' : param.reportState}&mineId=${param.mineId=='' ? '0' : param.mineId}&tableName=${param.tableName}&state=1';">反销号</a>
													</c:when>
												</c:choose>
											</c:when>
											<c:otherwise><!--已上报-->
												<a href="javaScript:;" onClick="window.location='loadBigTrouble.xhtml?bigTrouble.id=${list.id}&bigTrouble.troubleType=${param.troubleType}&bigTrouble.reportState=1&mineId=${param.mineId=='' ? '0' : param.mineId}&tableName=${param.tableName}';">查看</a> 
												<c:choose>
													<c:when test="${list.state==0||list.stateTime>param.reportMonth}">
														<a href="javaScript:;" onClick="if(window.confirm('  确定执行吗？'))window.location='updateBigTroubleOut.xhtml?bigTrouble.id=${list.id}&bigTrouble.tradeType=${param.tradeType}&bigTrouble.reportMonth=${param.reportMonth}&bigTrouble.tableType=${param.tableType}&bigTrouble.troubleType=${param.troubleType}&bigTrouble.reportState=${param.reportState=='' ? '0' : param.reportState}&mineId=${param.mineId=='' ? '0' : param.mineId}&tableName=${param.tableName}&state=0';">销号</a>
													</c:when>
													<c:when test="${list.state==1&&list.stateTime<=param.reportMonth}">
														<a href="javaScript:;" onClick="if(window.confirm('  确定执行吗？'))window.location='updateBigTroubleOut.xhtml?bigTrouble.id=${list.id}&bigTrouble.tradeType=${param.tradeType}&bigTrouble.reportMonth=${param.reportMonth}&bigTrouble.tableType=${param.tableType}&bigTrouble.troubleType=${param.troubleType}&bigTrouble.reportState=${param.reportState=='' ? '0' : param.reportState}&mineId=${param.mineId=='' ? '0' : param.mineId}&tableName=${param.tableName}&state=1';">反销号</a>
													</c:when>
												</c:choose>
											</c:otherwise>
										 </c:choose>				
									</c:when>
									<c:otherwise><!--当前查看的报表已上报-->
										<a href="javaScript:;" onClick="window.location='loadBigTrouble.xhtml?bigTrouble.id=${list.id}&bigTrouble.troubleType=${param.troubleType}&bigTrouble.reportState=1&mineId=${param.mineId=='' ? '0' : param.mineId}&tableName=${param.tableName}';">查看</a> 
									</c:otherwise>
									</c:choose>
								</td>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
		</c:when>
		<c:otherwise><!--市级或省级用户-->
			<td><a href="javaScript:;" onClick="window.location='loadBigTrouble.xhtml?bigTrouble.id=${list.id}&bigTrouble.troubleType=${param.troubleType}&bigTrouble.reportState=${param.reportState=='' ? '0' : param.reportState}&mineId=${param.mineId=='' ? '0' : param.mineId}&tableName=${param.tableName}';">查看</a></td>
	    </c:otherwise>
	</c:choose>	
	</tr>
	</c:forEach>
	  <tr>
	 	 <td height="45" colspan="8" align="center" valign="bottom"><p:navigation pagination="${pagination}" /></td>
	  </tr>
	  <tr>
	 	 <td height="45" colspan="8" align="center" valign="bottom">
	 	 <c:choose><c:when test="${param.reportState==123}"><input type="button" id="but_sub" value="返 回" onclick="javaScript:history.back();"/></c:when><c:otherwise><input type="button" id="but_sub" value="返 回" onclick="loc()"/></c:otherwise></c:choose> 
	 	 	  <input type="button" id="but_sub" value="导出到excel" onclick="toExcel()"/>
	 	 </td>
	  </tr>
</table>
</body>
</html>